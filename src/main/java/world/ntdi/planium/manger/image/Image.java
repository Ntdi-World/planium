package world.ntdi.planium.manger.image;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public abstract class Image {
    @Getter
    private final String path;
    @Getter
    private final String text;
    @Getter
    private final int fontSize;
    @Getter
    private final int x;
    @Getter
    private final int y;
    @Getter
    private BufferedImage bufferedImage;

    public Image(String path, String text, int fontSize, int x, int y) throws IOException {
        this.path = path;
        this.text = text;
        this.fontSize = fontSize;
        this.x = x;
        this.y = y;
        this.bufferedImage = ImageIO.read(new File(this.getPath()));

        Font font = new Font("Hubot-Sans", Font.BOLD, fontSize);
        Graphics g = getBufferedImage().getGraphics();

        g.setFont(font);
        g.setColor(Color.WHITE);
        g.drawString(getText(), getX(), getY());
    }
}
