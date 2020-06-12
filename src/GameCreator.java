import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * This Class manages Game setup. It contains many static methods that manage the Game Object initialization, along with
 * a UI component
 *
 * @author irswr
 */
public class GameCreator extends JPanel implements ActionListener, ListSelectionListener, DocumentListener {
    //GameCreator constants
    private final MainUI MAIN_UI; //Contains the MainUI Object that created this
    private final JList<String> PLAYER_LIST; //Contains the list of all of the Player's names
    private final DefaultListModel<String> PLAYER_LIST_MODEL; //Contains the model that stores the Player's names
    private final JButton[] BUTTONS; //Contains the buttons that manage Player creation
    private final JTextField NAME_FIELD; //Contains the text field that allows the user to input their Players name
    private final ArrayList<JComboBox<String>> TYPE_BOXES; //Stores the combo boxes that allow the user to choose their Players type
    private final JPanel TYPE_PANEL; //Contains the combo boxes
    private final ArrayList<Color> COLORS; //Stores the Players Colors
    private final ArrayList<JButton> COLOR_BUTTONS; //Stores the buttons that allow the user to choose their Players Color
    private final JPanel COLOR_PANEL; //Contains the color buttons

    /**
     * Initializes the creation of a default game
     * @param mainUI the MainUI that created this
     * @throws IllegalArgumentException when a null MainUI is passed
     */
    public GameCreator(MainUI mainUI) {
        if (mainUI != null) {
            this.MAIN_UI = mainUI;
            setLayout(new BorderLayout());

            JLabel label = new JLabel("Add your players");
            add(label, BorderLayout.NORTH);

            JPanel centerPanel = new JPanel();

            JPanel listPanel = new JPanel();
            listPanel.setLayout(new BoxLayout(listPanel, BoxLayout.Y_AXIS));
            listPanel.add(new JLabel("Players:"));
            PLAYER_LIST_MODEL = new DefaultListModel<>();
            PLAYER_LIST = new JList<>(PLAYER_LIST_MODEL);
            PLAYER_LIST.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            PLAYER_LIST.setVisibleRowCount(-1);
            PLAYER_LIST.addListSelectionListener(this);
            listPanel.add(PLAYER_LIST);
            centerPanel.add(listPanel);

            TYPE_BOXES = new ArrayList<>();
            TYPE_PANEL = new JPanel();
            TYPE_PANEL.setLayout(new BoxLayout(TYPE_PANEL, BoxLayout.Y_AXIS));
            TYPE_PANEL.add(new JLabel("Types:"));
            centerPanel.add(TYPE_PANEL);

            COLORS = new ArrayList<>();
            COLOR_BUTTONS = new ArrayList<>();
            COLOR_PANEL = new JPanel();
            COLOR_PANEL.setLayout(new BoxLayout(COLOR_PANEL, BoxLayout.Y_AXIS));
            COLOR_PANEL.add(new JLabel("Colors:"));
            centerPanel.add(COLOR_PANEL);

            add(centerPanel, BorderLayout.CENTER);

            JPanel controlPanel = new JPanel();

            BUTTONS = new JButton[3];
            BUTTONS[0] = new JButton("Add");
            BUTTONS[0].addActionListener(this);
            BUTTONS[0].setEnabled(false);
            BUTTONS[1] = new JButton("Remove");
            BUTTONS[1].addActionListener(this);
            BUTTONS[1].setEnabled(false);
            BUTTONS[2] = new JButton("Continue");
            BUTTONS[2].addActionListener(this);
            BUTTONS[2].setEnabled(false);

            NAME_FIELD = new JTextField(20);
            NAME_FIELD.setEditable(true);
            NAME_FIELD.getDocument().addDocumentListener(this);

            controlPanel.add(BUTTONS[1]);
            controlPanel.add(NAME_FIELD);
            controlPanel.add(BUTTONS[0]);
            controlPanel.add(BUTTONS[2]);
            add(controlPanel, BorderLayout.SOUTH);
        } else {
            throw new IllegalArgumentException("A null parameter was passed");
        }
    }

