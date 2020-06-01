/**
 * Represents a Dice
 *
 * @author irswr
 */
public class Dice {
    //Dice constants
    private final int NUM_SIDES; //The number of sides on the Dice

    /**
     * Constructor for Dice
     *
     * @param sides the number of sides on the Dice. This should be positive
     * @throws IllegalArgumentException when a negative number of sides is passed
     */
    public Dice(int sides) {
        if (sides > 0) {
            NUM_SIDES = sides;
        } else {
            throw new IllegalArgumentException("A negative number of sides was passed");
        }
    }

    /**
     * Gets a roll of the Dice
     *
     * @return the value rolled
     */
    public int getRoll() {
        return (int) (1 + NUM_SIDES * Math.random());
    }
}
