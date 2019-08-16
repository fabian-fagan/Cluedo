import java.awt.Color;
import java.awt.Graphics;

public class FloorCell extends Cell implements Drawable {

    public FloorCell(char name, Board b) {
        super(name, b);
    }

    @Override
    public void draw(Graphics g, int x, int y, int width, int height) {
        g.setColor(Color.blue);
        g.fillRect(x, y, width, height);
        g.setColor(Color.black);
        g.drawRect(x, y, width-1, height-1);
    }
}
