package org.example.Pages;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import org.example.Data.controllers.General;
import org.example.Data.records.Item;

public class ItemsUI extends JPanel {
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

        grid = new JPanel(new GridLayout(0, 5, 10, 10));
        topBar = new JPanel(new BorderLayout());
        topBar.add(new JLabel("Items"), BorderLayout.CENTER);
        exitButton = new JButton("Exit");
        exitButton.addActionListener(e -> exit.run());
        topBar.add(exitButton, BorderLayout.EAST);
        add(topBar, BorderLayout.NORTH);

        JScrollPane scrollPane = new JScrollPane(grid);
        add(scrollPane, BorderLayout.CENTER);
        try {
            update();
        } catch (Exception e) {
            e.printStackTrace();
            exit.run();
        }
    }

    public void update() throws Exception {
        var items = General.setItems().get();
        grid.removeAll();
        for (var item : items.items()) {
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
