package vos.back;

import annotations.DataField;
import models.back.Api;
import org.apache.commons.lang.StringUtils;
import vos.OneData;

import java.io.Serializable;
import java.util.Arrays;

public class ApiVO extends OneData implements Serializable {
    
    @DataField(name = "apiId")
    public Long apiId;
    @DataField(name = "url")
    public String url;
    @DataField(name = "action")
    public String action;
    @DataField(name = "method")
    public String method;
    @DataField(name = "body")
    public String body;
    @DataField(name = "header")
    public String header;
    @DataField(name = "请求参数")
    public String param;
    @DataField(name = "返回状态")
    public String status;
    @DataField(name = "异常报告")
    public String exception;
    @DataField(name = "返回结果")
    public String result;
    @DataField(name = "开始时间")
    public Long startTime;
    @DataField(name = "结束时间")
    public Long endTime;
    
    @DataField(name = "用户id")
    public Long personId;
    @DataField(name = "用户token")
    public String personToken;
    @DataField(name = "用户信息")
    public String personInfo;
    
    
    public ApiVO() {
        this.condition = " order by id desc ";
    }
    
    public ApiVO(Api api) {
        this.apiId = api.id;
        this.url = api.url;
        this.action = api.action;
        this.method = api.method;
        this.body = api.body;
        this.header = api.header;
        this.param = api.param;
        this.status = api.status;
        this.exception = api.exception;
        this.result = api.result;
        this.startTime = api.startTime;
        this.endTime = api.endTime;
        this.personId = api.personId;
        this.personToken = api.personToken;
        this.personInfo = api.personInfo;
    }
    
}
