import java.util.ArrayList;

/**
 * Represents an AI Player
 *
 * @author irswr
 */
public class AIPlayer implements Player {
    //AIPlayer constants
    private final String NAME; //Stores this Player's name. Used exclusively for UI purposes, has no internal uses
    private final int BOARD_SIZE; //Stores the size of the board
    private final int JAIL_POSITION; //Stores the position of the jail on the board
    private final int SALARY; //The amount of money the Player should get for passing go
    private final int TURNS_IN_JAIL; //The number of turns the Player should be sent to jail for
    private final String[] PROMPTS; //Stores the prompts that are used during the Game

    //AIPlayer fields
    private int wallet; //Stores the value of the Player's wallet
    private int position; //Stores the Player's current position on the board
    private int turnsInJail; //Stores the number of turns that the Player has left in jail

    /**
     * Constructor for AIPlayer
     *
     * @param name             the Player's name
     * @param startingWallet   the Player's wallet
     * @param startingPosition the Player's position
     * @param boardSize        the board's size. This shouldn't be negative or less than startingPosition
     * @param turnsJail        the number of turns the Player has left in jail
     * @param jailPosition     the position of the jail on the board. This must be on the board
     * @param salary           the salary the Player should be awarded for each turn
     * @param numTurnsInJail   the number of turns the Player should be sent to jail for when they are sent there
     * @param prompts          the prompts that are used during the Game
     * @throws IllegalArgumentException when the passed parameters are invalid
     */
    public AIPlayer(String name, int startingWallet, int startingPosition, int boardSize, int turnsJail,
                    int jailPosition, int salary, int numTurnsInJail, String[] prompts) {
        if (name != null && startingWallet >= 0 && startingPosition >= 0 && startingPosition < boardSize &&
                turnsJail >= 0 && jailPosition >= 0 && jailPosition < boardSize && salary >= 0 && numTurnsInJail >= 0 &&
                !(turnsJail > 0 && startingPosition != jailPosition) && prompts != null && prompts.length == 16) {
            NAME = name;
            wallet = startingWallet;
            position = startingPosition;
            BOARD_SIZE = boardSize;
            turnsInJail = turnsJail;
            JAIL_POSITION = jailPosition;
            SALARY = salary;
            TURNS_IN_JAIL = numTurnsInJail;
            PROMPTS = prompts;
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
     * Prompts the Player to confirm or decline a Trade
     *
     * @param description the description that should be shown to the Player
     * @param trade       the Trade
     * @return 0 if the Player rejects the trade but wants to continue, 1 if they accept, and -1 if they want to stop
     */
    @Override
    public int promptTrade(String description, Trade trade) {

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
     * @throws IllegalArgumentException when the Player would go bankrupt on this transaction
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
     * @throws IllegalArgumentException when the Player wouldn't go bankrupt on this transaction
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
     * @throws IllegalArgumentException when an index that is off the board is passed
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
