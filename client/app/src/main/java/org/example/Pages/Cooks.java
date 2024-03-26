package org.example.Pages;

import org.example.Data.Data;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Cooks extends JPanel {
    private static final Color LIGHT_GRAY = new Color(240, 240, 240);
    private Heading heading;
    private Table tables;

    public Cooks() {
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        setBackground(Color.WHITE);

        heading = new Heading();
        add(heading, BorderLayout.NORTH);

        tables = new Table();
        JScrollPane scrollPane = new JScrollPane(tables);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        add(scrollPane, BorderLayout.CENTER);
    }

    class Heading extends JPanel {
        private JLabel waiterLabel;
        private JLabel assignTableLabel;

        public Heading() {
            setLayout(new FlowLayout(FlowLayout.LEFT, 10, 5));
            setBackground(Color.WHITE);

            waiterLabel = new JLabel("Waiter: ______");
            waiterLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
            add(waiterLabel);

            assignTableLabel = new JLabel("Tables Assigned: ______");
            assignTableLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
            add(assignTableLabel);
        }
    }

    class OrderUi extends JPanel {
        private JPanel orderPanel;
        private JPanel statusPanel;

        public OrderUi(Data.Order orderData) {
            setLayout(new BorderLayout());
            setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
            setBackground(LIGHT_GRAY);

            orderPanel = new JPanel();
            orderPanel.setLayout(new BoxLayout(orderPanel, BoxLayout.Y_AXIS));
            orderPanel.setBackground(LIGHT_GRAY);
            orderPanel.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
            orderPanel.add(new JLabel("<html><b>Order " + orderData.id() + "</b></html>"));
            for (String item : orderData.items()) {
                orderPanel.add(new JLabel(item));
            }
            add(orderPanel, BorderLayout.CENTER);

            statusPanel = new JPanel();
            statusPanel.setLayout(new BoxLayout(statusPanel, BoxLayout.Y_AXIS));
            statusPanel.setBackground(LIGHT_GRAY);
            statusPanel.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
            statusPanel.add(new JLabel("<html><b>Status</b></html>"));
            statusPanel.add(new JLabel(orderData.status()));
            add(statusPanel, BorderLayout.EAST);
        }
    }

    class Table extends JPanel {
        private ArrayList<OrderUi> orders;

        public Table() {
            setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
            setBackground(Color.WHITE);
            orders = new ArrayList<>();
            for (Data.Order order : Data.getOrders().values()) {
                OrderUi orderUi = new OrderUi(order);
                orders.add(orderUi);
                add(orderUi);
            }
        }
    }
}
