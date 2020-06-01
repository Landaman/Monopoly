/**
 * A collection of static methods that set up the Game. This could be integrated into Game, but is left out for neatness
 *
 * @author irswr
 */
public class GameCreator {
    public static Game makeGame(
            //Space parameters
            String[] spaceNames, int[] spaceMoneyLosses, int[] spaceMovementLosses, int[] spaceSpaceLosses,
            int[] spaceDeckUsed, String[] spaceColorGroups, int[] spacePerHouses,
            int[] spacePerHotels,
            //Property parameters
            boolean[] spaceHaveProperty, int[] propertyPrices, int[] propertyMortgages, int[] propertyBuildPrices,
            String[] propertyColorGroups, int[] propertyMaxHouses, int[][] propertyRents, int[] propertyStartingHouses,
            boolean[] propertyAreDiceMultipliers, boolean[] propertyAreScaled, boolean[] propertyAreMortgaged,
            Player[] propertyOwners,
            //Game parameters
            String[] colorGroups, int jailPosition, int bailCost,
            //Player parameters
            String[] playerNames, String[] playerTypes, int[] playerWallets, int[] playerPositions,
            int[] playerJailCards, int[] playerSalaries,
            //Dice parameters
            int numDice, int diceSides,
            //Deck parameters
            String[][] cardTypes, String[][] cardDescriptions, int[][] cardMoneyLosses, boolean[][] cardPerPlayer,
            int[][] cardMovementLosses, int[][] cardSpaceLosses, String[][] cardColorGroup, int[][] cardPerHouses,
            int[][] cardPerHotels, boolean[][] cardGetOutJail, Player[][] cardOwners) {
        Space[] spaces = setupSpaces(
                //Space parameters
                spaceNames, spaceMoneyLosses, spaceMovementLosses, spaceSpaceLosses, spaceColorGroups, spacePerHouses,
                spacePerHotels, spaceDeckUsed, cardTypes.length,
                //Property parameters
                spaceHaveProperty, propertyPrices, propertyMortgages,
                propertyBuildPrices, propertyColorGroups, propertyMaxHouses, propertyRents, propertyStartingHouses,
                propertyAreDiceMultipliers, propertyAreScaled, propertyAreMortgaged, propertyOwners);
        Player[] players = setupPlayers(playerNames, playerTypes, playerWallets, playerPositions, spaces.length,
                playerJailCards, jailPosition, playerSalaries);
        Dice[] dice = setupDice(numDice, diceSides);
        Deck[] deck = setupDecks(cardTypes, cardDescriptions, cardMoneyLosses, cardPerPlayer, cardMovementLosses,
                cardSpaceLosses, cardColorGroup, cardPerHouses, cardPerHotels, cardGetOutJail, cardOwners, spaces.length);

        return new Game(spaces, colorGroups, jailPosition, bailCost, dice, deck, );
    }

