package results.sso;

import annotations.DataField;

public class AppResult extends Result {
    
    public AppData data;
    
    public class AppData {
    
        @DataField(name = "应用id")
        public Long appId;
        @DataField(name = "应用名称")
        public String name;
        
        @DataField(name = "master")
        public String master;
        @DataField(name = "secret")
        public String secret;
        
        @DataField(name = "所属源id")
        public Long sourceId;
        
        public AppData() {
        }
        
    }
    
    public AppResult() {
    }
    
}