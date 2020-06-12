import javax.print.Doc;
import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Runs through the process of creating a custom game board
 * @author irswr
 */
public class CustomGameCreator extends JPanel implements ActionListener{
    //CustomGameCreator constants
    private final CardLayout CARD_LAYOUT; //Stores the card layout used to change what is shown
    private final JPanel MAIN_PANEL; //Stores the main panel
    private final MainUI MAIN_UI; //Stores the MainUI
    private final JPanel[] panels; //Stores the panels used in custom Game creation
    private final JButton CONTINUE; //Stores the continue button
    private final JButton BACK; //Stores the back button

    //CustomGameCreator fields
    private int currentStage; //Stores the current stage of the game setup
    private String[] spaceNames; //Stores the Space names
    private int[] spaceMoneyLosses; //Stores the Space money losses
    private int[] spaceMovementLosses; //Stores the Space movement losses
    private int[] spaceSpaceLosses; //Stores the Space Space losses
    private String[] spaceColorGroups; //Stores the Space color groups
    private double[] spaceRentMultipliers; //Stores the Space rent multipliers
    private double[] spaceRollMultipliers; //Stores the Space roll multipliers
    private int[] spacePerHouse; //Stores the cost per house of landing on the Space
    private int[] spacePerHotel; //Stores the cost per hotel of landing on this Space
    private int[] spaceDeckUsed; //Stores the deck used for landing on this Space
    private boolean[] spaceHasProperty; //Stores whether or not this Space has a Property
    private int[] propertyCosts; //Stores the Properties cost
    private int[] propertyMortgages; //Stores the Properties mortgages
    private double[] propertyMortgagePercents; //Stores the percent of the Properties mortgage that should be paid as interest
    private int[] propertyBuildPrices; //Stores the price of building on these Properties
    private String[] propertyColorGroups; //Stores the color group of these Properties
    private int[] propertyStartingHouses; //Stores the number of houses the Properties should start with
    private int[] propertyMaxHouses; //Stores the maximum number of Properties that should be stored on these Properties
    private int[][] propertyRents; //Stores the rents of the Properties
    private boolean[] propertyAreDiceMultiplier; //Stores whether or not these Properties are dice multipliers
    private boolean[] propertyAreScaled; //Stores whether or not these Properties are scaled
    private boolean[] propertyAreMortgaged; //Stores whether or not these Properties are mortgaged
    private Player[] propertyOwners; //Stores the Players that own these Properties
    private String[][] cardTypes; //Stores the types of the Cards
    private String[][] cardDescriptions; //Stores the descriptions of the Cards
    private int[][] cardMoneyLosses; //Stores the money losses for the Cards
    private boolean[][] cardPerPlayer; //Stores whether each Card's money loss is per Player
    private int[][] cardMovementLoss; //Stores each Card's movement loss
    private int[][] cardSpaceLoss; //Stores each Card's space loss
    private String[][] cardColorGroups; //Stores each Card's color group
    private double[][] cardRentMultipliers; //Stores each Card's rent multipliers
    private double[][] cardRollMultipliers; //Stores each Card's roll multipliers
    private int[][] cardPerHouses; //Stores each Card's cost per house
    private int[][] cardPerHotel; //Stores each Card's cost per hotel
    private boolean[][] cardIsGetOutJail; //Stores whether each Card is a get out of jail free Card
    private Player[][] cardOwners; //Stores the Players who own the Cards
    private String[] colorGroups; //Stores the Game's color groups
    private int jailPosition; //Stores the index that represents the jail's position
    private int jailBail; //Stores the amount the Player should pay as bail
    private int numDice; //Stores the number of Dice
    private int numDiceSides; //Stores the number of sides on the Dice
    private String[] playerNames; //Stores the names of the Players
    private String[] playerTypes; //Stores the types of the Players
    private int[] playerWallets; //Stores the Players wallets
    private int[] playerPositions; //Stores the Players positions
    private Color[] playerColors; //Stores the Players Colors
    private int[] playerSalaries; //Stores the Players salaries
    private int[] playerTurnsInJail; //Stores the Players turns in jail
    private int[] playerJailTurns; //Stores the Players turns in jail right now

    /**
     * Begins the process of setting up a custom game board
     * @param mainUI the MainUI that created this
     */
    public CustomGameCreator(MainUI mainUI) {
        if (mainUI != null) {
            MAIN_UI = mainUI;
            setLayout(new BorderLayout());

            MAIN_PANEL = new JPanel();
            CARD_LAYOUT = new CardLayout();
            MAIN_PANEL.setLayout(CARD_LAYOUT);

            add(MAIN_PANEL, BorderLayout.CENTER);

            JPanel buttonPanel = new JPanel();
            BACK = new JButton("Back");
            BACK.addActionListener(this);
            buttonPanel.add(BACK);

            CONTINUE = new JButton("Continue");
            CONTINUE.addActionListener(this);
            buttonPanel.add(CONTINUE);
            add(buttonPanel, BorderLayout.SOUTH);


            panels = new JPanel[5];
            panels[0] = new SpaceSetupPage();
            MAIN_PANEL.add(panels[0], "space setup");
            CARD_LAYOUT.show(MAIN_PANEL, "space setup");


            currentStage = 0;
        } else {
            throw new IllegalArgumentException("A null MainUI was passed");
        }
    }

    /**
     * Invoked when an action occurs.
     *
     * @param e the event to be processed
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == BACK) {
            if (currentStage == 0) {
                MAIN_UI.showMenu();
            } else if (currentStage == 1) {
                if (((DecksSetupPage) panels[0]).canGoBack()) {
                    CARD_LAYOUT.show(MAIN_PANEL, "space setup");
                    currentStage--;
                    MAIN_PANEL.remove(panels[0]);
                    panels[0] = null;
                }
            } else if (currentStage == 2) {
                if (((PropertySetupPage) panels[1]).canGoBack()) {
                    CARD_LAYOUT.show(MAIN_PANEL, "card setup");
                    currentStage--;
                    MAIN_PANEL.remove(panels[1]);
                    panels[1] = null;
                }
            } else if (currentStage == 3) {
                CARD_LAYOUT.show(MAIN_PANEL, "property setup");
                currentStage--;
                MAIN_PANEL.remove(panels[2]);
                panels[2] = null;
            } else if (currentStage == 4) {
                CARD_LAYOUT.show(MAIN_PANEL, "game setup");
                currentStage--;
                MAIN_PANEL.remove(panels[3]);
                panels[3] = null;
            }
        } else if (e.getSource() == CONTINUE) {
            if (currentStage == 0) {
                if (((SpaceSetupPage) panels[0]).canContinue()) {
                    SpaceSetupPage setupPage = (SpaceSetupPage) panels[0];
                    spaceNames = setupPage.getSpaceNames();
                    spaceMoneyLosses = setupPage.getSpaceMoneyLosses();
                    spaceMovementLosses = setupPage.getSpaceMovementLosses();
                    spaceSpaceLosses = setupPage.getSpaceSpaceLosses();
                    spaceColorGroups = setupPage.getSpaceColorGroups();
                    spaceRentMultipliers = setupPage.getSpaceRentMultipliers();
                    spaceRollMultipliers = setupPage.getSpaceRollMultipliers();
                    spacePerHouse = setupPage.getSpacePerHouses();
                    spacePerHotel = setupPage.getSpacePerHotel();
                    spaceDeckUsed = setupPage.getSpaceDeckUsed();
                    spaceHasProperty = setupPage.getSpaceHasProperty();
                    panels[1] = new DecksSetupPage();
                    MAIN_PANEL.add(panels[1], "card setup");
                    CARD_LAYOUT.show(MAIN_PANEL, "card setup");
                    currentStage++;
                } else {
                    JOptionPane.showMessageDialog(MAIN_UI, "Spaces are in an invalid state");
                }
            } else if (currentStage == 1) {
                if (((DecksSetupPage) panels[1]).canContinue()) {
                    DecksSetupPage setupPage = (DecksSetupPage) panels[1];
                    cardTypes = setupPage.getDECK_TYPES();
                    cardDescriptions = setupPage.getDeckNames();
                    cardMoneyLosses = setupPage.getDeckMoneyLosses();
                    cardPerPlayer = setupPage.getDeckIsPerPlayer();
                    cardMovementLoss = setupPage.getDeckMovementLosses();
                    cardSpaceLoss = setupPage.getDeckSpaceLosses();
                    cardColorGroups = setupPage.getDeckColorGroups();
                    cardRentMultipliers = setupPage.getDeckRentMultipliers();
                    cardRollMultipliers = setupPage.getDeckRollMultipliers();
                    cardPerHouses = setupPage.getDeckCostPerHouse();
                    cardPerHotel = setupPage.getDeckCostPerHotel();
                    cardIsGetOutJail = setupPage.getDeckAreJailCard();
                    panels[2] = new PropertySetupPage();
                    MAIN_PANEL.add(panels[2], "property setup");
                    CARD_LAYOUT.show(MAIN_PANEL, "card setup");
                    currentStage++;
                }
            } else if (currentStage == 2) {
                if (((PropertySetupPage) panels[2]).canContinue()) {
                    PropertySetupPage setupPage = (PropertySetupPage) panels[2];
                    propertyCosts = setupPage.getPropertyCosts();
                    propertyMortgages = setupPage.getPropertyMortgages();
                    propertyMortgagePercents = setupPage.getPropertyMortgagePercents();
                    propertyBuildPrices = setupPage.getPropertyBuildPrices();
                    propertyColorGroups = setupPage.getPropertyColorGroups();
                    propertyMaxHouses = setupPage.getPropertyMaxHouses();
                    propertyRents = setupPage.getPropertyRents();
                    propertyAreDiceMultiplier = setupPage.getPropertyAreDiceMultiplier();
                    propertyAreScaled = setupPage.getPropertyAreScaled();
                    ArrayList<String> colorGroups = new ArrayList<>();
                    for (String colorGroup : propertyColorGroups) {
                        if (!colorGroups.contains(colorGroup)) {
                            colorGroups.add(colorGroup);
                        }
                    }
                    this.colorGroups = colorGroups.toArray(new String[0]);
                    panels[3] = new GameSetupPage();
                    MAIN_PANEL.add(panels[3], "game setup");
                    CARD_LAYOUT.show(MAIN_PANEL, "game setup");
                    currentStage++;
                }
            } else if (currentStage == 3) {
                GameSetupPage setupPage = (GameSetupPage) panels[3];
                jailPosition = setupPage.getJailPosition();
                jailBail = setupPage.getBailCost();
                numDice = setupPage.getNumDice();
                numDiceSides = setupPage.getNumDiceSides();
                panels[4] = new PlayerSetupPage();
                MAIN_PANEL.add(panels[4], "player setup");
                CARD_LAYOUT.show(MAIN_PANEL, "player setup");
            } else if (currentStage == 4) {
                if (((PlayerSetupPage) panels[4]).canContinue()) {
                    PlayerSetupPage setupPage = (PlayerSetupPage) panels[4];
                    playerNames = setupPage.getPlayerNames();
                    playerTypes = setupPage.getPlayerTypes();
                    playerWallets = setupPage.getPlayerWallets();
                    playerPositions = setupPage.getPlayerPositions();
                    playerColors = setupPage.getPlayerColors();
                    playerSalaries = setupPage.getPlayerSalaries();
                    playerTurnsInJail = setupPage.getPlayerJailTurns();
                    playerJailTurns = new int[playerTurnsInJail.length];
                    Arrays.fill(playerJailTurns, Defaults.getPlayerTurnsInJail());
                    cardOwners = new Player[cardTypes.length][];
                    for (int i = 0; i < cardOwners.length; i++) {
                        cardOwners[i] = new Player[cardTypes[i].length];
                    }
                    propertyStartingHouses = new int[spaceHasProperty.length];
                    for (int i = 0; i < spaceHasProperty.length; i++) {
                        if (spaceHasProperty[i]) {
                            propertyStartingHouses[i] = 0;
                        } else {
                            propertyStartingHouses[i] = -1;
                        }
                    }
                    propertyAreMortgaged = new boolean[spaceHasProperty.length];
                    Arrays.fill(propertyAreMortgaged, false);
                    propertyOwners = new Player[spaceHasProperty.length];
                    Arrays.fill(propertyOwners, null);

                    MAIN_UI.setupGame(spaceNames, spaceMoneyLosses, spaceMovementLosses, spaceSpaceLosses, spaceColorGroups,
                            spaceRentMultipliers, spaceRollMultipliers, spaceDeckUsed, spacePerHouse, spacePerHotel,
                            spaceHasProperty, propertyCosts, propertyMortgages, propertyMortgagePercents,
                            propertyBuildPrices, propertyColorGroups, propertyMaxHouses, propertyRents, propertyStartingHouses,
                            propertyAreDiceMultiplier, propertyAreScaled, propertyAreMortgaged, propertyOwners, colorGroups,
                            jailPosition, jailBail, Defaults.getPROMPTS(), playerNames, playerTypes, playerWallets,
                            playerPositions, playerJailTurns, playerSalaries, playerTurnsInJail, playerColors, numDice,
                            numDiceSides, cardTypes, cardDescriptions, cardMoneyLosses, cardPerPlayer, cardMovementLoss,
                            cardSpaceLoss, cardColorGroups, cardRentMultipliers, cardRollMultipliers, cardPerHouses,
                            cardPerHotel, cardIsGetOutJail, cardOwners);
                }
            }
        }
    }

    /**
     * Sets up the setup of the Players
     * @author irswr
     */
    private class PlayerSetupPage extends JPanel implements ActionListener, DocumentListener{
        private final ArrayList<PlayerSetup> PLAYER_SETUPS; //Stores the Player setups currently in use
        private final JPanel PLAYER_PANEL; //Stores the Panel being used to store the Players
        private final ArrayList<JButton> REMOVE_BUTTONS; //Stores the remove buttons currently in use
        private final JButton ADD_BUTTON; //Stores the add button
        private final JTextField NAME_FIELD; //Stores the field that allows the Player to input a name

