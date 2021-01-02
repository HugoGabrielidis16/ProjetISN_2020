package object;

import engine.HitBox;

import java.awt.image.BufferedImage;

public class ObjectCore implements GameObject {

    protected final static int WIDTH = 10;
    protected final static int HEIGHT = 10;
    protected int x;
    protected int y;
    protected boolean hasBeenEaten = false;
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
        this.hasBeenEaten = true;
    }

    @Override
    public boolean hasBeenEaten(){
        return this.hasBeenEaten;
    }

    @Override
    public void draw(BufferedImage im) {

    }
}
