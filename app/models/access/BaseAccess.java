package models.access;

import models.BaseModel;
import org.apache.commons.lang.StringUtils;
import play.Play;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Collections;
import java.util.List;

@Entity
@Table(name = "Access")
public class BaseAccess extends BaseModel {
    
    public String code;
    
    public String name;
    
    public String intro;
    
    @Column(length = 1000)
    public String routes;
    
    public static BaseAccess add(String code, String name, String intro) {
        BaseAccess access = new BaseAccess();
        access.code = code;
        access.name = name;
        access.intro = intro;
        return access.save();
    }
    
    public static BaseAccess add(String code, String name) {
        return add(code, name, null);
    }
    
    public static void init() {
        for (int i = 1; i < 100; i++) {
            String access = Play.configuration.getProperty("access." + i);
            if (StringUtils.isBlank(access)) {
                break;
            }
            String[] array = StringUtils.split(access, ",");
            if (findByCode(array[0]) != null) {
                continue;
            }
            if (array.length == 2) {
                add(array[0], array[1]);
            } else if (array.length == 3) {
                add(array[0], array[1], array[2]);
            }
        }
    }
    
    public String parentCode() {
        return this.code.length() > 3 ? this.code.substring(0, 3) : null;
    }
    
    public void del() {
        this.logicDelete();
    }
    
    public static <T extends BaseAccess> T findByID(Long id) {
        return BaseAccess.find(defaultSql("id =?"), id).first();
    }
    
    public static <T extends BaseAccess> T findByCode(String code) {
        return BaseAccess.find(defaultSql("code =?"), code).first();
    }
    
    public static List<BaseAccess> fetchByIds(List<Long> ids) {
        if (ids == null || ids.isEmpty()) {
            return Collections.EMPTY_LIST;
        }
        return BaseAccess.find(defaultSql("id in (:ids)")).bind("ids", ids.toArray()).fetch();
    }
    
    public static List<BaseAccess> fetchAll() {
        return BaseAccess.find(defaultSql()).fetch();
    }
}
