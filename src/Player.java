import java.util.ArrayList;

/**
 * Represents a Player
 *
 * @author irswr
 */
public interface Player {
    /**
     * Gets the number of turns this Player has left in jail
     *
     * @return the number of turns this Player has left in jail
     */
    int getTurnInJail();

    /**
     * Moves the Player forward by spaces
     *
     * @param spaces the number of spaces to move the Player forward by
     */
    void move(int spaces);

    /**
     * Prompts the Player if they would like to do something
     *
     * @param description the description that should be shown to the Player
     * @return the Players decision whether or not to buy the card
     */
    boolean promptBoolean(String description);

    /**
     * Prompts the Player for an integer value
     *
     * @param description the description that should be shown to the Player
     * @param min         the minimum value that the Player should be able to enter. Should be equal to none for none
     * @param max         the maximum value that the Player should be able to enter. Should be equal to none for none
     * @param none        the value the Player should enter for none
     * @return the integer that the Player decides on
     */
    int promptInt(String description, int min, int max, int none);

    /**
     * Prompts the Player to pick an Object out of the provided Array
     *
     * @param description the description that should be shown to the Player
     * @param objects     the Array that the Player should pick from
     * @return the index of the chosen Object
     */
    int promptArray(String description, Object[] objects);

    /**
     * Prompts the Player to pick an Object out of the provided ArrayList
     *
     * @param description the description that should be shown to the player
     * @param objects     the ArrayList that the Player should pick from
     * @return the index of the chosen Object
     */
    int promptArrayList(String description, ArrayList objects);

    /**
     * Prompts the Player if they would like to start a trade with any of the Players in players
     *
     * @param description the description that should be shown to the Player
     * @param players     the Players that this Player should be asked if they want to trade
     * @return the completed Trade that the Players have or haven't done
     */
    Trade promptTrade(String description, Player[] players);

    /**
     * Gets the Players name
     *
     * @return the Players name
     */
    String getName();

    /**
     * Gets whether the Player can afford their purchase
     *
     * @param amount amount of the purchase
     * @return whether the Player can afford their purchase
     */
    boolean canAfford(int amount);

    /**
     * Gets the amount stored in the Players wallet
     *
     * @return the Players wallet
     */
    int getWallet();

    /**
     * Adds amount to the Players wallet. This also manages what happens when the Player has negative money
     *
     * @param amount the amount to add to the Players wallet
     */
    void updateWallet(int amount);

    /**
     * Allows the Player to have a negative balance
     *
     * @param amount the amount the Player should be charged
     */
    void doBankruptcy(int amount);

    /**
     * Gets the Players current position on the board
     *
     * @return the Players current position on the board
     */
    int getPosition();

    /**
     * Moves the Player to the given index on the game board
     *
     * @param index the index the Player should move to
     */
    void goToSpace(int index);
}
