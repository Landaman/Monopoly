import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

/**
 * Represents the Game board. Contains all of the methods for general game execution
 *
 * @author irswr
 */
public class Game {
    //Game constants
    private final Space[] GAME_BOARD; //This stores the Spaces that come together to form the gameBoard
    private final String[] COLOR_GROUPS; //This stores the color groups that are used on the gameBoard
    private final Player[] PLAYERS; //Stores all of the games Players
    private final Dice[] DICE; //Stores the Dice for the game
    private final Deck[] DECKS; //This stores the Game's Decks. 0 is Community Chest, 1 is Chance
    private final GameUI GAME_UI; //Stores the GameUI Object that will manage the UI
    private final int JAIL_SPACE; //Stores the index of the Jail space
    private final int JAIL_BAIL; //Stores the amount the Player needs to pay to get out of jail

    //Game fields
    private int currentPlayer; //Stores the index of the current Player in PLAYERS

    /*
    The following methods will manage all aspects of game initialization
     */

    /**
     * Constructs for Game. Validates according to the conditions outlined here
     *
     * @param gameBoard   a completed Array of Spaces. This shouldn't be null or contain any null elements
     * @param colorGroups the color groups used in the gameBoard. This shouldn't be null
     * @param jailSpace   the index of the jail
     * @param jailBail    the amount the Player needs to pay to get out of jail
     * @param players     a completed Array of the game's Players. This shouldn't be null or contain any null elements
     * @param dice        a completed Array of the game's Dice. This shouldn't be null or contain any null elements
     * @param decks       a completed Array of Decks or contain any null elements
     * @param gameUI      a completed GameUI. This shouldn't be null or contain any null elements
     * @throws IllegalArgumentException when a null, or empty parameter is passed
     */
    public Game(Space[] gameBoard, String[] colorGroups, int jailSpace, int jailBail, Player[] players,
                Dice[] dice, Deck[] decks, GameUI gameUI) {
        if (gameBoard != null && colorGroups != null && colorGroups.length > 0 && jailSpace >= 0 &&
                jailSpace < gameBoard.length && jailBail >= 0 &&
                players != null && players.length > 0 && dice != null && dice.length > 0 && decks != null &&
                gameUI != null) {
            for (Space space : gameBoard) { //Validates that there are no null Spaces
                if (space == null) {
                    throw new IllegalArgumentException("A null Space was passed");
                }

                if (space.getPROPERTY() != null) { //Validates that all Spaces fall into the passed color groups
                    boolean matchFound = false;
                    for (String string : colorGroups) {
                        if (string.equals(space.getPROPERTY().getCOLOR_GROUP())) {
                            matchFound = true;
                            break;
                        }
                    }

                    if (!matchFound) {
                        throw new IllegalArgumentException("A Property contained a color group that wasn't defined");
                    }
                }
            }

            for (Player player : players) { //Validates that there are no null Players
                if (player == null) {
                    throw new IllegalArgumentException("A null Player was passed");
                }
            }

            for (Dice die : dice) { //Validates that there are no null Dice
                if (die == null) {
                    throw new IllegalArgumentException("A null Dice was passed");
                }
            }

            for (Deck deck : decks) { //Validates that there are no null Decks
                if (deck == null) {
                    throw new IllegalArgumentException("A null Deck was passed");
                }
            }

            for (String colorGroup : colorGroups) {
                if (!validateColorGroup(colorGroup, gameBoard)) {
                    throw new IllegalArgumentException("An invalid color group was passed");
                }
            }

            GAME_BOARD = gameBoard;
            COLOR_GROUPS = colorGroups;
            JAIL_SPACE = jailSpace;
            JAIL_BAIL = jailBail;
            PLAYERS = players;
            DICE = dice;
            DECKS = decks;
            GAME_UI = gameUI;
            updatePropertiesRent(GAME_BOARD);
            currentPlayer = 0;
        } else {
            throw new IllegalArgumentException("A null or empty Array was passed");
        }
    }

    /*
    The following methods are static helper methods
     */

