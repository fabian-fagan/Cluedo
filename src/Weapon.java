
public class Weapon extends Card {

	public Weapon(String name) {
		super(name);
		// TODO Auto-generated constructor stub
	}

	public char getID() {
		char c = this.getName().charAt(0);
		return c;
	} 
	
	
}
