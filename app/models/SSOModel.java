package models;

import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class SSOModel extends BaseModel {
    
    public Long ssoId;
    public Long ssoUpdate;
    
    
    public void update(Long ssoId, Long ssoUpdate) {
        this.ssoId = ssoId;
        this.ssoUpdate = ssoUpdate;
        this.save();
    }
    
    public void update(Long ssoUpdate) {
        this.ssoUpdate = ssoUpdate;
        this.save();
    }
}
