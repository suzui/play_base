package models.back;

import models.BaseModel;
import org.apache.commons.lang.StringUtils;
import vos.back.ApiVO;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Entity
public class Api extends BaseModel {
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
    
    public void del() {
        this.logicDelete();
    }
    
    public static Api findByID(Long id) {
        return Api.find(defaultSql("id=?"), id).first();
    }
    
    public static List<Api> fetchByIds(List<Long> ids) {
        if (ids == null || ids.isEmpty()) {
            return Collections.EMPTY_LIST;
        }
        return Api.find(defaultSql("id in (:ids)")).bind("ids", ids.toArray()).fetch();
    }
    
    public static List<Api> fetchAll() {
        return Api.find(defaultSql()).fetch();
    }
    
    public static List<Api> fetch(ApiVO apiVO) {
        Object[] data = data(apiVO);
        List<String> hqls = (List<String>) data[0];
        List<Object> params = (List<Object>) data[1];
        return Api.find(defaultSql(StringUtils.join(hqls, " and ")) + apiVO.condition, params.toArray())
                .fetch(apiVO.page, apiVO.size);
    }
    
    public static int count(ApiVO apiVO) {
        Object[] data = data(apiVO);
        List<String> hqls = (List<String>) data[0];
        List<Object> params = (List<Object>) data[1];
        return (int) Api.count(defaultSql(StringUtils.join(hqls, " and ")), params.toArray());
    }
    
    public static Object[] data(ApiVO apiVO) {
        List<String> hqls = new ArrayList<>();
        List<Object> params = new ArrayList<>();
        if (StringUtils.isNotBlank(apiVO.search)) {
            hqls.add("concat_ws(',',url,action,personId,personInfo) like ?");
            params.add("%" + apiVO.search + "%");
        }
        return new Object[]{hqls, params};
    }
    
}
