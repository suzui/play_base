package vos;

import annotations.DataField;

public class LongVO extends OneData {
    
    @DataField(name = "值")
    public Long value;
    
    public LongVO() {
    
    }
    
    public LongVO(Long value) {
        this.clean();
        this.value = value;
    }
    
}