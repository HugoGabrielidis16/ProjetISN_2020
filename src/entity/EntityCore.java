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
    protected int speedModifier = 1;
    protected int invicibilityTime = 0;

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
            case DOWN -> this.y = this.y + 10 * this.speedModifier;
            case UP -> this.y = this.y - 10 * this.speedModifier;
            case RIGHT -> this.x = this.x + 10 * this.speedModifier;
            case LEFT -> this.x = this.x - 10 * this.speedModifier;
        }

        this.hitBox = new HitBox(this.x, this.y, WIDTH, HEIGHT);
        this.lastCommand = cmd;
        if (this.getClass().equals(EntityPlayer.class)){this.invicibilityTimer();}

    }

    protected void invicibilityTimer(){
        if (this.isInvicible){
            if(this.invicibilityTime == 15000){
                this.isInvicible = false;
                this.speedModifier = 1;
            }
            this.invicibilityTime = this.invicibilityTime + 50;
        }
        else {this.invicibilityTime = 0;}
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
            case DOWN -> this.y = this.y - 10*this.speedModifier;
            case UP -> this.y = this.y + 10*this.speedModifier;
            case RIGHT -> this.x = this.x - 10*this.speedModifier;
            case LEFT -> this.x = this.x + 10*this.speedModifier;
        }

        this.lastCommand = null;
    }
}
