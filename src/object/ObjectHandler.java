package object;

import engine.HitBox;
import engine.Map;

import javax.print.attribute.standard.JobKOctets;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class ObjectHandler {

    private final static int[] redBallNumberPerLevel = {6, 4, 2, 1};
    private final static int[] yellowBallNumberPerLevel = {3, 2, 1, 0};
    private int redBallSpawned = 0;
    private int yellowBallSpawned = 0;
    private int whiteBallSpawned = 1;
    protected final ArrayList objects;
    protected final Map gameMap ;

    public ObjectHandler(ArrayList objects, Map gameMap, int currentLevel) {
        this.objects = objects;
        this.gameMap = gameMap;
        this.randomObjectSpawner(currentLevel);
    }

    public boolean hasWhiteBallsBeenEaten(){
        if (this.whiteBallSpawned == 0){return true;}
        return false;
    }

    private void randomObjectSpawner(int level) {
        ArrayList coord = this.generateObjectCoordinates();
        boolean tooMuchRedBall = false;
        boolean tooMuchyellowBall = false;

        for (Object o : coord){
            int[] pos = (int[]) o;

            int choice = 0;
            boolean tantQue = true;

            while(tantQue){
                choice = this.randomizer();

                if (choice < 1000) {choice = 0;}
                else if (choice >= 1000 && choice < 2000) {choice = 1;}
                else if (choice >= 2000) {choice = 2;}

                if(choice == 1 ) {tantQue =false;}

                if ((choice == 0 && !tooMuchRedBall)){tantQue = false;}
                else if ((choice == 2 && !tooMuchyellowBall)){tantQue = false;}
            }

            switch (choice) {
                case 2 -> {
                    this.spawnObject("yellowBall", pos[0], pos[1]);
                    this.yellowBallSpawned = this.yellowBallSpawned + 1;
                    if (this.yellowBallSpawned == yellowBallNumberPerLevel[level - 1]) {
                        tooMuchyellowBall = true;
                    }
                }
                case 0 -> {
                    this.spawnObject("redBall", pos[0], pos[1]);
                    this.redBallSpawned = this.redBallSpawned + 1;
                    if (this.redBallSpawned == redBallNumberPerLevel[level - 1]) {
                        tooMuchRedBall = true;
                    }
                }
                case 1 -> {
                    this.spawnObject("whiteBall", pos[0], pos[1]);
                    this.whiteBallSpawned = this.whiteBallSpawned + 1;
                }
                }
            }
        }

        private int randomizer(){
            Random random = new Random();
            int choice = random.nextInt(15000);
            int choice2 = random.nextInt(15000);
            choice = (choice2 + choice) / 10;
            return choice;
        }

    private ArrayList generateObjectCoordinates() {

        ArrayList coordinates = new ArrayList();

        for (int x = 0 ; x <= 20 ; x++){
            for (int y = 0 ; y <= 20 ; y++){
                HitBox hitBox = new HitBox(x*20, y*20, 20, 20);
                if(this.gameMap.isOnStructure(hitBox)) {
                    coordinates.add(new int[] {x*20 + 5, y*20 + 5});
                }
            }
        }
        return coordinates;
    }

    private void checkObjectLife() {
        for (int i = 0; i < this.objects.size(); i++) {
            GameObject o = (GameObject) this.objects.get(i);
            if (o.hasBeenEaten()) {
                this.objects.remove(i);

                if (ObjectWhiteBall.class.equals(o.getClass())) {
                    this.whiteBallSpawned = this.whiteBallSpawned - 1;
                }
            }
        }
    }

    public void spawnObject(String str, int x, int y){

        int number = this.objects.size();

        switch (str){
            case "yellowBall":
                this.objects.add(number, new ObjectYellowBall(x, y));
            case "redBall":
                this.objects.add(number, new ObjectRedBall(x, y));
            case "whiteBall":
                this.objects.add(number, new ObjectWhiteBall(x, y));
        }
    }

    public void run(){
        this.checkObjectLife();
    }

    public void draw (BufferedImage im) {
        for (Object o : this.objects){
            GameObject gameObject = (GameObject) o;
            gameObject.draw(im);
        }
    }



}
