package Game;

import engine.FileReader;
import engine.Map;

import java.awt.*;
import java.util.ArrayList;
import java.awt.image.BufferedImage;
import java.io.FileNotFoundException;

public class GameCoreMap implements Map {

    private final FileReader reader;
    private final ArrayList polygonsStructure = new ArrayList();

    public GameCoreMap(int level) throws FileNotFoundException {
        this.reader = new FileReader("src/levelsfile/level"+Integer.toString(level)+".txt");
        this.getMapStructure();
    }

    private void getMapStructure(){
        this.getPolygonStructure(this.reader.getFileContent());
        this.reader.close();
    }

    private void getPolygonStructure(ArrayList mapStructure){

        int[] firstPos = null;
        int k = 0;
        ArrayList x = new ArrayList<Integer>();
        ArrayList y = new ArrayList<Integer>();

        for (int i = 0; i < mapStructure.size() ; i++) {

            int[] pos = (int[]) mapStructure.get(i);

            x.add(k, pos[0]);
            y.add(k, pos[1]);
            k++;

            if (firstPos != null){

                if (firstPos[0] == pos[0] && firstPos[1] == pos[1]) {
                    this.polygonsStructure.add(new Polygon(this.changeArrayListToList(x), this.changeArrayListToList(y), x.size()));
                    x.clear();
                    y.clear();
                    firstPos = new int[] {5000, 5000};
                    k=0;
                }
            }

            if (firstPos == null) {
                firstPos = new int[] {pos[0], pos[1]};
            }
            else if (firstPos[0] == 5000 && firstPos[1] == 5000){
                firstPos = null;
            }

        }

    }

    private int[] changeArrayListToList(ArrayList list){
        int[] res = new int[list.size()];

        for (int i = 0 ; i < list.size() ; i++){
            res[i] = (int) list.get(i);
        }
        return res;
    }

    @Override
    public boolean isOnStructure(int[] pos){
        //HITBOX POINT
        int x = pos[0];
        //35 PEUT CHANGER SI ON CHANGE LA TAILLE DU PERSO
        int xp = x + 35;
        int y = pos[1];
        int yp = y + 35;

        if (0 <= y && y <= 760 && 0 <= x && x <= 760)
        {
            for(int i = 0 ; i < this.polygonsStructure.size() ; i++) {
                Polygon poly = (Polygon) this.polygonsStructure.get(i);
                if (poly.contains(x, y) || poly.contains(xp, yp) || poly.contains(xp, y) || poly.contains(x, yp)) {return false;}
            }
        }
        else {return false;}
        return true;
    }

    @Override
    public void draw(BufferedImage im) {

        for(int i = 0 ; i < this.polygonsStructure.size() ; i++) {
            Polygon poly = (Polygon) this.polygonsStructure.get(i);
            Graphics2D crayon = (Graphics2D) im.getGraphics();
            crayon.setColor(Color.darkGray);
            crayon.fillPolygon(poly);
            crayon.setColor(Color.black);
            crayon.drawPolygon(poly);
        }
    }
}
