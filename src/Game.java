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
		weapons = new ArrayList<Weapon>();
		characters = new ArrayList<Character>();
		rooms = new ArrayList<Room>();
		cards = new ArrayList<Card>();
		players = new ArrayList<Player>();
		weapons.add(new Weapon("Candlestick"));
		weapons.add(new Weapon("Dagger"));
		weapons.add(new Weapon("Lead Pipe"));
		weapons.add(new Weapon("Revolver"));
		weapons.add(new Weapon("Rope"));
		weapons.add(new Weapon("Spanner"));
		
		characters.add(new Character("Mrs. White"));
		characters.add(new Character("Mr. Green"));
		characters.add(new Character("Mrs. Peacock"));
		characters.add(new Character("Prof. Plum"));
		characters.add(new Character("Miss Scarlett"));
		characters.add(new Character("Col. Mustard"));
		
		rooms.add(new Room("Kitchen"));
		rooms.add(new Room("Ball Room"));
		rooms.add(new Room("Conservatory"));
		rooms.add(new Room("Billiard Room"));
		rooms.add(new Room("Library"));
		rooms.add(new Room("Study"));
		rooms.add(new Room("Hall"));
		rooms.add(new Room("Lounge"));
		rooms.add(new Room("Dining Room"));
		
		for (Weapon w : weapons) {
			cards.add(w);
		}
		
		for (Character c : characters) {
			cards.add(c);
		}
		
		for (Room r : rooms) {
			cards.add(r);
		}
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
	    	players.add(new Player(characters.get(i).getName(), this));
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
	    
	    currentPlayer = players.get(0);
	    currentPlayer.newTurn();
	}
	
	public static void main(String[] args) {
		new Game();
	}
}
