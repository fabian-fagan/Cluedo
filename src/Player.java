import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Player {
	private String name;
	/**
	 * Player class, contains information on their hand, position and ID as well as
	 * controls movement on the board.
	 */
	private int playerID;
	private List<Card> hand;
	private Cell pos; // position on the board
	private Game game;
	private int roll;

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

	public char getPlayID() {
		return (char) (playerID + 1 + '0');
	}

	public char getInit() {
		return name.charAt(0);
	}

	public void addToHand(Card c) {
		if (c != null)
			this.hand.add(c);
		else
			System.out.println("Card is null");
	}

	public List<Card> getHand() {
		return hand;
	}

	public String printHand() {
		String s = "";
		for (Card c : hand) {
			s = s + c.getName() + ", ";
		}
		return s;
	}

	/**
	 * Called on new turn, receives input from player for direction to move as well
	 * as whether they want to make a suggestion/accusation. Loops equivalent to the
	 * roll number that the player receives.
	 */
	public void newTurn() {

		Dice dice = new Dice();
		this.roll = dice.roll();

		Scanner sc = new Scanner(System.in);
		for (int i = 0; i < roll; i++) {
			if (inRoom()) {
				System.out.println(name + ": " + (roll - i)
						+ " moves (W - Up, A - Left, S - Down, D - Right) or make a suggestion! (M)");
			} else
				System.out.println(name + ": " + (roll - i)
						+ " moves (W - Up, A - Left, S - Down, D - Right) or make an accusation! (K)");
			String input = sc.next(); 
			if (input.equalsIgnoreCase("W")) {
				moveUp();
			}

			if (input.equalsIgnoreCase("A")) {
				moveLeft();
			}

			if (input.equalsIgnoreCase("S")) {
				moveDown();
			}

			if (input.equalsIgnoreCase("D")) {
				moveRight();
			}

			if (input.equalsIgnoreCase("K")) {
				makeAccusation();

			}
			if (input.equalsIgnoreCase("M")) {
				if (inRoom()) {
					makeSuggestion();
					i = roll; // end turn
				} else
					System.out.println("You are not in a room");
			}
			if (input.equalsIgnoreCase("E")) { // end turn
				i = roll;
			}
			if (input.equalsIgnoreCase("H")) { // print hand
				System.out.println(printHand());
			}
		}
		if (inRoom()) {
			System.out.println("Would you like to make a suggestion? (Y/N)");
			if (sc.next().equalsIgnoreCase("Y")) {
				makeSuggestion();
			}
		}

	}

	/**
	 * @return
	 */
	public boolean inRoom() {
		char posName = pos.getCellName();
		if (posName == '#')
			return false;
		if (posName == '!')
			return false;
		if (posName == '-')
			return false;
		if (posName == 'P')
			return false;
		else
			return true;
	}

	public void spawn(Cell spawnPos) {
		pos = spawnPos;
		spawnPos.enter(Player.this, null);
	}

	public void moveUp() {
		Board board = this.game.getBoard();
		boolean out = false;
		List<List<Cell>> cells = board.getCells();
		for (int rowList = 0; rowList < cells.size() - 1; rowList++) { // scans cells for player position (by rows)
			List<Cell> row = cells.get(rowList);
			for (int col = 0; col < row.size() - 1; col++) {
				if (row.get(col).getPlayer() != null) {
					if (row.get(col).getPlayer().toString().equals(name)) { // if cell contains player
						if (rowList != 0) { // if player is not on top row
							if (cells.get(rowList - 1).get(col).isTraversable()) {
								this.setPos(cells.get(rowList - 1).get(col)); // move up
								cells.get(rowList - 1).get(col).addPlayer(this);
								cells.get(rowList).get(col).remPlayer(this);
							} else {
								out = true; // cell is not traversable
							}
						} else {
							out = true;
						}
					}
				}
			}
		}
		board.setCells(cells);
		game.setBoard(board); // set new board
		System.out.println(board.toString());
		if (out == true) {
			System.out.println("Out of bounds!");
			this.roll = this.roll + 1;
		}
	}

	public void moveDown() {
		Board board = this.game.getBoard();
		boolean out = false;
		Boolean moved = false;
		List<List<Cell>> cells = board.getCells();
		for (int rowList = 0; rowList < cells.size() - 1; rowList++) { // scans cells for player position (by rows)
			List<Cell> row = cells.get(rowList);
			for (int col = 0; col < row.size() - 1; col++) {
				if (row.get(col).getPlayer() != null) {
					if (row.get(col).getPlayer().toString().equals(name)) { // if cell contains player
						if (rowList != cells.size() - 1 && moved == false) { // if player is not on bottom row
							if (cells.get(rowList + 1).get(col).isTraversable()) {
								this.setPos(cells.get(rowList + 1).get(col)); // move down
								cells.get(rowList + 1).get(col).addPlayer(this);
								cells.get(rowList).get(col).remPlayer(this);
								moved = true;
								break;
							} else {
								out = true; // cell is not traversable
							}
						} else if (moved == false) {
							out = true;

						}
					}
				}
			}
		}
		board.setCells(cells);
		game.setBoard(board); // set new board
		System.out.println(board.toString());
		if (out == true) {
			System.out.println("Out of bounds!");
			this.roll = this.roll + 1;
		}
	}

	public void moveLeft() {
		Board board = this.game.getBoard();
		boolean out = false;

		List<List<Cell>> cells = board.getCells();
		for (int rowList = 0; rowList < cells.size() - 1; rowList++) { // scans cells for player position (by rows)
			List<Cell> row = cells.get(rowList);
			for (int col = 0; col < row.size() - 1; col++) {
				if (row.get(col).getPlayer() != null) {
					if (row.get(col).getPlayer().toString().equals(name)) { // if cell contains player
						if (col != 0) { // if player is not left edge
							if (cells.get(rowList).get(col - 1).isTraversable()) {
								this.setPos(cells.get(rowList).get(col - 1)); // move left
								cells.get(rowList).get(col - 1).addPlayer(this);
								cells.get(rowList).get(col).remPlayer(this);
								break;
							} else {
								out = true; // cell is not traversable
							}
						} else {
							out = true;
						}
					}
				}
			}
		}

		board.setCells(cells);
		game.setBoard(board); // set new board
		System.out.println(board.toString());
		if (out == true) {
			System.out.println("Out of bounds!");
			this.roll = this.roll + 1;
		}
	}

	public void moveRight() {
		Board board = this.game.getBoard();
		boolean out = false;
		List<List<Cell>> cells = board.getCells();
		for (int rowList = 0; rowList < cells.size() - 1; rowList++) { // scans cells for player position (by rows)
			List<Cell> row = cells.get(rowList);
			for (int col = 0; col < row.size() - 1; col++) {
				if (row.get(col).getPlayer() != null) {
					if (row.get(col).getPlayer().toString().equals(name)) { // if cell contains player
						if (col != row.size() - 1) { // if player is not right edge
							if (cells.get(rowList).get(col + 1).isTraversable()) {
								this.setPos(cells.get(rowList).get(col + 1)); // move right
								cells.get(rowList).get(col + 1).addPlayer(this);
								cells.get(rowList).get(col).remPlayer(this);
								break;
							} else {
								out = true; // cell is not traversable
							}
						} else {
							out = true;
						}
					}
				}
			}
		}

		board.setCells(cells);
		game.setBoard(board); // set new board
		System.out.println(board.toString());
		if (out == true) {
			System.out.println("Out of bounds!");
			this.roll = this.roll + 1;
		}
	}

	/*
	 * Makes a suggestion and then calls checkRefute which checks if any other
	 * player can refute the suggestion.
	 */

	/**
	 * @return
	 */
	public boolean makeSuggestion() {
		Scanner sc = new Scanner(System.in);
		System.out.println("Who do you think could be the murderer? Enter the number");
		List<PCharacter> chars = Board.characters;
		for (int i = 0; i < chars.size(); i++) {
			System.out.println(i + ": " + chars.get(i).getName());
		}
		int m = sc.nextInt();
		System.out.println("What did the murderer use?");
		List<Weapon> weapons = Board.weapons;
		for (int i = 0; i < weapons.size(); i++) {
			System.out.println(i + ": " + weapons.get(i).getName());
		}
		int n = sc.nextInt();
		PCharacter murderer = chars.get(m);
		Weapon murderWeapon = weapons.get(n);

		System.out.println("You chose: " + murderer.getName() + " with " + murderWeapon.getName() + ", correct? (Y/N)");
		String next = sc.next();
		if (next.equalsIgnoreCase("N"))
			return makeSuggestion();
		if (next.equalsIgnoreCase("Y")) {
			Suggestion s = new Suggestion(murderer, murderWeapon, pos.getRoom());
			game.checkSuggestionRefute(this, s);
		}
		return false;
	}

	/**
	 * Makes accusation and calls checkRefute similar to make suggestion
	 * @return
	 */
	
	public boolean makeAccusation() {
		Scanner sc = new Scanner(System.in);
		System.out.println("Who are you accusing of murder? Enter the number");
		List<PCharacter> chars = Board.characters;
		for (int i = 0; i < chars.size(); i++) {
			System.out.println(i + ": " + chars.get(i).getName());
		}
		int m = sc.nextInt();
		System.out.println("What did the murderer use?");
		List<Weapon> weapons = Board.weapons;
		for (int i = 0; i < weapons.size(); i++) {
			System.out.println(i + ": " + weapons.get(i).getName());
		}
		int n = sc.nextInt();
		System.out.println("What room did the murder take place in?");
		List<Room> rooms = Board.rooms;
		for (int i = 0; i < rooms.size(); i++) {
			System.out.println(i + ": " + rooms.get(i).getName());
		}
		int w = sc.nextInt();
		PCharacter murderer = chars.get(m);
		Weapon murderWeapon = weapons.get(n);
		Room murderRoom = rooms.get(w);
		System.out.println("You chose: " + murderer.getName() + " with " + murderWeapon.getName() + " in the "
				+ murderRoom.getName() + ", correct? (Y/N)");
		String next = sc.next();
		if (next.equalsIgnoreCase("N"))
			return makeAccusation();
		if (next.equalsIgnoreCase("Y")) {
			Accusation s = new Accusation(murderer, murderWeapon, pos.getRoom());
			game.checkAccusationRefute(this, s);
		}
		return false;
	}

	/**
	 * @param s
	 * @return
	 */
	public boolean canRefuteSuggestion(Suggestion s) {
		if (hand.contains(s.getCharacter())) {
			System.out.println(name + " has " + s.getCharacter().getName());
			return true;
		}
		if (hand.contains(s.getWeapon())) {
			System.out.println(name + " has " + s.getWeapon().getName());
			return true;
		}
		if (hand.contains(s.getRoom())) {
			System.out.println(name + " has " + s.getRoom().getName());
			return true;
		}
		return false;
	}

	/**
	 * @param s
	 * @return
	 */
	public boolean canRefuteAccusation(Accusation s) {
		if (hand.contains(s.getCharacter())) {
			System.out.println(name + " has " + s.getCharacter().getName());
			System.out.println("You have been removed from the game!");
			return true;
		}
		if (hand.contains(s.getWeapon())) {
			System.out.println(name + " has " + s.getWeapon().getName());
			System.out.println("You have been removed from the game!");
			return true;
		}
		if (hand.contains(s.getRoom())) {
			System.out.println(name + " has " + s.getRoom().getName());
			System.out.println("You have been removed from the game!");
			return true;
		}
		return false;
	}
}
