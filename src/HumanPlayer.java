import java.util.ArrayList;

/**
 * Represents a HumanPlayer
 *
 * @author irswr
 */
public class HumanPlayer implements Player {
    //HumanPlayer constants
    private final String NAME; //Stores this Player's name. Used exclusively for UI purposes, has no internal uses. This shouldn't be null
    private final int BOARD_SIZE; //Stores the size of the board
    private final int JAIL_POSITION; //The position of the jail on the board
    private final int SALARY; //The amount of money the Player should get for passing go
    private final int TURNS_IN_JAIL; //The number of turns the Player should be in jail for

    //HumanPlayer fields
    private int wallet; //Stores the value of the Player's wallet
    private int position; //Stores the Player's current position on the board
    private int turnsInJail; //Stores the number of turns the Player has left in jail. Shouldn't be less than 0

    /**
     * Constructor for HumanPlayer. Validates according to the conditions outlined here
     *
     * @param name             the Player's name. This shouldn't be null
     * @param startingWallet   the Player's wallet. This shouldn't be negative
     * @param startingPosition the Player's position. This shouldn't be negative
     * @param boardSize        the board's size. This shouldn't be negative or less than startingPosition
     * @param turnsJail        the number of turns the Player has left in jail. Shouldn't be less than 0
     * @param jailPosition     the position of the jail on the board. This must be on the board
     * @param salary           the salary the Player should be awarded for each turn
     * @param numTurnsInJail   the number of turns the Player should be in jail for when they go
     * @throws IllegalArgumentException when invalid parameters are passed
     */
    public HumanPlayer(String name, int startingWallet, int startingPosition, int boardSize, int turnsJail,
                       int jailPosition, int salary, int numTurnsInJail) {
        if (name != null && startingWallet >= 0 && startingPosition >= 0 && startingPosition < boardSize &&
                turnsJail >= 0 && jailPosition >= 0 && jailPosition < boardSize && salary >= 0 && numTurnsInJail >= 0 &&
                !(turnsJail > 0 && startingPosition != jailPosition)) {
            NAME = name;
            wallet = startingWallet;
            position = startingPosition;
            BOARD_SIZE = boardSize;
            turnsInJail = turnsJail;
            JAIL_POSITION = jailPosition;
            SALARY = salary;
            TURNS_IN_JAIL = numTurnsInJail;
        } else {
            throw new IllegalArgumentException("An invalid parameter was passed");
        }
    }

    /**
     * Gets the number of turns this Player has left in jail
     *
     * @return the number of turns this Player has left in jail
     */
    @Override
    public int getTurnInJail() {
        return turnsInJail;
    }

    /**
     * Moves the Player forward by spaces
     *
     * @param spaces the number of spaces to move the Player forward by
     * @throws IllegalArgumentException when a negative or zero value is passed
     */
    @Override
    public void move(int spaces) {
        if (spaces > 0) {
            if (position + spaces < BOARD_SIZE) {
                position += spaces;
            } else {
                position += spaces;
                position -= BOARD_SIZE;
                updateWallet(SALARY); //If they've gone past the end of the board, they've passed go and should be awarded their salary
            }
        } else {
            throw new IllegalArgumentException("An invalid value was passed");
        }
    }

    /**
     * Prompts the Player if they would like to do something
     *
     * @param description the description that should be shown to the Player
     * @return the Players decision whether or not to buy the card
     */
    @Override
    public boolean promptBoolean(String description) {

    }

    /**
     * Prompts the Player for an integer value
     *
     * @param description the description that should be shown to the Player
     * @param min         the minimum value that the Player should be able to enter. Should be equal to none for none
     * @param max         the maximum value that the Player should be able to enter. Should be equal to none for none
     * @param none        the value the Player should enter for none
     * @return the integer that the Player decides on
     */
    @Override
    public int promptInt(String description, int min, int max, int none) {

    }

    /**
     * Prompts the Player to pick an Object out of the provided Array
     *
     * @param description the description that should be shown to the Player
     * @param objects     the Array that the Player should pick from
     * @return the index of the chosen Object
     */
    @Override
    public int promptArray(String description, Object[] objects) {

    }

    /**
     * Prompts the Player to pick an Object out of the provided ArrayList
     *
     * @param description the description that should be shown to the player
     * @param objects     the ArrayList that the Player should pick from
     * @return the index of the chosen Object
     */
    @Override
    public int promptArrayList(String description, ArrayList objects) {

    }

    /**
     * Gets the Players name
     *
     * @return the Players name
     */
    @Override
    public String toString() {
        return NAME;
    }

    /**
     * Gets whether the Player can afford their purchase
     *
     * @param amount amount of the purchase
     * @return whether the Player can afford their purchase
     */
    @Override
    public boolean canAfford(int amount) {
        return amount <= wallet;
    }

    /**
     * Gets the amount stored in the Players wallet
     *
     * @return the Players wallet
     */
    @Override
    public int getWallet() {
        return wallet;
    }

    /**
     * Adds amount to the Players wallet. This also manages what happens when the Player has negative money
     *
     * @param amount the amount to add to the Players wallet
     * @throws IllegalArgumentException when this would cause the Player to go into debt
     */
    @Override
    public void updateWallet(int amount) {
        if (wallet + amount >= 0) {
            wallet += amount;
        } else {
            throw new IllegalArgumentException("An invalid amount of money was passed");
        }
    }

    /**
     * Allows the Player to have a negative balance
     *
     * @param amount the amount the Player should be charged
     * @throws IllegalArgumentException when the Player doesn't go into debt using this function
     */
    @Override
    public void doBankruptcy(int amount) {
        if (wallet + amount < 0) {
            wallet += amount;
        } else {
            throw new IllegalArgumentException("An invalid amount of money was passed");
        }
    }

    /**
     * Gets the Players current position on the board
     *
     * @return the Players current position on the board
     */
    @Override
    public int getPosition() {
        return position;
    }

    /**
     * Moves the Player to the given index on the game board
     *
     * @param index the index the Player should move to
     * @throws IllegalArgumentException when the index provided is outside the board
     */
    @Override
    public void goToSpace(int index) {
        if (index >= 0 && index < BOARD_SIZE) {
            if (index == JAIL_POSITION) {
                position = index;
                turnsInJail = TURNS_IN_JAIL;
            } else if (index < position) {
                position = index;
                updateWallet(SALARY);
            } else {
                position = index;
            }
        } else {
            throw new IllegalArgumentException("An invalid index was passed");
        }
    }
}
