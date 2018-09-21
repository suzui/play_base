package jobs;

import play.jobs.Job;
import play.jobs.On;
import utils.BaseUtils;
import utils.HolidayUtils;

import java.util.Date;

@On("0 0 1,23 * * ?")
public class HolidayJob extends Job {
    
    
    @Override
    public void doJob() throws Exception {
        super.doJob();
        if (BaseUtils.isProd() || BaseUtils.isTest()) {
            HolidayUtils.yearMonth(new Date());
        }
    }
}