    /**
     * Makes a Game out of the default values
     *
     * @param playerNames  the names of the Players
     * @param playerTypes  the types of the Players
     * @param playerColors the Players colors
     * @param gameUI       the Game's UI
     */
    public static Game makeGame(String[] playerNames, String[] playerTypes, Color[] playerColors, GameUI gameUI) {
        int[] playerWallets = new int[playerNames.length];
        Arrays.fill(playerWallets, Defaults.getPlayerStartingWallet());
        int[] playerPositions = new int[playerNames.length];
        Arrays.fill(playerPositions, Defaults.getPlayerStartingPosition());
        int[] playerSentences = new int[playerNames.length];
        Arrays.fill(playerSentences, Defaults.getPlayerTurnsInJail());
        int[] playerSalaries = new int[playerNames.length];
        Arrays.fill(playerSalaries, Defaults.getPlayerSalary());
        int[] playerJailTurns = new int[playerNames.length];
        Arrays.fill(playerSalaries, Defaults.getPlayerJailTime());
        return makeGame(Defaults.getSpaceNames(), Defaults.getSpaceMoneyLosses(), Defaults.getSpaceMovementLosses(),
                Defaults.getSpaceSpaceLosses(), Defaults.getSpaceColorGroups(), Defaults.getSpaceRentMultipliers(),
                Defaults.getSpaceRollMultipliers(), Defaults.getSpaceDeckUsed(), Defaults.getSpacePerHouse(),
                Defaults.getSpacePerHotel(), Defaults.getSpaceHaveProperty(), Defaults.getPropertyPrices(),
                Defaults.getPropertyMortgages(), Defaults.getPropertyMortgagePercent(), Defaults.getPropertyBuildPrices(),
                Defaults.getPropertyColorGroups(), Defaults.getPropertyMaxHouses(), Defaults.getPropertyRents(),
                Defaults.getPropertyStartingHouses(), Defaults.getPropertyAreDiceMultiplier(),
                Defaults.getPropertyAreScaled(), Defaults.getPropertyAreMortgaged(), Defaults.getPropertyOwners(),
                Defaults.getColorGroups(), Defaults.getJailPosition(), Defaults.getJailBail(), gameUI,
                Defaults.getPROMPTS(), playerNames, playerTypes, playerWallets, playerPositions, playerSentences,
                playerSalaries, playerJailTurns, playerColors, Defaults.getNumDice(), Defaults.getDiceSides(),
                Defaults.getCardTypes(), Defaults.getCardDescriptions(), Defaults.getCardMoneyLosses(),
                Defaults.getCardPerPlayer(), Defaults.getCardMovementLosses(), Defaults.getCardSpaceLosses(),
                Defaults.getCardColorGroups(), Defaults.getCardRentMultipliers(), Defaults.getCardRollMultipliers(),
                Defaults.getCardPerHouse(), Defaults.getCardPerHotel(), Defaults.getCardIsGetOutJail(),
                Defaults.getCardOwners());
    }

    /**
     * Sets up a Game Board. Validates according to the conditions outlined here. All of these Arrays should have
     * the same length
     *
     * @param spaceNames                     the Space's name. This shouldn't be null
     * @param spaceMoneyLosses               the monetary penalty for landing on these Spaces. Should be 0 for no penalty
     * @param spaceMovementLosses            the movement penalty for landing on these Spaces. Should be 0 for no movement
     * @param spaceSpaceLosses               the index of the Space that the Player should go to for landing on these Spaces. Shouldn't be
     *                                  less than -1, which is if they shouldn't move
     * @param colorGroups               the color group that the player should go to as a penalty for landing on this space. Can be
     *                                  null for none
     * @param spaceRentMultipliers           the multiplier of the rent that should be paid
     * @param spaceRollMultipliers           the amount that should be multiplied by a new roll as payment for rent instead of normal
     * @param spacePerHouses                 the amount the Player should pay per house. Shouldn't be less than 0
     * @param spacePerHotels                 the amount the Player should pay per hotel. Shouldn't be less than 0
     * @param spaceDeckUsed                  the index of the Deck that this Space should use. Shouldn't be less than -1, which represents no
     *                                  Deck
     * @param spaceHaveProperty              whether or not these Space's have a Property. Should be false if not
     * @param propertyPrices            the price of this Property. This should be greater than 0
     * @param propertyMortgages         the mortgage of these Property. This should be greater than 0 and should be less then the price
     * @param propertyMortgagePercent   the percent of mortgage that should be paid as interest
     * @param propertyBuildPrices       the price of building on these Property's. This should be greater than 0
     * @param propertyColorGroups       the color group of these Property's. This shouldn't be null
     * @param propertyMaxHouses         the maximum number of houses on these Property's. This shouldn't be negative
     * @param propertyRents             the rents for these Property's at given states of ownership. This shouldn't be null
     * @param propertyStartingHouses    the number of houses these Property's should start with. This shouldn't be negative
     * @param propertyAreDiceMultipliers whether the values in propertyRents are dice roll multipliers
     * @param propertyAreScaled         whether the values in Rents are based on number of Properties exclusively
     * @param propertyAreMortgaged      whether or not these Properties are mortgaged
     * @param propertyOwners            the Player who should own these property's. This can be null
     * @param playerNames          the names of the Players to be created. This shouldn't be null
     * @param playerTypes          the types of the Players to be created (human or AI). This shouldn't be null or anything else
     * @param playerWallets        the amount of money that should be placed in the Player's wallet
     * @param playerPositions      the position that the Player should start at
     * @param playerJailTurns      the number of turns the Player has in jail. Should be 0 for none
     * @param jailPosition   the position of the jail on the board. This must be at a valid position
     * @param playerSalaries       the salaries the Players should be awarded for passing go
     * @param playerColors the Players colors
     * @param numTurnsInJail the number of turns the Player should send in jail when they are sent there
     * @param prompts        the prompts that are used during the Game. This is only used for the AIPlayer Class
     * @param gameUI         the UI for the Game
     * @param bailCost the cost to get out of jail
     * @param numDice the number of dice. This should be greater than 0
     * @param diceSides   the number of sides on the dice. This should be greater than 0
     * @param cardTypes          the types of Cards to be created. This shouldn't be null
     * @param cardDescriptions   the descriptions of the Cards to be created. This shouldn't be null
     * @param cardMoneyLosses         the amount of money the Player should gain from each of these Cards. This shouldn't be null
     * @param cardPerPlayer      whether or not the value of cardMoney should be applied to each other Player, and then have
     *                           the opposite done to the normal Player. This shouldn't be null
     * @param cardMovementLosses       the amount of Spaces the Player should move for landing using these Cards. This shouldn't be null
     * @param cardSpaceLosses          the index of the Space the Player should go to for getting these Cards. This shouldn't be null
     * @param cardColorGroup     the index of the Space the Player should go to for getting these Cards. This shouldn't be null
     * @param cardRentMultiplier the rent multiplier that the Player should pay on the next Space they land on
     * @param cardRollMultiplier the amount that should be multiplied by a new roll as payment for rent instead of normal
     * @param cardPerHouses       the amount of money the Player should pay per house owned. This shouldn't be null
     * @param cardPerHotels       the amount of money the Player should pay per hotel owned. This shouldn't be null
     * @param cardGetOutJail     whether or not these Cards are get out of jail cards. This shouldn't be null
     * @param cardOwners          the Player who holds these cards. This shouldn't be null
     * @return the created Game
     */
    public static Game makeGame(
            //Space parameters
            String[] spaceNames, int[] spaceMoneyLosses, int[] spaceMovementLosses, int[] spaceSpaceLosses,
            String[] spaceColorGroups, double[] spaceRentMultipliers, double[] spaceRollMultipliers,
            int[] spaceDeckUsed, int[] spacePerHouses, int[] spacePerHotels,
            //Property parameters
            boolean[] spaceHaveProperty, int[] propertyPrices, int[] propertyMortgages, double[] propertyMortgagePercent,
            int[] propertyBuildPrices, String[] propertyColorGroups, int[] propertyMaxHouses, int[][] propertyRents,
            int[] propertyStartingHouses, boolean[] propertyAreDiceMultipliers, boolean[] propertyAreScaled,
            boolean[] propertyAreMortgaged, Player[] propertyOwners,
            //Game parameters
            String[] colorGroups, int jailPosition, int bailCost, GameUI gameUI, String[] prompts,
            //Player parameters
            String[] playerNames, String[] playerTypes, int[] playerWallets, int[] playerPositions,
            int[] playerJailTurns, int[] playerSalaries, int[] numTurnsInJail, Color[] playerColors,
            //Dice parameters
            int numDice, int diceSides,
            //Deck parameters
            String[][] cardTypes, String[][] cardDescriptions, int[][] cardMoneyLosses, boolean[][] cardPerPlayer,
            int[][] cardMovementLosses, int[][] cardSpaceLosses, String[][] cardColorGroup, double[][] cardRentMultiplier,
            double[][] cardRollMultiplier, int[][] cardPerHouses, int[][] cardPerHotels, boolean[][] cardGetOutJail,
            Player[][] cardOwners) {
        Space[] spaces = setupSpaces(
                //Space parameters
                spaceNames, spaceMoneyLosses, spaceMovementLosses, spaceSpaceLosses, spaceColorGroups, spaceRentMultipliers,
                spaceRollMultipliers, spacePerHouses, spacePerHotels, spaceDeckUsed, cardTypes.length,
                //Property parameters
                spaceHaveProperty, propertyPrices, propertyMortgages, propertyMortgagePercent,
                propertyBuildPrices, propertyColorGroups, propertyMaxHouses, propertyRents, propertyStartingHouses,
                propertyAreDiceMultipliers, propertyAreScaled, propertyAreMortgaged, propertyOwners);
        Player[] players = setupPlayers(playerNames, playerTypes, playerWallets, playerPositions, spaces.length,
                playerJailTurns, jailPosition, playerSalaries, numTurnsInJail, prompts, spaces, colorGroups, gameUI,
                playerColors);
        Dice[] dice = setupDice(numDice, diceSides);
        Deck[] deck = setupDecks(cardTypes, cardDescriptions, cardMoneyLosses, cardPerPlayer, cardMovementLosses,
                cardSpaceLosses, cardColorGroup, cardRentMultiplier, cardRollMultiplier, cardPerHouses, cardPerHotels,
                cardGetOutJail, cardOwners, spaces.length);

        return new Game(spaces, colorGroups, jailPosition, bailCost, players, dice, deck, gameUI, prompts);
    }

