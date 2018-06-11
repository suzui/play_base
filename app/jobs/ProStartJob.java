package jobs;

import enums.ProStatus;
import models.back.Pro;
import play.Play;
import play.jobs.OnApplicationStart;

@OnApplicationStart
public class ProStartJob extends BaseJob {
    
    @Override
    public void doJob() throws Exception {
        Pro pro = Pro.findByLocation(Play.applicationPath.getAbsolutePath());
        if (pro != null) {
            pro.status(ProStatus.NORMAL);
        }
    }
    
    
}