    /**
     * Updates the rents of the given spaces
     *
     * @param spaces the spaces to update the rents of, if they have Properties
     * @throws IllegalArgumentException when a null parameter is passed
     */
    private static void updatePropertiesRent(Space[] spaces) {
        if (spaces != null) {
            for (Space space : spaces) {
                if (space != null) {
                    if (space.getPROPERTY() != null) {
                        updateRent(space.getPROPERTY(), spaces);
                    }
                } else {
                    throw new IllegalArgumentException("A null Space was passed");
                }
            }
        } else {
            throw new IllegalArgumentException("A null parameter was passed");
        }
    }

    /**
     * Updates the rent of a given Property
     *
     * @param property the Property to update
     * @param spaces   the board to look at
     * @throws IllegalArgumentException when a null parameter is passed
     */
    private static void updateRent(Property property, Space[] spaces) {
        if (property != null && spaces != null) {
            property.setRent(playerPropertiesInColorGroup(property.getCOLOR_GROUP(), spaces, property.getOwner()).length,
                    playerHasMonopoly(property.getCOLOR_GROUP(), spaces, property.getOwner()));
        } else {
            throw new IllegalArgumentException("A null parameter was passed");
        }
    }

    /**
     * Validates a color group, meaning all of the Properties share the same multiplier and scaled states and only
     * own houses when they have the same owner as all of the other Properties in their color group
     *
     * @param colorGroup the color group to validate
     * @param spaces     the board to look at
     * @return true if the color group is valid, false if not
     * @throws IllegalArgumentException when a null parameter is passed
     */
    private static boolean validateColorGroup(String colorGroup, Space[] spaces) {
        if (colorGroup != null && spaces != null) {
            Property[] properties = propertiesInColorGroup(colorGroup, spaces);
            boolean isDiceMultiplier = properties[0].IS_DICE_MULTIPLIER();
            boolean isScaled = properties[0].IS_SCALED();
            Player owner = properties[0].getOwner();

            for (Property property : properties) {
                if (property == null) {
                    throw new IllegalArgumentException("A null Property was passed");
                }

                //Ensures all of the Properties share the same dice multiplier and scaled states
                if (isDiceMultiplier != property.IS_DICE_MULTIPLIER() || isScaled != property.IS_SCALED()) {
                    return false;
                }

                //Ensures that all of the Properties that have houses have the same owner/an owner
                if ((owner != property.getOwner() || property.getOwner() == null) && property.getNumHouses() > 0) {
                    return false;
                }
            }
            return true;
        } else {
            throw new IllegalArgumentException("A null parameter was passed");
        }
    }

    /**
     * Gets all of the Properties in a color group on a game board
     *
     * @param colorGroup the color group to look for
     * @param spaces     the game board to read
     * @return the Properties in a color group
     * @throws IllegalArgumentException when a null parameter is passed
     */
    private static Property[] propertiesInColorGroup(String colorGroup, Space[] spaces) {
        if (colorGroup != null && spaces != null) {
            ArrayList<Property> result = new ArrayList<>();
            for (Space space : spaces) {
                if (space.getPROPERTY() != null && space.getPROPERTY().getCOLOR_GROUP().equals(colorGroup)) {
                    result.add(space.getPROPERTY());
                }
            }

            return result.toArray(new Property[0]);
        } else {
            throw new IllegalArgumentException("A null parameter was passed");
        }
    }

    /**
     * Gets all of the Properties owned by a Player in a color group on a game board
     *
     * @param colorGroup the color group to look for
     * @param spaces     the game board to read
     * @param player     the Player to look for. This can be null, in which case an empty Array will be returned
     * @return the Properties in a color group owned by a Player
     */
    private static Property[] playerPropertiesInColorGroup(String colorGroup, Space[] spaces, Player player) {
        ArrayList<Property> result = new ArrayList<>();
        if (player != null) {
            for (Property property : propertiesInColorGroup(colorGroup, spaces)) {
                if (property.getOwner().equals(player)) {
                    result.add(property);
                }
            }
        }
        return result.toArray(new Property[0]);
    }

