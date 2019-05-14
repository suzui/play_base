package models.util;

import models.BaseModel;
import javax.persistence.FetchType;
import utils.DateUtils;

import javax.persistence.Column;
import javax.persistence.Entity;
import java.util.List;

@Entity
public class Holiday extends BaseModel {
    
    @Column(columnDefinition = STRING + "'日期 yyyy-MM-dd'")
    public String date;
    @Column(columnDefinition = INTEGER + "'状态 1:休假 2:补班'")
    public Integer status;
    
    
    public static void add(String date, Integer status) {
        Holiday holiday = findByDate(date);
        if (holiday != null) {
            return;
        }
        holiday = new Holiday();
        holiday.date = date;
        holiday.status = status;
        holiday.save();
    }
    
    public boolean isHoliday() {
        return this.status == 1;
    }
    
    public boolean isWork() {
        return this.status == 2;
    }
    
    public static Holiday findByDate(Long date) {
        return Holiday.find(defaultSql("date=?"), DateUtils.format(date, "yyyy-MM-dd")).first();
    }
    
    public static Holiday findByDate(String date) {
        return Holiday.find(defaultSql("date=?"), date).first();
    }
    
    public static List<Holiday> fetchByStatus(Integer status) {
        return Holiday.find(defaultSql("status=?"), status).fetch();
    }
    
    public static List<Holiday> fetchAll() {
        return Holiday.find(defaultSql()).fetch();
    }
    
}
