package models.token;

import models.BaseModel;
import org.apache.commons.lang.RandomStringUtils;

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
        at.accesstoken = RandomStringUtils.random(6) + "-" + System.currentTimeMillis();
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
        this.person.loginAmount = this.person.loginAmount == null ? 0 : this.person.loginAmount;
        this.person.loginAmount++;
        this.person.save();
        this.save();
        this.fetchOthers().forEach(at -> at.del());
    }
    
    public void pushToken(String pushToken) {
        this.pushToken = pushToken;
        this.save();
    }
    
    public void del() {
        this.logicDelete();
    }
    
    public static AccessToken findByAccesstoken(String accesstoken) {
        return AccessToken.find(defaultSql("accesstoken=?"), accesstoken).first();
    }
    
    public List<AccessToken> fetchOthers() {
        return AccessToken.find(defaultSql("person=? and appType=? and clientType=? and id<>? "), this.person, this.appType, this.clientType, this.id).fetch();
    }
    
    public static <T extends BasePerson> T findPersonByAccesstoken(String accesstoken) {
        return (T) findByAccesstoken(accesstoken).person;
    }
    
}
