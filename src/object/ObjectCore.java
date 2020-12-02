package object;

import engine.HitBox;

import java.awt.image.BufferedImage;

public class ObjectCore implements GameObject {

    protected final static int WIDTH = 20;
    protected final static int HEIGHT = 20;
    protected int x;
    protected int y;
    protected boolean hasDied = false;
    protected HitBox hitBox;

    public ObjectCore(int x, int y){
        this.x = x;
        this.y = y;
        this.hitBox = new HitBox(this.x, this.y, WIDTH, HEIGHT);
    }

    @Override
    public HitBox getHitBox(){
        return this.hitBox;
    }

    @Override
    public void killObject(){
        this.hasDied = true;
    }

    @Override
    public boolean hasDied(){
        return this.hasDied;
    }

    @Override
    public void draw(BufferedImage im) {

    }
}
