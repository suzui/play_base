package results.sso;

import annotations.DataField;

import java.util.List;

public class OrganizesResult extends Result {
    
    public OrganizesData data;
    
    public class OrganizesData {
        @DataField(name = "分页页码")
        public Integer page;
        @DataField(name = "每页数量")
        public Integer size;
        @DataField(name = "总页数")
        public Integer totalPage;
        @DataField(name = "总条数")
        public Integer totalSize;
        
        @DataField(name = "数组")
        public List<OrganizeResult.OrganizeData> array;
        
        public OrganizesData() {
        }
    }
    
    public OrganizesResult() {
    }
    
    
}
