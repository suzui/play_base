package models.token;

import models.BaseModel;
import utils.BaseUtils;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.util.*;

@Entity
@Table(name = "Relation")
public class BaseRelation extends BaseModel {
    
    @ManyToOne
    public BaseOrganize organize;//组织
    @ManyToOne
    public BasePerson person;//人员
    
    @Column(columnDefinition = DOUBLE + "'排序'")
    public Double rank;
    
    @Column(columnDefinition = BOOLEAN + "'是否管理员'")
    public Boolean isAdmin = false;
    
    
    public void setAdmin(Boolean isAdmin) {
        this.isAdmin = isAdmin;
        this.save();
    }
    
    public void setAdmin() {
        BaseRelation.fetchByOrganize(this.organize).forEach(r -> r.setAdmin(false));
        this.isAdmin = true;
        this.save();
    }
    
    public static void cleanAdmin(BaseOrganize organize) {
        BaseRelation.fetchByOrganize(organize).forEach(r -> r.setAdmin(false));
    }
    
    public void move(Long preId, Long nextId) {
        move(preId == null ? null : BaseRelation.findByID(preId), nextId == null ? null : BaseRelation.findByID(nextId));
    }
    
    public void move(BaseRelation pre, BaseRelation next) {
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
    
    public Double initRank() {
        Double rank = BaseRelation.find(defaultSql("select max(rank) from Relation where organize=?"), this.organize).first();
        return rank == null ? 0 : rank + 1;
    }
    
    public <T extends BasePerson> T person() {
        return this.person == null ? null : (T) this.person;
    }
    
    public <T extends BaseOrganize> T organize() {
        return this.organize == null ? null : (T) this.organize;
    }
    
    public Boolean isAdmin() {
        return this.organize.person != null && this.organize.person.id.equals(person.id);
    }
    
    public static Boolean isAdmin(BaseOrganize organize, BasePerson person) {
        return organize != null && organize.person != null && organize.person.id.equals(person.id);
    }
    
    public static Boolean isJoined(BaseOrganize organize, BasePerson person) {
        return findByOrganizeAndPerson(organize, person) != null;
    }
    
    public static Boolean isJoined(Long organizeId, Long personId) {
        return findByOrganizeAndPerson(organizeId, personId) != null;
    }
    
    public static <T extends BasePerson> List<T> persons(List<Long> organizeIds, List<Long> personIds) {
        Set<T> persons = new LinkedHashSet<>();
        if (BaseUtils.collectionNotEmpty(organizeIds)) {
            fetchByOrganizes(organizeIds).forEach(r -> persons.add(r.person()));
        }
        if (BaseUtils.collectionNotEmpty(personIds)) {
            T.fetchByIds(personIds).forEach(p -> persons.add((T) p));
        }
        return new ArrayList<>(persons);
    }
    
    
    public static void del(long organizeId, long personId) {
        BaseRelation relation = findByOrganizeAndPerson(organizeId, personId);
        if (relation != null) {
            relation.del();
        }
    }
    
    public static void del(BaseOrganize organize, BasePerson person) {
        BaseRelation relation = findByOrganizeAndPerson(organize, person);
        if (relation != null) {
            relation.del();
        }
    }
    
    public void del() {
        if (this.organize.isRoot()) {
            this.person.relations(organize).forEach(relation -> relation.del());
        }
        this.logicDelete();
    }
    
    public static <T extends BaseRelation> T findByID(Long id) {
        return T.find(defaultSql("id=?"), id).first();
    }
    
    public static <T extends BaseRelation> T findByOrganizeAndPerson(Long organizeId, Long personId) {
        return T.find(defaultSql("organize.id=? and person.id=?"), organizeId, personId).first();
    }
    
    public static <T extends BaseRelation> T findByOrganizeAndPerson(BaseOrganize organize, BasePerson person) {
        return T.find(defaultSql("organize=? and person=?"), organize, person).first();
    }
    
    public static <T extends BaseRelation> List<T> fetchByIds(List<Long> ids) {
        if (ids == null || ids.isEmpty()) {
            return Collections.EMPTY_LIST;
        }
        return T.find(defaultSql("id in (:ids)")).bind("ids", ids.toArray()).fetch();
    }
    
    public static <T extends BaseRelation> List<T> fetchByPerson(Long personId) {
        return T.find(defaultSql("person.id=?"), personId).fetch();
    }
    
    public static <T extends BaseRelation> List<T> fetchByPerson(BasePerson person) {
        return T.find(defaultSql("person=?"), person).fetch();
    }
    
    public static <T extends BaseRelation> List<T> fetchByOrganize(Long organizeId) {
        return T.find(defaultSql("organize.id=?"), organizeId).fetch();
    }
    
    public static <T extends BaseRelation> List<T> fetchByOrganize(BaseOrganize organize) {
        return T.find(defaultSql("organize=?"), organize).fetch();
    }
    
    public static <T extends BaseRelation> List<T> fetchByOrganizes(List<Long> organizeIds) {
        if (BaseUtils.collectionEmpty(organizeIds)) {
            return Collections.EMPTY_LIST;
        }
        return T.find(defaultSql("organize.id in (:organizeIds)")).bind("organizeIds", organizeIds.toArray()).fetch();
    }
    
    public static <T extends BaseRelation> List<T> fetchAll() {
        return T.find(defaultSql()).fetch();
    }
}
