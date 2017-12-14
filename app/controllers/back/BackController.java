package controllers.back;

import controllers.BaseController;
import models.back.Admin;
import play.Logger;
import play.cache.Cache;
import play.mvc.Before;
import play.mvc.Http.Request;
import vos.back.ApiVO;
import vos.Result;
import vos.Result.StatusCode;

public class BackController extends BaseController {
    
    @Before(priority = 100, unless = {"back.Application.index", "back.Application.login"})
    static void access() {
        Logger.info("[access start]:================");
        final Request request = Request.current();
        final Admin admin = getCurrPerson();
        if (admin == null) {
            if (request.isAjax()) {
                renderJSON(Result.failed(StatusCode.SYSTEM_ACCESS_FOBIDDEN));
            }
            Application.index();
        }
        ApiVO apiVO = (ApiVO) Cache.get(request.hashCode() + "");
        apiVO.personId = admin.id;
        Cache.replace(request.hashCode() + "", apiVO);
        Logger.info("[access admin]:%s", admin.id);
        Logger.info("[access end]:================");
    }
    
}
