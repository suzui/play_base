package results.sso;

import annotations.DataField;
import models.sso.SsoOrganize;

public class OrganizeResult extends Result {
    
    public OrganizeData data;
    
    public static class OrganizeData {
        @DataField(name = "组织id")
        public Long organizeId;
        @DataField(name = "组织名称")
        public String name;
        @DataField(name = "组织logo")
        public String logo;
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
        
        public OrganizeData(SsoOrganize organize) {
            this.organizeId = organize.ssoId;
            this.name = organize.name;
            this.logo = organize.logo;
            if (organize.parent != null) {
                this.parentId = ((SsoOrganize) organize.parent).ssoId;
            }
            this.rank = organize.rank;
        }
    }
    
    public OrganizeResult() {
    }
    
    
}
