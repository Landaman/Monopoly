import java.util.ArrayList;

/**
 * Represents a Trade between two Players. Once it is confirmed (represented by isConfirmed) it can no longer be altered.
 * Allows Players to trade Properties (with no houses), Cards, and money
 *
 * @author irswr
 */
public class Trade {
    //Trade constants
    private final Player SENDER; //The Player who sends the Trade
    private final Player RECEIVER; //The Player who receives the Trade

    //Trade fields
    private final ArrayList<Property> senderProperties; //The Properties that the sender is offering
    private final ArrayList<Property> receiverProperties; //The Properties that the receiver is offering
    private final ArrayList<Card> senderCards; //The Cards that the sender is offering
    private final ArrayList<Card> receiverCards; //The Cards the receiver is offering
    private int senderMoney; //Stores the amount of money the sender is offering
    private int receiverMoney; //Stores the amount of money the receiver is offering
    private boolean isConfirmed; //Stores whether this Trade has been confirmed

    /**
     * Constructor for Trade. Validates according to the parameters outlined here
     *
     * @param sender   the sender of the Trade
     * @param receiver the receiver of the Trade
     * @param prompt   the prompt that should be shown to the receiver
     */
    public Trade(Player sender, Player receiver, String prompt) {
        if (sender != null && receiver != null) {
            SENDER = sender;
            RECEIVER = receiver;
            isConfirmed = false;
            senderProperties = new ArrayList<>();
            receiverProperties = new ArrayList<>();
            senderCards = new ArrayList<>();
            receiverCards = new ArrayList<>();
            senderMoney = 0;
            receiverMoney = 0;
        } else {
            throw new IllegalArgumentException("A null Player was passed");
        }
    }

    /**
     * Adds a Property to the list the sender is offering
     *
     * @param property the Property being offered
     * @throws IllegalArgumentException when a null Property or a Property owned by someone other than the Sender is passed
     * @throws IllegalStateException    when called after isConfirmed is true
     */
    public void addSenderProperty(Property property) {
        if (!isConfirmed) {
            if (property != null && SENDER.equals(property.getOwner()) && property.canSell()) {
                senderProperties.add(property);
            } else {
                throw new IllegalArgumentException("An invalid Property was passed");
            }
        } else {
            throw new IllegalStateException("Tried to alter a Trade after it was confirmed");
        }
    }

    /**
     * Adds a Property to the list the receiver is offering
     *
     * @param property the Property being offered
     * @throws IllegalArgumentException when a null or Property owned by someone else is passed
     * @throws IllegalStateException    when called after isConfirmed is true
     */
    public void addReceiverProperty(Property property) {
        if (!isConfirmed) {
            if (property != null && RECEIVER.equals(property.getOwner()) && property.canSell()) {
                receiverProperties.add(property);
            } else {
                throw new IllegalArgumentException("An invalid Property was passed");
            }
        } else {
            throw new IllegalStateException("Tried to alter a Trade after it was confirmed");
        }
    }

    /**
     * Gets the list of Properties being offered by the sender
     *
     * @return the list of Properties being offered by the sender
     */
    public ArrayList<Property> getSenderProperties() {
        return senderProperties;

    }

    /**
     * Gets the list of Properties being offered by the receiver
     *
     * @return the list of Properties being offered by the receiver
     */
    public ArrayList<Property> getReceiverProperties() {
        return receiverProperties;
    }

    /**
     * Removes a Property from the list the sender is offering
     *
     * @param property the Property being removed
     * @throws IllegalArgumentException when a null Property is passed
     * @throws IllegalStateException    when is called after isConfirmed is true
     */
    public void removeSenderProperty(Property property) {
        if (!isConfirmed) {
            if (property != null) {
                senderProperties.remove(property);
            } else {
                throw new IllegalArgumentException("An invalid Property was passed");
            }
        } else {
            throw new IllegalStateException("Tried to alter a Trade after it was confirmed");
        }
    }

