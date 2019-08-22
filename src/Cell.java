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
	private boolean animating = false;

	/**
	 * @param name
	 * @param b
	 */
	public Cell(char name, Board b) {
		super();
		this.name = name;
		this.board = b;
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
	 * draw this cell on the given graphics pane, depending on its contents
	 */
	public void draw(Graphics g, int x, int y, int width, int height) {
		this.x = x;
		this.y = y;
		if (animating) {
			g.setColor(Color.LIGHT_GRAY);
			g.fillRect(x, y, width, height);
			g.setColor(Color.black);
			g.drawRect(x, y, width - 1, height - 1);
		}
		animating = false;
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
			if (p.isEliminated()) { // cross out player if they are eliminated
				try {
					image = ImageIO.read(new File("cross.png"));
				} catch (IOException e) {
					e.printStackTrace();
				}
				g.drawImage(image, x, y, width, height, new ImagePanel());
			}

		}
		if (name == '-') {
			g.setColor(Color.ORANGE);
			g.fillRect(x, y, width, height);
		}
		if (weapon != null) {
			if (weapon.getName().equals("Revolver")) {
				try {
					image = ImageIO.read(new File("revolver.png"));
				} catch (IOException e) {
					e.printStackTrace();
				}
				g.drawImage(image, x, y, width, height, new ImagePanel());

			}
			if (weapon.getName().equals("Rope")) {
				try {
					image = ImageIO.read(new File("rope.png"));
				} catch (IOException e) {
					e.printStackTrace();
				}
				g.drawImage(image, x, y, width, height, new ImagePanel());

			}
			if (weapon.getName().equals("Dagger")) {
				try {
					image = ImageIO.read(new File("dagger.png"));
				} catch (IOException e) {
					e.printStackTrace();
				}
				g.drawImage(image, x, y, width, height, new ImagePanel());

			}
			if (weapon.getName().equals("Spanner")) {
				try {
					image = ImageIO.read(new File("spanner.png"));
				} catch (IOException e) {
					e.printStackTrace();
				}
				g.drawImage(image, x, y, width, height, new ImagePanel());

			}
			if (weapon.getName().equals("Candlestick")) {
				try {
					image = ImageIO.read(new File("candlestick.png"));
				} catch (IOException e) {
					e.printStackTrace();
				}
				g.drawImage(image, x, y, width, height, new ImagePanel());

			}
			if (weapon.getName().equals("Lead Pipe")) {
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

	public void setX(int x) {
		this.x = x;
	}

	public void setY(int y) {
		this.y = y;
	}

	public void animate() {
		animating = true;
	}

}