        /**
         * Sets up the setup of the Players
         */
        public PlayerSetupPage() {
            setLayout(new BorderLayout());

            JPanel buttonPanel = new JPanel();
            NAME_FIELD = new JTextField(20);
            NAME_FIELD.setToolTipText("Players name");
            NAME_FIELD.getDocument().addDocumentListener(this);
            buttonPanel.add(NAME_FIELD);
            ADD_BUTTON = new JButton("Add");
            ADD_BUTTON.addActionListener(this);
            ADD_BUTTON.setEnabled(false);
            buttonPanel.add(ADD_BUTTON);
            add(buttonPanel, BorderLayout.SOUTH);

            PLAYER_PANEL = new JPanel();
            PLAYER_PANEL.setLayout(new BoxLayout(PLAYER_PANEL, BoxLayout.Y_AXIS));

            PLAYER_SETUPS = new ArrayList<>();
            REMOVE_BUTTONS = new ArrayList<>();
        }

        /**
         * Gets whether or not setup can continue
         * @return whether or not setup can continue
         */
        public boolean canContinue() {
            boolean foundHumanPlayer = false;
            for (int i = 0; i < PLAYER_SETUPS.size(); i++) {
                for (int j = 0; j < PLAYER_SETUPS.size(); j++) {
                    if (PLAYER_SETUPS.get(i).getPlayerType().equals("Human Player")) {
                        foundHumanPlayer = true;
                    }

                    if (i != j && PLAYER_SETUPS.get(i).getPlayerColor().getRGB() != PLAYER_SETUPS.get(j).getPlayerColor().getRGB()) {
                        JOptionPane.showMessageDialog(MAIN_UI, "All players must have a unique color");
                        return false;
                    }
                }
            }
            if (!foundHumanPlayer) {
                JOptionPane.showMessageDialog(MAIN_UI, "The game must have at least one human player");
            }
            return foundHumanPlayer;
        }

        /**
         * Invoked when an action occurs.
         *
         * @param e the event to be processed
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == ADD_BUTTON) {
                PLAYER_SETUPS.add(new PlayerSetup(NAME_FIELD.getText()));
                NAME_FIELD.setText("");
                JButton removeButton = new JButton("Remove");
                removeButton.addActionListener(this);
                REMOVE_BUTTONS.add(removeButton);
                PLAYER_SETUPS.get(PLAYER_SETUPS.size() -1).add(removeButton);
                PLAYER_PANEL.add(PLAYER_SETUPS.get(PLAYER_SETUPS.size() - 1));
                PLAYER_PANEL.validate();
                PLAYER_PANEL.repaint();
            } else if (REMOVE_BUTTONS.contains(e.getSource())) {
                int index = REMOVE_BUTTONS.indexOf(e.getSource());
                REMOVE_BUTTONS.remove(index);
                PLAYER_PANEL.remove(PLAYER_SETUPS.remove(index));
                PLAYER_PANEL.revalidate();
                PLAYER_PANEL.repaint();
            }
        }

        /**
         * Gives notification that there was an insert into the document.  The
         * range given by the DocumentEvent bounds the freshly inserted region.
         *
         * @param e the document event
         */
        @Override
        public void insertUpdate(DocumentEvent e) {
            for (PlayerSetup playerSetup : PLAYER_SETUPS) {
                if (playerSetup.getPlayerName().equals(NAME_FIELD.getText())) {
                    ADD_BUTTON.setEnabled(false);
                    return;
                }
            }
            ADD_BUTTON.setEnabled(NAME_FIELD.getText() != null && !NAME_FIELD.getText().equals(""));
        }

        /**
         * Gives notification that a portion of the document has been
         * removed.  The range is given in terms of what the view last
         * saw (that is, before updating sticky positions).
         *
         * @param e the document event
         */
        @Override
        public void removeUpdate(DocumentEvent e) {
            for (PlayerSetup playerSetup : PLAYER_SETUPS) {
                if (playerSetup.getPlayerName().equals(NAME_FIELD.getText())) {
                    ADD_BUTTON.setEnabled(false);
                    return;
                }
            }
            ADD_BUTTON.setEnabled(NAME_FIELD.getText() != null && !NAME_FIELD.getText().equals(""));
        }

        /**
         * Gives notification that an attribute or set of attributes changed.
         *
         * @param e the document event
         */
        @Override
        public void changedUpdate(DocumentEvent e) {
            for (PlayerSetup playerSetup : PLAYER_SETUPS) {
                if (playerSetup.getPlayerName().equals(NAME_FIELD.getText())) {
                    ADD_BUTTON.setEnabled(false);
                    return;
                }
            }
            ADD_BUTTON.setEnabled(NAME_FIELD.getText() != null && !NAME_FIELD.getText().equals(""));
        }

        /**
         * Gets the names of the Players being setup
         * @return the names of the Players being setup
         */
        public String[] getPlayerNames() {
            String[] playerNames = new String[PLAYER_SETUPS.size()];
            for (int i = 0; i < playerNames.length; i++) {
                playerNames[i] = PLAYER_SETUPS.get(i).getPlayerName();
            }
            return playerNames;
        }

        /**
         * Gets the types of the Players being setup
         * @return the types of the Players being setup
         */
        public String[] getPlayerTypes() {
            String[] playerTypes = new String[PLAYER_SETUPS.size()];
            for (int i = 0; i < playerTypes.length; i++) {
                playerTypes[i] = PLAYER_SETUPS.get(i).getPlayerType();
            }
            return playerTypes;
        }

        /**
         * Gets the colors of the Players being setup
         * @return the colors of the Players being setup
         */
        public Color[] getPlayerColors() {
            Color[] playerColors = new Color[PLAYER_SETUPS.size()];
            for (int i = 0; i < playerColors.length; i++) {
                playerColors[i] = PLAYER_SETUPS.get(i).getPlayerColor();
            }
            return playerColors;
        }

        /**
         * Gets the Players starting wallet
         * @return the Players starting wallet
         */
        public int[] getPlayerWallets() {
            int[] playerWallets = new int[PLAYER_SETUPS.size()];
            for (int i = 0; i < playerWallets.length; i++) {
                playerWallets[i] = PLAYER_SETUPS.get(i).getStartingWallet();
            }
            return playerWallets;
        }

        /**
         * Gets the Players starting position
         * @return the Players starting position
         */
        public int[] getPlayerPositions() {
            int[] playerPositions = new int[PLAYER_SETUPS.size()];
            for (int i = 0; i < playerPositions.length; i++) {
                playerPositions[i] = PLAYER_SETUPS.get(i).getStartingPosition();
            }
            return playerPositions;
        }

        /**
         * Gets the Players salaries
         * @return the Players salaries
         */
        public int[] getPlayerSalaries() {
            int[] playerSalaries = new int[PLAYER_SETUPS.size()];
            for (int i = 0; i < playerSalaries.length; i++) {
                playerSalaries[i] = PLAYER_SETUPS.get(i).getSalary();
            }
            return playerSalaries;
        }

        /**
         * Gets the number of turns the Players spend in jail
         * @return the number of turns the Players spend in jail
         */
        public int[] getPlayerJailTurns() {
            int[] playerJailTurns = new int[PLAYER_SETUPS.size()];
            for (int i = 0; i < playerJailTurns.length; i++) {
                playerJailTurns[i] = PLAYER_SETUPS.get(i).getJailTurns();
            }
            return playerJailTurns;
        }
    }

    /**
     * Manages the setup of a single Player
     */
    private class PlayerSetup extends JPanel {
        //PlayerSetup constants
        private final JLabel NAME_LABEL; //Stores the label that represents the Players name
        private final JComboBox<String> TYPE; //Stores the selector that allows the Player to choose their type
        private final JButton COLOR_BUTTON; //Stores the button that allows the Player to pick their color
        private final JSpinner WALLET; //Stores the selector that allows the Player to pick their wallet
        private final JSpinner POSITION; //Stores the selector that allows the Player to pick their position
        private final JSpinner SALARY; //Stores the selector that allows the Player to pick their salary
        private final JSpinner JAIL_TURNS; //Stores the selector that allows the Player to pick the number of turns their jail sentance lasts

        /**
         * Sets up the setup of a single Player
         * @param name the name of the Player
         * @throws IllegalArgumentException when a null or empty name is passed
         */
        public PlayerSetup(String name) {
            if (name != null && name.length() > 0) {
                NAME_LABEL = new JLabel(name);
                NAME_LABEL.setToolTipText("The player's name");
                add(NAME_LABEL);
                TYPE = new JComboBox<>(new String[] {"Human Player", "AI Player"});
                TYPE.setToolTipText("The player's type");
                COLOR_BUTTON = new JButton();
                COLOR_BUTTON.addActionListener(e -> {
                    Color color = JColorChooser.showDialog(this, "Pick your player's color",
                            COLOR_BUTTON.getBackground());
                    if (color != null) {
                        COLOR_BUTTON.setBackground(color);
                    }
                });
                COLOR_BUTTON.setToolTipText("The player's color");
                add(COLOR_BUTTON);
                WALLET = new JSpinner(new SpinnerNumberModel(1500, 0, 10000, 1));
                WALLET.setToolTipText("The player's wallet");
                add(WALLET);
                POSITION = new JSpinner(new SpinnerNumberModel(0, 0, spaceNames.length -1, 1));
                POSITION.setToolTipText("The index -1 of the player's starting position");
                add(POSITION);
                SALARY = new JSpinner(new SpinnerNumberModel(200, 0, 10000, 1));
                SALARY.setToolTipText("The player's salary");
                add(SALARY);
                JAIL_TURNS = new JSpinner(new SpinnerNumberModel(3, 0, 10, 1));
                JAIL_TURNS.setToolTipText("The number of turns the Player spends in jail");
                add(JAIL_TURNS);
            } else {
                throw new IllegalArgumentException("A null or empty name was passed");
            }
        }

        /**
         * Gets the Players name as is stored in the NAME_LABEL Label
         * @return the Players name as is stored in the NAME_LABEL Label
         */
        public String getPlayerName() {
            return NAME_LABEL.getText();
        }

        /**
         * Gets the Players type as is stored in the TYPE ComboBox
         * @return the Players type as is stored in the TYPE ComboBox
         */
        public String getPlayerType() {
            return TYPE.getSelectedIndex() == 0 ? "Human Player" : "AI Player";
        }

        /**
         * Gets the Players color as is stored in the COLOR_BUTTON Button
         * @return the Players color as is stored in the COLOR_BUTTON Button
         */
        public Color getPlayerColor() {
            return COLOR_BUTTON.getBackground();
        }

        /**
         * Gets the Players starting wallet as is stored in the WALLET Spinner
         * @return the Players starting wallet as is stored in the WALLET Spinner
         */
        public int getStartingWallet() {
            return (Integer) WALLET.getValue();
        }

        /**
         * Gets the Players starting position as is stored in the POSITION Spinner
         * @return the Players starting position as is stored in the POSITION Spinner
         */
        public int getStartingPosition() {
            return (Integer) POSITION.getValue();
        }

        /**
         * Gets the Players salary as is stored in the SALARY Spinner
         * @return the Players salary as is stored in the SALARY Spinner
         */
        public int getSalary() {
            return (Integer) SALARY.getValue();
        }

        /**
         * Gets the number of turns the Player spends in jail as is stored in the JAIL_TURNS Spinner
         * @return the number of turns the Player spends in jail as is stored in the JAIL_TURNS Spinner
         */
        public int getJailTurns() {
            return (Integer) JAIL_TURNS.getValue();
        }
    }

