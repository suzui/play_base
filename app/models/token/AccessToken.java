package models.token;

import enums.ClientType;
import models.BaseModel;
import models.person.Person;
import models.sso.SsoPerson;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;
import results.sso.PersonResult;
import utils.SSOUtils;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import java.util.List;

@Entity
public class AccessToken extends BaseModel {
    
    public String accesstoken;
    @ManyToOne
    public BasePerson person;
    public String appVersion;
    public String appType;
    public String osVersion;
    public String clientType;
    public String deviceToken;
    public String pushToken;
    public Boolean notify = true;
    
    public static AccessToken add(BasePerson person) {
        AccessToken at = new AccessToken();
        at.person = person;
        at.accesstoken = RandomStringUtils.randomAlphabetic(6) + "-" + System.currentTimeMillis();
        at.person.loginAmount = at.person.loginAmount == null ? 0 : at.person.loginAmount;
        at.person.loginAmount++;
        at.person.save();
        return at.save();
    }
    
    public void update(String appVersion, String appType, String osVersion, String clientType, String deviceToken) {
        this.appVersion = appVersion;
        this.appType = appType;
        this.osVersion = osVersion;
        this.clientType = clientType;
        this.deviceToken = deviceToken;
        this.person.firstLoginTime = this.person.firstLoginTime == null ? System.currentTimeMillis() : this.person.firstLoginTime;
        this.person.lastLoginTime = System.currentTimeMillis();
        this.person.save();
        this.save();
        if (!(ClientType.WEB.code() + "").equals(this.clientType)) {
            this.fetchOthersByPerson().forEach(at -> at.del());
        }
    }
    
    public void pushToken(String pushToken) {
        this.pushToken = pushToken;
        this.save();
        if (!(ClientType.WEB.code() + "").equals(this.clientType)) {
            this.fetchOthersByPushToken().forEach(at -> at.del());
        }
    }
    
    public void del() {
        this.logicDelete();
    }
    
    public static AccessToken findByAccesstoken(String accesstoken) {
        if (StringUtils.isBlank(accesstoken)) {
            return null;
        }
        if (accesstoken.length() != 56) {
            return AccessToken.find(defaultSql("accesstoken = ?"), accesstoken).first();
        }
        AccessToken at = AccessToken.find(defaultSql("accesstoken = ?"), accesstoken.substring(36)).first();
        if (at != null) {
            return at;
        }
        PersonResult personResult = SSOUtils.auth(accesstoken);
        if (personResult == null || !personResult.succ()) {
            return null;
        }
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
    
    public List<AccessToken> fetchOthersByPerson() {
        return AccessToken.find(defaultSql("person=? and appType=? and clientType<>'100' and id<>?"), this.person, this.appType, this.id).fetch();
    }
    
    public List<AccessToken> fetchOthersByPushToken() {
        return AccessToken.find(defaultSql("pushToken=? and appType=? and clientType<>'100' and id<>?"), this.pushToken, this.appType, this.id).fetch();
    }
    
}