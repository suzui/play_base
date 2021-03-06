package controllers;

import annotations.ActionMethod;
import annotations.DataField;
import annotations.EnumClass;
import annotations.ParamField;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import javassist.*;
import javassist.bytecode.CodeAttribute;
import javassist.bytecode.LocalVariableAttribute;
import javassist.bytecode.MethodInfo;
import org.apache.commons.lang.StringUtils;
import play.Play;
import play.mvc.Before;
import play.mvc.Controller;
import utils.BaseUtils;
import vos.*;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.lang.reflect.Type;
import java.util.*;

public class DocController extends Controller {
    private static final String DOC = "doc";
    private static Gson gson = new GsonBuilder().disableHtmlEscaping().create();
    
    @Before(priority = -1)
    static void doc() {
        if (!request.params._contains(DOC)) {
            return;
        }
        try {
            List<String> codes = new ArrayList<>();
            Class<StatusCode> statusCode = StatusCode.class;
            for (Field field : statusCode.getFields()) {
                Object[] value = (Object[]) field.get(statusCode);
                codes.add(value[0] + " " + new Validation((String) value[1]).content + "\n");
            }
            Collections.sort(codes);
            
            Map<String, List<String[]>> enums = new LinkedHashMap<>();
            String frameworkPath = Play.frameworkPath.getAbsolutePath();
            String applicationPath = Play.applicationPath.getAbsolutePath();
            String[] enumModules = {frameworkPath + "/modules/play_base", applicationPath.substring(0, applicationPath.lastIndexOf("_")) + "_common"};
            for (String enumModule : enumModules) {
                for (String filename : new File(enumModule + "/app/enums").list()) {
                    if (!filename.endsWith(".java")) {
                        continue;
                    }
                    Class<?> clazz = Class.forName("enums." + filename.replace(".java", ""));
                    EnumClass ec = clazz.getAnnotation(EnumClass.class);
                    if (ec == null || !ec.visible()) {
                        continue;
                    }
                    enums.put(ec.name(), BaseUtils.enums(clazz));
                }
            }
            
            String url = request.url;
            Method method = request.invokedMethod;
            ActionMethod am = method.getAnnotation(ActionMethod.class);
            String api = am.name();
            List<Object[]> param = new ArrayList<>();
            Class one = null;
            if (method.getParameterCount() > 0) {
                Type type = method.getParameterTypes()[0];
                if (OneData.class.isAssignableFrom((Class<?>) type)) {
                    one = (Class<OneData>) type;
                }
            }
            if (one != null) {
                if (StringUtils.isNotBlank(am.param())) {
                    for (String p : StringUtils.split(am.param(), ",")) {
                        if (StringUtils.isBlank(p)) {
                            continue;
                        }
                        String _p = p.replace("-", "").replace("+", "");
                        Object[] o = new Object[6];
                        Field f = one.getField(_p);
                        DataField df = f.getAnnotation(DataField.class);
                        o[0] = f.getName();
                        o[1] = df.name();
                        o[2] = f.getType().getSimpleName();
                        o[3] = p.startsWith("-") ? "否" : "是";
                        o[4] = df.demo();
                        o[5] = df.comment() + " " + (df.enums().length > 0 ? gson.toJson(BaseUtils.enums(df.enums()[0])) : "");
                        param.add(o);
                    }
                } else if (StringUtils.isNotBlank(am.except())) {
                    for (Field f : one.getDeclaredFields()) {
                        if (am.except().contains(f.getName())) {
                            continue;
                        }
                        DataField df = f.getAnnotation(DataField.class);
                        if (df == null) {
                            continue;
                        }
                        if (!df.enable()) {
                            continue;
                        }
                        Object[] o = new Object[6];
                        o[0] = f.getName();
                        o[1] = df.name();
                        o[2] = f.getType().getSimpleName();
                        o[3] = am.required().contains(f.getName()) ? "是" : "否";
                        o[4] = df.demo();
                        o[5] = df.comment() + " " + (df.enums().length > 0 ? gson.toJson(BaseUtils.enums(df.enums()[0])) : "");
                        param.add(o);
                    }
                }
            } else {
                Parameter[] parameters = method.getParameters();
                if (parameters.length > 0) {
                    String methodName = method.getName();
                    Class controller = request.controllerClass;
                    ClassPool pool = ClassPool.getDefault();
                    ClassClassPath ccPath = new ClassClassPath(controller);
                    pool.insertClassPath(ccPath);
                    String localPath = Play.getFile(Play.mode.isProd() ? "precompiled/java" : "tmp/classes").getAbsolutePath();
                    pool.insertClassPath(localPath);
                    CtClass cc = pool.get(controller.getName());
                    CtMethod cm = cc.getDeclaredMethod(methodName);
                    MethodInfo methodInfo = cm.getMethodInfo();
                    CodeAttribute codeAttribute = methodInfo.getCodeAttribute();
                    LocalVariableAttribute attr = (LocalVariableAttribute) codeAttribute
                            .getAttribute(LocalVariableAttribute.tag);
                    String[] paramNames = new String[cm.getParameterTypes().length];
                    int pos = Modifier.isStatic(cm.getModifiers()) ? 0 : 1;
                    for (int i = 0; i < paramNames.length; i++) {
                        paramNames[i] = attr.variableName(i + pos);
                    }
                    for (Parameter p : parameters) {
                        ParamField pf = p.getAnnotation(ParamField.class);
                        if (pf != null) {
                            Object[] o = new Object[6];
                            o[0] = paramNames[Integer.parseInt(p.getName().replace("arg", ""))];
                            o[1] = pf.name();
                            o[2] = p.getType().getSimpleName();
                            o[3] = pf.required() ? "是" : "否";
                            o[4] = pf.demo();
                            o[5] = pf.comment() + " " + (pf.enums().length > 0 ? gson.toJson(BaseUtils.enums(pf.enums()[0])) : "");
                            param.add(o);
                        }
                    }
                }
            }
            Class<? extends Data>[] clazz = am.clazz();
            String result = "";
            if (clazz.length == 1 && OneData.class.isAssignableFrom(clazz[0])) {
                result = new Gson().toJson(((OneData) clazz[0].newInstance()).doc());
            } else if (clazz.length == 2 && PageData.class.isAssignableFrom(clazz[0])
                    && OneData.class.isAssignableFrom(clazz[1])) {
                result = new Gson().toJson(((PageData) clazz[0].newInstance()).doc((Class<? extends OneData>) clazz[1]));
            }
            if (!am.withcode()) {
                codes = Collections.EMPTY_LIST;
            }
            if (!am.withenum()) {
                enums = Collections.EMPTY_MAP;
            }
            renderTemplate("doc.html", url, api, param, result, codes, enums);
        } catch (Exception e) {
            e.printStackTrace();
            renderHtml("文档错误:" + e.getMessage());
        } finally {
        }
    }
    
}