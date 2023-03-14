package world.ntdi.planium.manger.image;

import lombok.experimental.UtilityClass;

import java.awt.*;
import java.awt.image.BufferedImage;

@UtilityClass
public class ImageSerialization {
    public String serializeName(Integer fontSize, Integer x, Integer y, String text, BufferedImage image) {
        if (fontSize == null) {
            fontSize = 100 - (text.length() * 2);
        }

        Font font = new Font("Hubot-Sans", Font.BOLD, fontSize);
        Graphics g = image.getGraphics();
        FontMetrics metrics = g.getFontMetrics(font);

        if (x == null) {
            x = 50;
        }
        if (y == null) {
            y = (image.getHeight() - metrics.getHeight()) / 2 + metrics.getAscent();
        }


        text = text.trim().replaceAll(" ", "_");
        return "size-" + fontSize + "-x-" + x + "-y-" + y + "-text-" + text + ".png";
    }
}
