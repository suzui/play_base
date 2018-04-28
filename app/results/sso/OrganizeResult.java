package results.sso;

import annotations.DataField;
import models.sso.SsoOrganize;
import models.sso.SsoPerson;

public class OrganizeResult extends Result {
    
    public OrganizeData data;
    
    public static class OrganizeData {
        @DataField(name = "组织id")
        public Long organizeId;
        @DataField(name = "组织名称")
        public String name;
        @DataField(name = "组织logo")
        public String logo;
        @DataField(name = "组织类型")
        public Integer type;
        @DataField(name = "上级组织id")
        public Long parentId;
        @DataField(name = "组织负责人id")
        public Long personId;
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
            if (organize.type != null) {
                this.type = organize.type.code();
            }
            if (organize.parent != null) {
                this.parentId = ((SsoOrganize) organize.parent).ssoId;
            }
            if (organize.person != null) {
                this.personId = ((SsoPerson) organize.person).ssoId;
            } else {
                this.personId = -1l;
            }
            this.rank = organize.rank;
        }
    }
    
    public OrganizeResult() {
    }
    
    
}
