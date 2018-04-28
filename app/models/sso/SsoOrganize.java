package models.sso;

import enums.OrganizeType;
import listeners.SSOModelListener;
import models.token.BaseOrganize;

import javax.persistence.*;


@Entity
@EntityListeners(SSOModelListener.class)
public abstract class SsoOrganize extends BaseOrganize implements SsoModel {
    
    public Long ssoId;
    public Long ssoUpdate;
    public String ssoPro;
    
    @Enumerated(EnumType.STRING)
    public OrganizeType type;//organize中无需重复声明 enum需定义personType
    
    @Transient
    public Boolean listener = true;
    
    public void preUpdate(Long ssoId) {
        this.ssoId = ssoId;
    }
    
    public void closeListener() {
        this.listener = false;
    }
    
    public boolean onListener() {
        return this.listener;
    }
    
    
    public static <T extends SsoOrganize> T findBySsoId(Long ssoId) {
        return SsoOrganize.find("ssoId=?", ssoId).first();
    }
    
}
