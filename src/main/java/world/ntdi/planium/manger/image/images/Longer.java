package world.ntdi.planium.manger.image.images;

import lombok.Getter;
import world.ntdi.planium.manger.image.Image;

import java.io.IOException;

public class Longer extends Image {
    @Getter
    private static final String filePath = "src/main/resources/static/base/Ntdi_world_1200px-01.png";
    public Longer(String text, Integer fontSize, Integer x, Integer y) throws IOException {
        super(getFilePath(), text, fontSize, x, y);
    }
}
