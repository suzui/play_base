package models.back;

import models.BaseModel;
import org.apache.commons.lang.StringUtils;
import play.Play;
import utils.ConfigUtils;
import utils.ShellUtils;
import vos.back.ProVO;

import javax.persistence.Entity;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Entity
public class Pro extends BaseModel {
    
    public String name;
    public String location;
    public String git;
    public String branch;
    public String shell;
    public String url;
    
    public static Pro add(ProVO proVO) {
        Pro pro = new Pro();
        pro.edit(proVO);
        return pro;
    }
    
    public void edit(ProVO proVO) {
        this.name = proVO.name != null ? proVO.name : name;
        this.location = proVO.location != null ? proVO.location : location;
        this.git = proVO.git != null ? proVO.git : git;
        this.branch = proVO.branch != null ? proVO.branch : branch;
        this.shell = proVO.shell != null ? proVO.shell : shell;
        this.url = proVO.url != null ? proVO.url : url;
        this.save();
    }
    
    public int update() {
        String shell = Play.frameworkPath.getAbsolutePath() + "/modules/play_base/conf/shell/update.sh";
        return ShellUtils.exec(shell, ConfigUtils.user, ConfigUtils.password, this.location, this.branch);
    }
    
    
    public void del() {
        this.logicDelete();
    }
    
    public static Pro findByID(Long id) {
        return Pro.find(defaultSql("id=?"), id).first();
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
        return Pro.find(defaultSql(StringUtils.join(hqls, " and ")) + proVO.condition(), params.toArray())
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
            hqls.add("concat_ws(',',enName,zhName) like ?");
            params.add("%" + proVO.search + "%");
        }
        return new Object[]{hqls, params};
    }
    
}
