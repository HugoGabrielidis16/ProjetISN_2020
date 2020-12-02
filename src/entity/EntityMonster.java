package entity;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class EntityMonster extends EntityCore {

    public EntityMonster(){
        super();
        this.isInvicible = true;
    }

    @Override
    public void draw(BufferedImage im) {
        Graphics2D crayon = (Graphics2D) im.getGraphics();
        crayon.setColor(Color.red);
        crayon.fillOval(this.x, this.y,WIDTH,HEIGHT);
        crayon.setColor(Color.black);
        crayon.drawOval(this.x, this.y,WIDTH,HEIGHT);
    }

}
