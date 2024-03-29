package org.example.Pages.Cooks;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import javax.swing.Timer;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import org.example.Data.controllers.Cooks;
import org.example.Data.controllers.Cooks.CookOrder;

public class OrdersUI extends JPanel {
    OrdersStuff gridPanel;
    Timer timer;

    /**
     * OrdersStuff
     */
    public class OrdersStuff extends JPanel {

        public OrdersStuff() throws Exception {
            setLayout(new GridLayout(0, 3, 10, 10));

            JScrollPane scrollPane = new JScrollPane(this);
            scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

            var orders = Cooks.getOrders().get();
            for (var order : orders.orders()) {
                add(new OrderUI(order));
            }
        }

        public void update() {
            removeAll();
            var orders = Cooks.getOrders().get();
            for (var order : orders.orders()) {
                add(new OrderUI(order));
            }
            revalidate();
            repaint();
        }

        /**
         * OrderUI
         */
        public class OrderUI extends JButton {
            public OrderUI(CookOrder order) {
                setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

                add(new JLabel("Id: " + Integer.toString(order.id())));
                add(new JLabel("Status: " + order.status().toString()));
                add(new JLabel("Time ordered: " + new SimpleDateFormat("HH:mm").format(order.timeOrdered())));
            }
        }
    }

    public OrdersUI(Runnable exit) {
        setLayout(new BorderLayout());
        // Update every 5 seconds
        timer = new Timer(5000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Updated");
                gridPanel.update();
            }
        });
        JPanel topBar = new JPanel(new BorderLayout());
        topBar.add(new JLabel("Orders"), BorderLayout.WEST);
        var exitButton = new JButton("Exit");
        exitButton.addActionListener(e -> {
            timer.stop();
            exit.run();
        });
        topBar.add(exitButton, BorderLayout.EAST);

        add(topBar, BorderLayout.NORTH);

        try {
            gridPanel = new OrdersStuff();
            add(gridPanel, BorderLayout.CENTER);
            timer.start();
        } catch (Exception e) {
            e.printStackTrace();
            exit.run();
        }
    }
}
