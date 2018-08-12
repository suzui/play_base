package models.token;

import models.BaseModel;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.util.List;

@Entity
@Table(name = "Relation")
public class BaseRelation extends BaseModel {
    
    @ManyToOne
    public BaseOrganize organize;//组织
    @ManyToOne
    public BasePerson person;//人员
    
    @Column(columnDefinition = DOUBLE + "'排序'")
    public Double rank;
    
    
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
    
    public static Boolean isAdmin(BaseOrganize organize, BasePerson person) {
        return organize.person.id.equals(person.id);
    }
    
    public void del() {
        this.logicDelete();
    }
    
    public static <T extends BaseRelation> T findByOrganizeAndPerson(BaseOrganize organize, BasePerson person) {
        return T.find(defaultSql("organize=? and person=?"), organize, person).first();
    }
    
    public static <T extends BaseRelation> List<T> fetchByPerson(BasePerson person) {
        return T.find(defaultSql("person=?"), person).fetch();
    }
    
    public static <T extends BaseRelation> List<T> fetchByOrganize(BaseOrganize organize) {
        return T.find(defaultSql("organize=?"), organize).fetch();
    }
}
