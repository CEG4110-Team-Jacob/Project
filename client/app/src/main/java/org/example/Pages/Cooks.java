package org.example.Pages;

import javax.swing.*;

import org.example.Data.Data;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Cooks extends JPanel {
    Heading heading;
    Table tables;

    public Cooks() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        heading = new Heading();
        add(heading);
        tables = new Table();
        add(tables);
    }

    class Heading extends JPanel {
        JLabel waiter;
        JLabel assignTable;

        public Heading() {
            waiter = new JLabel();
            waiter.setText("Waiter: ____");
            waiter.setBorder(BorderFactory.createLineBorder(new Color(0, 0, 0), 1));
            add(waiter);

            assignTable = new JLabel();
            assignTable.setText("Tables Assigned: ____");
            // assignTable.setBorder(BorderFactory.createLineBorder(new Color(0, 0, 0), 1));
            add(assignTable);
        }
    }

    class OrderUi extends JPanel {
        JPanel order;
        JPanel status;

        public OrderUi(Data.Order orderData) {
            setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
            setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
            order = new JPanel();
            order.setLayout(new BoxLayout(order, BoxLayout.Y_AXIS));
            order.add(new JLabel("Order " + orderData.id()));
            for (String item : orderData.items()) {
                order.add(new JLabel(item));
            }
            add(order);
            this.status = new JPanel();
            this.status.setLayout(new BoxLayout(this.status, BoxLayout.Y_AXIS));
            this.status.add(new JLabel("Status"));
            this.status.add(new JLabel(orderData.status()));
            add(this.status);
        }
    }

    class Table extends JPanel {
        ArrayList<OrderUi> orders;

        public Table() {
            orders = new ArrayList<>();
            setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
            for (Data.Order order : Data.getOrders().values()) {
                OrderUi orderUi = new OrderUi(order);
                orders.add(orderUi);
                add(orderUi);
            }
        }
    }
}
