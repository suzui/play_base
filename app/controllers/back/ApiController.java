package controllers.back;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;
import models.back.Api;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;
import play.libs.WS;
import utils.BaseUtils;
import utils.DateUtils;
import vos.PageData;
import vos.Result;
import vos.back.ApiVO;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ApiController extends BackController {
    
    public static void list(ApiVO vo) {
        List<Api> apis = Api.fetch(vo);
        List<ApiVO> apiVOs = apis.stream().map(a -> new ApiVO(a)).collect(Collectors.toList());
        renderJSON(Result.succeed(new PageData(apiVOs)));
    }
    
    public static void info(ApiVO vo) {
        Api api = Api.findByID(vo.apiId);
        ApiVO apiVO = new ApiVO(api);
        apiVO.complete(api);
        apiVO.apiId = vo.apiId;
        renderJSON(Result.succeed(apiVO));
    }
    
    public static void mock(ApiVO vo) {
        Api api = Api.findByID(vo.apiId);
        Map<String, String> param = new Gson().fromJson(StringUtils.isBlank(vo.param) ? api.param : vo.param, Map.class);
        if (param != null) param.remove("body");
        Map<String, LinkedTreeMap> header = new Gson().fromJson(StringUtils.isBlank(vo.header) ? api.header : vo.header, Map.class);
        WS.WSRequest request = WS.url(BaseUtils.BASE_URL + api.url.split("\\?")[0]);
        request.setHeader("mock", "");
        for (LinkedTreeMap map : header.values()) {
            request.setHeader((String) map.get("name"), ((ArrayList<String>) map.get("values")).get(0));
        }
        request.setHeader("randomseed", RandomStringUtils.randomAlphanumeric(16));
        request.setParameters(param).post();
        renderJSON(Result.succeed());
    }
    
    public static void page(ApiVO vo) {
        if (vo.startTime == null) {
            vo.startTime = System.currentTimeMillis() + DateUtils.DAY - 3;
        }
        if (vo.endTime == null) {
            vo.endTime = System.currentTimeMillis() + DateUtils.DAY;
        }
        List<Api> apis = Api.fetch(vo);
        List<ApiVO> apiVOs = apis.stream().map(a -> new ApiVO(a)).collect(Collectors.toList());
        Map map = new HashMap();
        map.put("code", 0);
        map.put("msg", "");
        map.put("count", Api.count(vo));
        map.put("data", apiVOs);
        String result = "";
        try {
            result = Result.mapper.writeValueAsString(map);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        renderJSON(result);
    }
    
}
