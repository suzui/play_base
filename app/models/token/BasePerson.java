package models.token;

import enums.Sex;
import models.BaseModel;
import org.apache.commons.lang.StringUtils;
import play.data.validation.MaxSize;
import play.data.validation.MinSize;
import play.data.validation.Required;

import javax.persistence.*;

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
    public Sex sex = Sex.NOPOINT;
    public Long firstLoginTime;//首次登录时间
    public Long lastLoginTime;//最后登录时间
    public Integer loginAmount;//登录次数
    
    @ManyToOne
    public BaseOrganize organize;//机构
    
    public static boolean isPhoneLegal(String phone) {
        String regExp = "^((13[0-9])|(15[0-9])|(18[0-9])|(17[0-9]))\\d{8}$";
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
    
    public static <T extends BasePerson> T findByID(Long id) {
        return BasePerson.find(defaultSql("id=?"), id).first();
    }
    
}
