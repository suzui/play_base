package results.sso;

import annotations.DataField;
import models.token.BaseOrganize;

public class OrganizeResult extends Result {
    
    public OrganizeData data;
    
    public class OrganizeData {
        @DataField(name = "组织id")
        public Long organizeId;
        @DataField(name = "组织名称")
        public String name;
        @DataField(name = "上级组织id")
        public Long parentId;
        @DataField(name = "排序")
        public Double rank;
        
        @DataField(name = "更新时间")
        public Long updateTime;
        @DataField(name = "是否删除")
        public Integer deleted;
        
        public OrganizeData() {
        
        }
        
        public OrganizeData(BaseOrganize organize) {
            this.organizeId = organize.ssoId;
            this.name = organize.name;
            this.parentId = organize.parent.ssoId;
            this.rank = organize.rank;
        }
    }
    
    public OrganizeResult() {
    }
    
    
}
