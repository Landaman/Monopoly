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

    //HumanPlayer fields
    private final int wallet; //Stores the value of the Player's wallet
    private final int position; //Stores the Player's current position on the board
    private final int turnsInJail; //Stores the number of turns the Player has left in jail. Shouldn't be less than 0

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
     * @throws IllegalArgumentException when invalid parameters are passed
     */
    public HumanPlayer(String name, int startingWallet, int startingPosition, int boardSize, int turnsJail,
                       int jailPosition, int salary) {
        if (name != null && startingWallet >= 0 && startingPosition >= 0 && startingPosition < boardSize &&
                turnsJail >= 0 && jailPosition >= 0 && jailPosition < boardSize && salary >= 0) {
            NAME = name;
            wallet = startingWallet;
            position = startingPosition;
            BOARD_SIZE = boardSize;
            turnsInJail = turnsJail;
            JAIL_POSITION = jailPosition;
            SALARY = salary;
        } else {
            throw new IllegalArgumentException("An invalid parameter was passed");
        }
    }
}
