package models.token;

import enums.ClientType;
import models.BaseModel;
import models.person.Person;
import models.sso.SsoPerson;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;
import org.hibernate.annotations.Table;
import results.sso.PersonResult;
import utils.SSOUtils;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import java.util.List;

@Entity
@Table(appliesTo = "AccessToken", comment = "token")
public class AccessToken extends BaseModel {
    
    public String accesstoken;
    @ManyToOne
    public BasePerson person;
    public String appVersion;
    public String appType;
    public String osVersion;
    public String clientType;
    public String deviceToken;
    public String deviceInfo;
    public String pushToken;
    public Boolean notify = true;
    
    public <T extends BasePerson> T person() {
        return this.person == null ? null : (T) this.person;
    }
    
    public static AccessToken add(BasePerson person) {
        AccessToken at = new AccessToken();
        at.person = person;
        at.accesstoken = RandomStringUtils.randomAlphabetic(6) + "-" + System.currentTimeMillis();
        if (at.person.loginAmount == null) {
            at.person.loginAmount = 1;
            at.person.firstLoginTime = System.currentTimeMillis();
        } else {
            at.person.loginAmount++;
            at.person.lastLoginTime = System.currentTimeMillis();
        }
        at.person.save();
        return at.save();
    }
    
    public void update(String appVersion, String appType, String osVersion, String clientType, String deviceInfo, String deviceToken) {
        this.appVersion = appVersion;
        this.appType = appType;
        this.osVersion = osVersion;
        this.clientType = clientType;
        this.deviceInfo = deviceInfo;
        this.deviceToken = deviceToken;
        this.save();
        if (!(ClientType.WEB.code() + "").equals(this.clientType)) {
            this.fetchOthersByPerson().forEach(at -> at.del());
        }
    }
    
    public void pushToken(String pushToken) {
        if (StringUtils.equalsIgnoreCase(this.pushToken, pushToken)) {
            return;
        }
        this.pushToken = pushToken;
        this.save();
        this.fetchOthersByPushToken().forEach(at -> at.del());
    }
    
    public static void logout(Long personId, String appType) {
        AccessToken at = AccessToken.find(defaultSql("person.id=? and appType=?"), personId, appType).first();
        if (at != null) {
            at.del();
        }
    }
    
    public static void logout(Person person, String appType) {
        logout(person.id, appType);
    }
    
    public boolean isWeb() {
        return (ClientType.WEB.code() + "").equals(this.clientType);
    }
    
    public boolean isMobile() {
        return !isWeb();
    }
    
    public void del() {
        this.logicDelete();
    }
    
    public static AccessToken findByAccesstoken(String accesstoken) {
        if (StringUtils.isBlank(accesstoken)) {
            return null;
        }
        //非56位 走本地校验
        if (accesstoken.length() != 56) {
            return AccessToken.find(defaultSql("accesstoken = ?"), accesstoken).first();
        }
        //56位 先取前36位 走本地校验
        AccessToken at = AccessToken.find(defaultSql("accesstoken = ?"), accesstoken.substring(36)).first();
        if (at != null) {
            return at;
        }
        if (!AccessToken.find("accesstoken = ?", accesstoken.substring(36)).fetch().isEmpty()) {
            return null;
        }
        //本地校验无记录 取前36位 往sso中心查询
        PersonResult personResult = SSOUtils.auth(accesstoken);
        if (personResult == null || !personResult.succ()) {
            return null;
        }
        //sso校验通过 取前36位生成本地记录 下次直接查询本地
        SsoPerson person = SsoPerson.findBySsoId(personResult.data.personId);
        at = new AccessToken();
        at.person = person;
        at.accesstoken = accesstoken.substring(36);
        return at.save();
    }
    
    public static <T extends BasePerson> T findPersonByAccesstoken(String accesstoken) {
        AccessToken accessToken = findByAccesstoken(accesstoken);
        if (accessToken == null) {
            return null;
        }
        return (T) accessToken.person;
    }
    
    public static AccessToken findMobile(Long personId, String appType) {
        return AccessToken.find(defaultSql("person.id=? and appType=? and clientType<>'100' "), personId, appType).first();
    }
    
    public static AccessToken findMobile(Person person, String appType) {
        return findMobile(person.id, appType);
    }
    
    public static AccessToken findMobile(Long personId, int appType) {
        return findMobile(personId, appType + "");
    }
    
    public static AccessToken findMobile(Person person, int appType) {
        return findMobile(person.id, appType);
    }
    
    public List<AccessToken> fetchOthersByPerson() {
        return AccessToken.find(defaultSql("person=? and appType=? and clientType<>'100' and id<>?"), this.person, this.appType, this.id).fetch();
    }
    
    public List<AccessToken> fetchOthersByPushToken() {
        return AccessToken.find(defaultSql("pushToken=? and appType=? and clientType<>'100' and id<>?"), this.pushToken, this.appType, this.id).fetch();
    }
    
}