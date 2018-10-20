package binders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import org.apache.commons.lang.StringUtils;
import play.Logger;
import play.data.binding.Global;
import play.data.binding.TypeBinder;
import play.i18n.Lang;
import play.mvc.Http.Request;
import utils.BaseUtils;
import vos.OneData;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

@Global
public class DataBinder implements TypeBinder<OneData> {
    
    public static final ObjectMapper mapper = new ObjectMapper();
    public static final Gson gson = new Gson();
    
    @Override
    public Object bind(String name, Annotation[] annotations, String value, Class actualClass, Type genericType) throws Exception {
        Map<String, Object> params = new HashMap<>();
        String lang = BaseUtils.propertyOn("i18n") ? Lang.get() : null;
        for (Entry<String, String> e : Request.current().params.allSimple().entrySet()) {
            try {
                String k = e.getKey(), v = e.getValue();
                if (k.equals("vo") || k.equals("body")) continue;//vo body 参数过滤
                if (v == null || v.equals("null") || StringUtils.equals(value, "(null)") || v.equals("undefined") || v.equals("NaN"))
                    continue;//空值过滤
                if (v.equals("")) {
                    if (!String.class.isAssignableFrom(actualClass.getField(k).getType())) continue;//非string类型空值过滤
                    else params.put(k, v);
                } else if (v.startsWith("[") && v.endsWith("]")) {
                    if (!String.class.isAssignableFrom(actualClass.getField(k).getType()))
                        params.put(k, mapper.readValue(v, List.class));//list类型处理
                    else params.put(k, v);
                } else if (v.startsWith("{") && v.endsWith("}")) {
                    if (!String.class.isAssignableFrom(actualClass.getField(k).getType()))
                        params.put(k, mapper.readValue(v, Map.class));//vo类型处理
                    else params.put(k, v);
                } else {
                    if (StringUtils.isNotBlank(lang)) {
                        char[] cs = k.toCharArray();
                        cs[0] -= 32;
                        params.put(lang + String.valueOf(cs), v);
                    }
                    params.put(k, v);
                }
            } catch (Exception exception) {
                continue;
            }
        }
        if (!params.containsKey("page")) params.put("page", 1);
        if (!params.containsKey("size")) params.put("size", Integer.MAX_VALUE);
        String json = mapper.writeValueAsString(params);
        Logger.info("[databinder]:%s", json);
        return gson.fromJson(json, actualClass);
    }
}