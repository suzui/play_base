package models.sso;

public interface SsoModel {
    
    void preUpdate(Long ssoUpdate);

    void closeListener();

    boolean onListener();

    
}
