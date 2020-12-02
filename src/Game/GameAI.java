package Game;

import engine.AI;
import engine.Cmd;
import engine.Map;
import entity.Entity;
import entity.EntityMonster;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Random;

public class GameAI implements AI {

    protected final ArrayList monsters;
    protected final Map gameMap ;
    protected int ellapsedTimeMillis = 0;

    public GameAI(ArrayList monsters, Map gameMap){
        this.monsters = monsters;
        this.gameMap = gameMap;
    }

    @Override
    public void spawnMonster(){
        int number = this.monsters.size();
        Entity monster = new EntityMonster();
        monster.setCommand(Cmd.DOWN);
        this.monsters.add(number, monster);
        this.ellapsedTimeMillis = 0;
    }

    @Override
    public void handleMonster(){
        this.checkMonsterLife();
        if(ellapsedTimeMillis == 10000) {this.spawnMonster();}
        for (int i = 0 ; i < this.monsters.size() ; i++){
            this.randomMonsterDeplacement((Entity) this.monsters.get(i));
        }
        this.ellapsedTimeMillis = this.ellapsedTimeMillis + 100;
    }

    private void checkMonsterLife(){
        for (int i = 0 ; i< this.monsters.size() ; i++) {
            Entity monster = (Entity) this.monsters.get(i);
            if(monster.hasDied()) {this.monsters.remove(i);}
        }
    }

    @Override
    public void draw(BufferedImage im){
        for (int i = 0 ; i < this.monsters.size() ; i++){
            Entity monster = (EntityMonster) this.monsters.get(i);
            monster.draw(im);
        }
    }

    private void randomMonsterDeplacement(Entity monster){
        monster.setCommand(this.chooseDir(this.checkMonsterDirPossibilities(monster), monster.getLastDir()));
    }

    private Cmd chooseDir(ArrayList dir, Cmd lastDir){
        if(dir.contains(lastDir)) {
            return lastDir;
        }
        else{
            Random random = new Random();
            int choice = random.nextInt(dir.size());
            return (Cmd) dir.get(choice);
        }
    }

    private ArrayList checkMonsterDirPossibilities(Entity monster){
        ArrayList dirPossibilities = new ArrayList();
        int[] monsterPos = monster.getPosition();
        int x = monsterPos[0];
        int y = monsterPos[1];
        for (Cmd cmd: Cmd.values()){
            if (cmd != Cmd.IDLE){
                switch (cmd) {
                    case DOWN -> y = y + 10;
                    case UP -> y = y - 10;
                    case RIGHT -> x = x + 10;
                    case LEFT -> x = x - 10;
                }
                if(this.gameMap.isOnStructure(new int[] {x, y})) {dirPossibilities.add(cmd);}
            }
        }
        return dirPossibilities;
    }

}