    /*
    The following methods manage Space creation
     */

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
     * @param perHouses                 the amount the Player should pay per house. Shouldn't be less than 0
     * @param perHotels                 the amount the Player should pay per hotel. Shouldn't be less than 0
     * @param deckUsed                  the index of the Deck that this Space should use. Shouldn't be less than -1, which represents no
     *                                  Deck
     * @param numDecks                  the number of Decks. Used for validation, is not stored
     * @param haveProperty              whether or not these Space's have a Property. Should be false if not
     * @param propertyPrices            the price of this Property. This should be greater than 0
     * @param propertyMortgages         the mortgage of these Property. This should be greater than 0 and should be less then the price
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
            int[] perHouses, int[] perHotels, int[] deckUsed, int numDecks,
            //Property parameters
            boolean[] haveProperty, int[] propertyPrices, int[] propertyMortgages, int[] propertyBuildPrices,
            String[] propertyColorGroups, int[] propertyMaxHouses, int[][] propertyRents, int[] propertyStartingHouses,
            boolean[] propertyAreDiceMultiplier, boolean[] propertyAreScaled, boolean[] propertyAreMortgaged,
            Player[] propertyOwners) {
        if (names != null && moneyLosses != null && movementLosses != null && spaceLosses != null && //Space validation
                colorGroups != null && perHouses != null && perHotels != null && deckUsed != null &&
                names.length == moneyLosses.length && moneyLosses.length == movementLosses.length &&
                movementLosses.length == spaceLosses.length && spaceLosses.length == colorGroups.length &&
                colorGroups.length == perHouses.length && perHouses.length == perHotels.length &&
                perHotels.length == deckUsed.length &&
                //Property validation
                haveProperty != null && propertyPrices != null && propertyMortgages != null &&
                propertyBuildPrices != null && propertyColorGroups != null && propertyMaxHouses != null &&
                propertyRents != null && propertyStartingHouses != null && propertyAreDiceMultiplier != null &&
                propertyAreScaled != null && propertyAreMortgaged != null && propertyOwners != null &&
                deckUsed.length == haveProperty.length && haveProperty.length == propertyPrices.length &&
                propertyPrices.length == propertyMortgages.length &&
                propertyMortgages.length == propertyBuildPrices.length &&
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
                        names[i], moneyLosses[i], movementLosses[i], spaceLosses[i], colorGroups[i], perHouses[i],
                        perHotels[i], deckUsed[i], spaces.length, numDecks,
                        //Property parameters
                        haveProperty[i], propertyPrices[i], propertyMortgages[i], propertyBuildPrices[i],
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
     * @param perHouse                 the amount the Player should pay per house owned. Shouldn't be less than 0
     * @param perHotel                 the amount the Player should pay per hotel owned. Shouldn't be less than 0
     * @param deckUsed                 the index of the Deck that this Card should use. Shouldn't be less than -1, which represents no
     *                                 deck used
     * @param boardSize                the board's size. Used for validation, is not stored
     * @param numDecks                 the number of Decks. Used for validation, is not stored
     * @param hasProperty              whether or not this Space has a Property. Should be false if not
     * @param propertyPrice            the price of this Property. This should be greater than 0
     * @param propertyMortgage         the mortgage of this Property. This should be greater than 0 and less than the price
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
            String name, int moneyLoss, int movementLoss, int spaceLoss, String colorGroup, int perHouse, int perHotel,
            int deckUsed, int boardSize, int numDecks,
            //Property parameters
            boolean hasProperty, int propertyPrice, int propertyMortgage, int propertyBuildPrice,
            String propertyColorGroup, int propertyMaxHouses, int[] propertyRents, int propertyStartingHouses,
            boolean propertyIsDiceMultiplier, boolean propertyIsScaled, boolean propertyIsMortgaged,
            Player propertyOwner) {
        if (hasProperty) {
            return new Space(
                    //Space creation
                    name, moneyLoss, movementLoss, spaceLoss, colorGroup, perHouse, perHotel, deckUsed, boardSize, numDecks,
                    //Property creation
                    new Property(propertyPrice, propertyMortgage, propertyBuildPrice, propertyColorGroup,
                            propertyMaxHouses, propertyRents, propertyStartingHouses, propertyIsDiceMultiplier,
                            propertyIsScaled, propertyIsMortgaged, propertyOwner, name));
        } else {
            return new Space(name, moneyLoss, movementLoss, spaceLoss, colorGroup, perHouse, perHotel, deckUsed,
                    boardSize, numDecks, null);
        }
    }

    /*
    The following methods manage Player creation
     */

    /**
     * Sets up an Array of Players. Validates according to the conditions outlined here. All of these Arrays
     * should have the same length
     *
     * @param names        the names of the Players to be created. This shouldn't be null
     * @param types        the types of the Players to be created (human or AI). This shouldn't be null or anything else
     * @param wallets      the amount of money that should be placed in the Player's wallet
     * @param positions    the position that the Player should start at
     * @param boardSize    the max board size, this is used for validation. This shouldn't be 0 or less than any of the
     *                     position values
     * @param jailTurns    the number of turns the Player has in jail. Should be 0 for none
     * @param jailPosition the position of the jail on the board. This must be at a valid position
     * @param salaries     the salaries the Players should be awarded for passing go
     * @return the created Players Array
     * @throws IllegalArgumentException when a null or mismatched Array is passed
     */
    private static Player[] setupPlayers(String[] names, String[] types, int[] wallets, int[] positions, int boardSize,
                                         int[] jailTurns, int jailPosition, int[] salaries) {
        if (names != null && types != null && wallets != null && positions != null && jailTurns != null && salaries != null &&
                names.length == types.length && types.length == wallets.length && wallets.length == positions.length &&
                positions.length == jailTurns.length && jailTurns.length == salaries.length) {
            Player[] players = new Player[names.length];
            for (int i = 0; i < players.length; i++) {
                players[i] = setupPlayer(names[i], types[i], wallets[i], positions[i], boardSize, jailTurns[i],
                        jailPosition, salaries[i]);
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
     * @param name         the name of the Player to be created. This shouldn't be null
     * @param type         the type of the Player to be created (human or ai). This shouldn't be null or anything else
     * @param wallet       the amount of money that should be placed in the Player's wallet
     * @param position     the position that the Player should start at
     * @param boardSize    the max board size, this is used for validation This shouldn't be 0 or less than position
     * @param jailTurns    the number of turns the Player has in jail. Should be 0 for none
     * @param jailPosition the position of the jail on the game board. This must be at a valid position
     * @param salary       the salaries the Players should be awarded for passing go. This must be greater than 0
     * @return the created Player Object
     * @throws IllegalArgumentException when type is not ai or human
     */
    private static Player setupPlayer(String name, String type, int wallet, int position, int boardSize, int jailTurns,
                                      int jailPosition, int salary) {
        if (type.equals("ai")) {
            return new AIPlayer(name, wallet, position, boardSize, jailTurns, jailPosition, salary);
        } else if (type.equals("human")) {
            return new HumanPlayer(name, wallet, position, boardSize, jailTurns, jailPosition, salary);
        } else {
            throw new IllegalArgumentException("An invalid type value was passed");
        }
    }

    /*
    The following methods manage Dice creation
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

    /*
    The following methods manage Deck setup
     */

    /**
     * Sets up an Array of Decks. Validates according to the conditions outlined here. None of these Arrays can be null,
     * but the values in them may be null
     *
     * @param cardTypes        the types of Cards to be created. This shouldn't be null
     * @param cardDescriptions the descriptions of the Cards to be created. This shouldn't be null
     * @param cardMoney        the amount of money the Player should gain from each of these Cards. This shouldn't be null
     * @param cardPerPlayer    whether or not the value of cardMoney should be applied to each other Player, and then have
     *                         the opposite done to the normal Player. This shouldn't be null
     * @param cardMovement     the amount of Spaces the Player should move for landing using these Cards. This shouldn't be null
     * @param cardSpace        the index of the Space the Player should go to for getting these Cards. This shouldn't be null
     * @param cardColorGroup   the index of the Space the Player should go to for getting these Cards. This shouldn't be null
     * @param cardPerHouse     the amount of money the Player should pay per house owned. This shouldn't be null
     * @param cardPerHotel     the amount of money the Player should pay per hotel owned. This shouldn't be null
     * @param cardGetOutJail   whether or not these Cards are get out of jail cards. This shouldn't be null
     * @param cardOwner        the Player who holds these cards. This shouldn't be null
     * @param boardSize        the board's size. Used for validation, this isn't stored
     * @return the completed Array of Decks
     * @throws IllegalArgumentException when a null or mismatched Array is passed
     */
    private static Deck[] setupDecks(String[][] cardTypes, String[][] cardDescriptions, int[][] cardMoney,
                                     boolean[][] cardPerPlayer, int[][] cardMovement, int[][] cardSpace,
                                     String[][] cardColorGroup, int[][] cardPerHouse, int[][] cardPerHotel,
                                     boolean[][] cardGetOutJail, Player[][] cardOwner, int boardSize) {
        if (cardTypes != null && cardDescriptions != null && cardMoney != null && cardPerPlayer != null &&
                cardMovement != null && cardSpace != null && cardColorGroup != null && cardPerHouse != null &&
                cardPerHotel != null && cardGetOutJail != null && cardOwner != null &&
                cardTypes.length == cardDescriptions.length && cardDescriptions.length == cardMoney.length &&
                cardMoney.length == cardPerPlayer.length && cardPerPlayer.length == cardMovement.length &&
                cardMovement.length == cardSpace.length && cardSpace.length == cardColorGroup.length &&
                cardColorGroup.length == cardPerHouse.length && cardPerHouse.length == cardPerHotel.length &&
                cardPerHotel.length == cardGetOutJail.length && cardGetOutJail.length == cardOwner.length) {
            Deck[] decks = new Deck[cardTypes.length];
            for (int i = 0; i < decks.length; i++) {
                decks[i] = setupDeck(cardTypes[i], cardDescriptions[i], cardMoney[i], cardPerPlayer[i], cardMovement[i],
                        cardSpace[i], cardColorGroup[i], cardPerHouse[i], cardPerHotel[i], cardGetOutJail[i],
                        cardOwner[i], boardSize);
            }
            return decks;
        } else {
            throw new IllegalArgumentException("A null or mismatched Array was passed");
        }

    }

    /**
     * Sets up a Deck. Validates according to the conditions outlined here. None of these Arrays can be null,
     * but the values in them may be null
     *
     * @param cardTypes        the types of Cards to be created. This shouldn't be null
     * @param cardDescriptions the descriptions of the Cards to be created. This shouldn't be null
     * @param cardMoney        the amount of money the Player should gain from each of these Cards. This shouldn't be null
     * @param cardPerPlayer    whether or not the value of cardMoney should be applied to each other Player, and then have
     *                         the opposite done to the normal Player. This shouldn't be null
     * @param cardMovement     the amount of Spaces the Player should move for landing using these Cards. This shouldn't be null
     * @param cardSpace        the index of the Space the Player should go to for getting these Cards. This shouldn't be null
     * @param cardColorGroup   the index of the Space the Player should go to for getting these Cards. This shouldn't be null
     * @param cardPerHouse     the amount of money the Player should pay per house owned. This shouldn't be null
     * @param cardPerHotel     the amount of money the Player should pay per hotel owned. This shouldn't be null
     * @param cardGetOutJail   whether or not these Cards are get out of jail cards. This shouldn't be null
     * @param cardOwner        the Player who holds these cards. This shouldn't be null
     * @param boardSize        the board's size. Used for validation, this isn't stored
     * @return the completed Deck
     * @throws IllegalArgumentException when a null or mismatched Array is passed
     */
    private static Deck setupDeck(String[] cardTypes, String[] cardDescriptions, int[] cardMoney,
                                  boolean[] cardPerPlayer, int[] cardMovement, int[] cardSpace, String[] cardColorGroup,
                                  int[] cardPerHouse, int[] cardPerHotel, boolean[] cardGetOutJail, Player[] cardOwner,
                                  int boardSize) {
        if (cardTypes != null && cardDescriptions != null && cardMoney != null && cardPerPlayer != null &&
                cardMovement != null && cardSpace != null && cardColorGroup != null && cardPerHouse != null &&
                cardPerHotel != null && cardGetOutJail != null && cardOwner != null &&
                cardTypes.length == cardDescriptions.length && cardDescriptions.length == cardMoney.length &&
                cardMoney.length == cardPerPlayer.length && cardPerPlayer.length == cardMovement.length &&
                cardMovement.length == cardSpace.length && cardSpace.length == cardColorGroup.length &&
                cardColorGroup.length == cardPerHouse.length && cardPerHouse.length == cardPerHotel.length &&
                cardPerHotel.length == cardGetOutJail.length && cardGetOutJail.length == cardOwner.length) {
            Card[] cards = new Card[cardTypes.length];
            for (int i = 0; i < cards.length; i++) {
                cards[i] = new Card(cardTypes[i], cardDescriptions[i], cardMoney[i], cardPerPlayer[i], cardMovement[i],
                        cardSpace[i], cardColorGroup[i], cardPerHouse[i], cardPerHotel[i], cardGetOutJail[i],
                        cardOwner[i], boardSize);
            }
            return new Deck(cards);
        } else {
            throw new IllegalArgumentException("A null or mismatched Array was passed");
        }

    }
}
