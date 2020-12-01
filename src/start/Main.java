package start;

import Game.*;
import engine.Game;
import engine.GameController;
import engine.GamePainter;
import engine.GameEngineGraphical;

import java.io.FileNotFoundException;

/**
 * lancement du moteur avec le jeu
 */
public class Main {

	public static void main(String[] args) throws InterruptedException, FileNotFoundException {


		// creation du jeu particulier et de son afficheur
		Game game = new GameCore();
		GamePainter painter = new GameCorePainter(game);
		GameController controller = new GameCoreController();

		// classe qui lance le moteur de jeu generique
		GameEngineGraphical engine = new GameEngineGraphical(game, painter, controller);
		engine.run();
	}

}
