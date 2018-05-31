package controllers.back;

import models.back.Api;
import vos.PageData;
import vos.Result;
import vos.back.ApiVO;

import java.util.List;
import java.util.stream.Collectors;

public class ApiController extends BackController {
    
    public static void list(ApiVO vo) {
        vo.size = 50;
        List<Api> apis = Api.fetch(vo);
        List<ApiVO> apiVOs = apis.stream().map(a -> new ApiVO(a)).collect(Collectors.toList());
        renderJSON(Result.succeed(new PageData(apiVOs)));
    }
    
}
