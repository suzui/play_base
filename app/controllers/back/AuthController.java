package controllers.back;

import enums.Access;
import models.back.Auth;
import vos.back.AuthVO;
import vos.PageData;
import vos.Result;

import java.util.List;
import java.util.stream.Collectors;

public class AuthController extends BackController {
    
    public static void list(AuthVO vo) {
        List<Auth> auths = Auth.fetch(vo);
        List<AuthVO> authVOs = auths.stream().map(a -> new AuthVO(a)).collect(Collectors.toList());
        renderJSON(Result.succeed(new PageData(authVOs)));
    }
    
    public static void add(AuthVO vo) {
        Auth.add(vo);
        renderJSON(Result.succeed());
    }
    
    public static void edit(AuthVO vo) {
        Auth auth = Auth.findByID(vo.authId);
        auth.edit(vo);
        renderJSON(Result.succeed());
    }
    
    public static void del(AuthVO vo) {
        Auth auth = Auth.findByID(vo.authId);
        auth.del();
        renderJSON(Result.succeed());
    }
    
    public static void dels(AuthVO vo) {
        List<Auth> auths = Auth.fetchByIds(vo.authIds);
        auths.forEach(a -> a.del());
        renderJSON(Result.succeed());
    }
    
}