    /**
     * Manages the setup of the Game
     * @author irswr
     */
    private class GameSetupPage extends JPanel {
        //GameSetupPage constants
        private final JSpinner[] SPINNERS; //Stores the spinners used to setup the game

        /**
         * Sets up the Game setup
         */
        public GameSetupPage() {
            SPINNERS = new JSpinner[4];
            SPINNERS[0] = new JSpinner(new SpinnerNumberModel(0, 0, spaceNames.length - 1, 1));
            SPINNERS[0].setToolTipText("Index -1 of the space that should be the jail");
            add(SPINNERS[0]);
            SPINNERS[1] = new JSpinner(new SpinnerNumberModel(0, 50, 1000, 1));
            SPINNERS[1].setToolTipText("Cost of the bail for jail");
            add(SPINNERS[1]);
            SPINNERS[2] = new JSpinner(new SpinnerNumberModel(1, 2, 10, 1));
            SPINNERS[2].setToolTipText("Number of dice");
            add(SPINNERS[2]);
            SPINNERS[3] = new JSpinner(new SpinnerNumberModel(1, 6, 20, 1));
            SPINNERS[3].setToolTipText("Number of sides on the dice");
            add(SPINNERS[3]);
        }

        /**
         * Gets the index of the jail as is stored in the SPINNERS[0] Spinner
         * @return the index of the jail as is stored in the SPINNERS[0] Spinner
         */
        public int getJailPosition() {
            return (Integer) SPINNERS[0].getValue();
        }

        /**
         * Gets the cost of bail as is stored in the SPINNERS[1] Spinner
         * @return the cost of bail as is stored in the SPINNERS[1] Spinner
         */
        public int getBailCost() {
            return (Integer) SPINNERS[1].getValue();
        }

        /**
         * Gets the number of Dice as is stored in the SPINNERS[2] Spinner
         * @return the number of Dice as is stored in the SPINNERS[2] Spinner
         */
        public int getNumDice() {
            return (Integer) SPINNERS[2].getValue();
        }

        /**
         * Gets the number of sides on the Dice as is stored in the SPINNERS[3] Spinner
         * @return the number of sides on the Dice as istored in the SPINNERS[3] Spinner
         */
        public int getNumDiceSides() {
            return (Integer) SPINNERS[3].getValue();
        }
    }

    /**
     * Manages the setup of the Properties
     * @author irswr
     */
    private class PropertySetupPage extends JPanel {
        //PropertySetupPage constants
        private final PropertySetup[] PROPERTY_SETUPS; //Stores the Properties being setup
        private final PropertyRentSetup[] PROPERTY_RENT_SETUPS; //Stores the rents of the Properties being setup
        private final CardLayout CARD_LAYOUT; //Stores the CardLayout for this Panel

        //PropertySetupPage fields
        private int currentState;
        private JPanel rentPanel;

        /**
         * Sets up the Property setup
         */
        public PropertySetupPage() {
            CARD_LAYOUT = new CardLayout();
            setLayout(CARD_LAYOUT);

            PROPERTY_SETUPS = new PropertySetup[spaceHasProperty.length];
            PROPERTY_RENT_SETUPS = new PropertyRentSetup[spaceHasProperty.length];

            JPanel initialPanel = new JPanel();
            initialPanel.setLayout(new BoxLayout(initialPanel, BoxLayout.Y_AXIS));

            for (int i = 0; i < PROPERTY_SETUPS.length; i++) {
                if (spaceHasProperty[i]) {
                    PROPERTY_SETUPS[i] = new PropertySetup(spaceNames[i]);
                    initialPanel.add(PROPERTY_SETUPS[i]);
                } else {
                    PROPERTY_SETUPS[i] = null;
                }
            }

            add(initialPanel, "initial");
            CARD_LAYOUT.show(this,"initial");

            currentState = 0;
        }

        /**
         * Determines if the setup process can continue
         * @return whether or not the setup process can continue
         */
        public boolean canContinue() {
            if (currentState == 0) {
                for (String string : spaceColorGroups) {
                    boolean matchFound = false;
                    for (PropertySetup propertySetup : PROPERTY_SETUPS) {
                        if (propertySetup != null) {
                            if (propertySetup.getPROPERTY_COLOR_GROUP().equals(string)) {
                                matchFound = true;
                                break;
                            }
                        }
                    }

                    if (!matchFound) {
                        JOptionPane.showMessageDialog(MAIN_UI, "A previously defined color group is missing");
                        return false;
                    }
                }

                for (String[] strings : cardColorGroups) {
                    for (String string : strings) {
                        boolean matchFound = false;
                        for (PropertySetup propertySetup : PROPERTY_SETUPS) {
                            if (propertySetup != null) {
                                if (propertySetup.getPROPERTY_COLOR_GROUP().equals(string)) {
                                    matchFound = true;
                                    break;
                                }
                            }
                        }
                        if (!matchFound) {
                            JOptionPane.showMessageDialog(MAIN_UI, "A previously defined color group is missing");
                            return false;
                        }
                    }
                }

                //If the tests were passed we'll now go and setup the rent panel
                rentPanel = new JPanel();
                rentPanel.setLayout(new BoxLayout(rentPanel, BoxLayout.Y_AXIS));
                for (int i = 0; i < PROPERTY_SETUPS.length; i++) {
                    if (PROPERTY_SETUPS[i] != null) {
                        int numStates;
                        String name = spaceNames[i];
                        if (PROPERTY_SETUPS[i].getPROPERTY_IS_SCALED()) {
                            numStates = 0;
                            String colorGroup = PROPERTY_SETUPS[i].getPROPERTY_COLOR_GROUP();
                            for (PropertySetup propertySetup : PROPERTY_SETUPS) {
                                if (propertySetup != null && propertySetup.getPROPERTY_COLOR_GROUP().equals(colorGroup)) {
                                    numStates++;
                                }
                            }
                        } else {
                            numStates = PROPERTY_SETUPS[i].getPROPERTY_MAX_HOUSES() + 2;
                        }
                        PROPERTY_RENT_SETUPS[i] = new PropertyRentSetup(name, numStates);
                        rentPanel.add(PROPERTY_RENT_SETUPS[i]);
                        add(rentPanel, "rent");
                        CARD_LAYOUT.show(this, "rent");
                    } else {
                        PROPERTY_RENT_SETUPS[i] = null;
                    }
                }
                currentState++;
                return false;
            } else {
                return true;
            }
        }

        /**
         * Gets whether or not the setup can go back
         * @return whether or not the setup can go back
         */
        public boolean canGoBack() {
            if (currentState == 0) {
                return true;
            } else {
                CARD_LAYOUT.show(this, "initial");
                remove(rentPanel);
                currentState--;
                return false;
            }
        }

        /**
         * Gets the prices of the setup Properties
         * @return the prices of the setup Properties
         */
        public int[] getPropertyCosts() {
            int[] propertyCost = new int[PROPERTY_SETUPS.length];
            for (int i = 0; i < propertyCost.length; i++) {
                if (PROPERTY_SETUPS[i] != null) {
                    propertyCost[i] = PROPERTY_SETUPS[i].getPROPERTY_COST();
                } else {
                    propertyCost[i] = -1;
                }
            }
            return propertyCost;
        }

        /**
         * Gets the mortgages of the setup Properties
         * @return the mortgages of the setup Properties
         */
        public int[] getPropertyMortgages() {
            int[] propertyMortgage = new int[PROPERTY_SETUPS.length];
            for (int i = 0; i < propertyMortgage.length; i++) {
                if (PROPERTY_SETUPS[i] != null) {
                    propertyMortgage[i] = PROPERTY_SETUPS[i].getPROPERTY_MORTGAGE();
                } else {
                    propertyMortgage[i] = -1;
                }
            }
            return propertyMortgage;
        }

        /**
         * Gets the interest percent of the setup Properties
         * @return the interest percent of the setup Properties
         */
        public double[] getPropertyMortgagePercents() {
            double[] propertyMortgagePercents = new double[PROPERTY_SETUPS.length];
            for (int i = 0; i < propertyMortgagePercents.length; i++) {
                if (PROPERTY_SETUPS[i] != null) {
                    propertyMortgagePercents[i] = PROPERTY_SETUPS[i].getPROPERTY_MORTGAGE_PERCENT();
                } else {
                    propertyMortgagePercents[i] = -1;
                }
            }
            return propertyMortgagePercents;
        }

        /**
         * Gets the prices for building on the setup Properties
         * @return the prices for building on the setup Properties
         */
        public int[] getPropertyBuildPrices() {
            int[] propertyBuildPrices = new int[PROPERTY_SETUPS.length];
            for (int i = 0; i < propertyBuildPrices.length; i++) {
                if (PROPERTY_SETUPS[i] != null) {
                    propertyBuildPrices[i] = PROPERTY_SETUPS[i].getPROPERTY_BUILD_PRICE();
                } else {
                    propertyBuildPrices[i] = -1;
                }
            }
            return propertyBuildPrices;
        }

        /**
         * Gets the color groups of the setup Properties
         * @return the color groups of the setup Properties
         */
        public String[] getPropertyColorGroups() {
            String[] propertyColorGroups = new String[PROPERTY_SETUPS.length];
            for (int i = 0; i < propertyColorGroups.length; i++) {
                if (PROPERTY_SETUPS[i] != null) {
                    propertyColorGroups[i] = PROPERTY_SETUPS[i].getPROPERTY_COLOR_GROUP();
                } else {
                    propertyColorGroups[i] = null;
                }
            }
            return propertyColorGroups;
        }

        /**
         * Gets the max number of houses that can be built on the setup Properties
         * @return the max number of houses that can be built on the setup Properties
         */
        public int[] getPropertyMaxHouses() {
            int[] propertyMaxHouses = new int[PROPERTY_SETUPS.length];
            for (int i = 0; i < propertyMaxHouses.length; i++) {
                if (PROPERTY_SETUPS[i] != null) {
                    propertyMaxHouses[i] = PROPERTY_SETUPS[i].getPROPERTY_MAX_HOUSES();
                } else {
                    propertyMaxHouses[i] = -1;
                }
            }
            return propertyMaxHouses;
        }

        /**
         * Gets whether the setup Properties are dice multipliers
         * @return whether the setup Properties are dice multipliers
         */
        public boolean[] getPropertyAreDiceMultiplier() {
            boolean[] propertyDiceMultiplier = new boolean[PROPERTY_SETUPS.length];
            for (int i = 0; i < propertyDiceMultiplier.length; i++) {
                if (PROPERTY_SETUPS[i] != null) {
                    propertyDiceMultiplier[i] = PROPERTY_SETUPS[i].getPROPERTY_IS_DICE_MULTIPLIER();
                } else {
                    propertyDiceMultiplier[i] = false;
                }
            }
            return propertyDiceMultiplier;
        }

        /**
         * Gets whether the setup Properties are scaled
         * @return whether the setup Properties are scaled
         */
        public boolean[] getPropertyAreScaled() {
            boolean[] propertyAreScaled = new boolean[PROPERTY_SETUPS.length];
            for (int i = 0; i < propertyAreScaled.length; i++) {
                if (PROPERTY_SETUPS[i] != null) {
                    propertyAreScaled[i] = PROPERTY_SETUPS[i].getPROPERTY_IS_SCALED();
                } else {
                    propertyAreScaled[i] = false;
                }
            }
            return propertyAreScaled;
        }

        /**
         * Gets the rents for the setup Properties
         * @return the rents for the setup Properties
         */
        public int[][] getPropertyRents() {
            int[][] propertyRents = new int[PROPERTY_RENT_SETUPS.length][];
            for (int i = 0; i < propertyRents.length; i++) {
                if (PROPERTY_RENT_SETUPS[i] != null) {
                    propertyRents[i] = PROPERTY_RENT_SETUPS[i].getPropertyRents();
                } else {
                    propertyRents[i] = null;
                }
            }
            return propertyRents;
        }
    }

    /**
     * Manages the entering of the rents for a single Property
     * @author irswr
     */
    private static class PropertyRentSetup extends JPanel {
        private final JSpinner[] RENT_SPINNERS; //Stores the Properties rent in a Spinner

        /**
         * Sets up the entering of the rents for a single Property
         * @param name the name of the Property
         * @param numStates the number of rent states the Property has
         */
        public PropertyRentSetup (String name, int numStates) {
            if (name != null && name.length() > 0 && numStates > 0) {
                RENT_SPINNERS = new JSpinner[numStates];
                for (int i = 0; i < RENT_SPINNERS.length; i++) {
                    RENT_SPINNERS[i] = new JSpinner(new SpinnerNumberModel(100, 1, 5000, 1));
                    RENT_SPINNERS[i].setToolTipText("Rent state #" + (i + 1));
                    add(RENT_SPINNERS[i]);
                }
            } else {
                throw new IllegalArgumentException("An invalid parameter was passed");
            }
        }

