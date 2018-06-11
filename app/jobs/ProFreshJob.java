package jobs;

import play.jobs.Every;

@Every("30s")
public class ProFreshJob extends BaseJob {
    
    
    @Override
    public void doJob() throws Exception {
        super.doJob();
        
    }
}
