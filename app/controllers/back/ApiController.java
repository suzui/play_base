package controllers.back;

import com.fasterxml.jackson.core.JsonProcessingException;
import models.back.Api;
import play.libs.WS;
import utils.BaseUtils;
import vos.PageData;
import vos.Result;
import vos.back.ApiVO;

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
        renderJSON(Result.succeed(apiVO));
    }
    
    public static void mock(ApiVO vo) {
        Api api = Api.findByID(vo.apiId);
        WS.url(BaseUtils.BASE_URL + api.url).post();
        renderJSON(Result.succeed());
    }
    
    public static void page(ApiVO vo) {
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
