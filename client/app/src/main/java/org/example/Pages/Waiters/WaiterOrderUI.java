package org.example.Pages.Waiters;

import javax.swing.*;

import org.example.Data.controllers.Waiters;
import org.example.Data.controllers.Waiters.WaiterPostOrder;
import org.example.Data.controllers.Waiters.WaiterTable;
import org.example.Data.records.Item;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

// Modified ChatGPT Code
public class WaiterOrderUI extends JPanel {
    Details details;
    Menu menu;
    Order order;

    List<Item> selectedItems; // List to store the prices of selected items

    Runnable exit;

    WaiterTable table;

    public WaiterOrderUI(WaiterTable table, Runnable exit) {
        this.exit = exit;
        this.table = table;

        setLayout(new BorderLayout());
        details = new Details(table);
        try {
            menu = new Menu((item) -> addToList(item));
        } catch (Exception e) {
            e.printStackTrace();
            exit.run();
        }
        order = new Order();
        selectedItems = new ArrayList<>();

        JPanel buttons = new JPanel(new FlowLayout());
        var createButton = new JButton("Create");
        createButton.addActionListener(e -> {
            WaiterPostOrder order = new WaiterPostOrder(selectedItems.stream().map(item -> item.id()).toList(),
                    table.id());
            Optional<Integer> response = Waiters.addOrder(order);
            if (response.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Failed to create Order");
                return;
            }
            exit.run();
        });
        var exitButton = new JButton("Exit");
        exitButton.addActionListener(e -> exit.run());
        buttons.add(createButton);
        buttons.add(exitButton);

        add(details, BorderLayout.NORTH);
        add(menu, BorderLayout.CENTER);
        add(order, BorderLayout.WEST);
        add(buttons, BorderLayout.SOUTH);
    }

    private void updateList() {
        var itemsListUI = order.selectedItemsArea;
        itemsListUI.clear();
        for (int i = 0; i < selectedItems.size(); i++) {
            var item = selectedItems.get(i);
            JPanel itemDetails = new JPanel(new BorderLayout());
            String price = "$" + String.format("%.2f", item.price() / 100.0);
            itemDetails.add(new JLabel(item.name() + "- " + price), BorderLayout.CENTER);

            JButton delete = new JButton("X");
            int index = i;
            delete.addActionListener(e -> {
                selectedItems.remove(index);
                updateList();
            });
            itemDetails.add(delete, BorderLayout.EAST);

            itemDetails.setMaximumSize(new Dimension(itemsListUI.getWidth(), itemsListUI.getHeight() / 10));
            itemsListUI.list.add(itemDetails);
        }
        itemsListUI.update();
        order.update(selectedItems);
    }

    private void addToList(Item item) {
        selectedItems.add(item);
        updateList();
    }
}
