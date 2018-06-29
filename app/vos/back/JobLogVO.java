package vos.back;

import annotations.DataField;
import models.back.JobLog;
import vos.OneData;

public class JobLogVO extends OneData {
    
    @DataField(name = "job纪录id")
    public Long jobLogId;
    
    public JobLogVO() {
    
    }
    
    public JobLogVO(JobLog jobLog) {
        this.jobLogId = jobLog.id;
    }
    
    
}
