package models;

import listeners.SSOModelListener;

import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
@EntityListeners(SSOModelListener.class)
public abstract class SSOModel extends BaseModel {
    
    public Long ssoId;
    public Long ssoUpdate;
    
    
    public void preUpdate(Long ssoId, Long ssoUpdate) {
        this.ssoId = ssoId;
        this.ssoUpdate = ssoUpdate;
    }
    
    public void preUpdate(Long ssoUpdate) {
        this.ssoUpdate = ssoUpdate;
    }
    
    public void update(Long ssoId, Long ssoUpdate) {
        this.preUpdate(ssoId, ssoUpdate);
        this.save();
    }
    
    public void update(Long ssoUpdate) {
        this.preUpdate(ssoUpdate);
        this.save();
    }
}
