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
    public BaseOrganize organize;
    
    public static BasePermission add(String name, BaseOrganize organize) {
        BasePermission permission = new BasePermission();
        permission.name = name;
        permission.organize = organize;
        return permission.save();
    }
    
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
