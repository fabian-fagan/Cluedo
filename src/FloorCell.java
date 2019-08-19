import java.awt.Color;
import java.awt.Graphics;

public class FloorCell extends Cell implements Drawable {

    public FloorCell(char name, Board b) {
        super(name, b);
    }

    @Override
    public void draw(Graphics g, int x, int y, int width, int height) {
    	if (name == '-') {
			g.setColor(Color.BLACK);
			g.fillRect(x, y, width, height);
		} 
    	
    	
    	else {
    	g.setColor(Color.YELLOW);
        g.fillRect(x, y, width, height);
        g.setColor(Color.black);
        g.drawRect(x, y, width-1, height-1);

    	}
    	
    	if (getPlayer() != null) {
    		Player p = getPlayer();
			if (p.getName().equals("Mrs. White")) {
				g.setColor(Color.WHITE);
				g.fillRect(x, y, width, height);
			}
			if (p.getName().equals("Mr. Green")) {
				g.setColor(Color.GREEN);
				g.fillRect(x, y, width, height);
			}
			if (p.getName().equals("Mrs. Peacock")) {
				g.setColor(Color.BLUE);
				g.fillRect(x, y, width, height);
			}
			if (p.getName().equals("Prof. Plum")) {
				g.setColor(Color.CYAN);
				g.fillRect(x, y, width, height);
			}
			if (p.getName().equals("Miss Scarlett")) {
				g.setColor(Color.RED);
				g.fillRect(x, y, width, height);
			}
			if (p.getName().equals("Col. Mustard")) {
				g.setColor(Color.PINK);
				g.fillRect(x, y, width, height);
			}

		}
    	
    }
}
