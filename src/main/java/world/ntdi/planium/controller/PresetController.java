package world.ntdi.planium.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import world.ntdi.planium.manger.cache.Cache;
import world.ntdi.planium.manger.image.Image;
import world.ntdi.planium.manger.image.ImageSerialization;
import world.ntdi.planium.manger.image.images.Longer;
import world.ntdi.planium.manger.image.images.Stubby;
import world.ntdi.planium.model.ImageLocation;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

@RestController
@RequestMapping("/api/v1/preset/")
public class PresetController {
    private Cache<String, Image> cache = new Cache<>(100);

    @GetMapping(path = "/stubby")
    public ImageLocation createStubby(@RequestParam String text, @RequestParam(required = false) Integer fontSize, @RequestParam(required = false) Integer x, @RequestParam(required = false) Integer y) throws IOException {
        return checkImageLocation(text, fontSize, x, y, Stubby.getPath());
    }

    @GetMapping(path = "/long")
    public ImageLocation createLong(@RequestParam String text, @RequestParam(required = false) Integer fontSize, @RequestParam(required = false) Integer x, @RequestParam(required = false) Integer y) throws IOException {
        return checkImageLocation(text, fontSize, x, y, Longer.getPath());
    }


    private ImageLocation checkImageLocation(String text, Integer fontSize, Integer x, Integer y, String bufferedIO) throws IOException {
        String serialized = ImageSerialization.serializeName(fontSize, x, y, text, ImageIO.read(new File(bufferedIO)));

        if (cache.get(serialized) != null) {
            return new ImageLocation("localhost:8080/cache/" + cache.get(serialized).getFile().getName());
        }

        Stubby image = new Stubby(text, fontSize, x, y);
        cache.put(image.getSerializeName(), image);
        return new ImageLocation("localhost:8080/cache/" + image.getFile().getName());
    }
}
