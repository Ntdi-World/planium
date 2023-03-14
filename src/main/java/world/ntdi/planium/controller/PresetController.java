package world.ntdi.planium.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import world.ntdi.planium.manger.cache.Cache;
import world.ntdi.planium.manger.image.Image;
import world.ntdi.planium.manger.image.ImageSerialization;
import world.ntdi.planium.manger.image.images.Stubby;
import world.ntdi.planium.model.ImageLocation;

import java.io.IOException;

@RestController
@RequestMapping("/api/v1/preset/")
public class PresetController {
    private Cache<String, Image> cache = new Cache<>(100);

    @GetMapping(path = "/stubby")
    public ImageLocation createStubby() throws IOException {
        String text = "Hello World";
        int fontSize = 75;
        int x = 25;
        int y = 220;
        String serialized = ImageSerialization.serializeName(fontSize, x, y, text);

        if (cache.get(serialized) != null) {
            return new ImageLocation("localhost:8080/cache/" + cache.get(serialized).getFile().getName());
        }

        Stubby image = new Stubby("Hello World", 75, 25, 220);
        cache.put(image.getSerializeName(), image);
        return new ImageLocation("localhost:8080/cache/" + image.getFile().getName());
    }
}
