import java.util.ArrayList;
import java.util.List;

public class Player {
	private String name;
	private List<Card> hand;
	private Cell pos; //position on the board
	private int moves; //moves determined from the roll of the dice
	private Game game;
	
	public Player(String name, Game game) {
		this.name = name;
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
		
	}
}
