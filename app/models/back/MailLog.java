package models.back;

import enums.LogStatus;
import models.BaseModel;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Entity
public class MailLog extends BaseModel {
    
    public String email;
    public String content;
    @Enumerated(EnumType.STRING)
    public LogStatus status;
}
