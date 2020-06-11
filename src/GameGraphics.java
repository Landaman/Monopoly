import javax.swing.*;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.util.Arrays;

/**
 * Represents the graphics for the monopoly game board
 *
 * @author irswr
 */
public class GameGraphics extends JPanel {
    private static final Color BACKGROUND_COLOR = new Color(203, 228, 207);
    private static final Color BROWN = new Color(148, 83, 55);
    private static final Color LIGHT_BLUE = new Color(170, 224, 250);
    private static final Color PINK = new Color(215, 58, 150);
    private static final Color ORANGE = new Color(247, 148, 29);
    private static final Color RED = new Color(237, 27, 36);
    private static final Color YELLOW = new Color(254, 242, 0);
    private static final Color GREEN = new Color(31, 176, 91);
    private static final Color DARK_BLUE = new Color(0, 113, 186);
    //GameGraphics constants
    private final Game GAME; //The Game Object in use

    /**
     * Constructs the Game's Graphics
     *
     * @param game the Game Object that represents this Game
     */
    public GameGraphics(Game game) {
        if (game != null) {
            GAME = game;
        } else {
            throw new IllegalArgumentException("A null Game Object was passed");
        }
    }

    /**
     * Draws this Game's Players
     *
     * @param g       the Graphics Object to draw with
     * @param players the Players to draw
     * @param startX  the x-coordinate the board starts at
     * @param startY  the y-coordinate the board starts at
     * @param width   the width of the board
     * @param height  the height of the board
     * @throws IllegalArgumentException when an invalid parameter is passed
     */
    private static void drawPlayers(Graphics g, Player[] players, int startX, int startY, int width, int height) {
        if (players != null && players.length > 0) {
            for (Player player : players) {
                drawPlayer(g, player.getCOLOR(), player.getPosition(), player.getBOARD_SIZE(), startX, startX, width, height);
            }
        } else {
            throw new IllegalArgumentException("An invalid parameter was passed");
        }
    }

    /**
     * Draws a Player at the given coordinates
     *
     * @param g         the Graphics Object to draw with
     * @param color     the Color to draw the player
     * @param numSpaces the number of Spaces on the board
     * @param startX    the x-coordinate the board starts at
     * @param startY    the y-coordinate the board starts at
     * @param width     the width of the board
     * @param height    the height of the board
     * @throws IllegalArgumentException when an invalid parameter is passed
     */
    private static void drawPlayer(Graphics g, Color color, int playerPosition, int numSpaces, int startX, int startY,
                                   int width, int height) {
        if (g != null && color != null && numSpaces > playerPosition && playerPosition >= 0 && numSpaces % 4 == 0 & startX >= 0 &&
                startY >= 0 && width > 0 && height > 0) {
            Graphics2D g2 = (Graphics2D) g;
            g2.setColor(color);
            double spaceWidth = (double) width / ((double) numSpaces / 4 + 1);
            double spaceHeight = (double) height / ((double) numSpaces / 4 + 1);
            int randomX = (int) ((spaceWidth - spaceWidth / 4) * Math.random());
            int randomY = (int) ((spaceHeight - spaceWidth / 4) * Math.random());
            if (playerPosition < numSpaces / 4) { //If the Player is on the first side of the board
                g2.fill(new Ellipse2D.Double(startX + width - spaceWidth * (playerPosition + 1) +
                        randomX, startY + height - spaceHeight + randomY, spaceWidth / 4, spaceHeight / 4));
            } else if (playerPosition < numSpaces / 2) { //If the Player is on the fourth side of the board
                g2.fill(new Ellipse2D.Double(startX + randomX, startY + height - spaceHeight * (playerPosition -
                        (double) numSpaces / 4 + 1) + randomY, spaceWidth / 4, spaceHeight / 4));
            } else if (playerPosition < numSpaces * 3 / 4) { //If the Player is on the third side of the board
                g2.fill(new Ellipse2D.Double(startX + spaceWidth * (playerPosition - (double) numSpaces / 2) + randomX,
                        startY + randomY, spaceWidth / 4, spaceHeight / 4));
            } else {
                g2.fill(new Ellipse2D.Double(startX + width - spaceWidth + randomX, startY + spaceHeight *
                        (playerPosition - (double) numSpaces * 3 / 4) + randomY, spaceWidth / 4, spaceHeight / 4));
            }
        } else {
            throw new IllegalArgumentException("An invalid parameter was passed");
        }
    }

