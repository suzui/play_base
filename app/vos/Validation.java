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
        if (StringUtils.isBlank(message)) {
            return;
        }
        if (!message.contains("|")) {
            this.type = ValidationType.TOAST.code();
            this.content = message;
            return;
        }
        String[] messages = StringUtils.split(message, "|");
        if (messages.length != 4) {
            return;
        }
        this.type = ValidationType.DIALOG.code();
        String title = messages[0].trim();
        String content = messages[1].trim();
        String cancel = messages[2].trim();
        String submit = messages[3].trim();
        if (StringUtils.isNotBlank(title)) {
            this.title = title;
        }
        if (StringUtils.isNotBlank(content)) {
            this.content = content;
        }
        if (StringUtils.isNotBlank(cancel)) {
            if (!cancel.contains(":")) {
                this.cancelText = cancel;
                this.cancelType = ButtonType.CLOSE.code();
            } else {
                String[] cancels = StringUtils.split(cancel, ":");
                this.cancelText = cancels[0].trim();
                this.cancelType = Integer.parseInt(cancels[1].trim());
            }
        }
        if (StringUtils.isNotBlank(submit)) {
            if (!submit.contains(":")) {
                this.submitText = submit;
                this.submitType = ButtonType.CLOSE.code();
            } else {
                String[] submits = StringUtils.split(submit, ":");
                this.submitText = submits[0].trim();
                this.submitType = Integer.parseInt(submits[1].trim());
            }
        }
    }
    
}