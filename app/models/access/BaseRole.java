package models.access;

import models.BaseModel;
import models.token.BaseOrganize;
import play.jobs.Job;
import utils.BaseUtils;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.util.Collections;
import java.util.List;

@Entity
@Table(name = "Role")
public class BaseRole extends BaseModel {
    
    @Column(columnDefinition = STRING + "'角色名称'")
    public String name;
    
    @Column(columnDefinition = STRING_5000 + "'权限ids'")
    public String accessIds;
    
    @ManyToOne
    public BaseOrganize root;//所属机构
    
    @Deprecated
    @ManyToOne
    public BaseOrganize organize;//所属机构
    
    public <T extends BaseOrganize> T root() {
        return this.root == null ? null : (T) this.root;
    }
    
    public <T extends BaseAccess> List<T> access() {
        return BaseAccess.fetchByIds(BaseUtils.idsToList(this.accessIds));
    }
    
    public void del() {
        BaseRole role = this;
        new Job() {
            @Override
            public void doJob() throws Exception {
                super.doJob();
                BaseAuthorization.fetchByRole(role).forEach(a -> a.del());
            }
        }.now();
        this.logicDelete();
    }
    
    public static <T extends BaseRole> T findByID(Long id) {
        return T.find(defaultSql("id =?"), id).first();
    }
    
    public static <T extends BaseRole> List<T> fetchByIds(List<Long> ids) {
        if (ids == null || ids.isEmpty()) {
            return Collections.EMPTY_LIST;
        }
        return T.find(defaultSql("id in (:ids)")).bind("ids", ids.toArray()).fetch();
    }
    
    public static <T extends BaseRole> List<T> fetchByRoot(BaseOrganize root) {
        return T.find(defaultSql("root = ?"), root).fetch();
    }
    
    public static <T extends BaseRole> List<T> fetchAll() {
        return T.find(defaultSql("organize is null")).fetch();
    }
    
}
