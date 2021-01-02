package Game;

import engine.AI;
import engine.Cmd;
import engine.Game;
import engine.HitBox;
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
    private int score = 0;


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

        this.playerCommandHandler(commande);

        this.objectHandler.run();
        this.gameAI.handleMonster();
        //System.out.println("Execute "+commande);

        this.entityHitsHandler();

        if(this.objectHandler.hasWhiteBallsBeenEaten()){
            this.changeGameLevel();
        }

        this.score = this.score + 1;

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
                if (ObjectRedBall.class.equals(gameObject.getClass())) {
                    this.player.makePlayerInvicible(15000);
                    this.gameAI.changeMonstersInvicibleState(false);
                    this.score = this.score + 100;
                    gameObject.killObject();
                }
                if (ObjectWhiteBall.class.equals(gameObject.getClass())) {
                    this.score = this.score + 50;
                    gameObject.killObject();
                }
                if (ObjectYellowBall.class.equals(gameObject.getClass())) {
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
        this.loadGameLevel(this.level);
    }

    private void clearEntities(){
        this.monsters = new ArrayList();
        this.objects = new ArrayList();
        this.player = new EntityPlayer();
    }

    private void loadGameLevel(int level) throws FileNotFoundException {
        this.clearEntities();
        this.loadGameMap(level);
        this.gameAI = new GameAI(this.monsters, this.gameMap, this.level);
        this.objectHandler = new ObjectHandler(this.objects, this.gameMap, this.level);
    }

    private void loadGameMap(int level) throws FileNotFoundException {
        this.gameMap = new GameCoreMap(level);
    }

    private void playerCommandHandler(Cmd cmd){
        this.player.setCommand(cmd);
        if(!this.gameMap.isOnStructure(this.player.getHitBox())) {this.player.rollBackCommand(cmd);}
    }

}