    /**
     * Gets all of the Properties that a Player owns
     *
     * @param spaces the game board to read
     * @param player the Player to look for. This can be null, in which case an empty Array will be returned
     * @return the Properties owned by Player
     * @throws IllegalArgumentException when a null parameter is passed (other than Player)
     */
    private static Property[] playerProperties(Space[] spaces, Player player) {
        if (spaces != null) {
            ArrayList<Property> result = new ArrayList<>();
            if (player != null) {
                for (Space space : spaces) {
                    if (space != null) {
                        if (space.getPROPERTY() != null && space.getPROPERTY().getOwner().equals(player)) {
                            result.add(space.getPROPERTY());
                        }
                    } else {
                        throw new IllegalArgumentException("A null Space was passed");
                    }
                }
            }

            return result.toArray(new Property[0]);
        } else {
            throw new IllegalArgumentException("A null Space Array was passed");
        }
    }

    /**
     * Gets an Array of all of the Players Properties that have buildings
     *
     * @param spaces the game board to read
     * @param player the Player to look for. This can be null, in which case an empty Array will be returned
     * @return an Array of all of the Players Properties that have buildings
     */
    private static Property[] playerPropertiesWithBuildings(Space[] spaces, Player player) {
        ArrayList<Property> result = new ArrayList<>();
        for (Property property : playerProperties(spaces, player)) {
            if (property.getNumHouses() > 0) {
                result.add(property);
            }
        }
        return result.toArray(new Property[0]);
    }

    /**
     * Gets an Array of all of the Players Properties that can be sold or mortgaged
     *
     * @param spaces the game board to read
     * @param player the Player to look for. This can be null, in which case an empty Array will be returned
     * @return an Array of all of the Players Properties that can be sold or mortgaged
     */
    private static Property[] playerSellableProperties(Space[] spaces, Player player) {
        ArrayList<Property> result = new ArrayList<>();
        for (Property property : playerProperties(spaces, player)) {
            if (property.canSell()) {
                result.add(property);
            }
        }
        return result.toArray(new Property[0]);
    }

    /**
     * Gets an Array of all of the Players Properties that can be built on
     *
     * @param spaces the game board to read
     * @param player the Player to look for. This can be null, in which case an empty Array will be returned
     * @return an Array of all of the Players Properties that can be sold or mortgaged
     */
    private static Property[] playerBuildableProperties(Space[] spaces, Player player) {
        ArrayList<Property> result = new ArrayList<>();
        for (Property property : playerProperties(spaces, player)) {
            if (property.canBuild() && playerHasMonopoly(property.getCOLOR_GROUP(), spaces, player)) {
                result.add(property);
            }
        }
        return result.toArray(new Property[0]);
    }

    /**
     * Gets an Array of all of the Players Properties that are mortgaged
     *
     * @param spaces the game board to read
     * @param player the Player to look for. This can be null, in which case an empty Array will be returned
     * @return an Array of all of the Players Properties that are mortgaged
     */
    private static Property[] playerMortgagedProperties(Space[] spaces, Player player) {
        ArrayList<Property> result = new ArrayList<>();
        for (Property property : playerProperties(spaces, player)) {
            if (property.isMortgaged()) {
                result.add(property);
            }
        }
        return result.toArray(new Property[0]);
    }

    /**
     * Gets the number of houses owned by the Player. This does not include hotels
     *
     * @param spaces the game board to read
     * @param player the Player to look for. This can be null, in which case 0 will be returned
     * @return the number of houses owned by the Player
     */
    private static int getNumHouses(Space[] spaces, Player player) {
        int result = 0;
        if (player != null) {
            for (Property property : playerPropertiesWithBuildings(spaces, player)) {
                if (property.getNumHouses() < property.getMAX_HOUSES()) { //If it's equal to the maximum number of houses it has a hotel
                    result += property.getNumHouses();
                }
            }
        }
        return result;
    }

    /**
     * Gets the number of hotels owned by the Player
     *
     * @param spaces the game board to read
     * @param player the Player to look for. This can be null, in which case 0 will be returned
     * @return the number of hotels owned by the Player
     */
    private static int getNumHotels(Space[] spaces, Player player) {
        int result = 0;
        if (player != null) {
            for (Property property : playerPropertiesWithBuildings(spaces, player)) {
                if (property.getNumHouses() == property.getMAX_HOUSES()) {
                    result++;
                }
            }
        }
        return result;
    }

