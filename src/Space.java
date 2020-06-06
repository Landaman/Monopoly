/**
 * Represents a Space on the gameBoard. Each Space can have some effect, but not more than one. These can include a
 * Community Chest or Chance Card, a Property, some sort of monetary effect, a movement effect, or a Space they should go to
 *
 * @author irswr
 */
public class Space {
    //Space constants
    private final String NAME; //Stores this Space's name. Used exclusively for UI purposes, has no internal uses
    private final int MONEY_PENALTY; //Stores the monetary penalty for landing on this Space. Should be 0 for no effect
    private final int MOVEMENT_PENALTY; //Stores the movement penalty for landing on this Space. Should be 0 for no effect
    private final int SPACE_PENALTY; //Stores the index of the Space that the Player should go to as a penalty for landing on this Space. This shouldn't be less than -1, which means no effect
    private final String COLOR_GROUP; //Stores the color group that this Player should go to as a penalty for landing on this Space. Can be null for none
    private final double RENT_MULTIPLIER; //Stores the multiplier of the rent of the next Space the Player should land on
    private final double ROLL_MULTIPLIER; //Stores the multiplier that the Player should pay on a new roll on the Space they land on. This requires RENT_MULTIPLIER to be 0 to cancel the previous roll
    private final int PRICE_PER_HOUSE; //Stores the amount the Player should pay per house owned. Should be 0 for no charge
    private final int PRICE_PER_HOTEL; //Stores the amount the Player should pay per hotel owned. Should be 0 for no charge
    private final int DECK_USED; //Stores the index of the Deck this Space uses. Shouldn't be less than -1, which represents no Deck
    private final Property PROPERTY; //Stores this Space's Property. This can be null

    /**
     * Constructor for Space. Validates the conditions outlined here
     *
     * @param name           the Space's name. This shouldn't be null
     * @param moneyLoss      the monetary penalty for landing on this Space. Should be 0 for no effect
     * @param movementLoss   the movement penalty for landing on this Space. Should be 0 for no effect
     * @param spaceLoss      the index of the Space that the Player should go to for landing on this Space. This shouldn't
     *                       be less than -1
     * @param colorGroup     the color group that the Player should move to for landing on this Space. This can be null
     * @param rentMultiplier the multiplier of the the rent of the next Space the Player should land on
     * @param rollMultiplier the multiplier the Player should pay on a new roll
     * @param perHouse       the amount the Player should pay per house owned. Shouldn't be less than 0
     * @param perHotel       the amount the Player should pay per hotel owned. Shouldn't be less than 0
     * @param deckUsed       the index of the Deck this board uses.
     * @param boardSize      the board's size. Used for validation, is not stored
     * @param numDecks       the number of Decks. Used for validation, is not stored
     * @param property       the Property to be stored on this Space. This can be null
     * @throws IllegalArgumentException when the passed parameters are invalid
     */
    public Space(String name, int moneyLoss, int movementLoss, int spaceLoss, String colorGroup, double rentMultiplier,
                 double rollMultiplier, int perHouse, int perHotel, int deckUsed, int boardSize, int numDecks,
                 Property property) {
        if (name != null && name.length() > 0 && spaceLoss >= -1 && deckUsed >= -1 && deckUsed < numDecks && rentMultiplier >= 0 &&
                rollMultiplier >= 0 && spaceLoss < boardSize &&
                !((perHouse != 0 && perHotel == 0) || (perHouse == 0 && perHotel != 0)) &&
                !(rollMultiplier != 0 && rentMultiplier != 0) &&
                !((movementLoss == 0 && spaceLoss == -1 && colorGroup == null && rentMultiplier != 1.0)) &&
                !((moneyLoss != 0 || movementLoss != 0 || spaceLoss != -1 || colorGroup != null || perHouse != 0 || deckUsed != -1) && property != null) &&
                !((moneyLoss != 0 || movementLoss != 0 || spaceLoss != -1 || colorGroup != null || perHouse != 0 || property != null) && deckUsed != -1) &&
                !((moneyLoss != 0 || movementLoss != 0 || spaceLoss != -1 || colorGroup != null || deckUsed != -1 || property != null) && perHouse != 0) &&
                !((moneyLoss != 0 || movementLoss != 0 || spaceLoss != -1 || perHouse != 0 || deckUsed != -1 || property != null) && colorGroup != null) &&
                !((moneyLoss != 0 || movementLoss != 0 || colorGroup != null || perHouse != 0 || deckUsed != -1 || property != null) && spaceLoss != -1) &&
                !((moneyLoss != 0 || spaceLoss != -1 || colorGroup != null || perHouse != 0 || deckUsed != -1 || property != null) && movementLoss != 0) &&
                !(property != null && !name.equals(property.toString()))) {
            NAME = name;
            MONEY_PENALTY = moneyLoss;
            MOVEMENT_PENALTY = movementLoss;
            SPACE_PENALTY = spaceLoss;
            COLOR_GROUP = colorGroup;
            RENT_MULTIPLIER = rentMultiplier;
            ROLL_MULTIPLIER = rollMultiplier;
            PRICE_PER_HOUSE = perHouse;
            PRICE_PER_HOTEL = perHotel;
            DECK_USED = deckUsed;
            PROPERTY = property;
        } else {
            throw new IllegalArgumentException("An invalid parameter was passed");
        }
    }

    /**
     * Gets the name of this Space
     *
     * @return the name of this Space
     */
    @Override
    public String toString() {
        return NAME;
    }

    /**
     * Gets the Property held by this Space, if it has one
     *
     * @return the Property held by this Space, if applicable
     */
    public Property getPROPERTY() {
        return PROPERTY;
    }

    /**
     * Gets the money penalty for landing on this Space
     *
     * @return the money penalty for landing on this Space
     */
    public int getMONEY_PENALTY() {
        return MONEY_PENALTY;
    }

    /**
     * Gets the movement penalty for landing on this Space
     *
     * @return the movement penalty for landing on this Space
     */
    public int getMOVEMENT_PENALTY() {
        return MOVEMENT_PENALTY;
    }

    /**
     * Gets the Space that the Player should go to for landing on this Space
     *
     * @return the Space that the Player should go to for landing on this Space
     */
    public int getSPACE_PENALTY() {
        return SPACE_PENALTY;
    }

    /**
     * Gets the color group that the Player should go to for landing on this Space
     *
     * @return the Space that the Player should go to for landing on this Space
     */
    public String getCOLOR_GROUP() {
        return COLOR_GROUP;
    }

    /**
     * Gets the rent multiplier that the Player should have to pay for landing on this Space
     *
     * @return the rent multiplier
     */
    public double getRENT_MULTIPLIER() {
        return RENT_MULTIPLIER;
    }

    /**
     * Gets the roll multiplier that the Player should pay for landing on this Space
     *
     * @return the roll multiplier
     */
    public double getROLL_MULTIPLIER() {
        return ROLL_MULTIPLIER;
    }

    /**
     * Gets the price per house that the Player should pay for landing on this Space
     *
     * @return the price per house for landing on this Space
     */
    public int getPRICE_PER_HOUSE() {
        return PRICE_PER_HOUSE;
    }

    /**
     * Gets the price per hotel that the Player should pay for landing on this Space
     *
     * @return the price per hotel for landing on this Space
     */
    public int getPRICE_PER_HOTEL() {
        return PRICE_PER_HOTEL;
    }

    /**
     * Gets the deck the Player should draw from for landing on this Space
     *
     * @return the deck the Player should draw from
     */
    public int getDECK_USED() {
        return DECK_USED;
    }
}

