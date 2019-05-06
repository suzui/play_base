package models.back;

import enums.ProStatus;
import models.BaseModel;
import org.apache.commons.lang.StringUtils;
import play.Play;
import utils.BaseUtils;
import utils.ConfigUtils;
import utils.ShellUtils;
import vos.back.ProVO;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Entity
public class Pro extends BaseModel {
    
    public String name;
    public String location;
    public String port;
    public String git;
    public String branch;
    public String shell;
    public String url;
    public String user;
    public String password;
    public String playid;
    public String npmapp;
    
    @Enumerated(EnumType.STRING)
    public ProStatus status;
    
    
    public static Pro add(ProVO proVO) {
        Pro pro = new Pro();
        pro.edit(proVO);
        return pro;
    }
    
    public void edit(ProVO proVO) {
        this.name = proVO.name != null ? proVO.name : name;
        this.location = proVO.location != null ? proVO.location : location;
        this.port = proVO.port != null ? proVO.port : port;
        this.git = proVO.git != null ? proVO.git : git;
        this.branch = proVO.branch != null ? proVO.branch : branch;
        this.shell = proVO.shell != null ? proVO.shell : shell;
        this.url = proVO.url != null ? proVO.url : url;
        this.user = proVO.user != null ? proVO.user : user;
        this.password = proVO.password != null ? proVO.password : password;
        this.playid = proVO.playid != null ? proVO.playid : playid;
        this.npmapp = proVO.npmapp != null ? proVO.npmapp : npmapp;
        this.save();
    }
    
    public boolean isend() {
        return StringUtils.isNotBlank(this.playid);
    }
    
    public void status(ProStatus status) {
        if (!this.isend()) {
            return;
        }
        this.status = status;
        this.save();
    }
    
    public String user() {
        return StringUtils.isBlank(this.user) ? ConfigUtils.user : this.user;
    }
    
    public String password() {
        return StringUtils.isBlank(this.password) ? ConfigUtils.password : this.password;
    }
    
    public String playid() {
        return StringUtils.isBlank(this.playid) ? ConfigUtils.playid : this.playid;
    }
    
    public ShellUtils.Result gitbranch() {
        return ShellUtils.exec(Play.frameworkPath.getAbsolutePath() + "/modules/play_base/conf/shell/gitbranch.sh", this.user(), this.password(), this.location);
    }
    
    public ShellUtils.Result gitlog() {
        return ShellUtils.exec(Play.frameworkPath.getAbsolutePath() + "/modules/play_base/conf/shell/gitlog.sh", this.user(), this.password(), this.location);
    }
    
    public ShellUtils.Result update() {
        return ShellUtils.exec(Play.frameworkPath.getAbsolutePath() + "/modules/play_base/conf/shell/update.sh", this.user(), this.password(), this.location, this.branch);
    }
    
    public ShellUtils.Result stop() {
        return ShellUtils.exec(Play.frameworkPath.getAbsolutePath() + "/modules/play_base/conf/shell/stop.sh", this.user(), this.password(), this.location);
    }
    
    public ShellUtils.Result start() {
        return ShellUtils.exec(Play.frameworkPath.getAbsolutePath() + "/modules/play_base/conf/shell/start.sh", this.user(), this.password(), this.location, this.playid());
    }
    
    public ShellUtils.Result restart() {
        return ShellUtils.exec(Play.frameworkPath.getAbsolutePath() + "/modules/play_base/conf/shell/restart.sh", this.user(), this.password(), this.location, this.playid());
    }
    
    public ShellUtils.Result check() {
        return ShellUtils.exec(Play.frameworkPath.getAbsolutePath() + "/modules/play_base/conf/shell/check.sh", this.user(), this.password(), this.location);
    }
    
    public ShellUtils.Result webStart() {
        String mode = BaseUtils.isProd() ? "" : ":test";
        return ShellUtils.exec(Play.frameworkPath.getAbsolutePath() + "/modules/play_base/conf/shell/web/start.sh", this.user(), this.password(), this.location, this.npmapp, mode);
    }
    
    public ShellUtils.Result webStop() {
        return ShellUtils.exec(Play.frameworkPath.getAbsolutePath() + "/modules/play_base/conf/shell/web/stop.sh", this.user(), this.password(), this.location, this.npmapp);
    }
    
    public ShellUtils.Result webRestart() {
        String mode = BaseUtils.isProd() ? "" : ":test";
        return ShellUtils.exec(Play.frameworkPath.getAbsolutePath() + "/modules/play_base/conf/shell/web/restart.sh", this.user(), this.password(), this.location, this.npmapp, mode);
    }
    
    public ShellUtils.Result clean(String pattern) {
        return ShellUtils.exec(Play.frameworkPath.getAbsolutePath() + "/modules/play_base/conf/shell/clean.sh", this.user(), this.password(), this.location, pattern);
    }
    
    
    public static boolean canStart() {
        return Pro.find(defaultSql("status = ?"), ProStatus.START).fetch().isEmpty();
    }
    
    public void del() {
        this.logicDelete();
    }
    
    public static Pro findByID(Long id) {
        return Pro.find(defaultSql("id=?"), id).first();
    }
    
    public static Pro findByLocation(String location) {
        return Pro.find(defaultSql("location like ?"), "%" + location + "%").first();
    }
    
    public static List<Pro> fetchByIds(List<Long> ids) {
        if (ids == null || ids.isEmpty()) {
            return Collections.EMPTY_LIST;
        }
        return Pro.find(defaultSql("id in (:ids)")).bind("ids", ids.toArray()).fetch();
    }
    
    public static List<Pro> fetchAll() {
        return Pro.find(defaultSql()).fetch();
    }
    
    public static List<Pro> fetch(ProVO proVO) {
        Object[] data = data(proVO);
        List<String> hqls = (List<String>) data[0];
        List<Object> params = (List<Object>) data[1];
        return Pro.find(defaultSql(StringUtils.join(hqls, " and ")) + proVO.condition, params.toArray())
                .fetch(proVO.page, proVO.size);
    }
    
    public static int count(ProVO proVO) {
        Object[] data = data(proVO);
        List<String> hqls = (List<String>) data[0];
        List<Object> params = (List<Object>) data[1];
        return (int) Pro.count(defaultSql(StringUtils.join(hqls, " and ")), params.toArray());
    }
    
    public static Object[] data(ProVO proVO) {
        List<String> hqls = new ArrayList<>();
        List<Object> params = new ArrayList<>();
        if (StringUtils.isNotBlank(proVO.search)) {
            hqls.add("concat_ws(',',name,location) like ?");
            params.add("%" + proVO.search + "%");
        }
        return new Object[]{hqls, params};
    }
    
}
