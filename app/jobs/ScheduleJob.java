package jobs;

import models.back.Api;
import play.jobs.Every;
import play.jobs.Job;
import utils.ApiQueue;
import vos.back.ApiVO;

import java.util.Date;

@Every("3s")
public class ScheduleJob extends Job {
    
    public static int i = 0;
    
    @Override
    public void before() {
        super.before();
    }
    
    @Override
    public void doJob() throws Exception {
        super.doJob();
    }
    
    @Override
    public void onException(Throwable e) {
        e.printStackTrace();
    }
    
    @Override
    public void after() {
        super.after();
    }
    
    @Override
    public void _finally() {
        super._finally();
    }
}
