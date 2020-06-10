/**
 * This class stores the default (standard monopoly) values for the literal constants used in Game
 *
 * @author irswr
 */
public class Defaults {
    private static final String[] SPACE_NAMES = {"GO", "MEDITERRANEAN AVENUE", "COMMUNITY CHEST", "BALTIC AVENUE",
            "INCOME TAX", "READING RAILROAD", "ORIENTAL AVENUE", "CHANCE", "VERMONT AVENUE", "CONNECTICUT AVENUE",
            "JAIL", "ST. CHARLES PLACE", "ELECTRIC COMPANY", "STATES AVENUE", "VIRGINIA AVENUE", "PENNSYLVANIA RAILROAD",
            "ST. JAMES PLACE", "COMMUNITY CHEST", "TENNESSEE AVENUE", "NEW YORK AVENUE", "FREE PARKING", "KENTUCKY AVENUE",
            "CHANCE", "INDIANA AVENUE", "ILLINOIS AVENUE", "B. & O. RAILROAD", "ATLANTIC AVENUE", "VENTNOR AVENUE",
            "WATER WORKS", "MARVIN GARDENS", "GO TO JAIL", "PACIFIC AVENUE", "NORTH CAROLINA AVENUE", "COMMUNITY CHEST",
            "PENNSYLVANIA AVENUE", "SHORT LINE", "CHANCE", "PARK PLACE", "LUXURY TAX", "BOARDWALK"};

    private static final int[] SPACE_MONEY_LOSSES = {0, 0, 0, 0, -200, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
            0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, -100, 0};

