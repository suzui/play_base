package utils;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.LineIterator;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TextUtils {
    public static void main(String[] args) {
        
        File file = new File("log");
        LineIterator it = null;
        try {
            it = FileUtils.lineIterator(file, "UTF-8");
            while (it.hasNext()) {
                String s = it.next();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            LineIterator.closeQuietly(it);
        }
        
    }
}

