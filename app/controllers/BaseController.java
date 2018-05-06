package controllers;

import jobs.UpdateLoginInfoJob;
import models.token.AccessToken;
import models.token.BasePerson;
import org.apache.commons.lang.StringUtils;
import play.Logger;
import play.Play;
import play.cache.Cache;
import play.db.jpa.JPA;
import play.mvc.*;
import play.mvc.Http.Cookie;
import play.mvc.Http.Header;
import play.mvc.Http.Request;
import play.mvc.Http.Response;
import play.mvc.Scope.Session;
import utils.ApiQueue;
import vos.Result;
import vos.Result.StatusCode;
import vos.back.ApiVO;

import javax.persistence.EntityTransaction;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@With(DocController.class)
public class BaseController extends Controller {
    
    private static final String VO = "vo";
    private static final String DOC = "doc";
    private static final String CURRENT_PERSON_ID = "currentPersonId";
    private static final String KEEP_PERSON_ID = "keepPersonId";
    public static final String BASE_URL = Play.configuration.getProperty("application.baseUrl");
    
    @Before(priority = 0)
    static void api() {
        final Request request = Request.current();
        ApiVO apiVO = new ApiVO();
        apiVO.url = request.url;
        apiVO.action = request.action;
        apiVO.method = request.method;
        apiVO.body = String.format("", request.body);
        apiVO.header = request.headers.entrySet().stream()
                .map(e -> e.getKey() + ":" + e.getValue()).collect(Collectors.toList()) + "";
        apiVO.param = request.params.allSimple().entrySet().stream()
                .map(e -> e.getKey() + ":" + e.getValue()).collect(Collectors.toList()) + "";
        Cache.add(request.hashCode() + "", apiVO);
    }
    
