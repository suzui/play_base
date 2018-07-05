package utils;

import jobs.JobJob;
import vos.back.JobVO;

import java.util.concurrent.ConcurrentLinkedQueue;

public class JobQueue {
    
    private static JobQueue instance;
    
    static {
        instance = new JobQueue();
    }
    
    private final ConcurrentLinkedQueue<JobVO> queue = new ConcurrentLinkedQueue<>();
    
    public static JobQueue getInstance() {
        return instance;
    }
    
    public void add(JobVO apiVO) {
        queue.add(apiVO);
        new JobJob().now();
    }
    
    public JobVO poll() {
        return queue.poll();
    }
}
