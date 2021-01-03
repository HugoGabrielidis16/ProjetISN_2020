package object;

import engine.HitBox;

import java.awt.*;
import java.awt.image.BufferedImage;

public class ObjectWhiteBall extends ObjectCore{

    public ObjectWhiteBall(int x, int y) {
        super(x, y);
        this.propertyCode = 1;
    }

    @Override
    public void draw(BufferedImage im){
        Graphics2D crayon = (Graphics2D) im.getGraphics();
        crayon.setColor(Color.white);
        crayon.fillOval(this.x, this.y,WIDTH,HEIGHT);
        crayon.setColor(Color.black);
        crayon.drawOval(this.x, this.y,WIDTH,HEIGHT);
        //HitBox.drawHitBox(this.hitBox, im);
    }



}