    /**
     * Draws the game board
     *
     * @param g      the Graphics Object to draw with
     * @param game   the Game Object
     * @param x      the x-coordinate to start drawing at
     * @param y      the y-coordinate to start drawing at
     * @param width  the width to draw the board
     * @param height the height to draw the board
     * @throws IllegalArgumentException when a null Graphics Object is passed
     */
    public static void drawGameBoard(Graphics g, Game game, int x, int y, int width, int height) {
        if (g != null && game != null) {
            g.setColor(BACKGROUND_COLOR);
            g.fillRect(x, y, width, height);
            MenuGraphics.drawMonopolyLogo(g, width / 2, height / 6, x + width / 2 - width / 4, y + height / 2 -
                    height / 6);
            drawCardPools(g, game.getDECKS(), x + width / 4, height * 3 / 4, width * 2 / 3, height / 8);
            drawBoardSpaces(g, game.getGAME_BOARD(), x, y, width, height);
        } else {
            throw new IllegalArgumentException(" A null Graphics Object was passed");
        }
    }

    /**
     * Draws the areas that the Decks are kept
     *
     * @param g      the Graphics Object to draw with
     * @param decks  the Decks to draw
     * @param x      the x-coordinate to start at
     * @param y      the y-coordinate to start at
     * @param width  the total width
     * @param height the height
     * @throws IllegalArgumentException when an invalid parameter is passed
     */
    private static void drawCardPools(Graphics g, Deck[] decks, int x, int y, int width, int height) {
        if (g != null && decks != null && x >= 0 && y >= 0 && width > 0 && height > 0) {
            int deckWidth;
            if (decks.length < 4) { //If there's less than 4 decks we can use a fixed value for the width
                deckWidth = width / 4;
            } else {
                deckWidth = width / decks.length - width / 10;
            }
            g.setFont(new Font("Kabel Heavy", Font.BOLD, 20));
            g.setFont(g.getFont().deriveFont((float) deckWidth / g.getFontMetrics().stringWidth("  COMMUNITY  ") * 20));
            for (int i = 0; i < decks.length; i++) {
                g.setColor(Color.ORANGE);
                g.fillRect(x + width / decks.length * i, y, deckWidth, height);
                int stringX = x + width / decks.length * i + deckWidth / 2;
                int stringY = y + g.getFontMetrics().getHeight();
                g.setColor(Color.BLACK);
                if (decks[i].getTYPE().contains(" ")) {
                    stringX -= g.getFontMetrics().stringWidth(decks[i].getTYPE().substring(0, decks[i].getTYPE().
                            lastIndexOf(" "))) / 2;
                    g.drawString(decks[i].getTYPE().substring(0, decks[i].getTYPE().lastIndexOf(" ")), stringX,
                            stringY);
                    stringX += g.getFontMetrics().stringWidth(decks[i].getTYPE().substring(0, decks[i].getTYPE().
                            lastIndexOf(" "))) / 2;
                    stringX -= g.getFontMetrics().stringWidth(decks[i].getTYPE().substring(decks[i].getTYPE().
                            lastIndexOf(" "))) / 2;
                    g.drawString(decks[i].getTYPE().substring(decks[i].getTYPE().lastIndexOf(" ")), stringX,
                            stringY + g.getFontMetrics().getHeight());
                } else {
                    stringX -= g.getFontMetrics().stringWidth(decks[i].getTYPE()) / 2;
                    g.drawString(decks[i].getTYPE(), stringX, stringY);
                }
            }
        }
    }

