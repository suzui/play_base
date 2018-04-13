package utils;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import play.Play;
import play.libs.WS;
import play.libs.WS.HttpResponse;
import results.sso.*;

import java.io.IOException;

public class SSOUtils {
    
    public static final String HOST = Play.configuration.getProperty("sso.host");
    public static final String MASTER = Play.configuration.getProperty("sso.master");
    public static final ObjectMapper mapper = new ObjectMapper();
    
    static {
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }
    
    public static String SECRET;
    
    public static AppResult auth() {
        HttpResponse response = WS.url(HOST + "/source/app/auth").setParameter("master", MASTER).post();
        if (response.success()) {
            try {
                AppResult result = mapper.readValue(response.getString(), AppResult.class);
                SECRET = result.data.secret;
                return result;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
    
    public static OrganizesResult organizeIncrease(long ssoUpdate) {
        HttpResponse response = WS.url(HOST + "/data/organize/increase").setParameter("secret", SECRET).setParameter("updateTime", ssoUpdate).post();
        if (response.success()) {
            try {
                OrganizesResult result = mapper.readValue(response.getString(), OrganizesResult.class);
                return result;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
    
    public static PersonsResult personIncrease(long ssoUpdate) {
        HttpResponse response = WS.url(HOST + "/data/person/increase").setParameter("secret", SECRET)
                .setParameter("updateTime", ssoUpdate).post();
        if (response.success()) {
            try {
                PersonsResult result = mapper.readValue(response.getString(), PersonsResult.class);
                return result;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
    
    public static RelationsResult relationIncrease(long ssoUpdate) {
        HttpResponse response = WS.url(HOST + "/data/relation/increase").setParameter("secret", SECRET)
                .setParameter("updateTime", ssoUpdate).post();
        if (response.success()) {
            try {
                RelationsResult result = mapper.readValue(response.getString(), RelationsResult.class);
                return result;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
    
    public static PersonResult login(String username, Integer type, String password) {
        HttpResponse response = WS.url(HOST + "/user/login").setParameter("secret", SECRET)
                .setParameter("username", username).setParameter("type", type).setParameter("password", password).post();
        if (response.success()) {
            try {
                return mapper.readValue(response.getString(), PersonResult.class);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
    
    public static PersonResult info(String ssoId) {
        HttpResponse response = WS.url(HOST + "/user/info").setParameter("secret", SECRET)
                .setParameter("personId", ssoId).post();
        if (response.success()) {
            try {
                return mapper.readValue(response.getString(), PersonResult.class);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
    
    public static PersonResult verify(String accesstoken) {
        HttpResponse response = WS.url(HOST + "/user/verify").setParameter("secret", SECRET)
                .setParameter("accesstoken", accesstoken).post();
        if (response.success()) {
            try {
                return mapper.readValue(response.getString(), PersonResult.class);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
    
    
}
