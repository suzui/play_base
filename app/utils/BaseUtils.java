package utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import interfaces.BaseEnum;
import models.BaseModel;
import models.back.Admin;
import models.token.AccessToken;
import models.token.BasePerson;
import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.StringUtils;
import play.Play;
import play.jobs.Job;
import play.jobs.JobsPlugin;
import play.mvc.Http;
import play.mvc.Scope;
import play.utils.Java;
import vos.VersionVO;

import java.lang.reflect.Method;
import java.text.DecimalFormat;
import java.util.*;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.FutureTask;
import java.util.concurrent.ScheduledFuture;
import java.util.stream.Collectors;

public class BaseUtils {
    
    private static final String CURRENT_ADMIN_ID = "currentAdminId";
    private static final String KEEP_ADMIN_ID = "keepAdminId";
    public static final String BASE_URL = Play.configuration.getProperty("application.baseUrl");
    public static final String QINIU_URL = Play.configuration.getProperty("qiniu.domain");
    
    public static final ObjectMapper mapper = new ObjectMapper();
    public static final Gson gson = new Gson();
    
    public static boolean isProd() {
        String playid = Play.id;
        return playid != null && playid.endsWith("p");
    }
    
    public static boolean isTest() {
        String playid = Play.id;
        return playid != null && playid.endsWith("t");
    }
    
    public static boolean isDev() {
        String playid = Play.id;
        return playid != null && playid.endsWith("d");
    }
    
    public static String property(String key, String defualt) {
        return Play.configuration.getProperty(key, defualt);
    }
    
    public static String property(String key) {
        return Play.configuration.getProperty(key);
    }
    
    public static boolean propertyOn(String key) {
        return property(key, "off").equals("on");
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
        String source = getHeader("source");
        if (StringUtils.isBlank(source)) {
            return null;
        }
        return Long.parseLong(source);
    }
    
    public static Long getOrganize() {
        String organize = getHeader("organize");
        if (StringUtils.isBlank(organize)) {
            return null;
        }
        return Long.parseLong(organize);
    }
    
    public static Long getRoot() {
        String root = getHeader("root");
        if (StringUtils.isBlank(root)) {
            return null;
        }
        return Long.parseLong(root);
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
    
    public static Boolean isCurrentVersion() {
        String key = VersionVO.key(getApptype(), getClienttype());
        return isCurrentVersion(VersionVO.version(key), getAppversion());
    }
    
    public static Boolean isCurrentVersion(String server, String app) {
        return StringUtils.equals(server, app);
    }
    
    public static Boolean isOldVersion() {
        String key = VersionVO.key(getApptype(), getClienttype());
        return isOldVersion(VersionVO.version(key), getAppversion());
    }
    
    public static Boolean isOldVersion(String server, String app) {
        if (StringUtils.isBlank(server) || StringUtils.isBlank(app)) {
            return false;
        }
        String[] servers = StringUtils.split(server, "."), apps = StringUtils.split(app, ".");
        if (servers.length != apps.length || servers.length != 3 || apps.length != 3) {
            return false;
        }
        DecimalFormat df = new DecimalFormat("00");
        for (int i = 0; i < 3; i++) {
            servers[i] = df.format(Integer.valueOf(servers[i]));
            apps[i] = df.format(Integer.valueOf(apps[i]));
        }
        System.err.println(join(servers));
        System.err.println(join(apps));
        return join(servers).compareTo(join(apps)) > 0;
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
    
    public static List page(List list, int page, int size, Comparator comparator) {
        if (collectionEmpty(list)) {
            return new ArrayList<>();
        }
        int from = (page - 1) * size;
        int to = Math.min(list.size(), page * size);
        if (from >= list.size()) {
            return new ArrayList<>();
        }
        if (comparator != null) {
            list.sort(comparator);
        }
        return list.subList(from, to);
    }
    
    public static List page(List list, int page, int size) {
        return page(list, page, size, null);
    }
    
    public static List<String> strToList(String string) {
        if (StringUtils.isBlank(string)) {
            return new ArrayList<>();
        }
        return Arrays.asList(StringUtils.split(string, ","));
    }
    
    public static List<Long> idsToList(String ids) {
        if (StringUtils.isBlank(ids)) {
            return new ArrayList<>();
        }
        return Arrays.stream(StringUtils.split(ids, ",")).map(id -> Long.parseLong(id)).collect(Collectors.toList());
    }
    
    public static List<Integer> intsToList(String ints) {
        if (StringUtils.isBlank(ints)) {
            return new ArrayList<>();
        }
        return Arrays.stream(StringUtils.split(ints, ",")).map(i -> Integer.parseInt(i)).collect(Collectors.toList());
    }
    
    public static List<Integer> codesToList(String codes) {
        if (StringUtils.isBlank(codes)) {
            return new ArrayList<>();
        }
        return Arrays.stream(StringUtils.split(codes, ",")).map(code -> Integer.parseInt(code)).collect(Collectors.toList());
    }
    
    public static <T extends BaseModel> List<Long> modelToId(List<T> models) {
        if (collectionEmpty(models)) {
            return new ArrayList<>();
        }
        return models.stream().map(m -> m.id).collect(Collectors.toList());
    }
    
    public static String join(Object[] array) {
        if (array == null || array.length == 0) {
            return "";
        }
        return StringUtils.join(array, ",");
    }
    
    public static String join(List list) {
        if (collectionEmpty(list)) {
            return "";
        }
        return StringUtils.join(list, ",");
    }
    
    public static List fromJson(String json) {
        return gson.fromJson(json, List.class);
    }
    
    public static String toJson(List json) {
        return gson.toJson(json);
    }
    
    public static String listToHql(List<String> list) {
        if (collectionEmpty(list)) {
            return "";
        }
        return list.stream().map(s -> "'" + s + "'").collect(Collectors.joining());
    }
    
    public static String genURL(String url, Object... params) {
        return String.format(url, params);
    }
    
    public Boolean toBooleanObject(Integer i) {
        return BooleanUtils.toBooleanObject(i);
    }
    
    public Integer toIntegerObject(Boolean b) {
        return BooleanUtils.toIntegerObject(b);
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
            return new ArrayList<>();
        }
    }
    
}