    /**
     * Sets up an Array of Spaces. Validates according to the conditions outlined here. All of these Arrays should have
     * the same length
     *
     * @param names                     the Space's name. This shouldn't be null
     * @param moneyLosses               the monetary penalty for landing on these Spaces. Should be 0 for no penalty
     * @param movementLosses            the movement penalty for landing on these Spaces. Should be 0 for no movement
     * @param spaceLosses               the index of the Space that the Player should go to for landing on these Spaces. Shouldn't be
     *                                  less than -1, which is if they shouldn't move
     * @param colorGroups               the color group that the player should go to as a penalty for landing on this space. Can be
     *                                  null for none
     * @param rentMultipliers           the multiplier of the rent that should be paid
     * @param rollMultipliers           the amount that should be multiplied by a new roll as payment for rent instead of normal
     * @param perHouses                 the amount the Player should pay per house. Shouldn't be less than 0
     * @param perHotels                 the amount the Player should pay per hotel. Shouldn't be less than 0
     * @param deckUsed                  the index of the Deck that this Space should use. Shouldn't be less than -1, which represents no
     *                                  Deck
     * @param numDecks                  the number of Decks. Used for validation, is not stored
     * @param haveProperty              whether or not these Space's have a Property. Should be false if not
     * @param propertyPrices            the price of this Property. This should be greater than 0
     * @param propertyMortgages         the mortgage of these Property. This should be greater than 0 and should be less then the price
     * @param propertyMortgagePercent   the percent of mortgage that should be paid as interest
     * @param propertyBuildPrices       the price of building on these Property's. This should be greater than 0
     * @param propertyColorGroups       the color group of these Property's. This shouldn't be null
     * @param propertyMaxHouses         the maximum number of houses on these Property's. This shouldn't be negative
     * @param propertyRents             the rents for these Property's at given states of ownership. This shouldn't be null
     * @param propertyStartingHouses    the number of houses these Property's should start with. This shouldn't be negative
     * @param propertyAreDiceMultiplier whether the values in propertyRents are dice roll multipliers
     * @param propertyAreScaled         whether the values in Rents are based on number of Properties exclusively
     * @param propertyAreMortgaged      whether or not these Properties are mortgaged
     * @param propertyOwners            the Player who should own these property's. This can be null
     * @return the created Spaces Array
     * @throws IllegalArgumentException when a null or mismatched Array is passed
     */
    private static Space[] setupSpaces(
            //Space parameters
            String[] names, int[] moneyLosses, int[] movementLosses, int[] spaceLosses, String[] colorGroups,
            double[] rentMultipliers, double[] rollMultipliers, int[] perHouses, int[] perHotels, int[] deckUsed, int numDecks,
            //Property parameters
            boolean[] haveProperty, int[] propertyPrices, int[] propertyMortgages, double[] propertyMortgagePercent,
            int[] propertyBuildPrices, String[] propertyColorGroups, int[] propertyMaxHouses, int[][] propertyRents,
            int[] propertyStartingHouses, boolean[] propertyAreDiceMultiplier, boolean[] propertyAreScaled,
            boolean[] propertyAreMortgaged,
            Player[] propertyOwners) {
        if (names != null && moneyLosses != null && movementLosses != null && spaceLosses != null && //Space validation
                colorGroups != null && rentMultipliers != null && rollMultipliers != null &&
                perHouses != null && perHotels != null && deckUsed != null && names.length == moneyLosses.length &&
                moneyLosses.length == movementLosses.length && movementLosses.length == spaceLosses.length &&
                spaceLosses.length == colorGroups.length && colorGroups.length == rentMultipliers.length &&
                rentMultipliers.length == rollMultipliers.length && rollMultipliers.length == perHouses.length &&
                perHouses.length == perHotels.length && perHotels.length == deckUsed.length &&
                //Property validation
                haveProperty != null && propertyPrices != null && propertyMortgages != null && propertyMortgagePercent != null &&
                propertyBuildPrices != null && propertyColorGroups != null && propertyMaxHouses != null &&
                propertyRents != null && propertyStartingHouses != null && propertyAreDiceMultiplier != null &&
                propertyAreScaled != null && propertyAreMortgaged != null && propertyOwners != null &&
                deckUsed.length == haveProperty.length && haveProperty.length == propertyPrices.length &&
                propertyPrices.length == propertyMortgages.length && propertyMortgages.length == propertyMortgagePercent.length &&
                propertyMortgagePercent.length == propertyBuildPrices.length &&
                propertyBuildPrices.length == propertyColorGroups.length &&
                propertyColorGroups.length == propertyMaxHouses.length &&
                propertyMaxHouses.length == propertyRents.length &&
                propertyRents.length == propertyStartingHouses.length &&
                propertyStartingHouses.length == propertyAreDiceMultiplier.length &&
                propertyAreDiceMultiplier.length == propertyAreScaled.length &&
                propertyAreScaled.length == propertyAreMortgaged.length &&
                propertyAreMortgaged.length == propertyOwners.length) {
            Space[] spaces = new Space[names.length];
            for (int i = 0; i < spaces.length; i++) {
                spaces[i] = setupSpace(
                        //Space parameters
                        names[i], moneyLosses[i], movementLosses[i], spaceLosses[i], colorGroups[i], rentMultipliers[i],
                        rollMultipliers[i], perHouses[i], perHotels[i], deckUsed[i], spaces.length, numDecks,
                        //Property parameters
                        haveProperty[i], propertyPrices[i], propertyMortgages[i], propertyMortgagePercent[i], propertyBuildPrices[i],
                        propertyColorGroups[i], propertyMaxHouses[i], propertyRents[i], propertyStartingHouses[i],
                        propertyAreDiceMultiplier[i], propertyAreScaled[i], propertyAreMortgaged[i], propertyOwners[i]);
            }
            return spaces;
        } else {
            throw new IllegalArgumentException("A null or mismatched Array was passed");
        }
    }

