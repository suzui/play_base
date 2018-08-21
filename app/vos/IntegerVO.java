package vos;

import annotations.DataField;

import java.io.Serializable;

public class IntegerVO extends OneData implements Serializable {
    
    @DataField(name = "值")
    public Integer value;
    
    public IntegerVO() {
    
    }
    
    public IntegerVO(Integer value) {
        super(0);
        this.value = value;
    }
    
}