    /**
     * Determines if a Player owns all of the Properties in a color group on a game board
     *
     * @param colorGroup the color group to look for
     * @param spaces     the game board to read
     * @param player     the Player to look for
     * @return whether the Player has a monopoly over a color group
     */
    private static boolean playerHasMonopoly(String colorGroup, Space[] spaces, Player player) {
        if (Arrays.equals(propertiesInColorGroup(colorGroup, spaces), playerPropertiesInColorGroup(colorGroup, spaces,
                player))) {
            for (Property property : playerPropertiesInColorGroup(colorGroup, spaces, player)) {
                if (property.isMortgaged()) {
                    return false;
                }
            }
            return true;
        } else {
            return false;
        }
    }

    /**
     * Finds all of the Cards owned by Player in the passed Decks
     *
     * @param player the Player to look for Cards owned by
     * @param decks  the decks to check for Cards owned by the Player
     * @return an Array of the Cards owned by the Player in all of the Decks
     * @throws IllegalArgumentException when a null parameter is passed
     */
    private static Card[] getOwnedCards(Player player, Deck[] decks) {
        ArrayList<Card> result = new ArrayList<>();
        if (player != null && decks != null) {
            for (Deck deck : decks) {
                if (deck != null) {
                    for (Card card : deck.getOwnedCards()) {
                        if (card != null) {
                            if (card.getOwner().equals(player)) {
                                result.add(card);
                            }
                        } else {
                            throw new IllegalArgumentException("A null Card was passed");
                        }
                    }
                }
                throw new IllegalArgumentException("A null Deck was passed");
            }
            return result.toArray(new Card[0]);
        } else {
            throw new IllegalArgumentException("A null Player or Deck Array was passed");
        }
    }

    /**
     * Gets the rolls from the Dice Array
     *
     * @param dice an Array of Dice. This shouldn't be null or contain any null Dice
     * @return the rolls from the Dice
     * @throws IllegalArgumentException when the passed Dice are null
     */
    private static int[] getRoll(Dice[] dice) {
        if (dice != null) {
            int[] result = new int[dice.length];
            for (int i = 0; i < result.length; i++) {
                if (dice[i] != null) {
                    result[i] = dice[i].getRoll();
                } else {
                    throw new IllegalArgumentException("A null Dice was passed");
                }
            }
            return result;
        } else {
            throw new IllegalArgumentException("A null or empty Dice Array was passed");
        }
    }

    /**
     * Determines whether or not a Player rolled doubles, meaning all of their rolls were the same
     *
     * @param rolls the Array of rolls the Player did
     * @return whether or not the Player rolled Doubles
     */
    private static boolean rolledDoubles(int[] rolls) {
        if (rolls != null) {
            int firstRoll = rolls[0];
            for (int roll : rolls) {
                if (roll != firstRoll) {
                    return false;
                }
            }
            return true;
        } else {
            throw new IllegalArgumentException("A null or empty rolls Array was passed");
        }
    }

    /**
     * Determines if this Player has a get out of jail free Card
     *
     * @param player the Player to check
     * @param decks  the Decks to look through
     * @return whether or not this Player has a get out of jail free Card
     */
    private static Card playerHasJailCard(Player player, Deck[] decks) {
        for (Card card : getOwnedCards(player, decks)) {
            if (card.IS_GET_OUT_JAIL()) {
                return card;
            }
        }
        return null;
    }

    /**
     * Gets the total value of the Players rolls
     *
     * @param rolls the Array of rolls the Player did
     * @return the total value of the Players rolls
     * @throws IllegalArgumentException when a null or empty rolls Array is passed
     */
    private static int calcRollTotal(int[] rolls) {
        if (rolls != null) {
            int result = 0;
            for (int roll : rolls) {
                result += roll;
            }
            return result;
        } else {
            throw new IllegalArgumentException("A null or empty rolls Array was passed");

        }
    }

    /**
     * Determines if other Players have get out of jail free Cards
     *
     * @param thisPlayer the Player who we shouldn't check
     * @param players    the Array of Players we should check
     * @param decks      the decks we should check
     * @return the list of Players that have jail cards
     * @throws IllegalArgumentException when a null parameter is passed
     */
    private static Player[] otherPlayerHasJailCard(Player thisPlayer, Player[] players, Deck[] decks) {
        if (thisPlayer != null && players != null && players.length > 0) {
            ArrayList<Player> result = new ArrayList<>();
            for (Player player : players) {
                if (player != null) {
                    if (!player.equals(thisPlayer)) {
                        if (playerHasJailCard(player, decks) != null) {
                            result.add(player);
                        }
                    }
                } else {
                    throw new IllegalArgumentException("A null Player was passed");
                }
            }
            return result.toArray(new Player[0]);
        } else {
            throw new IllegalArgumentException("A null or empty Player or Players Array was passed");
        }
    }

