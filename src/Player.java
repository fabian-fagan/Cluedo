
import javax.imageio.ImageIO;
import javax.swing.*;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Player{
	private String name;
	private String playerName;
	private String color;
	/**
	 * Player class, contains information on their hand, position and ID as well as
	 * controls movement on the board.
	 */
	private int playerID;
	private List<Card> hand;
	private Cell pos; // position on the board
	private Game game;
	private int roll = 0;
	private int moves = 0;
	private boolean isEliminated = false;

	public Player(String name, int pID, Game game) {
		this.name = name;
		this.playerID = pID;
		this.game = game;
		this.hand = new ArrayList<>();
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

	public void newTurn(){
		Dice dice = new Dice();
		this.roll = dice.roll();
		moves = roll;
		game.getBoard().redraw();

		return;
	}


	/**
	 * @return boolean true if in room
	 */
	private boolean inRoom() {
		char posName = pos.getCellName();
		if (posName == '#' || posName == '!' || posName == '-' || posName == 'P')
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
		if (out) {
			this.roll = this.roll + 1;

		}
		moves ++;
	}

	public void moveDown() {
		Board board = this.game.getBoard();
		boolean out = false;
		boolean moved = false;

		List<List<Cell>> cells = board.getCells();
		for (int rowList = 0; rowList < cells.size() - 1; rowList++) { // scans cells for player position (by rows)
			List<Cell> row = cells.get(rowList);
			for (int col = 0; col < row.size() - 1; col++) {
				if (row.get(col).getPlayer() != null) {
					if (row.get(col).getPlayer().toString().equals(name)) { // if cell contains player
						if (rowList != cells.size() - 1 && !moved) { // if player is not on bottom row
							if (cells.get(rowList + 1).get(col).isTraversable()) {
								this.setPos(cells.get(rowList + 1).get(col)); // move down
								cells.get(rowList + 1).get(col).addPlayer(this);
								cells.get(rowList).get(col).remPlayer(this);
								moved = true;
								break;
							} else {
								out = true; // cell is not traversable
							}
						} else if (!moved) {
							out = true;

						}
					}
				}
			}
		}
		board.setCells(cells);
		game.setBoard(board); // set new board
		if (out) {
			this.roll = this.roll + 1;


		}
		moves++;
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

		if (out) {
			this.roll = this.roll + 1;


		}
		else { game.pl = game.pl + 1; }
		moves++;
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
		if (out) {
			this.roll = this.roll + 1;
		}
		moves ++;

	}

	/*
	 * Makes a suggestion and then calls checkRefute which checks if any other
	 * player can refute the suggestion.
	 */

	/**
	 * @return
	 */

	boolean makeSuggestion() {
		if(inRoom()) {
			// Ask for murderer
			List<PCharacter> chars = Board.characters;
			// convert to array of the names
			String[] charStringArray = new String[chars.size()];
			for (int i = 0; i < chars.size(); i++) {
				charStringArray[i] = chars.get(i).getName();
			}
			int c = JOptionPane.showOptionDialog(null, "Who do you think could be the murderer?",
					"", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, charStringArray, charStringArray[0]);
			PCharacter murderer = chars.get(c);
			// Ask for weapon
			List<Weapon> weapons = Board.weapons;
			String[] weapStringArray = new String[weapons.size()];
			for (int i = 0; i < weapons.size(); i++) {
				weapStringArray[i] = weapons.get(i).getName();
			}
			int w = JOptionPane.showOptionDialog(null, "What weapon did the murderer use?",
					"", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, weapStringArray, weapStringArray[0]);
			Weapon weapon = weapons.get(w);

			int dialogButton = JOptionPane.YES_NO_CANCEL_OPTION;
			int dialogResult = JOptionPane.showConfirmDialog(null, "You chose: " + murderer.getName() + " with " + weapon.getName() + ", correct?", "Your suggestion", dialogButton);
			if (dialogResult == JOptionPane.NO_OPTION) {
				return makeSuggestion();
			}
			if (dialogResult == JOptionPane.YES_OPTION) {
				Suggestion s = new Suggestion(murderer, weapon, pos.getRoom());
				game.checkSuggestionRefute(this, s);
				game.nextPlayer();
			}
		} else {
			game.displayMessage("You are not in a room");
		}
		return false;
	}

	/**
	 * Makes accusation and calls checkRefute similar to make suggestion
	 *
	 * @return
	 */

	public boolean makeAccusation() {
		// Ask for murderer
		List<PCharacter> chars = Board.characters;
		// convert to array of the names
		String[] charStringArray = new String[chars.size()];
		for (int i = 0; i < chars.size(); i++) {
			charStringArray[i] = chars.get(i).getName();
		}
		int c = JOptionPane.showOptionDialog(null, "Who are you accusing of murder?",
				"", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, charStringArray, charStringArray[0]);
		PCharacter murderer = chars.get(c);
		// Ask for weapon
		List<Weapon> weapons = Board.weapons;
		String[] weapStringArray = new String[weapons.size()];
		for (int i = 0; i < weapons.size(); i++) {
			weapStringArray[i] = weapons.get(i).getName();
		}
		int w = JOptionPane.showOptionDialog(null, "What weapon did the murderer use?",
				"", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, weapStringArray, weapStringArray[0]);
		Weapon weapon = weapons.get(w);
		//Ask for room
		List<Room> rooms = Board.rooms;
		String[] roomStringArray = new String[rooms.size()];
		for (int i = 0; i < rooms.size(); i++) {
			roomStringArray[i] = rooms.get(i).getName();
		}
		int r = JOptionPane.showOptionDialog(null, "What room did the murder take place in?",
				"", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, roomStringArray, roomStringArray[0]);
		Room room = rooms.get(r);



		int dialogButton = JOptionPane.YES_NO_CANCEL_OPTION;
		int dialogResult = JOptionPane.showConfirmDialog(null, "You chose: " + murderer.getName() + " with " + weapon.getName() + " in the " + room.getName() + ", correct?", "Your suggestion", dialogButton);
		if (dialogResult == JOptionPane.NO_OPTION) {
			return makeAccusation();
		}
		if (dialogResult == JOptionPane.YES_OPTION) {
			Accusation a = new Accusation(murderer, weapon, room);
			game.checkAccusationRefute(this, a);
		}
		return false;
	}

	/**
	 * @param s
	 * @return
	 */
	public boolean canRefuteSuggestion(Suggestion s) {
		if (hand.contains(s.getCharacter())) {
			game.displayMessage(this.getPrefferedName() + " has " + s.getCharacter().getName());
			return true;
		}
		if (hand.contains(s.getWeapon())) {
			game.displayMessage(this.getPrefferedName() + " has " + s.getWeapon().getName());
			return true;
		}
		if (hand.contains(s.getRoom())) {
			game.displayMessage(this.getPrefferedName() + " has " + s.getRoom().getName());
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
			game.displayMessage(this.getPrefferedName() + " has " + s.getCharacter().getName());
			return true;
		}
		if (hand.contains(s.getWeapon())) {
			game.displayMessage(this.getPrefferedName() + " has " + s.getWeapon().getName());
			return true;
		}
		if (hand.contains(s.getRoom())) {
			game.displayMessage(this.getPrefferedName() + " has " + s.getRoom().getName());
			return true;
		}
		return false;
	}

	public void hasMoved() {
		this.roll = this.roll - 1;
		if (inRoom()) {
			int dialogButton = JOptionPane.YES_NO_CANCEL_OPTION;
			int dialogResult = JOptionPane.showConfirmDialog(null, "Would you like to make a suggestion?", "", dialogButton);
			if (dialogResult == JOptionPane.NO_OPTION) {
				game.nextPlayer();
				return;
			}
			if (dialogResult == JOptionPane.YES_OPTION) {
				makeSuggestion();
			}
		}
		else if(roll == 0){
			game.nextPlayer();
		}
	}

	public void eliminate(){
		isEliminated = true;
	}

	public boolean isEliminated(){
		return isEliminated;
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

	public String getRollString() { return String.valueOf(this.roll); }

	public String getName() {
		return name;
	}

	public String getPrefferedName(){
		if(playerName == null)
			return name;
		else return playerName;
	}

	public Cell getPos() {
		return pos;
	}

	public void setPos(Cell pos) {
		this.pos = pos;
	}

	public String getColor(){
		return color;
	}

	public void setColor(String c){
		this.color = c;
	}

	public void setName(String name){
		if(name != null)
			this.name = name;
	}

	public String getPlayerName(){
		return playerName;
	}

	public void setPlayerName(String name){
		this.playerName = name;
	}

	public int getRoll() {
		return roll;
	}



}