    /**
     * Sets up a Space including its Property. Validates according to the conditions outlined here
     *
     * @param name                     the Space's name. This shouldn't be null
     * @param moneyLoss                the monetary penalty for landing on this Space. Should be 0 for no penalty
     * @param movementLoss             the movement penalty for landing on this Space. Should be 0 for no penalty
     * @param spaceLoss                the index of the Space that the Player should go to for landing on this Space. Should be -1
     *                                 if they shouldn't move
     * @param colorGroup               the color group that the player should go to as a penalty for landing on this space. Can be
     *                                 null for none
     * @param rentMultiplier           the multiplier that the Player should have to pay on the rent of the next Space they land on
     * @param rollMultiplier           the amount that should be multiplied by a new roll as payment for rent instead of normal
     * @param perHouse                 the amount the Player should pay per house owned. Shouldn't be less than 0
     * @param perHotel                 the amount the Player should pay per hotel owned. Shouldn't be less than 0
     * @param deckUsed                 the index of the Deck that this Card should use. Shouldn't be less than -1, which represents no
     *                                 deck used
     * @param boardSize                the board's size. Used for validation, is not stored
     * @param numDecks                 the number of Decks. Used for validation, is not stored
     * @param hasProperty              whether or not this Space has a Property. Should be false if not
     * @param propertyPrice            the price of this Property. This should be greater than 0
     * @param propertyMortgage         the mortgage of this Property. This should be greater than 0 and less than the price
     * @param propertyMortgagePercent  the percent of the Properties mortgage that should be charged as interest
     * @param propertyBuildPrice       the price of building on this Property. This should be greater than 0
     * @param propertyColorGroup       the color group of this Property. This shouldn't be null
     * @param propertyMaxHouses        the maximum number of houses on this Property. This shouldn't be negative
     * @param propertyRents            the rents for this Property at given states of ownership. This shouldn't be null
     * @param propertyStartingHouses   the number of houses this Property should start with. This shouldn't be negative
     * @param propertyIsDiceMultiplier whether the values of propertyRents are dice multipliers rather than normal
     * @param propertyIsScaled         whether the values of propertyRents are completely based on how many Properties a
     *                                 Player owns
     * @param propertyIsMortgaged      whether or not this Property is mortgaged. Cannot be mortgaged if unowned
     * @param propertyOwner            the Player who should own this Property. This can be null
     * @return the created Space
     */
    private static Space setupSpace(
            //Space parameters
            String name, int moneyLoss, int movementLoss, int spaceLoss, String colorGroup, double rentMultiplier,
            double rollMultiplier, int perHouse, int perHotel, int deckUsed, int boardSize, int numDecks,
            //Property parameters
            boolean hasProperty, int propertyPrice, int propertyMortgage, double propertyMortgagePercent, int propertyBuildPrice,
            String propertyColorGroup, int propertyMaxHouses, int[] propertyRents, int propertyStartingHouses,
            boolean propertyIsDiceMultiplier, boolean propertyIsScaled, boolean propertyIsMortgaged,
            Player propertyOwner) {
        if (hasProperty) {
            return new Space(
                    //Space creation
                    name, moneyLoss, movementLoss, spaceLoss, colorGroup, rentMultiplier, rollMultiplier, perHouse,
                    perHotel, deckUsed, boardSize, numDecks,
                    //Property creation
                    new Property(propertyPrice, propertyMortgage, propertyMortgagePercent, propertyBuildPrice, propertyColorGroup,
                            propertyMaxHouses, propertyRents, propertyStartingHouses, propertyIsDiceMultiplier,
                            propertyIsScaled, propertyIsMortgaged, propertyOwner, name));
        } else {
            return new Space(name, moneyLoss, movementLoss, spaceLoss, colorGroup, rentMultiplier, rollMultiplier,
                    perHouse, perHotel, deckUsed, boardSize, numDecks, null);
        }
    }

