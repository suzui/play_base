package utils;

import com.google.gson.Gson;
import com.qiniu.common.QiniuException;
import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import play.cache.Cache;

import java.io.File;

public class QiniuUtils {
    
    public final static String UPTOKENKEY = "qiniuUptoken";
    public final static String ACCESSKEY = BaseUtils.property("qiniu.accessKey");
    public final static String SECRETKEY = BaseUtils.property("qiniu.secretKey");
    public final static String BUCKET = BaseUtils.property("qiniu.bucket");
    public final static String DOMAIN = BaseUtils.property("qiniu.domain");
    
    public static String initUpToken() {
        Auth auth = Auth.create(ACCESSKEY, SECRETKEY);
        String upToken = auth.uploadToken(BUCKET, null, 86400, null);
        //不同项目间公用 cache不带prefix 只区分正式测试环境
        Cache.set((BaseUtils.isProd() ? "prod_" : "test_") + UPTOKENKEY, upToken);
        return upToken;
    }
    
    public static String upToken() {
        Object uptoken = Cache.get((BaseUtils.isProd() ? "prod_" : "test_") + UPTOKENKEY);
        if (uptoken == null) {
            uptoken = initUpToken();
        }
        return (String) uptoken;
    }
    
    public static String upload(File file) {
        //构造一个带指定Zone对象的配置类
        Configuration cfg = new Configuration(Zone.zone0());
        //...其他参数参考类注释
        UploadManager uploadManager = new UploadManager(cfg);
        //...生成上传凭证，然后准备上传
        //默认不指定key的情况下，以文件内容的hash值作为文件名
        String key = file.getName();
        String upToken = upToken();
        try {
            Response response = uploadManager.put(file.getAbsolutePath(), key, upToken);
            //解析上传成功的结果
            DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
        } catch (QiniuException ex) {
        }
        return DOMAIN + "/" + key;
    }
    
    
}
