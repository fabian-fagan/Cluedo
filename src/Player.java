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
		System.out.println(name + ": " + roll + " moves (W - Up, A - Left, S - Down, D - Right)" );
		Scanner sc = new Scanner(System.in);
		for (int i = 0; i < roll; i++) {
			String direction = sc.next();
			if (direction.equalsIgnoreCase("W")) { 
			  moveUp();
			}
			
			if (direction.equalsIgnoreCase("A")) { 
			  moveLeft();  	
			}
			
		    if (direction.equalsIgnoreCase("S")) { 
		      moveDown();
		    }
		    
		    if (direction.equalsIgnoreCase("D")) { 
			  moveRight();
			 }
		}
	}
	
	public void spawn(Cell spawnPos) {
	    pos = spawnPos;
	    spawnPos.enter(Player.this, null);
	  }
	
	public void moveUp() {
		   Board board = this.game.getBoard();
		   List<List<Cell>> cells = board.getCells();
		   for (int rowList = 0; rowList < cells.size() - 1; rowList++) { //scans cells for player position (by rows)
			   List<Cell> row = cells.get(rowList);
			   for (int col = 0; col < row.size() - 1; col++ ) {
				   if (row.get(col).getPlayer() != null) {
				     if (row.get(col).getPlayer().toString().equals(name)) { //if cell contains player
					   if (rowList != 0) { //if player is not on top row 
						   this.setPos(cells.get(rowList - 1).get(col)); //move up
						   cells.get(rowList - 1).get(col).addPlayer(this);
						   cells.get(rowList).get(col).remPlayer(this);
					   }
					   else { System.out.println("Out of bounds.");}
				     }
				   }
			   }
		   }
		   board.setCells(cells);
		   game.setBoard(board); //set new board
		   System.out.println(board.toString()); //debug purposes
	   }
	   
	   public void moveDown() {
		   Board board = this.game.getBoard();
		   Boolean moved = false;
		   List<List<Cell>> cells = board.getCells();
		   for (int rowList = 0; rowList < cells.size() - 1; rowList++) { //scans cells for player position (by rows)
			   List<Cell> row = cells.get(rowList);
			   for (int col = 0; col < row.size() - 1; col++ ) {
				   if (row.get(col).getPlayer() != null) {
				     if (row.get(col).getPlayer().toString().equals(name)) { //if cell contains player
					   if (rowList != cells.size() - 1 && moved == false) { //if player is not on bottom row 
						   this.setPos(cells.get(rowList + 1).get(col)); //move down					   
						   cells.get(rowList + 1).get(col).addPlayer(this);
						   cells.get(rowList).get(col).remPlayer(this);
						   System.out.println(rowList + "," + col);
						   moved = true;
						   break;
					   }
					   else if (moved == false) { System.out.println("Out of bounds.");}
				     }
				   }
			   }
		   }
		   board.setCells(cells);
		   game.setBoard(board); //set new board
		   System.out.println(board.toString()); //debug purposes
	   }
		   
		   public void moveLeft() {
			   Board board = this.game.getBoard();
			   List<List<Cell>> cells = board.getCells();
			   for (int rowList = 0; rowList < cells.size() - 1; rowList++) { //scans cells for player position (by rows)
				   List<Cell> row = cells.get(rowList);
				   for (int col = 0; col < row.size() - 1; col++ ) {
					   if (row.get(col).getPlayer() != null) {
					     if (row.get(col).getPlayer().toString().equals(name)) { //if cell contains player
						   if (col != 0) { //if player is not left edge 
							   this.setPos(cells.get(rowList).get(col - 1)); //move left
							   cells.get(rowList).get(col - 1).addPlayer(this);
							   cells.get(rowList).get(col).remPlayer(this);
							   break;
						   }
						   else { System.out.println("Out of bounds.");}
					     }
					   }
				   }
			   }
		   
		   board.setCells(cells);
		   game.setBoard(board); //set new board
		   System.out.println(board.toString()); //debug purposes
		}
		   public void moveRight() {
			   Board board = this.game.getBoard();
			   List<List<Cell>> cells = board.getCells();
			   for (int rowList = 0; rowList < cells.size() - 1; rowList++) { //scans cells for player position (by rows)
				   List<Cell> row = cells.get(rowList);
				   for (int col = 0; col < row.size() - 1; col++ ) {
					   if (row.get(col).getPlayer() != null) {
					     if (row.get(col).getPlayer().toString().equals(name)) { //if cell contains player
						   if (col != row.size() - 1) { //if player is not right edge 
							   this.setPos(cells.get(rowList).get(col + 1)); //move right
							   cells.get(rowList).get(col + 1).addPlayer(this);
							   cells.get(rowList).get(col).remPlayer(this);
							   break;
						   }
						   else { System.out.println("Out of bounds.");}
					     }
					   }
				   }
			   }
		   
		   board.setCells(cells);
		   game.setBoard(board); //set new board
		   System.out.println(board.toString()); //debug purposes
		}
}
