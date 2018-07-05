package vos.back;

import annotations.DataField;
import models.back.Job;
import org.apache.commons.lang.BooleanUtils;
import vos.OneData;

import java.io.Serializable;

public class JobVO extends OneData implements Serializable {
    
    @DataField(name = "job纪录id")
    public String jobId;
    @DataField(name = "job")
    public String name;
    @DataField(name = "executor")
    public String executor;
    @DataField(name = "context")
    public String context;
    @DataField(name = "开始时间")
    public Long startTime;
    @DataField(name = "结束时间")
    public Long endTime;
    @DataField(name = "异常报告")
    public String exception;
    
    @DataField(name = "是否异常")
    public Integer error;
    @DataField(name = "运行环境")
    public String env;
    
    
    public JobVO() {
    
    }
    
    public JobVO(Job job) {
        super(0l);
        this.jobId = job.get_id().toString();
        this.name = job.name;
        this.executor = job.executor;
        this.context = job.context;
        this.startTime = job.startTime;
        this.endTime = job.endTime;
        this.exception = job.exception;
        this.env = job.env;
    }
    
}
