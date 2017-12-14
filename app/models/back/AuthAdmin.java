package models.back;

import models.BaseModel;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import java.util.List;

@Entity
public class AuthAdmin extends BaseModel {

	@ManyToOne
	public Auth auth;
	@ManyToOne
	public Admin admin;

	public static AuthAdmin add(Auth auth, Admin admin) {
		AuthAdmin authAdmin = findByAuthAndAdmin(auth, admin);
		if (authAdmin != null) {
			return authAdmin;
		}
		authAdmin = new AuthAdmin();
		authAdmin.auth = auth;
		authAdmin.admin = admin;
		return authAdmin.save();
	}

	public void del() {
		this.logicDelete();
	}

	public static void delByAuth(Auth auth) {
		fetchByAuth(auth).forEach(ap -> ap.del());
	}

	public static void delByAdmin(Admin admin) {
		fetchByAdmin(admin).forEach(ap -> ap.del());
	}

	public static AuthAdmin findByAuthAndAdmin(Auth auth, Admin admin) {
		return AuthAdmin.find(defaultSql("auth=? and admin=?"), auth, admin).first();
	}

	public static List<AuthAdmin> fetchByAuth(Auth auth) {
		return AuthAdmin.find(defaultSql("auth=?"), auth).fetch();
	}

	public static List<AuthAdmin> fetchByAdmin(Admin admin) {
		return AuthAdmin.find(defaultSql("admin=?"), admin).fetch();
	}

	public static List<Auth> fetchAuthByAdmin(Admin admin) {
		return AuthAdmin.find(defaultSql("select aa.auth from AuthAdmin aa where aa.admin=?"), admin).fetch();
	}

}
