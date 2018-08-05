package models.back;

import annotations.ConfigField;
import models.BaseModel;
import org.apache.commons.lang.StringUtils;
import utils.ConfigUtils;
import vos.back.ConfigVO;

import javax.persistence.Column;
import javax.persistence.Entity;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Entity
public class Config extends BaseModel {
    
    public String name;
    public String value;
    @Column(length = 1000)
    public String intro;
    
    public static Config add(ConfigVO configVO) {
        Config config = new Config();
        config.name = configVO.name;
        config.edit(configVO);
        return config;
    }
    
    public void edit(ConfigVO configVO) {
        this.value = configVO.value != null ? configVO.value : value;
        this.intro = configVO.intro != null ? configVO.intro : intro;
        this.save();
    }
    
    public static void init() {
        for (Field f : ConfigUtils.class.getFields()) {
            if (findByName(f.getName()) == null) {
                ConfigVO configVO = new ConfigVO();
                configVO.name = f.getName();
                ConfigField cf = f.getAnnotation(ConfigField.class);
                configVO.value = cf.value();
                configVO.intro = cf.intro();
                add(configVO);
            }
        }
    }
    
    public void del() {
        this.logicDelete();
    }
    
    public static Config findByID(Long id) {
        return Config.find(defaultSql("id=?"), id).first();
    }
    
    public static Config findByName(String name) {
        return Config.find(defaultSql("name=?"), name).first();
    }
    
    public static List<Config> fetchByIds(List<Long> ids) {
        if (ids == null || ids.isEmpty()) {
            return Collections.EMPTY_LIST;
        }
        return Config.find(defaultSql("id in (:ids)")).bind("ids", ids.toArray()).fetch();
    }
    
    public static List<Config> fetchAll() {
        return Config.find(defaultSql()).fetch();
    }
    
    public static List<Config> fetch(ConfigVO configVO) {
        Object[] data = data(configVO);
        List<String> hqls = (List<String>) data[0];
        List<Object> params = (List<Object>) data[1];
        return Config.find(defaultSql(StringUtils.join(hqls, " and ")) + configVO.condition, params.toArray())
                .fetch(configVO.page, configVO.size);
    }
    
    public static int count(ConfigVO configVO) {
        Object[] data = data(configVO);
        List<String> hqls = (List<String>) data[0];
        List<Object> params = (List<Object>) data[1];
        return (int) Config.count(defaultSql(StringUtils.join(hqls, " and ")), params.toArray());
    }
    
    public static Object[] data(ConfigVO configVO) {
        List<String> hqls = new ArrayList<>();
        List<Object> params = new ArrayList<>();
        if (StringUtils.isNotBlank(configVO.search)) {
            hqls.add("concat_ws(',',name,intro) like ?");
            params.add("%" + configVO.search + "%");
        }
        return new Object[]{hqls, params};
    }
    
}
