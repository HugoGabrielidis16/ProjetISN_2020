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

    public ArrayList getFileContent(){

        ArrayList res = new ArrayList();
        while(lecteur.hasNextInt()){
            int valeur = lecteur.nextInt();
            int valeur2 = lecteur.nextInt();
            res.add(new int[] {valeur, valeur2});
        }
        return res;
    }

    public void close(){
        this.lecteur.close();
    }

}
