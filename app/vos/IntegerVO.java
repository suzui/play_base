package vos;

import annotations.DataField;

public class IntegerVO extends OneData {
    
    @DataField(name = "值")
    public Integer value;
    
    public IntegerVO() {
    
    }
    
    public IntegerVO(Integer value) {
        this.clean();
        this.value = value;
    }
    
}