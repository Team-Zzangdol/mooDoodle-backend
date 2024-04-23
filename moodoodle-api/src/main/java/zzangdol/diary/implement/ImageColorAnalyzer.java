package zzangdol.diary.implement;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import javax.imageio.ImageIO;
import org.springframework.stereotype.Component;

@Component
public class ImageColorAnalyzer {

    public String analyzeAverageColorAsHex(String imageUrl) {
        try {
            int[] averageColor = getAverageColor(imageUrl);
            return rgbToHex(averageColor[0], averageColor[1], averageColor[2]);
        } catch (Exception e) {
            return "#ffffff";
        }
    }

    private int[] getAverageColor(String imageUrl) throws IOException {
        URL url = new URL(imageUrl);
        BufferedImage image = ImageIO.read(url);

        long sumR = 0, sumG = 0, sumB = 0;
        int width = image.getWidth();
        int height = image.getHeight();
        int count = width * height;

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                int pixel = image.getRGB(x, y);
                sumR += (pixel >> 16) & 0xff;
                sumG += (pixel >> 8) & 0xff;
                sumB += pixel & 0xff;
            }
        }

        return new int[]{(int) (sumR / count), (int) (sumG / count), (int) (sumB / count)};
    }

    private String rgbToHex(int red, int green, int blue) {
        return String.format("#%02x%02x%02x", red, green, blue);
    }

}
