package controllers.back;

import enums.ProStatus;
import models.back.Pro;
import org.apache.commons.lang.StringUtils;
import play.jobs.Job;
import vos.PageData;
import vos.Result;
import vos.back.ProVO;

import java.util.List;
import java.util.stream.Collectors;


public class ProController extends BackController {
    
    public static void list(ProVO vo) {
        List<Pro> pros = Pro.fetch(vo);
        List<ProVO> proVOs = pros.stream().map(a -> new ProVO(a)).collect(Collectors.toList());
        renderJSON(Result.succeed(new PageData(proVOs)));
    }
    
    public static void add(ProVO vo) {
        Pro.add(vo);
        renderJSON(Result.succeed());
    }
    
    public static void edit(ProVO vo) {
        Pro pro = Pro.findByID(vo.proId);
        pro.edit(vo);
        renderJSON(Result.succeed());
    }
    
    public static void git(ProVO vo) {
        Pro pro = Pro.findByID(vo.proId);
        ProVO proVO = new ProVO(pro);
        proVO.branchs = pro.gitbranch().read;
        proVO.gitlog = pro.gitlog().read;
        renderJSON(Result.succeed(proVO));
    }
    
    public static void stop(ProVO vo) {
        Pro pro = Pro.findByID(vo.proId);
        pro.status(ProStatus.STOP);
        new Job() {
            @Override
            public void doJob() throws Exception {
                pro.stop();
            }
        }.now();
        renderJSON(Result.succeed());
    }
    
    public static void start(ProVO vo) {
        if (!Pro.canStart()) {
            renderJSON(Result.failed(Result.StatusCode.BACK_START_FAILED));
        }
        Pro pro = Pro.findByID(vo.proId);
        pro.status(ProStatus.START);
        new Job() {
            @Override
            public void doJob() throws Exception {
                pro.start();
            }
        }.now();
        renderJSON(Result.succeed());
    }
    
    public static void update(ProVO vo) {
        Pro pro = Pro.findByID(vo.proId);
        new Job() {
            @Override
            public void doJob() throws Exception {
                pro.update();
            }
        }.now();
        renderJSON(Result.succeed());
    }
    
    public static void restart(ProVO vo) {
        if (!Pro.canStart()) {
            renderJSON(Result.failed(Result.StatusCode.BACK_START_FAILED));
        }
        Pro pro = Pro.findByID(vo.proId);
        if (StringUtils.isNotBlank(pro.port)) {
            pro.nuxt();
        } else {
            pro.status(ProStatus.START);
            new Job() {
                @Override
                public void doJob() throws Exception {
                    pro.restart();
                }
            }.now();
        }
        renderJSON(Result.succeed());
    }
    
    public static void del(ProVO vo) {
        Pro pro = Pro.findByID(vo.proId);
        pro.del();
        renderJSON(Result.succeed());
    }
    
    public static void dels(ProVO vo) {
        List<Pro> pros = Pro.fetchByIds(vo.proIds);
        pros.forEach(p -> p.del());
        renderJSON(Result.succeed());
    }
    
    
}
