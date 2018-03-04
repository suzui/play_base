package vos;

import annotations.DataField;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import models.BaseModel;
import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.StringUtils;
import play.Logger;
import play.i18n.Lang;

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
    @DataField(name = "查询条件")
    public String condition;
    
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
    
    public static String convert(String en, String zh) {
        return StringUtils.equals(Lang.get(), "en") ? en : zh;
    }
    
    public void condition(String condition) {
        this.condition = " " + condition + " ";
    }
    
    public void clean() {
        this.page = null;
        this.size = null;
        this.condition = null;
    }
    
    public Map<Object, Object> doc() {
        Map<Object, Object> map = new LinkedHashMap<>();
        try {
            for (Field f : this.getClass().getDeclaredFields()) {
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
                    Type genericType = f.getGenericType();
                    List<Object> list = new ArrayList<>();
                    ParameterizedType pt = (ParameterizedType) genericType;
                    Type one = pt.getActualTypeArguments()[0];
                    if (!OneData.class.isAssignableFrom((Class<?>) one)) {
                        list.add(((Class<?>) one).getSimpleName());
                    } else if (this.getClass().isAssignableFrom((Class<?>) one)) {
                        list.add(new HashMap<>());
                    } else {
                        list.add(((Class<OneData>) one).newInstance().doc());
                    }
                    map.put(f.getName(), list);
                } else if (this.getClass().isAssignableFrom((Class<?>) type)) {
                    map.put(f.getName(), new HashMap<>());
                } else if (OneData.class.isAssignableFrom((Class<?>) type)) {
                    map.put(f.getName(), ((Class<OneData>) type).newInstance().doc());
                } else {
                    map.put(f.getName(), StringUtils.join(Arrays.asList(df.name(), f.getType().getSimpleName(), df.demo(), df.comment()), "|").replace("||", ""));
                }
            }
        } catch (Exception e) {
            Logger.info("[datadoc]:%s", e.getMessage());
        }
        return map;
    }
    
}
