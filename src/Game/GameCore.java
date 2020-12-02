package Game;

import engine.AI;
import engine.Cmd;
import engine.Game;
import engine.HitBox;
import entity.Entity;
import entity.EntityPlayer;
import object.GameObject;
import object.ObjectHandler;
import object.ObjectRedBall;

import java.awt.image.BufferedImage;
import java.io.FileNotFoundException;
import java.util.ArrayList;


public class GameCore implements Game {

    private int level = 1;
    private boolean hasMapBeenLoaded = false;
    private ArrayList monsters = new ArrayList();
    private final ArrayList objects = new ArrayList();
    private final Entity player = new EntityPlayer();
    private engine.Map gameMap;
    private AI gameAI;
    private ObjectHandler objectHandler;


    public GameCore() throws FileNotFoundException {
        this.loadGameMap(level);
        this.gameAI = new GameAI(this.monsters, this.gameMap);
        this.objectHandler = new ObjectHandler(this.objects, this.gameMap);
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
    public void evolve(Cmd commande) {

        this.playerCommandHandler(commande);

        this.objectHandler.run();
        this.gameAI.handleMonster();
        //System.out.println("Execute "+commande);

        this.entityHitsHandler();

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
                if (this.player.getInvicibleState()) { monster.killEntity(); }
                else {this.player.killEntity();}
            }
        }

        for (Object o : this.objects){
            GameObject gameObject = (GameObject) o;
            if (playerHitBox.hitWithAnotherHitBox(gameObject.getHitBox())){
                gameObject.killObject();
                if (ObjectRedBall.class.equals(gameObject.getClass())) {
                    this.player.changeInvicibleState(true);
                }
            }
        }
    }

    private void loadGameMap(int level) throws FileNotFoundException {
        this.gameMap = new GameCoreMap(level);
    }

    private void playerCommandHandler(Cmd cmd){
        this.player.setCommand(cmd);
        if(!this.gameMap.isOnStructure(this.player.getPosition())) {this.player.rollBackCommand(cmd);}
    }

}
