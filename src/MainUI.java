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
    //Main UI fields
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

        add(menu, "menu");
        add(gameCreator, "game creator");
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
        JFrame frame = new MainUI();
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
     * Invoked when an action occurs.
     *
     * @param e the event to be processed
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == BUTTONS[0]) {
            CARD_LAYOUT.show(this.getContentPane(), "game creator");
        } else if (e.getSource() == BUTTONS[1]) {
            //TODO: Implement this
        } else if (e.getSource() == BUTTONS[2]) {
            System.exit(0);
        }
    }
}
