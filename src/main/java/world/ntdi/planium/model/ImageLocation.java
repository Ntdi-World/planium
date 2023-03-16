package world.ntdi.planium.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class ImageLocation {
    private final String path;
    private final Integer fontSize;
    private final Integer x;
    private final Integer y;
    private final String text;
}
