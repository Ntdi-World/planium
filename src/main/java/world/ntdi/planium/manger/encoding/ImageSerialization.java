package world.ntdi.planium.manger.encoding;

import lombok.experimental.UtilityClass;
import world.ntdi.planium.manger.image.Image;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Base64;

@UtilityClass
public class ImageSerialization {
    public String serializeName(Image.Type type, Integer fontSize, Integer x, Integer y, String text, BufferedImage image) {
        if (fontSize == null) {
            fontSize = type.getFontMax() - (int) (text.length() * type.getFontMod());
        }

        Font font = new Font("Hubot-Sans", Font.BOLD, fontSize);
        Graphics g = image.getGraphics();
        FontMetrics metrics = g.getFontMetrics(font);

        if (x == null) {
            x = (type.isCenterX() ? (image.getWidth() - metrics.stringWidth(text)) / 2 : 50);
        }
        if (y == null) {
            y = (image.getHeight() - metrics.getHeight()) / 2 + metrics.getAscent();
        }


        text = text.trim().replaceAll(" ", "_");
        String name = "size-" + fontSize + "-x-" + x + "-y-" + y + "-text-" + text + "-type-" + type + ".png";
        return Base58Check.bytesToBase58(name.getBytes());
    }
}
