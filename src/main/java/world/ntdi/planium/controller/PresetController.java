package world.ntdi.planium.controller;

import org.atmosphere.config.service.Get;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

@RestController
@RequestMapping("/api/v1/preset/")
public class PresetController {

    @GetMapping(path = "/long", produces = MediaType.IMAGE_PNG_VALUE)
    public ResponseEntity<BufferedImage> createLong() {
        try {
            return ResponseEntity.ok(ImageIO.read(new File("src/main/resources/static/Ntdi_world_1200px-01.png")));
        } catch (IOException e) {
            e.printStackTrace();
        }

//        int fontSize = 100 - (int) (message.length() * 1.5);
//
//        Font font = new Font("Hubot-Sans", Font.BOLD, fontSize);
//        Graphics g = image.getGraphics();
//
//        FontMetrics metrics = g.getFontMetrics(font);
//
//        int positionY = (image.getHeight() - metrics.getHeight()) / 2 + metrics.getAscent();
//
//        g.setFont(font);
//        g.setColor(Color.WHITE);
//        g.drawString(message, 50, positionY);
//
//        return image;
        return null;
    }
}
