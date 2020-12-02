package entity;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class EntityPlayer extends EntityCore {

    private int life;
    //private int invicibilityTime = 0;

    public EntityPlayer(){
        super();
        this.life = 3;
        this.isInvicible = true;
    }

    @Override
    public void killEntity(){
        if (!(this.life == 0)) {
            this.life = this.life - 1;
            this.hasDied = false;
        }
        else{
            this.hasDied = true;
        }
    }

    @Override
    public void draw(BufferedImage im) {
        Graphics2D crayon = (Graphics2D) im.getGraphics();
        crayon.setColor(Color.blue);
        crayon.fillOval(this.x, this.y,WIDTH,HEIGHT);
        crayon.setColor(Color.black);
        crayon.drawOval(this.x, this.y,WIDTH,HEIGHT);
    }
}