    /**
     * Removes a Property from the list the receiver is offering
     *
     * @param property the Property being removed
     * @throws IllegalArgumentException when a null Property or a Property owned by someone other than the receiver is passed
     * @throws IllegalStateException    when is called after isConfirmed is true
     */
    public void removeReceiverProperty(Property property) {
        if (!isConfirmed) {
            if (property != null) {
                receiverProperties.remove(property);
            } else {
                throw new IllegalArgumentException("An invalid Property was passed");
            }
        } else {
            throw new IllegalStateException("Tried to alter a Trade after it was confirmed");
        }
    }

    /**
     * Adds a Card to the list the sender is offering
     *
     * @param card the Card being added
     * @throws IllegalArgumentException when a null Card or a Card owned by someone other than the sender is passed
     * @throws IllegalStateException    when is called after isConfirmed is true
     */
    public void addSenderCard(Card card) {
        if (!isConfirmed) {
            if (card != null && SENDER.equals(card.getOwner())) {
                senderCards.add(card);
            } else {
                throw new IllegalArgumentException("An invalid Card was passed");
            }
        } else {
            throw new IllegalStateException("Tried to alter a Trade after it was confirmed");
        }
    }

    /**
     * Adds a Card to the list the receiver is offering
     *
     * @param card the Card being added
     * @throws IllegalArgumentException when a null Card or a Card owned by someone other than the receiver is passed
     * @throws IllegalStateException    when is called after isConfirmed is true
     */
    public void addReceiverCard(Card card) {
        if (!isConfirmed) {
            if (card != null && RECEIVER.equals(card.getOwner())) {
                receiverCards.add(card);
            } else {
                throw new IllegalArgumentException("An invalid Card was passed");
            }
        } else {
            throw new IllegalStateException("Tried to alter a Trade after it was confirmed");
        }
    }

    /**
     * Removes a Card from the list the sender is offering
     *
     * @param card the Card being removed
     * @throws IllegalArgumentException when a null Card or Card owned by someone other than the sender is passed
     * @throws IllegalStateException    when is called after isConfirmed is true
     */
    public void removeSenderCard(Card card) {
        if (!isConfirmed) {
            if (card != null) {
                senderCards.remove(card);
            } else {
                throw new IllegalArgumentException("An invalid Card was passed");
            }
        } else {
            throw new IllegalStateException("Tried to alter a Trade after it was confirmed");
        }
    }

    /**
     * Removes a Card from the list the receiver is offering
     *
     * @param card the Card being removed
     * @throws IllegalArgumentException when a null Card or Card owned by someone other than the receiver is passed
     * @throws IllegalStateException    when is called after isConfirmed is true
     */
    public void removeReceiverCard(Card card) {
        if (!isConfirmed) {
            if (card != null) {
                receiverCards.remove(card);
            } else {
                throw new IllegalArgumentException("An invalid Card was passed");
            }
        } else {
            throw new IllegalStateException("Tried to alter a Trade after it was confirmed");
        }
    }

    /**
     * Gets the list of Cards being offered by the sender
     *
     * @return the list of Cards being offered by the sender
     */
    public ArrayList<Card> getSenderCards() {
        return senderCards;
    }

    /**
     * Gets the list of Cards being offered by the receiver
     *
     * @return the list of Cards being offered by the receiver
     */
    public ArrayList<Card> getReceiverCards() {
        return receiverCards;
    }

    /**
     * Adds money to the amount the sender is offering
     *
     * @param amount the amount being added
     * @throws IllegalArgumentException when a negative amount or an amount the sender cannot afford is passed
     * @throws IllegalStateException    when is called after isConfirmed is true
     */
    public void addSenderMoney(int amount) {
        if (!isConfirmed) {
            if (amount > 0 && SENDER.canAfford(amount)) {
                senderMoney += amount;
            } else {
                throw new IllegalArgumentException("An invalid amount of money was passed");
            }
        } else {
            throw new IllegalStateException("Tried to alter a Trade after it was confirmed");
        }
    }

