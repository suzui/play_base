package models.back;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import org.apache.commons.lang.StringUtils;
import org.bson.types.ObjectId;
import play.Play;
import play.modules.mongo.MongoDB;
import play.modules.mongo.MongoEntity;
import play.modules.mongo.MongoMapper;
import play.modules.mongo.MongoModel;
import vos.back.JobVO;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

@MongoEntity
public class Job extends MongoModel {
    
    public String name;
    public String executor;
    public String context;
    public Long startTime;
    public Long endTime;
    public String status;
    public String exception;
    public String env;
    
    public static Job add(JobVO jobVO) {
        Job job = new Job();
        job.env = Play.applicationPath.getAbsolutePath();
        job.edit(jobVO);
        return job;
    }
    
    public void edit(JobVO jobVO) {
        this.name = jobVO.name != null ? jobVO.name : name;
        this.executor = jobVO.executor != null ? jobVO.executor : executor;
        this.context = jobVO.context != null ? jobVO.context : context;
        this.startTime = jobVO.startTime != null ? jobVO.startTime : startTime;
        this.endTime = jobVO.endTime != null ? jobVO.endTime : endTime;
        this.startTime = jobVO.startTime != null ? jobVO.startTime : startTime;
        this.exception = jobVO.exception != null ? jobVO.exception : exception;
        this.save();
    }
    
    public static Job findByID(String id) {
        DBObject one = MongoDB.db().getCollection("job").findOne(new BasicDBObject("_id", new ObjectId(id)));
        Job job = MongoMapper.convertValue(one.toMap(), Job.class);
        return job;
    }
    
    public static List<Job> fetchAll() {
        return Job.find().fetch();
    }
    
    public static List<Job> fetch(JobVO jobVO) {
        Object[] objects = data(jobVO);
        List<String> sqls = (List<String>) objects[0];
        List<Pattern> params = (List<Pattern>) objects[1];
        return Job.find("by" + StringUtils.join(sqls, "And"), params.toArray()).order("by-startTime")
                .fetch(jobVO.page, jobVO.size);
    }
    
    public static int count(JobVO jobVO) {
        Object[] objects = data(jobVO);
        List<String> sqls = (List<String>) objects[0];
        List<Pattern> params = (List<Pattern>) objects[1];
        return (int) Job.count("by" + StringUtils.join(sqls, "And"), params.toArray());
    }
    
    public static Object[] data(JobVO jobVO) {
        List<String> sqls = new ArrayList<>();
        List<Object> params = new ArrayList<>();
        sqls.add("status");
        params.add(Pattern.compile("^.*$", Pattern.CASE_INSENSITIVE));
        if (StringUtils.isNotBlank(jobVO.name)) {
            sqls.add("name");
            params.add(Pattern.compile("^.*" + jobVO.name + ".*$"));
        }
        if (jobVO.startTime != null) {
            sqls.add("startTime");
            params.add(new BasicDBObject("$gte", jobVO.startTime));
        }
        if (jobVO.endTime != null) {
            sqls.add("endTime");
            params.add(new BasicDBObject("$lte", jobVO.endTime));
        }
        if (jobVO.error != null && jobVO.error == 1) {
            sqls.add("exception");
            params.add(new BasicDBObject("$ne", null));
        }
        return new Object[]{sqls, params};
    }
    
}
