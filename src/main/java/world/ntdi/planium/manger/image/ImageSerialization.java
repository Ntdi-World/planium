package world.ntdi.planium.manger.image;

import lombok.experimental.UtilityClass;

@UtilityClass
public class ImageSerialization {
    public String serializeName(int fontSize, int x, int y, String text) {
        text = text.trim().replaceAll(" ", "_");
        return "size-" + fontSize + "-x-" + x + "-y-" + y + "-text-" + text + ".png";
    }
}
