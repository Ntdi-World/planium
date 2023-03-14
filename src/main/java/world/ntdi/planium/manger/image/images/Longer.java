package world.ntdi.planium.manger.image.images;

import lombok.Getter;
import world.ntdi.planium.manger.image.Image;

import java.io.IOException;

public class Longer extends Image {
    @Getter
    private static final String path = "src/main/resources/static/base/Ntdi_World_1200px-01.png";
    public Longer(String text, Integer fontSize, Integer x, Integer y) throws IOException {
        super(getPath(), text, fontSize, x, y);
    }
}
