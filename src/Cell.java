
/**
 * Individual cell objects in the board. Contains information on its player/weapon it contains or whether it is empty. 
 *
 */
public class Cell {
	private char name;
	private Player player;
	private Weapon weapon;
	private Board board;
	
	public Cell(char name, Board b) {
		this.name = name;
		this.board = b;
	}
	
	public Cell(Player pl, char name) {
		this.player = pl;
		this.name = name;
	}
	/*
	 * use this to get the representation of the cell itself when a player is on it
	 */
	public char getCellName() {
		return name;
	}
	
	public char getName() {
		if(player == null && weapon == null) return name;
		if(player == null && weapon != null) return weapon.getID();
		else return player.getPlayID();
	}
	
	public Weapon getWeapon() {
		return weapon;
	}
	
	public void setWeapon(Weapon w) {
		this.weapon = w;
	}
	
	public Room getRoom() {
		return board.getRoom(this);
	}
	
	public boolean canMoveHere(Player p) {
		if (p == player) return true; // player can stay in place
		return player == null;
	}
	/**
	 * checks if the cell is a room, door or part of the hallway
	 */
	public Boolean isTraversable() {
		if (name == '#' || name == '=') {
			return true;
		}
		else return false;

		
	}
	
	protected boolean movePlayer(Player p) {
		if (! canMoveHere(p)) return false;
		addPlayer(p);
		return true;
	}

	public boolean enter(Player p, Game game) {
		return movePlayer(p);
	}
	
	protected void addPlayer(Player p) {
		this.player = p;
	}
	
	protected void remPlayer(Player p) {
		this.player = null;
	}
	public Player getPlayer() {
		if(player != null) {
		return this.player;
		}
		else return null;
	}
}
