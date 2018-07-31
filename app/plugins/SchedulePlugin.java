package plugins;

import play.Logger;
import play.PlayPlugin;
import play.jobs.JobsPlugin;
import utils.BaseUtils;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ScheduledFuture;

public class SchedulePlugin extends PlayPlugin {
    
    
    @Override
    public void onLoad() {
        Logger.info("SchedulePlugin load.");
    }
    
    
    @Override
    public void afterApplicationStart() {
        super.afterApplicationStart();
        if (BaseUtils.propertyOn("schedule")) {
            return;
        }
        BlockingQueue<Runnable> queue = JobsPlugin.executor.getQueue();
        Logger.info("Schedule Job size:%d", queue.size());
        for (final Object o : queue) {
            ScheduledFuture task = (ScheduledFuture) o;
            if (task.isDone() || task.isCancelled()) {
                continue;
            }
            //Job job = (Job) Java.extractUnderlyingCallable((FutureTask) task);
            //if (BaseJob.class.isAssignableFrom(job.getClass())) {
            task.cancel(true);
            //}
        }
    }
    
    
}
