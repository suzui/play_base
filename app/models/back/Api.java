package models.back;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import org.apache.commons.lang.StringUtils;
import org.bson.types.ObjectId;
import play.Logger;
import play.Play;
import play.modules.mongo.MongoDB;
import play.modules.mongo.MongoEntity;
import play.modules.mongo.MongoMapper;
import play.modules.mongo.MongoModel;
import utils.BaseUtils;
import vos.back.ApiVO;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

@MongoEntity
public class Api extends MongoModel {
    
    public String url;
    public String action;
    public String method;
    public String body;
    public String header;
    public String param;
    public String status;
    public String exception;
    public String result;
    
    public Long startTime;
    public Long endTime;
    
    public Long personId;//请求用户id
    public String personToken;//请求用户token
    public String personInfo;//请求用户信息用户名、名字、手机
    
    public String mock;
    public String env;
    
    public static Api add(ApiVO vo) {
        Api api = new Api();
        api.env = Play.applicationPath.getAbsolutePath();
        api.edit(vo);
        return api;
    }
    
    public void edit(ApiVO vo) {
        this.url = vo.url != null ? vo.url : url;
        this.action = vo.action != null ? vo.action : action;
        this.method = vo.method != null ? vo.method : method;
        this.body = vo.body != null ? vo.body : body;
        this.header = vo.header != null ? vo.header : header;
        this.param = vo.param != null ? vo.param : param;
        this.status = vo.status != null ? vo.status : status;
        this.exception = vo.exception != null ? vo.exception : exception;
        this.result = vo.result != null ? vo.result : result;
        this.startTime = vo.startTime != null ? vo.startTime : startTime;
        this.endTime = vo.endTime != null ? vo.endTime : endTime;
        this.personId = vo.personId != null ? vo.personId : personId;
        this.personToken = vo.personToken != null ? vo.personToken : personToken;
        this.personInfo = vo.personInfo != null ? vo.personInfo : personInfo;
        this.mock = vo.header.contains("mock") ? "mock" : null;
        this.save();
    }
    
    public static Api findByID(String id) {
        DBObject one = MongoDB.db().getCollection("api").findOne(new BasicDBObject("_id", new ObjectId(id)));
        Api api = MongoMapper.convertValue(one.toMap(), Api.class);
        return api;
    }
    
    public static List<Api> fetchAll() {
        return Api.find().fetch();
    }
    
    public static List<Api> fetch(ApiVO vo) {
        Logger.info("[apifetch]:%s", BaseUtils.gson.toJson(vo));
        Object[] objects = data(vo);
        List<String> sqls = (List<String>) objects[0];
        List<Pattern> params = (List<Pattern>) objects[1];
        List<Api> list = Api.find("by" + StringUtils.join(sqls, "And"), params.toArray()).order("by-startTime")
                .fetch(vo.page, vo.size);
        Logger.info("[apifetch]:%s",list.size());
        return list;
    }
    
    public static int count(ApiVO vo) {
        Object[] objects = data(vo);
        List<String> sqls = (List<String>) objects[0];
        List<Pattern> params = (List<Pattern>) objects[1];
        return (int) Api.count("by" + StringUtils.join(sqls, "And"), params.toArray());
    }
    
    public static Object[] data(ApiVO vo) {
        List<String> sqls = new ArrayList<>();
        List<Object> params = new ArrayList<>();
        sqls.add("status");
        params.add(Pattern.compile("^.*$", Pattern.CASE_INSENSITIVE));
        if (StringUtils.isNotBlank(vo.url)) {
            sqls.add("url");
            params.add(Pattern.compile("^.*" + vo.url + ".*$"));
        }
        if (StringUtils.isNotBlank(vo.personToken)) {
            sqls.add("personToken");
            params.add(Pattern.compile("^.*" + vo.personToken + ".*$"));
        }
        if (StringUtils.isNotBlank(vo.personInfo)) {
            sqls.add("personInfo");
            params.add(Pattern.compile("^.*" + vo.personInfo + ".*$"));
        }
        if (vo.startTime != null) {
            sqls.add("startTime");
            params.add(new BasicDBObject("$gte", vo.startTime));
        }
        if (vo.endTime != null) {
            sqls.add("endTime");
            params.add(new BasicDBObject("$lte", vo.endTime));
        }
        if (StringUtils.isNotBlank(vo.env)) {
            sqls.add("env");
            params.add(Pattern.compile("^.*" + vo.env + ".*$"));
        }
        if (vo.error != null && vo.error == 1) {
            sqls.add("exception");
            params.add(new BasicDBObject("$ne", null));
        }
        if (vo.mock != null && vo.mock == 1) {
            sqls.add("mock");
            params.add(new BasicDBObject("$ne", null));
        }
        return new Object[]{sqls, params};
    }
    
}
