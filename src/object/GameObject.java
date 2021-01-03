package object;

import engine.HitBox;

import java.awt.image.BufferedImage;
import java.nio.Buffer;

public interface GameObject {

    public void draw(BufferedImage im);

    HitBox getHitBox();

    public void killObject();

    public boolean hasBeenEaten();

    public int getPropertyCode();
}


