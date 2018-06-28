package vos.back;

import annotations.DataField;
import models.log.JobLog;
import vos.OneData;

public class JobLogVO extends OneData {
    
    @DataField(name = "系统参数id")
    public Long jobLogId;
    @DataField(name = "名称")
    public String name;
    @DataField(name = "属性")
    public String value;
    @DataField(name = "描述")
    public String intro;
    
    public JobLogVO() {
    
    }
    
    public JobLogVO(JobLog jobLog) {
        this.jobLogId = jobLog.id;
    }
    
    
}
