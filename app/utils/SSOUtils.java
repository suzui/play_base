package utils;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import models.sso.SsoOrganize;
import models.sso.SsoPerson;
import models.sso.SsoRelation;
import play.Play;
import play.libs.WS;
import play.libs.WS.HttpResponse;
import results.sso.*;

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
        HttpResponse response = WS.url(HOST + "/source/app/auth").setParameter("master", MASTER).post();
        if (response.success()) {
            AppResult result = new Gson().fromJson(response.getString(), AppResult.class);
            SECRET = result.data.secret;
            return result;
        }
        
        return null;
    }
    
    
    public static OrganizesResult organizeIncrease(long ssoUpdate) {
        if (SECRET == null) auth();
        HttpResponse response = WS.url(HOST + "/data/organize/increase").setParameter("secret", SECRET).setParameter("updateTime", ssoUpdate).post();
        if (response.success()) {
            OrganizesResult result = new Gson().fromJson(response.getString(), OrganizesResult.class);
            return result;
            
        }
        return null;
    }
    
    public static PersonsResult personIncrease(long ssoUpdate) {
        if (SECRET == null) auth();
        HttpResponse response = WS.url(HOST + "/data/person/increase").setParameter("secret", SECRET)
                .setParameter("updateTime", ssoUpdate).post();
        if (response.success()) {
            PersonsResult result = new Gson().fromJson(response.getString(), PersonsResult.class);
            return result;
        }
        return null;
    }
    
    public static RelationsResult relationIncrease(long ssoUpdate) {
        if (SECRET == null) auth();
        HttpResponse response = WS.url(HOST + "/data/relation/increase").setParameter("secret", SECRET)
                .setParameter("updateTime", ssoUpdate).post();
        if (response.success()) {
            RelationsResult result = new Gson().fromJson(response.getString(), RelationsResult.class);
            return result;
        }
        return null;
    }
    
    public static PersonResult login(String username, Integer type, String password) {
        if (SECRET == null) auth();
        HttpResponse response = WS.url(HOST + "/user/login").setParameter("secret", SECRET)
                .setParameter("username", username).setParameter("type", type).setParameter("password", password).post();
        if (response.success()) {
            return new Gson().fromJson(response.getString(), PersonResult.class);
        }
        return null;
    }
    
    public static PersonResult info(Long ssoId) {
        return info(ssoId + "");
    }
    
    public static PersonResult info(String ssoId) {
        if (SECRET == null) auth();
        HttpResponse response = WS.url(HOST + "/user/info").setParameter("secret", SECRET)
                .setParameter("personId", ssoId).post();
        if (response.success()) {
            return new Gson().fromJson(response.getString(), PersonResult.class);
        }
        return null;
    }
    
    public static PersonResult verify(String accesstoken) {
        if (SECRET == null) auth();
        HttpResponse response = WS.url(HOST + "/user/verify").setParameter("secret", SECRET)
                .setParameter("accesstoken", accesstoken).post();
        if (response.success()) {
            return new Gson().fromJson(response.getString(), PersonResult.class);
        }
        return null;
    }
    
    public static OrganizeResult organizeAdd(SsoOrganize organize) {
        if (SECRET == null) auth();
        OrganizeResult.OrganizeData organizeData = new OrganizeResult.OrganizeData(organize);
        Map<String, String> map = new Gson().fromJson(new Gson().toJson(organizeData), HashMap.class);
        HttpResponse response = WS.url(HOST + "/data/organize/add").setParameter("secret", SECRET).setParameters(map).post();
        if (response.success()) {
            OrganizeResult result = new Gson().fromJson(response.getString(), OrganizeResult.class);
            return result;
        }
        return null;
    }
    
    public static OrganizeResult organizeEdit(SsoOrganize organize) {
        if (SECRET == null) auth();
        OrganizeResult.OrganizeData organizeData = new OrganizeResult.OrganizeData(organize);
        Map<String, String> map = new Gson().fromJson(new Gson().toJson(organizeData), HashMap.class);
        HttpResponse response = WS.url(HOST + "/data/organize/edit").setParameter("secret", SECRET).setParameters(map).post();
        if (response.success()) {
            OrganizeResult result = new Gson().fromJson(response.getString(), OrganizeResult.class);
            return result;
        }
        return null;
    }
    
    public static OrganizeResult organizeDelete(SsoOrganize organize) {
        if (SECRET == null) auth();
        OrganizeResult.OrganizeData organizeData = new OrganizeResult.OrganizeData(organize);
        HttpResponse response = WS.url(HOST + "/data/organize/delete").setParameter("secret", SECRET).setParameter("organizeId", organizeData.organizeId).post();
        if (response.success()) {
            OrganizeResult result = new Gson().fromJson(response.getString(), OrganizeResult.class);
            return result;
        }
        return null;
    }
    
    
    public static PersonResult personAdd(SsoPerson person) {
        if (SECRET == null) auth();
        PersonResult.PersonData personData = new PersonResult.PersonData(person);
        Map<String, String> map = new Gson().fromJson(new Gson().toJson(personData), HashMap.class);
        HttpResponse response = WS.url(HOST + "/data/person/add").setParameter("secret", SECRET).setParameters(map).post();
        if (response.success()) {
            PersonResult result = new Gson().fromJson(response.getString(), PersonResult.class);
            return result;
        }
        return null;
    }
    
    public static PersonResult personEdit(SsoPerson person) {
        if (SECRET == null) auth();
        PersonResult.PersonData personData = new PersonResult.PersonData(person);
        Map<String, String> map = new Gson().fromJson(new Gson().toJson(personData), HashMap.class);
        HttpResponse response = WS.url(HOST + "/data/person/edit").setParameter("secret", SECRET).setParameters(map).post();
        if (response.success()) {
            PersonResult result = new Gson().fromJson(response.getString(), PersonResult.class);
            return result;
        }
        return null;
    }
    
    public static PersonResult personDelete(SsoPerson person) {
        if (SECRET == null) auth();
        PersonResult.PersonData personData = new PersonResult.PersonData(person);
        HttpResponse response = WS.url(HOST + "/data/person/delete").setParameter("secret", SECRET).setParameter("personId", personData.personId).post();
        if (response.success()) {
            PersonResult result = new Gson().fromJson(response.getString(), PersonResult.class);
            return result;
        }
        return null;
    }
    
    public static RelationResult relationAdd(SsoRelation relation) {
        if (SECRET == null) auth();
        RelationResult.RelationData relationData = new RelationResult.RelationData(relation);
        Map<String, String> map = new Gson().fromJson(new Gson().toJson(relationData), HashMap.class);
        HttpResponse response = WS.url(HOST + "/data/relation/add").setParameter("secret", SECRET).setParameters(map).post();
        if (response.success()) {
            RelationResult result = new Gson().fromJson(response.getString(), RelationResult.class);
            return result;
        }
        return null;
    }
    
    public static RelationResult relationEdit(SsoRelation relation) {
        if (SECRET == null) auth();
        RelationResult.RelationData relationData = new RelationResult.RelationData(relation);
        Map<String, String> map = new Gson().fromJson(new Gson().toJson(relationData), HashMap.class);
        HttpResponse response = WS.url(HOST + "/data/relation/edit").setParameter("secret", SECRET).setParameters(map).post();
        if (response.success()) {
            RelationResult result = new Gson().fromJson(response.getString(), RelationResult.class);
            return result;
        }
        return null;
    }
    
    public static RelationResult relationDelete(SsoRelation relation) {
        if (SECRET == null) auth();
        RelationResult.RelationData relationData = new RelationResult.RelationData(relation);
        HttpResponse response = WS.url(HOST + "/data/relation/delete").setParameter("secret", SECRET).setParameter("relationId", relationData.relationId).post();
        if (response.success()) {
            RelationResult result = new Gson().fromJson(response.getString(), RelationResult.class);
            return result;
        }
        return null;
    }
    
    
}
