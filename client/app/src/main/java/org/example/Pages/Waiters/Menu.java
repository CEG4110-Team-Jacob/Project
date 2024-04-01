package org.example.Pages.Waiters;

import java.awt.Color;
import java.awt.Component;
import java.awt.GridLayout;
import java.util.function.Consumer;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import org.example.Data.controllers.General;
import org.example.Data.enums.ItemType;
import org.example.Data.records.Item;

class Menu extends JPanel {
    JPanel food;
    JPanel drinks;
    Item.ListItems items;
    Consumer<Item> select;

    public Menu(Consumer<Item> select) throws Exception {
        this.select = select;

        setLayout(new GridLayout(1, 2, 10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        items = General.setItems().get();

        food = new JPanel();
        food.setLayout(new GridLayout(0, 2, 5, 5));
        food.setBorder(BorderFactory.createTitledBorder("Food"));
        items.items().stream().filter(item -> item.type() == ItemType.Food).forEach(item -> {
            var itemPanel = createMenuItem(item);
            food.add(itemPanel);
        });

        drinks = new JPanel();
        drinks.setLayout(new GridLayout(0, 2, 5, 5));
        drinks.setBorder(BorderFactory.createTitledBorder("Drinks"));
        items.items().stream().filter(item -> item.type() == ItemType.Beverage).forEach(item -> {
            var itemPanel = createMenuItem(item);
            drinks.add(itemPanel);
        });

        add(new JScrollPane(food));
        add(new JScrollPane(drinks));
    }

    private Component createMenuItem(Item item) {
        JLabel priceLabel = new JLabel("$" + String.format("%.2f", item.price() / 100.0)); // Display price in
                                                                                           // dollars
        JPanel itemPanel = new JPanel();
        itemPanel.setLayout(new BoxLayout(itemPanel, BoxLayout.Y_AXIS));
        itemPanel.add(new JLabel("" + item.name()));
        itemPanel.add(priceLabel);
        if (!item.inStock()) {
            JLabel stockLabel = new JLabel("Out of Stock");
            stockLabel.setForeground(Color.RED);
            itemPanel.add(stockLabel);
        }
        JTextArea description = new JTextArea(item.description());
        description.setLineWrap(true);
        description.setWrapStyleWord(true);
        description.setEditable(false);
        itemPanel.add(new JScrollPane(description));

        JButton select = new JButton("Select");
        select.setEnabled(item.inStock());
        select.addActionListener(e -> {
            this.select.accept(item);
        });
        itemPanel.add(select);

        return itemPanel;
    }
}