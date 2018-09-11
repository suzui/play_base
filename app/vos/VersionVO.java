package vos;

import annotations.DataField;
import enums.AppType;
import enums.ClientType;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.BooleanUtils;
import play.Logger;
import play.Play;
import utils.BaseUtils;

import java.io.IOException;
import java.io.Serializable;

public class VersionVO extends OneData implements Serializable {
    
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
    
    public VersionVO(AppType app, ClientType client) {
        this.clean();
        try {
            for (String line : FileUtils.readLines(
                    Play.getFile("/documentation/version/" + "VERSION_" + app.name() + "_" + client.name()), "utf8")) {
                String key = line.substring(0, line.indexOf(":"));
                String value = line.substring(line.indexOf(":") + 1, line.length());
                if ("version".equals(key)) {
                    this.version = value;
                }
                if ("downloadUrl".equals(key)) {
                    this.downloadUrl = value;
                }
                if ("updateIntro".equals(key)) {
                    this.updateIntro = value;
                }
                if ("isForcedUpdate".equals(key)) {
                    this.isForcedUpdate = Integer.parseInt(value);
                }
            }
        } catch (IOException e) {
            Logger.error("versionerror:%s", e.getMessage());
        }
    }
    
    public boolean isForcedUpdate() {
        return BooleanUtils.toBoolean(this.isForcedUpdate);
    }
    
    public VersionVO needUpdate() {
        this.needUpdate = BooleanUtils.toIntegerObject(BaseUtils.isOldVersion(this.version, BaseUtils.getAppversion()));
        return this;
    }
    
    public static String key(AppType app, ClientType client) {
        return key(app.code() + "", client.code() + "");
    }
    
    public static String key(String app, String client) {
        return "version_" + app + "_" + client;
    }
    
}