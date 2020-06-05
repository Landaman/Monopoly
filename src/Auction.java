import java.util.ArrayList;

/**
 * Represents an Auction
 *
 * @author irswr
 */
public class Auction {
    //Auction constants
    private final Property PROPERTY; //This stores the Property being auctioned
    private final ArrayList<Player> PLAYERS; //This stores the Players who are engaged in bidding for this Property. This can decline
    private final String PROMPT; //The prompt that should be shown to Players

    //Auction fields
    private int currentValue; //This stores the current value being placed on the Property
    private boolean isConfirmed; //This stores whether or not the auction is confirmed

    /**
     * Constructor for Auction. Validates according to the conditions below
     *
     * @param property the Property being auctioned off
     * @param players  the players participating in the auction
     * @param prompt the prompt that should be shown to Player each round
     * @throws IllegalArgumentException when a null or empty parameter is passed
     */
    public Auction(Property property, ArrayList<Player> players, String prompt) {
        if (property != null && property.getOwner() == null && players != null &&
                players.size() > 0 && prompt != null) {
            PROPERTY = property;
            PLAYERS = players;
            currentValue = 0;
            isConfirmed = false;
            PROMPT = prompt;
        } else {
            throw new IllegalArgumentException("A null or empty parameter was passed");
        }
    }

    /**
     * Does a round of the Auction. This means that it prompts each Player for a bid, and removes them if they don't
     * outbid the current value
     *
     * @throws IllegalStateException when is called after isConfirmed is true
     */
    public void doRound() {
        if (!isConfirmed) {
            for (Player player : PLAYERS) {
                int bid = player.promptInt(PROMPT,
                        currentValue + 1, -1, -1, this);
                if (player.canAfford(bid)) {
                    if (bid > currentValue) {
                        currentValue = bid;
                    } else {
                        PLAYERS.remove(player);
                    }
                }
            }
            if (PLAYERS.size() == 1) {
                isConfirmed = true;
                PLAYERS.get(0).updateWallet(-currentValue);
                PROPERTY.setOwner(PLAYERS.get(0));
            }
        } else {
            throw new IllegalStateException("Tried to alter an Auction after it was confirmed");
        }
    }

    /**
     * Gets the current bid of the Auction
     *
     * @return the current bid of the Auction
     */
    public int getCurrentValue() {
        return currentValue;
    }

    /**
     * Gets the Property being auctioned
     * @return the Property being auctioned
     */
    public Property getPROPERTY() {
        return PROPERTY;
    }
    /**
     * Gets whether or not this Auction is confirmed
     *
     * @return whether or not this Auction is confirmed
     */
    public boolean isConfirmed() {
        return isConfirmed;
    }
}
