package business.estructuraTaules.taula;

import javax.swing.*;
import java.awt.*;

/**
 * Represents the guilty chart.
 */
public class GuiltyChart {

    /**
     * Generates the chart using the data passed as parameters which are the number of professions in the table.
     *
     * @param kings number of kings in the table.
     * @param knights number of knights in the table.
     * @param queens number of queens in the table.
     * @param minstrels number of minstrels in the table.
     * @param peasants number of peasants in the table.
     * @param shrubbers number of shrubbers in the table.
     * @param clergymans number of clergymans in the table.
     * @param enchanters number of enchanters in the table.
     */
    public void generateChart(int kings, int knights, int queens, int minstrels, int peasants, int shrubbers, int clergymans, int enchanters) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("GUILTY CHART");
            frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
            frame.setSize(950, 600); // Amplia la mida de la finestra

            JPanel panel = new JPanel() {
                @Override
                protected void paintComponent(Graphics g) {
                    super.paintComponent(g);

                    Graphics2D g2d = (Graphics2D) g;
                    int[] data = {kings, knights, queens, minstrels, peasants, shrubbers, clergymans, enchanters}; // Valors de les columnes
                    String[] columnNames = {"KINGS", "KNIGHTS", "QUEENS", "MINSTRELS", "PEASANTS", "SHRUBBERS", "CLERGYMANS", "ENCHANTERS"}; // Noms de les columnes

                    int numColumns = data.length;
                    int panelWidth = getWidth();
                    int panelHeight = getHeight();
                    int columnWidth = (int) (panelWidth / (numColumns * 1.5));
                    int columnSpacing = 20;
                    int startX = (panelWidth - (numColumns * columnWidth + (numColumns - 1) * columnSpacing)) / 2;

                    int maxDataValue = getMaxValue(data);

                    for (int i = 0; i < numColumns; i++) {
                        int barHeight = (int) ((double) data[i] / maxDataValue * (panelHeight - 50));

                        int x = startX + i * (columnWidth + columnSpacing);
                        int y = panelHeight - barHeight - 30;

                        Color columnColor = getColumnColor(i);
                        g2d.setColor(columnColor);
                        g2d.fillRect(x, y, columnWidth, barHeight);

                        g2d.setColor(Color.BLACK);
                        g2d.drawString(columnNames[i], x, panelHeight - 10);
                        g2d.drawString(String.valueOf(data[i]), x, y - 5);
                    }
                }
            };

            frame.getContentPane().add(panel);
            frame.setVisible(true);
        });
    }

    /**
     * Returns the maximum value of the data to know the height of the columns.
     *
     * @param data The data.
     * @return The maximum value of the data.
     */
    private static int getMaxValue(int[] data) {
        int max = Integer.MIN_VALUE;
        for (int value : data) {
            if (value > max) {
                max = value;
            }
        }
        return max;
    }

    /**
     * Returns the color of the column, the color is hardcoded to be always the same.
     *
     * @param i The index of the column.
     * @return The color of the column.
     */
    private static Color getColumnColor(int i) {
        switch (i) {
            case 0 -> {
                return Color.RED;
            }
            case 1 -> {
                return Color.BLUE;
            }
            case 2 -> {
                return Color.GREEN;
            }
            case 3 -> {
                return Color.YELLOW;
            }
            case 4 -> {
                return Color.ORANGE;
            }
            case 5 -> {
                return Color.PINK;
            }
            case 6 -> {
                return Color.MAGENTA;
            }
            case 7 -> {
                return Color.CYAN;
            }
        }
        return Color.BLACK;
    }
}
