package engine;

import java.awt.image.BufferedImage;
import java.io.FileNotFoundException;

/**
 * @author Horatiu Cirstea, Vincent Thomas
 *
 *         un jeu qui peut evoluer (avant de se terminer) sur un plateau width x
 *         height
 */
public interface Game {

	/**
	 * methode qui contient l'evolution du jeu en fonction de la commande
	 * 
	 * @param userCmd
	 *            commande utilisateur
	 */
	public void evolve(Cmd userCmd) throws FileNotFoundException;

	/**
	 * @return true si et seulement si le jeu est fini
	 */
	public boolean isFinished();

	public void drawPlayer(BufferedImage im);

	public void drawMonsterAndObjects(BufferedImage im);

	public void drawMap(BufferedImage im);

	public boolean hasMapBeenDrawedYet();

	public void drawScoreAndLives(BufferedImage im);
}
