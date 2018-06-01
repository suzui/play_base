package models.back;

import com.mongodb.BasicDBObject;
import org.apache.commons.lang.StringUtils;
import play.modules.mongo.MongoEntity;
import play.modules.mongo.MongoModel;
import vos.back.ApiVO;

import javax.persistence.Column;
import javax.persistence.Lob;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

@MongoEntity
public class Api extends MongoModel {
    
    @Column(length = 1000)
    public String url;
    @Column(length = 1000)
    public String action;
    public String method;
    @Lob
    public String body;
    @Lob
    public String header;
    @Lob
    public String param;
    public String status;
    @Lob
    public String exception;
    @Lob
    public String result;
    
    public Long startTime;
    public Long endTime;
    
    public Long personId;//请求用户id
    public String personToken;//请求用户token
    public String personInfo;//请求用户信息用户名、名字、手机
    
    public static Api init() {
        Api api = new Api();
        return api.save();
    }
    
    public static Api add(ApiVO apiVO) {
        Api api = new Api();
        api.edit(apiVO);
        return api;
    }
    
    public void edit(ApiVO apiVO) {
        this.url = apiVO.url != null ? apiVO.url : url;
        this.action = apiVO.action != null ? apiVO.action : action;
        this.method = apiVO.method != null ? apiVO.method : method;
        this.body = apiVO.body != null ? apiVO.body : body;
        this.header = apiVO.header != null ? apiVO.header : header;
        this.param = apiVO.param != null ? apiVO.param : param;
        this.status = apiVO.status != null ? apiVO.status : status;
        this.exception = apiVO.exception != null ? apiVO.exception : exception;
        this.startTime = apiVO.startTime != null ? apiVO.startTime : startTime;
        this.endTime = apiVO.endTime != null ? apiVO.endTime : endTime;
        this.result = apiVO.result != null ? apiVO.result : result;
        this.personId = apiVO.personId != null ? apiVO.personId : personId;
        this.personToken = apiVO.personToken != null ? apiVO.personToken : personToken;
        this.personInfo = apiVO.personInfo != null ? apiVO.personInfo : personInfo;
        this.save();
    }
    
    public static Api findByID(Long id) {
        return Api.find(("id=?"), id).first();
    }
    
    public static List<Api> fetchAll() {
        return Api.find().fetch();
    }
    
    public static List<Api> fetch(ApiVO apiVO) {
        Object[] objects = data(apiVO);
        List<String> sqls = (List<String>) objects[0];
        List<Pattern> params = (List<Pattern>) objects[1];
        return Api.find("by" + StringUtils.join(sqls, "And"), params.toArray()).order("by-startTime")
                .fetch(apiVO.page, apiVO.size);
    }
    
    public static int count(ApiVO apiVO) {
        Object[] objects = data(apiVO);
        List<String> sqls = (List<String>) objects[0];
        List<Pattern> params = (List<Pattern>) objects[1];
        return (int) Api.count("by" + StringUtils.join(sqls, "And"), params.toArray());
    }
    
    public static Object[] data(ApiVO apiVO) {
        List<String> sqls = new ArrayList<>();
        List<Object> params = new ArrayList<>();
        sqls.add("status");
        params.add(Pattern.compile("^.*$", Pattern.CASE_INSENSITIVE));
        if (StringUtils.isNotBlank(apiVO.url)) {
            sqls.add("url");
            params.add(Pattern.compile("^.*" + apiVO.url + ".*$"));
        }
        if (StringUtils.isNotBlank(apiVO.personToken)) {
            sqls.add("personToken");
            params.add(Pattern.compile("^.*" + apiVO.personToken + ".*$"));
        }
        if (StringUtils.isNotBlank(apiVO.personInfo)) {
            sqls.add("personInfo");
            params.add(Pattern.compile("^.*" + apiVO.personInfo + ".*$"));
        }
        if (apiVO.startTime != null) {
            sqls.add("startTime");
            params.add(new BasicDBObject("$gte", apiVO.startTime));
        }
        if (apiVO.endTime != null) {
            sqls.add("endTime");
            params.add(new BasicDBObject("$lte", apiVO.endTime));
        }
        return new Object[]{sqls, params};
    }
    
}
