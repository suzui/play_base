package utils;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageConfig;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import org.apache.commons.lang.RandomStringUtils;
import play.Play;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class QRCodeUtils {
    
    public static void gen(String filePath, String logoPath, String content) {
        try {
            
            
            int width = 300; // 图像宽度
            int height = 300; // 图像高度
            String format = "jpg";// 图像类型
            Map<EncodeHintType, Object> hints = new HashMap<EncodeHintType, Object>();
            hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");//编码
            hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);//容错
            BitMatrix bitMatrix = new MultiFormatWriter().encode(content,
                    BarcodeFormat.QR_CODE, width, height, hints);// 生成矩阵
            Path path = FileSystems.getDefault().getPath(filePath);
            MatrixToImageConfig config = new MatrixToImageConfig();
            MatrixToImageWriter.writeToPath(bitMatrix, format, path, config);// 输出图像
            
            BufferedImage image = ImageIO.read(new File(filePath));
            Graphics2D g = image.createGraphics();
            //logo起始位置，此目的是为logo居中显示
            int w = width / 6;
            int h = height / 6;
            int x = (width - w) / 2;
            int y = (height - h) / 2;
            //绘制图
            BufferedImage logo = ImageIO.read(new File(logoPath));
            g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g.drawImage(
                    logo.getScaledInstance(w, h, Image.SCALE_SMOOTH),
                    x, y, null);
            
            //给logo画边框
            //构造一个具有指定线条宽度以及 cap 和 join 风格的默认值的实心 BasicStroke
            g.setStroke(new BasicStroke(1));
            g.setColor(Color.RED);
            g.drawRect(x, y, w, h);
            
            g.dispose();
            //写入logo照片到二维码
            ImageIO.write(image, format, new File(filePath));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static File gen(String content) {
        String filePath = "tmp/mcshcode" + RandomStringUtils.randomNumeric(16) + ".jpg";
        String logoPath = "public/images/logo/logo50_50.jpg";
        gen(filePath, logoPath, content);
        return new File(filePath);
    }
    
    public static void main(String[] args) {
        gen("xxx");
        return;
    }
    
    
}

