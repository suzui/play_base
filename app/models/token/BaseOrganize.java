package models.token;

import enums.OrganizeType;
import models.BaseModel;
import models.access.BaseAuthorization;
import models.access.BaseCrowd;
import models.access.BaseRole;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import play.jobs.Job;
import utils.BaseUtils;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Entity
@Table(name = "Organize")
public class BaseOrganize extends BaseModel {
    
    @Column(columnDefinition = STRING + "'组织code'")
    public String code;
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
    @Deprecated
    public BaseOrganize organize;//组织机构 机构类型为机构本身
    
    @ManyToOne
    public BaseOrganize root;//根机构
    
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
    
    public <T extends BaseOrganize> List<T> allChildren() {
        List<T> organizes = new ArrayList<>();
        organizes.addAll(this.children());
        this.children().forEach(c -> organizes.addAll(c.allChildren()));
        return organizes;
    }
    
    public <T extends BasePerson> List<T> persons() {
        List<T> persons = new ArrayList<>();
        BaseRelation.fetchByOrganize(this).forEach(r -> persons.add(r.person()));
        return persons;
    }
    
    public Double initRank() {
        Double rank = BaseOrganize.find(defaultSql("select max(rank) from Organize where parent.id=?"), this.id).first();
        return rank == null ? 0 : rank + 1;
    }
    
    public void move(Long preId, Long nextId) {
        move(preId == null ? null : BaseOrganize.findByID(preId), nextId == null ? null : BaseOrganize.findByID(nextId));
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
    
    @Deprecated
    public boolean isOrganize() {
        return this.organize.id.equals(this.id);
    }
    
    public boolean isRoot() {
        return this.root.id.equals(this.id);
    }
    
    public void person(BasePerson person) {
        if (person == null) {
            BaseRelation.fetchByOrganize(this).forEach(r -> r.setAdmin(false));
        } else {
            BaseRelation relation = BaseRelation.findByOrganizeAndPerson(this, person);
            if (relation != null) {
                relation.setAdmin();
            }
        }
        this.person = person;
        this.save();
    }
    
    public void del() {
        BaseOrganize organize = this;
        new Job() {
            @Override
            public void doJob() throws Exception {
                super.doJob();
                BaseRelation.fetchByOrganize(organize).forEach(r -> r.del());
                BaseAuthorization.fetchByOrganzie(organize).forEach(a -> a.del());
                BaseRole.fetchByOrganize(organize).forEach(p -> p.del());
                BaseCrowd.fetchByOrganize(organize).forEach(c -> c.del());
            }
        }.now();
        this.logicDelete();
    }
    
    public static <T extends BaseOrganize> T findByID(Long id) {
        return T.find(defaultSql("id=?"), id).first();
    }
    
    public static <T extends BaseOrganize> T findByCode(String code) {
        return T.find(defaultSql("code=?"), code).first();
    }
    
    public static <T extends BaseOrganize> T findByNameAndOrganize(String name, BaseOrganize organize) {
        return T.find(defaultSql("name=? and organize=?"), name, organize).first();
    }
    
    public static <T extends BaseOrganize> List<T> fetchByIds(List<Long> ids) {
        if (BaseUtils.collectionEmpty(ids)) {
            return Collections.EMPTY_LIST;
        }
        return T.find(defaultSql("id in (:ids)")).bind("ids", ids.toArray()).fetch();
    }
    
    public static <T extends BaseOrganize> List<T> fetchByPerson(BasePerson person) {
        return T.find(defaultSql("person=?"), person).fetch();
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
