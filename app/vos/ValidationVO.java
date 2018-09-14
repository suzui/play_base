package vos;

import annotations.DataField;
import org.apache.commons.lang.StringUtils;

import java.util.Arrays;
import java.util.List;

public class ValidationVO extends OneData {
    
    @DataField(name = "标题")
    public String title;
    @DataField(name = "内容")
    public String content;
    @DataField(name = "按钮列表")
    public List<String> buttons;
    
    public ValidationVO() {
    
    }
    
    public ValidationVO(String title, String content, List<String> buttons) {
        this.clean();
        this.title = title;
        this.content = content;
        this.buttons = buttons;
    }
    
    public ValidationVO(String title, String content, String button) {
        this(title, content, Arrays.asList(button));
    }
    
    public ValidationVO(String title, String content) {
        this(title, content, Arrays.asList("取消", "确认"));
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
                    this.buttons = Arrays.asList("确认");
                } else if (messages.length == 3) {
                    this.buttons = Arrays.asList(messages[2]);
                } else if (messages.length == 4) {
                    this.buttons = Arrays.asList(messages[2], messages[3]);
                }
            }
        }
    }
    
}