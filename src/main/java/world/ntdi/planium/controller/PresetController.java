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
    private Cache<String, Image> cache = new Cache<>(100000);
    private final String url = "https://planium.ntdi.world/api/v1/picture/";
    @GetMapping(path = "/stubby")
    public ImageLocation createStubby(@RequestParam String text, @RequestParam(required = false) Integer fontSize, @RequestParam(required = false) Integer x, @RequestParam(required = false) Integer y) throws IOException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        return checkImageLocation(text, fontSize, x, y, "base/Ntdi_world_600px-01.png");
    }

    @GetMapping(path = "/long")
    public ImageLocation createLong(@RequestParam String text, @RequestParam(required = false) Integer fontSize, @RequestParam(required = false) Integer x, @RequestParam(required = false) Integer y) throws IOException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        return checkImageLocation(text, fontSize, x, y, "base/Ntdi_world_1200px-01.png");
    }


    private ImageLocation checkImageLocation(String text, Integer fontSize, Integer x, Integer y, String bufferedIO) throws IOException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        String serialized = ImageSerialization.serializeName(fontSize, x, y, text, ImageIO.read(new File(bufferedIO)));

        if (cache.get(serialized) != null) {
            return new ImageLocation(url + cache.get(serialized).getFile().getName());
        }

        Image image = new Image(bufferedIO, text, fontSize, x, y);

        cache.put(image.getSerializeName(), image);
        return new ImageLocation(url + image.getFile().getName());
    }
}
