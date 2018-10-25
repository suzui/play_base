package controllers.back;

import com.fasterxml.jackson.core.JsonProcessingException;
import models.back.Job;
import utils.DateUtils;
import vos.PageData;
import vos.Result;
import vos.back.JobVO;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class JobController extends BackController {
    
    public static void list(JobVO vo) {
        List<Job> jobs = Job.fetch(vo);
        List<JobVO> jobVOs = jobs.stream().map(a -> new JobVO(a)).collect(Collectors.toList());
        renderJSON(Result.succeed(new PageData(jobVOs)));
    }
    
    public static void page(JobVO vo) {
        if (vo.startTime == null) {
            vo.startTime = System.currentTimeMillis() - DateUtils.DAY * 3;
        }
        if (vo.endTime == null) {
            vo.endTime = System.currentTimeMillis() + DateUtils.DAY;
        }
        List<Job> jobs = Job.fetch(vo);
        List<JobVO> jobVOs = jobs.stream().map(a -> new JobVO(a)).collect(Collectors.toList());
        Map map = new HashMap();
        map.put("code", 0);
        map.put("msg", "");
        map.put("count", Job.count(vo));
        map.put("data", jobVOs);
        String result = "";
        try {
            result = Result.mapper.writeValueAsString(map);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        renderJSON(result);
    }
    
}
