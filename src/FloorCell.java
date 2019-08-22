import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import javax.imageio.ImageIO;

public class FloorCell extends Cell implements Drawable {
	private BufferedImage image;
	private boolean animating;

	public FloorCell(char name, Board b) {
		super(name, b);
	}

	/**
	 * draws floor cells which can contain players
	 */
	@Override
	public void draw(Graphics g, int x, int y, int width, int height) {
		if (animating) {
			g.setColor(Color.LIGHT_GRAY);
			g.fillRect(x, y, width, height);
			g.setColor(Color.black);
			g.drawRect(x, y, width - 1, height - 1);
			try {
				TimeUnit.MILLISECONDS.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		animating = false;

		if (name == '-') {
			g.setColor(Color.BLACK);
			g.fillRect(x, y, width, height);
		}

		else {
			g.setColor(Color.YELLOW);
			g.fillRect(x, y, width, height);
			g.setColor(Color.black);
			g.drawRect(x, y, width - 1, height - 1);

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
			if (p.isEliminated()) { // crosses out eliminated players
				try {
					image = ImageIO.read(new File("cross.png"));
				} catch (IOException e) {
					e.printStackTrace();
				}
				g.drawImage(image, x, y, width, height, new ImagePanel());
			}

		}

	}

	public void animate() {
		animating = true;
	}
}
