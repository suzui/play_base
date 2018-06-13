package utils;

import models.token.AccessToken;
import models.token.BasePerson;
import org.apache.commons.lang.StringUtils;
import play.Play;
import play.jobs.Job;
import play.jobs.JobsPlugin;
import play.mvc.Http;
import play.mvc.Scope;
import play.utils.Java;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.FutureTask;
import java.util.concurrent.ScheduledFuture;

public class BaseUtils {
    
    private static final String CURRENT_PERSON_ID = "currentPersonId";
    private static final String KEEP_PERSON_ID = "keepPersonId";
    public static final String BASE_URL = Play.configuration.getProperty("application.baseUrl");
    
    
    public static boolean isProd() {
        return "p".equals(Play.id);
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
    
    
    public static void setPersonIdToSession(Long personId) {
        setSession(CURRENT_PERSON_ID, personId + "");
    }
    
    
    public static void removePersonIdToSession() {
        removeSession(CURRENT_PERSON_ID);
    }
    
    
    public static String getPersonIdFromSession() {
        return getSession(CURRENT_PERSON_ID);
    }
    
    
    public static void setPersonIdToCookie(Long personId) {
        setCookie(KEEP_PERSON_ID, personId + "");
    }
    
    
    public static void removePersonIdToCookie() {
        removeCookie(KEEP_PERSON_ID);
    }
    
    
    public static String getPersonIdFromCookie() {
        return getCookie(KEEP_PERSON_ID);
    }
    
    
    public static <T extends BasePerson> T getCurrPerson() {
        String personId = getPersonIdFromSession();
        if (personId == null) {
            personId = getPersonIdFromCookie();
        }
        if (personId == null) {
            return null;
        }
        return BasePerson.findByID(Long.parseLong(personId));
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
        String app = getHeader("pad");
        if (app == null) {
            return null;
        }
        return Long.parseLong(app);
    }
    
    public static Long getSource() {
        String source = getHeader("source");
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
    
    
}
