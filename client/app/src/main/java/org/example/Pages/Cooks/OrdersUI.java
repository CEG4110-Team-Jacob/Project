package org.example.Pages.Cooks;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import javax.swing.Timer;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;

import org.example.Data.controllers.Cooks;
import org.example.Data.controllers.Cooks.CookOrder;
import org.example.Data.controllers.Cooks.PostSetStatus;
import org.example.Data.enums.Status;

public class OrdersUI extends JPanel {
    OrdersStuff gridPanel;
    Timer timer;

    /**
     * OrdersStuff
     */
    public class OrdersStuff extends JPanel {
        JPanel grid;

        public OrdersStuff() throws Exception {
            setLayout(new BorderLayout());

            grid = new JPanel(new GridLayout(0, 3, 10, 10));

            JScrollPane scrollPane = new JScrollPane(grid);
            scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
            add(scrollPane, BorderLayout.CENTER);
            update();
        }

        public void update() {
            grid.removeAll();
            var orders = Cooks.setOrders().get();
            for (var order : orders.orders()) {
                grid.add(new OrderUI(order, this::update));
            }
            revalidate();
            repaint();
        }

        /**
         * OrderUI
         */
        public class OrderUI extends JButton {
            public OrderUI(CookOrder order, Runnable update) {
                setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

                add(new JLabel("Id: " + Integer.toString(order.id())));
                var status = new JPanel(new BorderLayout());
                var statusBox = new JComboBox<>(Status.cooks);
                statusBox.setSelectedItem(order.status());
                statusBox.addActionListener(e -> {
                    var stat = (Status) statusBox.getSelectedItem();
                    Cooks.setStatus(new PostSetStatus(order.id(), stat));
                    update.run();
                });
                status.add(statusBox, BorderLayout.CENTER);
                status.add(new JLabel("Status: "), BorderLayout.WEST);
                add(status);
                add(new JLabel("Time ordered: " + new SimpleDateFormat("HH:mm").format(order.timeOrdered())));
                add(Box.createVerticalStrut(10));
                add(new JLabel("Items: "));
                for (var item : order.items()) {
                    add(new JLabel(item.name()));
                }
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
        JLabel label = new JLabel("Orders");
        label.setHorizontalAlignment(SwingConstants.CENTER);
        topBar.add(label, BorderLayout.CENTER);
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
