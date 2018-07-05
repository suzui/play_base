package jobs;

import enums.AppType;
import enums.ClientType;
import play.jobs.Job;
import play.jobs.OnApplicationStart;
import utils.CacheUtils;
import vos.VersionVO;

@OnApplicationStart(async = true)
public class VersionJob extends Job {
    
    @Override
    public void doJob() throws Exception {
        for (AppType app : AppType.values()) {
            for (ClientType client : ClientType.values()) {
                if (client != ClientType.WEB) {
                    VersionVO versionVO = new VersionVO(app, client);
                    CacheUtils.add(VersionVO.key(app, client), versionVO);
                }
            }
        }
    }
    
}