    /**
     * Draws the Spaces on the game board at the given coordinates
     *
     * @param g      the Graphics Object to draw with
     * @param spaces the Spaces to draw
     * @param x      the x-coordinate to start at
     * @param y      the y-coordinate to start at
     * @param width  the width of the board
     * @param height the height of the board
     * @throws IllegalArgumentException when an invalid parameter is passed
     */
    private static void drawBoardSpaces(Graphics g, Space[] spaces, int x, int y, int width, int height) {
        if (spaces != null && spaces.length > 0 && spaces.length % 4 == 0) {
            Font font = new Font("Kabel Heavy", Font.BOLD, 20);
            int boardSideLength = spaces.length / 4;
            font = font.deriveFont((float) Math.round((double) width / (boardSideLength + 1) /
                    g.getFontMetrics(font).stringWidth("    BOARDWALK    ") * font.getSize()));
            drawRow(g, Arrays.copyOfRange(spaces, 0, boardSideLength), 0, x + width, y + height, //First we draw the bottom row
                    width, (double) height / (boardSideLength + 1), font);
            drawRow(g, Arrays.copyOfRange(spaces, boardSideLength, 2 * boardSideLength), 1, x, y + height,  //Then we draw the left row
                    (double) width / (boardSideLength + 1), height, font);
            drawRow(g, Arrays.copyOfRange(spaces, 2 * boardSideLength, 3 * boardSideLength), 2, x, y, width, //Then we draw the top row
                    (double) height / (boardSideLength + 1), font);
            drawRow(g, Arrays.copyOfRange(spaces, 3 * boardSideLength, spaces.length), 3, x + width, //Then we draw the left row
                    y, (double) width / (boardSideLength + 1), height, font);
        } else {
            throw new IllegalArgumentException("An invalid parameter was passed");
        }

    }

    /**
     * Draws a row of Spaces on the board
     *
     * @param g         the Graphics Object to draw with
     * @param spaces    the Spaces to draw
     * @param direction the direction to draw in (0 is right to left, 1 is bottom to top, 2 is left to right,
     *                  3 is top to bottom)
     * @param x         the x-coordinate to start drawing at (the farthest over)
     * @param y         the y-coordinate to start drawing at (the farthest over)
     * @param width     the width of the combined Spaces
     * @param height    the height of the combined Spaces
     * @param font      the Font that should be used to draw the text
     * @throws IllegalArgumentException when an invalid parameter is passed
     */
    private static void drawRow(Graphics g, Space[] spaces, int direction, double x, double y, double width, double height,
                                Font font) {
        if (spaces != null && spaces.length > 0 && x >= 0 && y >= 0 && width > 0 && height > 0) {
            if (direction == 0) { //Right to left
                for (int i = 0; i < spaces.length; i++) {
                    drawBoardSpace(g, spaces[i], x - width / (spaces.length + 1) * (i + 1),
                            y - height, width / (spaces.length + 1), height, font);
                }
            } else if (direction == 1) { //Bottom to top
                for (int i = 0; i < spaces.length; i++) {
                    drawBoardSpace(g, spaces[i], x, y - height / (spaces.length + 1) * (i + 1),
                            width, height / (spaces.length + 1), font);
                }
            } else if (direction == 2) { //Left to right
                for (int i = 0; i < spaces.length; i++) {
                    drawBoardSpace(g, spaces[i], x + width / (spaces.length + 1) * i, y,
                            width / (spaces.length + 1), height, font);
                }
            } else if (direction == 3) { //Top to bottom
                for (int i = 0; i < spaces.length; i++) {
                    drawBoardSpace(g, spaces[i], x - width, y + height / (spaces.length + 1) * i,
                            width, height / (spaces.length + 1), font);
                }
            } else {
                throw new IllegalArgumentException("An invalid direction was passed");
            }
        } else {
            throw new IllegalArgumentException("An invalid parameter was passed");
        }
    }

