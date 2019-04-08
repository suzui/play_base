package vos;

import annotations.DataField;

import java.util.List;

public class StatisticsCatalogVO extends OneData {
    
    @DataField(name = "名称")
    public String name;
    @DataField(name = "数量")
    public Integer amount;
    @DataField(name = "总数")
    public Integer total;
    @DataField(name = "列表")
    public List<StatisticsItemVO> items;
    
    
    public StatisticsCatalogVO() {
        this.clean();
    }
    
    public StatisticsCatalogVO(String name, List<StatisticsItemVO> items) {
        this.clean();
        this.name = name;
        this.amount = items.size();
    }
    
    
}
