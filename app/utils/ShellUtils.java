package utils;

import play.Logger;

import java.io.File;
import java.io.InputStream;

public class ShellUtils {
    
    
    public static int exec(String shell, String... fields) {
        int status = -1;
        try {
            String[] cmd = new String[fields.length + 1];
            cmd[0] = shell;
            for (int i = 0; i < fields.length; i++) {
                cmd[i + 1] = fields[i];
            }
            Logger.info("[shell start]:================");
            Logger.info("[shell cmd]:%s", shell, fields);
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
