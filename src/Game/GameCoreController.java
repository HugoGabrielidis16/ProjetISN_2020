package Game;

import java.awt.event.KeyEvent;

import engine.Cmd;
import engine.GameController;


/**
 * @author Horatiu Cirstea, Vincent Thomas
 *
 * controleur de type KeyListener
 *
 */
public class GameCoreController implements GameController {

    /**
     * commande en cours
     */
    private Cmd commandeEnCours;

    /**
     * construction du controleur par defaut le controleur n'a pas de commande
     */
    public GameCoreController() {
        this.commandeEnCours = Cmd.IDLE;
    }

    /**
     * quand on demande les commandes, le controleur retourne la commande en
     * cours
     *
     * @return commande faite par le joueur
     */
    public Cmd getCommand() {
        return this.commandeEnCours;
    }

    @Override
    /**
     * met a jour les commandes en fonctions des touches appuyees
     */
    public void keyPressed(KeyEvent e) {
        // si on appuie sur 'q',commande joueur est gauche

        switch (e.getKeyChar()) {
            case 'd', 'D' -> this.commandeEnCours = Cmd.RIGHT;
            case 'q', 'Q' -> this.commandeEnCours = Cmd.LEFT;
            case 's', 'S' -> this.commandeEnCours = Cmd.DOWN;
            case 'z', 'Z' -> this.commandeEnCours = Cmd.UP;
        }

    }


    public static int [] move(Cmd cmd){
        int x=0;
        int y=0;
        switch (cmd) {
            case DOWN -> y =y + 10;
            case UP -> y = y - 10;
            case RIGHT -> x = x + 10;
            case LEFT -> x = x - 10;
        }
        return new int[]{x,y};
    }

    @Override
    /**
     * met a jour les commandes quand le joueur relache une touche
     */
    public void keyReleased(KeyEvent e) {
        this.commandeEnCours = Cmd.IDLE;
    }

    @Override
    /**
     * ne fait rien
     */
    public void keyTyped(KeyEvent e) {

    }

}

