package org.example.Components;

import javax.swing.*;

import java.awt.*;

// Modified ChatGPT Code
public class TablesUI extends JPanel {

    public final static int X = 10;
    public final static int Y = 10;

    // List of tables displayed
    public JPanel[][] tables;

    public TablesUI() {
        setLayout(new BorderLayout());

        tables = new JPanel[X][Y];

        // Creates a JPanel to accommodate the layout of tables
        JPanel centerPanel = new JPanel(new GridLayout(X, Y, 5, 5));
        var scrollPane = new JScrollPane(centerPanel);

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

    public void update(Runnable run) {
        // Clear all tables
        for (var tables : this.tables) {
            for (var table : tables) {
                table.removeAll();
            }
        }
        run.run();
        revalidate();
        repaint();
    }
}