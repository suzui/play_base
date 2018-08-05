package models.access;

import models.BaseModel;
import models.token.BaseOrganize;
import models.token.BasePerson;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.util.List;

@Entity
@Table(name = "PersonAccess")
public class BaseAccessPerson extends BaseModel {
    
    @ManyToOne
    public BaseAccess access;
    
    @ManyToOne
    public BasePerson person;
    
    @ManyToOne
    public BaseOrganize organize;
    
    public static BaseAccessPerson add(BaseAccess access, BasePerson person) {
        BaseAccessPerson accessPerson = BaseAccessPerson.findByAccessAndPerson(access, person);
        if (accessPerson != null) return accessPerson;
        accessPerson = new BaseAccessPerson();
        accessPerson.access = access;
        accessPerson.person = person;
        return accessPerson.save();
    }
    
    public static BaseAccessPerson add(BaseAccess access, BasePerson person, BaseOrganize organize) {
        BaseAccessPerson accessPerson = BaseAccessPerson.findByAccessAndPersonAndOrganize(access, person, organize);
        if (accessPerson != null) return accessPerson;
        accessPerson = new BaseAccessPerson();
        accessPerson.access = access;
        accessPerson.person = person;
        accessPerson.organize = organize;
        return accessPerson.save();
    }
    
    public void del() {
        this.logicDelete();
    }
    
    public static <T extends BaseAccessPerson> T findByAccessAndPerson(BaseAccess access, BasePerson person) {
        return BaseAccessPerson.find(defaultSql("access = ? and person = ?"), access, person).first();
    }
    
    public static <T extends BaseAccessPerson> T findByAccessAndPersonAndOrganize(BaseAccess access, BasePerson person, BaseOrganize organize) {
        return BaseAccessPerson.find(defaultSql("access = ? and person = ? and organize=?"), access, person, organize).first();
    }
    
    public static List<BaseAccessPerson> fetchByPerson(BasePerson person) {
        return BaseAccessPerson.find(defaultSql("person = ?"), person).fetch();
    }
    
    public static List<BaseAccessPerson> fetchByPersonAndOrganize(BasePerson person, BaseOrganize organize) {
        return BaseAccessPerson.find(defaultSql("person = ? and organize=?"), person, organize).fetch();
    }
}
