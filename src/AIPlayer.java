import java.awt.*;
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
    /**
     * <ul>
     * <li>PROMPTS[0] should prompt the user if they would like to use a get out of jail free Card</li>
     * <li>PROMPTS[1] should prompt the user if they would like to Trade another Player for a get out of jail free Card</li>
     * <li>PROMPTS[2] should prompt the user to pay their bail</li>
     * <li>PROMPTS[3] should prompt the user if they would like to participate in an auction for a Property</li>
     * <li>PROMPTS[4] should prompt the user if they would like to buy a Property</li>
     * <li>PROMPTS[5] should prompt the user if they would like to sell constructions</li>
     * <li>PROMPTS[6] should prompt the user if they would like to mortgage any Properties</li>
     * <li>PROMPTS[7] should prompt the user if they would like to un-mortgage any Properties</li>
     * <li>PROMPTS[8] should prompt the user if they would like to build any constructions</li>
     * <li>PROMPTS[9] should prompt whoever is the currentOfferer in a Trade if they think the sender should offer any more Properties</li>
     * <li>PROMPTS[10] should prompt whoever is the currentOfferer in a Trade if they think the sender should remove any Properties</li>
     * <li>PROMPTS[11] should prompt the whoever is the currentOfferer in a Trade if they think the sender should offer any more Cards</li>
     * <li>PROMPTS[12] should prompt whoever is the currentOfferer in a Trade if they think the sender should remove any Cards</li>
     * <li>PROMPTS[13] should prompt whoever is the currentOfferer in a Trade if they think the sender should offer any more money</li>
     * <li>PROMPTS[14] should prompt whoever is the currentOfferer in a Trade if they think the sender should remove any money</li>
     * <li>PROMPTS[15] should prompt whoever is the currentOfferer in a Trade if they think the receiver should offer amy more Properties</li>
     * <li>PROMPTS[16] should prompt whoever is the currentOfferer in a Trade if they think the receiver should remove any Properties</li>
     * <li>PROMPTS[17] should prompt whoever is the currentOfferer in a Trade if they think the receiver should offer any more Cards</li>
     * <li>PROMPTS[18] should prompt whoever is the currentOfferer in a Trade if they think the receiver should remove any Cards</li>
     * <li>PROMPTS[19] should prompt whoever is the currentOfferer in a Trade if they think the receiver should offer any money</li>
     * <li>PROMPTS[20] should prompt whoever is the currentOfferer in a Trade if they think the receiver should remove any money</li>
     * <li>PROMPTS[21] should prompt the user to confirm a Trade</li>
     * <li>PROMPTS[22] should prompt the user if they would like to trade anyone</li>
     * <li>PROMPTS[23] should prompt the user to sell constructions when they're bankrupt</li>
     * <li>PROMPTS[24] should prompt the user to mortgage Properties when they're bankrupt</li>
     * <li>PROMPTS[25] should prompt the user to un-mortgage a Property that they inherit from someone who's bankrupt</li>
     * <li>PROMPTS[26] should prompt the user to enter how much they want to bid in an Auction</li>
     * </ul>
     */
    private final String[] PROMPTS; //Stores the prompts that are used during the Game
    private final Space[] GAME_BOARD; //Stores the game board. THIS CLASS SHOULD NEVER DIRECTLY MODIFY IT, ONLY READ IT
    private final String[] COLOR_GROUPS; //Stores the color groups on the game board
    private final Color COLOR; //Stores this Players color

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
     * @param gameBoard        the game board. This is read-only for this Class
     * @param colorGroups      the color groups on the game board. This is read-only for this Class
     * @param color this Player's color
     * @throws IllegalArgumentException when the passed parameters are invalid
     */
    public AIPlayer(String name, int startingWallet, int startingPosition, int boardSize, int turnsJail,
                    int jailPosition, int salary, int numTurnsInJail, String[] prompts, Space[] gameBoard,
                    String[] colorGroups, Color color) {
        if (name != null && startingWallet >= 0 && startingPosition >= 0 && startingPosition < boardSize &&
                turnsJail >= 0 && jailPosition >= 0 && jailPosition < boardSize && salary >= 0 && numTurnsInJail >= 0 &&
                !(turnsJail > 0 && startingPosition != jailPosition) && prompts != null && prompts.length == 27 && gameBoard != null &&
                Game.validateGameBoard(gameBoard, colorGroups) && color != null) {
            NAME = name;
            wallet = startingWallet;
            position = startingPosition;
            BOARD_SIZE = boardSize;
            turnsInJail = turnsJail;
            JAIL_POSITION = jailPosition;
            SALARY = salary;
            TURNS_IN_JAIL = numTurnsInJail;
            PROMPTS = prompts;
            GAME_BOARD = gameBoard;
            COLOR_GROUPS = colorGroups;
            COLOR = color;
        } else {
            throw new IllegalArgumentException("An invalid parameter was passed");
        }
    }

    /*
    The following methods are static helper methods that help the AIPlayer recognize prompts and Properties, and assign values
     */

    /**
     * Recognizes a prompt that is given to the AIPlayer
     *
     * @param prompts the prompts that are in use this Game
     * @param prompt  the prompt to read
     * @return the index of the recognized prompt
     * @throws IllegalArgumentException when a null parameter is passed, or when the prompt isn't found
     */
    private static int recognizePrompt(String[] prompts, String prompt) {
        if (prompts != null && prompt != null) {
            for (int i = 0; i < prompts.length; i++) {
                if (prompts[i] != null) {
                    if (prompts[i].equals(prompt)) {
                        return i;
                    }
                } else {
                    throw new IllegalArgumentException("A null prompt was passed");
                }
            }

            throw new IllegalArgumentException("The passed prompt was not found in the prompts Array");
        } else {
            throw new IllegalArgumentException("A null parameter was passed");
        }
    }

    /**
     * Finds the given Space on the game board
     *
     * @param spaces the Space to find
     * @param space  the game board to read
     * @param <T>    either Space or Property
     * @return the index of the given Space
     * @throws IllegalArgumentException whne a null parameter is passed, or the given Space can not be found
     */
    private static <T> int recognizeSpace(Space[] spaces, T space) {
        if (spaces != null && (space instanceof Property || space instanceof Space)) {
            for (int i = 0; i < spaces.length; i++) {
                if (spaces[i] != null) {
                    if (space instanceof Space) {
                        if (spaces[i].equals(space)) {
                            return i;
                        }
                    } else {
                        if (spaces[i].getPROPERTY() != null && spaces[i].getPROPERTY().equals(space)) {
                            return i;
                        }
                    }
                } else {
                    throw new IllegalArgumentException("A null Space was passed");
                }
            }
            throw new IllegalArgumentException("Could not find the given Space");
        } else {
            throw new IllegalArgumentException("An invalid parameter was passed");
        }
    }

    /**
     * Gets the values the AI thinks each Property is worth on the game board
     *
     * @param spaces     the game board to read
     * @param thisPlayer this AIPlayer
     * @return the values the AI thinks each Property is worth
     */
    private static int[] getValues(Space[] spaces, Player thisPlayer) {
        if (spaces != null && thisPlayer != null) {
            int[] result = new int[spaces.length];
            for (int i = 0; i < spaces.length; i++) {
                if (spaces[i] != null) {
                    if (spaces[i].getPROPERTY() != null) {
                        Player[] owners = getColorGroupOwners(spaces, spaces[i].getCOLOR_GROUP());
                        if (owners.length == 0) { //If no-one owns Property in this color group, the value should be normal
                            result[i] = spaces[i].getPROPERTY().getPRICE();
                        } else if (owners.length == 1) {
                            if (owners[0].equals(thisPlayer)) { //If this Player is the only owner, we should inflate values
                                if (percentOwnedByPlayer(spaces, owners[0], spaces[i].getPROPERTY().getCOLOR_GROUP()) < .5) { //If this is the case the AI owns less than half of the Properties in a group, so its still not a guaranteed monopoly
                                    result[i] = (int) Math.round(spaces[i].getPROPERTY().getPRICE() * 1.25);
                                } else if (percentOwnedByPlayer(spaces, owners[0], spaces[i].getPROPERTY().
                                        getCOLOR_GROUP()) < 1.0) { //If this is the case, the Player owns more than half of the Properties in a color group, so this is a monopoly very much worth perusing
                                    result[i] = (int) Math.round(spaces[i].getPROPERTY().getPRICE() * 1.5);
                                } else { //If this is the case, the Player owns all of the Properties in a color group
                                    result[i] = (int) Math.round(spaces[i].getPROPERTY().getPRICE() * 2.0);
                                    result[i] += spaces[i].getPROPERTY().getNumHouses() * spaces[i].getPROPERTY().getBUILD_PRICE(); //We should add value for every house on the Property
                                }
                            } else { //If someone else has the only Property, we should hold values constant to deny them a monopoly, but we shouldn't aggressively pursue
                                result[i] = spaces[i].getPROPERTY().getPRICE();
                            }
                        } else { //If this is the case, at least two people have a stake in this color group
                            boolean playerHasStake = false;
                            for (Player player : owners) { //First we should find out if this Player has a stake in this Property
                                if (player.equals(thisPlayer)) {
                                    playerHasStake = true;
                                    break;
                                }
                            }
                            if (playerHasStake) {
                                if (percentOwnedByPlayer(spaces, thisPlayer, spaces[i].getPROPERTY().getCOLOR_GROUP()) < .5) { //If the Player has less than half of the color group, odds are they aren't getting a monopoly
                                    result[i] = (int) Math.round(spaces[i].getPROPERTY().getPRICE() * .75);
                                } else { //If the Player has at least half of a color group, the odds that they get a monopoly are good
                                    result[i] = (int) Math.round(spaces[i].getPROPERTY().getPRICE() * 1.25);
                                }
                            } else { //If this Player doesn't have a stake in this color group and there's no chance of someone getting a monopoly, this Property isn't worth very much
                                result[i] = (int) Math.round(spaces[i].getPROPERTY().getPRICE() * .25);
                            }
                        }
                    } else {
                        result[i] = -1;
                    }
                } else {
                    throw new IllegalArgumentException("A null Space was passed");
                }
            }
            return result;
        } else {
            throw new IllegalArgumentException("A null parameter was passed");
        }
    }

    /**
     * Gets the value that the AI gives the passed Property
     *
     * @param spaces     the game board to read
     * @param thisPlayer this Player
     * @param property   the Property to look for
     * @return the value of the passed Property
     * @throws IllegalArgumentException when a null parameter is passed
     */
    private static int getPropertyValue(Space[] spaces, Player thisPlayer, Property property) {
        if (spaces != null && thisPlayer != null && property != null) {
            return getValues(spaces, thisPlayer)[recognizeSpace(spaces, property)];
        } else {
            throw new IllegalArgumentException("A null parameter was passed");
        }
    }

    /**
     * Calculates the minimum wallet that the AI should have in order to be safe landing on any Space
     *
     * @param spaces     the game board to read
     * @param thisPlayer this AIPlayer
     * @return the mimimum safe wallet that the AI should have
     * @throws IllegalArgumentException when a null parameter is passed
     */
    private static int calcMinWallet(Space[] spaces, Player thisPlayer) {
        if (spaces != null) {
            int maxRent = 0;
            for (Space space : spaces) {
                if (space != null) {
                    if (space.getPROPERTY() != null && space.getPROPERTY().getOwner() != null &&
                            !space.getPROPERTY().getOwner().equals(thisPlayer) && space.getPROPERTY().getRent() > maxRent) {
                        maxRent = space.getPROPERTY().getRent();
                    }
                } else {
                    throw new IllegalArgumentException("A null Space was passed");
                }
            }
            return maxRent;
        } else {
            throw new IllegalArgumentException("A null Spaces Array was passed");
        }
    }

    /**
     * Gets the percentage (as a double) of Properties that the Player owns out of a color group
     *
     * @param spaces     the game board to read
     * @param player     the Player to look for
     * @param colorGroup the color group to look for
     * @return the percentage of Properties that a Player owns out of a color group
     */
    private static double percentOwnedByPlayer(Space[] spaces, Player player, String colorGroup) {
        return (double) Game.playerPropertiesInColorGroup(colorGroup, spaces, player).length /
                Game.propertiesInColorGroup(colorGroup, spaces).length;
    }

    /**
     * Gets the Players who own Properties in the provided color group
     *
     * @param spaces     the game board to read
     * @param colorGroup the color group to look for
     * @return the Players who own Properties in the provided color group
     */
    private static Player[] getColorGroupOwners(Space[] spaces, String colorGroup) {
        ArrayList<Player> owners = new ArrayList<>();
        for (Property property : Game.propertiesInColorGroup(colorGroup, spaces)) {
            if (property.getOwner() != null && !owners.contains(property.getOwner())) {
                owners.add(property.getOwner());
            }
        }
        return owners.toArray(new Player[0]);
    }

    /**
     * Analyzes a Trade. Returns the amount that it is in favor of the AI for
     *
     * @param spaces     the game board to read
     * @param trade      the Trade to analyze
     * @param thisPlayer this Player
     * @return the amount that this Trade is in favor of the AI
     */
    private static int analyzeTrade(Space[] spaces, Trade trade, Player thisPlayer) {
        if (spaces != null && trade != null && (thisPlayer.equals(trade.getSENDER()) ||
                thisPlayer.equals(trade.getRECEIVER()))) {
            int senderValue = 0;
            int receiverValue = 0;

            for (Property property : trade.getSenderProperties()) { //This adds the AI's calculated worth of each Property to the values
                senderValue += getPropertyValue(spaces, thisPlayer, property);
            }

            for (Property property : trade.getReceiverProperties()) { //This adds the AI's calculated worth of each Property to the values
                receiverValue += getPropertyValue(spaces, thisPlayer, property);
            }

            for (Card card : trade.getSenderCards()) { //Each get out of jail free Card the Player is offering is worth $25
                if (card.IS_GET_OUT_JAIL()) {
                    senderValue += 25;
                }
            }

            for (Card card : trade.getReceiverCards()) { //Each get out out of jail free Card the Player is offering is worth $25
                if (card.IS_GET_OUT_JAIL()) {
                    receiverValue += 25;
                }
            }

            senderValue += trade.getSenderMoney(); //Finally, we'll add the raw value each Player is offering

            receiverValue += trade.getReceiverMoney(); //Finally, we'll add the raw value each Player is offering


            if (thisPlayer.equals(trade.getSENDER())) { //Now we'll complete our analysis
                return receiverValue - senderValue;
            } else {
                return senderValue - receiverValue;
            }
        } else {
            throw new IllegalArgumentException("An invalid parameter was passed");
        }
    }

    /**
     * Calculates the percentage of the board (as a double) that is unowned
     *
     * @param spaces the game board to read
     * @return the percentage of the board that is unowned
     * @throws IllegalArgumentException when a null Spaces Array or Space is passed
     */
    private static double percentUnowned(Space[] spaces) {
        if (spaces != null) {
            int numOwned = 0;
            for (Space space : spaces) {
                if (space != null) {
                    if (space.getPROPERTY() != null && space.getPROPERTY().getOwner() != null) {
                        numOwned++;
                    }
                } else {
                    throw new IllegalArgumentException("A null Space was passed");
                }
            }

            return (double) numOwned / spaces.length;
        } else {
            throw new IllegalArgumentException("A null Spaces Array was passed");
        }
    }

    /*
    The following methods are the implementation of the Player Interface.
     */

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
     * @param object      the object that is part of this prompt
     * @return the Players decision whether or not to buy the card
     * @throws IllegalArgumentException when a null or invalid prompt is passed
     */
    @Override
    public <T> boolean promptBoolean(String description, T object) {
        if (description != null) {
            int index = recognizePrompt(PROMPTS, description);
            if (index == 0 && object == null) {
                return percentUnowned(GAME_BOARD) < .75; //If less than 75% of the board is unowned it's worth getting out
            } else if (index == 2 && object == null) {
                return percentUnowned(GAME_BOARD) < .75; //If less than 75% of the board is unowned it's worth getting out
            } else if (index == 3 && object instanceof Property) { //It's always worth participating in an auction
                if (GAME_BOARD[recognizeSpace(GAME_BOARD, object)].getPROPERTY() != null &&
                        GAME_BOARD[recognizeSpace(GAME_BOARD, object)].getPROPERTY().getOwner() == null) { //This just validates the passed Space
                    return true;
                } else {
                    throw new IllegalArgumentException("An invalid Property was passed");
                }
            } else if (index == 4 && object instanceof Property) { //It's usually worth buying a Property
                Property property = GAME_BOARD[recognizeSpace(GAME_BOARD, object)].getPROPERTY(); //This just validates the passed Space
                if (property != null && property.getOwner() == null) {
                    return wallet - property.getPRICE() > calcMinWallet(GAME_BOARD, this);
                } else {
                    throw new IllegalArgumentException("An invalid Property was passed");
                }
            } else if (index == 22 && (object instanceof Player)) {
                return true;
            } else if (index == 25 && object instanceof Property) {
                Property property = GAME_BOARD[recognizeSpace(GAME_BOARD, object)].getPROPERTY(); //This just validates the passed Space
                if (property != null && property.getOwner() != null) {
                    return wallet - property.getUnMORTGAGE() > calcMinWallet(GAME_BOARD, this);
                } else {
                    throw new IllegalArgumentException("An invalid Property was passed");
                }
            } else {
                throw new IllegalArgumentException("An invalid Property was passed");
            }
        } else {
            throw new IllegalArgumentException("A null description was passed");
        }
    }

    /**
     * Prompts the Player for an integer value
     *
     * @param description the description that should be shown to the Player
     * @param min         the minimum value that the Player should be able to enter. Should be equal to none for none
     * @param max         the maximum value that the Player should be able to enter. Should be equal to none for none
     * @param none        the value the Player should enter for none
     * @param object      the object that is part of this prompt
     * @return the integer that the Player decides on
     * @throws IllegalArgumentException when a null or invalid prompt is passed
     */
    @Override
    public <T> int promptInt(String description, int min, int max, int none, T object) {
        if (description != null) {
            int index = recognizePrompt(PROMPTS, description);
            if (index == 13 && object instanceof Trade) {
                if (this.equals(((Trade) object).getSENDER())) { //If we're the sender we shouldn't add more money
                    return none;
                } else if (this.equals(((Trade) object).getRECEIVER())) {
                    if (analyzeTrade(GAME_BOARD, (Trade) object, this) < 0) { //If we're the receiver and analyzeTrade calculates the Trade as not in our favor, we'll add the missing value back here
                        return Math.min(-analyzeTrade(GAME_BOARD, (Trade) object, this), max); //This ensures that we don't add too much money
                    } else {
                        return none;
                    }
                } else {
                    throw new IllegalArgumentException("An invalid prompt was passed");
                }
            } else if (index == 14 && object instanceof Trade) {
                if (this.equals(((Trade) object).getSENDER())) {
                    if (analyzeTrade(GAME_BOARD, (Trade) object, this) < 0) { //If we're the sender and analyzeTrade calculates that we're on the loosing end then we should offer less money
                        return Math.min(-analyzeTrade(GAME_BOARD, (Trade) object, this), max); //This ensures that we don't take away too much money
                    } else {
                        return none;
                    }
                } else if (this.equals(((Trade) object).getRECEIVER())) { //If we're the receiver we shouldn't make the sender offer less
                    return none;
                } else {
                    throw new IllegalArgumentException("An invalid prompt was passed");
                }
            } else if (index == 19 && object instanceof Trade) {
                if (this.equals(((Trade) object).getRECEIVER())) { //If we're the receiver we shouldn't add more money
                    return none;
                } else if (this.equals(((Trade) object).getSENDER())) {
                    if (analyzeTrade(GAME_BOARD, (Trade) object, this) < 0) { //If we're the sender and analyzeTrade calculates the Trade as not in our favor, we'll add the missing value back here
                        return Math.min(-analyzeTrade(GAME_BOARD, (Trade) object, this), max); //This ensures we don't add too much money
                    } else {
                        return none;
                    }
                } else {
                    throw new IllegalArgumentException("An invalid prompt was passed");
                }
            } else if (index == 20 && object instanceof Trade) {
                if (this.equals(((Trade) object).getRECEIVER())) { //I
                    if (analyzeTrade(GAME_BOARD, (Trade) object, this) < 0) { //If we're the receiver and analyzeTrade calculates that we're on the loosing end we should offer less
                        return Math.min(-analyzeTrade(GAME_BOARD, (Trade) object, this), max); //This ensures that we don't take away too much money
                    } else {
                        return none;
                    }
                } else if (this.equals(((Trade) object).getSENDER())) { //If we're the sender we shouldn't take away any money
                    return none;
                } else {
                    throw new IllegalArgumentException("An invalid prompt was passed");
                }
            } else if (index == 21 && object instanceof Trade) {
                if (analyzeTrade(GAME_BOARD, (Trade) object, this) >= 0) { //If analyzeTrade calculates the Trade as at least fair, then we'll accept it
                    return max;
                } else { //If it's not fair we'll keep going
                    return none;
                }
            } else if (index == 26 && object instanceof Auction) {
                int safeMax = wallet - calcMinWallet(GAME_BOARD, this);
                int value = getValues(GAME_BOARD, this)[recognizeSpace(GAME_BOARD, ((Auction) object).getPROPERTY())];
                if (((Auction) object).getCurrentValue() < value) { //If this is the case, we're still willing to bid for the Property
                    return (int) (min + (Math.min(safeMax, value) - min) * Math.random()); //This adds a little bit of randomization to what the AI will bid
                } else { //This auctions already gone past what we're willing to pay for this, so we're done
                    return none;
                }
            } else {
                throw new IllegalArgumentException("An invalid prompt was passed");
            }
        } else {
            throw new IllegalArgumentException("A null description was passed");
        }
    }

    /**
     * Prompts the Player to pick an Object out of the provided Array
     *
     * @param description the description that should be shown to the Player
     * @param objects     the Array that the Player should pick from
     * @param extra       an additional Object that may be provided
     * @return the index of the chosen Object
     */
    @Override
    public <T, S> int promptArray(String description, T[] objects, S extra) {
        if (description != null && objects != null && objects.length > 0) {
            int index = recognizePrompt(PROMPTS, description);
            if (index == 5 && objects[0] instanceof Property && extra == null) { //We should never sell houses if we don't have to
                for (T object : objects) { //This just validates the passed Objects
                    Property property = GAME_BOARD[recognizeSpace(GAME_BOARD, object)].getPROPERTY();
                    if (property != null && property.getOwner().equals(this) && property.getNumHouses() > 0) {
                        return -1;
                    } else {
                        throw new IllegalArgumentException("An invalid Property was passed");
                    }
                }
            } else if (index == 6 && objects[0] instanceof Property && extra == null) { //We should never mortgage if we don't have to
                for (T object : objects) { //This just validates the passed Objects
                    Property property = GAME_BOARD[recognizeSpace(GAME_BOARD, object)].getPROPERTY();
                    if (property != null && property.getOwner().equals(this) && property.canSell()) {
                        return -1;
                    } else {
                        throw new IllegalArgumentException("An invalid Properties Array was passed");
                    }
                }
            } else if (index == 7 && objects[0] instanceof Property && extra == null) { //If we can un-mortgage things without going bankrupt, we probably should
                Property[] properties = new Property[objects.length];
                for (int i = 0; i < objects.length; i++) { //Validates all of the passed Properties
                    properties[i] = GAME_BOARD[recognizeSpace(GAME_BOARD, objects[i])].getPROPERTY();
                    if (properties[i] == null || !properties[i].getOwner().equals(this) || !properties[i].isMortgaged()) {
                        throw new IllegalArgumentException("An invalid Property was passed");
                    }
                }
                int maxSafe = wallet - calcMinWallet(GAME_BOARD, this);
                int best = -1;
                for (int i = 0; i < properties.length; i++) { //This calculates the best deal that the AI can get
                    if (wallet - properties[i].getUnMORTGAGE() >= maxSafe && (best == -1 ||
                            getPropertyValue(GAME_BOARD, this, properties[i]) >
                                    getPropertyValue(GAME_BOARD, this, properties[best]))) { //If the AI considers this Property to be more, then this one should be the best
                        best = i;
                    } else if (wallet - properties[i].getUnMORTGAGE() >= maxSafe &&
                            getPropertyValue(GAME_BOARD, this, properties[best]) ==
                                    getPropertyValue(GAME_BOARD, this, properties[i]) &&
                            properties[i].getUnMORTGAGE() < properties[best].getUnMORTGAGE()) { //If the AI considers these two to have the same value, but the read one is cheaper, we should go with that one
                        best = i;
                    }
                }
                return best;
            } else if (index == 8 && objects[0] instanceof Property && extra == null) { //If we should build houses without going bankrupt, we should
                Property[] properties = new Property[objects.length];
                for (int i = 0; i < objects.length; i++) { //Validates all of the passed Properties
                    properties[i] = GAME_BOARD[recognizeSpace(GAME_BOARD, objects[i])].getPROPERTY();
                    if (properties[i] == null || !properties[i].getOwner().equals(this) || !properties[i].canBuild()) {
                        throw new IllegalArgumentException("An invalid Property was passed");
                    }
                }
                int maxSafe = wallet - calcMinWallet(GAME_BOARD, this);
                int best = -1;
                for (int i = 0; i < properties.length; i++) { //This calculates the best deal that the AI can get
                    if (wallet - properties[i].getBUILD_PRICE() >= maxSafe && (best == -1 ||
                            getPropertyValue(GAME_BOARD, this, properties[i]) >
                                    getPropertyValue(GAME_BOARD, this, properties[i]))) {
                        best = i;
                    } else if (wallet - properties[i].getBUILD_PRICE() >= maxSafe &&
                            getPropertyValue(GAME_BOARD, this, properties[i]) ==
                                    getPropertyValue(GAME_BOARD, this, properties[best]) &&
                            properties[i].getBUILD_PRICE() < properties[best].getBUILD_PRICE()) {
                        best = i;
                    }
                }
                return best;
            } else if (index == 9 && objects[0] instanceof Property && extra instanceof Trade) {
                Property[] properties = new Property[objects.length];
                for (int i = 0; i < objects.length; i++) { //This validates the Properties
                    properties[i] = GAME_BOARD[recognizeSpace(GAME_BOARD, objects[i])].getPROPERTY();
                    if (properties[i] == null || !properties[i].canSell()) {
                        throw new IllegalArgumentException("An invalid Property was passed");
                    }
                }
                if (this.equals(((Trade) extra).getSENDER())) { //If we're the sender we don't want to offer any more
                    return -1;
                } else if (this.equals(((Trade) extra).getRECEIVER())) { //Here's where the AI will pursue wanted Properties
                    if (analyzeTrade(GAME_BOARD, (Trade) extra, this) <= 0) { //Here the AI will actively pursue Properties if the Trade is neutral
                        int best = 0;
                        for (int i = 1; i < properties.length; i++) {
                            if (getPropertyValue(GAME_BOARD, this, properties[i]) > getPropertyValue(GAME_BOARD, this, properties[best])) {
                                best = i;
                            }
                        }

                        if (analyzeTrade(GAME_BOARD, (Trade) extra, this) == 0 &&
                                getPropertyValue(GAME_BOARD, this, properties[best]) <=
                                        properties[best].getPRICE()) { //If the trade is neutral and the AI doesn't place a special value on this Property it shouldn't deliberately disrupt the balance
                            return -1;
                        } else {
                            return best;
                        }
                    } else {
                        return -1;
                    }
                } else {
                    throw new IllegalArgumentException("An invalid prompt was passed");
                }
            } else if (index == 10 && objects[0] instanceof Property && extra instanceof Trade) {
                Property[] properties = new Property[objects.length];
                for (int i = 0; i < objects.length; i++) { //This validates the Properties
                    properties[i] = GAME_BOARD[recognizeSpace(GAME_BOARD, objects[i])].getPROPERTY();
                    if (properties[i] == null || !properties[i].canSell()) {
                        throw new IllegalArgumentException("An invalid Property was passed");
                    }
                }
                if (this.equals(((Trade) extra).getSENDER())) {
                    if (analyzeTrade(GAME_BOARD, (Trade) extra, this) < 0) { //If analyzeTrade determines the Trade isn't in our favor, we should offer less Properties to get it there
                        int value = analyzeTrade(GAME_BOARD, (Trade) extra, this);
                        int best = 0;
                        for (int i = 1; i < properties.length; i++) { //This makes sure that the Property we're using gets us as close to 0 trade value as possible
                            if (Math.abs(value + getPropertyValue(GAME_BOARD, this, properties[i])) <
                                    Math.abs(value + getPropertyValue(GAME_BOARD, this, properties[best]))) {
                                best = i;
                            }
                        }
                        return best;
                    }
                } else if (this.equals(((Trade) extra).getRECEIVER())) { //If we're the sender, we don't want the receiver offering any less
                    return -1;
                } else {
                    throw new IllegalArgumentException("An invalid prompt was passed");
                }
            } else if (index == 11 && objects[0] instanceof Card && extra instanceof Trade) {
                Card[] cards = new Card[objects.length];
                for (int i = 0; i < objects.length; i++) { //This validates the Cards
                    if (objects[i] instanceof Card) {
                        cards[i] = (Card) objects[i];
                        if (cards[i].getOwner() == null) {
                            throw new IllegalArgumentException("An invalid Card was passed");
                        }
                    } else {
                        throw new IllegalArgumentException("An invalid prompt was passed");
                    }
                }
                if (this.equals(((Trade) extra).getSENDER())) { //If we're the sender, we don't want to offer any more than we have to
                    return -1;
                } else if (this.equals(((Trade) extra).getRECEIVER())) {
                    if (analyzeTrade(GAME_BOARD, (Trade) extra, this) < 0) { //The AI can use Cards to balance a Trade, but won't persue them
                        for (int i = 0; i < cards.length; i++) {
                            if (cards[i].IS_GET_OUT_JAIL()) { //The AI will only place values on get out of jail free Cards
                                return i;
                            }
                        }
                    }
                    return -1;
                } else {
                    throw new IllegalArgumentException("An invalid prompt was passed");
                }
            } else if (index == 12 && objects[0] instanceof Card && extra instanceof Trade) {
                Card[] cards = new Card[objects.length];
                for (int i = 0; i < objects.length; i++) { //This validates the Cards
                    if (objects[i] instanceof Card) {
                        cards[i] = (Card) objects[i];
                        if (cards[i].getOwner() == null) {
                            throw new IllegalArgumentException("An invalid Card was passed");
                        }
                    } else {
                        throw new IllegalArgumentException("An invalid prompt was passed");
                    }
                }
                if (this.equals(((Trade) extra).getSENDER())) {
                    if (analyzeTrade(GAME_BOARD, (Trade) extra, this) < 0) { //If the Trade is unfavorable, the AI can use Cards to try and balance it
                        for (int i = 0; i < cards.length; i++) {
                            if (cards[i].IS_GET_OUT_JAIL()) { //The AI will only place values on get out of jail free Cards
                                return i;
                            }
                        }
                    }
                    return -1;
                } else if (this.equals(((Trade) extra).getRECEIVER())) { //If we're the receiver, we don't want the sender offering any less
                    return -1;
                } else {
                    throw new IllegalArgumentException("An invalid prompt was passed");
                }
            } else if (index == 15 && objects[0] instanceof Property && extra instanceof Trade) {
                Property[] properties = new Property[objects.length];
                for (int i = 0; i < objects.length; i++) { //This validates the Properties
                    properties[i] = GAME_BOARD[recognizeSpace(GAME_BOARD, objects[i])].getPROPERTY();
                    if (properties[i] == null || !properties[i].canSell()) {
                        throw new IllegalArgumentException("An invalid Property was passed");
                    }
                }
                if (this.equals(((Trade) extra).getRECEIVER())) { //If we're the receiver we don't want to offer any more
                    return -1;
                } else if (this.equals(((Trade) extra).getSENDER())) { //Here's where the AI will pursue wanted Properties
                    if (analyzeTrade(GAME_BOARD, (Trade) extra, this) <= 0) { //Here the AI will actively pursue Properties if the Trade is neutral
                        int best = 0;
                        for (int i = 1; i < properties.length; i++) {
                            if (getPropertyValue(GAME_BOARD, this, properties[i]) > getPropertyValue(GAME_BOARD, this, properties[best])) {
                                best = i;
                            }
                        }

                        if (analyzeTrade(GAME_BOARD, (Trade) extra, this) == 0 &&
                                getPropertyValue(GAME_BOARD, this, properties[best]) <=
                                        properties[best].getPRICE()) { //If the trade is neutral and the AI doesn't place a special value on this Property it shouldn't deliberately disrupt the balance
                            return -1;
                        } else {
                            return best;
                        }
                    } else {
                        return -1;
                    }
                } else {
                    throw new IllegalArgumentException("An invalid prompt was passed");
                }
            } else if (index == 16 && objects[0] instanceof Property && extra instanceof Trade) {
                Property[] properties = new Property[objects.length];
                for (int i = 0; i < objects.length; i++) { //This validates the Properties
                    properties[i] = GAME_BOARD[recognizeSpace(GAME_BOARD, objects[i])].getPROPERTY();
                    if (properties[i] == null || !properties[i].canSell()) {
                        throw new IllegalArgumentException("An invalid Property was passed");
                    }
                }
                if (this.equals(((Trade) extra).getRECEIVER())) {
                    if (analyzeTrade(GAME_BOARD, (Trade) extra, this) < 0) { //If analyzeTrade determines the Trade isn't in our favor, we should offer less Properties to get it there
                        int value = analyzeTrade(GAME_BOARD, (Trade) extra, this);
                        int best = 0;
                        for (int i = 1; i < properties.length; i++) { //This makes sure that the Property we're using gets us as close to 0 trade value as possible
                            if (Math.abs(value + getPropertyValue(GAME_BOARD, this, properties[i])) <
                                    Math.abs(value + getPropertyValue(GAME_BOARD, this, properties[best]))) {
                                best = i;
                            }
                        }
                        return best;
                    }
                } else if (this.equals(((Trade) extra).getSENDER())) { //If we're the receiver, we don't want the sender offering any less
                    return -1;
                } else {
                    throw new IllegalArgumentException("An invalid prompt was passed");
                }
            } else if (index == 17 && objects[0] instanceof Card && extra instanceof Trade) {
                Card[] cards = new Card[objects.length];
                for (int i = 0; i < objects.length; i++) { //This validates the Cards
                    if (objects[i] instanceof Card) {
                        cards[i] = (Card) objects[i];
                        if (cards[i].getOwner() == null) {
                            throw new IllegalArgumentException("An invalid Card was passed");
                        }
                    } else {
                        throw new IllegalArgumentException("An invalid prompt was passed");
                    }
                }
                if (this.equals(((Trade) extra).getRECEIVER())) { //If we're the receiver, we don't want to offer any more than we have to
                    return -1;
                } else if (this.equals(((Trade) extra).getSENDER())) {
                    if (analyzeTrade(GAME_BOARD, (Trade) extra, this) < 0) { //The AI can use Cards to balance a Trade, but won't pursue them
                        for (int i = 0; i < cards.length; i++) {
                            if (cards[i].IS_GET_OUT_JAIL()) { //The AI will only place values on get out of jail free Cards
                                return i;
                            }
                        }
                    }
                    return -1;
                } else {
                    throw new IllegalArgumentException("An invalid prompt was passed");
                }
            } else if (index == 18 && objects[0] instanceof Card && extra instanceof Trade) {
                Card[] cards = new Card[objects.length];
                for (int i = 0; i < objects.length; i++) { //This validates the Cards
                    if (objects[i] instanceof Card) {
                        cards[i] = (Card) objects[i];
                        if (cards[i].getOwner() == null) {
                            throw new IllegalArgumentException("An invalid Card was passed");
                        }
                    } else {
                        throw new IllegalArgumentException("An invalid prompt was passed");
                    }
                }
                if (this.equals(((Trade) extra).getRECEIVER())) {
                    if (analyzeTrade(GAME_BOARD, (Trade) extra, this) < 0) { //If the Trade is unfavorable, the AI can use Cards to try and balance it
                        for (int i = 0; i < cards.length; i++) {
                            if (cards[i].IS_GET_OUT_JAIL()) { //The AI will only place values on get out of jail free Cards
                                return i;
                            }
                        }
                    }
                    return -1;
                } else if (this.equals(((Trade) extra).getSENDER())) { //If we're the sender, we don't want the receiver offering any less
                    return -1;
                } else {
                    throw new IllegalArgumentException("An invalid prompt was passed");
                }
            } else if (index == 22 && (objects[0] instanceof Player)
                    && extra == null) {
                Player[] players = new Player[objects.length];
                for (int i = 0; i < objects.length; i++) { //This validates the Players
                    if (objects[i] instanceof Player) {
                        players[i] = (Player) objects[i];
                        if (players[i].equals(this)) {
                            throw new IllegalArgumentException("AI was prompted to trade itself");
                        }
                    } else {
                        throw new IllegalArgumentException("An invalid prompt was passed");
                    }
                }
                Property bestValueProperty = null;
                int bestPlayer = -1;
                for (int i = 0; i < players.length; i++) {
                    for (Property property : Game.playerProperties(GAME_BOARD, players[i])) {
                        if (getPropertyValue(GAME_BOARD, this, property) > property.getPRICE() &&
                                (bestValueProperty == null || getPropertyValue(GAME_BOARD, this, property) >
                                        getPropertyValue(GAME_BOARD, this, bestValueProperty))) { //This gets the best Property and ensures that the AI only persues Properties it actually wants
                            bestValueProperty = property;
                            bestPlayer = i;
                        }
                    }
                }
                return bestPlayer;
            } else if (index == 23 && objects[0] instanceof Property && extra == null) {
                if (wallet < 0) {
                    Property[] properties = new Property[objects.length];
                    for (int i = 0; i < objects.length; i++) { //This validates the Properties
                        properties[i] = GAME_BOARD[recognizeSpace(GAME_BOARD, objects[i])].getPROPERTY();
                        if (!properties[i].getOwner().equals(this) || properties[i].getNumHouses() == 0) {
                            throw new IllegalArgumentException("An invalid Property was passed");
                        }
                    }

                    int lowestValueProperty = 0;
                    for (int i = 1; i < properties.length; i++) { //This finds the lowest value Property in the list
                        if (getPropertyValue(GAME_BOARD, this, properties[i]) <
                                getPropertyValue(GAME_BOARD, this, properties[lowestValueProperty])) {
                            lowestValueProperty = i;
                        }
                    }
                    return lowestValueProperty;
                } else {
                    throw new IllegalArgumentException("An invalid prompt was passed");
                }
            } else if (index == 24 && objects[0] instanceof Property && extra == null) {
                if (wallet < 0) {
                    Property[] properties = new Property[objects.length];
                    for (int i = 0; i < objects.length; i++) { //This validates the Properties
                        properties[i] = GAME_BOARD[recognizeSpace(GAME_BOARD, objects[i])].getPROPERTY();
                        if (!properties[i].getOwner().equals(this) || !properties[i].canSell()) {
                            throw new IllegalArgumentException("An invalid Property was passed");
                        }
                    }

                    int lowestValueProperty = 0;
                    for (int i = 1; i < properties.length; i++) { //This finds the lowest value Property in the list
                        if (getPropertyValue(GAME_BOARD, this, properties[i]) <
                                getPropertyValue(GAME_BOARD, this, properties[lowestValueProperty])) {
                            lowestValueProperty = i;
                        }
                    }
                    return lowestValueProperty;
                } else {
                    throw new IllegalArgumentException("An invalid prompt was passed");
                }
            } else {
                throw new IllegalArgumentException("An invalid prompt was passed");
            }
        } else {
            throw new IllegalArgumentException("An invalid prompt was passed");
        }
        throw new IllegalArgumentException("An invalid prompt was passed");
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

    /**
     * Gets the Players Color
     *
     * @return the Players Color
     */
    @Override
    public Color getCOLOR() {
        return COLOR;
    }

    /**
     * Gets the game board's size
     *
     * @return the game board's size
     */
    @Override
    public int getBOARD_SIZE() {
        return BOARD_SIZE;
    }
}
