package controllers.back;

import binders.PasswordBinder;
import enums.Access;
import models.back.Admin;
import models.back.AuthAdmin;
import play.data.binding.As;
import vos.Result;
import vos.Result.StatusCode;
import vos.back.AccessVO;
import vos.back.AdminVO;

import java.util.List;

public class Application extends BackController {
    
    public static void index() {
        final Admin admin = getCurrPerson();
        if (admin != null) {
            home();
        }
        render();
    }
    
    public static void login(String username, @As(binder = PasswordBinder.class) String password) {
        Admin admin = Admin.findByUsername(username);
        if (admin == null) {
            renderJSON(Result.failed(StatusCode.PERSON_ACCOUNT_NOTEXIST));
        }
        if (!admin.isPasswordRight(password)) {
            renderJSON(Result.failed(StatusCode.PERSON_PASSWORD_ERROR));
        }
        setPersonIdToSession(admin.id);
        renderJSON(Result.succeed());
    }
    
    public static void logout() {
        removePersonIdToSession();
        index();
    }
    
    public static void home() {
        Admin currAdmin = getCurrPerson();
        AdminVO admin = new AdminVO(currAdmin).codes(AuthAdmin.fetchAuthByAdmin(currAdmin));
        List<AccessVO> access = AccessVO.init();
        render(admin, access);
    }
}