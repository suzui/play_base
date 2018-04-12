package models.token;

import models.SSOModel;
import org.apache.commons.lang.StringUtils;
import play.data.validation.MaxSize;
import play.data.validation.MinSize;
import play.data.validation.Required;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "Person")
public abstract class BasePerson extends SSOModel {
    @Required
    @MinSize(2)
    @MaxSize(10)
    public String username;
    public String zone;
    public String phone;
    public String email;
    public String password;
    public String number;
    public String name;
    public String nickname;
    public String pinyin;
    public String avatar;
    @Column(length = 1000)
    public String intro;
    public Long birthday;
    public Long firstLoginTime;
    public Long lastLoginTime;
    public Integer loginAmount;
    
    @ManyToOne
    public BaseOrganize root;//组织root
    
    @Transient
    public String accesstoken;
    
    public void updateToken() {
        this.accesstoken = UUID.randomUUID().toString();
        this.save();
    }
    
    public String accesstoken() {
        if (this.accesstoken == null) {
            this.updateToken();
        }
        return this.accesstoken;
    }
    
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
    
    public static <T extends BasePerson> T findBySsoId(Long ssoId) {
        return BasePerson.find(defaultSql("ssoId=?"), ssoId).first();
    }
    
}
