package jobs;

import play.jobs.Job;
import play.jobs.JobsPlugin;
import utils.JobQueue;
import vos.back.JobVO;

public class BaseJob extends Job {
    
    public JobVO vo;
    
    @Override
    public void before() {
        super.before();
        vo = new JobVO();
        vo.startTime = System.currentTimeMillis();
        vo.name = this.getClass().getSimpleName();
    }
    
    @Override
    public void doJob() throws Exception {
        super.doJob();
        vo.executor = JobsPlugin.executor.toString();
        vo.context = this.getInvocationContext().toString();
    }
    
    @Override
    public void onException(Throwable e) {
        super.onException(e);
        vo.exception = e.getMessage() + "\n";
        for (StackTraceElement stackTraceElement : e.getStackTrace()) {
            vo.exception += stackTraceElement.toString() + "\n";
        }
        System.err.println(vo.exception);
    }
    
    @Override
    public void after() {
        super.after();
    }
    
    @Override
    public void _finally() {
        super._finally();
        vo.endTime = System.currentTimeMillis();
        JobQueue.getInstance().add(vo);
    }
}
