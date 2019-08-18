import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class MouseListener extends MouseAdapter {
	private Board board;
	private Game game;

	public MouseListener(Board b, Game g) {
		this.board = b;
		this.game = g;

	}

	public void mouseClicked(MouseEvent e) {

		List<List<Cell>> cells = board.getCells();
		System.out.println(e.getX() + " " + e.getY());
		for (int rowList = 0; rowList < cells.size() - 1; rowList++) { // scans cells for x,y position (by rows)
			List<Cell> row = cells.get(rowList);
			for (int col = 0; col < row.size() - 1; col++) {
				if ((row.get(col).getX() == e.getX()) && (row.get(col).getY() == e.getY())) {
					System.out.println("found");

				}
			}   //feel free to delete this as I couldnt get it working (wanted it to move player if a cell is clicked on)
		}
	}

}
