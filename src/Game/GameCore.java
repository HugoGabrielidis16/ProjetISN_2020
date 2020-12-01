package Game;

import engine.Cmd;
import engine.Game;
import entity.*;

import java.awt.image.BufferedImage;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;


public class GameCore implements Game {

    private int level = 1;
    private boolean hasMapBeenLoaded = false;
    private final static Entity player = new EntityPlayer();
    private final static Map <String, Entity> monsters = new HashMap<String, Entity>();
    private engine.Map gameMap;
    private int k = 0;


    public GameCore() throws FileNotFoundException {
        this.loadGameMap(level);
    }

    private void loadGameMap(int level) throws FileNotFoundException {
        this.gameMap = new GameCoreMap(level);
    }

    private void spawnMonster() {
        int size = this.monsters.size() + 1;
        this.monsters.put("monster" + Integer.toString(size), new EntityMonster());
    }

    private void deplaceMonster(){
        Entity monster = this.monsters.get("monster1");
        if (this.isEntityAllowedToMove(monster, Cmd.RIGHT)) {monster.setCommand(Cmd.RIGHT);}
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
    public void drawMonster(BufferedImage im){
        for (Entity i : this.monsters.values()){
            i.draw(im);
        }
    }

    @Override
    public void evolve(Cmd commande) {

        if (this.isEntityAllowedToMove(this.player, commande)) {this.player.setCommand(commande);}
        if(this.k == 0) {
            this.spawnMonster();
            this.k = this.k +1;
        }
        this.deplaceMonster();
        System.out.println("Execute "+commande);

    }

    private boolean isEntityAllowedToMove(Entity entity, Cmd cmd){
        int[] pos = entity.getPosition();
        int xp = pos[0];
        int yp = pos[1];

        switch (cmd) {
            case DOWN -> yp = yp + 10;
            case UP -> yp = yp - 10;
            case RIGHT -> xp = xp + 10;
            case LEFT -> xp = xp - 10;
        }
        return this.gameMap.isOnStructure(new int[] {xp, yp});
    }

    /**
     * verifier si le jeu est fini
     */
    @Override
    public boolean isFinished() {
        // le jeu n'est jamais fini
        return false;
    }
}
