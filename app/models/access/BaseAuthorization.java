package models.access;

import models.BaseModel;
import models.token.BaseOrganize;
import models.token.BasePerson;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.util.List;

@Entity
@Table(name = "Authorization")
public class BaseAuthorization extends BaseModel {
    
    @ManyToOne(fetch = FetchType.LAZY)
    public BasePerson person;//人员
    
    @ManyToOne(fetch = FetchType.LAZY)
    public BaseRole role;//角色
    
    @ManyToOne(fetch = FetchType.LAZY)
    public BaseCrowd crowd;//范围
    
    @ManyToOne(fetch = FetchType.LAZY)
    public BaseOrganize organize;//所属机构
    
    public <T extends BasePerson> T person() {
        return this.person == null ? null : (T) this.person;
    }
    
    public <T extends BaseRole> T role() {
        return this.role == null ? null : (T) this.role;
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
    
    public static <T extends BaseAuthorization> T findByPersonAndRoleAndCrowd(BasePerson person, BaseRole role, BaseCrowd crowd) {
        return T.find(defaultSql("person=? and role=? and crowd=?"), person, role, crowd).first();
    }
    
    public static <T extends BaseAuthorization> T findByPersonAndRole(BasePerson person, BaseRole role) {
        return T.find(defaultSql("person=? and role=?"), person, role).first();
    }
    
    public static <T extends BaseAuthorization> List<T> fetchByPersonAndOrganize(BasePerson person, BaseOrganize organize) {
        return T.find(defaultSql("person=? and organize=?"), person, organize).fetch();
    }
    
    public static <T extends BaseAuthorization> List<T> fetchByPerson(BasePerson person) {
        return T.find(defaultSql("person=?"), person).fetch();
    }
    
    public static <T extends BaseAuthorization> List<T> fetchByRole(BaseRole role) {
        return T.find(defaultSql("role=?"), role).fetch();
    }
    
    public static <T extends BaseAuthorization> List<T> fetchByCrowd(BaseCrowd crowd) {
        return T.find(defaultSql("crowd=?"), crowd).fetch();
    }
    
    public static <T extends BaseAuthorization> List<T> fetchByOrganzie(BaseOrganize organize) {
        return T.find(defaultSql("organize=?"), organize).fetch();
    }
    
    
}
