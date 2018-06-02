package controllers.back;

import com.fasterxml.jackson.core.JsonProcessingException;
import groovy.util.Eval;
import models.back.Api;
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
    
    public static void page(ApiVO vo) {
        List<Api> apis = Api.fetch(vo);
        List<ApiVO> apiVOs = apis.stream().map(a -> new ApiVO(a)).collect(Collectors.toList());
        PageData pageData = new PageData(vo.page, vo.size, Api.count(vo), apiVOs);
        Map map = new HashMap();
        map.put("code", 0);
        map.put("msg", "");
        map.put("count", pageData.totalSize);
        map.put("data", pageData.array);
        String result = "";
        try {
            result = Result.mapper.writeValueAsString(map);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        renderJSON(result);
    }
    
}
