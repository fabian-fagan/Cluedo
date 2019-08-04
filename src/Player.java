import java.util.ArrayList;
import java.util.List;

public class Player {
	private String name;
	private int playerID;
	private List<Card> hand;
	private Cell pos; //position on the board
	private int moves; //moves determined from the roll of the dice
	private Game game;
	
	public Player(String name, int pID, Game game) {
		this.name = name;
		this.playerID = pID;
		this.game = game;
		this.hand = new ArrayList<Card>();
	}
	
	public Cell getPos() {
		return pos;
	}
	
	public void setPos(Cell pos) {
		this.pos = pos;
	}
	
	public String toString() {
		return name;
	}
	
	public char getCharID() {
		return (char)(playerID+1+'0');
	}
	
	public char getInit() {
		return name.charAt(0);
	}
	
	public void addToHand(Card c) {
		if(c != null) this.hand.add(c);
		else System.out.println("Card is null");
	}
	
	public String printHand() {
		String s = "";
		for (Card c : hand) {
			s = s + c.getName() + ", ";
		}
		return s;
	}
	
	public void newTurn() {
		
		Dice dice = new Dice();
		int roll = dice.roll();
	}
	
	public void spawn(Cell spawnPos) {
	    pos = spawnPos;
	    spawnPos.enter(Player.this, null);
	  }
}
