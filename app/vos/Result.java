package vos;

import com.fasterxml.jackson.databind.ObjectMapper;
import play.Logger;

import java.io.IOException;

/**
 * 网络请求结果
 *
 * @author Su
 */
public class Result {
    
    public String status;
    
    public int code;
    
    public String message;
    
    public Data data;
    
    public Validation validation;
    
    public long systemTime;
    
    public static class Status {
        public final static String SUCC = "succ";
        public final static Integer SUCCCODE = 20000;
        public final static String SUCCTEXT = "-请求成功%s";
        public final static String FAIL = "fail";
        public final static Integer FAILCODE = 50000;
        public final static String FAILTEXT = "系统异常";
    }
    
    public static class StatusCode {
        public static final Object[] SUCC = {Status.SUCCCODE, Status.SUCCTEXT};
        public static final Object[] FAIL = {Status.FAILCODE, Status.FAILTEXT};
        public static final Object[] BACK_UPDATE_FORBIDDEN = {30001, "无更新权限"};
        public static final Object[] BACK_UPDATE_FAILED = {30002, "更新失败"};
        public static final Object[] BACK_RESTART_FAILED = {30003, "重启失败"};
        public static final Object[] BACK_START_FAILED = {30004, "有项目正在启动"};
        public static final Object[] SYSTEM_APP_UPDATE = {40000, "-检测到有版本更新"};
        public static final Object[] SYSTEM_TOKEN_UNVALID = {40001, "-accesstoken失效"};
        public static final Object[] SYSTEM_REQUEST_REPEAT = {40002, "重复请求"};
        public static final Object[] SYSTEM_ACCESS_FOBIDDEN = {40003, "无相应权限"};
        public static final Object[] SYSTEM_PARAM_ERROR = {40004, "参数不合法"};
        public static final Object[] PERSON_USERNAME_UNVALID = {40101, "用户名格式错误"};
        public static final Object[] PERSON_PHONE_UNVALID = {40102, "手机号码格式错误"};
        public static final Object[] PERSON_EMAIL_UNVALID = {40103, "邮箱格式错误"};
        public static final Object[] PERSON_PASSWORD_UNVALID = {40104, "密码格式错误"};
        public static final Object[] PERSON_USERNAME_EXIST = {40105, "该用户名已被使用"};
        public static final Object[] PERSON_PHONE_EXIST = {40106, "该手机号码已被使用"};
        public static final Object[] PERSON_EMAIL_EXIST = {40107, "该邮箱已被使用"};
        public static final Object[] PERSON_PASSWORD_ERROR = {40108, "密码错误"};
        public static final Object[] PERSON_CAPTCHA_ERROR = {40109, "验证码错误"};
        public static final Object[] PERSON_ACCOUNT_NOTEXIST = {40110, "用户不存在"};
        public static final Object[] PERSON_ACCOUNT_BINDED = {40111, "账号已被绑定"};
        
        //statuscode转换三种validation说明
        //"-请求成功" type:100 content:请求成功
        //"系统异常"  type:102 content:请求成功
        //"标题|内容|取消|确定:102" type:101 title:标题 content:内容 cancelText:取消 cancelType:101 submitText:确定 submitType:102
        
        public static Object[] format(Object[] sc, String... os) {
            if (sc.length != 2) {
                return sc;
            }
            sc[1] = String.format((String) sc[1], os);
            return sc;
        }
        
    }
    
    public Result() {
        systemTime = System.currentTimeMillis();
    }
    
    public static final ObjectMapper mapper = new ObjectMapper();
    
    static {
        // mapper.setSerializationInclusion(Include.NON_NULL);
    }
    
    public static String failed() {
        return result(Status.FAIL, Status.FAILCODE, Status.FAILTEXT, null);
    }
    
    public static String failed(Object[] codemessage) {
        return result(Status.FAIL, (int) codemessage[0], (String) codemessage[1], null);
    }
    
    public static String failed(Object[] codemessage, String message) {
        return result(Status.FAIL, (int) codemessage[0], message, null);
    }
    
    public static String succeed() {
        return result(Status.SUCC, Status.SUCCCODE, Status.SUCCTEXT, null);
    }
    
    public static String succeed(String message) {
        return result(Status.SUCC, Status.SUCCCODE, message, null);
    }
    
    public static String succeed(Object[] codemessage) {
        return result(Status.SUCC, (int) codemessage[0], (String) codemessage[1], null);
    }
    
    public static String succeed(Data data) {
        return result(Status.SUCC, Status.SUCCCODE, Status.SUCCTEXT, data);
    }
    
    public static String succeed(Data data, String message) {
        return result(Status.SUCC, Status.SUCCCODE, message, data);
    }
    
    public static String succeed(Data data, Object[] codemessage) {
        return result(Status.SUCC, (int) codemessage[0], (String) codemessage[1], data);
    }
    
    private static String result(String status, int code, String message, Data data) {
        Result result = new Result();
        result.status = status;
        result.code = code;
        result.validation = new Validation(message);
        result.message = result.validation.content;
        result.data = data;
        return convert(result);
    }
    
    private static String convert(Result result) {
        try {
            return mapper.writeValueAsString(result);
        } catch (IOException e) {
            Logger.info("[result failed]:%s", e.getMessage());
        }
        return null;
    }
    
}