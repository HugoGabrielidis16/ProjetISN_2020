package object;

import java.awt.*;
import java.awt.image.BufferedImage;

public class ObjectYellowBall extends ObjectCore {

    public ObjectYellowBall(int x, int y) {
        super(x, y);
    }

    @Override
    public void draw(BufferedImage im){
        Graphics2D crayon = (Graphics2D) im.getGraphics();
        crayon.setColor(Color.yellow);
        crayon.fillOval(this.x, this.y,WIDTH,HEIGHT);
        crayon.setColor(Color.black);
        crayon.drawOval(this.x, this.y,WIDTH,HEIGHT);
    }

}
