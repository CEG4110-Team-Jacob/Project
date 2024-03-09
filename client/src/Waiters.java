
import javax.swing.*;
import javax.swing.border.EmptyBorder;

import java.awt.*;

public class Waiters extends JPanel {
    Details details;
    Menu menu;
    Order order;

    public Waiters() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        details = new Details();
        menu = new Menu();
        order = new Order();
        add(details);
        add(menu);
        add(order);
    }
}

class Details extends JPanel {
    JLabel waiter;
    JLabel table;
    JLabel time;

    public Details() {
        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        //
        waiter = new JLabel();
        waiter.setText("Waiter: ____");
        waiter.setBorder(BorderFactory.createLineBorder(new Color(0, 0, 0), 1));
        add(waiter);

        table = new JLabel();
        table.setBorder(BorderFactory.createLineBorder(new Color(0, 0, 0), 1));
        table.setText("Table: ____");
        add(table);

        time = new JLabel();
        time.setBorder(BorderFactory.createLineBorder(new Color(0, 0, 0), 1));
        time.setText("Time: ____");
        add(time);
    }
}

class Order extends JPanel {
    JPanel total;
    JPanel items;

    public Order() {
        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));

        total = new JPanel();
        total.setLayout(new BoxLayout(total, BoxLayout.Y_AXIS));
        total.add(new JLabel("Total Price: "));
        total.add(new JLabel("Tip: "));
        total.add(new JLabel("Total: "));
        total.add(new JLabel("Status: "));
        total.setBorder(BorderFactory.createLineBorder(new Color(0, 0, 0), 10));
        add(total);

        items = new JPanel();
        items.setLayout(new BoxLayout(items, BoxLayout.Y_AXIS));
        total.setBorder(BorderFactory.createLineBorder(new Color(0, 0, 0), 1));
        add(items);
    }
}

class Menu extends JPanel {
    JPanel food;
    JPanel drinks;

    public Menu() {
        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));

        food = new JPanel();
        food.setLayout(new GridLayout(2, 3));
        food.add(new JLabel("Fries"));
        food.add(new JLabel("Burger"));
        food.add(new JLabel("Pasta"));
        food.add(new JLabel("Rice"));
        food.add(new JLabel("Chicken"));
        food.add(new JLabel("Sausage"));
        add(food);

        drinks = new JPanel();
        drinks.setLayout(new GridLayout(2, 2));
        drinks.add(new JLabel("Coke"));
        drinks.add(new JLabel("Water"));
        drinks.add(new JLabel("Coffee"));
        drinks.add(new JLabel("Beer"));
        add(drinks);
    }
}