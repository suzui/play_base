package jobs;

import models.back.Pro;
import play.jobs.Job;
import play.jobs.On;
import utils.DateUtils;

import java.text.DecimalFormat;
import java.util.List;

@On("0 0 1 1 * ?")
public class ProCleanJob extends Job {
    
    @Override
    public void doJob() throws Exception {
        super.doJob();
        List<Pro> pros = Pro.fetchAll();
        long time = System.currentTimeMillis();
        String pattern = "log.log." + DateUtils.year(time) + "-" + new DecimalFormat("00").format((DateUtils.month(time) - 1));
        pros.stream().filter(p -> p.location.contains("app")).forEach(p -> {
            p.clean(pattern);
        });
    }
    
}
