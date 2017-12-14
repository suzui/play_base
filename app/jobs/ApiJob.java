package jobs;

import models.back.Api;
import play.cache.Cache;
import play.jobs.Job;
import vos.back.ApiVO;

public class ApiJob extends Job {
    
    private String requestHashCode;
    
    public ApiJob(String requestHashCode) {
        this.requestHashCode = requestHashCode;
    }
    
    @Override
    public void doJob() throws Exception {
        ApiVO apiVO = (ApiVO) Cache.get(requestHashCode);
        Api.add(apiVO);
        Cache.delete(requestHashCode);
    }
}