    /*
    The following methods will manage turn to turn play, excluding UI interaction
     */

    /**
     * This method covers all of the possible ways a Player can get out of jail during their turn
     *
     * @param rolls  the Array of rolls the Player did
     * @param player the Player that's in jail
     * @throws IllegalArgumentException when a null or empty rolls Array is passed
     * @throws IllegalStateException    when the Player is not in jail or not at the appropriate position
     */
    private void doJail(int[] rolls, Player player) {
        if (rolls != null && rolls.length > 0 && player != null) {
            if (player.getTurnInJail() > 0 && player.getPosition() == JAIL_SPACE) { //This ensures the Player is in jail
                GAME_UI.showDiceRoll(rolls);
                if (rolledDoubles(rolls)) { //If the Player rolled doubles they can get out now
                    player.move(calcRollTotal(rolls));
                } else if (playerHasJailCard(player, DECKS) != null) { //If the Player has a get out of jail free card, we should ask if they want to use it
                    if (player.promptBoolean("Would you like to use your get out of jail free card?")) {
                        Objects.requireNonNull(playerHasJailCard(player, DECKS)).setOwner(null); //This won't produce a NullPointerException as we check for it
                        player.move(calcRollTotal(rolls));
                    }
                } else if (playerHasJailCard(player, DECKS) == null && otherPlayerHasJailCard(player, PLAYERS, DECKS)
                        .length > 0) { //If someone else has a get out of jail free card, we should ask the Player if they want to trade for it
                    Trade jailTrade;
                    do { //We'll run this Trade until the Player decides not to run a Trade anymore
                        jailTrade = player.promptTrade("Would you like to trade another player for a get out of jail free card?",
                                otherPlayerHasJailCard(player, PLAYERS, DECKS));
                    } while (jailTrade != null);
                    if (playerHasJailCard(player, DECKS) != null) { //This ensures that the trade was successful and we're not just letting them out for free
                        Objects.requireNonNull(playerHasJailCard(player, DECKS)).setOwner(null); //This won't produce a NullPointerException as we check for it
                        player.move(calcRollTotal(rolls));
                    }
                } else if (player.promptBoolean("Would you like to pay your bail?")) { //If the Player wants to pay their bail, we should let them go
                    if (player.canAfford(-JAIL_BAIL)) {
                        player.updateWallet(-JAIL_BAIL);
                        player.move(calcRollTotal(rolls));
                    } else {
                        player.cannotAffordOptional();
                    }
                } else if (player.getTurnInJail() == 1) {
                    player.updateWallet(-JAIL_BAIL);
                    player.move(calcRollTotal(rolls));
                }
            } else {
                throw new IllegalStateException("Player is not in jail or is not at the jail but is in jail");
            }
        } else {
            throw new IllegalArgumentException("A null or empty parameter was passed");
        }
    }

    /**
     * Executes the movement part of the Players turn
     *
     * @param rolls  the Array of rolls the Player did
     * @param player the Player to move
     * @throws IllegalArgumentException when a null or empty parameter is passed
     * @throws IllegalStateException    when the Player is in jail
     */
    private void doMove(int[] rolls, Player player) {
        if (rolls != null && rolls.length > 0 && player != null) {
            if (player.getTurnInJail() == 0) {
                GAME_UI.showDiceRoll(rolls);
                player.move(calcRollTotal(rolls));
            } else {
                throw new IllegalStateException("The Player tried to move while in jail");
            }
        } else {
            throw new IllegalArgumentException("A null or empty parameter was passed");
        }
    }

