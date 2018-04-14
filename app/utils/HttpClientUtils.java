package utils;

import com.ning.http.client.AsyncHttpClient;
import com.ning.http.client.FluentStringsMap;
import com.ning.http.client.Response;
import play.Logger;

import java.util.Map;
import java.util.Map.Entry;

public class HttpClientUtils {
    
    public static String getMethod(String url, Map<String, String> param) {
        final AsyncHttpClient client = new AsyncHttpClient();
        try {
            FluentStringsMap map = new FluentStringsMap();
            if (param != null) {
                for (Entry<String, String> e : param.entrySet()) {
                    map.add(e.getKey(), e.getValue());
                }
            }
            Response response = client.prepareGet(url).setQueryParams(map).setRequestTimeout(5 * 60 * 1000).execute().get();
            String responseBody = response.getResponseBody("utf8");
            Logger.info("[httprepsonse get]:%s", responseBody);
            return responseBody;
        } catch (Exception e) {
            Logger.error(e, e.getMessage());
        } finally {
            client.close();
        }
        return null;
    }
    
    public static String postMethod(String url, Map<String, String> param) {
        final AsyncHttpClient client = new AsyncHttpClient();
        try {
            FluentStringsMap map = new FluentStringsMap();
            if (param != null) {
                for (Entry<String, String> e : param.entrySet()) {
                    map.add(e.getKey(), e.getValue());
                }
            }
            Response response = client.preparePost(url).setQueryParams(map).setRequestTimeout(5 * 60 * 1000).execute().get();
            String responseBody = response.getResponseBody("utf8");
            Logger.info("[httprepsonse post]:%s", responseBody);
            return responseBody;
        } catch (Exception e) {
            Logger.error(e, e.getMessage());
        } finally {
            client.close();
        }
        return null;
    }
    
    
}
