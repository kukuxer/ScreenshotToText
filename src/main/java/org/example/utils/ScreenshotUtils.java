package org.example.utils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.example.ui.BottomTextDisplay.showTextAtBottom;
import static org.example.utils.ImageTextExtractor.extractText;
import static org.example.utils.ImageTextExtractor.preprocess;

public class ScreenshotUtils {
    private static int screenshotNumber = 1;

    public static void captureAndSave(Rectangle rect) {
        try {
            Robot robot = new Robot();
            BufferedImage img = robot.createScreenCapture(rect);

            File folder = new File("Screenshots");
            if (!folder.exists()) folder.mkdir();

            String timestamp = LocalDateTime.now()
                    .format(DateTimeFormatter.ofPattern("HH-mm_dd_MMM_yy"))
                    .toLowerCase();

            String fileName = String.format("screenshot_%03d_%s.png", screenshotNumber++, timestamp);
            File file = new File(folder, fileName);


            ImageIO.write(img, "png", file);
            System.out.println("✅ Screenshot saved: " + file.getAbsolutePath());

            Thread.sleep(100);

            BufferedImage savedImage = ImageIO.read(file);
            BufferedImage preprocessed = preprocess(savedImage);
            showTextAtBottom(extractText(preprocessed));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
