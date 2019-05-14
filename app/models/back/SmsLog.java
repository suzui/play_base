package models.back;

import enums.LogStatus;
import models.BaseModel;
import javax.persistence.FetchType;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Entity
public class SmsLog extends BaseModel {
    
    public String phone;
    public String content;
    @Enumerated(EnumType.STRING)
    public LogStatus status;
}
