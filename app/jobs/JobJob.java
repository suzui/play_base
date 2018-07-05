package jobs;

import play.jobs.Job;
import utils.JobQueue;
import vos.back.JobVO;

public class JobJob extends Job {
    
    @Override
    public void doJob() throws Exception {
        JobVO jobVO = JobQueue.getInstance().poll();
        if (jobVO != null) {
            models.back.Job.add(jobVO);
        }
    }
}
