package results.sso;

import annotations.DataField;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class AppsResult extends Result {
    
    public AppsData data;
    
    public class AppsData {
        @DataField(name = "分页页码")
        public Integer page;
        @DataField(name = "每页数量")
        public Integer size;
        @DataField(name = "总页数")
        public Integer totalPage;
        @DataField(name = "总条数")
        public Integer totalSize;
        
        @DataField(name = "数组")
        public List<AppResult.AppData> array;
        
        public AppsData() {
        }
    }
    
    public AppsResult() {
    }
    
    
}
