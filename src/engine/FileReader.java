package engine;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.File;

public class FileReader {

    private final Scanner lecteur;

    public FileReader(String fileName) throws FileNotFoundException {
        File fichier = new File(fileName);
        this.lecteur = new Scanner(fichier);
    }

    public ArrayList getStructure(){

        ArrayList res = new ArrayList();
        while(lecteur.hasNextInt()){
            int valeur = lecteur.nextInt();
            int valeur2 = lecteur.nextInt();
            res.add(new int[] {valeur, valeur2});
        }
        return res;
    }

    public ArrayList getLevelStructure() {
        ArrayList res = new ArrayList();
        while(lecteur.hasNext()){
            String str = lecteur.nextLine();
            if (!(str.equals("structurename:(posx,posy):(append_len_x,append_len_y)"))) {res.add(str);}
        }
        return res;
    }

    public void close(){
        this.lecteur.close();
    }

}
