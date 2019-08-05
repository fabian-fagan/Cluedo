import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class Game {
	
	/* =========== fields =========== */
	private List<Player> players;
	private Player currentPlayer;
	private int playerID, playerCount;
	private Suggestion solution;
	private Board board;
	private List<Card> cards;
	private List<Weapon> weapons;
	private List<Character> characters;
	private List<Room> rooms;
	
	public Game() {
		board = new Board();
		playerID = 0;
		/* Initialize all weapon, character and room card lists
		 * Doing both individual lists of weapon, character and room objects as well as
		 * a single list of all cards for testing purposes to see which is better
		 */
		
		players = new ArrayList<Player>();
		weapons = new ArrayList<Weapon>(board.weapons);
		characters = new ArrayList<Character>(board.characters);
		rooms = new ArrayList<Room>(board.rooms);
		cards = new ArrayList<Card>(board.cards);
		Collections.shuffle(weapons);
		Collections.shuffle(rooms);
		Collections.shuffle(characters);
		Collections.shuffle(cards);
		
		solution = new Suggestion(
				weapons.remove(0),
				characters.remove(0),
				rooms.remove(0)
				);
		
		playerCount = 0;
	    while (playerCount < 3) {
	    	Scanner sc = new Scanner(System.in);
	    	System.out.println("How many players? (3-6)");
	    	playerCount = sc.nextInt();
	    	System.out.println("Amount of players chosen: " + playerCount);
	    }
	    for(int i = 0; i<playerCount; i++) {
	    	players.add(new Player(characters.get(i).getName(), i, this));
	    	System.out.println("Player " + (i+1) + ": " + players.get(i).toString());
	    }
	    
	    // Deal the cards, the cards list has already been shuffled
	    playerID = 0;
	    int cardsInHand = cards.size()/playerCount;
	    for (int i = 0; i<playerCount; i++) {
	    	for (int j = 0; j<cards.size()/playerCount; j++) {
	    		players.get(i).addToHand(cards.get(j + i*cardsInHand));
	    	}
	    }
	    // debugging purposes, check the hand of the first player
	    System.out.println(players.get(0).printHand());
	    
	    for (int i=0; i<playerCount; ++i)
	        players.get(i).spawn(board.playerSpawns.get(i));
	    
	    System.out.println(board.toString());
	    
	    currentPlayer = players.get(0);
	    currentPlayer.newTurn();
	}
	public Board getBoard() {
		return board;
	
	}
	
	public void setBoard(Board b) {
		board = b; 
		
		
	}
	
	public static void main(String[] args) {
		new Game();
	}
}
