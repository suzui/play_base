package models;

import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class SSOModel extends BaseModel {
    
    public Long ssoId;
    public Long ssoUpdate;
    
}
