package controllers.back;

import enums.ProStatus;
import models.back.Pro;
import play.jobs.Job;
import vos.PageData;
import vos.Result;
import vos.StatusCode;
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
    
    public static void update(ProVO vo) {
        Pro pro = Pro.findByID(vo.proId);
        new Job() {
            @Override
            public void doJob() throws Exception {
                pro.update();
            }
        }.now();
        renderJSON(Result.failed(StatusCode.BACK_UPDATE_FAILED));
    }
    
    public static void restart(ProVO vo) {
        Pro pro = Pro.findByID(vo.proId);
        pro.status(ProStatus.START);
        new Job() {
            @Override
            public void doJob() throws Exception {
                pro.restart();
            }
        }.now();
        renderJSON(Result.succeed());
    }
    
    public static void del(ProVO vo) {
        Pro pro = Pro.findByID(vo.proId);
        pro.del();
        renderJSON(Result.succeed());
    }
    
    
}
