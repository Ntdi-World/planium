package world.ntdi.planium.manger.image;

import lombok.Getter;

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

    public Image(String path, String text, Integer fontSize, Integer x, Integer y) throws IOException {
        this.path = path;
        this.text = text;

        this.fontSize = Objects.requireNonNullElseGet(fontSize, () -> 100 - (text.length() * 2));

        this.bufferedImage = ImageIO.read(new File(this.getPath()));

        Font font = new Font("Hubot-Sans", Font.BOLD, getFontSize());
        Graphics g = getBufferedImage().getGraphics();
        FontMetrics metrics = g.getFontMetrics(font);

        this.x = Objects.requireNonNullElse(x, 50);
        this.y = Objects.requireNonNullElseGet(y, () -> (bufferedImage.getHeight() - metrics.getHeight()) / 2 + metrics.getAscent());

        g.setFont(font);
        g.setColor(Color.WHITE);
        g.drawString(getText(), getX(), getY());

        this.serializeName = ImageSerialization.serializeName(getFontSize(), getX(), getY(), text, getBufferedImage());
        this.file = new File("cache/" + serializeName);
        this.file.mkdirs();

        ImageIO.write(bufferedImage, "png", getFile());
    }

    public enum ImageTypes {
        STUBBY("src/main/resources/static/base/Ntdi_World_600px-01.png"),
        LONGER("src/main/resources/static/base/Ntdi_World_1200px-01.png");

        @Getter
        private final String path;

        ImageTypes(String path) {
            this.path = path;
        }

        @Override
        public String toString() {
            return getPath();
        }
    }
}
