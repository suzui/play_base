package utils;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import play.Play;
import play.libs.WS;
import play.libs.WS.HttpResponse;
import results.sso.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class SSOUtils {
    
    public static final String HOST = Play.configuration.getProperty("sso.host");
    public static final String MASTER = Play.configuration.getProperty("sso.master");
    public static final ObjectMapper mapper = new ObjectMapper();
    
    static {
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }
    
    public static String SECRET;
    
    public static AppResult auth() {
        try {
            HttpResponse response = WS.url(HOST + "/source/app/auth").setParameter("master", MASTER).post();
            if (response.success()) {
                
                AppResult result = mapper.readValue(response.getString(), AppResult.class);
                SECRET = result.data.secret;
                return result;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public static OrganizesResult organizeIncrease(long ssoUpdate) {
        try {
            HttpResponse response = WS.url(HOST + "/data/organize/increase").setParameter("secret", SECRET).setParameter("updateTime", ssoUpdate).post();
            if (response.success()) {
                OrganizesResult result = mapper.readValue(response.getString(), OrganizesResult.class);
                return result;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public static OrganizeResult organizeAdd(OrganizeResult organizeResult) {
        try {
            Map<String, String> map = mapper.readValue(mapper.writeValueAsString(organizeResult.data), HashMap.class);
            HttpResponse response = WS.url(HOST + "/data/organize/add").setParameter("secret", SECRET).setParameters(map).post();
            if (response.success()) {
                OrganizeResult result = mapper.readValue(response.getString(), OrganizeResult.class);
                return result;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public static OrganizeResult organizeEdit(OrganizeResult organizeResult) {
        try {
            Map<String, String> map = mapper.readValue(mapper.writeValueAsString(organizeResult.data), HashMap.class);
            HttpResponse response = WS.url(HOST + "/data/organize/edit").setParameter("secret", SECRET).setParameters(map).post();
            if (response.success()) {
                OrganizeResult result = mapper.readValue(response.getString(), OrganizeResult.class);
                return result;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public static OrganizeResult organizeDelete(long ssoId) {
        try {
            HttpResponse response = WS.url(HOST + "/data/organize/delete").setParameter("secret", SECRET).setParameter("organizeId", ssoId).post();
            if (response.success()) {
                OrganizeResult result = mapper.readValue(response.getString(), OrganizeResult.class);
                return result;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    
    public static PersonsResult personIncrease(long ssoUpdate) {
        try {
            HttpResponse response = WS.url(HOST + "/data/person/increase").setParameter("secret", SECRET)
                    .setParameter("updateTime", ssoUpdate).post();
            if (response.success()) {
                PersonsResult result = mapper.readValue(response.getString(), PersonsResult.class);
                return result;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public static PersonResult personAdd(PersonResult personResult) {
        try {
            Map<String, String> map = mapper.readValue(mapper.writeValueAsString(personResult.data), HashMap.class);
            HttpResponse response = WS.url(HOST + "/data/person/add").setParameter("secret", SECRET).setParameters(map).post();
            if (response.success()) {
                PersonResult result = mapper.readValue(response.getString(), PersonResult.class);
                return result;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public static PersonResult personEdit(PersonResult personResult) {
        try {
            Map<String, String> map = mapper.readValue(mapper.writeValueAsString(personResult.data), HashMap.class);
            HttpResponse response = WS.url(HOST + "/data/person/edit").setParameter("secret", SECRET).setParameters(map).post();
            if (response.success()) {
                PersonResult result = mapper.readValue(response.getString(), PersonResult.class);
                return result;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public static PersonResult personDelete(long ssoId) {
        try {
            HttpResponse response = WS.url(HOST + "/data/person/delete").setParameter("secret", SECRET).setParameter("organizeId", ssoId).post();
            if (response.success()) {
                PersonResult result = mapper.readValue(response.getString(), PersonResult.class);
                return result;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public static RelationsResult relationIncrease(long ssoUpdate) {
        try {
            HttpResponse response = WS.url(HOST + "/data/relation/increase").setParameter("secret", SECRET)
                    .setParameter("updateTime", ssoUpdate).post();
            if (response.success()) {
                RelationsResult result = mapper.readValue(response.getString(), RelationsResult.class);
                return result;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public static RelationResult relationAdd(RelationResult relationResult) {
        try {
            Map<String, String> map = mapper.readValue(mapper.writeValueAsString(relationResult.data), HashMap.class);
            HttpResponse response = WS.url(HOST + "/data/relation/add").setParameter("secret", SECRET).setParameters(map).post();
            if (response.success()) {
                RelationResult result = mapper.readValue(response.getString(), RelationResult.class);
                return result;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public static RelationResult relationEdit(RelationResult relationResult) {
        try {
            Map<String, String> map = mapper.readValue(mapper.writeValueAsString(relationResult.data), HashMap.class);
            HttpResponse response = WS.url(HOST + "/data/relation/edit").setParameter("secret", SECRET).setParameters(map).post();
            if (response.success()) {
                RelationResult result = mapper.readValue(response.getString(), RelationResult.class);
                return result;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public static RelationResult relationDelete(long ssoId) {
        try {
            HttpResponse response = WS.url(HOST + "/data/relation/delete").setParameter("secret", SECRET).setParameter("relationId", ssoId).post();
            if (response.success()) {
                RelationResult result = mapper.readValue(response.getString(), RelationResult.class);
                return result;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public static PersonResult login(String username, Integer type, String password) {
        try {
            HttpResponse response = WS.url(HOST + "/user/login").setParameter("secret", SECRET)
                    .setParameter("username", username).setParameter("type", type).setParameter("password", password).post();
            if (response.success()) {
                return mapper.readValue(response.getString(), PersonResult.class);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public static PersonResult info(String ssoId) {
        try {
            HttpResponse response = WS.url(HOST + "/user/info").setParameter("secret", SECRET)
                    .setParameter("personId", ssoId).post();
            if (response.success()) {
                return mapper.readValue(response.getString(), PersonResult.class);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public static PersonResult verify(String accesstoken) {
        try {
            HttpResponse response = WS.url(HOST + "/user/verify").setParameter("secret", SECRET)
                    .setParameter("accesstoken", accesstoken).post();
            if (response.success()) {
                return mapper.readValue(response.getString(), PersonResult.class);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    
}