        /**
         * Gets the rents for this Property as stored in the JSpinners
         * @return the rents for this Property as stored in the JSpinners
         */
        public int[] getPropertyRents() {
            int[] rents = new int[RENT_SPINNERS.length];
            for (int i = 0; i < RENT_SPINNERS.length; i++) {
                rents[i] = (Integer) RENT_SPINNERS[i].getValue();
            }
            return rents;
        }
    }

    /**
     * Sets up a single Property
     * @author irswr
     */
    private static class PropertySetup extends JPanel implements ActionListener, ChangeListener {
        //PropertySetup constants
        private final JSpinner PROPERTY_COST; //Stores the Properties cost in a Spinner
        private final JSpinner PROPERTY_MORTGAGE; //Stores the Properties mortgages in a Spinner
        private final JSpinner PROPERTY_MORTGAGE_PERCENT; //Stores the percent of the Properties mortgage that should be paid as interest
        private final JSpinner PROPERTY_BUILD_PRICE; //Stores the price of building on this Property
        private final JTextField PROPERTY_COLOR_GROUP; //Stores the color group of this Property
        private final JSpinner PROPERTY_MAX_HOUSES; //Stores the maximum number of Properties that should be stored on this Property
        private final JComboBox<String> PROPERTY_IS_DICE_MULTIPLIER; //Stores whether or not this Property is a dice multiplier
        private final JComboBox<String> PROPERTY_IS_SCALED; //Stores whether or not this Property is scaled

        /**
         * Generates a panel to facilitate the setup of a single Property
         * @param name the name of the Property
         */
        public PropertySetup(String name) {
            if (name != null && name.length() > 0) {
                //Stores the name of the Property
                new JLabel(name);
                PROPERTY_COST = new JSpinner(new SpinnerNumberModel(101, 1, 1000, 1));
                PROPERTY_COST.addChangeListener(this);
                PROPERTY_COST.setToolTipText("Cost of this property");
                add(PROPERTY_COST);
                PROPERTY_MORTGAGE = new JSpinner(new SpinnerNumberModel(100, 1, 1000, 1));
                PROPERTY_MORTGAGE.addChangeListener(this);
                PROPERTY_MORTGAGE.setToolTipText("Mortgage cost of this property");
                add(PROPERTY_MORTGAGE);
                PROPERTY_MORTGAGE_PERCENT = new JSpinner(new SpinnerNumberModel(.1, 0.0, 1.0, .01));
                PROPERTY_MORTGAGE_PERCENT.addChangeListener(this);
                PROPERTY_MORTGAGE_PERCENT.setToolTipText("Percent of the mortgage that should be charged as interest for this property");
                add(PROPERTY_MORTGAGE_PERCENT);
                PROPERTY_BUILD_PRICE = new JSpinner(new SpinnerNumberModel(25, 0, 1000, 1));
                PROPERTY_COLOR_GROUP = new JTextField(20);
                PROPERTY_COLOR_GROUP.addActionListener(this);
                PROPERTY_COLOR_GROUP.setToolTipText("Color group this property belongs to. This includes utilities and railroads");
                add(PROPERTY_COLOR_GROUP);
                PROPERTY_MAX_HOUSES = new JSpinner(new SpinnerNumberModel(5, 0, 10, 1));
                PROPERTY_MAX_HOUSES.addChangeListener(this);
                PROPERTY_MAX_HOUSES.setToolTipText("Number of houses that equal a hotel on this property");
                add(PROPERTY_MAX_HOUSES);
                PROPERTY_IS_DICE_MULTIPLIER = new JComboBox<>(new String[] {"Yes", "No"});
                PROPERTY_IS_DICE_MULTIPLIER.setSelectedIndex(1);
                PROPERTY_IS_DICE_MULTIPLIER.addActionListener(this);
                PROPERTY_IS_DICE_MULTIPLIER.setToolTipText("Should the rents for this property be a multiple of the users roll?");
                add(PROPERTY_IS_DICE_MULTIPLIER);
                PROPERTY_IS_SCALED = new JComboBox<>(new String[] {"Yes", "No"});
                PROPERTY_IS_SCALED.setSelectedIndex(1);
                PROPERTY_IS_SCALED.addActionListener(this);
                PROPERTY_IS_SCALED.setToolTipText("Are the rents for this property based on number owned in the group rather than houses?");
                add(PROPERTY_IS_SCALED);
            } else {
                throw new IllegalArgumentException("A null or empty name was passed");
            }
        }

        /**
         * Invoked when an action occurs.
         *
         * @param e the event to be processed
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == PROPERTY_IS_SCALED) {
                if (PROPERTY_IS_SCALED.getSelectedIndex() == 1) {
                    PROPERTY_MAX_HOUSES.setEnabled(true);
                    PROPERTY_BUILD_PRICE.setEnabled(true);
                } else {
                    PROPERTY_MAX_HOUSES.setValue(0);
                    PROPERTY_MAX_HOUSES.setEnabled(false);
                    PROPERTY_BUILD_PRICE.setValue(0);
                    PROPERTY_BUILD_PRICE.setEnabled(false);
                }
            }
        }

        /**
         * Invoked when the target of the listener has changed its state.
         *
         * @param e a ChangeEvent object
         */
        @Override
        public void stateChanged(ChangeEvent e) {
            if (e.getSource() == PROPERTY_COST) {
                if ((Integer) PROPERTY_COST.getValue() <= (Integer) PROPERTY_MORTGAGE.getValue()) {
                    PROPERTY_COST.setValue((Integer) PROPERTY_MORTGAGE.getValue() + 1);
                }
            } else if (e.getSource() == PROPERTY_MORTGAGE) {
                if ((Integer) PROPERTY_COST.getValue() <= (Integer) PROPERTY_MORTGAGE.getValue()) {
                    PROPERTY_MORTGAGE.setValue((Integer) PROPERTY_COST.getValue() -1);
                }
            } else if (e.getSource() == PROPERTY_BUILD_PRICE) {
                if ((Integer) PROPERTY_BUILD_PRICE.getValue() > 0) {
                    PROPERTY_IS_SCALED.setSelectedIndex(1);
                    PROPERTY_IS_SCALED.setEnabled(false);
                } else {
                    PROPERTY_IS_SCALED.setEnabled(true);
                }
            } else if (e.getSource() == PROPERTY_MAX_HOUSES) {
                if ((Integer) PROPERTY_MAX_HOUSES.getValue() > 0) {
                    PROPERTY_IS_SCALED.setSelectedIndex(1);
                    PROPERTY_IS_SCALED.setEnabled(false);
                } else {
                    PROPERTY_IS_SCALED.setEnabled(true);
                }
            }
        }

        /**
         * Gets the cost of this property as stored in the propertyCost Spinner
         * @return the cost of this property as stored in the propertyCost Spinner
         */
        public int getPROPERTY_COST() {
            return (Integer) PROPERTY_COST.getValue();
        }

        /**
         * Gets the mortgage of this Property as is stored in the propertyMortgage Spinner
         * @return the mortgage of this Property as is stored in the propertyMortgage Spinner
         */
        public int getPROPERTY_MORTGAGE() {
            return (Integer) PROPERTY_MORTGAGE.getValue();
        }

        /**
         * Gets the percent of the mortgage that should be charged as interest as is stored in the propertyMortgagePercent Spinner
         * @return the percent of the mortgage that should be charged as interest as is stored in the propertyMortgagePercent Spinner
         */
        public double getPROPERTY_MORTGAGE_PERCENT() {
            return (Double) PROPERTY_MORTGAGE_PERCENT.getValue();
        }

        /**
         * Gets the price of building on the Property as is stored in the propertyBuildPrice Spinner
         * @return the price of building on the Property as is stored in the propertyBuildPrice Spinner
         */
        public int getPROPERTY_BUILD_PRICE() {
            return (Integer) PROPERTY_BUILD_PRICE.getValue();
        }

        /**
         * Gets the color group of this Space as is stored in the propertyColorGroup Label
         * @return the color group of building on this Space as is stored in the PropertyColorGroup Label
         */
        public String getPROPERTY_COLOR_GROUP() {
            return PROPERTY_COLOR_GROUP.getText();
        }

        /**
         * Returns the max number of houses that can be built on this Property as is stored in the propertyMaxHouses label
         * @return the max number of houses that can be built on this Property as is stored in the propertyMaxHouses label
         */
        public int getPROPERTY_MAX_HOUSES() {
            return (Integer) PROPERTY_MAX_HOUSES.getValue();
        }

        /**
         * Gets whether or not this Property is a dice multiplier as is stored in the propertyIsDiceMultiplier ComboBox
         * @return whether or not this Property is a dice multiplier as is stored in the propertyIsDiceMultiplier ComboBox
         */
        public boolean getPROPERTY_IS_DICE_MULTIPLIER() {
            return PROPERTY_IS_DICE_MULTIPLIER.getSelectedIndex() == 0;
        }

        /**
         * Gets whether or not this Property is scaled as is stored in the propertyIsScaled ComboBox
         * @return whether or not this Property is scaled as is stored i nthe propertyIsScaled ComboBox
         */
        public boolean getPROPERTY_IS_SCALED() {
            return PROPERTY_IS_SCALED.getSelectedIndex() == 0;
        }


    }
    /**
     * A page that manages setting up the boards Spaces
     * @author irswr
     */
    private static class SpaceSetupPage extends JPanel implements ActionListener, DocumentListener {
        //SpaceSetupPage constants
        private final ArrayList<SpaceSetup> SPACE_SETUPS; //Stores the setup for the Spaces
        private final ArrayList<JButton> REMOVE_BUTTONS; //Stores the remove buttons for each Space
        private final JTextField NAME_FIELD; //Stores the name field for input
        private final JButton ADD_BUTTON; //Stores the add button
        private final JPanel CENTER_PANEL; //Stores the panel in the center that holds the cities

        /**
         * Sets up the page that allows the user to setup spaces
         */
        public SpaceSetupPage() {
            setLayout(new BorderLayout());

            CENTER_PANEL = new JPanel();
            CENTER_PANEL.setLayout(new BoxLayout(CENTER_PANEL, BoxLayout.Y_AXIS));
            add(CENTER_PANEL, BorderLayout.CENTER);

            SPACE_SETUPS = new ArrayList<>();
            REMOVE_BUTTONS = new ArrayList<>();

            JPanel buttonPanel = new JPanel();
            NAME_FIELD = new JTextField(20);
            NAME_FIELD.getDocument().addDocumentListener(this);
            buttonPanel.add(NAME_FIELD);

            ADD_BUTTON = new JButton("Add");
            ADD_BUTTON.addActionListener(this);
            ADD_BUTTON.setEnabled(false);
            buttonPanel.add(ADD_BUTTON);
            add(buttonPanel, BorderLayout.SOUTH);
        }

        /**
         * Invoked when an action occurs.
         *
         * @param e the event to be processed
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == ADD_BUTTON) {
                SPACE_SETUPS.add(new SpaceSetup(NAME_FIELD.getText()));
                NAME_FIELD.setText("");
                JButton removeButton = new JButton("Remove");
                removeButton.addActionListener(this);
                REMOVE_BUTTONS.add(removeButton);
                SPACE_SETUPS.get(SPACE_SETUPS.size() - 1).add(removeButton);
                CENTER_PANEL.add(SPACE_SETUPS.get(SPACE_SETUPS.size() - 1));
                CENTER_PANEL.validate();
                CENTER_PANEL.repaint();
            } else if (REMOVE_BUTTONS.contains(e.getSource())) {
                int index = REMOVE_BUTTONS.indexOf(e.getSource());
                CENTER_PANEL.remove(SPACE_SETUPS.remove(index));
                REMOVE_BUTTONS.remove(index);
                CENTER_PANEL.revalidate();
                CENTER_PANEL.repaint();
            }
        }

        /**
         * Gives notification that there was an insert into the document.  The
         * range given by the DocumentEvent bounds the freshly inserted region.
         *
         * @param e the document event
         */
        @Override
        public void insertUpdate(DocumentEvent e) {
            for (SpaceSetup spaceSetup : SPACE_SETUPS) {
                if (spaceSetup.getSpaceName().equals(NAME_FIELD.getText())) {
                    ADD_BUTTON.setEnabled(false);
                    return;
                }
            }
            ADD_BUTTON.setEnabled(!NAME_FIELD.getText().equals(""));
        }

        /**
         * Gives notification that a portion of the document has been
         * removed.  The range is given in terms of what the view last
         * saw (that is, before updating sticky positions).
         *
         * @param e the document event
         */
        @Override
        public void removeUpdate(DocumentEvent e) {
            for (SpaceSetup spaceSetup : SPACE_SETUPS) {
                if (spaceSetup.getSpaceName().equals(NAME_FIELD.getText())) {
                    ADD_BUTTON.setEnabled(false);
                    return;
                }
            }
            ADD_BUTTON.setEnabled(!NAME_FIELD.getText().equals(""));
        }

