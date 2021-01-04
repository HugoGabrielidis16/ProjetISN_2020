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
    private int whiteBallSpawned = 20;
    protected final ArrayList objects;
    protected final Map gameMap ;

    public ObjectHandler(ArrayList objects, Map gameMap, int currentLevel) {
        this.objects = objects;
        this.gameMap = gameMap;
        this.randomObjectSpawner(currentLevel);
    }

    public boolean hasWhiteBallsBeenEaten(){
        return this.whiteBallSpawned == 0;
    }

    private void randomObjectSpawner(int level) {
        ArrayList coord = this.generateObjectCoordinates();
        boolean tooMuchRedBall = false;
        boolean tooMuchYellowBall = false;

        for (Object o : coord){
            int[] pos = (int[]) o;

            int choice = randomizer(tooMuchYellowBall, tooMuchRedBall);

            switch (choice) {
                case 0 -> {
                    this.spawnObject("yellowBall", pos[0], pos[1]);
                    if (this.yellowBallSpawned == yellowBallNumberPerLevel[level - 1]) {
                        tooMuchYellowBall = true;
                    }
                }
                case 1 -> {
                    this.spawnObject("redBall", pos[0], pos[1]);
                    if (this.redBallSpawned == redBallNumberPerLevel[level - 1]) {
                        tooMuchRedBall = true;
                    }
                }
                case 2 -> {
                    this.spawnObject("whiteBall", pos[0], pos[1]);
                }
                }
            }

        //this.whiteBallSpawned = coord.size() - (redBallNumberPerLevel[level - 1] + yellowBallNumberPerLevel[level - 1] );

        }



        private int randomizer(boolean y, boolean r){
            Random random = new Random();
            int choice = 0;
            boolean tantQue = true;

            while (tantQue){
                choice = random.nextInt(3);
                if (choice==0 && !y){
                    tantQue=false;
                }
                else if (choice==1 && !r){
                    tantQue=false;
                }
                else if (choice==2){
                    tantQue=false;
                }
            }

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

    public void decrementWhiteBallNumber(){
        this.whiteBallSpawned = this.whiteBallSpawned - 1;
    }

    private void checkObjectLife() {
        for (int i = 0; i < this.objects.size(); i++) {
            GameObject o = (GameObject) this.objects.get(i);
            if (o.hasBeenEaten()) {

                this.objects.remove(i);
            }
        }
    }

    public void spawnObject(String str, int x, int y){

        int number = this.objects.size();

        switch (str) {
            case "yellowBall" -> {
                this.objects.add(number, new ObjectYellowBall(x, y));
                this.yellowBallSpawned = this.yellowBallSpawned + 1;
            }
            case "redBall" -> {
                this.objects.add(number, new ObjectRedBall(x, y));
                this.redBallSpawned = this.redBallSpawned + 1;
            }
            case "whiteBall" -> {
                this.objects.add(number, new ObjectWhiteBall(x, y));
            }
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
