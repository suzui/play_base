package utils;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import models.util.Holiday;
import play.Logger;
import play.libs.WS;

import java.util.Date;

public class HolidayUtils {
    
    
    public static final String APPHOST = "https://v.juhe.cn/calendar/month";
    public static final String APPKEY = "d2ba4a77231241762e545260adf86031";
    
    
    public static void yearMonth(Date date) {
        WS.HttpResponse response = WS.url(APPHOST).setParameter("year-month", DateUtils.format(date, "yyyy-M"))
                .setParameter("key", APPKEY).get();
        Logger.info("[holiday response] %s", response.getString());
        JsonObject result = response.getJson().getAsJsonObject();
        if ("Success".equalsIgnoreCase(result.get("reason").getAsString())) {
            JsonArray holiday_array = result.get("result").getAsJsonObject().get("data").getAsJsonObject().get("holiday_array").getAsJsonArray();
            for (int i = 0; i < holiday_array.size(); i++) {
                JsonArray list = holiday_array.get(i).getAsJsonObject().get("list").getAsJsonArray();
                for (int j = 0; j < list.size(); j++) {
                    JsonObject day = list.get(j).getAsJsonObject();
                    Holiday.add(DateUtils.format(DateUtils.format(day.get("date").getAsString(), "yyyy-M-d"), "yyyy-MM-dd"), day.get("status").getAsInt());
                }
            }
        }
    }
    
    public static boolean isWork(Long date) {
        Holiday holiday = Holiday.findByDate(date);
        if (holiday != null) {
            return holiday.isWork();
        } else {
            return "1,2,3,4,5".contains(DateUtils.dayOfWeek(date) + "");
        }
    }
    
    public static boolean isRest(Long date) {
        Holiday holiday = Holiday.findByDate(date);
        if (holiday != null) {
            return holiday.isHoliday();
        } else {
            return "6,7".contains(DateUtils.dayOfWeek(date) + "");
        }
    }
    
    
}
