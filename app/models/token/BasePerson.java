package models.token;

import enums.AccessType;
import enums.PersonType;
import enums.Sex;
import models.BaseModel;
import models.access.BaseAccess;
import models.access.BaseAuthorization;
import models.person.Person;
import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.StringUtils;
import play.data.validation.MaxSize;
import play.data.validation.MinSize;
import play.data.validation.Required;
import play.jobs.Job;
import utils.BaseUtils;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Entity
@Table(name = "Person")
public class BasePerson extends BaseModel {
    @Required
    @MinSize(2)
    @MaxSize(10)
    @Column(columnDefinition = STRING + "'用户名'")
    public String username;
    @Column(columnDefinition = STRING + "'密码'")
    public String password;
    @Column(columnDefinition = STRING + "'地区'")
    public String zone;
    @Column(columnDefinition = STRING + "'手机'")
    public String phone;
    @Column(columnDefinition = STRING + "'邮箱'")
    public String email;
    @Column(columnDefinition = STRING + "'姓名'")
    public String name;
    @Column(columnDefinition = STRING + "'昵称'")
    public String nickname;
    @Column(columnDefinition = STRING + "'拼音'")
    public String pinyin;
    @Column(columnDefinition = STRING + "'头像'")
    public String avatar;
    @Column(columnDefinition = LONG + "'出生日期'")
    public Long birthday;
    @Column(columnDefinition = STRING_1000 + "'简介'")
    public String intro;
    @Column(columnDefinition = STRING_1000 + "'备注'")
    public String remark;
    @Column(columnDefinition = STRING + "'工号'")
    public String number;
    @Column(columnDefinition = STRING + "'座机'")
    public String landline;
    @Column(columnDefinition = STRING + "'办公地点'")
    public String office;
    @Column(columnDefinition = LONG + "'面试时间'")
    public Long interviewday;
    @Column(columnDefinition = LONG + "'入职时间'")
    public Long entryday;
    @Column(columnDefinition = STRING + "'学校'")
    public String school;
    @Column(columnDefinition = STRING + "'专业'")
    public String major;
    @Column(columnDefinition = LONG + "'毕业时间'")
    public Long graduateday;
    @Column(columnDefinition = STRING + "'籍贯'")
    public String register;
    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = STRING + "'性别'")
    public Sex sex = Sex.NOPOINT;
    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = STRING + "'用户类型'")
    public PersonType type;//项目enum需定义
    @Column(columnDefinition = LONG + "'首次登录时间'")
    public Long firstLoginTime;
    @Column(columnDefinition = LONG + "'最后登录时间'")
    public Long lastLoginTime;
    @Column(columnDefinition = LONG + "'登录次数'")
    public Integer loginAmount;
    
    @Column(columnDefinition = BOOLEAN + "'是否需要做全增量标识 根据场景标识'")
    public Boolean increase = false;
    @Column(columnDefinition = BOOLEAN + "'是否初始管理员'")
    public Boolean origin = false;
    
    @ManyToOne
    public BaseOrganize organize;//所属机构
    
    public <T extends BaseOrganize> T organize() {
        return this.organize == null ? null : (T) this.organize;
    }
    
    public static boolean isPhoneLegal(String phone) {
        String regExp = "^(1)\\d{10}$";
        return StringUtils.isNotBlank(phone) && phone.matches(regExp);
    }
    
    public static boolean isEmailLegal(String email) {
        String regExp = "[a-zA-Z0-9._%-]+@[a-zA-Z0-9]+(.[a-zA-Z]{2,4}){1,4}";
        return StringUtils.isNotBlank(email) && email.matches(regExp);
    }
    
    public static boolean isPasswordLegal(String password) {
        return StringUtils.isNotBlank(password) && password.length() >= 6;
    }
    
    public boolean isPasswordRight(String password) {
        return StringUtils.equalsIgnoreCase(password, this.password);
    }
    
    public void editPassword(String password) {
        this.password = password;
        this.save();
    }
    
    public void increase(boolean increase) {
        if (increase && this.loginAmount == null) {
            //未登录用户increase无需改成true,避免初次增量乐观锁
            return;
        }
        this.increase = increase;
        this.save();
    }
    
    public void del() {
        BasePerson person = this;
        new Job() {
            @Override
            public void doJob() throws Exception {
                super.doJob();
                BaseAuthorization.fetchByPerson(person).forEach(a -> a.del());
                BaseRelation.fetchByPerson(person).forEach(r -> r.del());
            }
        }.now();
        this.logicDelete();
    }
    
    public static <T extends BasePerson> T findByID(Long id) {
        return BasePerson.find(defaultSql("id=?"), id).first();
    }
    
    public static <T extends BasePerson> List<T> fetchByIds(List<Long> ids) {
        if (BaseUtils.collectionEmpty(ids)) {
            return Collections.EMPTY_LIST;
        }
        return Person.find(defaultSql("id in (:ids)")).bind("ids", ids.toArray()).fetch();
    }
    
    public static <T extends BasePerson> T findByUsername(String username, Integer type) {
        PersonType personType = PersonType.convert(type);
        BasePerson basePerson = BasePerson.find(defaultSql("username=? and type=?"), username, personType).first();
        if (basePerson == null) {
            basePerson = BasePerson.find(defaultSql("email=? and type=?"), username, personType).first();
        }
        if (basePerson == null) {
            basePerson = BasePerson.find(defaultSql("phone=? and type=?"), username, personType).first();
        }
        return (T) basePerson;
    }
    
    //超级后台管理员授权
    public <T extends BaseAuthorization> List<T> authorizations() {
        return T.fetchByPerson(this);
    }
    
    //超级后台管理员权限
    public <T extends BaseAccess> List<T> access() {
        if (BooleanUtils.isTrue(this.origin)) {
            return T.fetchByType(AccessType.ADMIN);
        }
        List<T> access = new ArrayList<>();
        for (BaseAuthorization a : BaseAuthorization.fetchByPerson(this)) {
            access.addAll(a.permission.access());
        }
        return access;
    }
    
    //机构后台管理员授权
    public <T extends BaseAuthorization> List<T> authorizations(BaseOrganize organize) {
        return T.fetchByPersonAndOrganize(this, organize);
    }
    
    //机构后台管理员权限
    public <T extends BaseAccess> List<T> access(BaseOrganize organize) {
        if (BooleanUtils.isTrue(this.origin)) {
            return T.fetchByType(AccessType.ORGANIZE);
        }
        List<T> access = new ArrayList<>();
        for (BaseAuthorization a : BaseAuthorization.fetchByPersonAndOrganize(this, organize)) {
            access.addAll(a.permission.access());
        }
        return access;
    }
    
}
