import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

/**
 * Mouse listener, controls movement of players through clicks 
 *
 */
public class MouseListener extends MouseAdapter {
	private Board board;
	private Game game;

	public MouseListener(Board b, Game g) {
		this.board = b;
		this.game = g;

	}


	public void mouseClicked(MouseEvent e) {
		Player p;
		Cell pCell;
		p = game.getCurrentPlayer();
		pCell = p.getPos();
		int cellWidth = game.getBoard().getCellWidth();
		int cellHeight = game.getBoard().getCellHeight();
		int cellX = pCell.getX() / cellWidth;
		int cellY = pCell.getY() / cellHeight;
		int mouseX = e.getX() / cellWidth;
		int mouseY = e.getY() / cellHeight - 3; // weird offset of 3 for the position of the mouse in relation to the
		// cells y
		if (mouseX > cellX && mouseY == cellY) {
			p.moveRight();
			p.hasMoved();
		} else if (mouseX < cellX && mouseY == cellY) {
			p.moveLeft();
			p.hasMoved();
		} else if (mouseX == cellX && mouseY > cellY) {
			p.moveDown();
			p.hasMoved();
		} else if (mouseX == cellX && mouseY < cellY) {
			p.moveUp();
			p.hasMoved();

		}}}