package world.ntdi.planium.manger.image;

import lombok.Getter;
import world.ntdi.planium.manger.encoding.ImageSerialization;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Objects;

public class Image {
    @Getter
    private final String path;
    @Getter
    private final String text;
    @Getter
    private final Integer fontSize;
    @Getter
    private final Integer x;
    @Getter
    private final Integer y;
    @Getter
    private final BufferedImage bufferedImage;
    @Getter
    private final String serializeName;
    @Getter
    private final File file;

    public Image(String path, String text, Type type, Integer fontSize, Integer x, Integer y) throws IOException {
        this.path = path;
        this.text = text;

        this.fontSize = Objects.requireNonNullElseGet(fontSize, () -> type.fontMax - (int) (text.length() * type.fontMod));

        this.bufferedImage = ImageIO.read(new File(this.getPath()));

        Font font = new Font("Hubot-Sans", Font.BOLD, getFontSize());
        Graphics g = getBufferedImage().getGraphics();
        FontMetrics metrics = g.getFontMetrics(font);

        this.x = Objects.requireNonNullElse(x, 50);
        this.y = Objects.requireNonNullElseGet(y, () -> (bufferedImage.getHeight() - metrics.getHeight()) / 2 + metrics.getAscent());

        g.setFont(font);
        g.setColor(Color.WHITE);
        g.drawString(getText(), getX(), getY());

        this.serializeName = ImageSerialization.serializeName(type, getFontSize(), getX(), getY(), text, getBufferedImage());
        this.file = new File("cache/" + serializeName);
        this.file.mkdirs();

        ImageIO.write(bufferedImage, "png", getFile());
    }

    public enum Type {
        STUBBY(100, 2.5F),
        LONG(100, 2F),
        GITHUB(150, 2F),
        GIANT(200, 2F);

        @Getter
        private final int fontMax;
        @Getter
        private final float fontMod;

        Type(int fontMax, float fontMod) {
            this.fontMax = fontMax;
            this.fontMod = fontMod;
        }
    }
}
