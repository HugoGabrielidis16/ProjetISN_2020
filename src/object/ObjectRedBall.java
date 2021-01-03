package object;

import java.awt.*;
import java.awt.image.BufferedImage;

public class ObjectRedBall extends ObjectCore {

    public ObjectRedBall(int x, int y) {
        super(x, y);
        this.propertyCode = 0;
    }

    @Override
    public void draw(BufferedImage im){
        Graphics2D crayon = (Graphics2D) im.getGraphics();
        crayon.setColor(Color.red);
        crayon.fillOval(this.x, this.y,WIDTH,HEIGHT);
        crayon.setColor(Color.black);
        crayon.drawOval(this.x, this.y,WIDTH,HEIGHT);
    }

}
