import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 * Board class, controls parsing of board and item spawns.
 *
 */
public class Board extends JPanel {
	Map<Integer, Cell> playerSpawns = new HashMap<Integer, Cell>();
	int spawnCount;
	private Game game;
	private List<List<Cell>> cells;
	private List<Cell> itemSpawn;
	private Map<Room, List<Cell>> cellRoom;
	private Map<Character, Room> roomMap;
	private final int boardWidth = 24;
	private final int boardHeight = 25;
	private int cellWidth;
	private int cellHeight;
	private BufferedImage image;
	private BufferedImage image2;
	public static final List<Weapon> weapons = new ArrayList<Weapon>();
	static {
		weapons.add(new Weapon("Candlestick"));
		weapons.add(new Weapon("Dagger"));
		weapons.add(new Weapon("Lead Pipe"));
		weapons.add(new Weapon("Revolver"));
		weapons.add(new Weapon("Rope"));
		weapons.add(new Weapon("Spanner"));
	}

	public static final List<PCharacter> characters = new ArrayList<PCharacter>();
	static {
		characters.add(new PCharacter("Mrs. White"));
		characters.add(new PCharacter("Mr. Green"));
		characters.add(new PCharacter("Mrs. Peacock"));
		characters.add(new PCharacter("Prof. Plum"));
		characters.add(new PCharacter("Miss Scarlett"));
		characters.add(new PCharacter("Col. Mustard"));
	}

	public static final List<Room> rooms = new ArrayList<Room>();
	static {
		rooms.add(new Room("Kitchen"));
		rooms.add(new Room("Ball Room"));
		rooms.add(new Room("Conservatory"));
		rooms.add(new Room("Billiard Room"));
		rooms.add(new Room("Library"));
		rooms.add(new Room("Study"));
		rooms.add(new Room("Hall"));
		rooms.add(new Room("Lounge"));
		rooms.add(new Room("Dining Room"));
	}

	public static final List<Card> cards = new ArrayList<Card>();
	static {
		cards.addAll(rooms);
		cards.addAll(characters);
		cards.addAll(weapons);
		Collections.shuffle(cards);
	}

	public Board(Game game) {
		this.game = game;
		createBoard();
	}

