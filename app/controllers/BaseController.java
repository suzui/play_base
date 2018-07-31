package controllers;

import annotations.ActionMethod;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import enums.ClientType;
import exceptions.ResultException;
import jobs.UpdateLoginInfoJob;
import models.back.Admin;
import models.token.AccessToken;
import models.token.BasePerson;
import org.apache.commons.lang.StringUtils;
import play.Logger;
import play.db.jpa.JPA;
import play.mvc.*;
import play.mvc.Http.Header;
import play.mvc.Http.Request;
import utils.ApiQueue;
import utils.BaseUtils;
import utils.CacheUtils;
import vos.Result;
import vos.Result.StatusCode;
import vos.VersionVO;
import vos.back.ApiVO;

import javax.persistence.EntityTransaction;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@With(DocController.class)
public class BaseController extends Controller {
    
    private static final String VO = "vo";
    private static final String DOC = "doc";
    private static Gson gson = new GsonBuilder().disableHtmlEscaping().create();
    
    @Before(priority = 0)
    static void api() {
        final Request request = Request.current();
        ApiVO apiVO = new ApiVO();
        apiVO.startTime = System.currentTimeMillis();
        apiVO.url = request.url;
        apiVO.action = request.action;
        apiVO.method = request.method;
        apiVO.body = String.format("", request.body);
        apiVO.header = gson.toJson(request.headers);
        apiVO.param = gson.toJson(request.params.allSimple());
        CacheUtils.add(request.hashCode() + "", apiVO);
    }
    
    @Before(priority = 1)
    static void requestInfo() {
        Logger.info("[requestInfo start]:================");
        Logger.info("[requestInfo header]:%s", gson.toJson(request.headers));
        Logger.info("[requestInfo action]:%s,%s,%s,%s", request.isAjax(), request.method, request.url, request.action);
        Logger.info("[requestInfo end]:================");
    }
    
    @Before(priority = 2)
    static void randomseed() {
        Logger.info("[randomseed start]:================");
        if (request != null && "post".equalsIgnoreCase(request.method)) {
            final Header randomseed = request.headers.get("randomseed");
            if (randomseed != null) {
                Logger.info("[randomseed]:%s", randomseed);
                final String key = request.action + randomseed.value();
                if (CacheUtils.get(key) != null) {
                    renderJSON(Result.failed(StatusCode.SYSTEM_POST_REPEAT));
                }
                CacheUtils.add(key, true, "10mn");
            }
        }
        Logger.info("[randomseed end]:================");
    }
    
    @Before(priority = 3)
    static void params() {
        Logger.info("[params start]:================");
        request.params.put(VO, "");
        Map<String, String> params = request.params.allSimple();
        Logger.info("[params]:%s", gson.toJson(params));
        if (BaseUtils.propertyOn("validation")) {
            ActionMethod am = request.invokedMethod.getAnnotation(ActionMethod.class);
            if (am != null && StringUtils.isNotBlank(am.param())) {
                for (String param : StringUtils.split(am.param().replaceAll("\\+", ""), ",")) {
                    if (StringUtils.isBlank(param) || param.startsWith("-")) {
                        continue;
                    }
                    if (!params.containsKey(param)) {
                        renderJSON(Result.failed(StatusCode.SYSTEM_PARAM_ERROR, param + "不能为空"));
                    }
                }
            }
        }
        Logger.info("[params end]:================");
    }
    
    @Before(priority = 4)
    static void headers() {
        Logger.info("[headers start]:================");
        List<String> emptys = new ArrayList<>();
        request.headers.entrySet().forEach(e -> {
            if (e.getValue() == null) {
                emptys.add(e.getKey());
            } else {
                String value = e.getValue().value();
                if (StringUtils.isBlank(value) || StringUtils.equals(value, "null") || StringUtils.equals(value, "(null)") || StringUtils.equals(value, "undefined") || StringUtils.equals(value, "NaN")) {
                    emptys.add(e.getKey());
                }
            }
        });
        emptys.forEach(key -> request.headers.remove(key));
        Logger.info("[headers end]:================");
    }
    
    @Catch
    static void exception(Throwable throwable) {
        System.err.println("xxx");
        if (!request.params._contains(DOC)) {
            EntityTransaction transaction = JPA.em().getTransaction();
            if (transaction.isActive()) {
                transaction.rollback();
            }
            //throwable.printStackTrace();
            Logger.info("[exception start]:================");
            ApiVO apiVO = (ApiVO) CacheUtils.get(request.hashCode() + "");
            apiVO.status = response.status + "";
            apiVO.exception = throwable.getMessage() + "\n";
            for (StackTraceElement stackTraceElement : throwable.getStackTrace()) {
                apiVO.exception += stackTraceElement.toString() + "\n";
            }
            CacheUtils.replace(request.hashCode() + "", apiVO);
            Logger.info("[exception]:%s", apiVO.exception);
            Logger.info("[exception end]:================");
            Object[] codemessage = StatusCode.FAIL;
            if (throwable instanceof ResultException) {
                codemessage = ((ResultException) throwable).codemessage;
            }
            renderJSON(Result.failed(codemessage));
        }
    }
    
    @After
    static void status() {
        if (!request.params._contains(DOC)) {
            Logger.info("[status start]:================");
            Logger.info("[status]:%s", response.status);
            ApiVO apiVO = (ApiVO) CacheUtils.get(request.hashCode() + "");
            apiVO.status = response.status + "";
            CacheUtils.replace(request.hashCode() + "", apiVO);
            Logger.info("[status end]:================");
        }
    }
    
