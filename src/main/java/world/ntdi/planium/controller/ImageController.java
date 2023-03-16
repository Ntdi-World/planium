package world.ntdi.planium.controller;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

@RestController
@RequestMapping("/api/v1/")
public class ImageController {

    @RequestMapping("/picture/{id}")
    @ResponseBody
    public HttpEntity<byte[]> getArticleImage(@PathVariable String id) throws IOException {

        BufferedImage bImage = null;
        try {
            bImage = ImageIO.read(new File("cache/" + id));
        } catch (IOException ignored) {}

        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ImageIO.write(bImage, "png", bos);

        byte[] image = bos.toByteArray();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_PNG);
        headers.setContentLength(image.length);

        return new HttpEntity<byte[]>(image, headers);
    }
}
