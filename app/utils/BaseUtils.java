package utils;

import interfaces.BaseEnum;
import models.back.Admin;
import models.token.AccessToken;
import models.token.BasePerson;
import org.apache.commons.lang.StringUtils;
import play.Play;
import play.jobs.Job;
import play.jobs.JobsPlugin;
import play.mvc.Http;
import play.mvc.Scope;
import play.utils.Java;
import vos.VersionVO;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.FutureTask;
import java.util.concurrent.ScheduledFuture;

public class BaseUtils {
    
    private static final String CURRENT_ADMIN_ID = "currentAdminId";
    private static final String KEEP_ADMIN_ID = "keepAdminId";
    public static final String BASE_URL = Play.configuration.getProperty("application.baseUrl");
    
    public static boolean isProd() {
        return "p".equals(Play.id);
    }
    
    public static boolean isTest() {
        return "t".equals(Play.id);
    }
    
    public static boolean isDev() {
        return "d".equals(Play.id);
    }
    
    public static boolean propertyOn(String property) {
        return Play.configuration.getProperty(property, "off").equals("on");
    }
    
    public static void setSession(String key, String value) {
        Scope.Session.current().put(key, value);
    }
    
    public static void removeSession(String key) {
        Scope.Session.current().remove(key);
    }
    
    public static String getSession(String key) {
        Scope.Session session = Scope.Session.current();
        return session != null && session.contains(key) ? session.get(key) : null;
    }
    
    public static void setCookie(String key, String value) {
        Http.Response.current().setCookie(key, value, "365d");
    }
    
    public static void removeCookie(String key) {
        Http.Response.current().removeCookie(key);
    }
    
    public static String getCookie(String key) {
        Http.Cookie cookie = Http.Request.current().cookies.get(key);
        return null != cookie ? cookie.value : null;
    }
    
    public static void setHeader(String key, String value) {
        Http.Request.current().headers.put(key, new Http.Header(key, value));
    }
    
    public static void removeHeader(String key) {
        Http.Request.current().headers.remove(key);
    }
    
    
    public static String getHeader(String key) {
        Http.Request request = Http.Request.current();
        if (request == null) {
            return null;
        }
        Http.Header header = request.headers.get(key);
        return header != null ? header.value() : null;
    }
    
    
    public static void setAdminToSession(Long adminId) {
        setSession(CURRENT_ADMIN_ID, adminId + "");
    }
    
    
    public static void removeAdminToSession() {
        removeSession(CURRENT_ADMIN_ID);
    }
    
    
    public static String getAdminFromSession() {
        return getSession(CURRENT_ADMIN_ID);
    }
    
    
    public static void setAdminToCookie(Long adminId) {
        setCookie(KEEP_ADMIN_ID, adminId + "");
    }
    
    
    public static void removeAdminToCookie() {
        removeCookie(KEEP_ADMIN_ID);
    }
    
    
    public static String getAdminFromCookie() {
        return getCookie(KEEP_ADMIN_ID);
    }
    
    
    public static Admin getCurrAdmin() {
        String adminId = getAdminFromSession();
        if (adminId == null) {
            adminId = getAdminFromCookie();
        }
        if (adminId == null) {
            return null;
        }
        return Admin.findByID(Long.parseLong(adminId));
    }
    
    
    public static String getToken() {
        return getHeader("accesstoken");
    }
    
    
    public static AccessToken getAccessTokenByToken() {
        String token = getToken();
        return token == null ? null : AccessToken.findByAccesstoken(token);
    }
    
    
    public static <T extends BasePerson> T getPersonByToken() {
        String token = getToken();
        return token == null ? null : AccessToken.findPersonByAccesstoken(token);
    }
    
    
    public static Long getApp() {
        String app = getHeader("app");
        if (app == null) {
            return null;
        }
        return Long.parseLong(app);
    }
    
    public static Long getSource() {
        String source = getHeader("organize");
        if (StringUtils.isBlank(source)) {
            return null;
        }
        return Long.parseLong(source);
    }
    
    public static Long getRoot() {
        String root = getHeader("root");
        if (StringUtils.isBlank(root)) {
            return null;
        }
        return Long.parseLong(root);
    }
    
    public static Long getOrganize() {
        String organize = getHeader("organize");
        if (StringUtils.isBlank(organize)) {
            return null;
        }
        return Long.parseLong(organize);
    }
    
    public static String getAppversion() {
        return getHeader("appversion");
    }
    
    public static String getApptype() {
        return getHeader("apptype");
    }
    
    public static String getOsversion() {
        return getHeader("osversion");
    }
    
    public static String getClienttype() {
        return getHeader("clienttype");
    }
    
    public static String getDevicetoken() {
        return getHeader("devicetoken");
    }
    
    public static Boolean isOldVersion() {
        VersionVO versionVO = (VersionVO) CacheUtils.get(VersionVO.key(getApptype(), getClienttype()));
        String appversion = getAppversion();
        return appversion != null && versionVO != null && versionVO.version.compareTo(appversion) > 0;
    }
    
    public static String initPassword() {
        return CodeUtils.md5("123456");
    }
    
    public static void cancelJob(Class clazz) {
        BlockingQueue<Runnable> queue = JobsPlugin.executor.getQueue();
        for (final Object o : queue) {
            ScheduledFuture task = (ScheduledFuture) o;
            if (task.isDone() || task.isCancelled()) {
                continue;
            }
            Job job = (Job) Java.extractUnderlyingCallable((FutureTask) task);
            if (clazz.isAssignableFrom(job.getClass())) {
                task.cancel(true);
                break;
            }
        }
    }
    
    
    public static Boolean collectionEmpty(Collection collection) {
        return collection == null || collection.isEmpty();
    }
    
    public static Boolean collectionNotEmpty(Collection collection) {
        return collection != null && !collection.isEmpty();
    }
    
    public static List<String[]> enums(Class clazz) {
        try {
            Method method = clazz.getMethod("values");
            BaseEnum[] values = (BaseEnum[]) method.invoke(null, null);
            List<String[]> list = new ArrayList<>();
            for (BaseEnum value : values) {
                list.add(new String[]{value.code() + "", value.intro()});
            }
            return list;
        } catch (Exception e) {
            return Collections.EMPTY_LIST;
        }
    }
    
    
}