	/**
	 * - = out of bounds K = kitchen A = ballroom C = conservatory B = billiard room
	 * L = library S = study H = hall O = lounge D = dining room - = out of bounds #
	 * = floor / = wall "=" --- Door to room
	 */
	private void createBoard() {
		itemSpawn = new ArrayList<Cell>();
		cells = new ArrayList<List<Cell>>();
		cellRoom = new HashMap<Room, List<Cell>>();
		roomMap = new HashMap<Character, Room>();
		for (Room r : rooms) {
			cellRoom.put(r, new ArrayList<Cell>());
		}
		try {
			// parse the board
			Scanner lineScan = new Scanner(new File("board.tab"));

			while (lineScan.hasNextLine()) {
				List<Cell> boardRow = new ArrayList<Cell>();
				for (String token : lineScan.nextLine().split("\t")) {
					if (token.isEmpty())
						boardRow.add(null);
					else {
						Cell newCell = null;
						switch (token.charAt(0)) {
						case '-': // out of bounds
							newCell = new Cell('-', this);
							break;
						case '#': // floor
							newCell = new FloorCell('#', this);
							break;
						case '=': // door
							newCell = new Cell('=', this);
							break;
						case 'P': // Player Spawn
							newCell = new Cell('P', this);
							playerSpawns.put(spawnCount, newCell);
							++spawnCount;
							break;
						case 'R': // Room
							char roomID = token.charAt(1);
							String roomName = "";
							switch (roomID) {
							case 'K': // Kitchen
								roomName = "Kitchen";
								break;
							case 'A': // Ballroom
								roomName = "Ballroom";
								break;
							case 'C':
								roomName = "Conservatory";
								break;
							case 'D':
								roomName = "Dining Room";
								break;
							case 'B':
								roomName = "Billiard Room";
								break;
							case 'O':
								roomName = "Lounge";
								break;
							case 'H':
								roomName = "Hall";
								break;
							case 'S':
								roomName = "Study";
								break;
							case '!':
								roomName = "Accusation Room";
								break;
							case 'I':
								roomName = "Item Spawn";
								break;
							}
							Room newRoom = new Room(roomName);
							roomMap.put(roomID, newRoom);
							newCell = new RoomCell(roomID, this);
							addToRoom(newCell, roomName);
							if (roomName == "Item Spawn")
								itemSpawn.add(newCell);
							break;
						default:
							game.displayMessage("Unknown Tile: " + token.charAt(0));
						}
						boardRow.add(newCell);
					}
				}
				cells.add(boardRow);
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
     
	}

	protected void paintComponent(Graphics g) {
		requestFocus(true);
		super.paintComponent(g);
		cellWidth = getWidth() / boardWidth;
		cellHeight = (getHeight() - 200) / boardHeight;
		paintBoard(g);

	}

	private void paintBoard(Graphics g) {
		// paints cells
		requestFocus(true);
		for (int x = 0; (x < boardWidth) && (x < cells.size() - 1); x++) {
			for (int y = 0; (y < boardHeight) && (y < cells.size() - 1); y++) {
				this.cells.get(y).get(x).draw(g, x * cellWidth, y * cellHeight, cellWidth, cellHeight); // draw cell
				this.cells.get(y).get(x).setX(x * cellWidth);
				this.cells.get(y).get(x).setY(y * cellHeight);

			}
		}
		// paints dice depending on current players roll
		int roll = 0;
		if (game.getCurrentPlayer() != null) {
			roll = game.getCurrentPlayer().getRoll();
		}
		if (roll == 1) {
			try {
				image = ImageIO.read(new File("dice1.png"));
			} catch (IOException e) {
				e.printStackTrace();
			}
			g.drawImage(image, 20, 500, 100, 100, new ImagePanel());
		}
		if (roll == 2) {
			try {
				image = ImageIO.read(new File("dice1.png"));
				image2 = ImageIO.read(new File("dice1.png"));
			} catch (IOException e) {
				e.printStackTrace();
			}
			g.drawImage(image, 20, 500, 100, 100, new ImagePanel());
			g.drawImage(image2, 140, 500, 100, 100, new ImagePanel());
		}
		if (roll == 3) {
			try {
				image = ImageIO.read(new File("dice1.png"));
				image2 = ImageIO.read(new File("dice2.png"));
			} catch (IOException e) {
				e.printStackTrace();
			}
			g.drawImage(image, 20, 500, 100, 100, new ImagePanel());
			g.drawImage(image2, 140, 500, 100, 100, new ImagePanel());
		}
		if (roll == 4) {
			try {
				image = ImageIO.read(new File("dice2.png"));
				image2 = ImageIO.read(new File("dice2.png"));
			} catch (IOException e) {
				e.printStackTrace();
			}
			g.drawImage(image, 20, 500, 100, 100, new ImagePanel());
			g.drawImage(image2, 140, 500, 100, 100, new ImagePanel());
		}
		if (roll == 5) {
			try {
				image = ImageIO.read(new File("dice3.png"));
				image2 = ImageIO.read(new File("dice2.png"));
			} catch (IOException e) {
				e.printStackTrace();
			}
			g.drawImage(image, 20, 500, 100, 100, new ImagePanel());
			g.drawImage(image2, 140, 500, 100, 100, new ImagePanel());
		}
		if (roll == 6) {
			try {
				image = ImageIO.read(new File("dice3.png"));
				image2 = ImageIO.read(new File("dice3.png"));
			} catch (IOException e) {
				e.printStackTrace();
			}
			g.drawImage(image, 20, 500, 100, 100, new ImagePanel());
			g.drawImage(image2, 140, 500, 100, 100, new ImagePanel());
		}
		if (roll == 7) {
			try {
				image = ImageIO.read(new File("dice4.png"));
				image2 = ImageIO.read(new File("dice3.png"));
			} catch (IOException e) {
				e.printStackTrace();
			}
			g.drawImage(image, 20, 500, 100, 100, new ImagePanel());
			g.drawImage(image2, 140, 500, 100, 100, new ImagePanel());
		}
		if (roll == 8) {
			try {
				image = ImageIO.read(new File("dice4.png"));
				image2 = ImageIO.read(new File("dice4.png"));
			} catch (IOException e) {
				e.printStackTrace();
			}
			g.drawImage(image, 20, 500, 100, 100, new ImagePanel());
			g.drawImage(image2, 140, 500, 100, 100, new ImagePanel());
		}
		if (roll == 9) {
			try {
				image = ImageIO.read(new File("dice5.png"));
				image2 = ImageIO.read(new File("dice4.png"));
			} catch (IOException e) {
				e.printStackTrace();
			}
			g.drawImage(image, 20, 500, 100, 100, new ImagePanel());
			g.drawImage(image2, 140, 500, 100, 100, new ImagePanel());
		}
		if (roll == 10) {
			try {
				image = ImageIO.read(new File("dice5.png"));
				image2 = ImageIO.read(new File("dice5.png"));
			} catch (IOException e) {
				e.printStackTrace();
			}
			g.drawImage(image, 20, 500, 100, 100, new ImagePanel());
			g.drawImage(image2, 140, 500, 100, 100, new ImagePanel());
		}
		if (roll == 11) {
			try {
				image = ImageIO.read(new File("dice6.png"));
				image2 = ImageIO.read(new File("dice5.png"));
			} catch (IOException e) {
				e.printStackTrace();
			}
			g.drawImage(image, 20, 500, 100, 100, new ImagePanel());
			g.drawImage(image2, 140, 500, 100, 100, new ImagePanel());
		}
		if (roll == 12) {
			try {
				image = ImageIO.read(new File("dice6.png"));
				image2 = ImageIO.read(new File("dice6.png"));
			} catch (IOException e) {
				e.printStackTrace();
			}
			g.drawImage(image, 20, 500, 100, 100, new ImagePanel());
			g.drawImage(image2, 140, 500, 100, 100, new ImagePanel());
		}
	}

	public void redraw() {
		repaint();
	}

	private void addToRoom(Cell c, String name) {
		for (Room r : rooms) {
			if (r.getName() == name) {
				cellRoom.get(r).add(c);
			}
		}
	}


	public Room getRoom(Cell c) {
		char cname = c.getCellName();
		return roomMap.get(cname);
	}

	public String toString() {
		String ret = "";
		for (List<Cell> row : cells) {
			for (Cell c : row) {
				ret += c.name();
			}
			ret += '\n';
		}
		return ret;
	}

	/**
	 * @return
	 */
	public List<Cell> getItemSpawn() {
		return itemSpawn;
	}

	/**
	 * @return
	 */


	public List<List<Cell>> getCells() {
		return cells;
	}

	public int getCellWidth(){
		return cellWidth;
	}

	public int getCellHeight(){
		return cellHeight;
	}

	/**
	 * @param c
	 */
	public void setCells(List<List<Cell>> c) {
		this.cells = c;
	}

}
