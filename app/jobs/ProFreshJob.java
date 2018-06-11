package jobs;

import enums.ProStatus;
import models.back.Pro;
import play.Play;
import play.jobs.Every;

import java.io.File;
import java.util.List;

@Every("30s")
public class ProFreshJob extends BaseJob {
    
    
    @Override
    public void doJob() throws Exception {
        super.doJob();
        List<Pro> pros = Pro.fetchAll();
        pros.stream().filter(p -> p.location.contains("app")).forEach(p -> {
            File file = Play.getFile(p.location + "/server.pid");
            if (file.exists() && p.check().read.contains("java")) {
                p.status(ProStatus.NORMAL);
            } else {
                p.status(ProStatus.STOP);
            }
        });
        
    }
}
