package results.sso;

import annotations.DataField;
import models.sso.SsoOrganize;
import models.sso.SsoPerson;
import models.sso.SsoRelation;

public class RelationResult extends Result {
    
    public RelationData data;
    
    public static class RelationData {
        @DataField(name = "关系id")
        public Long relationId;
        @DataField(name = "用户id")
        public Long personId;
        @DataField(name = "组织id")
        public Long organizeId;
        @DataField(name = "排序")
        public Double rank;
        
        @DataField(name = "更新时间")
        public Long updateTime;
        @DataField(name = "是否删除")
        public Integer deleted;
        
        public RelationData() {
        }
        
        public RelationData(SsoRelation relation) {
            this.relationId = relation.ssoId;
            this.personId = ((SsoPerson) relation.person).ssoId;
            this.organizeId = ((SsoOrganize) relation.organize).ssoId;
            this.rank = relation.rank;
        }
    }
    
    public RelationResult() {
    }
    
}
