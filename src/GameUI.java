import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.font.LineBreakMeasurer;
import java.awt.font.TextLayout;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.text.AttributedCharacterIterator;
import java.text.AttributedString;
import java.util.ArrayList;
import java.util.Arrays;

public class GameUI extends JPanel implements ActionListener {
    private final MainUI MAIN_UI; //Stores the main UI that created this
    private final Game GAME; //Stores the Game Object
    private final GameGraphics GAME_GRAPHICS; //Stores the Graphics for the Game
    private final JButton[] BUTTONS; //Stores the buttons for the menu
    private final JLabel CURRENT_PLAYER; //A button that simply displays the current player
    private final JLabel WALLET; //A button that simply displays the Players Wallet

    public GameUI(String[] playerNames, String[] playerTypes, Color[] playerColors, MainUI mainUI) {
        if (mainUI != null) {
            GAME = GameCreator.makeGame(playerNames, playerTypes, playerColors, this);
            GAME_GRAPHICS = new GameGraphics(GAME);
            setLayout(new BorderLayout());
            add(GAME_GRAPHICS, BorderLayout.CENTER);

            JPanel buttonPanel = new JPanel();
            BUTTONS = new JButton[7];
            BUTTONS[0] = new JButton("End Turn");
            BUTTONS[0].addActionListener(this);
            buttonPanel.add(BUTTONS[0]);
            BUTTONS[1] = new JButton("Show All Properties");
            BUTTONS[1].addActionListener(this);
            buttonPanel.add(BUTTONS[1]);
            BUTTONS[2] = new JButton("Show Available Properties");
            BUTTONS[2].addActionListener(this);
            buttonPanel.add(BUTTONS[2]);
            BUTTONS[3] = new JButton("Show My Properties");
            BUTTONS[3].addActionListener(this);
            buttonPanel.add(BUTTONS[3]);
            BUTTONS[4] = new JButton("Show My Cards");
            BUTTONS[4].addActionListener(this);
            buttonPanel.add(BUTTONS[4]);
            BUTTONS[5] = new JButton("Show All Players");
            BUTTONS[5].addActionListener(this);
            buttonPanel.add(BUTTONS[5]);
            BUTTONS[6] = new JButton("Quit");
            BUTTONS[6].addActionListener(this);
            CURRENT_PLAYER = new JLabel(GAME.getCurrentPlayer().toString());
            CURRENT_PLAYER.setEnabled(false);
            buttonPanel.add(CURRENT_PLAYER);
            WALLET = new JLabel("$" + GAME.getCurrentPlayer().getWallet());
            WALLET.setEnabled(false);
            buttonPanel.add(WALLET);
            buttonPanel.add(BUTTONS[6]);
            add(buttonPanel, BorderLayout.SOUTH);

            MAIN_UI = mainUI;
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
        if (e.getSource() == BUTTONS[0]) {
            GAME.doTurn();
        } else if (e.getSource() == BUTTONS[1]) {
            new PropertySelectionDialog(Game.getProperties(GAME.getGAME_BOARD()));
        } else if (e.getSource() == BUTTONS[2]) {
            new PropertySelectionDialog(Game.getAvailableProperties(GAME.getGAME_BOARD()));
        } else if (e.getSource() == BUTTONS[3]) {
            new PropertySelectionDialog(Game.playerProperties(GAME.getGAME_BOARD(),
                    GAME.getCurrentPlayer()));
        } else if (e.getSource() == BUTTONS[4]) {
            new CardSelectionDialog(Game.getOwnedCards(GAME.getCurrentPlayer(),
                    GAME.getDECKS()));
        } else if (e.getSource() == BUTTONS[5]) {
            new PlayerSelectionDialog(GAME.getPLAYERS());
        } else if (e.getSource() == BUTTONS[6]) {
            int n = JOptionPane.showConfirmDialog(MAIN_UI, "Are you sure you want to quit?",
                    "Are you sure you want to quit?", JOptionPane.YES_NO_OPTION);
            if (n == JOptionPane.YES_OPTION) {
                System.exit(0);
            }
        }
    }

    /**
     * Prompts the user for a boolean
     *
     * @param description the description that should be shown to the user
     * @param object      a potential object that may be displayed as part of the message
     * @return the users decision
     * @throws IllegalArgumentException when a null description is passed
     */
    public <T> boolean booleanDialog(String description, T object) {
        if (description != null) {
            if (object instanceof Property) {
                JPanel panel = new JPanel();
                panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
                panel.add(new JLabel(description));
                JPanel viewer = new PropertyViewer((Property) object);
                viewer.setPreferredSize(new Dimension(400, 800));
                panel.add(viewer);
                return JOptionPane.showConfirmDialog(MAIN_UI, panel, description, JOptionPane.YES_NO_OPTION,
                        JOptionPane.PLAIN_MESSAGE) == JOptionPane.YES_OPTION;
            } else if (object instanceof Player) {
                JPanel panel = new JPanel();
                panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
                panel.add(new JLabel(description));
                JPanel viewer = new PlayerViewer((Player) object);
                viewer.setPreferredSize(new Dimension(200, 100));
                panel.add(viewer);
                return JOptionPane.showConfirmDialog(MAIN_UI, panel, description, JOptionPane.YES_NO_OPTION,
                        JOptionPane.PLAIN_MESSAGE) == JOptionPane.YES_OPTION;
            } else {
                return JOptionPane.showConfirmDialog(MAIN_UI, description, description, JOptionPane.YES_NO_OPTION,
                        JOptionPane.PLAIN_MESSAGE) == JOptionPane.YES_OPTION;
            }
        } else {
            throw new IllegalArgumentException("A null description was passed");
        }
    }

    /**
     * Prompts the user for an integer
     *
     * @param description the description that should be shown to the user
     * @param object      the Array the user is being prompted with
     * @return the integer the user choose
     * @throws IllegalArgumentException when a null description is passed
     */
    public <T> int intDialog(String description, T object) {
        if (description != null) {
            if (object != null && object.getClass() == Trade.class) {
                JPanel panel = new JPanel();
                panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
                panel.add(new JLabel(description));
                JPanel viewer = new TradeViewer((Trade) object);
                viewer.setPreferredSize(new Dimension(400, 200));
                panel.add(viewer);
                int result;
                String o;
                while (true) {
                    o = JOptionPane.showInputDialog(MAIN_UI, panel, description);

                    try {
                        result = Integer.parseInt(o);
                        break;
                    } catch (NumberFormatException ignored) {

                    }
                }
                return result;
            } else if (object != null && object.getClass() == Auction.class) {
                JPanel panel = new JPanel();
                panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
                panel.add(new JLabel(description));
                JPanel viewer = new PropertyViewer(((Auction) object).getPROPERTY());
                viewer.setPreferredSize(new Dimension(400, 800));
                panel.add(viewer);
                int result;
                String o;
                while (true) {
                    o = JOptionPane.showInputDialog(MAIN_UI, panel, description);

                    try {
                        result = Integer.parseInt(o);
                        break;
                    } catch (NumberFormatException ignored) {

                    }
                }
                return result;
            } else {
                int result;
                String o;
                while (true) {
                    o = JOptionPane.showInputDialog(MAIN_UI, description, description);

                    try {
                        result = Integer.parseInt(o);
                        break;
                    } catch (NumberFormatException ignored) {

                    }
                }
                return result;
            }
        } else {
            throw new IllegalArgumentException("A null description was passed");
        }
    }

    /**
     * Prompts the user to pick an object out of an Array (or none)
     *
     * @param description the description that should be shown to the user
     * @param objects     the objects the user an pick from
     * @param extra       an extra that can be shown to the user
     * @return the index of the picked Object
     * @throws IllegalArgumentException when a null description or Array is passed
     */
    public <T, S> int arrayDialog(String description, T[] objects, S extra) {
        if (description != null && objects != null) {
            JPanel panel = new JPanel();
            if (extra instanceof Trade) {
                panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
                panel.add(new JFrame(description));
                JPanel viewer = new TradeViewer((Trade) extra);
                viewer.setPreferredSize(new Dimension(400, 200));
                panel.add(viewer);
            }

            ArrayList<Object> temp = new ArrayList<>(Arrays.asList(objects));
            temp.add("None");
            Object[] options = temp.toArray(new Object[0]);

            JButton button = new JButton("Launch Viewer");
            if (objects[0] instanceof Property) {
                button.addActionListener(e -> {
                    Property[] properties = new Property[objects.length];
                    for (int i = 0; i < properties.length; i++) {
                        if (objects[i] instanceof Property) {
                            properties[i] = (Property) objects[i];
                        } else {
                            throw new IllegalArgumentException("A mismatched Array was passed");
                        }
                    }
                    new PropertySelectionDialog(properties);
                });

            } else if (objects[0] instanceof Card) {
                button.addActionListener(e -> {
                    Card[] cards = new Card[objects.length];
                    for (int i = 0; i < cards.length; i++) {
                        if (objects[i] instanceof Card) {
                            cards[i] = (Card) objects[i];
                        } else {
                            throw new IllegalArgumentException("A mismatched Array was passed");
                        }
                    }
                    new CardSelectionDialog(cards);
                });
            } else if (objects[0] instanceof Player) {
                button.addActionListener(e -> {
                    Player[] players = new Player[objects.length];
                    for (int i = 0; i < players.length; i++) {
                        if (objects[i] instanceof Player) {
                            players[i] = (Player) objects[i];
                        } else {
                            throw new IllegalArgumentException("A mismatched Array was passed");
                        }
                    }
                    new PlayerSelectionDialog(players);
                });
            } else {
                throw new IllegalArgumentException("An invalid Array type was passed");
            }
            panel.add(button);

            Object o = JOptionPane.showInputDialog(MAIN_UI, panel, description, JOptionPane.PLAIN_MESSAGE, null,
                    options, options[options.length - 1]);
            temp.removeIf(o1 -> (o1 instanceof String));
            if (temp.contains(o)) {
                return temp.indexOf(o);
            } else {
                return -1;
            }

        } else {
            throw new IllegalArgumentException("A null parameter was passed");
        }
    }


    /**
     * Updates the board
     */
    public void update() {
        CURRENT_PLAYER.setText(GAME.getCurrentPlayer().toString());
        WALLET.setText("$" + GAME.getCurrentPlayer().getWallet());
        GAME_GRAPHICS.repaint();
    }

    /**
     * Shows a message to the user
     *
     * @param message the message that should be displayed
     * @throws IllegalArgumentException when a null message is passed
     */
    public void displayMessage(String message) {
        if (message != null) {
            JOptionPane.showMessageDialog(MAIN_UI, message);
        } else {
            throw new IllegalArgumentException("A null message was passed");
        }
    }

    /**
     * Shows a Card to the user
     *
     * @param card        the card that should be displayed
     * @param description the description that should be shown to the user, if any
     * @throws IllegalArgumentException when a null Card is passed
     */
    public void displayCard(Card card, String description) {
        if (card != null) {
            JDialog dialog = new JDialog(MAIN_UI, card.toString(), true);
            JPanel panel = new JPanel(new BorderLayout());
            if (description != null) {
                panel.add(new JLabel(description), BorderLayout.NORTH);
            }
            panel.add(new CardViewer(card), BorderLayout.CENTER);
            JButton button = new JButton("OK");
            button.addActionListener(e -> dialog.setVisible(false));
            panel.add(button, BorderLayout.SOUTH);
            dialog.setContentPane(panel);
            dialog.setBounds(100, 100, 400, 200);
            dialog.setVisible(true);
        } else {
            throw new IllegalArgumentException("A null Card was passed");
        }
    }

    /**
     * Shows a Property to the user
     *
     * @param property    the Property that should be displayed
     * @param description the description that should be shown to the user, if any
     * @throws IllegalArgumentException when a null Property is passed
     */
    public void displayProperty(Property property, String description) {
        if (property != null) {
            JDialog dialog = new JDialog(MAIN_UI, property.toString(), true);
            JPanel panel = new JPanel(new BorderLayout());
            if (description != null) {
                panel.add(new JLabel(description), BorderLayout.NORTH);
            }
            panel.add(new PropertyViewer(property), BorderLayout.CENTER);
            JButton button = new JButton("OK");
            button.addActionListener(e -> dialog.setVisible(false));
            panel.add(button, BorderLayout.SOUTH);
            dialog.setContentPane(panel);
            dialog.setBounds(100, 100, 400, 800);
            dialog.setVisible(true);
        } else {
            throw new IllegalArgumentException("A null Property was passed");
        }
    }

    /**
     * Shows a Player to the user
     *
     * @param player      the Property that should be displayed
     * @param description the description that should be shown to the user, if any
     * @throws IllegalArgumentException when a null Property is passed
     */
    public void displayPlayer(Player player, String description) {
        if (player != null) {
            JDialog dialog = new JDialog(MAIN_UI, player.toString(), true);
            JPanel panel = new JPanel(new BorderLayout());
            if (description != null) {
                panel.add(new JLabel(description), BorderLayout.NORTH);
            }
            panel.add(new PlayerViewer(player), BorderLayout.CENTER);
            JButton button = new JButton("OK");
            button.addActionListener(e -> dialog.setVisible(false));
            panel.add(button, BorderLayout.SOUTH);
            dialog.setContentPane(panel);
            dialog.setBounds(100, 100, 200, 100);
            dialog.setVisible(true);
        } else {
            throw new IllegalArgumentException("A null Property was passed");
        }
    }

    /**
     * Allows the user to view a Player
     *
     * @author irswr
     */
    private class PlayerSelectionDialog extends JDialog implements ActionListener, ListSelectionListener {
        private final JList<String> PLAYER_LIST; //Stores the String representation of the Players in the list
        private final Player[] PLAYERS; //Stores the Players that the user can view
        private final JButton[] BUTTONS; //Stores the buttons that allow the user to interact with the dialog

        /**
         * Creates a dialog that allows the user to view a Player
         *
         * @param players the Players the user can view
         * @throws IllegalArgumentException when a null Players Array is passed
         */
        public PlayerSelectionDialog(Player[] players) {
            super(MAIN_UI, "Player Viewer", true);
            if (players != null) {
                PLAYERS = players;
                String[] playerNames = new String[players.length];
                for (int i = 0; i < playerNames.length; i++) {
                    playerNames[i] = players[i].toString();
                }

                JPanel panel = new JPanel(new BorderLayout());
                PLAYER_LIST = new JList<>(playerNames);
                PLAYER_LIST.addListSelectionListener(this);
                PLAYER_LIST.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
                PLAYER_LIST.setVisibleRowCount(-1);
                panel.add(PLAYER_LIST, BorderLayout.CENTER);

                JPanel controls = new JPanel();
                BUTTONS = new JButton[2];
                BUTTONS[0] = new JButton("View");
                BUTTONS[0].addActionListener(this);
                BUTTONS[0].setEnabled(false);
                controls.add(BUTTONS[0]);
                BUTTONS[1] = new JButton("Exit");
                BUTTONS[1].addActionListener(this);
                controls.add(BUTTONS[1]);
                panel.add(controls, BorderLayout.SOUTH);

                setContentPane(panel);
                setBounds(100, 100, 100, 500);
                setVisible(true);
            } else {
                throw new IllegalArgumentException("A null Players Array was passed");
            }
        }

        /**
         * Invoked when an action occurs.
         *
         * @param e the event to be processed
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == BUTTONS[0]) {
                displayPlayer(PLAYERS[PLAYER_LIST.getSelectedIndex()], null);
            } else if (e.getSource() == BUTTONS[1]) {
                setVisible(false);
            }
        }

        /**
         * Called whenever the value of the selection changes.
         *
         * @param e the event that characterizes the change.
         */
        @Override
        public void valueChanged(ListSelectionEvent e) {
            BUTTONS[0].setEnabled(PLAYER_LIST.getSelectedIndex() >= 0);
        }
    }

    /**
     * Creates a dialog that prompts the user to view a Property
     *
     * @author irswr
     */
    private class PropertySelectionDialog extends JDialog implements ActionListener, ListSelectionListener {
        private final JList<String> PROPERTY_LIST; //Stores the String representation of the Properties in a list
        private final Property[] PROPERTIES; //Stores the Properties that the user can view
        private final JButton[] BUTTONS; //Stores the buttons that allow the user to interact with the dialog

        /**
         * Creates a prompt that allows the user to view a Property
         *
         * @param properties the Properties that the user can view
         * @throws IllegalArgumentException when a null Property is passed
         */
        public PropertySelectionDialog(Property[] properties) {
            super(MAIN_UI, "Property Viewer", true);
            if (properties != null) {
                PROPERTIES = properties;
                String[] propertyNames = new String[properties.length];
                for (int i = 0; i < propertyNames.length; i++) {
                    propertyNames[i] = properties[i].toString();
                }

                JPanel panel = new JPanel(new BorderLayout());
                PROPERTY_LIST = new JList<>(propertyNames);
                PROPERTY_LIST.addListSelectionListener(this);
                PROPERTY_LIST.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
                PROPERTY_LIST.setVisibleRowCount(-1);
                panel.add(PROPERTY_LIST, BorderLayout.CENTER);

                JPanel controls = new JPanel();
                BUTTONS = new JButton[2];
                BUTTONS[0] = new JButton("View");
                BUTTONS[0].addActionListener(this);
                BUTTONS[0].setEnabled(false);
                controls.add(BUTTONS[0]);
                BUTTONS[1] = new JButton("Exit");
                BUTTONS[1].addActionListener(this);
                controls.add(BUTTONS[1]);
                panel.add(controls, BorderLayout.SOUTH);

                setContentPane(panel);
                setBounds(100, 100, 100, 700);
                setVisible(true);
            } else {
                throw new IllegalArgumentException("An invalid Properties Array was passed");
            }

        }

        /**
         * Invoked when an action occurs.
         *
         * @param e the event to be processed
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == BUTTONS[0]) {
                displayProperty(PROPERTIES[PROPERTY_LIST.getSelectedIndex()], null);
            } else if (e.getSource() == BUTTONS[1]) {
                setVisible(false);
            }
        }

        /**
         * Called whenever the value of the selection changes.
         *
         * @param e the event that characterizes the change.
         */
        @Override
        public void valueChanged(ListSelectionEvent e) {
            BUTTONS[0].setEnabled(PROPERTY_LIST.getSelectedIndex() >= 0);
        }
    }

    /**
     * Represents a JPanel that displays the card for a Property
     *
     * @author irswr
     */
    private class PropertyViewer extends JPanel {
        private final Property PROPERTY; //Stores the Property being displayed

        public PropertyViewer(Property property) {
            if (property != null) {
                PROPERTY = property;
            } else {
                throw new IllegalArgumentException("A null Property was passed");
            }
        }

        /**
         * Paints the Property
         *
         * @param g the Graphics Object to paint with
         */
        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            drawProperty(g, PROPERTY, Game.propertiesInColorGroup(PROPERTY.getCOLOR_GROUP(), GAME.getGAME_BOARD()).length,
                    0, 0, getWidth(), getHeight());
        }

        /**
         * Draws a Property at the given coordinates
         *
         * @param g          the Graphics Object to draw with
         * @param property   the Property to draw
         * @param numInGroup the number of houses in this Properties color group
         * @param x          the x-coordinate to start drawing at
         * @param y          the y-coordinate to start drawing at
         * @param width      the width to draw the Property
         * @param height     the height to draw the Property
         * @throws IllegalArgumentException when a null or invalid parameter is passed
         */
        private void drawProperty(Graphics g, Property property, int numInGroup, int x, int y, int width, int height) {
            if (g != null && property != null && x >= 0 && y >= 0 && width > 0 && height > 0) {
                String owner = "Owner: " + (property.getOwner() != null ? property.getOwner().toString() : "Unowned");
                String numHouses = property.getNumHouses() + " houses";
                g.setFont(new Font("Kabel Heavy", Font.PLAIN, 20));
                g.setFont(g.getFont().deriveFont((float) (width / 2) / g.getFontMetrics().stringWidth(owner) * 20));
                g.drawString(owner, x + width / 2 - g.getFontMetrics().stringWidth(owner) / 2,
                        y + g.getFontMetrics().getHeight());
                g.drawString(numHouses, x + width / 2 - g.getFontMetrics().stringWidth(numHouses) / 2, y +
                        g.getFontMetrics().getHeight() * 2);
                drawPropertyCard(g, property, numInGroup, x, y + g.getFontMetrics().getHeight() * 3, width, height -
                        g.getFontMetrics().getHeight() * 3);
            } else {
                throw new IllegalArgumentException("An invalid parameter was passed");
            }
        }

        /**
         * Draws a Property deed
         *
         * @param g          the Graphics Object to draw with
         * @param property   the Property to draw
         * @param numInGroup the number of Properties in this properties color group
         * @param x          the x-coordinate to start at
         * @param y          they-coordinate to start at
         * @param width      the width to draw
         * @param height     the height to draw
         */
        private void drawPropertyCard(Graphics g, Property property, int numInGroup, double x, double y, double width,
                                      double height) {
            if (g != null && property != null && numInGroup > 0 && x >= 0 && y >= 0 && width > 0 && height > 0) {
                double lineWidth = width / 50;
                Graphics2D g2 = (Graphics2D) g;
                g2.setColor(Color.BLACK);
                g2.setStroke(new BasicStroke(Math.round(lineWidth)));
                g2.draw(new Rectangle.Double(x, y, width, height));
                double leftAlign = x + width / 10;
                double rightAlign = x + width - width / 10;
                Color color = GameGraphics.recognizeColorGroup(property.getCOLOR_GROUP());
                if (color != null) { //This colors the box that draws the Properties name, if necessary
                    g2.setStroke(new BasicStroke());
                    g2.setColor(color);
                    g2.fill(new Rectangle2D.Double(leftAlign, y + width / 10, width - width / 5, height / 10));
                    g2.setColor(Color.BLACK);
                    g2.setStroke(new BasicStroke(Math.round(lineWidth)));
                }
                //This code draws the box that shows the Properties name
                g2.draw(new Rectangle.Double(leftAlign, y + width / 10, width - width / 5, height / 10));
                String propertyName = property.toString();
                g2.setFont(new Font("Kabel Heavy", Font.BOLD, 20));
                g2.setFont(g2.getFont().deriveFont((float) (width - width / 5) / g2.getFontMetrics().stringWidth("   " + propertyName + "   ") * 20));
                g2.drawString(propertyName, (int) Math.round(leftAlign + (width - width / 5) / 2 - g2.getFontMetrics().stringWidth(propertyName) / 2.0),
                        (int) Math.round(y + width / 10 + g2.getFontMetrics().getHeight()));

                String cost = "Price: $" + property.getPRICE();
                g2.setFont(new Font("Kabel Heavy", Font.PLAIN, 20));
                g2.setFont(g2.getFont().deriveFont((float) (width / 3) / g2.getFontMetrics().stringWidth("                   ") * 20));
                g2.drawString(cost, (int) Math.round(x + width / 2 - g2.getFontMetrics().stringWidth(cost) / 2.0),
                        (int) Math.round(y + width / 5 + height / 10));
                g2.setStroke(new BasicStroke(Math.round(lineWidth)));
                g2.draw(new Line2D.Double(leftAlign, y + width / 10 + height / 10 + g2.getFontMetrics().getHeight() * 1.35,
                        rightAlign, y + width / 10 + height / 10 + g2.getFontMetrics().getHeight() * 1.35));
                //This code draws the left side of the rents
                if (property.IS_SCALED()) { //Draws out the text that says "If ___ owned:"
                    for (int i = 1; i <= numInGroup; i++) {
                        String description = "If " + i + " owned:";
                        g2.drawString(description, (float) leftAlign, (float) (y + width / 10 + height / 10 +
                                g2.getFontMetrics().getHeight() * (i + 1)));
                    }
                } else { //Draws out the text that says "With ___ houses:"
                    for (int i = -1; i <= property.getMAX_HOUSES(); i++) {
                        String description;
                        if (i == -1) {
                            description = "Rent:";
                        } else if (i == 0) {
                            description = "With Monopoly:";
                        } else if (i == property.getMAX_HOUSES()) {
                            description = "With Hotel:";
                        } else {
                            description = "With " + i + " Houses:";
                        }
                        g2.drawString(description, (float) leftAlign, (float)
                                (y + width / 10 + height / 10 + g2.getFontMetrics().getHeight() * (i + 3)));
                    }
                    String houseCost = "Houses cost $" + property.getBUILD_PRICE();
                    g2.drawString(houseCost, (float) (x + width / 2 - g2.getFontMetrics().stringWidth(houseCost) / 2),
                            (float) (y + width / 10 + height / 10 + g2.getFontMetrics().getHeight() *
                                    (property.getRENTS().length + 2)));
                }

                //This code draws out the right side of the rent
                if (property.IS_DICE_MULTIPLIER()) { //This writes out the "___ * dice roll"
                    for (int i = 0; i < property.getRENTS().length; i++) {
                        String description = property.getRENTS()[i] + " * dice roll";
                        g2.drawString(description, (float) rightAlign - g2.getFontMetrics().stringWidth(description),
                                (float) (y + width / 10 + height / 10 + g2.getFontMetrics().getHeight() * (i + 2)));
                    }
                } else { //This writes out the "$___"
                    for (int i = 0; i < property.getRENTS().length; i++) {
                        String description = "$" + property.getRENTS()[i];
                        g2.drawString(description, (float) rightAlign - g2.getFontMetrics().stringWidth(description),
                                (float) (y + width / 10 + +height / 10 + g2.getFontMetrics().getHeight() * (i + 2)));
                    }
                }
                g2.draw(new Line2D.Double(leftAlign, y + height - g2.getFontMetrics().getHeight() * 2, rightAlign,
                        y + height - g2.getFontMetrics().getHeight() * 2));
                String mortgage = "Mortgage value $" + property.getMORTGAGE();
                g2.drawString(mortgage, (float) (x + width / 2 - g2.getFontMetrics().stringWidth(mortgage) / 2),
                        (float) (y + height - g2.getFontMetrics().getHeight()));

            } else {
                throw new IllegalArgumentException("An invalid parameter was passed");
            }
        }
    }

