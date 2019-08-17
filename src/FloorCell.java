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
    	if (getPlayer() != null) {
			g.setColor(Color.RED);
			g.fillRect(x, y, width, height);

		}
    	
    	else {
    	g.setColor(Color.blue);
        g.fillRect(x, y, width, height);
        g.setColor(Color.black);
        g.drawRect(x, y, width-1, height-1);

    	}
    }
}
