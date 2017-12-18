package utils;

import jobs.ApiJob;
import vos.back.ApiVO;

import java.util.concurrent.ConcurrentLinkedQueue;

public class ApiQueue {
    
    private static ApiQueue instance;
    
    static {
        instance = new ApiQueue();
    }
    
    private final ConcurrentLinkedQueue<ApiVO> queue = new ConcurrentLinkedQueue<>();
    
    public static ApiQueue getInstance() {
        return instance;
    }
    
    public void add(ApiVO apiVO) {
        queue.add(apiVO);
        new ApiJob().now();
    }
    
    public ApiVO poll() {
        return queue.poll();
    }
}
