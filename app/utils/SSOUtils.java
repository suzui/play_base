package utils;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import play.Play;
import play.libs.WS;
import play.libs.WS.HttpResponse;
import results.sso.AppResult;
import results.sso.PersonResult;

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
        HttpResponse response = WS.url(HOST + "/source/app/auth").setParameter("master", MASTER).get();
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
    
    public static PersonResult login(String username, String password) {
        HttpResponse response = WS.url(HOST + "/user/login").setParameter("secret", SECRET).
                setParameter("username", username).setParameter("password", password).get();
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
        HttpResponse response = WS.url(HOST + "/user/verify").setParameter("secret", SECRET).
                setParameter("accesstoken", accesstoken).get();
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
