package entity;

import engine.Cmd;

import java.awt.*;
import java.awt.image.BufferedImage;

public class EntityCore implements Entity {

    protected int x;
    protected int y;
    protected final static int WIDTH = 40;
    protected final static int HEIGHT = 40;
    protected boolean isInvicible;

    EntityCore(){
        this.x = 0;
        this.y = 0;
    }

    @Override
    public int[] getPosition() {
        int[] tab = new int[] {this.x, this.y};
        return (tab);
    }



    @Override
    public void setCommand(Cmd cmd) {

        switch (cmd) {
            case DOWN -> this.y = this.y + 10;
            case UP -> this.y = this.y - 10;
            case RIGHT -> this.x = this.x + 10;
            case LEFT -> this.x = this.x - 10;
        }
    }

    @Override
    public boolean getInvicibleState(){
        return this.isInvicible;
    }

    @Override
    public void draw(BufferedImage im) {
        Graphics2D crayon = (Graphics2D) im.getGraphics();
    }

    @Override
    public void changeInvicibleState(boolean state){
        this.isInvicible = state;
    }

}
