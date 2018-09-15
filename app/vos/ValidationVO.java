package vos;

import annotations.DataField;
import org.apache.commons.lang.StringUtils;

import java.util.Arrays;

public class ValidationVO extends OneData {
    
    @DataField(name = "类型")
    public Integer type;
    @DataField(name = "标题")
    public String title;
    @DataField(name = "内容")
    public String content;
    @DataField(name = "取消按钮")
    public String cancel;
    @DataField(name = "确认按钮")
    public String submit;
    
    public ValidationVO() {
    
    }
    
    public ValidationVO(String title, String content, String cancel, String submit) {
        this.clean();
        this.title = title;
        this.content = content;
        this.cancel = cancel;
        this.submit = submit;
    }
    
    public ValidationVO(String title, String content,String submit) {
        this(title, content, "取消",submit);
    }
    
    public ValidationVO(String title, String content) {
        this(title, content, "取消", "确认");
    }
    
    public ValidationVO(Object[] codemessage) {
        if (codemessage != null && codemessage.length == 2) {
            int code = (int) codemessage[0];
            String message = (String) codemessage[1];
            String[] messages = StringUtils.split(message, "|");
            if (messages.length > 1) {
                this.title = messages[0];
                this.content = messages[1];
                if (message.length() == 2) {
                    this.cancel =  "取消";
                    this.submit =  "确认";
                } else if (messages.length == 3) {
                    this.cancel = "取消";
                    this.submit=messages[2];
                } else if (messages.length == 4) {
                    this.submit=messages[3];
                    this.submit=messages[4];                }
            }
        }
    }
    
}