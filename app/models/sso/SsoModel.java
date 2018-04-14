package models.sso;

public interface SsoModel {
    
    void preUpdate(Long ssoId, Long ssoUpdate);
    
    void preUpdate(Long ssoUpdate);
    
}