    private class CardSelectionDialog extends JDialog implements ActionListener, ListSelectionListener {
        private final JList<String> CARD_LIST; //The list of Cards the user can select from
        private final Card[] CARDS; //The Cards the user can select from
        private final JButton[] BUTTONS; //The buttons in the dialog

        public CardSelectionDialog(Card[] cards) {
            super(MAIN_UI, "Card Viewer", true);
            if (cards != null) {
                CARDS = cards;
                String[] cardNames = new String[cards.length];
                for (int i = 0; i < cardNames.length; i++) {
                    cardNames[i] = cards[i].toString();
                }

                JPanel panel = new JPanel(new BorderLayout());
                CARD_LIST = new JList<>(cardNames);
                CARD_LIST.addListSelectionListener(this);
                CARD_LIST.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
                CARD_LIST.setVisibleRowCount(-1);
                panel.add(CARD_LIST, BorderLayout.CENTER);

                JPanel controls = new JPanel();
                BUTTONS = new JButton[2];
                BUTTONS[0] = new JButton("View");
                BUTTONS[0].addActionListener(this);
                BUTTONS[0].setEnabled(false);
                controls.add(BUTTONS[0]);
                BUTTONS[1] = new JButton("Exit");
                BUTTONS[1].addActionListener(this);
                controls.add(BUTTONS[1]);
                panel.add(controls, BorderLayout.SOUTH);

                setContentPane(panel);
                setBounds(100, 100, 100, 500);
                setVisible(true);
            } else {
                throw new IllegalArgumentException("A Cards Array was passed");
            }
        }

