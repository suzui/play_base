package jobs;

import enums.ProStatus;
import models.back.Pro;
import play.jobs.Every;
import play.jobs.Job;
import utils.ShellUtils;

import java.util.List;

@Every("1mn")
public class ProFreshJob extends Job {
    
    @Override
    public void doJob() throws Exception {
        super.doJob();
        List<Pro> pros = Pro.fetchAll();
        pros.stream().filter(p -> p.isend()).forEach(p -> {
            ShellUtils.Result result = p.check();
            if (result.succ()) {
                if (result.read.contains("java")) {
                    p.status(ProStatus.NORMAL);
                } else if (p.status != ProStatus.START) {
                    p.status(ProStatus.STOP);
                }
            }
            
        });
        
    }
}
