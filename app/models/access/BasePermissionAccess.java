package models.access;

import models.BaseModel;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.util.Collections;
import java.util.List;

@Entity
@Table(name = "PermissionAccess")
public class BasePermissionAccess extends BaseModel {
    
    @ManyToOne
    public BasePermission permission;
    
    @ManyToOne
    public BaseAccess access;
    
    public static BasePermissionAccess add(BasePermission permission, BaseAccess access) {
        BasePermissionAccess permissionAccess = findByPermissionAndAccess(permission, access);
        if (permissionAccess != null) {
            return permissionAccess;
        }
        permissionAccess = new BasePermissionAccess();
        permissionAccess.permission = permission;
        permissionAccess.access = access;
        return permissionAccess.save();
    }
    
    public void del() {
        this.logicDelete();
    }
    
    public static <T extends BasePermissionAccess> T findByPermissionAndAccess(BasePermission permission, BaseAccess access) {
        return BasePermissionAccess.find(defaultSql("permission=? and access=?"), permission, access).first();
    }
    
    public static List<BasePermissionAccess> fetchByIds(List<Long> ids) {
        if (ids == null || ids.isEmpty()) {
            return Collections.EMPTY_LIST;
        }
        return BasePermissionAccess.find(defaultSql("id in (:ids)")).bind("ids", ids.toArray()).fetch();
    }
    
    public static List<BasePermissionAccess> fetchByPermission(BasePermission permission) {
        return BasePermissionAccess.find(defaultSql("permission=?"), permission).fetch();
    }
    
    public static List<BasePermissionAccess> fetchByAccess(BaseAccess access) {
        return BasePermissionAccess.find(defaultSql("access=?"), access).fetch();
    }
    
}
