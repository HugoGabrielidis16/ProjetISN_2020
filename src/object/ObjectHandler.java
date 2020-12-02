package object;

import engine.Map;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class ObjectHandler {

    protected final ArrayList objects;
    protected final Map gameMap ;

    public ObjectHandler(ArrayList objects, Map gameMap) {
        this.objects = objects;
        this.gameMap = gameMap;
        this.spawnObject("redBall",50 , 50);
        this.spawnObject("redBall",730 , 690);
    }

    private void checkObjectLife(){
        for (int i = 0 ; i< this.objects.size() ; i++) {
            GameObject object = (GameObject) this.objects.get(i);
            System.out.println(object.hasDied());
            if(object.hasDied()) {this.objects.remove(i);}
        }
    }

    public void spawnObject(String str, int x, int y){

        int number = this.objects.size();

        switch (str){
            case "redBall":
                this.objects.add(number, new ObjectRedBall(x, y));
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
