package vos;

//@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "@class")
public class Data {
    
    public Data() {
    
    }
    
    // vo内属性定义
    // @JsonProperty("") 序列化对应字段
    // @JsonInclude(Include.NON_NULL) 该字段不输出 仅用于接收参数
    // @DataField(name="说明",demo="示例",comment="备注",enable=true(enable默认为true,false则该字段不用于接收参数 仅用于输出))
    // 与父类中定义重复 transient 单独申明
    
    // 文档生成规则
    // @ActionMethod(name="接口名",param="接收参数",except="排除参数",required="必要参数",clazz={返回VO})
    // 参数定义两种形式
    // 1.param="p1,+p2,-p3" 从自身及父类定义中取参数 不带前缀或者+为必填,-为非必填
    // 2.except="p1",required="p2" 仅从自身定义中取参数 取enable为true,且不在except内,另外在required中则为必填
    // clazz返回两种形式
    // 1.列表返回 clazz={PageData.class,OneData.class}
    // 2.单个返回 clazz=OneData.class
    // 返回内容包含vo自身内所有为标记为不输出的字段
    
}
