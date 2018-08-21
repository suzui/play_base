package vos;

import annotations.DataField;

import java.io.Serializable;

public class StringVO extends OneData implements Serializable {
    
    @DataField(name = "值")
    public String value;
    
    public StringVO() {
    
    }
    
    public StringVO(String value) {
        super(0);
        this.value = value;
    }
    
}