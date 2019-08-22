import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/**
 * Key listener, controls shortcuts
 *
 */
public class KeyListener extends KeyAdapter {
	private Game game;

	public KeyListener(Board b, Game g) {
		this.game = g;

	}

	/**
	 *Activates shortcuts
	 */
	public void keyPressed(KeyEvent e) {
		char c = e.getKeyChar();
		if (c == 'k') {
			game.nextPlayer();
		}
		if (c == 'a') {
			game.getCurrentPlayer().makeAccusation();
		}
		if (c == 's') {
			game.getCurrentPlayer().makeSuggestion();
		}
	}

}
