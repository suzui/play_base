package models.access;

import models.BaseModel;
import models.token.BaseOrganize;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.util.List;

@Entity
@Table(name = "Permission")
public class BasePermission extends BaseModel {
    
    public String name;
    
    @ManyToOne
    public BaseOrganize organize;//机构权限组
    
    
    public void del() {
        this.logicDelete();
    }
    
    public static <T extends BasePermission> T findByID(Long id) {
        return BasePermission.find(defaultSql("id =?"), id).first();
    }
    
    public static List<BasePermission> fetchByOrganize(BaseOrganize organize) {
        return BasePermission.find(defaultSql("organize = ?"), organize).fetch();
    }
    
}
