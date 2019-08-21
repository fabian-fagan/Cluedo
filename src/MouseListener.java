import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class MouseListener extends MouseAdapter {
	private Board board;
	private Game game;
	private Cell cell;

	public MouseListener(Board b, Game g) {
		this.board = b;
		this.game = g;

	}

	public MouseListener(Board b, Game g, Cell c) {
		this.board = b;
		this.game = g;
		this.cell = c;

	}

	/*public void mouseClicked(MouseEvent e) {

		Player p = game.getCurrentPlayer();
		Cell pCell = p.getPos();
		List<List<Cell>> cells = board.getCells();
		for (int rowList = 0; rowList < cells.size() - 1; rowList++) {
			List<Cell> row = cells.get(rowList);
			for (int col = 0; col < row.size() - 1; col++) {
				if (row.get(col).getPlayer() != null) {
					if (row.get(col).getPlayer().toString().equals(p.getName())) { // if cell contains player
						if ((row.get(col).getX() > e.getX())) { // if mouse is clicked to the left of player
							p.moveLeft();

						}
						if (row.get(col).getX() < e.getX() ) { // if mouse is clicked to the right of player
							p.moveRight();

						}
						/
						 if ((row.get(col).getY() > e.getY())) {
						 p.moveDown();
						 }
						 if ((row.get(col).getY() < e.getY())) {
						 p.moveUp();

						 }  //need to fix this
					}
				}
			}
		}
	}*/

	public void mouseClicked(MouseEvent e){
		Player p;
		Cell pCell;
		p = game.getCurrentPlayer();
		pCell = p.getPos();
		int cellWidth = game.getBoard().getCellWidth();
		int cellHeight = game.getBoard().getCellHeight();
		int cellX = pCell.getX()/cellWidth;
		int cellY = pCell.getY()/cellHeight;
		int mouseX = e.getX() / cellWidth;
		int mouseY = e.getY() / cellHeight - 3; // weird offset of 3 for the position of the mouse in relation to the cells y
		if(mouseX > cellX && mouseY == cellY){
			p.moveRight();
			p.hasMoved();
		}
		else if(mouseX < cellX && mouseY == cellY){
			p.moveLeft();
			p.hasMoved();
		}
		else if(mouseX == cellX && mouseY > cellY){
			p.moveDown();
			p.hasMoved();
		}
		else if(mouseX == cellX && mouseY < cellY){
			p.moveUp();
			p.hasMoved();
		}
	}
}