    @Finally
    static void finish() {
        if (!request.params._contains(DOC)) {
            Logger.info("[finish start]:================");
            Logger.info("[finish]:%s", response.out);
            ApiVO apiVO = (ApiVO) CacheUtils.get(request.hashCode() + "");
            apiVO.result = response.out + "";
            apiVO.endTime = System.currentTimeMillis();
            CacheUtils.safeDelete(request.hashCode() + "");
            ApiQueue.getInstance().add(apiVO);
            Logger.info("[finish end]:================");
        }
    }
    
    @Util
    protected static void accesstoken() {
        Logger.info("[accesstoken start]:================");
        final Request request = Request.current();
        if (request.params._contains(DOC)) {
            return;
        }
        final String accesstoken = getToken();
        if (StringUtils.isBlank(accesstoken) || AccessToken.findByAccesstoken(accesstoken) == null) {
            renderJSON(Result.failed(StatusCode.SYSTEM_TOKEN_UNVALID));
        }
        final AccessToken token = getAccessTokenByToken();
        if (token == null) {
            renderJSON(Result.failed(StatusCode.SYSTEM_TOKEN_UNVALID));
        }
        ApiVO apiVO = (ApiVO) CacheUtils.get(request.hashCode() + "");
        apiVO.personId = token.person.id;
        apiVO.personToken = token.accesstoken;
        apiVO.personInfo = StringUtils.join(Arrays.asList(token.person.username, token.person.name, token.person.phone).stream().filter(s -> StringUtils.isNotBlank(s)).toArray(), ",");
        CacheUtils.replace(request.hashCode() + "", apiVO);
        Logger.info("[accesstoken]:%s,%s,%s", token.person.id, token.person.name, token.person.username);
        final String appVersion = BaseUtils.getHeader("appversion");
        final String appType = BaseUtils.getHeader("apptype");
        final String osVersion = BaseUtils.getHeader("osversion");
        final String clientType = BaseUtils.getHeader("clienttype");
        final String deviceToken = BaseUtils.getHeader("devicetoken");
        if (StringUtils.isBlank(token.appVersion) || System.currentTimeMillis() - token.updateTime > 3 * 60 * 1000) {
            new UpdateLoginInfoJob(accesstoken, appVersion, appType, osVersion, clientType, deviceToken).now();
        }
        if (appVersion != null && appType != null && clientType != null && !StringUtils.equals(clientType, ClientType.WEB.code() + "") && CacheUtils.get(VersionVO.key(appType, clientType)) != null) {
            VersionVO versionVO = (VersionVO) CacheUtils.get(VersionVO.key(appType, clientType));
            if (versionVO.version.compareTo(appVersion) > 0 && versionVO.isForcedUpdate > 0) {
                renderJSON(Result.failed(StatusCode.SYSTEM_APP_UPDATE));
            }
        }
        Logger.info("[accesstoken end]:================");
    }
    
    @Util
    protected static void setAdminToSession(Long adminId) {
        BaseUtils.setAdminToSession(adminId);
    }
    
    @Util
    protected static void removeAdminToSession() {
        BaseUtils.removeAdminToSession();
    }
    
    @Util
    protected static String getAdminFromSession() {
        return BaseUtils.getAdminFromSession();
    }
    
    @Util
    protected static void setAdminToCookie(Long adminId) {
        BaseUtils.setAdminToCookie(adminId);
    }
    
    @Util
    protected static void removeAdminToCookie() {
        BaseUtils.removeAdminToCookie();
    }
    
    
    @Util
    protected static String getAdminFromCookie() {
        return BaseUtils.getAdminFromCookie();
    }
    
    @Util
    protected static Admin getCurrAdmin() {
        return BaseUtils.getCurrAdmin();
    }
    
    @Util
    protected static String getToken() {
        return BaseUtils.getToken();
    }
    
    @Util
    protected static AccessToken getAccessTokenByToken() {
        return BaseUtils.getAccessTokenByToken();
    }
    
    @Util
    protected static <T extends BasePerson> T getPersonByToken() {
        return BaseUtils.getPersonByToken();
    }
    
    @Util
    protected static Long getApp() {
        return BaseUtils.getApp();
    }
    
    @Util
    protected static Long getRoot() {
        return BaseUtils.getRoot();
    }
    
    @Util
    protected static Long getOrganize() {
        return BaseUtils.getOrganize();
    }
    
    @Deprecated
    @Util
    protected static Long getSource() {
        Long source = BaseUtils.getSource();
        if (source == null) {
            source = BaseUtils.getOrganize();
        }
        return source;
    }
    
    @Util
    protected static void setSession(String key, String value) {
        BaseUtils.setSession(key, value);
    }
    
    @Util
    protected static void removeSession(String key) {
        BaseUtils.removeSession(key);
    }
    
    @Util
    protected static String getSession(String key) {
        return BaseUtils.getSession(key);
    }
    
    @Util
    protected static void setCookie(String key, String value) {
        BaseUtils.setCookie(key, value);
    }
    
    @Util
    protected static void removeCookie(String key) {
        BaseUtils.removeCookie(key);
    }
    
    @Util
    protected static String getCookie(String key) {
        return BaseUtils.getCookie(key);
    }
    
    @Util
    protected static void setHeader(String key, String value) {
        BaseUtils.setHeader(key, value);
    }
    
    @Util
    protected static void removeHeader(String key) {
        BaseUtils.removeHeader(key);
    }
    
    @Util
    protected static String getHeader(String key) {
        return BaseUtils.getHeader(key);
    }
}