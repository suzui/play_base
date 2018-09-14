package vos;

import annotations.DataField;

public class StringVO extends OneData {
    
    @DataField(name = "值")
    public String value;
    
    public StringVO() {
    
    }
    
    public StringVO(String value) {
        this.clean();
        this.value = value;
    }
    
}