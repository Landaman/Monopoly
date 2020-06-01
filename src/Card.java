/**
 * Represents a Card that has an effect on the Player
 * Cards can have either a money penalty (this can be applied to just the landed player or to all players),
 * a movement penalty, a color group that the player should go to, a cost per house AND hotel,
 * a space that the Player should go to, or be a get out of jail free card.
 * The constructor will ensure that only one of these is true
 *
 * @author irswr
 */

public class Card {
    //Card constants
    private final String TYPE; //Stores this Card's type
    private final String DESCRIPTION; //Stores this Card's description
    private final int MONEY; //Stores the amount of money the Player should gain. Should be 0 for no change
    private final boolean PER_PLAYER; //Stores whether the effect of this Card should be charged to each other Player. Should be false if not
    private final int MOVEMENT; //Stores the amount of Spaces the Player should move. Should be 0 for no movement
    private final int SPACE; //Stores the index of the Space the Player should go to. Should be -1 for no movement
    private final String COLOR_GROUP; //Stores the color group the Player should go to. Should be null if they shouldn't move
    private final int PRICE_PER_HOUSE; //Stores the amount the Player should pay per house owned. Should be 0 for no charge
    private final int PRICE_PER_HOTEL; //Stores the amount the Player should pay per hotel owned. Should be 0 for no charge
    private final boolean IS_GET_OUT_JAIL; //Stores whether or not this Card is a get out of jail free. Should be false if not

    //Card fields
    private Player owner; //Stores the Player that currently holds this Card

    /**
     * Constructor for Card. Validates the constants as outlined here
     *
     * @param type        the type of the Card (I.E. Community Chest or Chance). This shouldn't be null
     * @param description the description of the Card. This shouldn't be null
     * @param money       the amount of money the Player should gain from this Card. Should be 0 for no change
     * @param perPlayer   whether the value of money should be enacted onto every other Player, and then the opposite of
     *                    the total done to this Player. Should be false if not
     * @param movement    the amount of Spaces the Player should move. Should be 0 for no movement
     * @param space       the index of the Space the Player should go to. Should be -1 for no movement
     * @param colorGroup  the color group that the Player should go to. Should be null if they shouldn't move
     * @param perHouse    the amount that the Player should pay per house owned. Should be 0 for no charge
     * @param perHotel    the amount that the Player should pay per hotel owned. Should be 0 for no charge
     * @param getOutJail  whether or not this is a get out of jail free Card. Should be false if not
     * @param own         the Player who holds this Card
     * @param boardSize   the board's size. Used for validation, is not stored
     * @throws IllegalArgumentException when an invalid parameter is passed
     */
    public Card(String type, String description, int money, boolean perPlayer, int movement, int space, String colorGroup,
                int perHouse, int perHotel, boolean getOutJail, Player own, int boardSize) {
        if (type != null && description != null && !(perPlayer && money == 0) && space >= -1 &&
                !((perHouse != 0 && perHotel == 0) || (perHouse == 0 && perHotel != 0)) &&
                !((money != 0 || movement != 0 || space != -1 || colorGroup != null || perHouse != 0) && getOutJail) &&
                !((money != 0 || movement != 0 || space != -1 || colorGroup != null || getOutJail) && perHouse != 0) &&
                !((money != 0 || movement != 0 || space != -1 || getOutJail || perHouse != 0) && colorGroup != null) &&
                !((money != 0 || movement != 0 || colorGroup != null || getOutJail || perHouse != 0) && space != -1) &&
                !((money != 0 || space != -1 || colorGroup != null || getOutJail || perHouse != 0) && movement != 0) &&
                space < boardSize) {
            TYPE = type;
            DESCRIPTION = description;
            MONEY = money;
            PER_PLAYER = perPlayer;
            MOVEMENT = movement;
            SPACE = space;
            COLOR_GROUP = colorGroup;
            PRICE_PER_HOUSE = perHouse;
            PRICE_PER_HOTEL = perHotel;
            IS_GET_OUT_JAIL = getOutJail;
            owner = own;
        } else {
            throw new IllegalArgumentException("An invalid parameter was passed");
        }
    }

    /**
     * Gets the Player who owns this Card. This can be null
     *
     * @return the Player who owns this Card, or null if no Player owns it
     */
    public Player getOwner() {
        return owner;
    }

    /**
     * Updates the owner of this Card
     *
     * @param newOwner the new owner of this card. Can be null
     */
    public void setOwner(Player newOwner) {
        owner = newOwner;
    }

    /**
     * Gets the amount of money the Player should pay for landing on this Card
     *
     * @return the amount of money the Player should pay for landing on this Card
     */
    public int getMONEY() {
        return MONEY;
    }

    /**
     * Gets whether the amount of money in MONEY should be applied to every other Player
     *
     * @return whether the amount of money in MONEY should be applied to every other Player
     */
    public boolean isPER_PLAYER() {
        return PER_PLAYER;
    }

    /**
     * Gets the amount of Spaces that the Player should move for landing on this Space
     *
     * @return the amount of Spaces that the Player should move
     */
    public int getMOVEMENT() {
        return MOVEMENT;
    }

    /**
     * Gets the Space that the Player should go to for landing on this Card
     *
     * @return the index of the Space that the Player should go to
     */
    public int getSPACE() {
        return SPACE;
    }

    /**
     * Gets the color group that the Player should go to for landing on this Card
     *
     * @return the color group that the Player should go to
     */
    public String getCOLOR_GROUP() {
        return COLOR_GROUP;
    }

    /**
     * The amount the Player should pay per house owned for landing on this Card
     *
     * @return the amount the Player should pay per house owned
     */
    public int getPRICE_PER_HOUSE() {
        return PRICE_PER_HOUSE;
    }

    /**
     * The amount the Player should pay per hotel owned for landing on this Card
     *
     * @return the amount the Player should pay per hotel owned
     */
    public int getPRICE_PER_HOTEL() {
        return PRICE_PER_HOTEL;
    }

    /**
     * Gets whether or not this Card is a get out of jail free Card
     *
     * @return whether or not this Card is a get out of jail free Card
     */
    public boolean IS_GET_OUT_JAIL() {
        return IS_GET_OUT_JAIL;
    }
}
