package utils;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageConfig;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

public class QRCodeUtils {
    
    public static void gen(String filePath, String logoPath, String content) throws WriterException, IOException {
        int width = 200; // 图像宽度
        int height = 200; // 图像高度
        String format = "png";// 图像类型
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
        int w = width / 5;
        int h = height / 5;
        int x = (width - w) / 2;
        int y = (height - h) / 2;
        //绘制图
        BufferedImage logo = ImageIO.read(new File(logoPath));
        g.drawImage(logo, x, y, w, h, null);
        
        //给logo画边框
        //构造一个具有指定线条宽度以及 cap 和 join 风格的默认值的实心 BasicStroke
        //g.setStroke(new BasicStroke(2));
        //g.setColor(Color.RED);
        //g.drawRect(x, y, width, height);
        
        g.dispose();
        //写入logo照片到二维码
        ImageIO.write(image, format, new File(filePath));
    }
    
    public static void main(String[] args) throws Exception {
        String filePath = "/Users/suzui/Desktop/1.png";
        String logoPath = "";
        String content = "";
        gen(filePath, logoPath, content);
        return;
    }
    
    
}