    /**
     * Sets up an Array of Players. Validates according to the conditions outlined here. All of these Arrays
     * should have the same length
     *
     * @param names          the names of the Players to be created. This shouldn't be null
     * @param types          the types of the Players to be created (human or AI). This shouldn't be null or anything else
     * @param wallets        the amount of money that should be placed in the Player's wallet
     * @param positions      the position that the Player should start at
     * @param boardSize      the max board size, this is used for validation. This shouldn't be 0 or less than any of the
     *                       position values
     * @param jailTurns      the number of turns the Player has in jail. Should be 0 for none
     * @param jailPosition   the position of the jail on the board. This must be at a valid position
     * @param salaries       the salaries the Players should be awarded for passing go
     * @param numTurnsInJail the number of turns the Player should send in jail when they are sent there
     * @param prompts        the prompts that are used during the Game. This is only used for the AIPlayer Class
     * @param gameBoard      the game board for use in the AIPlayer Class
     * @param colorGroups    the color groups used in the game board for the AIPLayer Class
     * @param gameUI         the UI for the Game for the HumanPlayer Class
     * @param colors         the Colors of the Players
     * @return the created Players Array
     * @throws IllegalArgumentException when a null or mismatched Array is passed
     */
    private static Player[] setupPlayers(String[] names, String[] types, int[] wallets, int[] positions, int boardSize,
                                         int[] jailTurns, int jailPosition, int[] salaries, int[] numTurnsInJail,
                                         String[] prompts, Space[] gameBoard, String[] colorGroups, GameUI gameUI,
                                         Color[] colors) {
        if (names != null && types != null && wallets != null && positions != null && jailTurns != null && salaries != null &&
                numTurnsInJail != null && colors != null && names.length == types.length && types.length == wallets.length &&
                wallets.length == positions.length && positions.length == jailTurns.length &&
                jailTurns.length == salaries.length && salaries.length == numTurnsInJail.length &&
                numTurnsInJail.length == colors.length) {
            Player[] players = new Player[names.length];
            for (int i = 0; i < players.length; i++) {
                players[i] = setupPlayer(names[i], types[i], wallets[i], positions[i], boardSize, jailTurns[i],
                        jailPosition, salaries[i], numTurnsInJail[i], prompts, gameBoard, colorGroups, gameUI, colors[i]);
            }
            return players;
        } else {
            throw new IllegalArgumentException("A null or mismatched Array was passed");
        }
    }

