package jobs;

import models.token.AccessToken;
import org.apache.commons.lang.StringUtils;
import play.jobs.Job;

public class UpdateLoginInfoJob extends Job {
    
    private String accesstoken;
    
    private String appVersion;
    private String appType;
    private String osVersion;
    private String clientType;
    private String deviceInfo;
    private String deviceToken;
    
    public UpdateLoginInfoJob(String accesstoken, String appVersion, String appType, String osVersion,
                              String clientType, String deviceInfo, String deviceToken) {
        this.accesstoken = accesstoken;
        this.appVersion = appVersion;
        this.appType = appType;
        this.osVersion = osVersion;
        this.clientType = clientType;
        this.deviceInfo = deviceInfo;
        this.deviceToken = deviceToken;
    }
    
    @Override
    public void doJob() throws Exception {
        AccessToken token = AccessToken.findByAccesstoken(accesstoken);
        if (token != null && StringUtils.isNotBlank(appVersion)) {
            token.update(appVersion, appType, osVersion, clientType, deviceInfo, deviceToken);
        }
    }
}
