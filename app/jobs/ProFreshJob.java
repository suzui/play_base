package jobs;

import enums.ProStatus;
import models.back.Pro;
import play.jobs.Every;
import play.jobs.Job;

import java.util.List;

@Every("1mn")
public class ProFreshJob extends Job {
    
    
    @Override
    public void doJob() throws Exception {
        super.doJob();
        List<Pro> pros = Pro.fetchAll();
        pros.stream().filter(p -> p.location.contains("app")).forEach(p -> {
            if (p.check().read.contains("java")) {
                p.status(ProStatus.NORMAL);
            } else {
                p.status(ProStatus.STOP);
            }
        });
        
    }
}
