
public class Suggestion {
	Card weapon, character, room;

	public Suggestion(Card weapon, Card character, Card room) {
		this.weapon = weapon;
		this.character = character;
		this.room = room;
	}

	public Card getWeapon() {
		return this.weapon;
	}

	public Card getCharacter() {
		return this.character;
	}

	public Card getRoom() {
		return this.room;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (obj instanceof Suggestion) {
			Suggestion o = (Suggestion) obj; // cast to Suggestion
			return o.getCharacter().equals(this.getCharacter()) && o.getRoom().equals(this.getRoom())
					&& o.getWeapon().equals(this.getWeapon());
		}
		// return false after instanceof because it will not equal
		return false;
	}

	public String toString() {
		return character.getName() + " with " + weapon.getName() + " in " + room.getName();
	}
}
