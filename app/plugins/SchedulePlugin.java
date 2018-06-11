package plugins;

import jobs.BaseJob;
import play.Logger;
import play.Play;
import play.PlayPlugin;
import play.jobs.Job;
import play.jobs.JobsPlugin;
import play.utils.Java;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.FutureTask;
import java.util.concurrent.ScheduledFuture;

public class SchedulePlugin extends PlayPlugin {
    
    
    @Override
    public void onLoad() {
        Logger.info("SchedulePlugin load.");
    }
    
    
    @Override
    public void afterApplicationStart() {
        super.afterApplicationStart();
        m();
    }
    
    public void m() {
        if (Play.configuration.getProperty("schedule", "off").equals("on")) {
            return;
        }
        BlockingQueue<Runnable> queue = JobsPlugin.executor.getQueue();
        Logger.info("Schedule Job size:%d", queue.size());
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
    
}
