package org.example.Components;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import org.example.Data.controllers.General;
import org.example.Data.records.Item;

/**
 * UI for Items list
 */
public class ItemsUI extends JPanel {
    /**
     * Functional Interface to create items
     */
    public interface ElementCreator {
        JPanel create(Item item, Runnable update);
    }

    JPanel grid;
    ElementCreator creator;
    protected JPanel topBar;
    protected JButton exitButton;

    public ItemsUI(Runnable exit, ElementCreator eCreator) {
        setLayout(new BorderLayout());
        creator = eCreator;
        // Set layout as Grid
        grid = new JPanel(new GridLayout(0, 5, 10, 10));
        // Create peripherals
        topBar = new JPanel(new BorderLayout());
        topBar.add(new JLabel("Items"), BorderLayout.CENTER);
        exitButton = new JButton("Exit");
        exitButton.addActionListener(e -> exit.run());
        topBar.add(exitButton, BorderLayout.EAST);
        add(topBar, BorderLayout.NORTH);
        // Pane that shows the items
        JScrollPane scrollPane = new JScrollPane(grid);
        add(scrollPane, BorderLayout.CENTER);
        try {
            update();
        } catch (Exception e) {
            e.printStackTrace();
            exit.run();
        }
    }

    /**
     * Updates the contents
     * 
     * @throws Exception
     */
    public void update() throws Exception {
        // Get the items
        var items = General.setItems().get();
        // Clear the UI
        grid.removeAll();
        // Loop through tiems
        for (var item : items.items()) {
            // Create panel based on CreatorElement
            var panel = creator.create(item, () -> {
                try {
                    update();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
            grid.add(panel);
        }
        revalidate();
        repaint();
    }
}
