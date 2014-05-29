package com.guhanjie.image;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
 * 采用AWT技术生成JPG的图片文件
 * @author guhanjie
 * 2014-05-28 17:29:56
 */
public class ImgCreate {
	public String create(String str, String filePath, int width, int height) {

        String fileName = System.currentTimeMillis() + ".jpg";
        String path = filePath + "/" + fileName;
        File file = new File(path);

        BufferedImage bi = new BufferedImage(width, height,
                BufferedImage.TYPE_INT_RGB);

        Graphics2D g2 = (Graphics2D) bi.getGraphics();
        g2.setBackground(Color.WHITE);
        g2.clearRect(0, 0, width, height);

       
        Font font = new Font("黑体", Font.BOLD, 25);
        g2.setFont(font);
        g2.setPaint(Color.RED);

       
        FontRenderContext context = g2.getFontRenderContext();
        Rectangle2D bounds = font.getStringBounds(str, context);
        double x = (width - bounds.getWidth()) / 2;
        double y = (height - bounds.getHeight()) / 2;
        double ascent = -bounds.getY();
        double baseY = y + ascent;

       
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
                RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

       
        g2.drawString(str, (int) x, (int) baseY);

        try {
            ImageIO.write(bi, "jpg", file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return file.getPath();
    }

    public static void main(String[] args) {
        ImgCreate create = new ImgCreate();
        System.out.println(create.create("Hello, world", "f:/test/", 500, 38));
    }
}
