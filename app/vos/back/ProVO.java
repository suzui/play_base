package vos.back;

import annotations.DataField;
import models.back.Api;
import models.back.Pro;
import vos.OneData;

import java.io.Serializable;

public class ProVO extends OneData implements Serializable {
    
    @DataField(name = "项目id")
    public Long proId;
    @DataField(name = "名称")
    public String name;
    @DataField(name = "位置路径")
    public String location;
    @DataField(name = "git地址")
    public String git;
    @DataField(name = "git分支")
    public String branch;
    @DataField(name = "shell脚本")
    public String shell;
    @DataField(name = "网址")
    public String url;
    
    public ProVO() {
    
    }
    
    public ProVO(Pro pro) {
        this.proId = pro.id;
        this.name = pro.name;
        this.location = pro.location;
        this.git = pro.git;
        this.branch = pro.branch;
        this.shell = pro.shell;
        this.url = pro.url;
    }
    
    
}
