package world.ntdi.planium.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import world.ntdi.planium.manger.cache.Cache;
import world.ntdi.planium.manger.image.Image;
import world.ntdi.planium.manger.encoding.ImageSerialization;
import world.ntdi.planium.model.ImageLocation;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

@RestController
@RequestMapping("/api/v1/preset/")
public class PresetController {
    private Cache<String, Image> cache = new Cache<>(50000);
    private final String url = "https://planium.ntdi.world/api/v1/picture/";
    @GetMapping(path = "/stubby")
    public ImageLocation createStubby(@RequestParam String text, @RequestParam(required = false) Integer fontSize, @RequestParam(required = false) Integer x, @RequestParam(required = false) Integer y) throws IOException  {
        return checkImageLocation(text, Image.Type.STUBBY, fontSize, x, y, "base/stubby.png");
    }

    @GetMapping(path = "/long")
    public ImageLocation createLong(@RequestParam String text, @RequestParam(required = false) Integer fontSize, @RequestParam(required = false) Integer x, @RequestParam(required = false) Integer y) throws IOException  {
        return checkImageLocation(text, Image.Type.LONG, fontSize, x, y, "base/long.png");
    }

    @GetMapping(path = "/github")
    public ImageLocation createGithub(@RequestParam String text, @RequestParam(required = false) Integer fontSize, @RequestParam(required = false) Integer x, @RequestParam(required = false) Integer y) throws IOException  {
        return checkImageLocation(text, Image.Type.GITHUB, fontSize, x, y, "base/github.png");
    }

    @GetMapping(path = "/square")
    public ImageLocation createSquare(@RequestParam String text, @RequestParam(required = false) Integer fontSize, @RequestParam(required = false) Integer x, @RequestParam(required = false) Integer y) throws IOException  {
        return checkImageLocation(text, Image.Type.SQUARE, fontSize, x, y, "base/square.png");
    }

    @GetMapping(path = "/giant")
    public ImageLocation createGiant(@RequestParam String text, @RequestParam(required = false) Integer fontSize, @RequestParam(required = false) Integer x, @RequestParam(required = false) Integer y) throws IOException  {
        return checkImageLocation(text, Image.Type.GIANT, fontSize, x, y, "base/giant.png");
    }

    @GetMapping(path = "/4k")
    public ImageLocation create4K(@RequestParam String text, @RequestParam(required = false) Integer fontSize, @RequestParam(required = false) Integer x, @RequestParam(required = false) Integer y) throws IOException  {
        return checkImageLocation(text, Image.Type.FOURK, fontSize, x, y, "base/4K.png");
    }


    private ImageLocation checkImageLocation(String text, Image.Type type, Integer fontSize, Integer x, Integer y, String bufferedIO) throws IOException {
        String serialized = ImageSerialization.serializeName(type, fontSize, x, y, text, ImageIO.read(new File(bufferedIO)));

        String path;

        if (cache.get(serialized) != null) {
            path = cache.get(serialized).getFile().getName();
        } else {
            Image image = new Image(bufferedIO, text, type, fontSize, x, y);
            cache.put(image.getSerializeName(), image);
            path = image.getFile().getName();
        }

        Image image = cache.get(serialized);
        return new ImageLocation(url + path, image.getFontSize(), image.getX(), image.getY(), text);
    }
}
