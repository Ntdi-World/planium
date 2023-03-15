package world.ntdi.planium.manger.image.images;

import lombok.Getter;
import world.ntdi.planium.manger.image.Image;

import java.io.IOException;

public class Stubby extends Image {
    @Getter
    private static final String filePath = "src/main/resources/static/base/Ntdi_World_600px-01.png";
    public Stubby(String text, Integer fontSize, Integer x, Integer y) throws IOException {
        super(getFilePath(), text, fontSize, x, y);
    }
}