    /**
     * Sets up a Player using the provided parameter
     * Differentiation between HumanPlayer and AIPlayer occurs here, and does not occur in Game
     *
     * @param name           the name of the Player to be created. This shouldn't be null
     * @param type           the type of the Player to be created (human or ai). This shouldn't be null or anything else
     * @param wallet         the amount of money that should be placed in the Player's wallet
     * @param position       the position that the Player should start at
     * @param boardSize      the max board size, this is used for validation This shouldn't be 0 or less than position
     * @param jailTurns      the number of turns the Player has in jail. Should be 0 for none
     * @param jailPosition   the position of the jail on the game board. This must be at a valid position
     * @param salary         the salaries the Players should be awarded for passing go. This must be greater than 0
     * @param numTurnsInJail the number of turns the Player should send in jail when they are sent there
     * @param prompts        the prompts that are used during the Game. This is only used for the AIPlayer Class
     * @param gameBoard      the game board for use in the AIPlayer Class
     * @param colorGroups    the color groups on the game board for use in the AIPlayer Class
     * @param gameUI         the UI for the Game. Used in the HumanPlayer Class
     * @param color          the Color of this Player
     * @return the created Player Object
     * @throws IllegalArgumentException when type is not ai or human
     */
    private static Player setupPlayer(String name, String type, int wallet, int position, int boardSize, int jailTurns,
                                      int jailPosition, int salary, int numTurnsInJail, String[] prompts,
                                      Space[] gameBoard, String[] colorGroups, GameUI gameUI, Color color) {
        if (type.equals("AI Player")) {
            return new AIPlayer(name, wallet, position, boardSize, jailTurns, jailPosition, salary, numTurnsInJail,
                    prompts, gameBoard, colorGroups, color);
        } else if (type.equals("Human Player")) {
            return new HumanPlayer(name, wallet, position, boardSize, jailTurns, jailPosition, salary, numTurnsInJail,
                    gameUI, color);
        } else {
            throw new IllegalArgumentException("An invalid type value was passed");
        }
    }

    /*
    The following methods manage initialization of the Game Class. They are static
     */

    /**
     * Sets up an Array of Dice. Validates according to the conditions outlined here
     *
     * @param numDice the number of dice. This should be greater than 0
     * @param sides   the number of sides on the dice. This should be greater than 0
     * @return the created Dice Array
     * @throws IllegalArgumentException when numDice is negative
     */
    private static Dice[] setupDice(int numDice, int sides) {
        if (numDice > 0) {
            Dice[] dice = new Dice[numDice];
            for (int i = 0; i < dice.length; i++) {
                dice[i] = new Dice(sides);
            }
            return dice;
        } else {
            throw new IllegalArgumentException("A negative numDice value was passed");
        }
    }

    /**
     * Sets up an Array of Decks. Validates according to the conditions outlined here. None of these Arrays can be null,
     * but the values in them may be null
     *
     * @param cardTypes          the types of Cards to be created. This shouldn't be null
     * @param cardDescriptions   the descriptions of the Cards to be created. This shouldn't be null
     * @param cardMoney          the amount of money the Player should gain from each of these Cards. This shouldn't be null
     * @param cardPerPlayer      whether or not the value of cardMoney should be applied to each other Player, and then have
     *                           the opposite done to the normal Player. This shouldn't be null
     * @param cardMovement       the amount of Spaces the Player should move for landing using these Cards. This shouldn't be null
     * @param cardSpace          the index of the Space the Player should go to for getting these Cards. This shouldn't be null
     * @param cardColorGroup     the index of the Space the Player should go to for getting these Cards. This shouldn't be null
     * @param cardRentMultiplier the rent multiplier that the Player should pay on the next Space they land on
     * @param cardRollMultiplier the amount that should be multiplied by a new roll as payment for rent instead of normal
     * @param cardPerHouse       the amount of money the Player should pay per house owned. This shouldn't be null
     * @param cardPerHotel       the amount of money the Player should pay per hotel owned. This shouldn't be null
     * @param cardGetOutJail     whether or not these Cards are get out of jail cards. This shouldn't be null
     * @param cardOwner          the Player who holds these cards. This shouldn't be null
     * @param boardSize          the board's size. Used for validation, this isn't stored
     * @return the completed Array of Decks
     * @throws IllegalArgumentException when a null or mismatched Array is passed
     */
    private static Deck[] setupDecks(String[][] cardTypes, String[][] cardDescriptions, int[][] cardMoney,
                                     boolean[][] cardPerPlayer, int[][] cardMovement, int[][] cardSpace,
                                     String[][] cardColorGroup, double[][] cardRentMultiplier, double[][] cardRollMultiplier,
                                     int[][] cardPerHouse, int[][] cardPerHotel, boolean[][] cardGetOutJail,
                                     Player[][] cardOwner, int boardSize) {
        if (cardTypes != null && cardDescriptions != null && cardMoney != null && cardPerPlayer != null &&
                cardMovement != null && cardSpace != null && cardColorGroup != null && cardRentMultiplier != null &&
                cardRollMultiplier != null && cardPerHouse != null && cardPerHotel != null && cardGetOutJail != null &&
                cardOwner != null && cardTypes.length == cardDescriptions.length &&
                cardDescriptions.length == cardMoney.length && cardMoney.length == cardPerPlayer.length &&
                cardPerPlayer.length == cardMovement.length && cardMovement.length == cardSpace.length &&
                cardSpace.length == cardColorGroup.length && cardColorGroup.length == cardRentMultiplier.length &&
                cardRentMultiplier.length == cardRollMultiplier.length && cardRollMultiplier.length == cardPerHouse.length &&
                cardPerHouse.length == cardPerHotel.length && cardPerHotel.length == cardGetOutJail.length &&
                cardGetOutJail.length == cardOwner.length) {
            Deck[] decks = new Deck[cardTypes.length];
            for (int i = 0; i < decks.length; i++) {
                decks[i] = setupDeck(cardTypes[i], cardDescriptions[i], cardMoney[i], cardPerPlayer[i], cardMovement[i],
                        cardSpace[i], cardColorGroup[i], cardRentMultiplier[i], cardRollMultiplier[i], cardPerHouse[i],
                        cardPerHotel[i], cardGetOutJail[i], cardOwner[i], boardSize);
            }
            return decks;
        } else {
            throw new IllegalArgumentException("A null or mismatched Array was passed");
        }

    }

