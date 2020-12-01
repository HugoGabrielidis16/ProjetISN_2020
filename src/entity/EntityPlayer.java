package entity;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class EntityPlayer extends EntityCore {

    public EntityPlayer(){
        super();
        this.x = 400;
        this.y = 400;
        this.isInvicible = false;
    }

    @Override
    public void draw(BufferedImage im) {
        Graphics2D crayon = (Graphics2D) im.getGraphics();
        crayon.setColor(Color.blue);
        crayon.fillOval(this.x, this.y,WIDTH,HEIGHT);
    }
}
