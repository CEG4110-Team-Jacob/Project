package org.example.Pages.Waiters;

import javax.swing.*;
import java.awt.*;

public class Waiters extends JPanel {
    Details details;
    Menu menu;
    Order order;

    public Waiters() {
        setLayout(new BorderLayout());
        details = new Details();
        menu = new Menu();
        order = new Order();
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

        public Order() {
            setLayout(new BorderLayout());
            setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

            total = new JPanel();
            total.setLayout(new GridLayout(4, 2, 5, 5));
            total.setBorder(BorderFactory.createLineBorder(Color.BLACK));
            total.add(new JLabel("Total Price: "));
            total.add(new JLabel());
            total.add(new JLabel("Tip: "));
            total.add(new JLabel());
            total.add(new JLabel("Total: "));
            total.add(new JLabel());
            total.add(new JLabel("Status: "));
            total.add(new JLabel());
            add(total, BorderLayout.WEST);

            items = new JPanel();
            items.setLayout(new BoxLayout(items, BoxLayout.Y_AXIS));
            items.setBorder(BorderFactory.createLineBorder(Color.BLACK));
            add(items, BorderLayout.CENTER);
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
            food.add(new JButton("Fries"));
            food.add(new JButton("Burger"));
            food.add(new JButton("Pasta"));
            food.add(new JButton("Rice"));
            food.add(new JButton("Chicken"));
            food.add(new JButton("Sausage"));

            drinks = new JPanel();
            drinks.setLayout(new GridLayout(2, 2, 5, 5));
            drinks.setBorder(BorderFactory.createTitledBorder("Drinks"));
            drinks.add(new JButton("Coke"));
            drinks.add(new JButton("Water"));
            drinks.add(new JButton("Coffee"));
            drinks.add(new JButton("Beer"));

            add(food);
            add(drinks);
        }
    }
}
