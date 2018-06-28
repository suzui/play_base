package jobs;

import play.Logger;
import play.jobs.Job;
import play.jobs.JobsPlugin;

public class BaseJob extends Job {
    
    @Override
    public void before() {
        super.before();
        Logger.info("[job before]:%s", this.getClass().getSimpleName());
        
    }
    
    @Override
    public void doJob() throws Exception {
        super.doJob();
        Logger.info("[job do]:%s", this.executor);
        Logger.info("[job do]:%s", JobsPlugin.executor);
        Logger.info("[job do]:%s", this.getInvocationContext());
    }
    
    @Override
    public void onException(Throwable e) {
        super.onException(e);
    }
    
    @Override
    public void after() {
        super.after();
    }
}
