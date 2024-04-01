package org.example.Pages.Cooks;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import javax.swing.Timer;
import javax.swing.BorderFactory;
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
import org.example.Data.records.Item;

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
         * OrderUI Improved By ChatGPT
         */
        public class OrderUI extends JPanel {
            public OrderUI(CookOrder order, Runnable update) {
                setBorder(BorderFactory.createLineBorder(Color.black));
                setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

                // Id
                add(createInfoLabel("Id: " + order.id()));

                // Time ordered
                add(createInfoLabel("Time ordered: " + new SimpleDateFormat("HH:mm").format(order.timeOrdered())));

                // Status
                JPanel statusPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
                JComboBox<Status> statusComboBox = new JComboBox<>(Status.cooks);
                statusComboBox.setSelectedItem(order.status());
                statusComboBox.setPreferredSize(new Dimension(120, statusComboBox.getPreferredSize().height));
                statusComboBox.addActionListener(e -> {
                    Status selectedStatus = (Status) statusComboBox.getSelectedItem();
                    Cooks.setStatus(new PostSetStatus(order.id(), selectedStatus));
                    update.run();
                });
                statusPanel.add(new JLabel("Status: "));
                statusPanel.add(statusComboBox);
                add(statusPanel);

                // Items
                // add(Box.createVerticalStrut(10));
                add(createInfoLabel("Items:"));
                for (Item item : order.items()) {
                    add(createInfoLabel("- " + item.name()));
                }
            }

            private JLabel createInfoLabel(String text) {
                JLabel label = new JLabel(text);
                label.setAlignmentX(Component.LEFT_ALIGNMENT);
                return label;
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