    /*
    The following methods manage Space creation
     */

    /**
     * Sets up a Deck. Validates according to the conditions outlined here. None of these Arrays can be null,
     * but the values in them may be null
     *
     * @param cardTypes          the types of Cards to be created. This shouldn't be null
     * @param cardDescriptions   the descriptions of the Cards to be created. This shouldn't be null
     * @param cardMoney          the amount of money the Player should gain from each of these Cards. This shouldn't be null
     * @param cardPerPlayer      whether or not the value of cardMoney should be applied to each other Player, and then have
     *                           the opposite done to the normal Player. This shouldn't be null
     * @param cardMovement       the amount of Spaces the Player should move for landing using these Cards. This shouldn't be null
     * @param cardSpace          the index of the Space the Player should go to for getting these Cards. This shouldn't be null
     * @param cardColorGroup     the index of the Space the Player should go to for getting these Cards. This shouldn't be null
     * @param cardRentMultiplier the multiplier that the Player should pay for the next Space they land on
     * @param cardRollMultiplier the amount that should be multiplied by a new roll as payment for rent instead of normal
     * @param cardPerHouse       the amount of money the Player should pay per house owned. This shouldn't be null
     * @param cardPerHotel       the amount of money the Player should pay per hotel owned. This shouldn't be null
     * @param cardGetOutJail     whether or not these Cards are get out of jail cards. This shouldn't be null
     * @param cardOwner          the Player who holds these cards. This shouldn't be null
     * @param boardSize          the board's size. Used for validation, this isn't stored
     * @return the completed Deck
     * @throws IllegalArgumentException when a null or mismatched Array is passed
     */
    private static Deck setupDeck(String[] cardTypes, String[] cardDescriptions, int[] cardMoney,
                                  boolean[] cardPerPlayer, int[] cardMovement, int[] cardSpace, String[] cardColorGroup,
                                  double[] cardRentMultiplier, double[] cardRollMultiplier, int[] cardPerHouse,
                                  int[] cardPerHotel, boolean[] cardGetOutJail, Player[] cardOwner, int boardSize) {
        if (cardTypes != null && cardDescriptions != null && cardMoney != null && cardPerPlayer != null &&
                cardMovement != null && cardSpace != null && cardColorGroup != null && cardRentMultiplier != null &&
                cardRollMultiplier != null && cardPerHouse != null && cardPerHotel != null && cardGetOutJail != null &&
                cardOwner != null && cardTypes.length == cardDescriptions.length &&
                cardDescriptions.length == cardMoney.length && cardMoney.length == cardPerPlayer.length &&
                cardPerPlayer.length == cardMovement.length && cardMovement.length == cardSpace.length &&
                cardSpace.length == cardColorGroup.length && cardColorGroup.length == cardRentMultiplier.length &&
                cardRentMultiplier.length == cardRollMultiplier.length && cardRollMultiplier.length == cardPerHouse.length &&
                cardPerHouse.length == cardPerHotel.length && cardPerHotel.length == cardGetOutJail.length &&
                cardGetOutJail.length == cardOwner.length) {
            Card[] cards = new Card[cardTypes.length];
            for (int i = 0; i < cards.length; i++) {
                cards[i] = new Card(cardTypes[i], cardDescriptions[i], cardMoney[i], cardPerPlayer[i], cardMovement[i],
                        cardSpace[i], cardColorGroup[i], cardRentMultiplier[i], cardRollMultiplier[i], cardPerHouse[i],
                        cardPerHotel[i], cardGetOutJail[i], cardOwner[i], boardSize);
            }
            return new Deck(cards);
        } else {
            throw new IllegalArgumentException("A null or mismatched Array was passed");
        }

    }

