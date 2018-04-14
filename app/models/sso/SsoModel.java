package models.sso;

import listeners.SSOModelListener;

import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
@EntityListeners(SSOModelListener.class)
public interface SsoModel {
    
    public void preUpdate(Long ssoId, Long ssoUpdate);
    
    public void preUpdate(Long ssoUpdate);
    
    
}
