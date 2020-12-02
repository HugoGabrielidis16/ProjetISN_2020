package engine;

import java.awt.image.BufferedImage;

public interface AI {

    public void spawnMonster();

    public void handleMonster();

    public void draw(BufferedImage im);

}
