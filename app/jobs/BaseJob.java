package jobs;

import play.jobs.Job;

public class BaseJob extends Job {
    
    @Override
    public void before() {
        super.before();
    }
    
    @Override
    public void doJob() throws Exception {
        super.doJob();
    }
    
    @Override
    public void after() {
        super.after();
    }
}