        /**
         * Invoked when an action occurs.
         *
         * @param e the event to be processed
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == BUTTONS[0]) {
                displayCard(CARDS[CARD_LIST.getSelectedIndex()], null);
            } else if (e.getSource() == BUTTONS[1]) {
                setVisible(false);
            }
        }

        /**
         * Called whenever the value of the selection changes.
         *
         * @param e the event that characterizes the change.
         */
        @Override
        public void valueChanged(ListSelectionEvent e) {
            BUTTONS[0].setEnabled(CARD_LIST.getSelectedIndex() >= 0);
        }
    }

    /**
     * Allows the user to view a Card
     *
     * @author irswr
     */
    private class CardViewer extends JPanel {
        private final Card CARD; //Stores the Card being displayed

        /**
         * Sets up a JPanel to view a Card
         *
         * @param card the Card to be displayed
         */
        public CardViewer(Card card) {
            if (card != null) {
                CARD = card;
            } else {
                throw new IllegalArgumentException("A null Card was passed");
            }
        }

        /**
         * Draws the Card for this Class
         *
         * @param g the Graphics Object to draw with
         */
        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            drawCard(g, CARD, 0, 0, getWidth(), getHeight());
        }

        /**
         * Draws a Card
         *
         * @param g      the Graphics Object to draw with
         * @param card   the Card to draw
         * @param x      the x-coordinate to start at
         * @param y      the y-coordinate to start at
         * @param width  the width to draw
         * @param height the height to draw
         * @throws IllegalArgumentException when an invalid parameter is passed
         */
        public void drawCard(Graphics g, Card card, int x, int y, int width, int height) {
            if (g != null && card != null && x >= 0 && y >= 0 && width > 0 && height > 0) {
                String owner = "Owner: " + (card.getOwner() != null ? card.getOwner() : "Unowned");
                g.setColor(Color.BLACK);
                g.setFont(new Font("Kabel Heavy", Font.BOLD, 20));
                g.setFont(g.getFont().deriveFont((float) (width / 2) / g.getFontMetrics().stringWidth(owner) * 20));
                g.drawString(owner, x + width / 2 - g.getFontMetrics().stringWidth(owner) / 2, y +
                        g.getFontMetrics().getHeight());

                g.setColor(Color.ORANGE);
                int rectY = y + g.getFontMetrics().getHeight() + g.getFontMetrics().getDescent();
                int rectHeight = height - rectY;
                g.fillRect(x, rectY, width, rectHeight);

                Graphics2D g2 = (Graphics2D) g;
                g2.setColor(Color.BLACK);
                String cardDescription = card.getDESCRIPTION();
                AttributedString a = new AttributedString(cardDescription);
                AttributedCharacterIterator paragraph = a.getIterator();
                LineBreakMeasurer lineMeasurer = new LineBreakMeasurer(paragraph, g2.getFontRenderContext());
                g2.setFont(g.getFont().deriveFont(Font.PLAIN));
                //This code ensures all of the description fits
                int paragraphStart = paragraph.getBeginIndex();
                int paragraphEnd = paragraph.getEndIndex();
                float drawPosY = rectY;
                lineMeasurer.setPosition(paragraphStart);
                while (lineMeasurer.getPosition() < paragraphEnd) {
                    TextLayout layout = lineMeasurer.nextLayout((float) width);
                    float drawPosX = layout.isLeftToRight()
                            ? 0 : (float) width - layout.getAdvance();
                    drawPosY += layout.getAscent();
                    layout.draw(g2, drawPosX, drawPosY);
                    drawPosY += layout.getDescent() + layout.getLeading();
                }

            } else {
                throw new IllegalArgumentException("An invalid parameter was passed");
            }
        }
    }

    /**
     * Allows the user to view a Player
     *
     * @author irswr
     */
    private class PlayerViewer extends JPanel implements ActionListener {
        private final Player PLAYER; //Stores the Player we're viewing
        private final JButton[] BUTTONS; //Stores the buttons that allow the user to get more info on this Player

        /**
         * Creates the Player Viewer
         *
         * @param player the Player to view
         * @throws IllegalArgumentException when a null Player is passed
         */
        public PlayerViewer(Player player) {
            super(new GridLayout(2, 2));
            if (player != null) {
                PLAYER = player;
                add(new JLabel("Wallet: " + player.getWallet()));
                add(new JLabel("Position: " + GAME.getGAME_BOARD()[player.getPosition()].toString()));
                BUTTONS = new JButton[2];
                BUTTONS[0] = new JButton("View Properties");
                BUTTONS[0].addActionListener(this);
                add(BUTTONS[0]);
                BUTTONS[1] = new JButton("View Cards");
                BUTTONS[1].addActionListener(this);
                add(BUTTONS[1]);
            } else {
                throw new IllegalArgumentException("A null Player was passed");
            }
        }

        /**
         * Invoked when an action occurs.
         *
         * @param e the event to be processed
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == BUTTONS[0]) {
                new PropertySelectionDialog(Game.playerProperties(GAME.getGAME_BOARD(), PLAYER));
            } else if (e.getSource() == BUTTONS[1]) {
                new CardSelectionDialog(Game.getOwnedCards(PLAYER, GAME.getDECKS()));
            }
        }
    }

    private class TradeViewer extends JPanel implements ActionListener, ListSelectionListener {
        private final Trade TRADE; //Stores the Trade being displayed
        private final JButton[] BUTTONS; //Stores the buttons that the user can interact with
        private final JList<String> SENDER_PROPERTIES; //Stores the Properties the sender is offering
        private final JList<String> SENDER_CARDS; //Stores the Cards the sender is offering
        private final JList<String> RECEIVER_PROPERTIES; //Stores the Properties the receiver is offering
        private final JList<String> RECEIVER_CARDS; //Stores the Cards the receiver is offering

        public TradeViewer(Trade trade) {
            super(new BorderLayout());
            if (trade != null) {
                JPanel tradePanel = new JPanel();

                JPanel senderPanel = new JPanel();
                senderPanel.setLayout(new BoxLayout(senderPanel, BoxLayout.Y_AXIS));
                senderPanel.add(new JLabel(trade.getSENDER().toString()));

                senderPanel.add(new JLabel("$" + trade.getSenderMoney()));

                String[] senderPropertyNames = new String[trade.getSenderProperties().size()];
                for (int i = 0; i < senderPropertyNames.length; i++) {
                    senderPropertyNames[i] = trade.getSenderProperties().get(i).toString();
                }
                SENDER_PROPERTIES = new JList<>(senderPropertyNames);
                SENDER_PROPERTIES.addListSelectionListener(this);
                SENDER_PROPERTIES.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
                SENDER_PROPERTIES.setVisibleRowCount(-1);
                senderPanel.add(SENDER_PROPERTIES);

                String[] senderCardNames = new String[trade.getSenderCards().size()];
                for (int i = 0; i < senderCardNames.length; i++) {
                    senderCardNames[i] = trade.getSenderCards().get(i).toString();
                }
                SENDER_CARDS = new JList<>(senderCardNames);
                SENDER_CARDS.addListSelectionListener(this);
                SENDER_CARDS.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
                SENDER_CARDS.setVisibleRowCount(-1);
                senderPanel.add(SENDER_CARDS);

                tradePanel.add(senderPanel);

                JPanel receiverPanel = new JPanel();
                receiverPanel.setLayout(new BoxLayout(receiverPanel, BoxLayout.Y_AXIS));
                receiverPanel.add(new JLabel(trade.getRECEIVER().toString()));

                receiverPanel.add(new JLabel("$" + trade.getReceiverMoney()));

                String[] receiverPropertyNames = new String[trade.getReceiverProperties().size()];
                for (int i = 0; i < receiverPropertyNames.length; i++) {
                    receiverPropertyNames[i] = trade.getReceiverProperties().get(i).toString();
                }
                RECEIVER_PROPERTIES = new JList<>(receiverPropertyNames);
                RECEIVER_PROPERTIES.addListSelectionListener(this);
                RECEIVER_PROPERTIES.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
                RECEIVER_PROPERTIES.setVisibleRowCount(-1);
                receiverPanel.add(RECEIVER_PROPERTIES);

                String[] receiverCardNames = new String[trade.getReceiverCards().size()];
                for (int i = 0; i < receiverCardNames.length; i++) {
                    receiverCardNames[i] = trade.getReceiverCards().get(i).toString();
                }
                RECEIVER_CARDS = new JList<>(receiverCardNames);
                RECEIVER_CARDS.addListSelectionListener(this);
                RECEIVER_CARDS.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
                RECEIVER_PROPERTIES.setVisibleRowCount(-1);
                receiverPanel.add(RECEIVER_CARDS);

                tradePanel.add(receiverPanel);

                add(tradePanel, BorderLayout.CENTER);

                JPanel buttonPanel = new JPanel();

                BUTTONS = new JButton[2];
                BUTTONS[0] = new JButton("View Property");
                BUTTONS[0].addActionListener(this);
                BUTTONS[0].setEnabled(false);
                buttonPanel.add(BUTTONS[0]);
                BUTTONS[1] = new JButton("View Card");
                BUTTONS[1].addActionListener(this);
                BUTTONS[1].setEnabled(false);
                buttonPanel.add(BUTTONS[1]);
                add(buttonPanel, BorderLayout.SOUTH);
                TRADE = trade;
            } else {
                throw new IllegalArgumentException("A null Trade was passed");
            }
        }

        /**
         * Invoked when an action occurs.
         *
         * @param e the event to be processed
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == BUTTONS[0]) {
                if (SENDER_PROPERTIES.getSelectedIndex() >= 0) {
                    displayProperty(TRADE.getSenderProperties().get(SENDER_PROPERTIES.getSelectedIndex()), null);
                } else if (RECEIVER_PROPERTIES.getSelectedIndex() >= 0) {
                    displayProperty(TRADE.getReceiverProperties().get(RECEIVER_PROPERTIES.getSelectedIndex()), null);
                }
            } else if (e.getSource() == BUTTONS[1]) {
                if (SENDER_CARDS.getSelectedIndex() >= 0) {
                    displayCard(TRADE.getSenderCards().get(SENDER_CARDS.getSelectedIndex()), null);
                } else if (RECEIVER_CARDS.getSelectedIndex() >= 0) {
                    displayCard(TRADE.getReceiverCards().get(RECEIVER_CARDS.getSelectedIndex()), null);
                }
            }
        }

        /**
         * Called whenever the value of the selection changes.
         *
         * @param e the event that characterizes the change.
         */
        @Override
        public void valueChanged(ListSelectionEvent e) {
            if (e.getSource() == SENDER_PROPERTIES) {
                if (SENDER_PROPERTIES.getSelectedIndex() >= 0) { //This un-selects from the receiver list
                    RECEIVER_PROPERTIES.setSelectedIndex(-1);
                    BUTTONS[0].setEnabled(true);
                } else {
                    BUTTONS[0].setEnabled(RECEIVER_PROPERTIES.getSelectedIndex() >= 0);
                }
            } else if (e.getSource() == RECEIVER_PROPERTIES) {
                if (RECEIVER_PROPERTIES.getSelectedIndex() >= 0) { //This un-selects from the sender list
                    SENDER_PROPERTIES.setSelectedIndex(-1);
                    BUTTONS[0].setEnabled(true);
                } else {
                    BUTTONS[0].setEnabled(SENDER_PROPERTIES.getSelectedIndex() >= 0);
                }
            } else if (e.getSource() == SENDER_CARDS) {
                if (SENDER_CARDS.getSelectedIndex() >= 0) {
                    RECEIVER_CARDS.setSelectedIndex(-1);
                    BUTTONS[1].setEnabled(true);
                } else {
                    BUTTONS[1].setEnabled(RECEIVER_CARDS.getSelectedIndex() >= 0);
                }
            } else if (e.getSource() == RECEIVER_CARDS) {
                if (RECEIVER_CARDS.getSelectedIndex() >= 0) {
                    SENDER_CARDS.setSelectedIndex(-1);
                    BUTTONS[1].setEnabled(true);
                } else {
                    BUTTONS[1].setEnabled(SENDER_CARDS.getSelectedIndex() >= 0);
                }
            }
        }
    }
}
