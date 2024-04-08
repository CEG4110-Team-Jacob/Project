package org.example.Components;

import javax.swing.*;

import java.awt.*;

// Modified ChatGPT Code
/**
 * Shows the tables
 */
public class TablesUI extends JPanel {

    /**
     * Restaurant X Size
     */
    public final static int X = 10;
    /**
     * Restaurant Y Size
     */
    public final static int Y = 10;

    /**
     * List of displayed tables
     */
    public JPanel[][] tables;

    public TablesUI() {
        setLayout(new BorderLayout());

        tables = new JPanel[X][Y];

        // Creates a JPanel to accommodate the layout of tables
        JPanel centerPanel = new JPanel(new GridLayout(X, Y, 5, 5));
        var scrollPane = new JScrollPane(centerPanel);

        // Create a default panel for each table
        for (int i = 0; i < X; i++) {
            for (int j = 0; j < Y; j++) {
                var panel = new JPanel();
                panel.setBorder(BorderFactory.createLineBorder(Color.black));
                tables[i][j] = panel;
                centerPanel.add(panel);
            }
        }

        add(scrollPane, BorderLayout.CENTER);
    }

    /**
     * Updates the UI
     * 
     * @param run Run after clearing the UI and repainting the UI
     */
    public void update(Runnable run) {
        // Clear all tables
        for (var tables : this.tables) {
            for (var table : tables) {
                table.removeAll();
            }
        }
        try {
            run.run();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        revalidate();
        repaint();
    }
}