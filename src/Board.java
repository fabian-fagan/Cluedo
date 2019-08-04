import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Board {
	Map<Integer, Cell> playerSpawns   = new HashMap<Integer, Cell>();
	Map<Integer, Cell> roomEntrances = new HashMap<Integer, Cell>();
	int entranceCount;
	int spawnCount;
	private Game game;
	private List<List<Cell>> cells;
	private List<Player> players;
	
	public static final List<Weapon> weapons = new ArrayList<Weapon>();
	static {
		weapons.add(new Weapon("Candlestick"));
		weapons.add(new Weapon("Dagger"));
		weapons.add(new Weapon("Lead Pipe"));
		weapons.add(new Weapon("Revolver"));
		weapons.add(new Weapon("Rope"));
		weapons.add(new Weapon("Spanner"));
	}
	
	public static final List<Character> characters = new ArrayList<Character>();
	static {
		characters.add(new Character("Mrs. White"));
		characters.add(new Character("Mr. Green"));
		characters.add(new Character("Mrs. Peacock"));
		characters.add(new Character("Prof. Plum"));
		characters.add(new Character("Miss Scarlett"));
		characters.add(new Character("Col. Mustard"));
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
	 * - = out of bounds
	 * K = kitchen
	 * A = ballroom
	 * C = conservatory
	 * B = billiard room
	 * L = library
	 * S = study
	 * H = hall
	 * O = lounge
	 * D = dining room
	 * - = out of bounds
	 * # = floor
	 * / = wall
	 */
	private void createBoard() {
		cells = new ArrayList<List<Cell>>();
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
		            // construct the right Cell for the leading character
		            // add it to a map if it is a special cell
		            switch (token.charAt(0)) {
		              case '-':	// out of bounds
		            	  newCell = new Cell('-');
		            	  break;
		              case '#': // floor
		            	  newCell = new Cell('#');
		            	  break;
		              case 'P': // Player Spawn
			              newCell = new Cell('#');
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
		                }
		                Room newRoom;
		                //if (roomID==5) newRoom = new AccusationRoom();
		                //else           
		                newRoom = new Room(roomName);
		                rooms.add(newRoom);
		                newCell = new Cell(roomID);
		                break;
		              default:
		                System.out.println("Unknown Tile: " + token.charAt(0));
		            }
		            boardRow.add(newCell);
		          }
		        }
		        cells.add(boardRow);
		      }
		    }
		    catch (IOException e) { e.printStackTrace(); }
		    
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
}