    /**
     * Handles what happens when a Player lands on a Space
     *
     * @param space  the Space that the Player landed on
     * @param player the Player that landed on the Space
     * @throws IllegalArgumentException when a null or invalid parameter is passed
     */
    private void handleSpace(Space space, Player player) {
        GAME_UI.showSpace(space);
        if (space != null && player != null) {
            if (space.getPROPERTY() == null) { //If the Space doesn't have a Property, we should look for its penalty
                if (space.getMONEY_PENALTY() != 0) { //If this is the case then we should pay the Player that amount
                    player.updateWallet(space.getMONEY_PENALTY());
                } else if (space.getMOVEMENT_PENALTY() != 0) { //If this is the case, the Player should move that amount
                    player.move(space.getMONEY_PENALTY());
                } else if (space.getSPACE_PENALTY() != -1) { //If this is the case, the Player should go to that Space
                    player.goToSpace(space.getSPACE_PENALTY());
                } else if (space.getCOLOR_GROUP() != null) { //If this is the case, the Player should go to that color group
                    player.goToColorGroup(space.getCOLOR_GROUP());
                } else if (space.getPRICE_PER_HOUSE() != 0) { //If this is the case, the Player should pay that amount per house and then hotel (the two share the same state)
                    player.updateWallet(-space.getPRICE_PER_HOUSE() * getNumHouses(GAME_BOARD, player));
                    player.updateWallet(-space.getPRICE_PER_HOTEL() * getNumHotels(GAME_BOARD, player));
                } else if (space.getDECK_USED() != -1) { //If this is the case, the Player should draw from that deck
                    if (space.getDECK_USED() >= 0 && space.getDECK_USED() < DECKS.length) {
                        handleCard(DECKS[space.getDECK_USED()].getCard(), player);
                    } else {
                        throw new IllegalArgumentException("An invalid Space was passed");
                    }
                }
            } else {
                handleProperty(space.getPROPERTY(), player);
            }
        } else {
            throw new IllegalArgumentException("A null parameter was passed");
        }
    }

    /**
     * Handles what happens when a Player draws a Card
     *
     * @param card   the Card the Player drew
     * @param player the Player that drew the Card
     * @throws IllegalArgumentException when a null parameter is passed
     */
    private void handleCard(Card card, Player player) {
        GAME_UI.showCard(card);
        if (card != null && player != null) {
            if (card.getMONEY() != 0) { //If this is the case, the Player should gain this amount
                if (!card.isPER_PLAYER()) {
                    player.updateWallet(card.getMONEY());
                } else {
                    int total = 0;
                    for (Player otherPlayer : PLAYERS) { //We'll run through all of the Players, updating their wallet by getMONEY()
                        if (!otherPlayer.equals(player)) {
                            otherPlayer.updateWallet(card.getMONEY());
                            total += card.getMONEY();
                        }
                    }
                    player.updateWallet(-total); //Now we'll do the opposite of the total amount done to the other Players to this Player
                }
            } else if (card.getMOVEMENT() != 0) { //If this is the case, the Player should move this amount
                player.move(card.getMOVEMENT());
            } else if (card.getSPACE() != -1) { //If this is the case, the Player should go to this Space
                player.goToSpace(card.getSPACE());
            } else if (card.getCOLOR_GROUP() != null) { //If this is the case, the Player should go to this color group
                player.goToColorGroup(card.getCOLOR_GROUP());
            } else if (card.getPRICE_PER_HOUSE() != 0) { //If this is the case, the Player pay that amount per house and per hotel (they are linked)
                player.updateWallet(-card.getPRICE_PER_HOUSE() * getNumHouses(GAME_BOARD, player));
                player.updateWallet(-card.getPRICE_PER_HOTEL() * getNumHotels(GAME_BOARD, player));
            } else if (card.IS_GET_OUT_JAIL()) { //If this is the case, the Player should get this Card
                card.setOwner(player);
            }
        } else {
            throw new IllegalArgumentException("A null parameter was passed");
        }
    }

    /**
     * Handles what happens when a Player lands on a Space that has a Property
     *
     * @param property the Property the Player landed on
     * @param player   the Player who landed on the Space
     */
    private void handleProperty(Property property, Player player) {
        GAME_UI.showProperty(property);
        if (property != null && player != null) {
            if (property.getOwner() == null) { //If this is the case, the Player can buy the Property
                if (player.promptBoolean("Would you like to buy " + property + " for " +
                        property.getPRICE() + "?")) { //If this is the case, the Player will buy the Property
                    if (player.canAfford(property.getPRICE())) {
                        player.updateWallet(property.getPRICE());
                    } else {
                        player.cannotAffordOptional();
                    }
                    property.setOwner(player);
                } else { //If they didn't choose to buy it, we need to run an auction now
                    handleAuction(property);
                }
            } else {
                if (!property.getOwner().equals(player)) { //If this is the case, the Player owes the owner rent
                    player.updateWallet(property.getRent());
                    property.getOwner().updateWallet(-property.getRent());
                }
            }
        } else {
            throw new IllegalArgumentException("A null parameter was passed");
        }
    }