        /**
         * Gives notification that an attribute or set of attributes changed.
         *
         * @param e the document event
         */
        @Override
        public void changedUpdate(DocumentEvent e) {
            for (SpaceSetup spaceSetup : SPACE_SETUPS) {
                if (spaceSetup.getSpaceName().equals(NAME_FIELD.getText())) {
                    ADD_BUTTON.setEnabled(false);
                    return;
                }
            }
            ADD_BUTTON.setEnabled(!NAME_FIELD.getText().equals(""));
        }

        /**
         * Returns whether or not the setup can continue
         * @return whether or not the setup can continue
         */
        public boolean canContinue() {
            for (SpaceSetup spaceSetup : SPACE_SETUPS) {
                if (spaceSetup.getSPACE_LOSS() >= SPACE_SETUPS.size()) {
                    return false;
                }
            }
            return SPACE_SETUPS.size() % 4 == 0 && SPACE_SETUPS.size() <= 100 && SPACE_SETUPS.size() > 0;
        }

        /**
         * Gets the names of the Spaces this contains
         * @return the names of the Spaces this contains
         */
        public String[] getSpaceNames() {
            String[] spaceNames = new String[SPACE_SETUPS.size()];
            for (int i = 0; i < spaceNames.length; i++) {
                spaceNames[i] = SPACE_SETUPS.get(i).getSpaceName();
            }
            return spaceNames;
        }

        /**
         * Gets the money gain for landing on these Space
         * @return the money gain for landing on these Space
         */
        public int[] getSpaceMoneyLosses() {
            int[] spaceMoney = new int[SPACE_SETUPS.size()];
            for (int i = 0; i < spaceMoney.length; i++) {
                spaceMoney[i] = SPACE_SETUPS.get(i).getMONEY_LOSS();
            }
            return spaceMoney;
        }

        /**
         * Gets the movement penalty for landing on these Spaces
         * @return the movement penalty for landing on these Spaces
         */
        public int[] getSpaceMovementLosses() {
            int[] spaceMovement = new int[SPACE_SETUPS.size()];
            for (int i = 0; i < spaceMovement.length; i++) {
                spaceMovement[i] = SPACE_SETUPS.get(i).getMOVEMENT_LOSS();
            }
            return spaceMovement;
        }

        /**
         * Gets the Space penalty for landing on these Spaces
         * @return the Space penalty for landing on these Spaces
         */
        public int[] getSpaceSpaceLosses() {
            int[] spaceLosses = new int[SPACE_SETUPS.size()];
            for (int i = 0; i < spaceLosses.length; i++) {
                spaceLosses[i] = SPACE_SETUPS.get(i).getSPACE_LOSS();
            }
            return spaceLosses;
        }

        /**
         * Gets the color group the Player should go to for landing on these Spaces
         * @return the color group the Player should go to for landing on these Spaces
         */
        public String[] getSpaceColorGroups() {
            String[] colorGroups = new String[SPACE_SETUPS.size()];
            for (int i = 0; i < colorGroups.length; i++) {
                colorGroups[i] = SPACE_SETUPS.get(i).getCOLOR_GROUP();
            }
            return colorGroups;
        }

        /**
         * Gets the rent multiplier the Player should pay for landing on these Spaces
         * @return the rent multiplier the Player should pay for landing on these Spaces
         */
        public double[] getSpaceRentMultipliers() {
            double[] rentMultipliers = new double[SPACE_SETUPS.size()];
            for (int i = 0; i < rentMultipliers.length; i++) {
                rentMultipliers[i] = SPACE_SETUPS.get(i).getRENT_MULTIPLIER();
            }
            return rentMultipliers;
        }

        /**
         * Gets the roll multipliers the Player should pay for landing on these Spaces
         * @return the roll multipliers the Player should pay for landing on these Spaces
         */
        public double[] getSpaceRollMultipliers() {
            double[] rollMultipliers = new double[SPACE_SETUPS.size()];
            for (int i = 0; i < rollMultipliers.length; i++) {
                rollMultipliers[i] = SPACE_SETUPS.get(i).getROLL_MULTIPLIER();
            }
            return rollMultipliers;
        }

        /**
         * Gets the cost per house the Player should pay for landing on these Spaces
         * @return the cost per house the Player should pay for landing on these Spaces
         */
        public int[] getSpacePerHouses() {
            int[] perHouse = new int[SPACE_SETUPS.size()];
            for (int i = 0; i < perHouse.length; i++) {
                perHouse[i] = SPACE_SETUPS.get(i).getPER_HOUSES();
            }
            return perHouse;
        }

        /**
         * Gets the cost per hotel the Player should pay for landing on these Spaces
         * @return the cost per hotel the Player should pay for landing on these Spaces
         */
        public int[] getSpacePerHotel() {
            int[] perHotel = new int[SPACE_SETUPS.size()];
            for (int i = 0; i < perHotel.length; i++) {
                perHotel[i] = SPACE_SETUPS.get(i).getPER_HOTEL();
            }
            return perHotel;
        }

        /**
         * Gets the deck used for landing on these Spaces
         * @return the deck used for landing on these Spaces
         */
        public int[] getSpaceDeckUsed() {
            int[] deckUsed = new int[SPACE_SETUPS.size()];
            for (int i = 0; i < deckUsed.length; i++) {
                deckUsed[i] = SPACE_SETUPS.get(i).getDECK_USED();
            }
            return deckUsed;
        }

        /**
         * Gets if these Spaces have Properties
         * @return if these Spaces have Properties
         */
        public boolean[] getSpaceHasProperty() {
            boolean[] hasProperty = new boolean[SPACE_SETUPS.size()];
            for (int i = 0; i < hasProperty.length; i++) {
                hasProperty[i] = SPACE_SETUPS.get(i).hasProperty();
            }
            return hasProperty;
        }
    }

    /**
     * Facilitates the setup of one Space
     * @author irswr
     */
    private static class SpaceSetup extends JPanel implements ActionListener, ChangeListener, DocumentListener {
        private final JLabel NAME_LABEL; //Stores the name of the Space
        private final JSpinner MONEY_LOSS; //Stores the money loss of the Space
        private final JSpinner MOVEMENT_LOSS; //Stores the number of Spaces lost
        private final JSpinner SPACE_LOSS; //Stores the index of the Space the user should go to
        private final JTextField COLOR_GROUP; //Stores the color group that the user should go to for landing on this Space
        private final JSpinner RENT_MULTIPLIER; //Stores the multiplier that the user should pay on the rent of the next Space they land on
        private final JSpinner ROLL_MULTIPLIER; //Stores the multiplier that the user should pay on a new roll rather than the rent of the next Space they land on
        private final JSpinner PER_HOUSES; //Stores the amount of money the Player should be charged per house they own
        private final JSpinner PER_HOTEL; //Stores the amount of money the Player should be charged per hotel they own
        private final JSpinner DECK_USED; //Stores the Deck used by this Space
        private final JComboBox<String> HAS_PROPERTY; //Stores whether or not this Space has a Property

        /**
         * Sets up the setup for a single Space
         * @param name the name of the Space
         * @throws IllegalArgumentException when a null or empty name is passed
         */
        public SpaceSetup(String name) {
            if (name != null && name.length() > 0) {
                NAME_LABEL = new JLabel(name);
                NAME_LABEL.setToolTipText("Name of the space");
                add(NAME_LABEL);
                MONEY_LOSS = new JSpinner(new SpinnerNumberModel(0, -1000, 1000, 1));
                MONEY_LOSS.addChangeListener(this);
                MONEY_LOSS.setToolTipText("Money gain for landing on this space. Should still be none for \"GO\" as that is handled separately");
                add(MONEY_LOSS);
                MOVEMENT_LOSS = new JSpinner(new SpinnerNumberModel(0, -40, 40, 1));
                MOVEMENT_LOSS.addChangeListener(this);
                MOVEMENT_LOSS.setToolTipText("Movement gain for landing on this space");
                add(MOVEMENT_LOSS);
                SPACE_LOSS = new JSpinner(new SpinnerNumberModel(-1, -1, 99, 1));
                SPACE_LOSS.addChangeListener(this);
                SPACE_LOSS.setToolTipText("Index -1 of the space the player should go to. Should be -1 for none");
                add(SPACE_LOSS);
                COLOR_GROUP = new JTextField(20);
                COLOR_GROUP.getDocument().addDocumentListener(this);
                COLOR_GROUP.setToolTipText("Color group the player should go to");
                add(COLOR_GROUP);
                RENT_MULTIPLIER = new JSpinner(new SpinnerNumberModel(1.0, 0.0, 10.0, 0.1));
                RENT_MULTIPLIER.setEnabled(false);
                RENT_MULTIPLIER.addChangeListener(this);
                RENT_MULTIPLIER.setToolTipText("Rent multiplier the player should pay on the space this takes them to");
                add(RENT_MULTIPLIER);
                ROLL_MULTIPLIER = new JSpinner(new SpinnerNumberModel(0.0, 0.0, 10.0, 0.1));
                ROLL_MULTIPLIER.setEnabled(false);
                ROLL_MULTIPLIER.addChangeListener(this);
                ROLL_MULTIPLIER.setToolTipText("Roll multiplier the player should pay on the space this takes them to instead of rent");
                add(ROLL_MULTIPLIER);
                PER_HOUSES = new JSpinner(new SpinnerNumberModel(0, 0, 500, 1));
                PER_HOUSES.addChangeListener(this);
                PER_HOUSES.setToolTipText("Cost per house of landing on this space");
                add(PER_HOUSES);
                PER_HOTEL = new JSpinner(new SpinnerNumberModel(0, 0, 500, 1));
                PER_HOTEL.addChangeListener(this);
                PER_HOTEL.setToolTipText("Cost per hotel of landing on this space");
                add(PER_HOTEL);
                DECK_USED = new JSpinner(new SpinnerNumberModel(-1, -1, 9, 1));
                DECK_USED.addChangeListener(this);
                DECK_USED.setToolTipText("Index -1 of the deck the user should draw from for landing on this space. Should be -1 for none");
                add(DECK_USED);
                HAS_PROPERTY = new JComboBox<>(new String[]{"Yes", "No"});
                HAS_PROPERTY.setSelectedIndex(1);
                HAS_PROPERTY.addActionListener(this);
                HAS_PROPERTY.setToolTipText("Does this space have a property?");
                add(HAS_PROPERTY);
            } else {
                throw new IllegalArgumentException("An null or empty name was passed");
            }
        }

        /**
         * Invoked when an action occurs.
         *
         * @param e the event to be processed
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == HAS_PROPERTY) {
                setAllEnabled(HAS_PROPERTY.getSelectedIndex() == 1);
                HAS_PROPERTY.setEnabled(true);
            }
        }

        /**
         * Invoked when the target of the listener has changed its state.
         *
         * @param e a ChangeEvent object
         */
        @Override
        public void stateChanged(ChangeEvent e) {
            if (e.getSource() == MONEY_LOSS) {
                if ((Integer) MONEY_LOSS.getValue() == 0) {
                    setAllEnabled(true);
                } else {
                    setAllEnabled(false);
                    MONEY_LOSS.setEnabled(true);
                }
            } else if (e.getSource() == MOVEMENT_LOSS) {
                if ((Integer) MOVEMENT_LOSS.getValue() == 0) {
                    setAllEnabled(true);
                    RENT_MULTIPLIER.setEnabled(false);
                } else {
                    setAllEnabled(false);
                    RENT_MULTIPLIER.setEnabled(true);
                    MOVEMENT_LOSS.setEnabled(true);
                }
            } else if (e.getSource() == SPACE_LOSS) {
                if ((Integer) SPACE_LOSS.getValue() == -1) {
                    setAllEnabled(true);
                    RENT_MULTIPLIER.setEnabled(false);
                } else {
                    setAllEnabled(false);
                    RENT_MULTIPLIER.setEnabled(true);
                    SPACE_LOSS.setEnabled(true);
                }
            } else if (e.getSource() == RENT_MULTIPLIER) {
                setMovementEnabled((Double) RENT_MULTIPLIER.getValue() == 1.0);
            } else if (e.getSource() == ROLL_MULTIPLIER) {
                if ((Double) ROLL_MULTIPLIER.getValue() == 0.0) {
                    setMovementEnabled(true);
                    RENT_MULTIPLIER.setEnabled(true);
                    RENT_MULTIPLIER.setValue(1.0);
                } else {
                    setMovementEnabled(false);
                    RENT_MULTIPLIER.setEnabled(false);
                    RENT_MULTIPLIER.setValue(0.0);
                }
            } else if (e.getSource() == PER_HOUSES) {
                if ((Integer) PER_HOUSES.getValue() == 0) {
                    setAllEnabled(true);
                    PER_HOTEL.setModel(new SpinnerNumberModel(0, 0, 500, 1));
                } else {
                    setAllEnabled(false);
                    PER_HOTEL.setEnabled(true);
                    PER_HOTEL.setModel(new SpinnerNumberModel(1, 1, 500, 1));
                    PER_HOUSES.setEnabled(true);
                }
            } else if (e.getSource() == PER_HOTEL) {
                if ((Integer) PER_HOTEL.getValue() == 0) {
                    setAllEnabled(true);
                    PER_HOUSES.setModel(new SpinnerNumberModel(0, 0, 500, 1));
                } else {
                    setAllEnabled(false);
                    PER_HOUSES.setEnabled(true);
                    PER_HOUSES.setModel(new SpinnerNumberModel(1, 1, 500, 1));
                    PER_HOTEL.setEnabled(true);
                }
            } else if (e.getSource() == DECK_USED) {
                setAllEnabled((Integer) DECK_USED.getValue() == -1);
                DECK_USED.setEnabled(true);
            }
        }

