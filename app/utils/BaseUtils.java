package utils;

import play.Play;
import play.jobs.Job;
import play.jobs.JobsPlugin;
import play.utils.Java;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.FutureTask;
import java.util.concurrent.ScheduledFuture;

public class BaseUtils {
    
    public static boolean isProd() {
        return "p".equals(Play.id);
    }
    
    public static void cancelJob(Class clazz) {
        BlockingQueue<Runnable> queue = JobsPlugin.executor.getQueue();
        for (final Object o : queue) {
            ScheduledFuture task = (ScheduledFuture) o;
            if (task.isDone() || task.isCancelled()) {
                continue;
            }
            Job job = (Job) Java.extractUnderlyingCallable((FutureTask) task);
            if (clazz.isAssignableFrom(job.getClass())) {
                task.cancel(true);
                break;
            }
        }
        
    }
    
}
