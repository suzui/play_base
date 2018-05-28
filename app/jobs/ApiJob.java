package jobs;

import models.back.Api;
import play.jobs.Job;
import utils.ApiQueue;
import vos.back.ApiVO;

public class ApiJob extends Job {
    
    @Override
    public void doJob() throws Exception {
        ApiVO apiVO = ApiQueue.getInstance().poll();
        if (apiVO != null && !apiVO.url.contains("/back/")) {
            Api.add(apiVO);
        }
    }
}
