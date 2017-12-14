package controllers.back;

import models.back.Admin;
import models.back.Auth;
import models.back.AuthAdmin;
import vos.PageData;
import vos.Result;
import vos.back.AdminVO;
import vos.back.AuthVO;

import java.util.List;
import java.util.stream.Collectors;

public class AdminController extends BackController {
    
    public static void list(AdminVO vo) {
        List<Admin> admins = Admin.fetch(vo);
        List<AdminVO> adminVOs = admins.stream().map(a -> new AdminVO(a)).collect(Collectors.toList());
        renderJSON(Result.succeed(new PageData(adminVOs)));
    }
    
    public static void add(AdminVO vo) {
        if (!Admin.isUsernameAvailable(vo.username)) {
            renderJSON(Result.failed());
        }
        Admin.add(vo);
        renderJSON(Result.succeed());
    }
    
    public static void edit(AdminVO vo) {
        Admin admin = Admin.findByID(vo.adminId);
        admin.edit(vo);
        renderJSON(Result.succeed());
    }
    
    public static void del(AdminVO vo) {
        Admin admin = Admin.findByID(vo.adminId);
        admin.del();
        renderJSON(Result.succeed());
    }
    
    public static void dels(AdminVO vo) {
        List<Admin> admins = Admin.fetchByIds(vo.adminIds);
        admins.forEach(a -> a.del());
        renderJSON(Result.succeed());
    }
    
    public static void auths(AdminVO vo) {
        Admin admin = Admin.findByID(vo.adminId);
        List<Auth> auths = Auth.fetchAll();
        List<Auth> adminAuths = AuthAdmin.fetchAuthByAdmin(admin);
        List<AuthVO> authVOs = auths.stream().map(a -> new AuthVO(a).flag( adminAuths.contains(a)))
                .collect(Collectors.toList());
        renderJSON(Result.succeed(new PageData(authVOs)));
    }
    
    public static void auth(AdminVO vo) {
        Admin admin = Admin.findByID(vo.adminId);
        List<Auth> auths = Auth.fetchByIds(vo.authIds);
        auths.forEach(a -> AuthAdmin.add(a, admin));
        renderJSON(Result.succeed());
    }
    
    public static void password(AdminVO vo) {
        Admin admin = Admin.findByID(vo.adminId);
        admin.edit(vo);
        renderJSON(Result.succeed());
    }
    
}
