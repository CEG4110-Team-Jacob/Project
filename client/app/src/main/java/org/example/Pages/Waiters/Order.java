package org.example.Pages.Waiters;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.example.Components.ListUI;
import org.example.Data.records.Item;

class Order extends JPanel {
    JPanel totalPanel;
    JPanel items;
    JLabel totalPriceLabel;
    public ListUI selectedItemsArea;
    JLabel tip;
    private JLabel totalLabel;

    public Order() {
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        totalPanel = new JPanel();
        totalPanel.setLayout(new GridLayout(4, 2, 5, 5));
        totalPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        totalPanel.add(new JLabel("Total Price: "));
        totalPriceLabel = new JLabel("$0.00"); // Initialize total price label
        totalPanel.add(totalPriceLabel);
        totalPanel.add(new JLabel("Tip: "));
        totalLabel = new JLabel();
        tip = new JLabel();
        totalPanel.add(tip);
        totalPanel.add(new JLabel("Total: "));
        totalPanel.add(totalLabel);
        add(totalPanel, BorderLayout.SOUTH);

        selectedItemsArea = new ListUI();
        selectedItemsArea.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        add(selectedItemsArea, BorderLayout.CENTER);
    }

    public void update(List<Item> items) {
        int total = items.stream().mapToInt(item -> item.price()).sum();
        totalPriceLabel.setText(String.format("$%.2f", total / 100.0));
        tip.setText("$0.00");
        totalLabel.setText(String.format("$%.2f", total / 100.0));
    }
}