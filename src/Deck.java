import java.util.ArrayList;
import java.util.Arrays;

/**
 * Represents a Deck of Cards
 *
 * @author irswr
 */
public class Deck {
    //Deck constants
    private final Card[] DEFAULT_DECK; //Stores the default deck that will be referenced when the standard deck is depleted. This shouldn't be null

    //Deck fields
    private ArrayList<Card> deck; //Stores the active deck that will be referenced when a card needs to be drawn. This shouldn't be null
    private ArrayList<Card> ownedCards; //Stores the Cards that a Player currently holds

    /**
     * Constructor for Deck
     *
     * @param cards the array of cards to be used. This shouldn't be null and should contain at least one card
     * @throws IllegalArgumentException when a null Card Array is passed, contains no Cards, or contains null Cards
     */
    public Deck(Card[] cards) {
        if (cards != null && cards.length > 0) {
            for (Card card : cards) { //Validates that there are no null Cards in the deck
                if (card == null) {
                    throw new IllegalArgumentException("An null Card was passed");
                }
            }

            DEFAULT_DECK = cards;
            resetDeck();
        } else {
            throw new IllegalArgumentException("A null or empty Card Array was passed");
        }
    }

    /**
     * Gets the type of the Deck
     * @return the type of the Deck
     */
    public String getTYPE() {
        return DEFAULT_DECK[0].getTYPE();
    }

    /**
     * Turns the deck passed into an ArrayList
     *
     * @param deck the Card[] that is to be returned as an ArrayList. This shouldn't be null and should contain
     *             at least one card
     * @return the created ArrayList
     * @throws IllegalArgumentException when a null or empty Deck is passed
     */
    private static ArrayList<Card> createArrayListDeck(Card[] deck) {
        if (deck != null && deck.length > 0) {
            return new ArrayList<>(Arrays.asList(deck));
        } else {
            throw new IllegalArgumentException("A null or empty Deck was passed");
        }
    }

    /**
     * Clears owned cards from the provided ArrayList
     *
     * @param cards the ArrayList to be edited
     * @return the removed Cards
     * @throws IllegalArgumentException when a null Cards ArrayList is passed
     */
    private static ArrayList<Card> clearOwnedCards(ArrayList<Card> cards) {
        ArrayList<Card> result = new ArrayList<>();
        if (cards != null) {
            for (Card card : cards) {
                if (card.getOwner() != null) {
                    result.add(card);
                }
            }
            cards.removeIf(card -> (card.getOwner() != null));
            return result;
        } else {
            throw new IllegalArgumentException("A null Cards ArrayList was passed");
        }
    }

    /**
     * Resets the deck from DEFAULT_DECK
     *
     * @throws IllegalStateException when the deck contains only owned cards
     */
    private void resetDeck() {
        deck = createArrayListDeck(DEFAULT_DECK);
        ownedCards = clearOwnedCards(deck);
        if (deck.size() == 0) {
            throw new IllegalStateException("The deck contains only owned cards");
        }
    }

    /**
     * Gets a Card from the deck. Will also reset the Deck and then recall itself if necessary
     *
     * @return the Card grabbed from the Deck
     */
    public Card getCard() {
        if (deck.size() > 0) {
            return deck.remove((int) (deck.size() * Math.random()));
        } else {
            resetDeck();
            return getCard();
        }
    }

    /**
     * Gets the ownedCards ArrayList
     *
     * @return the ownedCards ArrayList
     */
    public ArrayList<Card> getOwnedCards() {
        return ownedCards;
    }

    /**
     * Adds a Card to the list of owned Cards
     *
     * @param card the Card to add
     * @throws IllegalArgumentException when a null Card, unowned Card, or a Card that doesn't belong to this Deck is
     *                                  passed
     */
    public void addCardToOwnedCards(Card card) {
        if (card != null && card.getOwner() != null) {
            boolean isInDeck = false;
            for (Card c : DEFAULT_DECK) {
                if (c.equals(card)) {
                    isInDeck = true;
                    break;
                }
            }

            if (isInDeck) {
                ownedCards.add(card);
            } else {
                throw new IllegalArgumentException("An Card from another Deck was passed");
            }
        } else {
            throw new IllegalArgumentException("A null or unowned Card was passed");
        }
    }
}
