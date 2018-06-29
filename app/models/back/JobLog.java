package models.back;

import models.BaseModel;

import javax.persistence.Entity;

@Entity
public class JobLog extends BaseModel {
    
    public String job;
    public Long startTime;
    
    public Long endTime;
    
}
