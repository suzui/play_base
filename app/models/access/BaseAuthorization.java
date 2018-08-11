package models.access;

import models.BaseModel;
import models.token.BaseOrganize;
import models.token.BasePerson;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.util.List;

@Entity
@Table(name = "Authorization")
public class BaseAuthorization extends BaseModel {
    
    @ManyToOne
    public BasePerson person;//人员
    
    @ManyToOne
    public BasePermission permission;//权限组
    
    @ManyToOne
    public BaseCrowd crowd;//范围组
    
    @ManyToOne
    public BaseOrganize organize;//所属机构

    public <T extends BasePerson> T person() {
        return this.person == null ? null : (T) this.person;
    }
    
    public <T extends BasePermission> T permission() {
        return this.permission == null ? null : (T) this.permission;
    }
    
    public <T extends BaseCrowd> T crowd() {
        return this.crowd == null ? null : (T) this.crowd;
    }
    
    public <T extends BaseOrganize> T organize() {
        return this.organize == null ? null : (T) this.organize;
    }
    
    public void del() {
        this.logicDelete();
    }
    
    public static <T extends BaseAuthorization> T findByPersonAndPermissionAndCrowd(BasePerson person, BasePermission permission, BaseCrowd crowd) {
        return T.find(defaultSql("person=? and permission=? and crowd=?"), person, permission, crowd).first();
    }
    
    public static <T extends BaseAuthorization> T findByPersonAndPermission(BasePerson person, BasePermission permission) {
        return T.find(defaultSql("person=? and permission=?"), person, permission).first();
    }
    
    public static <T extends BaseAuthorization> List<T> fetchByPersonAndOrganize(BasePerson person, BaseOrganize organize) {
        return T.find(defaultSql("person=? and organize=?"), person, organize).fetch();
    }
    
    public static <T extends BaseAuthorization> List<T> fetchByPerson(BasePerson person) {
        return T.find(defaultSql("person=?"), person).fetch();
    }
    
    public static <T extends BaseAuthorization> List<T> fetchByPermission(BasePermission permission) {
        return T.find(defaultSql("permission=?"), permission).fetch();
    }
    
    public static <T extends BaseAuthorization> List<T> fetchByCrowd(BaseCrowd crowd) {
        return T.find(defaultSql("crowd=?"), crowd).fetch();
    }
    
    public static <T extends BaseAuthorization> List<T> fetchOrganzie(BaseOrganize organize) {
        return T.find(defaultSql("organize=?"), organize).fetch();
    }
    
    
}
