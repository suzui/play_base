package results.sso;

import annotations.DataField;

public class RelationResult extends Result {
    
    public RelationData data;
    
    public class RelationData {
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
    }
    
    public RelationResult() {
    }
    
}
