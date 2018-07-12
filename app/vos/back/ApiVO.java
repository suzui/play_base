package vos.back;

import annotations.DataField;
import models.back.Api;
import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.StringUtils;
import utils.JSONUtils;
import vos.OneData;

import java.io.Serializable;

public class ApiVO extends OneData implements Serializable {
    
    @DataField(name = "apiId")
    public String apiId;
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
    
    @DataField(name = "是否异常")
    public Integer error;
    @DataField(name = "是否模拟")
    public Integer mock;
    
    @DataField(name = "运行环境")
    public String env;
    
    public ApiVO() {
    }
    
    public ApiVO(Api api) {
        super(0l);
        this.apiId = api.get_id().toString();
        this.url = api.url;
        this.action = api.action;
        this.method = api.method;
        this.body = api.body;
        this.param = api.param;
        this.status = api.status;
        this.startTime = api.startTime;
        this.endTime = api.endTime;
        this.personId = api.personId;
        this.personToken = api.personToken;
        this.personInfo = api.personInfo;
        this.error = BooleanUtils.toIntegerObject(api.exception != null);
        this.mock = BooleanUtils.toIntegerObject(StringUtils.isNotBlank(api.mock));
        this.env = api.env;
        
    }
    
    public ApiVO complete(Api api) {
        this.header = api.header;
        this.exception = api.exception;
        this.result = JSONUtils.format(api.result);
        return this;
    }
    
}
