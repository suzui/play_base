package models.token;

import enums.PersonType;
import enums.Sex;
import models.BaseModel;
import models.access.BaseAccess;
import models.access.BaseAccessPerson;
import models.access.BasePermissionPerson;
import org.apache.commons.lang.StringUtils;
import play.data.validation.MaxSize;
import play.data.validation.MinSize;
import play.data.validation.Required;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "Person")
public class BasePerson extends BaseModel {
    @Required
    @MinSize(2)
    @MaxSize(10)
    public String username;//用户名
    public String password;//密码
    public String zone;//地区
    public String phone;//手机
    public String email;//邮箱
    public String name;//姓名
    public String number;//工号
    public String nickname;//昵称
    public String pinyin;//拼音
    public String avatar;//头像
    public Long birthday;//出生日期
    @Column(length = 1000)
    public String intro;//简介
    @Enumerated(EnumType.STRING)
    public Sex sex = Sex.NOPOINT;//person中无需重复声明 enum需定义
    @Enumerated(EnumType.STRING)
    public PersonType type;//person中无需重复声明 enum需定义
    public Long firstLoginTime;//首次登录时间
    public Long lastLoginTime;//最后登录时间
    public Integer loginAmount;//登录次数
    
    public Boolean increase = false;//是否需要做全增量标识 根据场景标识
    
    @ManyToOne
    public BaseOrganize organize;//机构
    
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
    
    public static <T extends BasePerson> T findByID(Long id) {
        return BasePerson.find(defaultSql("id=?"), id).first();
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
    
    //超级后台管理员权限
    public List<BaseAccess> access() {
        if (this.username.equals("admin")) {
            return BaseAccess.findAll();
        }
        List<BaseAccessPerson> accessPersons = BasePermissionPerson.fetchByPerson(this).stream().map(pp -> BaseAccessPerson.fetchByPerson(this)).flatMap(aps -> aps.stream()).collect(Collectors.toList());
        accessPersons.addAll(BaseAccessPerson.fetchByPerson(this));
        return new ArrayList<>(new HashSet<>(accessPersons.stream().map(ap -> ap.access).collect(Collectors.toList())));
    }
    
    //机构后台管理员权限
    public List<BaseAccess> access(BaseOrganize organize) {
        List<BaseAccessPerson> accessPersons = BasePermissionPerson.fetchByPersonAndOrganize(this, organize).stream().map(pp -> BaseAccessPerson.fetchByPersonAndOrganize(this, organize)).flatMap(aps -> aps.stream()).collect(Collectors.toList());
        accessPersons.addAll(BaseAccessPerson.fetchByPersonAndOrganize(this, organize));
        return new ArrayList<>(new HashSet<>(accessPersons.stream().map(ap -> ap.access).collect(Collectors.toList())));
    }
    
}