    /**
     * Recognizes the Color of the given color group
     *
     * @param colorGroup the color group to recognize
     * @return the Color of a given color group
     */
    public static Color recognizeColorGroup(String colorGroup) {
        return switch (colorGroup) {
            case "BROWN" -> BROWN;
            case "LIGHT BLUE" -> LIGHT_BLUE;
            case "PINK" -> PINK;
            case "ORANGE" -> ORANGE;
            case "RED" -> RED;
            case "YELLOW" -> YELLOW;
            case "GREEN" -> GREEN;
            case "DARK BLUE" -> DARK_BLUE;
            default -> null;
        };
    }

    /**
     * Draws a Space on the board at the given coordinates
     *
     * @param g      the Graphics Object to draw with
     * @param space  the Space to draw
     * @param x      the x-coordinate of the Space to draw
     * @param y      the y-coordinate of the Space to draw
     * @param width  the width of the Space that should be drawn
     * @param height the height of the Space that should be drawn
     * @param font   the Font that should be used to draw the text
     * @throws IllegalArgumentException when an invalid parameter is passed
     */
    private static void drawBoardSpace(Graphics g, Space space, double x, double y, double width, double height, Font
            font) {
        if (g != null && space != null && x >= 0 && y >= 0 && width > 0 && height > 0) {
            Graphics2D g2 = (Graphics2D) g;
            g2.setColor(BACKGROUND_COLOR);
            g2.fill(new Rectangle.Double(x, y, width, height));
            Color color = null;
            if (space.getPROPERTY() != null) {
                String colorGroup = space.getPROPERTY().getCOLOR_GROUP();
                color = recognizeColorGroup(colorGroup);
                if (color != null) { //If the Color isn't null, we should draw the colored box on the top
                    g2.setStroke(new BasicStroke());
                    g2.setColor(color);
                    g2.fill(new Rectangle.Double(x, y, width, height / 8));
                }
            }
            g2.setColor(Color.BLACK);
            g2.setFont(font);
            FontMetrics metrics = g2.getFontMetrics();
            double fontWidth = metrics.stringWidth(space.toString());
            double fontHeight = metrics.getHeight();
            String name = space.toString();
            double textX = x + width / 2 - fontWidth / 2;
            double textY = y + fontHeight + (color == null ? 0 : height / 8);
            if (name.contains(" ")) {
                fontWidth = metrics.stringWidth(name.substring(0, name.lastIndexOf(" ")));
                textX = x + width / 2 - fontWidth / 2;
                g2.drawString(name.substring(0, name.lastIndexOf(" ")), (float) textX, (float) (textY));
                fontWidth = metrics.stringWidth(name.substring(name.lastIndexOf(" ")));
                textX = x + width / 2 - fontWidth / 2;
                g2.drawString(name.substring(name.lastIndexOf(" ")), (float) textX, (float) (textY + fontHeight));
            } else {
                g2.drawString(name, (float) textX, (float) textY);
            }

            if (space.getPROPERTY() != null) {
                g2.setFont(new Font(g2.getFont().getName(), Font.PLAIN, (int) Math.round(g2.getFont().getSize() / 1.25)));
                metrics = g2.getFontMetrics();
                fontWidth = metrics.stringWidth("$" + space.getPROPERTY().getPRICE());
                g2.drawString("$" + space.getPROPERTY().getPRICE(), (float) (x + width / 2 - fontWidth / 2),
                        (float) (y + height * 7 / 8));
            }
            g2.setColor(Color.BLACK);
            double lineWidth = width / 25;
            g2.setStroke(new BasicStroke((float) lineWidth));
            g2.draw(new Rectangle.Double(x, y, width, height));
        }
    }

    /**
     * Draws the game board
     *
     * @param g the Graphics Object to draw with
     */
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawGameBoard(g, GAME, 0, 0, getWidth(), getHeight());
        drawPlayers(g, GAME.getPLAYERS(), 0, 0, getWidth(), getHeight());
    }
}