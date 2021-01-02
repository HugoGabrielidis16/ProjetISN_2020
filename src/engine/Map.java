package engine;

import java.awt.image.BufferedImage;

public interface Map {

    public abstract void draw(BufferedImage image);

    public abstract boolean isOnStructure(HitBox hitBox);

}
