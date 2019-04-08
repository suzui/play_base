package vos;

import annotations.DataField;

public class StatisticsItemVO extends OneData {
    
    @DataField(name = "名称")
    public String name;
    @DataField(name = "数量")
    public Integer amount;
    @DataField(name = "总数")
    public Integer total;
    
    public StatisticsItemVO() {
        this.clean();
    }
    
    public StatisticsItemVO(String name, Integer amount) {
        this.clean();
        this.name = name;
        this.amount = amount;
    }
    
    public StatisticsItemVO(String name, Integer amount, Integer total) {
        this.clean();
        this.name = name;
        this.amount = amount;
        this.total = total;
    }
    
    
}
