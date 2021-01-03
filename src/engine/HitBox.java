package engine;

import java.awt.*;
import java.awt.image.BufferedImage;

public class HitBox {

    private final int[] xPoints;
    private final int[] yPoints;
    private Polygon poly;

    public HitBox(int x, int y,int WIDTH, int HEIGHT){

        int yp = y + HEIGHT ;
        int xp = x + WIDTH ;

        this.xPoints = new int[] {x, xp , xp, x, x};
        this.yPoints = new int[] {y, y , yp, yp, y};

        this.poly = new Polygon(this.xPoints, this.yPoints, xPoints.length);
    }

    public Polygon getHitBoxPolygon(){
        return this.poly;
    }

    public boolean hitWithAnotherHitBox(HitBox hit){

        int x = hit.getX();
        int xp = hit.getXp();
        int y= hit.getY();
        int yp = hit.getYp();

        if (this.poly.contains(x, y) || this.poly.contains(xp, yp) || this.poly.contains(xp, y) || this.poly.contains(x, yp)) {return true;}
        else{return false;}
    }

    public int getX(){
        return this.xPoints[0];
    }
    public int getXp(){
        return this.xPoints[1];
    }
    public int getY(){
        return this.yPoints[0];
    }
    public int getYp(){ return this.yPoints[3]; }

    public static void drawHitBox(HitBox hit, BufferedImage im){

            Graphics2D crayon = (Graphics2D) im.getGraphics();
            crayon.setColor(Color.black);
            crayon.fillPolygon(hit.getHitBoxPolygon());

    }
}
