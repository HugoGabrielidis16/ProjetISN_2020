package entity;

import engine.Cmd;

import java.awt.image.BufferedImage;

public interface Entity {

    public abstract void changeInvicibleState(boolean state);

    public abstract boolean getInvicibleState();

    public abstract void draw(BufferedImage im);

    public abstract int[] getPosition();

    public abstract void setCommand(Cmd cmd);
}
