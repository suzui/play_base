package vos;

import annotations.DataField;
import models.api.Api;

import java.io.Serializable;

public class ApiVO extends OneData implements Serializable {
    
    @DataField(name = "apiId", enable = false)
    public Long apiId;
    @DataField(name = "url", enable = false)
    public String url;
    @DataField(name = "action", enable = false)
    public String action;
    @DataField(name = "method", enable = false)
    public String method;
    @DataField(name = "body", enable = false)
    public String body;
    @DataField(name = "header", enable = false)
    public String header;
    @DataField(name = "请求参数", enable = false)
    public String param;
    @DataField(name = "返回状态", enable = false)
    public String status;
    @DataField(name = "异常报告", enable = false)
    public String exception;
    @DataField(name = "返回结果", enable = false)
    public String result;
    
    @DataField(name = "用户id", enable = false)
    public Long personId;
    @DataField(name = "用户姓名", enable = false)
    public String personName;
    @DataField(name = "用户账号", enable = false)
    public String personUsername;
    
    public ApiVO() {
    
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
        if (api.person != null) {
            this.personId = api.person.id;
            this.personName = api.person.name;
            this.personUsername = api.person.username;
        }
        
    }
    
    
}
