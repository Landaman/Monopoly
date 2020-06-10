/**
 * Represents a Property
 *
 * @author irswr
 */
public class Property {
    //Property constants
    private final int PRICE; //Stores this Property's buy price. This should be greater than 0
    private final int MORTGAGE; //Stores the value the Player gets when this Property is mortgaged. This should be greater than 0 and less than PRICE
    private final double MORTGAGE_PERCENT; //Stores the percent of the Mortgage that should be charged as interest
    private final int BUILD_PRICE; //Stores the amount of money the Player needs to pay in order to build more on this land. This should be greater than 0
    private final String COLOR_GROUP; //Stores this Property's color group. This shouldn't be null
    private final int MAX_HOUSES; //Stores this Property's maximum number of houses. This shouldn't be negative
    private final int[] RENTS; //Stores this Property's rents at given states of ownership. This shouldn't be null
    private final boolean IS_DICE_MULTIPLIER; //Stores whether the values in RENTS are multipliers of the Players roll
    private final boolean IS_SCALED; //Stores whether RENTS scales based on how many Properties a Player owns in a group, rather than one point for a monopoly and the rest for houses
    private final String NAME; //This Properties name

    //Property fields
    private int rent; //Stores this Property's rent value
    private int numHouses; //Stores the number of houses on this Property
    private Player owner; //Stores the Player who owns this Property. This can be null
    private boolean isMortgaged; //Stores whether this Property is mortgaged

    /**
     * Constructor for Property. Validates the conditions outlined here
     *
     * @param price            the price of this Property. This should be greater than 0
     * @param mortgage         the mortgage of this Property. This should be greater than 0 and less than price
     * @param mortgagePercent  the percent of the Properties mortgage that should be charged as interest
     * @param buildPrice       the price of building on this Property. This should be greater than 0
     * @param colorGroup       the color group of this Property. This shouldn't be null
     * @param maxHouses        the maximum number of houses on this Property. This shouldn't be negative
     * @param rents            the rents for this Property at given states of ownership. This shouldn't be null
     * @param startingHouses   the number of houses this Property should start with. This shouldn't be negative.
     *                         If this is a non-zero value propertyOwner shouldn't be null as you can't have a
     *                         Property that has houses but no owner
     * @param isDiceMultiplier whether or not the rent values are multipliers of the Players roll
     * @param isScaled         whether or not the rent values scaled based on
     * @param mortgaged        whether or not this Property is mortgaged. If this is true, the Property must have an owner
     * @param propertyOwner    the Player who should own this Property. This can be null
     * @param name             the name of the Property
     * @throws IllegalArgumentException when the passed parameters are invalid
     */
    public Property(int price, int mortgage, double mortgagePercent, int buildPrice, String colorGroup,
                    int maxHouses, int[] rents, int startingHouses, boolean isDiceMultiplier,
                    boolean isScaled, boolean mortgaged, Player propertyOwner, String name) {
        if (mortgage < price && mortgage > 0 && buildPrice >= 0 &&
                colorGroup != null && rents != null && rents.length > 0 &&
                startingHouses >= 0 && startingHouses <= maxHouses &&
                !(isScaled && maxHouses != 0) && !(startingHouses > 0 && propertyOwner == null) &&
                !(!isScaled && rents.length != maxHouses + 2) && !(mortgaged && propertyOwner == null) &&
                name != null) {
            PRICE = price;
            MORTGAGE = mortgage;
            MORTGAGE_PERCENT = mortgagePercent;
            BUILD_PRICE = buildPrice;
            COLOR_GROUP = colorGroup;
            MAX_HOUSES = maxHouses;
            RENTS = rents;
            numHouses = startingHouses;
            rent = rents[0];
            IS_DICE_MULTIPLIER = isDiceMultiplier;
            IS_SCALED = isScaled;
            isMortgaged = mortgaged;
            owner = propertyOwner;
            NAME = name;
        } else {
            throw new IllegalArgumentException("An invalid parameter was passed");
        }
    }

