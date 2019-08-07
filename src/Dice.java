import java.util.Random;

/**
 * Represents dice to give player their allocated turns
 *
 */
public class Dice {
	private final int MAX = 12; // maximum face value of two 6 sided dice
	private int roll;
	
	public Dice() {
		roll = 1; // initial face value, doesn't really matter what it is
	}
	
	/**
	 * Roll returns the value of the roll (two dice, out of 12) and saves it in the object
	 * @return
	 */
	
	public int roll() {
		roll = (int)(Math.random() * MAX) + 1; //add one because 0 index
		return roll;
	}
	
	/**
	 * Possibly helpful for testing purposes
	 * @param r 
	 * value to set the roll
	 */
	public void setValue(int r) {
		roll = r;
	}
	
	public int getRoll() {
		return roll;
	}
}
