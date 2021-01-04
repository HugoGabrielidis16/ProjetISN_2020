package Game;

import engine.Game;
import engine.GamePainter;

import java.awt.*;
import java.awt.image.BufferedImage;

public class GameCorePainter implements GamePainter {

    //WINDOW SIZE
    protected static final int WIDTH = 400;
    protected static final int HEIGHT = 400;
    protected boolean levelChangeScreenInGoing = false;


    private final Game gameCore;

    public GameCorePainter(Game gameCore){
        this.gameCore = gameCore;
    }

    @Override
    public void draw(BufferedImage image) {

        if(this.gameCore.isLevelScreenInGoing()) {
            drawLevelScreen(image);
        }
        else{
            this.gameCore.drawPlayer(image);
            this.gameCore.drawMonsterAndObjects(image);
            this.gameCore.drawScoreAndLives(image);
        }

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
    public void levelChangePaint (BufferedImage im, int currentLevel, int scoreBonus){

        if (levelChangeScreenInGoing = false){
            this.levelChangeScreenInGoing = true;
        }
        else{
            this.levelChangeScreenInGoing = false;

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

    private void  drawLevelScreen(BufferedImage im){

        Graphics2D crayon = (Graphics2D) im.getGraphics();

        crayon.setColor(Color.black);
        crayon.fillRect(0,0, WIDTH,HEIGHT);

        crayon.setColor(Color.white);
        if(this.gameCore.getLevel() != 1){
            Font f = new Font("Comic Sans MS", Font.BOLD, 20);
            crayon.setFont(f);
            crayon.drawString("Level : " + Integer.toString(this.gameCore.getLevel()),155, 180);
            crayon.drawString("Score : " + Integer.toString(this.gameCore.getScore()),135, 200);
            crayon.drawString("Score bonus : " + Integer.toString(this.gameCore.getBonusScore()), 115, 220);
        }
        else {
            Font f = new Font("Comic Sans MS", Font.BOLD, 20);
            crayon.setFont(f);
            crayon.drawString("Level : " + Integer.toString(this.gameCore.getLevel()),155, 180);
        }

        crayon.drawString("Press Space to start...", 85, 350);
    }




}
