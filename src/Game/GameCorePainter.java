package Game;

import engine.Game;
import engine.GamePainter;

import java.awt.image.BufferedImage;

public class GameCorePainter implements GamePainter {

    //WINDOW SIZE
    protected static final int WIDTH = 800;
    protected static final int HEIGHT = 800;


    private final Game gameCore;

    public GameCorePainter(Game gameCore){
        this.gameCore = gameCore;
    }

    @Override
    public void draw(BufferedImage image) {

        this.gameCore.drawMap(image);
        this.gameCore.drawPlayer(image);
        this.gameCore.drawMonster(image);

    }

    public void initMap(BufferedImage image){
        if (!this.gameCore.hasMapBeenDrawedYet()) {
            image.getGraphics()
                    .fillRect(0, 0, WIDTH, HEIGHT);
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
