package controllers.back;

import models.back.Config;
import utils.ConfigUtils;
import vos.PageData;
import vos.Result;
import vos.back.ConfigVO;

import java.util.List;
import java.util.stream.Collectors;

public class ConfigController extends BackController {
    
    public static void list(ConfigVO vo) {
        List<Config> configs = Config.fetch(vo);
        List<ConfigVO> configVOs = configs.stream().map(a -> new ConfigVO(a)).collect(Collectors.toList());
        renderJSON(Result.succeed(new PageData(configVOs)));
    }
    
    public static void edit(ConfigVO vo) {
        Config config = Config.findByID(vo.configId);
        config.edit(vo);
        ConfigUtils.load();
        renderJSON(Result.succeed());
    }
    
    
}
