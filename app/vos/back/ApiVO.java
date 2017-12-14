package vos.back;

import annotations.DataField;
import models.back.Api;
import vos.OneData;

import java.io.Serializable;

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
    
    @DataField(name = "用户id")
    public Long personId;
    @DataField(name = "用户姓名")
    public String personName;
    @DataField(name = "用户账号")
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