    /**
     * Determines if the Players are in a valid state to finish setup
     *
     * @return if the Players are in a valid state to finish setup
     */
    private boolean canContinue() {
        if (PLAYER_LIST_MODEL.size() > 1) { //First we check if there are any Players
            boolean hasHuman = false;
            for (JComboBox<String> comboBox : TYPE_BOXES) { //Then we make sure there is at least one human
                if (comboBox.getSelectedIndex() == 0) {
                    hasHuman = true;
                    break;
                }
            }

            if (hasHuman) {
                for (int i = 0; i < COLORS.size(); i++) { //Then we make sure there are no identical colors
                    for (int j = 0; j < COLORS.size(); j++) {
                        if (i != j && COLORS.get(i).getRed() == COLORS.get(j).getRed() && COLORS.get(i).getBlue() ==
                                COLORS.get(j).getBlue() && COLORS.get(i).getGreen() == COLORS.get(j).getGreen()) {
                            return false;
                        }
                    }
                }
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    /*
    The following methods manage Player creation
     */

    /**
     * Invoked when an action occurs.
     *
     * @param e the event to be processed
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == BUTTONS[0]) { //If this is the case, the user wants to add a new Player
            PLAYER_LIST_MODEL.add(PLAYER_LIST_MODEL.getSize(), NAME_FIELD.getText());
            NAME_FIELD.setText("");
            BUTTONS[0].setEnabled(false);
            JComboBox<String> comboBox = new JComboBox<>(new String[]{"Human Player", "AI Player"});
            comboBox.addActionListener(this);
            TYPE_BOXES.add(comboBox);
            TYPE_PANEL.add(comboBox);
            JButton button = new JButton();
            button.setBackground(Color.white);
            button.addActionListener(this);
            COLOR_BUTTONS.add(button);
            COLOR_PANEL.add(button);
            COLORS.add(Color.white);
            BUTTONS[2].setEnabled(canContinue());
        } else if (e.getSource() == BUTTONS[1]) { //If this is the case, the user wants to remove an index
            TYPE_PANEL.remove(TYPE_BOXES.remove(PLAYER_LIST.getSelectedIndex()));
            COLORS.remove(PLAYER_LIST.getSelectedIndex());
            COLOR_PANEL.remove(COLOR_BUTTONS.remove(PLAYER_LIST.getSelectedIndex()));
            PLAYER_LIST_MODEL.remove(PLAYER_LIST.getSelectedIndex());
            PLAYER_LIST.clearSelection();
            BUTTONS[1].setEnabled(false);
            BUTTONS[2].setEnabled(canContinue());
        } else if (TYPE_BOXES.contains(e.getSource())) { //If this is the case the user interacted with a combo box
            BUTTONS[2].setEnabled(canContinue());
        } else if (COLOR_BUTTONS.contains(e.getSource())) {
            Color pickedColor = JColorChooser.showDialog(this, "Pick your player's color: ",
                    COLORS.get(COLOR_BUTTONS.indexOf(e.getSource())));
            if (pickedColor != null) {
                COLORS.set(COLOR_BUTTONS.indexOf(e.getSource()), pickedColor);
                ((JButton) e.getSource()).setBackground(pickedColor);
                BUTTONS[2].setEnabled(canContinue());
            }
        } else if (e.getSource() == BUTTONS[2]) { //This goes on an actually sets up the Game
            String[] playerNames = new String[PLAYER_LIST_MODEL.getSize()];
            for (int i = 0; i < playerNames.length; i++) {
                playerNames[i] = PLAYER_LIST_MODEL.get(i);

            }
            String[] playerTypes = new String[PLAYER_LIST_MODEL.getSize()];
            for (int i = 0; i < playerTypes.length; i++) {
                playerTypes[i] = TYPE_BOXES.get(i).getSelectedIndex() == 0 ? "Human Player" : "AI Player";
            }
            MAIN_UI.setupGame(playerNames, playerTypes, COLORS.toArray(new Color[0]));
        }
    }

    /**
     * Called whenever the value of the selection changes.
     *
     * @param e the event that characterizes the change.
     */
    @Override
    public void valueChanged(ListSelectionEvent e) { //If this is the case, the user interacted with the list
        BUTTONS[1].setEnabled(PLAYER_LIST.getSelectedIndex() >= 0);
    }

    /*
    The following methods manage Dice creation
     */

    /**
     * Gives notification that there was an insert into the document.  The
     * range given by the DocumentEvent bounds the freshly inserted region.
     *
     * @param e the document event
     */
    @Override
    public void insertUpdate(DocumentEvent e) {
        boolean matchFound = false;
        for (int i = 0; i < PLAYER_LIST_MODEL.size(); i++) {
            if (NAME_FIELD.getText().equals(PLAYER_LIST_MODEL.get(i))) {
                matchFound = true;
                break;
            }
        }

        if (!matchFound) {
            BUTTONS[0].setEnabled(NAME_FIELD.getText() != null && !NAME_FIELD.getText().equals(""));
        } else {
            BUTTONS[0].setEnabled(false);
        }
    }

    /*
    The following methods manage Deck setup
     */

    /**
     * Gives notification that a portion of the document has been
     * removed.  The range is given in terms of what the view last
     * saw (that is, before updating sticky positions).
     *
     * @param e the document event
     */
    @Override
    public void removeUpdate(DocumentEvent e) {
        boolean matchFound = false;
        for (int i = 0; i < PLAYER_LIST_MODEL.size(); i++) {
            if (NAME_FIELD.getText().equals(PLAYER_LIST_MODEL.get(i))) {
                matchFound = true;
                break;
            }
        }

        if (!matchFound) {
            BUTTONS[0].setEnabled(NAME_FIELD.getText() != null && !NAME_FIELD.getText().equals(""));
        } else {
            BUTTONS[0].setEnabled(false);
        }
    }

    /**
     * Gives notification that an attribute or set of attributes changed.
     *
     * @param e the document event
     */
    @Override
    public void changedUpdate(DocumentEvent e) {
        boolean matchFound = false;
        for (int i = 0; i < PLAYER_LIST_MODEL.size(); i++) {
            if (NAME_FIELD.getText().equals(PLAYER_LIST_MODEL.get(i))) {
                matchFound = true;
                break;
            }
        }

        if (!matchFound) {
            BUTTONS[0].setEnabled(NAME_FIELD.getText() != null && !NAME_FIELD.getText().equals(""));
        } else {
            BUTTONS[0].setEnabled(false);
        }
    }
}
