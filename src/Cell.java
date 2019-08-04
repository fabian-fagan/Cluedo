
public class Cell {
	private char name;
	private Player player;
	
	public Cell(char name) {
		this.name = name;
	}
	
	public Cell(Player pl, char name) {
		this.player = pl;
		this.name = name;
	}
	
	public char getName() {
		if(player == null) return name;
		else return player.getCharID();
	}
	
	public boolean canMoveHere(Player p) {
		if (p == player) return true; // player can stay in place
		return player == null;
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
}
