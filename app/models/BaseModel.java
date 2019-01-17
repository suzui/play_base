package models;

import exceptions.ResultException;
import listeners.BaseModelListener;
import models.token.BasePerson;
import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.StringUtils;
import play.Logger;
import play.db.jpa.Model;
import utils.BaseUtils;

import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import javax.persistence.Version;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@MappedSuperclass
@EntityListeners(BaseModelListener.class)
public class BaseModel extends Model {
    
    @Version
    public long version;
    
    public boolean deleted = false;
    
    public long createTime = System.currentTimeMillis();
    
    public long updateTime = System.currentTimeMillis();
    
    public static final String STRING = "varchar(255) comment ";
    public static final String STRING_1000 = "varchar(1000) comment ";
    public static final String STRING_2000 = "varchar(2000) comment ";
    public static final String STRING_3000 = "varchar(3000) comment ";
    public static final String STRING_4000 = "varchar(4000) comment ";
    public static final String STRING_5000 = "varchar(5000) comment ";
    public static final String STRING_TEXT = "longtext comment ";
    public static final String LONG = "bigint comment ";
    public static final String INTEGER = "int comment ";
    public static final String FLOAT = "float comment ";
    public static final String DOUBLE = "double comment ";
    public static final String BOOLEAN = "tinyint(1) comment ";
    
    private static final String AND = " and ";
    private static final String FROM = " from ";
    private static final String WHERE = " where ";
    private static final String FROM_WHERE_PATTERN = "from\\s([\\S].*?)\\swhere\\s";
    
    private static String defaultCondition() {
        return "deleted=false";
    }
    
    public static String defaultSql() {
        return defaultCondition();
    }
    
    public static String defaultSql(String originStr) {
        String originSql = originStr;
        if (StringUtils.containsIgnoreCase(originSql, FROM)) {
            if (StringUtils.containsIgnoreCase(originSql, WHERE)) {
                Pattern pattern = Pattern.compile(FROM_WHERE_PATTERN, Pattern.CASE_INSENSITIVE);
                Matcher matcher = pattern.matcher(originSql);
                while (matcher.find()) {
                    String tableName = matcher.group(1);
                    String string = tableName.contains(" ") ? tableName.substring(tableName.lastIndexOf(' ') + 1) + '.'
                            : "";
                    String newSqlString = string + defaultCondition() + AND;
                    String originString = matcher.group();
                    originSql = originSql.replace(originString, originString + newSqlString);
                }
            } else {
                originSql = originSql + WHERE + defaultCondition();
            }
        } else {
            originSql = defaultCondition() + (StringUtils.isBlank(originSql) ? "" : AND) + originSql;
        }
        if (BaseUtils.property("jpa.debugSQL").equals("true")) {
            Logger.info("sql:%s", originSql);
        }
        return originSql;
    }
    
    public <T extends BaseModel> T _id(Long id) {
        this.id = id;
        return (T) this;
    }
    
    public boolean isUpdate(long version) {
        return this.version > version;
    }
    
    public Date createTime() {
        return new Date(this.createTime);
    }
    
    public Date updateTime() {
        return new Date(this.updateTime);
    }
    
    public void timeUpdate() {
        this.updateTime = System.currentTimeMillis();
        this.save();
    }
    
    public void unique(BaseModel model, Object[] codemessage) {
        if (model != null && !model.id.equals(this.id)) {
            throw new ResultException(codemessage);
        }
    }
    
    public void logicDelete() {
        this.deleted = true;
        this.save();
    }
    
    public static Boolean convert(int b) {
        return BooleanUtils.toBooleanObject(b, 1, 0, -1);
    }
    
    public static Boolean withNull(long v) {
        return v == -1l;
    }
    
    public static Boolean withNotNull(long v) {
        return v == -2l;
    }
    
    public static Long getApp() {
        return BaseUtils.getApp();
    }
    
    public static Long getRoot() {
        return BaseUtils.getRoot();
    }
    
    public static Long getOrganize() {
        return BaseUtils.getOrganize();
    }
    
    public static Long getSource() {
        Long source = BaseUtils.getSource();
        if (source == null) {
            source = BaseUtils.getOrganize();
        }
        return source;
    }
    
    public static <T extends BasePerson> T getPersonByToken() {
        return BaseUtils.getPersonByToken();
    }
    
    public static String getSession(String key) {
        return BaseUtils.getSession(key);
    }
    
    public static String getCookie(String key) {
        return BaseUtils.getCookie(key);
    }
    
    public static String getHeader(String key) {
        return BaseUtils.getHeader(key);
    }
    
}
