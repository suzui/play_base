package vos.back;

import annotations.DataField;
import models.back.Config;
import vos.OneData;

import java.io.Serializable;

public class ConfigVO extends OneData {
    
    @DataField(name = "系统参数id")
    public Long configId;
    @DataField(name = "名称")
    public String name;
    @DataField(name = "属性")
    public String value;
    @DataField(name = "描述")
    public String intro;
    
    public ConfigVO() {
    
    }
    
    public ConfigVO(Config config) {
        this.configId = config.id;
        this.name = config.name;
        this.value = config.value;
        this.intro = config.intro;
    }
    
    
}
