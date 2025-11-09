package org.example.utils;

import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;



public class ImageTextExtractor {

    public static BufferedImage preprocess(BufferedImage img) {
        // Convert to grayscale
        BufferedImage gray = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_BYTE_GRAY);
        Graphics g = gray.getGraphics();
        g.drawImage(img, 0, 0, null);
        g.dispose();

        // Optional: resize if text is small
        BufferedImage resized = new BufferedImage(gray.getWidth()*2, gray.getHeight()*2, BufferedImage.TYPE_BYTE_GRAY);
        Graphics2D g2d = resized.createGraphics();
        g2d.drawImage(gray, 0, 0, resized.getWidth(), resized.getHeight(), null);
        g2d.dispose();

        return resized;
    }

    public static String extractText(BufferedImage  imageFile) {
        ITesseract tesseract = new Tesseract();
        tesseract.setDatapath("C:\\Program Files\\Tesseract-OCR\\tessdata");
        tesseract.setPageSegMode(3); // Automatic page segmentation with OSD

        tesseract.setLanguage("eng+rus");

        try {
            String text = tesseract.doOCR(imageFile);
            System.out.println("🧾 Extracted text:");
            System.out.println(text);

            return text;
        } catch (TesseractException e) {
            e.printStackTrace();
            return null;
        }
    }
}