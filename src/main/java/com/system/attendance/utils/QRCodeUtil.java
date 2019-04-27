package com.system.attendance.utils;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;
import org.springframework.util.ResourceUtils;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.OutputStream;
import java.io.IOException;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;


public final class QRCodeUtil {

    private static final int BLACK = 0xFF000000;
    private static final int WHITE = 0xFFFFFFFF;

//    @Value("${image.path}")
//    private static String path;

    private QRCodeUtil() {}

    public static void main(String[] args) throws FileNotFoundException {
        File paths = new File(ResourceUtils.getURL("classpath:").getPath());
        String localParent = paths.getParentFile()
                .getParent()+File.separator+"src"+
                File.separator+"main"+File.separator+"resources"+
                File.separator+"qrCode"+File.separator; //本地
        String cloudParent = paths.getParentFile().getParent()+File.separator+"qrCode"+File.separator;
        String parent = cloudParent.substring(5);//上线需要将前五个字符去除

        System.out.println(parent);
        System.out.println(paths);

//        getQRCode();
    }

    public static String getQRCode(){
        try {
            String content = JWTUtil.getToken();
            File paths = new File(ResourceUtils.getURL("classpath:").getPath());
            String path = paths.getParentFile().getParentFile().getParent()+File.separator+"qrCode"+File.separator;
            path = path.substring(5);
            MultiFormatWriter multiFormatWriter = new MultiFormatWriter();

            Map hints = new HashMap();
            hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
            BitMatrix bitMatrix = multiFormatWriter.encode(content, BarcodeFormat.QR_CODE, 400, 400,hints);
            File file = new File(path,"test.jpg");
            QRCodeUtil.writeToFile(bitMatrix, "jpg", file);
            System.out.println("生成二维码----"+file.getPath());
            return file.getPath();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    public static void writeToFile(BitMatrix matrix, String format, File file)
            throws IOException {
        BufferedImage image = toBufferedImage(matrix);
        if (!ImageIO.write(image, format, file)) {
            throw new IOException("Could not write an image of format " + format + " to " + file);
        }
    }


    public static BufferedImage toBufferedImage(BitMatrix matrix) {
        int width = matrix.getWidth();
        int height = matrix.getHeight();
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                image.setRGB(x, y, matrix.get(x, y) ? BLACK : WHITE);
            }
        }
        return image;
    }


    public static void writeToStream(BitMatrix matrix, String format, OutputStream stream)
            throws IOException {
        BufferedImage image = toBufferedImage(matrix);
        if (!ImageIO.write(image, format, stream)) {
            throw new IOException("Could not write an image of format " + format);
        }
    }

}