    /**
     * Runs an Auction for a Property
     *
     * @param property the Property being auctioned
     * @throws IllegalArgumentException when a null or owned Property is passed
     */
    private void handleAuction(Property property) {
        if (property != null && property.getOwner() == null) {
            ArrayList<Player> players = new ArrayList<>();
            for (Player player : PLAYERS) {
                if (player.promptBoolean("Would you like to participate in an auction for " +
                        property + "?")) {
                    players.add(player);
                }
            }

            if (players.size() > 0) {
                Auction auction = new Auction(property, players);
                do { //This will run through the Auction until the Auction Object determines it has a winner
                    auction.doRound();
                } while (!auction.isConfirmed());
            }
        } else {
            throw new IllegalArgumentException("A null or owned Property was passed");
        }
    }

    /**
     * Prompts the Player if they would like to sell buildings on any of the Properties they can.
     * Prompts over and over again until they decide against selling to allow them to sell multiple
     *
     * @param player                  the Player who is selling
     * @param propertiesWithBuildings the Properties owned by the Player that are eligible for sale
     * @throws IllegalArgumentException when a null or empty parameter is passed
     */
    private void promptSellBuildings(Player player, Property[] propertiesWithBuildings) {
        if (player != null && propertiesWithBuildings != null && propertiesWithBuildings.length > 0) {
            while (propertiesWithBuildings.length > 0) {
                int result = player.promptArray("Would you like to sell any houses?",
                        propertiesWithBuildings);
                if (result >= 0 && result < propertiesWithBuildings.length) {
                    if (propertiesWithBuildings[result].getOwner().equals(player)) {
                        propertiesWithBuildings[result].sellHouse();
                        propertiesWithBuildings = playerPropertiesWithBuildings(GAME_BOARD, player); //Updates to ensure the Player cannot remove a house when its no longer eligible
                    } else {
                        throw new IllegalArgumentException("A Property that wasn't owned by the Player was passed");
                    }
                } else {
                    break;
                }
            }
        } else {
            throw new IllegalArgumentException("A null or empty parameter was passed");
        }
    }

    /**
     * Prompts the Player if they would like to mortgage any of the Properties they can.
     * Prompts over and over again until they decide against selling to allow them to sell multiple
     *
     * @param player             the Player who is mortgaging
     * @param sellableProperties the Properties owned by the Player that are eligible for mortgage
     * @throws IllegalArgumentException when a null or empty parameter is passed
     */
    private void promptMortgage(Player player, Property[] sellableProperties) {
        if (player != null && sellableProperties != null && sellableProperties.length > 0) {
            while (sellableProperties.length > 0) {
                int result = player.promptArray("Would you like to mortgage any properties?", sellableProperties);
                if (result >= 0 && result < sellableProperties.length) {
                    if (sellableProperties[result].getOwner().equals(player)) {
                        sellableProperties[result].mortgage();
                        sellableProperties = playerSellableProperties(GAME_BOARD, player); //Updates to ensure the Player cannot double mortgage
                    } else {
                        throw new IllegalArgumentException("A Property that wasn't owned by the Player was passed");
                    }
                } else {
                    break;
                }
            }
        } else {
            throw new IllegalArgumentException("A null or empty parameter was passed");
        }
    }

