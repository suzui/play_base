package utils;

import org.apache.commons.lang.StringUtils;
import play.Logger;

import java.io.InputStream;

public class ShellUtils {
    
    public static class Result {
        
        
        public int status;
        public String read;
        
        private Result(int status) {
            this.status = status;
        }
        
        public boolean succ() {
            return status == 0;
        }
    }
    
    
    public static Result exec(String shell, String... params) {
        Result result = new Result(-1);
        try {
            String[] cmd = new String[params.length + 1];
            cmd[0] = shell;
            for (int i = 0; i < params.length; i++) {
                cmd[i + 1] = params[i];
            }
            Logger.info("[shell start]:================");
            Logger.info("[shell shell]:%s", shell);
            Logger.info("[shell param]:%s", StringUtils.join(params, ","));
            Process process = Runtime.getRuntime().exec(cmd);
            InputStream inputStream = process.getInputStream();
            String read = IOUtils.read(inputStream);
            result.read = read;
            Logger.info("[shell read]:%s", read);
            int status = process.waitFor();
            result.status = status;
            Logger.info("[shell status]:%d", status);
            Logger.info("[shell end]:================");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
    
}
