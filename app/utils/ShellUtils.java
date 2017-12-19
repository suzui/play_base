package utils;

import org.apache.commons.lang.StringUtils;
import play.Logger;

import java.io.File;
import java.io.InputStream;

public class ShellUtils {
    
    
    public static int exec(String shell, String... params) {
        int status = -1;
        try {
            String[] cmd = new String[params.length + 1];
            cmd[0] = shell;
            for (int i = 0; i < params.length; i++) {
                cmd[i + 1] = params[i];
            }
            Logger.info("[shell start]:================");
            Logger.info("[shell param]:%s", StringUtils.join(params, ","));
            Process process = Runtime.getRuntime().exec(cmd, null, new File(shell).getParentFile());
            InputStream inputStream = process.getInputStream();
            String read = IOUtils.read(inputStream);
            Logger.info("[shell read]:%s", read);
            Logger.info("[shell end]:================");
            status = process.waitFor();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return status;
    }
    
}