    /**
     * Adds money to the amount the receiver is offering
     *
     * @param amount the amount being added
     * @throws IllegalArgumentException when a negative amount or an amount the receiver cannot afford is passed
     * @throws IllegalStateException    when is called after isConfirmed is true
     */
    public void addReceiverMoney(int amount) {
        if (!isConfirmed) {
            if (amount > 0 && RECEIVER.canAfford(amount)) {
                receiverMoney += amount;
            } else {
                throw new IllegalArgumentException("An invalid amount of money was passed");
            }
        } else {
            throw new IllegalStateException("Tried to alter a Trade after it was confirmed");
        }
    }

    /**
     * Removes money from the amount to sender is offering
     *
     * @param amount the amount being removed
     * @throws IllegalArgumentException when a positive amount or an amount greater than the sender is offering is passed
     * @throws IllegalStateException    when is called after isConfirmed is true
     */
    public void removeSenderMoney(int amount) {
        if (!isConfirmed) {
            if (amount > 0 && amount <= senderMoney) {
                senderMoney -= amount;
            } else {
                throw new IllegalArgumentException("An invalid amount of money was passed");
            }
        } else {
            throw new IllegalStateException("Tried to alter a Trade after it was confirmed");
        }
    }

    /**
     * Removes money from the amount to receiver is offering
     *
     * @param amount the amount being removed
     * @throws IllegalArgumentException when a positive amount or an amount greater than the receiver is offering is passed
     * @throws IllegalStateException    when is called after isConfirmed is true
     */
    public void removeReceiverMoney(int amount) {
        if (!isConfirmed) {
            if (amount > 0 && amount <= receiverMoney) {
                receiverMoney -= amount;
            } else {
                throw new IllegalArgumentException("An invalid amount of money was passed");
            }
        } else {
            throw new IllegalStateException("Tried to alter a Trade after it was confirmed");
        }
    }

    /**
     * Gets the amount of money the sender is offering
     *
     * @return the amount of money the sender is offering
     */
    public int getSenderMoney() {
        return senderMoney;
    }

    /**
     * Gets the amount of money the receiver is offering
     *
     * @return the amount of money the receiver is offering
     */
    public int getReceiverMoney() {
        return receiverMoney;
    }

    /**
     * Confirms the trade and transfers the Properties, Cards, and money to their new owners
     *
     * @throws IllegalStateException when is called after isConfirmed is true
     */
    public void confirmTrade() {
        if (!isConfirmed) {
            for (Property property : senderProperties) { //Sends all of the senderProperties to the receiver
                property.setOwner(RECEIVER);
            }

            for (Card card : senderCards) { //Sends all of the senderCards to the receiver
                card.setOwner(RECEIVER);
            }

            //Sends the sender money to the receiver
            SENDER.updateWallet(-senderMoney);
            RECEIVER.updateWallet(senderMoney);

            for (Property property : receiverProperties) { //Sends all of the receiver properties to the sender
                property.setOwner(SENDER);
            }

            for (Card card : receiverCards) { //Sends all of the receiver cards to the sender
                card.setOwner(SENDER);
            }

            //Sends the receiver money to the sender
            RECEIVER.updateWallet(-receiverMoney);
            SENDER.updateWallet(receiverMoney);
            isConfirmed = true;
        } else {
            throw new IllegalStateException("Tried to alter a Trade after it was confirmed");
        }
    }

    /**
     * Gets the Player who is the sender of this Trade
     *
     * @return the sender of this Trade
     */
    public Player getSENDER() {
        return SENDER;
    }

    /**
     * Gets the Player who is the receiver of this Trade
     *
     * @return the receiver of the Trade
     */
    public Player getRECEIVER() {
        return RECEIVER;
    }
}