    /**
     * Copy constructor for Property
     *
     * @param property the Property to copy
     * @throws IllegalArgumentException when a null Property is passed
     */
    public Property(Property property) {
        if (property != null) {
            PRICE = property.PRICE;
            MORTGAGE = property.MORTGAGE;
            MORTGAGE_PERCENT = property.MORTGAGE_PERCENT;
            BUILD_PRICE = property.BUILD_PRICE;
            COLOR_GROUP = property.COLOR_GROUP;
            MAX_HOUSES = property.MAX_HOUSES;
            RENTS = property.RENTS;
            IS_DICE_MULTIPLIER = property.IS_DICE_MULTIPLIER;
            IS_SCALED = property.IS_SCALED;
            NAME = property.NAME;
            rent = property.rent;
            numHouses = property.numHouses;
            owner = property.owner;
            isMortgaged = property.isMortgaged;
        } else {
            throw new IllegalArgumentException("A null Property was passed");
        }
    }

    /**
     * Gets the price of this Property
     *
     * @return the price of this Property
     */
    public int getPRICE() {
        return PRICE;
    }

    /**
     * Gets this Property's owner, if it has one
     *
     * @return this Property's owner, if applicable
     */
    public Player getOwner() {
        return owner;
    }

    /**
     * Updates the Player who owns this Property
     *
     * @param newOwner the new owner of this Property
     * @throws IllegalStateException when this Property cannot be sold
     */
    public void setOwner(Player newOwner) {
        if (canSell()) {
            owner = newOwner;
        } else {
            throw new IllegalStateException("Cannot sell this Property");
        }
    }

    /**
     * Gets this Property's color group
     *
     * @return this Property's color group
     */
    public String getCOLOR_GROUP() {
        return COLOR_GROUP;
    }

    /**
     * Returns the rents for this Property
     * @return the rents for this Property
     */
    public int[] getRENTS() {
        return RENTS;
    }

    /**
     * Gets whether or not this Property uses a dice multiplier
     *
     * @return whether or not this Property uses a dice multiplier
     */
    public boolean IS_DICE_MULTIPLIER() {
        return IS_DICE_MULTIPLIER;
    }

    /**
     * Updates the value stored in the rent field to be appropriate given the state of the Board
     *
     * @param numPropertiesPlayerOwnsInGroup the number of Properties the Player owns in a color group
     * @param playerHasMonopoly              whether or not the Player has a monopoly over this color group
     * @throws IllegalStateException    when the Property is mortgaged and unowned
     * @throws IllegalArgumentException when an invalid parameter is passed
     */
    public void setRent(int numPropertiesPlayerOwnsInGroup, boolean playerHasMonopoly) {
        if (validateProperty() && numPropertiesPlayerOwnsInGroup >= 0 &&
                !(IS_SCALED && numPropertiesPlayerOwnsInGroup > RENTS.length) &&
                !(playerHasMonopoly && IS_SCALED && numPropertiesPlayerOwnsInGroup != RENTS.length)) {
            if (owner != null && !isMortgaged) { //If the Property is unowned or mortgaged, the rent should be 0
                if (IS_SCALED) { //If its scaled, the rent condition is just the number of properties the player owns
                    rent = RENTS[numPropertiesPlayerOwnsInGroup - 1]; //This will never be -1 because if this code is called then this Property must be owned, so propertiesPlayerOwnsInGroup will be at least 1
                } else {
                    if (!playerHasMonopoly) {
                        if (numHouses == 0) {
                            rent = RENTS[0];
                        } else {
                            throw new IllegalStateException("A Property that the player does not have a Monopoly " +
                                    "over has houses");
                        }
                    } else {
                        rent = RENTS[numHouses + 1];
                    }
                }
            } else {
                rent = 0;
            }
        } else if (!validateProperty()) {
            throw new IllegalStateException("This Property is an illegal state");
        } else {
            throw new IllegalArgumentException("An invalid parameter was passed");
        }
    }

    /**
     * Gets whether or not this Property is scaled, meaning the values in RENTS are based on the number of Properties
     *
     * @return whether or not this Property is scaled
     */
    public boolean IS_SCALED() {
        return IS_SCALED;
    }

    /**
     * Gets the number of houses on this Property
     *
     * @return the number of houses on this Property
     */
    public int getNumHouses() {
        return numHouses;
    }

    /**
     * Gets the maximum number of houses that can be built on this Property
     *
     * @return the maximum number of houses that can be built on this Property
     */
    public int getMAX_HOUSES() {
        return MAX_HOUSES;
    }

    /**
     * Determines whether or not this Property can be sold
     *
     * @return whether or not this Property can be sold
     */
    public boolean canSell() {
        return numHouses == 0 && !isMortgaged && validateProperty();
    }

