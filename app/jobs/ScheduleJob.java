package jobs;

import play.Play;
import play.jobs.Job;
import play.jobs.JobsPlugin;
import play.jobs.OnApplicationStart;
import play.utils.Java;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.FutureTask;
import java.util.concurrent.ScheduledFuture;

@OnApplicationStart(async = true)
public class ScheduleJob extends Job {
    
    public static int i = 0;
    
    @Override
    public void before() {
        super.before();
    }
    
    
    @Override
    public void doJob() throws Exception {
        super.doJob();
        if (Play.configuration.getProperty("schedule", "off").equals("on")) {
            return;
        }
        BlockingQueue<Runnable> queue = JobsPlugin.executor.getQueue();
        System.err.println(queue.size());
        for (final Object o : queue) {
            ScheduledFuture task = (ScheduledFuture) o;
            if (task.isDone() || task.isCancelled()) {
                continue;
            }
            Job job = (Job) Java.extractUnderlyingCallable((FutureTask) task);
            if (BaseJob.class.isAssignableFrom(job.getClass())) {
                task.cancel(true);
            }
        }
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
