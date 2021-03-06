package vos;

import annotations.DataField;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import models.BaseModel;
import javax.persistence.FetchType;
import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.StringUtils;
import play.Logger;
import play.i18n.Lang;
import utils.BaseUtils;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.*;

@JsonIgnoreProperties(ignoreUnknown = true)
public class OneData extends Data {
    
    @JsonProperty("id")
    @JsonInclude(Include.NON_NULL)
    public Long id;
    @JsonInclude(Include.NON_NULL)
    @DataField(name = "创建时间")
    public Long createTime;
    @JsonInclude(Include.NON_NULL)
    @DataField(name = "更新时间")
    public Long updateTime;
    @JsonInclude(Include.NON_NULL)
    @DataField(name = "是否删除")
    public Integer deleted;
    @JsonInclude(Include.NON_NULL)
    @DataField(name = "模糊搜索")
    public String search;
    @JsonInclude(Include.NON_NULL)
    @DataField(name = "起始时间")
    public Long fromTime;
    @JsonInclude(Include.NON_NULL)
    @DataField(name = "结束时间")
    public Long toTime;
    @JsonInclude(Include.NON_NULL)
    @DataField(name = "分页页码")
    public Integer page;
    @JsonInclude(Include.NON_NULL)
    @DataField(name = "每页数量")
    public Integer size;
    @JsonInclude(Include.NON_NULL)
    @DataField(name = "序号")
    public Integer sequence;
    @JsonInclude(Include.NON_NULL)
    @DataField(name = "查询条件")
    public String condition;
    @JsonInclude(Include.NON_NULL)
    @DataField(name = "校验状态", comment = "不传走校验逻辑 1通过 0不通过")
    public Integer validation;
    
    public OneData() {
        this.page = 1;
        this.size = Integer.MAX_VALUE;
        this.condition = " order by id ";
    }
    
    public OneData(long id) {
        this.id = id;
    }
    
    public OneData(long id, long createTime, long updateTime, boolean deleted) {
        this.id = id;
        this.createTime = createTime;
        this.updateTime = updateTime;
        this.deleted = BooleanUtils.toIntegerObject(deleted);
    }
    
    public OneData(BaseModel baseModel) {
        this(baseModel.id, baseModel.createTime, baseModel.updateTime, baseModel.deleted);
    }
    
    public void id(long id) {
        this.id = id;
    }
    
    public Integer page() {
        return this.page != null ? this.page : 1;
    }
    
    public Integer size() {
        return this.size != null ? this.size : Integer.MAX_VALUE;
    }
    
    public static String convert(String en, String zh) {
        return StringUtils.equals(Lang.get(), "en") ? en : zh;
    }
    
    public static List<Long> convert(String ids) {
        return BaseUtils.idsToList(ids);
    }
    
    public void condition(String condition) {
        this.condition = " " + condition + " ";
    }
    
    public boolean validation() {
        return this.validation != null && this.validation > 0;
    }
    
    public boolean unValidation() {
        return !validation();
    }
    
    public void clean() {
        this.page = null;
        this.size = null;
        this.condition = null;
    }
    
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + this.getClass().hashCode();
        result = prime * result + this.id.hashCode();
        return result;
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (null == obj) {
            return false;
        }
        if (this.getClass() != obj.getClass()) {
            return false;
        }
        OneData one = (OneData) obj;
        if (this.id.equals(one.id)) {
            return true;
        }
        return false;
    }
    
    public Map<Object, Object> doc() {
        Map<Object, Object> map = new LinkedHashMap<>();
        try {
            for (Field f : this.getClass().getFields()) {
                DataField df = f.getAnnotation(DataField.class);
                if (df == null) {
                    continue;
                }
                JsonInclude ji = f.getAnnotation(JsonInclude.class);
                if (ji != null && ji.value() == Include.NON_NULL) {
                    continue;
                }
                Type type = f.getType();
                if (List.class.isAssignableFrom((Class<?>) type)) {
                    //List类型
                    Type genericType = f.getGenericType();
                    List list = new ArrayList();
                    list.add(StringUtils.join(Arrays.asList("//" + df.name(), f.getType().getSimpleName(), df.comment()), "|"));
                    ParameterizedType pt = (ParameterizedType) genericType;
                    Type one = pt.getActualTypeArguments()[0];//List内对象type
                    if (this.getClass().isAssignableFrom((Class<?>) one)) {
                        //当前data及子类
                        list.add(new HashMap<>());
                    } else if (OneData.class.isAssignableFrom((Class<?>) one)) {
                        //与当前data无关的onedata及子类
                        list.add(((Class<OneData>) one).newInstance().doc());
                    } else {
                        //非onedata及子类的其它类型
                        list.add(StringUtils.join(Arrays.asList(df.name().replace("s", "").replace("列表", "").replace("数组", "").replace("集合", ""), ((Class<?>) one).getSimpleName()), "|"));
                    }
                    map.put(f.getName(), list);
                } else if (this.getClass().isAssignableFrom((Class<?>) type)) {
                    //当前data及子类
                    map.put(f.getName(), new HashMap<>());
                } else if (OneData.class.isAssignableFrom((Class<?>) type)) {
                    //与当前data无关的onedata及子类
                    map.put(f.getName(), ((Class<OneData>) type).newInstance().doc());
                } else {
                    //非onedata及子类的其它类型
                    String enums = (df.enums().length > 0 ? BaseUtils.toJson(BaseUtils.enums(df.enums()[0])).replaceAll("\\\"", "") : "");
                    map.put(f.getName(), StringUtils.join(Arrays.asList(df.name(), f.getType().getSimpleName(), df.demo(), df.comment(), enums), "|").replace("||", ""));
                }
            }
        } catch (Exception e) {
            Logger.info("[datadoc]:%s", e.getMessage());
        }
        return map;
    }
    
    
}
