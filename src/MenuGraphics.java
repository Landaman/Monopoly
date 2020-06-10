import javax.swing.*;
import java.awt.*;

/**
 * Represents the menu graphics for monopoly
 * @author irswr
 */
public class MenuGraphics extends JPanel {
    /**
     * Paints the background for the Menu screen
     *
     * @param g the Graphics Object to paint with
     */
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawBackground(g);
        drawMonopolyLogo(g, getWidth() * 3 / 4, getHeight() / 4, getWidth() / 2 - getWidth() * 3 / 4 / 2, getHeight() / 6);
    }

    /**
     * Draws a cloud (three ovals) at the given coordinates
     *
     * @param g      the Graphics Object to draw with
     * @param x      the x-coordinate to start drawing at
     * @param y      the y-coordinate to start drawing at
     * @param width  the width of the three clouds
     * @param height the height of the two clouds. The middle one will be 25% bigger
     */
    private static void drawCloud(Graphics g, int x, int y, int width, int height) {
        if (g != null && x >= 0 && y >= 0 && width >= 0 && height >= 0) {
            g.setColor(Color.white);
            g.fillOval(x, y, width / 3, height);
            g.fillOval(x + width / 6, y, width / 3, height * 5 / 4);
            g.fillOval(x + width / 3, y, width / 3, height);
        } else {
            throw new IllegalArgumentException("An invalid parameter was passed");
        }
    }

    /**
     * @param g            the Graphics Object to draw with
     * @param color        the color to draw
     * @param windowColor  the color to draw the windows
     * @param width        the width of the building to draw
     * @param height       the height of the building to draw
     * @param x            the x-coordinate to draw at
     * @param screenHeight the height of the screen, as buildings are always drawn at the bottom
     * @throws IllegalArgumentException when an invalid parameter is passed
     */
    private static void drawBuilding(Graphics g, Color color, Color windowColor, int width, int height, int x,
                                     int screenHeight) {
        if (g != null && color != null && width > 0 && height > 0 && x >= 0 && screenHeight >= 0) {
            g.setColor(color);
            g.fillRect(x, screenHeight - height, width, height);
            g.setColor(windowColor);
            for (int i = 0; i < 10; i++) {
                for (int j = 0; j < 2; j++) { //This draws windows on the building
                    g.fillRect(x + width / 16 + j * width / 2, screenHeight - height + height / 32 + i * height / 10,
                            width / 3, height / 25);
                }
            }
        } else {
            throw new IllegalArgumentException("An invalid parameter was passed");
        }
    }

    /**
     * Draws the monopoly logo at the given size at the given coordinates
     *
     * @param g      the Graphics Object to draw it with
     * @param width  the width of the Rectangle
     * @param height the height of the Rectangle
     * @param x      the x-coordinate to start drawing the Rectangle at
     * @param y      the y-coordinate to start drawing the Rectangle at
     * @throws IllegalArgumentException when an invalid parameter is passed
     */
    public static void drawMonopolyLogo(Graphics g, int width, int height, int x, int y) {
        if (g != null && width > 0 && height > 0 && x >= 0 && y >= 0) {
            g.setColor(Color.RED);
            int arcWidth = width / 15;
            int arcHeight = width / 15;

            g.fillRoundRect(x, y, width, height, arcWidth, arcHeight);

            Font font = new Font("Kabel Heavy", Font.PLAIN, 20);
            g.setFont(font);
            FontMetrics metrics = g.getFontMetrics(font);
            int fontHeight = metrics.getHeight();
            int fontWidth = metrics.stringWidth("MONOPOLY");
            font = g.getFont().deriveFont((float) width / fontWidth * 20.0f); //This resizes the font to fit the rectangle
            g.setFont(font);
            metrics = g.getFontMetrics(font);
            fontHeight = metrics.getHeight();
            fontWidth = metrics.stringWidth("MONOPOLY");
            g.setColor(Color.WHITE);
            g.drawString("MONOPOLY", x + width / 2 - fontWidth / 2, y + fontHeight - metrics.getDescent());
        } else {
            throw new IllegalArgumentException("An invalid parameter was passed");
        }
    }

    /**
     * Draws the background for the menu screen
     *
     * @param g the Graphics Object to draw with
     * @throws IllegalArgumentException when a null Graphics Object is passed
     */
    private void drawBackground(Graphics g) {
        if (g != null) {
            setBackground(Color.CYAN);

            for (int i = 0; i < 4; i++) { //This draws 4 evenly-spaced clouds at the top of the screen
                drawCloud(g, i * getWidth() / 4 + getWidth() / 16, getWidth() / 64 + (int) (50 * Math.random()),
                        getWidth() / 5, getHeight() / 16);
            }

            for (int i = 0; i < 10; i++) { //This draws 10 evenly-spaced skyscrapers at the bottom of the screen
                drawBuilding(g, Color.GRAY, Color.YELLOW, getWidth() / 11, getHeight() / 3 + (int) (50 * Math.random()),
                        i * getWidth() / 10, getHeight());
            }
        } else {
            throw new IllegalArgumentException("A null Graphics Object was passed");
        }
    }
}
