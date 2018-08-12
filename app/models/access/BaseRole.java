package models.access;

import models.BaseModel;
import models.token.BaseOrganize;
import play.jobs.Job;
import utils.BaseUtils;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.util.List;

@Entity
@Table(name = "Role")
public class BaseRole extends BaseModel {
    
    @Column(columnDefinition = STRING + "'角色名称'")
    public String name;
    
    @Column(columnDefinition = STRING_5000 + "'权限ids'")
    public String accessIds;
    
    @ManyToOne
    public BaseOrganize organize;//所属机构
    
    public <T extends BaseOrganize> T organize() {
        return this.organize == null ? null : (T) this.organize;
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
                BaseAuthorization.fetchByRole(role).forEach(a -> {
                    if (a.crowd == null) {
                        a.del();
                    } else {
                        a.role = null;
                        a.save();
                    }
                });
            }
        }.now();
        this.logicDelete();
    }
    
    public static <T extends BaseRole> T findByID(Long id) {
        return T.find(defaultSql("id =?"), id).first();
    }
    
    public static <T extends BaseRole> List<T> fetchByOrganize(BaseOrganize organize) {
        return T.find(defaultSql("organize = ?"), organize).fetch();
    }
    
    public static <T extends BaseRole> List<T> fetchAll() {
        return T.find(defaultSql("organize is null")).fetch();
    }
    
    
}
