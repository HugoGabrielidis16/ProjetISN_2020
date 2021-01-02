package entity;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class EntityPlayer extends EntityCore {

    private int life;
    private int colorRotation = 0;

    public EntityPlayer(){
        super();
        this.life = 3;
        this.isInvicible = false;
    }

    @Override
    public void makePlayerInvicible(int time){
        if(time == 3000){
            this.cantKill = true;
        }

        this.isInvicible = true;
        this.invicibilityTimer(time);
    }

    @Override
    protected Color colorDrawed(){
        Color col = Color.white;
        if(this.cantKill){
            switch (this.colorRotation){
                case 0 -> {
                    col = Color.green;
                    this.colorRotation = 1;
                }
                case 1 -> {
                    col = Color.red;
                    this.colorRotation = 0;
                }
            }
        }
        else{
            if(this.isInvicible) {col = Color.red;}
            else{col = Color.white;}
        }
        return col;
    }

    public void addLife(){
        if (this.life <3 ){this.life = this.life + 1;}
    }

    public int getLife(){return this.life;}

    @Override
    public void killEntity(){
        if (!(this.life == 0)) {
            this.life = this.life - 1;
            this.hasDied = false;
            this.makePlayerInvicible(3000);
        }
        else{
            this.hasDied = true;
        }
    }

    @Override
    public void draw(BufferedImage im) {
        Graphics2D crayon = (Graphics2D) im.getGraphics();
        crayon.setColor(this.colorDrawed());
        crayon.fillOval(this.x, this.y,WIDTH,HEIGHT);
        crayon.setColor(Color.black);
        crayon.drawOval(this.x, this.y,WIDTH,HEIGHT);
    }
}
