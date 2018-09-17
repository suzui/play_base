package vos;

import annotations.DataField;
import enums.ButtonType;
import enums.ValidationType;
import org.apache.commons.lang.StringUtils;

public class Validation {
    
    @DataField(name = "类型", enums = ValidationType.class)
    public Integer type;
    @DataField(name = "标题")
    public String title;
    @DataField(name = "内容")
    public String content;
    @DataField(name = "取消按钮文案")
    public String cancelText;
    @DataField(name = "取消按钮类型", enums = ButtonType.class)
    public Integer cancelType;
    @DataField(name = "确认按钮文案")
    public String submitText;
    @DataField(name = "确认按钮类型", enums = ButtonType.class)
    public Integer submitType;
    
    public Validation() {
    
    }
    
    public Validation(ValidationType type, String title, String content, ButtonType cancelType, String cancelText, String submitText, ButtonType submitType) {
        this.type = type.code();
        this.title = title;
        this.content = content;
        this.cancelText = cancelText;
        this.cancelType = cancelType.code();
        this.submitText = submitText;
        this.submitType = submitType.code();
    }
    
    
    public Validation(String message) {
        if (StringUtils.isNotBlank(message)) {
            String[] messages = StringUtils.split(message, "|");
            if (messages.length == 1) {
                type = ValidationType.TOAST.code();
                content = message;
            } else if (messages.length == 2) {
                type = ValidationType.DIALOG.code();
                content = messages[0];
                submitText = messages[1];
                submitType = ButtonType.CLOSE.code();
            } else if (messages.length == 3) {
                type = ValidationType.DIALOG.code();
                title = messages[0];
                content = messages[1];
                submitText = messages[2];
                submitType = ButtonType.CLOSE.code();
            } else if (messages.length == 4) {
                type = ValidationType.DIALOG.code();
                title = messages[0];
                content = messages[1];
                cancelText = messages[2];
                cancelType = ButtonType.CLOSE.code();
                submitText = messages[3];
                submitType = ButtonType.REPOST.code();
            }
        }
    }
    
}