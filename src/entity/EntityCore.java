package entity;

import engine.Cmd;
import engine.HitBox;
import engine.Map;

import java.awt.image.BufferedImage;

public class EntityCore implements Entity {

    protected int x;
    protected int y;
    protected final static int WIDTH = 40;
    protected final static int HEIGHT = 40;
    protected boolean hasDied = false;
    protected HitBox hitBox = new HitBox(this.x, this.y, WIDTH, HEIGHT);
    protected Cmd lastCommand;
    protected boolean isInvicible;

    EntityCore(){
        this.x = 380;
        this.y = 360;
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

        this.hitBox = new HitBox(this.x, this.y, WIDTH, HEIGHT);
        this.lastCommand = cmd;

    }

    @Override
    public HitBox getHitBox(){
        return this.hitBox;
    }

    @Override
    public boolean getInvicibleState(){
        return this.isInvicible;
    }

    @Override
    public void draw(BufferedImage im) {
    }

    @Override
    public void changeInvicibleState(boolean state){
        this.isInvicible = state;
    }

    @Override
    public Cmd getLastDir(){
        return this.lastCommand;
    }

    @Override
    public void killEntity(){
        this.hasDied = true;
    }

    @Override
    public boolean hasDied(){
        return this.hasDied;
    }

    @Override
    public void rollBackCommand(Cmd cmd){
        switch (cmd) {
            case DOWN -> this.y = this.y - 10;
            case UP -> this.y = this.y + 10;
            case RIGHT -> this.x = this.x - 10;
            case LEFT -> this.x = this.x + 10;
        }

        this.lastCommand = null;
    }
}
