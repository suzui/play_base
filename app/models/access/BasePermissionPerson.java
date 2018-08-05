package models.access;

import models.BaseModel;
import models.token.BaseOrganize;
import models.token.BasePerson;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.util.List;

@Entity
@Table(name = "PermissionPerson")
public class BasePermissionPerson extends BaseModel {
    
    @ManyToOne
    public BasePermission permission;
    
    @ManyToOne
    public BasePerson person;
    
    public void del() {
        this.logicDelete();
    }
    
    public static <T extends BasePermissionPerson> T findByPermissionAndPerson(BasePermission permission, BasePerson person) {
        return BasePermissionPerson.find(defaultSql("permission=? and person=?"), permission, person).first();
    }
    
    public static List<BasePermissionPerson> fetchByPermission(BasePermission permission) {
        return BasePermissionPerson.find(defaultSql("permission=?"), permission).fetch();
    }
    
    public static List<BasePermissionPerson> fetchByPerson(BasePerson person) {
        return BasePermissionPerson.find(defaultSql("person=?"), person).fetch();
    }
    
    public static List<BasePermissionPerson> fetchByPersonAndOrganize(BasePerson person, BaseOrganize organize) {
        return BasePermissionPerson.find(defaultSql("person=? and permission.organize=?"), person, organize).fetch();
    }
}