    private static final int[] SPACE_MOVEMENT_LOSSES = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
            0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};

    private static final int[] SPACE_SPACE_LOSSES = {-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
            -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 10, -1, -1, -1, -1, -1, -1, -1, -1, -1};

    private static final String[] SPACE_COLOR_GROUPS = {null, null, null, null, null, null, null, null, null, null,
            null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null,
            null, null, null, null, null, null, null, null, null, null, null, null};

    private static final double[] SPACE_RENT_MULTIPLIERS = {1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0,
            1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0,
            1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0};

    private static final double[] SPACE_ROLL_MULTIPLIERS = {0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0,
            0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0,
            0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0};

    private static final int[] SPACE_DECK_USED = {-1, -1, 0, -1, -1, -1, -1, 1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 0,
            -1, -1, -1, -1, 1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 0, -1, -1, 1, -1, -1, -1};

    private static final int[] SPACE_PER_HOUSE = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
            0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};

    private static final int[] SPACE_PER_HOTEL = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
            0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};

    private static final boolean[] SPACE_HAVE_PROPERTY = {false, true, false, true, false, true, true, false, true, true,
            false, true, true, true, true, true, true, false, true, true, false, true, false, true, true, true, true,
            true, true, true, false, true, true, false, true, true, false, true, false, true};

    private static final int[] PROPERTY_PRICES = {-1, 60, -1, 60, -1, 200, 100, -1, 100, 120, -1, 140, 150, 140, 160,
            200, 180, -1, 180, 200, -1, 220, -1, 220, 240, 200, 260, 260, 150, 280, -1, 300, 300, -1, 320, 200, -1, 350,
            -1, 400};

    private static final int[] PROPERTY_MORTGAGES = {-1, 30, -1, 30, -1, 100, 50, -1, 50, 60, -1, 70, 75, 70, 80, 100,
            90, -1, 90, 100, -1, 100, -1, 110, 120, 100, 130, 130, 75, 140, -1, 150, 150, -1, 160, 100, -1, 175, -1,
            200};

    private static final double[] PROPERTY_MORTGAGE_PERCENT = {-1, .1, -1, .1, -1, .1, .1, -1, .1, .1, -1, .1, .1, .1,
            .1, .1, .1, -1, .1, .1, -1, .1, -1, .1, .1, .1, .1, .1, .1, .1, -1, .1, .1, -1, .1, .1, -1, .1, -1, .1};

    private static final int[] PROPERTY_BUILD_PRICES = {-1, 50, -1, 50, -1, 0, 50, -1, 50, 50, -1, 100, 0, 100, 100, 0,
            100, -1, 100, 100, -1, 150, -1, 150, 150, 0, 150, 150, 0, 150, -1, 200, 200, -1, 200, 0, -1, 200, -1, 200};

    private static final String[] PROPERTY_COLOR_GROUPS = {null, "BROWN", null, "BROWN", null, "RAILROAD", "LIGHT BLUE",
            null, "LIGHT BLUE", "LIGHT BLUE", null, "PINK", "UTILITY", "PINK", "PINK", "RAILROAD", "ORANGE", null,
            "ORANGE", "ORANGE", null, "RED", null, "RED", "RED", "RAILROAD", "YELLOW", "YELLOW", "UTILITY", "YELLOW",
            null, "GREEN", "GREEN", null, "GREEN", "RAILROAD", null, "DARK BLUE", null, "DARK BLUE"};

    private static final int[] PROPERTY_MAX_HOUSES = {-1, 5, -1, 5, -1, 0, 5, -1, 5, 5, -1, 5, 0, 5, 5, 0, 5, -1, 5, 5,
            -1, 5, -1, 5, 5, 0, 5, 5, 0, 5, -1, 5, 5, -1, 5, 0, -1, 5, -1, 5};

    private static final int[][] PROPERTY_RENTS = {null, {2, 4, 10, 30, 90, 160, 250}, null, {4, 8, 20, 60, 180, 320, 450},
            null, {25, 50, 100, 200}, {6, 12, 30, 90, 270, 400, 550},
            null, {6, 12, 30, 90, 270, 400, 550}, {8, 16, 40, 100, 300, 450, 600}, null, {10, 20, 50, 150, 450, 625, 750},
            {4, 8}, {10, 20, 50, 150, 450, 625, 750}, {12, 24, 60, 180, 500, 700, 900}, {25, 50, 100, 200},
            {14, 28, 70, 200, 550, 750, 950}, null,
            {14, 28, 70, 200, 550, 750, 950}, {16, 32, 80, 220, 600, 800, 1000}, null, {18, 36, 90, 250, 700, 875, 1050},
            null, {18, 36, 90, 250, 700, 875, 1050}, {20, 40, 100, 300, 750, 925, 1100}, {25, 50, 100, 200},
            {22, 44, 110, 330, 880, 975, 1150}, {22, 44, 110, 330, 880, 975, 1150}, {4, 8},
            {24, 48, 120, 360, 850, 1025, 1200},
            null, {26, 52, 130, 390, 900, 1100, 1275}, {26, 52, 130, 390, 900, 1100, 1275}, null,
            {28, 56, 150, 450, 1000, 1200, 1400}, {25, 50, 100, 200}, null, {35, 70, 175, 500, 1100, 1300, 1500}, null,
            {50, 100, 200, 600, 1400, 1700, 2000}};

    private static final int[] PROPERTY_STARTING_HOUSES = {-1, 0, -1, 0, -1, 0, 0, -1, 0, 0, -1, 0, 0, 0, 0, 0, 0, -1,
            0, 0, -1, 0, -1, 0, 0, 0, 0, 0, 0, 0, -1, 0, 0, -1, 0, 0, -1, 0, -1, 0};

    private static final boolean[] PROPERTY_ARE_DICE_MULTIPLIER = {false, false, false, false, false, false, false,
            false, false, false, false, false, true, false, false, false, false, false, false, false, false, false,
            false, false, false, false, false, false, true, false, false, false, false, false, false, false, false,
            false, false, false};

    private static final boolean[] PROPERTY_ARE_SCALED = {false, false, false, false, false, true, false, false, false,
            false, false, false, true, false, false, true, false, false, false, false, false, false, false, false, false,
            true, false, false, true, false, false, false, false, false, false, true, false, false, false, false};

    private static final boolean[] PROPERTY_ARE_MORTGAGED = {false, false, false, false, false, false, false,
            false, false, false, false, false, false, false, false, false, false, false, false, false, false, false,
            false, false, false, false, false, false, false, false, false, false, false, false, false, false, false,
            false, false, false};

    private static final Player[] PROPERTY_OWNERS = {null, null, null, null, null, null, null, null, null, null,
            null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null,
            null, null, null, null, null, null, null, null, null, null, null, null};

    private static final String[] COLOR_GROUPS = {"BROWN", "LIGHT BLUE", "PINK", "ORANGE", "RED", "YELLOW", "GREEN",
            "DARK BLUE", "RAILROAD", "UTILITY"};

    private static final int JAIL_POSITION = 10;

    private static final int JAIL_BAIL = 50;

    private static final String[] PROMPTS = {"Would you like to use a get out of jail free card",
            "Would you like to trade for a get out of jail free card", "Would you like to pay your bail",
            "Would you like to participate in an auction for ", "Would you like to buy ",
            "Would you like to sell houses or hotels on ", "Would you like to mortgage ",
            "Would you like to un-mortgage ", "Would you like to build on ", "Should the sender offer any properties ",
            "Should the sender remove any properties ", "Should the sender offer any cards ",
            "Should the sender remove any cards", "Should the sender offer more money ",
            "Should the sender offer less money ", "Should the receiver offer any more properties ",
            "Should the receiver offer any less properties ", "Should the receiver offer any more cards ",
            "Should the receiver offer any less cards ", "Should the receiver offer any more money ",
            "Should receiver offer any less money ", "Is this trade fair ", "Would you like to trade ",
            "You are bankrupt! You can sell houses on any of these properties ",
            "You are bankrupt! You can mortgage any of these properties ",
            "Would you like to pay to un-mortgage this property that you are inheriting ",
            "How much do you want to bid for "};

    private static final int NUM_DICE = 2;

    private static final int DICE_SIDES = 6;

    private static final int PLAYER_STARTING_WALLET = 1500;

    private static final int PLAYER_STARTING_POSITION = 0;

    private static final int PLAYER_TURNS_IN_JAIL = 0;

    private static final int PLAYER_SALARY = 200;

    private static final int PLAYER_JAIL_TIME = 3;

    private static final String[][] CARD_TYPES = {{"Community Chest", "Community Chest", "Community Chest",
            "Community Chest", "Community Chest", "Community Chest", "Community Chest", "Community Chest",
            "Community Chest", "Community Chest", "Community Chest", "Community Chest", "Community Chest",
            "Community Chest", "Community Chest", "Community Chest"}, {"Chance", "Chance", "Chance", "Chance", "Chance",
            "Chance", "Chance", "Chance", "Chance", "Chance", "Chance", "Chance", "Chance", "Chance", "Chance", "Chance"}};

    private static final String[][] CARD_DESCRIPTIONS = {{"Grand Opera Night. Collect $50 from every player, for opening night seats",
            "Income tax refund. Collect $20", "Holiday fund matures. Receive $100",
            "Get out of jail free (This card can be traded or sold)",
            "Pay hospital fees of $100", "You have won second prize in a beauty contest. Collect $10",
            "From sale of stock. You get $50", "Pay school fees. Tax of $150",
            "Bank error in your favor. Collect $200", "Advance to \"GO\". Collect $200",
            "Go to jail. - Go Directly to jail - Do not pass go, do not collect $200",
            "Life insurance matures, Collect $100",
            "You are assessed for street repairs. For each house pay - $50. For each hotel pay - $150",
            "Receive $25 consultancy fee", "Doctor's fees. Pay $50", "You inherit $100"},
            {"Advance to St. Charles Place. If you pass \"Go\" collect $200",
                    "Advance token to Boardwalk", "Your building loan matures. Collect $150",
                    "You are assessed for street repairs. For each house pay - $50. For each hotel pay - $100",
                    "Advance token to the nearest railroad and pay owner twice the rental to which he/she is otherwise entitled. If railroad is unowned, you may buy it from the bank",
                    "Go back three spaces", "Get out of jail free",
                    "Advance token to the nearest railroad and pay owner twice the rental to which he/she is otherwise entitled. If railroad is unowned, you may buy it from the bank",
                    "Advance token to the nearest utility. If unowned, you may buy it from the bank. If owned, throw dice and pay owner a total of ten times the amount thrown",
                    "Advance to Illinois Avenue. If you pass \"GO\" collect $200",
                    "You have been elected chairman of the board. Pay each player $50",
                    "Go to jail. - Go directly to jail - Do not pass go, do not collect $200", "Bank pays you dividend of $50",
                    "Take a trip to reading railroad. If you pass go, collect $200", "Pay a poor tax of $15",
                    "Advance to \"GO\". Collect $200"}};

    private static final int[][] CARD_MONEY_LOSSES = {{-50, 20, 10, 0, -100, 10, 50, -150, 200, 0, 0, 100, 0, 25, -50,
            100}, {0, 0, 150, 0, 0, 0, 0, 0, 0, 0, 50, 0, 0, 0, -15, 0}};

    private static final boolean[][] CARD_PER_PLAYER = {{true, false, false, false, false, false, false, false, false,
            false, false, false, false, false, false, false}, {false, false, false, false, false, false, false, false,
            false, false, true, false, false, false, false, false}};

    private static final int[][] CARD_MOVEMENT_LOSSES = {{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},{0, 0, 0, 0, 0,
            -3, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}};

    private static final int[][] CARD_SPACE_LOSSES = {{-1, -1, -1, -1, -1, -1, -1, -1, -1, 0, 10, -1, -1, -1, -1, -1},
            {11, 39, -1, -1, -1, -1, -1, -1, -1, 24, -1, 10, -1, 5, -1, 0}};

    private static final String[][] CARD_COLOR_GROUPS = {{null, null, null, null, null, null, null, null, null, null,
            null, null, null, null, null, null}, {null, null, null, null, "RAILROAD", null, null, "RAILROAD", "UTILITY",
            null, null, null, null, null, null, null}};

    private static final double[][] CARD_RENT_MULTIPLIERS = {{1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0,
            1.0, 1.0, 1.0, 1.0, 1.0}, {1.0, 1.0, 1.0, 1.0, 2.0, 1.0, 1.0, 2.0, 0.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0}};

    private static final double[][] CARD_ROLL_MULTIPLIERS = {{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0,
            0.0, 0.0, 0.0, 0.0, 0.0}, {0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 10.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0}};

    private static final int[][] CARD_PER_HOUSE = {{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 50, 0, 0, 0}, {0, 0, 0, 50, 0,
            0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}};

    private static final int[][] CARD_PER_HOTEL = {{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 150, 0, 0, 0}, {0, 0, 0, 100, 0,
            0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}};

    private static final boolean[][] CARD_IS_GET_OUT_JAIL = {{false, false, false, true, false, false, false, false,
            false, false, false, false, false, false, false, false}, {false, false, false, false, false, false, true,
            false, false, false, false, false, false, false, false, false}};

    private static final Player[][] CARD_OWNERS = {{null, null, null, null, null, null, null, null, null, null,
            null, null, null, null, null, null}, {null, null, null, null, null, null, null, null, null, null, null,
            null, null, null, null, null}};

    /**
     * Gets the Default Space Names
     *
     * @return the Default Space Names
     */
    public static String[] getSpaceNames() {
        return SPACE_NAMES;
    }

    /**
     * Gets the Default money penalty for each Space
     *
     * @return the Default money penalty for each Space
     */
    public static int[] getSpaceMoneyLosses() {
        return SPACE_MONEY_LOSSES;
    }

    /**
     * Gets the Default movement penalty for each Space
     *
     * @return the Default movement penalty for each Space
     */
    public static int[] getSpaceMovementLosses() {
        return SPACE_MOVEMENT_LOSSES;
    }

    /**
     * Gets the Default Space penalty for each Space
     *
     * @return the Default Space penalty for each Space
     */
    public static int[] getSpaceSpaceLosses() {
        return SPACE_SPACE_LOSSES;
    }

    /**
     * Gets the Default color group penalty for each Space
     *
     * @return the Default color group penalty for each Space
     */
    public static String[] getSpaceColorGroups() {
        return SPACE_COLOR_GROUPS;
    }

    /**
     * Gets the Default rent multiplier for each Space
     *
     * @return the Default rent multiplier for each Space
     */
    public static double[] getSpaceRentMultipliers() {
        return SPACE_RENT_MULTIPLIERS;
    }

    /**
     * Gets the Default roll multiplier for each Space
     *
     * @return the Default roll multiplier for each Space
     */
    public static double[] getSpaceRollMultipliers() {
        return SPACE_ROLL_MULTIPLIERS;
    }

    /**
     * Gets the Default Deck for each Space
     *
     * @return the Default Deck for each Space
     */
    public static int[] getSpaceDeckUsed() {
        return SPACE_DECK_USED;
    }

    /**
     * Gets the Default cost per house for each Space
     *
     * @return the Default cost per house for each Space
     */
    public static int[] getSpacePerHouse() {
        return SPACE_PER_HOUSE;
    }

    /**
     * Gets the Default cost per hotel for each Space
     *
     * @return the Default cost per hotel for each Space
     */
    public static int[] getSpacePerHotel() {
        return SPACE_PER_HOTEL;
    }

    /**
     * Gets whether each Space has a Property
     *
     * @return whether each Space has a Property
     */
    public static boolean[] getSpaceHaveProperty() {
        return SPACE_HAVE_PROPERTY;
    }

    /**
     * Gets each Properties price
     *
     * @return each Properties price
     */
    public static int[] getPropertyPrices() {
        return PROPERTY_PRICES;
    }

    /**
     * Gets each Properties mortgage cost
     *
     * @return each Properties mortgage cost
     */
    public static int[] getPropertyMortgages() {
        return PROPERTY_MORTGAGES;
    }

    /**
     * Gets the percent of each Properties mortgage that should be charged as interest
     *
     * @return the percent of each Properties cost that should be charged as interest
     */
    public static double[] getPropertyMortgagePercent() {
        return PROPERTY_MORTGAGE_PERCENT;
    }

    /**
     * Gets the cost of building on each Property
     *
     * @return the cost of building on each Property
     */
    public static int[] getPropertyBuildPrices() {
        return PROPERTY_BUILD_PRICES;
    }

    /**
     * Gets each Properties color group
     *
     * @return each Properties color group
     */
    public static String[] getPropertyColorGroups() {
        return PROPERTY_COLOR_GROUPS;
    }

    /**
     * Gets the maximum number of houses that can be built on each Property
     *
     * @return the maximum number of houses that can be built on each Property
     */
    public static int[] getPropertyMaxHouses() {
        return PROPERTY_MAX_HOUSES;
    }

    /**
     * The rents for each Property
     *
     * @return the rents for each Property
     */
    public static int[][] getPropertyRents() {
        return PROPERTY_RENTS;
    }

    /**
     * Gets the Default number of houses for each Property
     *
     * @return the Default number of houses for each Property
     */
    public static int[] getPropertyStartingHouses() {
        return PROPERTY_STARTING_HOUSES;
    }

    /**
     * Gets whether each Property is a dice multiplier
     *
     * @return whether each Property is a dice multiplier
     */
    public static boolean[] getPropertyAreDiceMultiplier() {
        return PROPERTY_ARE_DICE_MULTIPLIER;
    }

    /**
     * Gets whether each Property is scaled
     *
     * @return whether each Property is scaled
     */
    public static boolean[] getPropertyAreScaled() {
        return PROPERTY_ARE_SCALED;
    }

    /**
     * Gets whether each Property is mortgaged
     *
     * @return whether each Property is mortgaged
     */
    public static boolean[] getPropertyAreMortgaged() {
        return PROPERTY_ARE_MORTGAGED;
    }

    /**
     * Gets each Properties owner
     *
     * @return each Properties owner
     */
    public static Player[] getPropertyOwners() {
        return PROPERTY_OWNERS;
    }

    /**
     * Gets the Default color groups
     *
     * @return the Default color groups
     */
    public static String[] getColorGroups() {
        return COLOR_GROUPS;
    }

    /**
     * Gets the Default position of the jail
     *
     * @return the default position of the jail
     */
    public static int getJailPosition() {
        return JAIL_POSITION;
    }

    /**
     * Gets the Default cost for bail
     *
     * @return the Default cost for bail
     */
    public static int getJailBail() {
        return JAIL_BAIL;
    }

    /**
     * Gets the Default prompts
     *
     * @return the Default prompts
     */
    public static String[] getPROMPTS() {
        return PROMPTS;
    }

    /**
     * Gets the Default number of Dice
     *
     * @return the Default number of Dice
     */
    public static int getNumDice() {
        return NUM_DICE;
    }

    /**
     * Gets the Default number of sides on each Dice
     *
     * @return the Default number of sides on each Dice
     */
    public static int getDiceSides() {
        return DICE_SIDES;
    }

    /**
     * Gets the starting wallet each Player should have
     *
     * @return the starting wallet each Player should have
     */
    public static int getPlayerStartingWallet() {
        return PLAYER_STARTING_WALLET;
    }

    /**
     * Gets the position each Player should start in
     *
     * @return the position each Player should start in
     */
    public static int getPlayerStartingPosition() {
        return PLAYER_STARTING_POSITION;
    }

    /**
     * Gets the number of turns each Player has in jail
     *
     * @return the number of turns each Player has in jail
     */
    public static int getPlayerTurnsInJail() {
        return PLAYER_TURNS_IN_JAIL;
    }

    /**
     * Gets the Default salary for each Player
     *
     * @return the Default salary for each Player
     */
    public static int getPlayerSalary() {
        return PLAYER_SALARY;
    }

    /**
     * Gets the length of the Players sentence
     *
     * @return the length of the Players sentence
     */
    public static int getPlayerJailTime() {
        return PLAYER_JAIL_TIME;
    }

    /**
     * Gets the type of each Card
     *
     * @return the type of each Card
     */
    public static String[][] getCardTypes() {
        return CARD_TYPES;
    }

    /**
     * Gets the description of each Card
     *
     * @return the description of each Card
     */
    public static String[][] getCardDescriptions() {
        return CARD_DESCRIPTIONS;
    }

    /**
     * Gets the monetary penalty for each Card
     *
     * @return the monetary penalty for each Card
     */
    public static int[][] getCardMoneyLosses() {
        return CARD_MONEY_LOSSES;
    }

    /**
     * Gets whether the monetary penalty for each Card applies to each Player
     *
     * @return whether the monetary penalty fore ach Card applies to each Player
     */
    public static boolean[][] getCardPerPlayer() {
        return CARD_PER_PLAYER;
    }

    /**
     * Gets the movement penalty for each Card
     *
     * @return the movement penalty for each Card
     */
    public static int[][] getCardMovementLosses() {
        return CARD_MOVEMENT_LOSSES;
    }

    /**
     * Gets the Space penalty for each Card
     *
     * @return the Space penalty for each Card
     */
    public static int[][] getCardSpaceLosses() {
        return CARD_SPACE_LOSSES;
    }

    /**
     * Gets the color group penalty of each Card
     *
     * @return the color group penalty of each Card
     */
    public static String[][] getCardColorGroups() {
        return CARD_COLOR_GROUPS;
    }

    /**
     * Gets the rent multiplier of each card
     *
     * @return the rent multiplier of each Card
     */
    public static double[][] getCardRentMultipliers() {
        return CARD_RENT_MULTIPLIERS;
    }

    /**
     * Gets the roll multiplier of each Card
     *
     * @return the roll multiplier of each Card
     */
    public static double[][] getCardRollMultipliers() {
        return CARD_ROLL_MULTIPLIERS;
    }

    /**
     * Gets the amount the Player should pay per house
     *
     * @return the amount the Player should pay per house
     */
    public static int[][] getCardPerHouse() {
        return CARD_PER_HOUSE;
    }

    /**
     * Gets the amount the Player should pay per hotel
     *
     * @return the amount the Player should pay per hotel
     */
    public static int[][] getCardPerHotel() {
        return CARD_PER_HOTEL;
    }

    /**
     * Gets whether each Card is a get out of jail free Card
     *
     * @return whether each Card is a get out of jail free Card
     */
    public static boolean[][] getCardIsGetOutJail() {
        return CARD_IS_GET_OUT_JAIL;
    }

    /**
     * Gets each Card's owner
     *
     * @return each Card's owner
     */
    public static Player[][] getCardOwners() {
        return CARD_OWNERS;
    }
}
