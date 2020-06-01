import java.util.ArrayList;

/**
 * Represents an Auction
 * @author irswr
 */
public class Auction {
    //Auction constants
    private Property PROPERTY; //This stores the Property being auctioned
    private ArrayList<Player> PLAYERS; //This stores the Players who are engaged in bidding for this Property. This can decline

    //Auction fields
    private int currentValue; //This stores the current value being placed on the Property
    private boolean isConfirmed; //This stores whether or not the auction is confirmed

    /**
     * Constructor for Auction. Validates according to the conditions below
     * @param property the Property being auctioned off
     * @param players the players participating in the auction
     * @throws IllegalArgumentException when a null or empty parameter is passed
     */
    public Auction(Property property, ArrayList<Player> players) {
        if (property != null && property.getOwner() == null && players != null &&
                players.size() > 0) {
            PROPERTY = property;
            PLAYERS = players;
            currentValue = 0;
            isConfirmed = false;
        } else {
            throw new IllegalArgumentException("A null or empty parameter was passed");
        }
    }

    /**
     * Does a round of the Auction. This means that it prompts each Player for a bid, and removes them if they don't
     * outbid the current value
     * @throws IllegalStateException when is called after isConfirmed is true
     */
    public void doRound() {
        if (!isConfirmed) {
            for (Player player : PLAYERS) {
                int bid = player.promptInt("How much are you willing to bid for " + PROPERTY + "?",
                        currentValue, -1, -1);
                if (player.canAfford(bid)) {
                    if (bid > currentValue) {
                        currentValue = bid;
                    } else {
                        PLAYERS.remove(player);
                    }
                } else {
                    player.cannotAffordOptional();
                }
            }
            if (PLAYERS.size() == 1) {
                isConfirmed = true;
                if (PLAYERS.get(0).canAfford(currentValue)) {
                    PLAYERS.get(0).updateWallet(-currentValue);
                    PROPERTY.setOwner(PLAYERS.get(0));
                } else {
                    throw new IllegalStateException("Auction is an illegal state");
                }
            }
        } else {
            throw new IllegalStateException("Tried to alter an Auction after it was confirmed");
        }
    }

    /**
     * Gets the current bid of the Auction
     * @return the current bid of the Auction
     */
    public int getCurrentValue() {
        return currentValue;
    }

    /**
     * Gets whether or not this Auction is confirmed
     * @return whether or not this Auction is confirmed
     */
    public boolean isConfirmed(){
        return isConfirmed;
    }
}
