package vos;

import annotations.DataField;

import java.io.Serializable;

public class AccessTokenVO extends OneData implements Serializable{
    
    @DataField(name = "token", enable = false)
    public String accesstoken;
    
    public AccessTokenVO() {
    }
    
    public AccessTokenVO(String accesstoken) {
        this.accesstoken = accesstoken;
    }
    
}
