package models.sso;

import enums.PersonType;
import listeners.SSOModelListener;
import models.token.BasePerson;

import javax.persistence.EntityListeners;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
@EntityListeners(SSOModelListener.class)
public class SsoPerson extends BasePerson {
    
    public Long ssoId;
    public Long ssoUpdate;
    
    @Enumerated(EnumType.STRING)
    public PersonType type;//person中无需重复声明 enum需定义personType
    
    public void preUpdate(Long ssoId, Long ssoUpdate) {
        this.ssoId = ssoId;
        this.ssoUpdate = ssoUpdate;
    }
    
    public void preUpdate(Long ssoUpdate) {
        this.ssoUpdate = ssoUpdate;
    }
    
    public static <T extends SsoPerson> T findBySsoId(Long ssoId) {
        return SsoPerson.find(defaultSql("ssoId=?"), ssoId).first();
    }
    
}
