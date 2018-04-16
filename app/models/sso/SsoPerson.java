package models.sso;

import enums.PersonType;
import listeners.SSOModelListener;
import models.token.BasePerson;

import javax.persistence.*;

@Entity
@EntityListeners(SSOModelListener.class)
public abstract class SsoPerson extends BasePerson implements SsoModel {
    
    public Long ssoId;
    public Long ssoUpdate;
    
    @Enumerated(EnumType.STRING)
    public PersonType type;//person中无需重复声明 enum需定义personType
    
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
    
    public static <T extends SsoPerson> T findBySsoId(Long ssoId) {
        return SsoPerson.find("ssoId=?", ssoId).first();
    }
    
}
