package results.sso;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Result {
    
    public String status;
    
    public int code;
    
    public String message;
    
    public long systemTime;
    
    public Result() {
    
    }
    
}