        /**
         * Sets all of the fields to a given status
         * @param isEnabled the status
         */
        private void setAllEnabled(boolean isEnabled) {
            COLOR_GROUP.setEnabled(isEnabled);
            HAS_PROPERTY.setEnabled(isEnabled);
            MONEY_LOSS.setEnabled(isEnabled);
            MOVEMENT_LOSS.setEnabled(isEnabled);
            SPACE_LOSS.setEnabled(isEnabled);
            PER_HOUSES.setEnabled(isEnabled);
            PER_HOTEL.setEnabled(isEnabled);
            DECK_USED.setEnabled(isEnabled);
        }

        /**
         * Sets the movement parameter that is enabled to a given state
         * @param isEnabled whether or not the movement parameter should be enabled
         */
        private void setMovementEnabled(boolean isEnabled) {
            if (COLOR_GROUP.getText() != null && !COLOR_GROUP.getText().equals("")) {
                COLOR_GROUP.setEnabled(isEnabled);
            } else if ((Integer) MOVEMENT_LOSS.getValue() != 0) {
                MOVEMENT_LOSS.setEnabled(isEnabled);
            } else if ((Integer) SPACE_LOSS.getValue() != -1) {
                SPACE_LOSS.setEnabled(isEnabled);
            }
        }

        /**
         * Gets the name stored in this Space's name field
         * @return the name stored in this Space's name field
         */
        public String getSpaceName() {
            return NAME_LABEL.getText();
        }

        /**
         * Gets the money loss stored in the moneyLoss Spinner
         * @return the money loss stored in the moneyLoss Spinner
         */
        public int getMONEY_LOSS() {
            return (Integer) MONEY_LOSS.getValue();
        }

        /**
         * Gets the movement loss stored in the movementLoss Spinner
         * @return the movement loss stored in the movementLoss Spinner
         */
        public int getMOVEMENT_LOSS() {
            return (Integer) MOVEMENT_LOSS.getValue();
        }

        /**
         * Gets the space loss stored in the spaceLoss Spinner
         * @return the space loss stored in the spaceLoss Spinner
         */
        public int getSPACE_LOSS() {
            return (Integer) SPACE_LOSS.getValue();
        }

        /**
         * Gets the color group stored in the colorGroup field
         * @return the color group stored in the colorGroup field
         */
        public String getCOLOR_GROUP() {
            return (COLOR_GROUP.getText().equals("") ? null : COLOR_GROUP.getText());
        }

        /**
         * Gets the rent multiplier stored in the rentMultiplier Spinner
         * @return the rent multiplier stored in the rentMultiplier Spinner
         */
        public double getRENT_MULTIPLIER() {
            return (Double) RENT_MULTIPLIER.getValue();
        }

        /**
         * Gets the roll multiplier stored in the rollMultiplier Spinner
         * @return the roll multiplier stored in the rollMultiplier Spinner
         */
        public double getROLL_MULTIPLIER() {
            return (Double) ROLL_MULTIPLIER.getValue();
        }

        /**
         * Gets the per house cost stored in the perHouses Spinner
         * @return the per house cost stored in the perHouse Spinner
         */
        public int getPER_HOUSES() {
            return (Integer) PER_HOUSES.getValue();
        }

        /**
         * Gets the per hotel cost stored in the perHotel Spinner
         * @return the per hotel cost stored in the perHotel Spinner
         */
        public int getPER_HOTEL() {
            return (Integer) PER_HOTEL.getValue();
        }

        /**
         * Gets the deck used stored in the deckUsed Spinner
         * @return the deck used stored in the deckUsed Spinner
         */
        public int getDECK_USED() {
            return (Integer) DECK_USED.getValue();
        }

        /**
         * Gets whether or not this Space has a Property stored in the hasProperty field
         * @return gets whether or not this Space has a Property stored in the hasProperty field
         */
        public boolean hasProperty() {
            return HAS_PROPERTY.getSelectedIndex() == 0;
        }

        /**
         * Gives notification that there was an insert into the document.  The
         * range given by the DocumentEvent bounds the freshly inserted region.
         *
         * @param e the document event
         */
        @Override
        public void insertUpdate(DocumentEvent e) {
            if (COLOR_GROUP.getText() == null || COLOR_GROUP.getText().equals("")) {
                setAllEnabled(true);
                RENT_MULTIPLIER.setEnabled(false);
            } else {
                setAllEnabled(false);
                RENT_MULTIPLIER.setEnabled(true);
                COLOR_GROUP.setEnabled(true);
                COLOR_GROUP.requestFocus();
            }
        }

        /**
         * Gives notification that a portion of the document has been
         * removed.  The range is given in terms of what the view last
         * saw (that is, before updating sticky positions).
         *
         * @param e the document event
         */
        @Override
        public void removeUpdate(DocumentEvent e) {
            if (COLOR_GROUP.getText() == null || COLOR_GROUP.getText().equals("")) {
                setAllEnabled(true);
                RENT_MULTIPLIER.setEnabled(false);
            } else {
                setAllEnabled(false);
                RENT_MULTIPLIER.setEnabled(true);
                COLOR_GROUP.setEnabled(true);
                COLOR_GROUP.requestFocus();
            }
        }

        /**
         * Gives notification that an attribute or set of attributes changed.
         *
         * @param e the document event
         */
        @Override
        public void changedUpdate(DocumentEvent e) {
            if (COLOR_GROUP.getText() == null || COLOR_GROUP.getText().equals("")) {
                setAllEnabled(true);
                RENT_MULTIPLIER.setEnabled(false);
            } else {
                setAllEnabled(false);
                RENT_MULTIPLIER.setEnabled(true);
                COLOR_GROUP.setEnabled(true);
                COLOR_GROUP.requestFocus();
            }
        }
    }

    /**
     * Facilitates the setup of the Decks
     * @author irswr
     */
    private class DecksSetupPage extends JPanel {
        //DecksSetupPage constants
        private final CardLayout CARD_LAYOUT; //Stores the Card Layout used to switch between panels
        private final int MAX_DECK_INDEX; //Stores the highest index used for the Decks
        private final JTextField[] TYPE_FIELDS; //Stores the types of the Decks
        private final String[] DECK_TYPES; //Stores the types of Decks used
        private final DeckSetupPage[] DECK_SETUP_PAGES; //Stores the deck setup pages

        //DecksSetupPage fields
        private int currentState = -1; //Stores which deck is currently being initialized

        /**
         * Initializes Decks setup
         */
        public DecksSetupPage() {
            int highestDeckUsed = 0;
            for (int i : spaceDeckUsed) {
                if (i > highestDeckUsed) {
                    highestDeckUsed = i;
                }
            }

            MAX_DECK_INDEX = highestDeckUsed;
            DECK_TYPES = new String[MAX_DECK_INDEX + 1];
            TYPE_FIELDS = new JTextField[MAX_DECK_INDEX + 1];
            DECK_SETUP_PAGES = new DeckSetupPage[MAX_DECK_INDEX + 1];

            CARD_LAYOUT = new CardLayout();
            setLayout(CARD_LAYOUT);

            JPanel initialPanel = new JPanel();
            initialPanel.setLayout(new BoxLayout(initialPanel, BoxLayout.Y_AXIS));
            for (int i = 0; i < highestDeckUsed + 1; i++) {
                TYPE_FIELDS[i] = new JTextField(20);
                initialPanel.add(TYPE_FIELDS[i]);
            }
            add(initialPanel, "initial");
            CARD_LAYOUT.show(this, "initial");
        }

        public boolean canContinue() {
            if (currentState == -1) {
                for (int i = 0; i < TYPE_FIELDS.length; i++) {
                    for (int j = 0; j < TYPE_FIELDS.length; j++) {
                        if (i != j && TYPE_FIELDS[i].getText().equals(TYPE_FIELDS[j].getText())) {
                            JOptionPane.showMessageDialog(MAIN_UI, "Invalid types");
                            return false;
                        }
                    }
                }
                for (int i = 0; i < TYPE_FIELDS.length; i++) {
                    DECK_TYPES[i] = TYPE_FIELDS[i].getText();
                }

                DECK_SETUP_PAGES[0] = new DeckSetupPage(DECK_TYPES[0]);
                add(DECK_SETUP_PAGES[0], "deck " + 0);
                CARD_LAYOUT.show(this, "deck " + 0);
                currentState++;
                return false;
            } else if (currentState < MAX_DECK_INDEX) {
                if (DECK_SETUP_PAGES[currentState].canContinue()) {
                    DECK_SETUP_PAGES[currentState + 1] = new DeckSetupPage(DECK_TYPES[currentState + 1]);
                    add(DECK_SETUP_PAGES[currentState + 1], "deck " + (currentState + 1));
                    CARD_LAYOUT.show(this, "deck" + (currentState + 1));
                    currentState++;
                } else {
                    JOptionPane.showMessageDialog(MAIN_UI, "Invalid cards");
                }
                return false;
            } else {
                if (DECK_SETUP_PAGES[currentState].canContinue()) {
                    return true;
                } else {
                    JOptionPane.showMessageDialog(MAIN_UI, "Invalid cards");
                    return false;
                }
            }
        }

        public boolean canGoBack() {
            if (currentState == -1) {
                return true;
            } else if (currentState == 0) {
                CARD_LAYOUT.show(this, "initial");
                remove(DECK_SETUP_PAGES[currentState]);
                currentState--;
                return false;
            } else {
                CARD_LAYOUT.show(this, "deck " + (currentState - 1));
                remove(DECK_SETUP_PAGES[currentState]);
                currentState--;
                return false;
            }
        }

        /**
         * Gets the names of the Cards in the Decks
         * @return the names of the Cards in the Decks
         */
        public String[][] getDeckNames() {
            String[][] names = new String[DECK_SETUP_PAGES.length][];
            for (int i = 0; i < DECK_SETUP_PAGES.length; i++) {
                names[i] = DECK_SETUP_PAGES[i].getCardNames();
            }
            return names;
        }

        /**
         * Gets the types of the Cards in the Decks
         * @return the types of the Cards in the Decks
         */
        public String[][] getDECK_TYPES() {
            String[][] types = new String[DECK_SETUP_PAGES.length][];
            for (int i = 0; i < DECK_SETUP_PAGES.length; i++) {
                types[i] = DECK_SETUP_PAGES[i].getCardTypes();
            }
            return types;
        }

        /**
         * Gets the money losses of the Cards in the Deck
         * @return the money losses of the Cards in the Deck
         */
        public int[][] getDeckMoneyLosses() {
            int[][] moneyLosses = new int[DECK_SETUP_PAGES.length][];
            for (int i = 0; i < DECK_SETUP_PAGES.length; i++) {
                moneyLosses[i] = DECK_SETUP_PAGES[i].getCardMoneyLosses();
            }
            return moneyLosses;
        }

        /**
         * Gets whether the money losses in the Decks are per Player
         * @return whether the money losses in the Decks are per Player
         */
        public boolean[][] getDeckIsPerPlayer() {
            boolean[][] perPlayer = new boolean[DECK_SETUP_PAGES.length][];
            for (int i = 0; i < DECK_SETUP_PAGES.length; i++) {
                perPlayer[i] = DECK_SETUP_PAGES[i].getCardIsPerPlayer();
            }
            return perPlayer;
        }

        /**
         * Gets the movement losses for the Cards in the Deck
         * @return the movement losses for the Cards in the Deck
         */
        public int[][] getDeckMovementLosses() {
            int[][] movementLosses = new int[DECK_SETUP_PAGES.length][];
            for (int i = 0; i < DECK_SETUP_PAGES.length; i++) {
                movementLosses[i] = DECK_SETUP_PAGES[i].getCardMovementLosses();
            }
            return movementLosses;
        }