    @Before(priority = 1)
    static void requestInfo() {
        Logger.info("[requestInfo start]:================");
        Logger.info("[requestInfo header]:%s", request.headers.entrySet().stream()
                .map(e -> e.getKey() + ":" + e.getValue()).collect(Collectors.toList()));
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
                if (Cache.get(key) != null) {
                    renderJSON(Result.failed(StatusCode.SYSTEM_POST_REPEAT));
                }
                Cache.add(key, true, "10mn");
            }
        }
        Logger.info("[randomseed end]:================");
    }
    
    @Before(priority = 3)
    static void params() {
        Logger.info("[params start]:================");
        request.params.put(VO, "");
        Logger.info("[params]:%s", request.params.allSimple().entrySet().stream()
                .map(e -> e.getKey() + ":" + e.getValue()).collect(Collectors.toList()));
        Logger.info("[params end]:================");
    }
    
    @Before(priority = 4)
    static void headers() {
        Logger.info("[headers start]:================");
        List<String> emptys = new ArrayList<>();
        request.headers.entrySet().forEach(e -> {
            if (e.getValue() == null || StringUtils.isBlank(e.getValue().value()) || StringUtils.equals(e.getValue().value(), "null")) {
                emptys.add(e.getKey());
            }
        });
        emptys.forEach(key -> request.headers.remove(key));
        Logger.info("[headers end]:================");
    }
    
    //@Catch
    static void exception(Throwable throwable) {
        EntityTransaction transaction = JPA.em().getTransaction();
        if (transaction.isActive()) {
            transaction.rollback();
        }
        throwable.printStackTrace();
        if (!request.params._contains(DOC)) {
            Logger.info("[exception start]:================");
            ApiVO apiVO = (ApiVO) Cache.get(request.hashCode() + "");
            apiVO.exception = throwable.getMessage();
            apiVO.status = response.status + "";
            Cache.replace(request.hashCode() + "", apiVO);
            Logger.info("[exception]:%s", throwable);
            Logger.info("[exception end]:================");
            renderJSON(Result.failed());
        }
    }
    
    @After
    static void status() {
        if (!request.params._contains(DOC)) {
            Logger.info("[status start]:================");
            Logger.info("[status]:%s", response.status);
            ApiVO apiVO = (ApiVO) Cache.get(request.hashCode() + "");
            apiVO.status = response.status + "";
            Cache.replace(request.hashCode() + "", apiVO);
            Logger.info("[status end]:================");
        }
    }
    
    @Finally
    static void finish() {
        if (!request.params._contains(DOC)) {
            Logger.info("[finish start]:================");
            Logger.info("[finish]:%s", response.out);
            ApiVO apiVO = (ApiVO) Cache.get(request.hashCode() + "");
            apiVO.result = response.out + "";
            Cache.safeDelete(request.hashCode() + "");
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
        ApiVO apiVO = (ApiVO) Cache.get(request.hashCode() + "");
        apiVO.personId = token.person.id;
        Cache.replace(request.hashCode() + "", apiVO);
        Logger.info("[accesstoken]:%s,%s,%s", token.person.id, token.person.name, token.person.username);
        final Map<String, Header> headers = request.headers;
        final String appVersion = headers.get("appversion") == null ? null : headers.get("appversion").value();
        final String appType = headers.get("apptype") == null ? null : headers.get("apptype").value();
        final String osVersion = headers.get("osversion") == null ? null : headers.get("osversion").value();
        final String clientType = headers.get("clienttype") == null ? null : headers.get("clienttype").value();
        final String deviceToken = headers.get("devicetoken") == null ? null : headers.get("devicetoken").value();
        if (token.person.lastLoginTime == null || System.currentTimeMillis() - token.person.lastLoginTime > 3 * 60 * 1000) {
            new UpdateLoginInfoJob(accesstoken, appVersion, appType, osVersion, clientType, deviceToken).now();
        }
        Logger.info("[accesstoken end]:================");
    }
    
    
    @Util
    protected static void setPersonIdToSession(Long personId) {
        setSession(CURRENT_PERSON_ID, personId + "");
    }
    
    @Util
    protected static void removePersonIdToSession() {
        removeSession(CURRENT_PERSON_ID);
    }
    
    @Util
    protected static String getPersonIdFromSession() {
        return getSession(CURRENT_PERSON_ID);
    }
    
    @Util
    protected static void setPersonIdToCookie(Long personId) {
        setCookie(KEEP_PERSON_ID, personId + "");
    }
    
    @Util
    protected static void removePersonIdToCookie() {
        removeCookie(KEEP_PERSON_ID);
    }
    
    
    @Util
    protected static String getPersonIdFromCookie() {
        return getCookie(KEEP_PERSON_ID);
    }
    
    @Util
    protected static <T extends BasePerson> T getCurrPerson() {
        String personId = getPersonIdFromSession();
        if (personId == null) {
            personId = getPersonIdFromCookie();
        }
        if (personId == null) {
            return null;
        }
        return BasePerson.findByID(Long.parseLong(personId));
    }
    
    @Util
    protected static String getToken() {
        return getHeader("accesstoken");
    }
    
    @Util
    protected static AccessToken getAccessTokenByToken() {
        String token = getToken();
        return token == null ? null : AccessToken.findByAccesstoken(token);
    }
    
    @Util
    public static <T extends BasePerson> T getPersonByToken() {
        String token = getToken();
        return token == null ? null : AccessToken.findPersonByAccesstoken(token);
    }
    
    @Util
    public static Long getApp() {
        String app = getHeader("app");
        if (app == null) {
            return null;
        }
        Logger.info("[headerapp]:%s", app);
        return Long.parseLong(app);
    }
    
    @Util
    public static Long getSource() {
        String source = getHeader("source");
        if (StringUtils.isBlank(source)) {
            source = getHeader("organize");
        }
        if (StringUtils.isBlank(source)) {
            return null;
        }
        Logger.info("[headersource]:%s", source);
        return Long.parseLong(source);
    }
    
    @Util
    protected static void setSession(String key, String value) {
        Session.current().put(key, value);
    }
    
    @Util
    protected static void removeSession(String key) {
        Session.current().remove(key);
    }
    
    @Util
    public static String getSession(String key) {
        Session session = Session.current();
        return session.contains(key) ? session.get(key) : null;
    }
    
    @Util
    protected static void setCookie(String key, String value) {
        Response.current().setCookie(key, value, "365d");
    }
    
    @Util
    protected static void removeCookie(String key) {
        Response.current().removeCookie(key);
    }
    
    @Util
    public static String getCookie(String key) {
        Cookie cookie = Request.current().cookies.get(key);
        return null != cookie ? cookie.value : null;
    }
    
    @Util
    protected static void setHeader(String key, String value) {
        Request.current().headers.put(key, new Header(key, value));
    }
    
    @Util
    protected static void removeHeader(String key) {
        Request.current().headers.remove(key);
    }
    
    @Util
    public static String getHeader(String key) {
        Request request = Request.current();
        if (request == null) {
            return null;
        }
        Header header = request.headers.get(key);
        return header != null ? header.value() : null;
    }
}