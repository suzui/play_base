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
@Table(name = "Crowd")
@org.hibernate.annotations.Table(appliesTo = "Crowd", comment = "范围")
public class BaseCrowd extends BaseModel {
    
    @Column(columnDefinition = STRING + "'范围名称'")
    public String name;
    
    @Column(columnDefinition = STRING_5000 + "'组织ids'")
    public String organizeIds;
    
    @ManyToOne
    public BaseOrganize organize;//所属机构
    
    public <T extends BaseOrganize> T organize() {
        return this.organize == null ? null : (T) this.organize;
    }
    
    public <T extends BaseOrganize> List<T> organizes() {
        return T.fetchByIds(BaseUtils.idsToList(this.organizeIds));
    }
    
    public void del() {
        BaseCrowd crowd = this;
        new Job() {
            @Override
            public void doJob() throws Exception {
                super.doJob();
                BaseAuthorization.fetchByCrowd(crowd).forEach(a -> {
                    if (a.role == null) {
                        a.del();
                    } else {
                        a.crowd = null;
                        a.save();
                    }
                });
            }
        }.now();
        this.logicDelete();
    }
    
    public static <T extends BaseCrowd> T findByID(Long id) {
        return T.find(defaultSql("id=?"), id).first();
    }
    
    public static <T extends BaseCrowd> List<T> fetchByIds(List<Long> ids) {
        if (ids == null || ids.isEmpty()) {
            return Collections.EMPTY_LIST;
        }
        return T.find(defaultSql("id in (:ids)")).bind("ids", ids.toArray()).fetch();
    }
    
    public static <T extends BaseCrowd> List<T> fetchByOrganize(BaseOrganize organize) {
        return T.find(defaultSql("organize = ?"), organize).fetch();
    }
    
    public static <T extends BaseCrowd> List<T> fetchAll() {
        return T.find(defaultSql("organize is null")).fetch();
    }
    
}
