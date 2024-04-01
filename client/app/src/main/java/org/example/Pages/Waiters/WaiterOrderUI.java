package org.example.Pages.Waiters;

import javax.swing.*;

import org.example.Components.ListUI;
import org.example.Data.controllers.Waiters.WaiterTable;
import org.example.Data.records.Item;

import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
            menu = new Menu();
        } catch (Exception e) {
            e.printStackTrace();
            exit.run();
        }
        order = new Order();
        selectedItems = new ArrayList<>();
        add(details, BorderLayout.NORTH);
        add(menu, BorderLayout.CENTER);
        add(order, BorderLayout.WEST);
    }

    class Details extends JPanel {
        JLabel waiter;
        JLabel table;
        JLabel time;

        public Details(WaiterTable tableData) {
            setLayout(new FlowLayout(FlowLayout.LEFT, 10, 10));
            setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

            waiter = new JLabel("Waiter: " + tableData.waiter().firstName() + " " + tableData.waiter().lastName());
            waiter.setBorder(BorderFactory.createLineBorder(Color.BLACK));
            add(waiter);

            table = new JLabel("Table: " + tableData.number());
            table.setBorder(BorderFactory.createLineBorder(Color.BLACK));
            add(table);

            time = new JLabel("Time: " + new SimpleDateFormat("HH:MM").format(new Date()));
            time.setBorder(BorderFactory.createLineBorder(Color.BLACK));
            add(time);
        }
    }

    class Order extends JPanel {
        JPanel total;
        JPanel items;
        JLabel totalPriceLabel;
        ListUI selectedItemsArea; // TextArea to display selected items

        public Order() {
            setLayout(new BorderLayout());
            setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

            total = new JPanel();
            total.setLayout(new GridLayout(4, 2, 5, 5));
            total.setBorder(BorderFactory.createLineBorder(Color.BLACK));
            total.add(new JLabel("Total Price: "));
            totalPriceLabel = new JLabel("$0.00"); // Initialize total price label
            total.add(totalPriceLabel);
            total.add(new JLabel("Tip: "));
            total.add(new JLabel());
            total.add(new JLabel("Total: "));
            total.add(new JLabel());
            total.add(new JLabel("Status: "));
            total.add(new JLabel());
            add(total, BorderLayout.SOUTH);

            selectedItemsArea = new ListUI();
            selectedItemsArea.setBorder(BorderFactory.createLineBorder(Color.BLACK));
            add(selectedItemsArea, BorderLayout.CENTER);
        }
    }

    class Menu extends JPanel {
        JPanel food;
        JPanel drinks;

        public Menu() throws Exception {
            setLayout(new GridLayout(1, 2, 10, 10));
            setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

            food = new JPanel();
            food.setLayout(new GridLayout(3, 2, 5, 5));
            food.setBorder(BorderFactory.createTitledBorder("Food"));
            addMenuItem(food, "Fries", 399); // Prices are in cents
            addMenuItem(food, "Burger", 599);
            addMenuItem(food, "Pasta", 899);
            addMenuItem(food, "Rice", 299);
            addMenuItem(food, "Chicken", 799);
            addMenuItem(food, "Sausage", 699);

            drinks = new JPanel();
            drinks.setLayout(new GridLayout(2, 2, 5, 5));
            drinks.setBorder(BorderFactory.createTitledBorder("Drinks"));
            addMenuItem(drinks, "Coke", 199);
            addMenuItem(drinks, "Water", 99);
            addMenuItem(drinks, "Coffee", 249);
            addMenuItem(drinks, "Beer", 499);

            add(food);
            add(drinks);
        }

        private void addMenuItem(JPanel panel, String itemName, int price) {
            JButton button = new JButton(itemName);
            JLabel label = new JLabel("$" + String.format("%.2f", price / 100.0)); // Display price in dollars
            JPanel itemPanel = new JPanel(new BorderLayout());
            itemPanel.add(button, BorderLayout.CENTER);
            itemPanel.add(label, BorderLayout.SOUTH);
            panel.add(itemPanel);

            // Add price and item name to selectedPrices and selectedItems lists when button
            // is clicked
            button.addActionListener(e -> {
                // selectedItems.add(price);
                // order.updateTotalPrice(); // Update total price label
                // order.updateSelectedItems(itemName, price); // Update selected items list
                // with price
            });
        }
    }
}
