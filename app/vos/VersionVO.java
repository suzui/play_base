package vos;

import annotations.DataField;
import enums.AppType;
import enums.ClientType;
import org.apache.commons.lang.BooleanUtils;
import utils.BaseUtils;

import java.io.Serializable;

public class VersionVO extends OneData implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    @DataField(name = "版本号")
    public String version = "";
    @DataField(name = "下载地址")
    public String downloadUrl = "";
    @DataField(name = "更新说明")
    public String updateIntro = "";
    @DataField(name = "是否强制更新")
    public Integer isForcedUpdate = 0;
    
    @DataField(name = "是否需要更新")
    public Integer needUpdate = 0;
    
    public VersionVO() {
    }
    
    public VersionVO(String key) {
        this.clean();
        this.version = BaseUtils.property(key + ".version");
        this.downloadUrl = BaseUtils.property(key + ".downloadUrl");
        this.updateIntro = BaseUtils.property(key + ".updateIntro");
        this.isForcedUpdate = Integer.parseInt(BaseUtils.property(key + ".isForcedUpdate"));
    }
    
    public VersionVO(AppType app, ClientType client) {
        this(key(app, client));
    }
    
    public VersionVO(int app, int client) {
        this(key(app, client));
    }
    
    public VersionVO(String app, String client) {
        this(key(app, client));
    }
    
    public static String version(String key) {
        return BaseUtils.property(key + ".version");
    }
    
    public static boolean isForcedUpdate(String key) {
        return BooleanUtils.toBoolean(Integer.parseInt(BaseUtils.property(key + ".isForcedUpdate")));
    }
    
    public VersionVO needUpdate() {
        this.needUpdate = BooleanUtils.toIntegerObject(BaseUtils.isOldVersion(this.version, BaseUtils.getAppversion()));
        return this;
    }
    
    public static String key(AppType app, ClientType client) {
        return app.name().toLowerCase() + "." + client.name().toLowerCase();
    }
    
    public static String key(int app, int client) {
        return key(AppType.convert(app), ClientType.convert(client));
    }
    
    public static String key(String app, String client) {
        return key(Integer.parseInt(app), Integer.parseInt(client));
    }
    
}