package Game;

import engine.Game;
import engine.GamePainter;

import java.awt.*;
import java.awt.image.BufferedImage;

public class GameCorePainter implements GamePainter {

    //WINDOW SIZE
    protected static final int WIDTH = 400;
    protected static final int HEIGHT = 400;


    private final Game gameCore;

    public GameCorePainter(Game gameCore){
        this.gameCore = gameCore;
    }

    @Override
    public void draw(BufferedImage image) {

        this.gameCore.drawPlayer(image);
        this.gameCore.drawMonsterAndObjects(image);
        this.gameCore.drawScoreAndLives(image);

    }

    public void initMap(BufferedImage image){
        if (!this.gameCore.hasMapBeenDrawedYet()) {
            Graphics2D crayon = (Graphics2D) image.getGraphics();
            crayon.setColor(Color.green);
            crayon.fillRect(0, 0, WIDTH, HEIGHT);
            this.gameCore.drawMap(image);
        }
    }

    @Override
    public int getWidth() {
        return WIDTH;
    }

    @Override
    public int getHeight() {
        return HEIGHT;
    }


}
