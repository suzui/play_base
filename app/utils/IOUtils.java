package utils;

import org.apache.commons.lang.StringUtils;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class IOUtils {
    
    public static String read(InputStream input) {
        try {
            //byte b[] = new byte[1024];
            //int l = -1;
            //while ((l = input.read(b)) != -1) {
            //    input.read(b, 0, l);
            //}
            //return new String(b).replaceAll("\u0000", "");
            BufferedReader reader = new BufferedReader(new InputStreamReader(input));
            String line;
            List<String> list = new ArrayList<>();
            while ((line = reader.readLine()) != null) {
                list.add(line);
            }
            reader.close();
            return StringUtils.join(list, "\n");
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }
}
