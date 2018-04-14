package models.sso;

import enums.PersonType;
import listeners.SSOModelListener;
import models.token.BasePerson;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Entity
@EntityListeners(SSOModelListener.class)
public abstract class SsoPerson extends BasePerson implements SsoModel {
    
    public Long ssoId;
    public Long ssoUpdate;
    
    @Enumerated(EnumType.STRING)
    public PersonType type;//person中无需重复声明 enum需定义personType
    
    public void preUpdate(Long ssoId) {
        this.ssoId = ssoId;
    }
    
    public static <T extends SsoPerson> T findBySsoId(Long ssoId) {
        return SsoPerson.find(defaultSql("ssoId=?"), ssoId).first();
    }
    
}
