package Game;

import engine.*;
import entity.Entity;
import entity.EntityPlayer;
import object.*;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.FileNotFoundException;
import java.util.ArrayList;


public class GameCore implements Game {

    private int level = 1;
    private boolean hasMapBeenLoaded = false;
    private ArrayList monsters = new ArrayList();
    private ArrayList objects = new ArrayList();
    private EntityPlayer player = new EntityPlayer();
    private engine.Map gameMap;
    private AI gameAI;
    private ObjectHandler objectHandler;
    private int score = -300;
    private int ellapsedTime = 0;
    private boolean levelScreen = true;


    public GameCore() throws FileNotFoundException {
        this.loadGameLevel(level);
    }

    @Override
    public boolean hasMapBeenDrawedYet(){
        return this.hasMapBeenLoaded;
    }

    @Override
    public void drawMap(BufferedImage im){
        this.gameMap.draw(im);
        this.hasMapBeenLoaded = true;
    }

    @Override
    public void drawPlayer(BufferedImage im){
        this.player.draw(im);
    }

    @Override
    public void drawMonsterAndObjects(BufferedImage im){
        this.gameAI.draw(im);
        this.objectHandler.draw(im);
    }

    @Override
    public void evolve(Cmd commande) throws FileNotFoundException {

        if (!this.levelScreen){
            this.playerCommandHandler(commande);

            this.objectHandler.run();
            this.gameAI.handleMonster();
            //System.out.println("Execute "+commande);

            this.entityHitsHandler();

            if(this.objectHandler.hasWhiteBallsBeenEaten()){
                this.changeGameLevel();
                this.levelScreen = true;

                this.ellapsedTime = this.ellapsedTime + 50;
            }

        }
        else if (commande == Cmd.SPACE && this.levelScreen) {
            this.levelScreen = false;
        }


    }

    private void levelScreenFlip (){
        this.levelScreen = true;
    }

    @Override
    public boolean isLevelScreenInGoing(){
        return this.levelScreen;
    }

    /**
     * verifier si le jeu est fini
     */
    @Override
    public boolean isFinished() {
        if(this.player.hasDied()) {return true;}
        return false;
    }

    private void entityHitsHandler(){
        HitBox playerHitBox = this.player.getHitBox();

        for (Object m : this.monsters){
            Entity monster = (Entity) m;
            if (playerHitBox.hitWithAnotherHitBox(monster.getHitBox())){
                if (this.player.getInvicibleState()) {
                    if (!this.player.cantKill()) {
                        monster.killEntity();
                        this.score = this.score + 1000;
                    }
                }
                else {this.player.killEntity();}
            }
        }

        for (Object o : this.objects){
            GameObject gameObject = (GameObject) o;
            if (playerHitBox.hitWithAnotherHitBox(gameObject.getHitBox())){
                if (ObjectRedBall.class.equals(gameObject.getClass()) && !this.player.getInvicibleState()) {
                    this.player.makePlayerInvicible(15000);
                    this.gameAI.changeMonstersInvicibleState(false);
                    this.score = this.score + 100;
                    gameObject.killObject();
                }
                else if (ObjectWhiteBall.class.equals(gameObject.getClass())) {
                    this.score = this.score + 50;
                    this.objectHandler.decrementWhiteBallNumber();
                    gameObject.killObject();
                }
                else if (ObjectYellowBall.class.equals(gameObject.getClass())) {
                    if (!(this.player.getLife() == 3)){
                        this.player.addLife();
                        gameObject.killObject();
                    }
                }
            }
        }
    }

    @Override
    public void drawScoreAndLives(BufferedImage im){
        Graphics2D crayon = (Graphics2D) im.getGraphics();
        crayon.setColor(Color.orange);
        crayon.drawString("Score : " + Integer.toString(this.score), 10, 395);
        crayon.drawString("Vie(s) : " + Integer.toString(this.player.getLife()), 340, 395);
    }

    private void changeGameLevel() throws FileNotFoundException {
        this.level = this.level + 1;
        this.hasMapBeenLoaded = false;
        this.loadGameLevel(this.level);
    }

    @Override
    public int getScore(){
        return this.score;
    }

    @Override
    public int getBonusScore(){
        return this.calculScore();
    }

    @Override
    public int getLevel(){
        return this.level;
    }


    private void clearEntities(){
        this.monsters = new ArrayList();
        this.objects = new ArrayList();
        this.player.setPosition (190, 170);
    }

    private void loadGameLevel(int level) throws FileNotFoundException {
        if(this.level != 1) {this.score = this.score + calculScore();}
        this.ellapsedTime =   0;
        
        this.clearEntities();
        this.loadGameMap(level);
        this.gameAI = new GameAI(this.monsters, this.gameMap, this.level);
        this.objectHandler = new ObjectHandler(this.objects, this.gameMap, this.level);
    }

    private void loadGameMap(int level) throws FileNotFoundException {
        this.gameMap = new GameCoreMap(level);
    }

    private int calculScore (){
        String resultat = String.valueOf(this.score * (1 - 1/(1 + this.ellapsedTime*0.001)));
        String delims = "[.]+";
        String[] tokens = resultat.split(delims);
        return Integer.parseInt(tokens[0]);
    }

    private void playerCommandHandler(Cmd cmd){
        int [] playerPosition =this.player.getPosition();
        int [] tab= GameCoreController.move(cmd);
        if(this.gameMap.isOnStructure(new HitBox(playerPosition[0] + tab[0],playerPosition[1] + tab[1],20,20))) {this.player.setCommand(cmd);}
    }

}