    /**
     * Gets whether or not this Property is mortgaged
     *
     * @return whether or not this Property is mortgaged
     */
    public boolean isMortgaged() {
        return isMortgaged;
    }

    /**
     * Validates a Property, meaning it isn't mortgaged and unowned, has a valid number of houses, and isn't mortgaged or unowned with houses
     *
     * @return whether or not this Property is valid
     */
    private boolean validateProperty() {
        return !(isMortgaged && owner == null) && (numHouses >= 0 && numHouses <= MAX_HOUSES) &&
                !((isMortgaged || owner == null) && numHouses > 0);
    }

    /**
     * Determines if there is room for more houses to be built on this Property. This does not deal with the legality of building
     *
     * @return if there is room for more houses on this Property
     */
    public boolean canBuild() {
        return numHouses < MAX_HOUSES;
    }

    /**
     * Gets the rent the Player should pay for landing on this Space
     *
     * @return the rent the Player should pay for landing on this Space
     */
    public int getRent() {
        return rent;
    }

    /**
     * Gets the mortgage cost of this Property
     *
     * @return the mortgage cost of this Property
     */
    public int getMORTGAGE() {
        return MORTGAGE;
    }

    /**
     * Gets the price of un-mortgaging this Property
     *
     * @return the price of un-mortgaging this Property
     */
    public int getUnMORTGAGE() {
        return (int) Math.round(MORTGAGE * MORTGAGE_PERCENT);
    }

    /**
     * Gets the cost of building on this Property
     *
     * @return the cost of building on this Property
     */
    public int getBUILD_PRICE() {
        return BUILD_PRICE;
    }

    /**
     * Builds a house/hotel on this Property
     *
     * @return the value owed by the Player who owns this Property
     * @throws IllegalStateException when the Property is in a state that prevents houses from being bought
     */
    public int buyHouse() {
        if (canBuild() && validateProperty() && owner != null && owner.canAfford(BUILD_PRICE)) {
            numHouses++;
            return -BUILD_PRICE;
        } else {
            throw new IllegalStateException("Property is in an illegal state for construction");
        }
    }

    /**
     * Sells a house/hotel from this Property
     *
     * @return the value owed to the Player who owns this Property
     * @throws IllegalStateException when the Property is in a state that prevents houses from being sold
     */
    public int sellHouse() {
        if (numHouses > 0 && validateProperty()) {
            numHouses--;
            return BUILD_PRICE / 2;
        } else {
            throw new IllegalStateException("Property is in an illegal state for construction removal");
        }
    }

    /**
     * Mortgages the Property
     *
     * @return the value owed to the Player who owns this Property
     * @throws IllegalStateException when the Property is in a state that prevents it from being mortgaged
     */
    public int mortgage() {
        if (canSell() && validateProperty() && owner != null) {
            isMortgaged = true;
            return MORTGAGE;
        } else {
            throw new IllegalStateException("Property is in an un-mortgageable state");
        }
    }

    /**
     * Transfers a mortgaged Property to a new owner. This bypasses the standard requirements that prevent a mortgaged
     * Property from being transferred. The new Player should've already been charged for interest, if applicable
     *
     * @param player the new owner of the Property. This can be null
     * @return the value that the new owner owes, if applicable
     * @throws IllegalStateException when the owner isn't bankrupt or the Property is in an illegal state
     */
    public int bankruptTransfer(Player player) {
        if (owner.getWallet() < 0 && validateProperty() && isMortgaged) {
            if (player == null) {
                isMortgaged = false;
                return 0;
            }
            owner = player;
            return (int) Math.round((MORTGAGE * MORTGAGE_PERCENT));
        } else {
            throw new IllegalStateException("Owner isn't bankrupt or the Property is in an illegal state");
        }
    }

    /**
     * Un-mortgages the Property
     *
     * @return the value owed by the Player who owns this Property
     * @throws IllegalStateException when the Property is in a state that prevents it form being un-mortgaged
     */
    public int unMortgage() {
        if (isMortgaged && validateProperty()) {
            isMortgaged = false;
            return (int) Math.round((-MORTGAGE * (1 + MORTGAGE_PERCENT)));
        } else {
            throw new IllegalStateException("Property is in an un-un-mortgageable state");
        }
    }

    /**
     * Gets this Properties name
     *
     * @return this Properties name
     */
    @Override
    public String toString() {
        return NAME;
    }
}
