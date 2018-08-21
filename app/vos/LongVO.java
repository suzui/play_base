package vos;

import annotations.DataField;

import java.io.Serializable;

public class LongVO extends OneData implements Serializable {
    
    @DataField(name = "值")
    public Long value;
    
    public LongVO() {
    
    }
    
    public LongVO(Long value) {
        super(0);
        this.value = value;
    }
    
}