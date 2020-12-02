package Game;

import engine.FileReader;
import engine.Map;

import java.awt.*;
import java.util.ArrayList;
import java.awt.image.BufferedImage;
import java.io.FileNotFoundException;

public class GameCoreMap implements Map {

    private FileReader reader;
    private final ArrayList polygonsStructure = new ArrayList();

    public GameCoreMap(int level) throws FileNotFoundException {
        this.reader = new FileReader("src/levels/level"+Integer.toString(level)+".txt");
        this.getMapStructure();
    }

    private void getMapStructure() throws FileNotFoundException {
        ArrayList structureList = this.reader.getLevelStructure();

        for (int i = 0; i < structureList.size() ; i++) {
            String str = (String) structureList.get(i);

            String delims = "[:]+";
            String[] tokens = str.split(delims);

            delims = "[,]+";
            String temp = (String) tokens[1];
            String[] pos = temp.split(delims);
            temp = (String) tokens[2];
            String[] append = temp.split(delims);

            this.loadStructure((String) tokens[0], new int[] {Integer.parseInt((String) pos[0]), Integer.parseInt((String) pos[1])},
                    new int[] {Integer.parseInt((String) append[0]), Integer.parseInt((String) append[1])});
        }
    }

    private void loadStructure(String structure, int[] pos, int[] append) throws FileNotFoundException {
        this.reader = new FileReader("src/levels/structures/"+structure+".txt");
        this.getPolygonStructure(this.reader.getStructure(), pos, append);
        this.reader.close();
    }

    private void getPolygonStructure(ArrayList mapStructure, int[] position, int[] append){

        int[] firstPos = null;
        int k = 0;
        ArrayList x = new ArrayList<Integer>();
        ArrayList y = new ArrayList<Integer>();

        for (int i = 0; i < mapStructure.size() ; i++) {

            int[] pos = (int[]) mapStructure.get(i);

            if (pos[0] == 2000) {
                x.add(k, append[0]);
            }
            else {x.add(k, pos[0]);}
            if (pos[1] == 1000) {
                y.add(k, append[1]);
            }
            else {y.add(k, pos[1]);}
            k++;

            if (firstPos != null){

                if (firstPos[0] == pos[0] && firstPos[1] == pos[1]) {
                    this.polygonsStructure.add(new Polygon(this.changeArrayListToList(x, position[0]), this.changeArrayListToList(y, position[1]), x.size()));
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

    private int[] changeArrayListToList(ArrayList list, int pos){
        int[] res = new int[list.size()];

        for (int i = 0 ; i < list.size() ; i++){
            //ON MODIFIE LECHELLE RAMENEE AU PIXEL
            //WITDH = HEIGHT / 40
            res[i] = ((int) list.get(i) + pos ) * 40 ;
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
            Stroke stroke = new BasicStroke(1.5F, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND);

            crayon.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            crayon.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);

            crayon.setColor(Color.gray);
            crayon.fillPolygon(poly);
            crayon.setStroke(stroke);
            crayon.setColor(Color.black);
            crayon.drawPolygon(poly);

        }
    }
}
