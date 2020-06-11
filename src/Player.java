import java.awt.*;

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
     * @param <T>         the type of the potential Object being passed
     * @param description the description that should be shown to the Player
     * @param object      the object that is part of this prompt
     * @return the Players decision whether or not to buy the card
     */
    <T> boolean promptBoolean(String description, T object);

    /**
     * Prompts the Player for an integer value
     *
     * @param <T>         the type of the potential Object being passed
     * @param description the description that should be shown to the Player
     * @param min         the minimum value that the Player should be able to enter. Should be equal to none for none
     * @param max         the maximum value that the Player should be able to enter. Should be equal to none for none
     * @param none        the value the Player should enter for none
     * @param object      the object that is part of this prompt
     * @return the integer that the Player decides on
     */
    <T> int promptInt(String description, int min, int max, int none, T object);

    /**
     * Prompts the Player to pick an Object out of the provided Array
     *
     * @param <T>         the type of the Array
     * @param description the description that should be shown to the Player
     * @param objects     the Array that the Player should pick from
     * @param extra       an additional Object that may be provided
     * @return the index of the chosen Object
     */
    <T, S> int promptArray(String description, T[] objects, S extra);

    /**
     * Gets the Players name
     *
     * @return the Players name
     */
    String toString();

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

    /**
     * Gets the Players Color
     *
     * @return the Players Color
     */
    Color getCOLOR();

    /**
     * Gets the game board's size
     *
     * @return the game board's size
     */
    int getBOARD_SIZE();
}
