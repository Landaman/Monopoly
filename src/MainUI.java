import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * The main UI component of the monopoly game
 *
 * @author irswr
 */
public class MainUI extends JFrame implements ActionListener {
    //MainUI constants
    private final JButton[] BUTTONS; //Contains the buttons for use in the menu
    private final CardLayout CARD_LAYOUT; //Contains the Layout Manager that is managing the currently displayed panel

    /**
     * Initializes the UI, with the MenuGraphics
     */
    public MainUI() {
        super("Monopoly");
        CARD_LAYOUT = new CardLayout();
        setLayout(CARD_LAYOUT);

        //Contains the menu panel
        JPanel menu = new JPanel(new BorderLayout());

        MenuGraphics menuGraphics = new MenuGraphics();
        menu.add(menuGraphics, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        BUTTONS = new JButton[3];
        BUTTONS[0] = new JButton("Start");
        BUTTONS[0].addActionListener(this);
        BUTTONS[1] = new JButton("Custom Board");
        BUTTONS[1].addActionListener(this);
        BUTTONS[2] = new JButton("Quit");
        BUTTONS[2].addActionListener(this);
        buttonPanel.add(BUTTONS[0]);
        buttonPanel.add(BUTTONS[1]);
        buttonPanel.add(BUTTONS[2]);
        menu.add(buttonPanel, BorderLayout.SOUTH);

        JPanel gameCreator = new GameCreator(this);
        JPanel customGameCreator = new CustomGameCreator(this);

        add(menu, "menu");
        add(gameCreator, "game creator");
        add(customGameCreator, "custom game creator");
        CARD_LAYOUT.show(this.getContentPane(), "menu");

        setBounds(100, 100, 1500, 1000);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    /**
     * Initializes the program
     *
     * @param args the args for the program. These are unused
     */
    public static void main(String[] args) {
        new MainUI();
    }

    /**
     * Sets up a Default game
     *
     * @param playerNames  the names of the Players
     * @param playerTypes  the types of the Players
     * @param playerColors the Colors of the Player
     */
    public void setupGame(String[] playerNames, String[] playerTypes, Color[] playerColors) {
        add(new GameUI(playerNames, playerTypes, playerColors, this), "game ui");
        CARD_LAYOUT.show(this.getContentPane(), "game ui");
    }

    /**
     * Sets up a Game Board
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
     */
    public void setupGame (
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
            String[] colorGroups, int jailPosition, int bailCost, String[] prompts,
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
        add(new GameUI(spaceNames, spaceMoneyLosses, spaceMovementLosses, spaceSpaceLosses, spaceColorGroups,
                spaceRentMultipliers, spaceRollMultipliers, spaceDeckUsed, spacePerHouses, spacePerHotels,
                spaceHaveProperty, propertyPrices, propertyMortgages, propertyMortgagePercent, propertyBuildPrices,
                propertyColorGroups, propertyMaxHouses, propertyRents, propertyStartingHouses, propertyAreDiceMultipliers,
                propertyAreScaled, propertyAreMortgaged, propertyOwners, colorGroups, jailPosition, bailCost, prompts,
                playerNames, playerTypes, playerWallets, playerPositions, playerJailTurns, playerSalaries, numTurnsInJail,
                playerColors, numDice, diceSides, cardTypes, cardDescriptions, cardMoneyLosses, cardPerPlayer,
                cardMovementLosses, cardSpaceLosses, cardColorGroup, cardRentMultiplier, cardRollMultiplier, cardPerHouses,
                cardPerHotels, cardGetOutJail, cardOwners, this), "game ui");
        CARD_LAYOUT.show(this.getContentPane(), "game ui");
    }

    /**
     * Returns the user to the menu
     */
    public void showMenu() {
        CARD_LAYOUT.show(this.getContentPane(), "menu");
    }

    /**
     * Invoked when an action occurs.
     *
     * @param e the event to be processed
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == BUTTONS[0]) {
            CARD_LAYOUT.show(this.getContentPane(), "game creator");
        } else if (e.getSource() == BUTTONS[1]) {
            CARD_LAYOUT.show(this.getContentPane(), "custom game creator");
        } else if (e.getSource() == BUTTONS[2]) {
            System.exit(0);
        }
    }

}
