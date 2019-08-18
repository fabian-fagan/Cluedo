import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.File;
import java.io.IOException;

/**
 * Individual cell objects in the board. Contains information on its
 * player/weapon it contains or whether it is empty.
 *
 */
public class Cell {
	public char name;
	private Player player;
	private Weapon weapon;
	private Board board;
	static int width;
	static int height;
	private int x, y;
	private BufferedImage image;

	/**
	 * @param name
	 * @param b
	 */
	public Cell(char name, Board b) {
		super();
		this.name = name;
		this.board = b;
		// hardcoding width and height for now
		int width = 24;
		int height = 25;
	}

	public Cell(Player pl, char name) {
		this.player = pl;
		this.name = name;
	}

	/**
	 * use this to get the representation of the cell itself when a player is on it
	 * 
	 * @return cell representation
	 */
	public char getCellName() {
		return name;
	}

	public char name() {
		if (player == null && weapon == null)
			return name;
		if (player == null && weapon != null)
			return weapon.getID();
		else
			return player.getPlayID();
	}

	/**
	 * @return weapon in this cell
	 */
	public Weapon getWeapon() {
		return weapon;
	}

	public void setWeapon(Weapon w) {
		this.weapon = w;
	}

	/**
	 * @return the room this cell is in
	 */
	public Room getRoom() {
		return board.getRoom(this);
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	/**
	 * draw this cell on the given graphics pane
	 */
	public void draw(Graphics g, int x, int y, int width, int height) {
		this.x = x;
		this.y = y;

		if (name == '=') {
			g.setColor(Color.LIGHT_GRAY);
			g.fillRect(x, y, width, height);
			g.setColor(Color.black);
			g.drawRect(x, y, width - 1, height - 1);
		}

		else {
			g.setColor(Color.GRAY);
			g.fillRect(x, y, width, height);
		}

		if (getPlayer() != null) {
			g.setColor(Color.RED);
			g.fillRect(x, y, width, height);

		}
		if (name == '-') {
			g.setColor(Color.BLACK);
			g.fillRect(x, y, width, height);
		}
		if (weapon != null) {
			if (weapon.getName().contentEquals("Revolver")) {
				try {
					image = ImageIO.read(new File("revolver.png"));
				} catch (IOException e) {
					e.printStackTrace();
				}
				g.drawImage(image, x, y, width, height, new ImagePanel());

			}
			if (weapon.getName().contentEquals("Rope")) {
				try {
					image = ImageIO.read(new File("rope.png"));
				} catch (IOException e) {
					e.printStackTrace();
				}
				g.drawImage(image, x, y, width, height, new ImagePanel());

			}
			if (weapon.getName().contentEquals("Dagger")) {
				try {
					image = ImageIO.read(new File("dagger.png"));
				} catch (IOException e) {
					e.printStackTrace();
				}
				g.drawImage(image, x, y, width, height, new ImagePanel());

			}
			if (weapon.getName().contentEquals("Spanner")) {
				try {
					image = ImageIO.read(new File("spanner.png"));
				} catch (IOException e) {
					e.printStackTrace();
				}
				g.drawImage(image, x, y, width, height, new ImagePanel());

			}
			if (weapon.getName().contentEquals("Candlestick")) {
				try {
					image = ImageIO.read(new File("candlestick.png"));
				} catch (IOException e) {
					e.printStackTrace();
				}
				g.drawImage(image, x, y, width, height, new ImagePanel());

			}
			if (weapon.getName().contentEquals("Lead Pipe")) {
				try {
					image = ImageIO.read(new File("leadpipe.png"));
				} catch (IOException e) {
					e.printStackTrace();
				}
				g.drawImage(image, x, y, width, height, new ImagePanel());

			}

		}
	}

	/**
	 * checks if player can move to this cell
	 * 
	 * @param p
	 * @return
	 */
	public boolean canMoveHere(Player p) {
		if (p == player)
			return true; // player can stay in place
		return player == null;
	}

	/**
	 * checks if the cell is a room, door or part of the hallway
	 */
	public Boolean isTraversable() {
		if (name == '#' || name == '=') {
			return true;
		} else
			return false;
	}

	protected boolean movePlayer(Player p) {
		if (!canMoveHere(p))
			return false;
		addPlayer(p);
		return true;
	}

	public boolean enter(Player p, Game game) {
		return movePlayer(p);
	}

	protected void addPlayer(Player p) {
		this.player = p;
	}

	protected void remPlayer(Player p) {
		this.player = null;
	}

	public Player getPlayer() {
		if (player != null) {
			return this.player;
		} else
			return null;
	}

	public int getX() {
		return this.x;
	}

	public int getY() {
		return this.y;
	}

}
