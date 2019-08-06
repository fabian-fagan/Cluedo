import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Board {
	Map<Integer, Cell> playerSpawns = new HashMap<Integer, Cell>();
	int spawnCount;
	private Game game;
	private List<List<Cell>> cells;
	private List<Player> players;
	private List<Cell> itemSpawn;
	private Map<Room, List<Cell>> cellRoom;
	private Map<Character, Room> roomMap;

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

	public Board() {
		createBoard();
		System.out.println(this.toString());
	}

	/**
	 * - = out of bounds K = kitchen A = ballroom C = conservatory B = billiard room
	 * L = library S = study H = hall O = lounge D = dining room - = out of bounds #
	 * = floor / = wall
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
							newCell = new Cell('#', this);
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
							newCell = new Cell(roomID, this);
							addToRoom(newCell, roomName);
							if (roomName == "Item Spawn")
								itemSpawn.add(newCell);
							break;
						default:
							System.out.println("Unknown Tile: " + token.charAt(0));
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
				ret += c.getName();
			}
			ret += '\n';
		}
		return ret;
	}

	public List<Cell> getItemSpawn() {
		return itemSpawn;
	}

	public List<List<Cell>> getCells() {
		return cells;
	}

	public void setCells(List<List<Cell>> c) {
		this.cells = c;
	}

}
