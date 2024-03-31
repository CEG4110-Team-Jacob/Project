package org.example.Pages.Waiters;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Waiters extends JPanel {
    Details details;
    Menu menu;
    Order order;
    List<Integer> selectedPrices; // List to store the prices of selected items
    List<String> selectedItems; // List to store the names of selected items

    public Waiters() {
        setLayout(new BorderLayout());
        details = new Details();
        menu = new Menu();
        order = new Order();
        selectedPrices = new ArrayList<>();
        selectedItems = new ArrayList<>();
        add(details, BorderLayout.NORTH);
        add(menu, BorderLayout.CENTER);
        add(order, BorderLayout.SOUTH);
    }

    class Details extends JPanel {
        JLabel waiter;
        JLabel table;
        JLabel time;

        public Details() {
            setLayout(new FlowLayout(FlowLayout.LEFT, 10, 10));
            setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

            waiter = new JLabel("Waiter: ____");
            waiter.setBorder(BorderFactory.createLineBorder(Color.BLACK));
            add(waiter);

            table = new JLabel("Table: ____");
            table.setBorder(BorderFactory.createLineBorder(Color.BLACK));
            add(table);

            time = new JLabel("Time: ____");
            time.setBorder(BorderFactory.createLineBorder(Color.BLACK));
            add(time);
        }
    }

    class Order extends JPanel {
        JPanel total;
        JPanel items;
        JLabel totalPriceLabel;
        JTextArea selectedItemsArea; // TextArea to display selected items

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
            add(total, BorderLayout.WEST);

            items = new JPanel();
            items.setLayout(new BorderLayout());
            items.setBorder(BorderFactory.createLineBorder(Color.BLACK));

            selectedItemsArea = new JTextArea();
            selectedItemsArea.setEditable(false);
            JScrollPane scrollPane = new JScrollPane(selectedItemsArea);
            items.add(scrollPane, BorderLayout.CENTER);
            add(items, BorderLayout.CENTER);
        }

        // Update total price label based on the selected items
        public void updateTotalPrice() {
            int totalPrice = 0;
            for (Integer price : selectedPrices) {
                totalPrice += price;
            }
            totalPriceLabel.setText("$" + String.format("%.2f", totalPrice / 100.0)); // Display total price in dollars
        }

        // Update selected items list
        public void updateSelectedItems(String itemName, int itemPrice) {
            selectedItems.add(itemName);
            selectedPrices.add(itemPrice);
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < selectedItems.size(); i++) {
                String item = selectedItems.get(i);
                int price = selectedPrices.get(i);
                sb.append(item);
                // Add spaces to align prices to the right
                for (int j = 0; j < 30 - item.length(); j++) {
                    sb.append(" ");
                }
                sb.append("$").append(String.format("%.2f", price / 100.0)).append("\n");
            }
            selectedItemsArea.setText(sb.toString());
        }
        
    }

    class Menu extends JPanel {
        JPanel food;
        JPanel drinks;

        public Menu() {
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

            // Add price and item name to selectedPrices and selectedItems lists when button is clicked
            button.addActionListener(e -> {
                selectedPrices.add(price);
                order.updateTotalPrice(); // Update total price label
                order.updateSelectedItems(itemName, price); // Update selected items list with price
            });
        }
    }
}
