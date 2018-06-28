package models.sso;

import listeners.SSOModelListener;
import models.token.BasePerson;
import results.sso.PersonResult;
import utils.SSOUtils;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Transient;

@Entity
@EntityListeners(SSOModelListener.class)
public abstract class SsoPerson extends BasePerson implements SsoModel {
    
    public Long ssoId;
    public Long ssoUpdate;
    public String ssoPro;
    
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
    
    @Override
    public boolean isPasswordRight(String password) {
        if (SSOUtils.isOn()) {
            PersonResult personResult = SSOUtils.verify(this.ssoId, password);
            return personResult != null && personResult.succ();
        } else {
            return true;
        }
    }
    
    @Override
    public void editPassword(String password) {
        if (SSOUtils.isOn()) {
            SSOUtils.password(this.ssoId, password);
        }
    }
    
    public static <T extends SsoPerson> T findBySsoId(Long ssoId) {
        return SsoPerson.find("ssoId=?", ssoId).first();
    }
    
}
