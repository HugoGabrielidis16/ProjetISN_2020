package entity;

import Game.GameCoreController;
import engine.Cmd;
import engine.HitBox;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class EntityMonster extends EntityCore {

    public EntityMonster(){
        super();
        this.isInvicible = true;
    }

    @Override
    public void setCommand(Cmd cmd) {
        int[] tab= GameCoreController.move(cmd);
        this.x= this.x+ tab[0];
        this.y=this.y+tab[1];

        this.hitBox = new HitBox(this.x, this.y, WIDTH - 5, HEIGHT - 5);
        this.lastCommand = cmd;
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
