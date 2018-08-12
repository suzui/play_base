package models.token;

import enums.OrganizeType;
import models.BaseModel;
import models.access.BaseAuthorization;
import models.access.BaseCrowd;
import models.access.BaseRole;
import play.jobs.Job;
import utils.BaseUtils;

import javax.persistence.*;
import java.util.Collections;
import java.util.List;

@Entity
@Table(name = "Organize")
public class BaseOrganize extends BaseModel {
    
    @Column(columnDefinition = STRING + "'组织logo'")
    public String logo;
    @Column(columnDefinition = STRING + "'组织名称'")
    public String name;
    @Column(columnDefinition = DOUBLE + "'组织排序'")
    public Double rank;
    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = DOUBLE + "'组织类型'")
    public OrganizeType type;//项目enum需定义
    @ManyToOne
    public BasePerson person;//组织负责人
    
    @ManyToOne
    public BaseOrganize parent;//父组织，根组织为null
    
    @ManyToOne
    public BaseOrganize organize;//组织机构 机构类型为机构本身
    
    @ManyToOne
    public BaseOrganize root;//总根机构
    
    public <T extends BasePerson> T person() {
        return this.person == null ? null : (T) this.person;
    }
    
    public <T extends BaseOrganize> T organize() {
        return this.organize == null ? null : (T) this.organize;
    }
    
    public <T extends BaseOrganize> T parent() {
        return this.parent == null ? null : (T) this.parent;
    }
    
    public <T extends BaseOrganize> T root() {
        return this.root == null ? null : (T) this.root;
    }
    
    public <T extends BaseOrganize> List<T> children() {
        return T.find(defaultSql("parent=?"), this).fetch();
    }
    
    public Double initRank() {
        Double rank = BaseOrganize.find(defaultSql("select max(rank) from Organize where parent.id=?"), this.id).first();
        return rank == null ? 0 : rank + 1;
    }
    
    public void move(BaseOrganize pre, BaseOrganize next) {
        if (pre == null || next == null) {
            if (pre == null) {
                this.rank = next.rank - 1;
            } else {
                this.rank = pre.rank + 1;
            }
        } else {
            this.rank = (pre.rank + next.rank) / 2;
        }
        this.save();
    }
    
    public boolean isOrganize() {
        return this.organize.id.equals(this.id);
    }
    
    public boolean isRoot() {
        return this.root.id.equals(this.id);
    }
    
    public void del() {
        BaseOrganize organize = this;
        new Job() {
            @Override
            public void doJob() throws Exception {
                super.doJob();
                BaseRelation.fetchByOrganize(organize).forEach(r -> r.del());
                BaseAuthorization.fetchOrganzie(organize).forEach(a -> a.del());
                BaseRole.fetchByOrganize(organize).forEach(p -> p.del());
                BaseCrowd.fetchByOrganize(organize).forEach(c -> c.del());
            }
        }.now();
        this.logicDelete();
    }
    
    public static <T extends BaseOrganize> T findByID(Long id) {
        return T.find(defaultSql("id=?"), id).first();
    }
    
    public static <T extends BaseOrganize> List<T> fetchByIds(List<Long> ids) {
        if (BaseUtils.collectionEmpty(ids)) {
            return Collections.EMPTY_LIST;
        }
        return T.find(defaultSql("id in (:ids)")).bind("ids", ids.toArray()).fetch();
    }
    
    public static <T extends BaseOrganize> List<T> fetchByParent(BaseOrganize organize) {
        return T.find(defaultSql("parent=?"), organize).fetch();
    }
    
    public static <T extends BaseOrganize> List<T> fetchByOrganize(BaseOrganize organize) {
        return T.find(defaultSql("organize=?"), organize).fetch();
    }
    
    public static <T extends BaseOrganize> List<T> fetchByRoot(BaseOrganize organize) {
        return T.find(defaultSql("root=?"), organize).fetch();
    }
    
    public static <T extends BaseOrganize> List<T> fetchByType(OrganizeType type) {
        return T.find(defaultSql("type=?"), type).fetch();
    }
    
    public static <T extends BaseOrganize> List<T> fetchAll() {
        return T.find(defaultSql()).fetch();
    }
    
    
}