        /**
         * Gets the Space losses for the Cards in the Deck
         * @return the Space losses for the Cards in the Deck
         */
        public int[][] getDeckSpaceLosses() {
            int[][] spaceLosses = new int[DECK_SETUP_PAGES.length][];
            for (int i = 0; i < DECK_SETUP_PAGES.length; i++) {
                spaceLosses[i] = DECK_SETUP_PAGES[i].getCardSpaceLosses();
            }
            return spaceLosses;
        }

        /**
         * Gets the color groups for the Cards in the Deck
         * @return the color groups for the Cards in the Deck
         */
        public String[][] getDeckColorGroups() {
            String[][] colorGroups = new String[DECK_SETUP_PAGES.length][];
            for (int i = 0; i < DECK_SETUP_PAGES.length; i++) {
                colorGroups[i] = DECK_SETUP_PAGES[i].getCardColorGroups();
            }
            return colorGroups;
        }

        /**
         * Gets the rent multipliers for the Cards in the Deck
         * @return the rent multipliers for the Cards in the Deck
         */
        public double[][] getDeckRentMultipliers() {
            double[][] rentMultipliers = new double[DECK_SETUP_PAGES.length][];
            for (int i = 0; i < DECK_SETUP_PAGES.length; i++) {
                rentMultipliers[i] = DECK_SETUP_PAGES[i].getCardRentMultipliers();
            }
            return rentMultipliers;
        }

        /**
         * Gets the roll multipliers for the Cards in the Deck
         * @return the roll multipliers for the Cards in the Deck
         */
        public double[][] getDeckRollMultipliers() {
            double[][] rollMultipliers = new double[DECK_SETUP_PAGES.length][];
            for (int i = 0; i < DECK_SETUP_PAGES.length; i++) {
                rollMultipliers[i] = DECK_SETUP_PAGES[i].getCardRollMultipliers();
            }
            return rollMultipliers;
        }

        /**
         * Gets the amount the Player should pay per house because of this Card
         * @return the amount the Player should pay per house because of this Card
         */
        public int[][] getDeckCostPerHouse() {
            int[][] perHouse = new int[DECK_SETUP_PAGES.length][];
            for (int i = 0; i < DECK_SETUP_PAGES.length; i++) {
                perHouse[i] = DECK_SETUP_PAGES[i].getCardPerHouses();
            }
            return perHouse;
        }

        /**
         * Gets the amount the Player should pay per hotel because of this Card
         * @return the amount the Player should pay per hotel because of this Card
         */
        public int[][] getDeckCostPerHotel() {
            int[][] perHotel = new int[DECK_SETUP_PAGES.length][];
            for (int i = 0; i < DECK_SETUP_PAGES.length; i++) {
                perHotel[i] = DECK_SETUP_PAGES[i].getCardPerHotel();
            }
            return perHotel;
        }

        /**
         * Gets whether the Cards in the Deck are get out of jail free Cards
         * @return whether the Cards in the Deck are get out of jail free Cards
         */
        public boolean[][] getDeckAreJailCard() {
            boolean[][] jailCard = new boolean[DECK_SETUP_PAGES.length][];
            for (int i = 0; i < DECK_SETUP_PAGES.length; i++) {
                jailCard[i] = DECK_SETUP_PAGES[i].getCardIsGetOutJail();
            }
            return jailCard;
        }
    }

    /**
     * A page that manages setting up a single Deck
     * @author irswr
     */
    private class DeckSetupPage extends JPanel implements ActionListener {
        //DeckSetupPage constants
        private final ArrayList<CardSetup> CARD_SETUPS; //Stores the setup for the Spaces
        private final ArrayList<JButton> REMOVE_BUTTONS; //Stores the remove buttons for each Space
        private final JTextField NAME_FIELD; //Stores the name field for input
        private final JButton ADD_BUTTON; //Stores the add button
        private final JPanel CENTER_PANEL; //Stores the panel in the center that holds the cities
        private final String TYPE; //The type of this Deck

        /**
         * Sets up the page that allows the user to setup spaces
         */
        public DeckSetupPage(String type) {
            if (type != null && type.length() > 0) {
                TYPE = type;
                setLayout(new BorderLayout());

                CENTER_PANEL = new JPanel();
                CENTER_PANEL.setLayout(new BoxLayout(CENTER_PANEL, BoxLayout.Y_AXIS));
                add(CENTER_PANEL, BorderLayout.CENTER);

                CARD_SETUPS = new ArrayList<>();
                REMOVE_BUTTONS = new ArrayList<>();

                JPanel buttonPanel = new JPanel();
                NAME_FIELD = new JTextField(20);
                buttonPanel.add(NAME_FIELD);

                ADD_BUTTON = new JButton("Add");
                ADD_BUTTON.addActionListener(this);
                ADD_BUTTON.setEnabled(false);
                buttonPanel.add(ADD_BUTTON);
                add(buttonPanel, BorderLayout.SOUTH);
            } else {
                throw new IllegalArgumentException("A null type was passed");
            }
        }

        /**
         * Invoked when an action occurs.
         *
         * @param e the event to be processed
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == ADD_BUTTON) {
                CARD_SETUPS.add(new CardSetup(NAME_FIELD.getText(), TYPE));
                NAME_FIELD.setText("");
                JButton removeButton = new JButton("Remove");
                removeButton.addActionListener(this);
                REMOVE_BUTTONS.add(removeButton);
                CARD_SETUPS.get(CARD_SETUPS.size() - 1).add(removeButton);
                CENTER_PANEL.add(CARD_SETUPS.get(CARD_SETUPS.size() - 1));
                CENTER_PANEL.validate();
                CENTER_PANEL.repaint();
            } else if (REMOVE_BUTTONS.contains(e.getSource())) {
                int index = REMOVE_BUTTONS.indexOf(e.getSource());
                CENTER_PANEL.remove(CARD_SETUPS.remove(index));
                REMOVE_BUTTONS.remove(index);
                CENTER_PANEL.revalidate();
                CENTER_PANEL.repaint();
            }
        }

        /**
         * Returns whether or not the setup can continue
         * @return whether or not the setup can continue
         */
        public boolean canContinue() {
            for (CardSetup cardSetup : CARD_SETUPS) {
                if (cardSetup.getSPACE_LOSS() >= spaceNames.length) {
                    return false;
                }
            }
            return CARD_SETUPS.size() > 0;
        }

        /**
         * Gets the names of the Cards this contains
         * @return the names of the Spaces this contains
         */
        public String[] getCardNames() {
            String[] cardNames = new String[CARD_SETUPS.size()];
            for (int i = 0; i < cardNames.length; i++) {
                cardNames[i] = CARD_SETUPS.get(i).getCardName();
            }
            return cardNames;
        }

        public String[] getCardTypes() {
            String[] cardTypes = new String[CARD_SETUPS.size()];
            for (int i = 0; i < cardTypes.length; i++) {
                cardTypes[i] = CARD_SETUPS.get(i).getCardType();
            }
            return cardTypes;
        }

        /**
         * Gets the money gain for landing on these Cards
         * @return the money gain for landing on these Cards
         */
        public int[] getCardMoneyLosses() {
            int[] cardMoney = new int[CARD_SETUPS.size()];
            for (int i = 0; i < cardMoney.length; i++) {
                cardMoney[i] = CARD_SETUPS.get(i).getMONEY_LOSSES();
            }
            return cardMoney;
        }

        public boolean[] getCardIsPerPlayer() {
            boolean[] cardPerPlayer = new boolean[CARD_SETUPS.size()];
            for (int i = 0; i < cardPerPlayer.length; i++) {
                cardPerPlayer[i] = CARD_SETUPS.get(i).getPerPlayer();
            }
            return cardPerPlayer;
        }

        /**
         * Gets the movement penalty for landing on these Cards
         * @return the movement penalty for landing on these Cards
         */
        public int[] getCardMovementLosses() {
            int[] cardMovement = new int[CARD_SETUPS.size()];
            for (int i = 0; i < cardMovement.length; i++) {
                cardMovement[i] = CARD_SETUPS.get(i).getMOVEMENT_LOSS();
            }
            return cardMovement;
        }

        /**
         * Gets the Space penalty for landing on these Cards
         * @return the Space penalty for landing on these Cards
         */
        public int[] getCardSpaceLosses() {
            int[] spaceLosses = new int[CARD_SETUPS.size()];
            for (int i = 0; i < spaceLosses.length; i++) {
                spaceLosses[i] = CARD_SETUPS.get(i).getSPACE_LOSS();
            }
            return spaceLosses;
        }

        /**
         * Gets the color group the Player should go to for landing on these Cards
         * @return the color group the Player should go to for landing on these Cards
         */
        public String[] getCardColorGroups() {
            String[] colorGroups = new String[CARD_SETUPS.size()];
            for (int i = 0; i < colorGroups.length; i++) {
                colorGroups[i] = CARD_SETUPS.get(i).getCOLOR_GROUP();
            }
            return colorGroups;
        }

        /**
         * Gets the rent multiplier the Player should pay for landing on these Cards
         * @return the rent multiplier the Player should pay for landing on these Cards
         */
        public double[] getCardRentMultipliers() {
            double[] rentMultipliers = new double[CARD_SETUPS.size()];
            for (int i = 0; i < rentMultipliers.length; i++) {
                rentMultipliers[i] = CARD_SETUPS.get(i).getRENT_MULTIPLIER();
            }
            return rentMultipliers;
        }

        /**
         * Gets the roll multipliers the Player should pay for landing on these Cards
         * @return the roll multipliers the Player should pay for landing on these Cards
         */
        public double[] getCardRollMultipliers() {
            double[] rollMultipliers = new double[CARD_SETUPS.size()];
            for (int i = 0; i < rollMultipliers.length; i++) {
                rollMultipliers[i] = CARD_SETUPS.get(i).getROLL_MULTIPLIER();
            }
            return rollMultipliers;
        }

        /**
         * Gets the cost per house the Player should pay for landing on these Cards
         * @return the cost per house the Player should pay for landing on these Cards
         */
        public int[] getCardPerHouses() {
            int[] perHouse = new int[CARD_SETUPS.size()];
            for (int i = 0; i < perHouse.length; i++) {
                perHouse[i] = CARD_SETUPS.get(i).getPER_HOUSES();
            }
            return perHouse;
        }

        /**
         * Gets the cost per hotel the Player should pay for landing on these Cards
         * @return the cost per hotel the Player should pay for landing on these Cards
         */
        public int[] getCardPerHotel() {
            int[] perHotel = new int[CARD_SETUPS.size()];
            for (int i = 0; i < perHotel.length; i++) {
                perHotel[i] = CARD_SETUPS.get(i).getPER_HOTEL();
            }
            return perHotel;
        }

        /**
         * Gets whether or not these Cards are get out of jail Cards
         * @return whether or not these Cards are get out jail Cards
         */
        public boolean[] getCardIsGetOutJail() {
            boolean[] getOutJail = new boolean[CARD_SETUPS.size()];
            for (int i = 0; i < getOutJail.length; i++) {
                getOutJail[i] = CARD_SETUPS.get(i).getIS_JAIL_CARD();
            }
            return getOutJail;
        }
    }

    /**
     * Facilitates the setup of one Card
     * @author irswr
     */
    private static class CardSetup extends JPanel implements ActionListener, ChangeListener, DocumentListener {
        //CardSetup constants
        private final JLabel NAME_LABEL; //Stores the name of the Card
        private final JLabel TYPE_LABEL; //Stores the type of the Card
        private final JSpinner MONEY_LOSSES; //Stores the money loss of the Card
        private final JComboBox<String> IS_PER_PLAYER; //Stores whether or not the money penalty is per player
        private final JSpinner MOVEMENT_LOSS; //Stores the number of Card lost
        private final JSpinner SPACE_LOSS; //Stores the index of the Space the user should go to
        private final JTextField COLOR_GROUP; //Stores the color group that the user should go to for landing on this Card
        private final JSpinner RENT_MULTIPLIER; //Stores the multiplier that the user should pay on the rent of the next Space they land on
        private final JSpinner ROLL_MULTIPLIER; //Stores the multiplier that the user should pay on a new roll rather than the rent of the next Space they land on
        private final JSpinner PER_HOUSES; //Stores the amount of money the Player should be charged per house they own
        private final JSpinner PER_HOTEL; //Stores the amount of money the Player should be charged per hotel they own
        private final JComboBox<String> IS_JAIL_CARD; //Stores whether or not this Space has a Property

