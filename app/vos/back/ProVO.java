package vos.back;

import annotations.DataField;
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
    @DataField(name = "状态")
    public Integer status;
    @DataField(name = "用户")
    public String user;
    @DataField(name = "密码")
    public String password;
    
    @DataField(name = "branchs")
    public String branchs;
    @DataField(name = "gitlog")
    public String gitlog;
    
    public ProVO() {
        this.condition = " order by name";
    }
    
    public ProVO(Pro pro) {
        super(pro.id);
        this.proId = pro.id;
        this.name = pro.name;
        this.location = pro.location;
        this.git = pro.git;
        this.branch = pro.branch;
        this.shell = pro.shell;
        this.url = pro.url;
        this.user = pro.user;
        this.password = pro.password;
        if (pro.status == null) {
            this.status = 0;
        } else {
            this.status = pro.status.code();
        }
    }
    
    
}