    /**
     * Prompts the Player if they would like to un-mortgage any of the Properties they can.
     * Prompts over and over again until they decide against un-mortgage to allow them to un-mortgage multiple
     *
     * @param player                   the Player who is un-mortgaging
     * @param unMortgageableProperties the Properties owned by the Player that are eligible to be un-mortgaged
     * @throws IllegalArgumentException when a null or empty parameter is passed
     */
    private void promptUnMortgage(Player player, Property[] unMortgageableProperties) {
        if (player != null && unMortgageableProperties != null && unMortgageableProperties.length > 0) {
            while (unMortgageableProperties.length > 0) {
                int result = player.promptArray("Would you like to un-mortgage any properties?", unMortgageableProperties);
                if (result >= 0 && result < unMortgageableProperties.length) {
                    if (unMortgageableProperties[result].getOwner().equals(player)) {
                        unMortgageableProperties[result].unMortgage();
                        unMortgageableProperties = playerMortgagedProperties(GAME_BOARD, player);
                    } else {
                        throw new IllegalArgumentException("A Property that wasn't owned by the Player was passed");
                    }
                } else {
                    break;
                }
            }
        } else {
            throw new IllegalArgumentException("A null or empty parameter was passed");
        }
    }

    /**
     * Prompts the Player if they would like to buy any buildings for their Properties
     *
     * @param player              the Player who is buying
     * @param buildableProperties the Properties owned by the Player that are eligible to be built on
     * @throws IllegalArgumentException when a null or empty parameter is passed
     */
    private void promptBuyBuildings(Player player, Property[] buildableProperties) {
        if (player != null && buildableProperties != null && buildableProperties.length > 0) {
            while (buildableProperties.length > 0) {
                int result = player.promptArray("Would you like to build on any properties?", buildableProperties);
                if (result >= 0 && result < buildableProperties.length) {
                    if (buildableProperties[result].getOwner().equals(player)) {
                        buildableProperties[result].buyHouse();
                        buildableProperties = playerBuildableProperties(GAME_BOARD, player);
                    } else {
                        throw new IllegalArgumentException("A Property that wasn't owned by a Player was passed");
                    }
                } else {
                    break;
                }
            }
        } else {
            throw new IllegalArgumentException("A null or empty parameter was passed");
        }
    }

    /**
     * Updates who the currentPlayer is. Is cyclical, meaning once currentPlayer would go out of bounds for PLAYERS it goes to 0
     */
    public void nextPlayer() {
        if (currentPlayer + 1 < PLAYERS.length) {
            currentPlayer++;
        } else {
            currentPlayer = 0;
        }
    }

    /**
     * Executes all aspects of a turn of the Game
     */
    public void doTurn() {
        updatePropertiesRent(GAME_BOARD); //This just refreshes all of the rents to account for any changes that occurred last turn
        Player player = PLAYERS[currentPlayer];
        GAME_UI.showPlayerTurn(player);
        boolean playerStartsInJail = player.getTurnInJail() > 0;
        int startingPosition = player.getPosition();
        int[] rolls = getRoll(DICE);
        if (playerStartsInJail) {
            doJail(rolls, player);
        } else {
            doMove(rolls, player);
        }

        while (startingPosition != player.getPosition()) { //Theoretically the Player could go around the board forever depending on the moves, so until they don't move after the Space is processed we'll keep processing the Spaces
            startingPosition = player.getPosition();
            handleSpace(GAME_BOARD[player.getPosition()], player);
            updatePropertiesRent(GAME_BOARD); //This just refreshes all of the rents to account for any changes that occurred last turn
        }

        if (playerPropertiesWithBuildings(GAME_BOARD, player).length > 0) { //If the Player can sell any buildings, we should ask
            promptSellBuildings(player, playerPropertiesWithBuildings(GAME_BOARD, player));
        }

        if (playerSellableProperties(GAME_BOARD, player).length > 0) { //If the Player can mortgage any Properties, we should ask
            promptMortgage(player, playerSellableProperties(GAME_BOARD, player));
        }

        if (playerMortgagedProperties(GAME_BOARD, player).length > 0) { //If the Player can un-mortgage any Properties, we should ask
            promptUnMortgage(player, playerMortgagedProperties(GAME_BOARD, player));
        }

        if (playerBuildableProperties(GAME_BOARD, player).length > 0) { //If the Player can buy any buildings, we should ask
            promptBuyBuildings(player, playerBuildableProperties(GAME_BOARD, player));
        }

        Trade trade;
        do {
            trade = player.promptTrade("Would you like to trade?", PLAYERS);
        } while (trade != null);

        if (playerStartsInJail || !rolledDoubles(rolls)) { //If the player started in jail or didn't roll doubles the next Player will be the next in line
            nextPlayer();
        }
    }
}