        /**
         * Sets up the setup for a single Card
         * @param name the name of the Card
         * @throws IllegalArgumentException when a null or empty name is passed
         */
        public CardSetup(String name, String type) {
            if (name != null && name.length() > 0 && type != null && type.length() > 0) {
                NAME_LABEL = new JLabel(name);
                NAME_LABEL.setToolTipText("Card description");
                add(NAME_LABEL);
                TYPE_LABEL = new JLabel(type);
                TYPE_LABEL.setToolTipText("Card type");
                add(TYPE_LABEL);
                MONEY_LOSSES = new JSpinner(new SpinnerNumberModel(0, -1000, 1000, 1));
                MONEY_LOSSES.addChangeListener(this);
                MONEY_LOSSES.setToolTipText("Card money gain");
                add(MONEY_LOSSES);
                IS_PER_PLAYER = new JComboBox<>(new String[] {"Yes", "No"});
                IS_PER_PLAYER.setSelectedIndex(1);
                IS_PER_PLAYER.setEnabled(false);
                IS_PER_PLAYER.setToolTipText("Is money gain applied to every other player?");
                add(IS_PER_PLAYER);
                MOVEMENT_LOSS = new JSpinner(new SpinnerNumberModel(0, -40, 40, 1));
                MOVEMENT_LOSS.addChangeListener(this);
                MOVEMENT_LOSS.setToolTipText("Card movement gain");
                add(MOVEMENT_LOSS);
                SPACE_LOSS = new JSpinner(new SpinnerNumberModel(-1, -1, 99, 1));
                SPACE_LOSS.addChangeListener(this);
                SPACE_LOSS.setToolTipText("Index - 1 of the space the player should go to. Should be -1 for none");
                add(SPACE_LOSS);
                COLOR_GROUP = new JTextField(20);
                COLOR_GROUP.getDocument().addDocumentListener(this);
                COLOR_GROUP.setToolTipText("Color group the player should go to");
                add(COLOR_GROUP);
                RENT_MULTIPLIER = new JSpinner(new SpinnerNumberModel(1.0, 0.0, 10.0, 0.1));
                RENT_MULTIPLIER.setEnabled(false);
                RENT_MULTIPLIER.addChangeListener(this);
                RENT_MULTIPLIER.setToolTipText("Rent multiplier that the player should pay on the space this moves them to");
                add(RENT_MULTIPLIER);
                ROLL_MULTIPLIER = new JSpinner(new SpinnerNumberModel(0.0, 0.0, 10.0, 0.1));
                ROLL_MULTIPLIER.setEnabled(false);
                ROLL_MULTIPLIER.addChangeListener(this);
                ROLL_MULTIPLIER.setToolTipText("Roll multiplier the player should pay instead of rent on the next space they land on");
                add(ROLL_MULTIPLIER);
                PER_HOUSES = new JSpinner(new SpinnerNumberModel(0, 0, 500, 1));
                PER_HOUSES.addChangeListener(this);
                PER_HOUSES.setToolTipText("Cost per house the player should pay");
                add(PER_HOUSES);
                PER_HOTEL = new JSpinner(new SpinnerNumberModel(0, 0, 500, 1));
                PER_HOTEL.addChangeListener(this);
                PER_HOTEL.setToolTipText("Cost per hotel the player should pay");
                add(PER_HOTEL);
                IS_JAIL_CARD = new JComboBox<>(new String[]{"Yes", "No"});
                IS_JAIL_CARD.setSelectedIndex(1);
                IS_JAIL_CARD.addActionListener(this);
                IS_JAIL_CARD.setToolTipText("Is this a get out of jail free card?");
                add(IS_JAIL_CARD);
            } else {
                throw new IllegalArgumentException("An null or empty name was passed");
            }
        }

        /**
         * Invoked when an action occurs.
         *
         * @param e the event to be processed
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == IS_JAIL_CARD) {
                setAllEnabled(IS_JAIL_CARD.getSelectedIndex() == 1);
                IS_JAIL_CARD.setEnabled(true);
            }
        }

        /**
         * Invoked when the target of the listener has changed its state.
         *
         * @param e a ChangeEvent object
         */
        @Override
        public void stateChanged(ChangeEvent e) {
            if (e.getSource() == MONEY_LOSSES) {
                if ((Integer) MONEY_LOSSES.getValue() == 0) {
                    if (IS_PER_PLAYER.getSelectedIndex() == 1) {
                        setAllEnabled(true);
                        MONEY_LOSSES.setEnabled(false);
                    } else {
                        MONEY_LOSSES.setValue(1);
                    }
                } else {
                    setAllEnabled(false);
                    IS_PER_PLAYER.setEnabled(true);
                    MONEY_LOSSES.setEnabled(true);
                }
            } else if (e.getSource() == MOVEMENT_LOSS) {
                if ((Integer) MOVEMENT_LOSS.getValue() == 0) {
                    setAllEnabled(true);
                    RENT_MULTIPLIER.setEnabled(false);
                } else {
                    setAllEnabled(false);
                    RENT_MULTIPLIER.setEnabled(true);
                    MOVEMENT_LOSS.setEnabled(true);
                }
            } else if (e.getSource() == SPACE_LOSS) {
                if ((Integer) SPACE_LOSS.getValue() == -1) {
                    setAllEnabled(true);
                    RENT_MULTIPLIER.setEnabled(false);
                } else {
                    setAllEnabled(false);
                    RENT_MULTIPLIER.setEnabled(true);
                    SPACE_LOSS.setEnabled(true);
                }
            } else if (e.getSource() == RENT_MULTIPLIER) {
                setMovementEnabled((Double) RENT_MULTIPLIER.getValue() == 1.0);
            } else if (e.getSource() == ROLL_MULTIPLIER) {
                if ((Double) ROLL_MULTIPLIER.getValue() == 0.0) {
                    setMovementEnabled(true);
                    RENT_MULTIPLIER.setEnabled(true);
                    RENT_MULTIPLIER.setValue(1.0);
                } else {
                    setMovementEnabled(false);
                    RENT_MULTIPLIER.setEnabled(false);
                    RENT_MULTIPLIER.setValue(0.0);
                }
            } else if (e.getSource() == PER_HOUSES) {
                if ((Integer) PER_HOUSES.getValue() == 0) {
                    setAllEnabled(true);
                    PER_HOTEL.setModel(new SpinnerNumberModel(0, 0, 500, 1));
                } else {
                    setAllEnabled(false);
                    PER_HOTEL.setEnabled(true);
                    PER_HOTEL.setModel(new SpinnerNumberModel(1, 1, 500, 1));
                    PER_HOUSES.setEnabled(true);
                }
            } else if (e.getSource() == PER_HOTEL) {
                if ((Integer) PER_HOTEL.getValue() == 0) {
                    setAllEnabled(true);
                    PER_HOUSES.setModel(new SpinnerNumberModel(0, 0, 500, 1));
                } else {
                    setAllEnabled(false);
                    PER_HOUSES.setEnabled(true);
                    PER_HOUSES.setModel(new SpinnerNumberModel(1, 1, 500, 1));
                    PER_HOTEL.setEnabled(true);
                }
            }
        }

        /**
         * Sets all of the fields to a given status
         * @param isEnabled the status
         */
        private void setAllEnabled(boolean isEnabled) {
            COLOR_GROUP.setEnabled(isEnabled);
            IS_JAIL_CARD.setEnabled(isEnabled);
            MONEY_LOSSES.setEnabled(isEnabled);
            MOVEMENT_LOSS.setEnabled(isEnabled);
            SPACE_LOSS.setEnabled(isEnabled);
            PER_HOUSES.setEnabled(isEnabled);
            PER_HOTEL.setEnabled(isEnabled);
        }

        /**
         * Sets the movement parameter that is enabled to a given state
         * @param isEnabled whether or not the movement parameter should be enabled
         */
        private void setMovementEnabled(boolean isEnabled) {
            if (COLOR_GROUP.getText() != null && !COLOR_GROUP.getText().equals("")) {
                COLOR_GROUP.setEnabled(isEnabled);
            } else if ((Integer) MOVEMENT_LOSS.getValue() != 0) {
                MOVEMENT_LOSS.setEnabled(isEnabled);
            } else if ((Integer) SPACE_LOSS.getValue() != -1) {
                SPACE_LOSS.setEnabled(isEnabled);
            }
        }

        /**
         * Gets the name stored in this Card's name field
         * @return the name stored in this Card's name field
         */
        public String getCardName() {
            return NAME_LABEL.getText();
        }

        /**
         * Gets the type stored in the Card's type field
         * @return Gets the type stored in the Card's type field
         */
        public String getCardType() {
            return TYPE_LABEL.getText();
        }

        /**
         * Gets the money loss stored in the moneyLoss Spinner
         * @return the money loss stored in the moneyLoss Spinner
         */
        public int getMONEY_LOSSES() {
            return (Integer) MONEY_LOSSES.getValue();
        }

        /**
         * Gets whether the money loss is per player as stored in the isPerPlayer ComboBox
         * @return whether the money loss is per player as is stored in the isPerPlayer ComboBox
         */
        public boolean getPerPlayer() {
            return IS_PER_PLAYER.getSelectedIndex() == 0;
        }

        /**
         * Gets the movement loss stored in the movementLoss Spinner
         * @return the movement loss stored in the movementLoss Spinner
         */
        public int getMOVEMENT_LOSS() {
            return (Integer) MOVEMENT_LOSS.getValue();
        }

        /**
         * Gets the space loss stored in the spaceLoss Spinner
         * @return the space loss stored in the spaceLoss Spinner
         */
        public int getSPACE_LOSS() {
            return (Integer) SPACE_LOSS.getValue();
        }

        /**
         * Gets the color group stored in the colorGroup field
         * @return the color group stored in the colorGroup field
         */
        public String getCOLOR_GROUP() {
            return (COLOR_GROUP.getText().equals("") ? null : COLOR_GROUP.getText());
        }

        /**
         * Gets the rent multiplier stored in the rentMultiplier Spinner
         * @return the rent multiplier stored in the rentMultiplier Spinner
         */
        public double getRENT_MULTIPLIER() {
            return (Double) RENT_MULTIPLIER.getValue();
        }

        /**
         * Gets the roll multiplier stored in the rollMultiplier Spinner
         * @return the roll multiplier stored in the rollMultiplier Spinner
         */
        public double getROLL_MULTIPLIER() {
            return (Double) ROLL_MULTIPLIER.getValue();
        }

        /**
         * Gets the per house cost stored in the perHouses Spinner
         * @return the per house cost stored in the perHouse Spinner
         */
        public int getPER_HOUSES() {
            return (Integer) PER_HOUSES.getValue();
        }

        /**
         * Gets the per hotel cost stored in the perHotel Spinner
         * @return the per hotel cost stored in the perHotel Spinner
         */
        public int getPER_HOTEL() {
            return (Integer) PER_HOTEL.getValue();
        }

        /**
         * Gets whether or not this Space has a Property stored in the hasProperty field
         * @return gets whether or not this Space has a Property stored in the hasProperty field
         */
        public boolean getIS_JAIL_CARD() {
            return IS_JAIL_CARD.getSelectedIndex() == 0;
        }

        /**
         * Gives notification that there was an insert into the document.  The
         * range given by the DocumentEvent bounds the freshly inserted region.
         *
         * @param e the document event
         */
        @Override
        public void insertUpdate(DocumentEvent e) {
            if (COLOR_GROUP.getText() == null || COLOR_GROUP.getText().equals("")) {
                setAllEnabled(true);
                RENT_MULTIPLIER.setEnabled(false);
            } else {
                setAllEnabled(false);
                RENT_MULTIPLIER.setEnabled(true);
                COLOR_GROUP.setEnabled(true);
                COLOR_GROUP.requestFocus();
            }
        }

        /**
         * Gives notification that a portion of the document has been
         * removed.  The range is given in terms of what the view last
         * saw (that is, before updating sticky positions).
         *
         * @param e the document event
         */
        @Override
        public void removeUpdate(DocumentEvent e) {
            if (COLOR_GROUP.getText() == null || COLOR_GROUP.getText().equals("")) {
                setAllEnabled(true);
                RENT_MULTIPLIER.setEnabled(false);
            } else {
                setAllEnabled(false);
                RENT_MULTIPLIER.setEnabled(true);
                COLOR_GROUP.setEnabled(true);
                COLOR_GROUP.requestFocus();
            }
        }

        /**
         * Gives notification that an attribute or set of attributes changed.
         *
         * @param e the document event
         */
        @Override
        public void changedUpdate(DocumentEvent e) {
            if (COLOR_GROUP.getText() == null || COLOR_GROUP.getText().equals("")) {
                setAllEnabled(true);
                RENT_MULTIPLIER.setEnabled(false);
            } else {
                setAllEnabled(false);
                RENT_MULTIPLIER.setEnabled(true);
                COLOR_GROUP.setEnabled(true);
                COLOR_GROUP.requestFocus();
            }
        }
    }
}
