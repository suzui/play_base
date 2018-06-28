package jobs;

import enums.ProStatus;
import models.back.Pro;
import play.jobs.Every;

import java.util.List;

@Every("30s")
public class ProFreshJob extends BaseJob {
    
    
    @Override
    public void doJob() throws Exception {
        super.doJob();
        List<Pro> pros = Pro.fetchAll();
        pros.stream().filter(p -> p.location.contains("pad")).forEach(p -> {
            if (p.check().read.contains("java")) {
                p.status(ProStatus.NORMAL);
            } else {
                p.status(ProStatus.STOP);
            }
        });
        
    }
}