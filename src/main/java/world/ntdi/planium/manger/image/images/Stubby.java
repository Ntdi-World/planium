package world.ntdi.planium.manger.image.images;

import world.ntdi.planium.manger.image.Image;

import java.io.IOException;

public class Stubby extends Image {
    public Stubby(String text, int fontSize, int x, int y) throws IOException {
        super("src/main/resources/static/base/Ntdi_World_600px-01.png", text, fontSize, x, y);
    }
}
