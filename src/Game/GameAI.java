package Game;

import engine.AI;
import engine.Cmd;
import engine.HitBox;
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
    protected boolean monsterStateChanged  =false;
    protected int timer = 0;
    protected int currentLevel = 0;
    protected int[] spawnTimePerLevel = {10000, 8000, 5000, 5000};


    public GameAI(ArrayList monsters, Map gameMap, int level){
        this.monsters = monsters;
        this.gameMap = gameMap;
        this.currentLevel = level;
    }

    @Override
    public void spawnMonster(){
        int number = this.monsters.size();
        Entity monster = new EntityMonster();
        if(this.monsterStateChanged) {monster.changeInvicibleState(false);}
        monster.setCommand(Cmd.DOWN);
        this.monsters.add(number, monster);
        this.ellapsedTimeMillis = 0;
    }

    @Override
    public void handleMonster(){
        this.checkMonsterLife();
        if(ellapsedTimeMillis == this.spawnTimePerLevel[this.currentLevel - 1]) {this.spawnMonster();}
        for (Object monster : this.monsters) {
            this.randomMonsterDeplacement((Entity) monster);
        }
        this.ellapsedTimeMillis = this.ellapsedTimeMillis + 50;

        if(this.monsterStateChanged){
            this.timer = this.timer + 50;
            if (this.timer == 15000) {
                this.changeMonstersInvicibleState(true);
                this.timer= 0;
            }
        }

    }

    private void checkMonsterLife(){
        for (int i = 0 ; i< this.monsters.size() ; i++) {
            Entity monster = (Entity) this.monsters.get(i);
            if(monster.hasDied()) {this.monsters.remove(i);}
        }
    }

    @Override
    public void changeMonstersInvicibleState(boolean state){
        for (Object o : this.monsters){
            Entity monstre = (Entity) o;
            ((Entity) o).changeInvicibleState(state);
        }
        if (!state) {this.monsterStateChanged = true;}
        else{this.monsterStateChanged = false;}
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
        int[] monsterPos = monster.getPosition();
        ArrayList dirPossibilities = new ArrayList();
        int x = monsterPos[0];
        int y = monsterPos[1];
        for (Cmd cmd: Cmd.values()){

            if (cmd != Cmd.IDLE && cmd != Cmd.SPACE){

                int[] tab = GameCoreController.move(cmd);

                HitBox box = new HitBox(x + tab[0], y + tab[1], 20, 20);
                if(this.gameMap.isOnStructure(box)) {dirPossibilities.add(cmd);}
            }
        }
        return dirPossibilities;
    }

}
