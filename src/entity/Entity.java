package entity;

import engine.Cmd;
import engine.HitBox;
import engine.Map;

import java.awt.image.BufferedImage;

public interface Entity {

    public abstract void changeInvicibleState(boolean state);

    public abstract boolean getInvicibleState();

    public void makePlayerInvicible(int time);

    public abstract void draw(BufferedImage im);

    public abstract int[] getPosition();

    public abstract void setCommand(Cmd cmd);

    public abstract Cmd getLastDir();

    public abstract void killEntity();

    public abstract boolean hasDied();

    public abstract HitBox getHitBox();

    public boolean cantKill ();
}
