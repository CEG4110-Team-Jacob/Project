package org.example.Pages.Waiters;

import java.awt.BorderLayout;
import java.awt.Color;
import java.util.Optional;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.Timer;

import org.example.Components.TablesUI;
import org.example.Data.controllers.General;
import org.example.Data.controllers.Waiters;
import org.example.Data.controllers.Waiters.WaiterOrder;
import org.example.Data.controllers.Waiters.WaiterTable;
import org.example.Pages.Utils.OptionsUI;

/**
 * Waiters' Main UI
 * Modified ChatGPT Code
 */
public class WaiterUI extends OptionsUI {
    private TablesUI tablesUI = new TablesUI();
    private JPanel main;
    public Timer timer;

    public WaiterUI(Runnable exit) {
        Runnable fullExit = () -> {
            timer.stop();
            exit.run();
        };

        main = new JPanel(new BorderLayout());

        JPanel topBar = new JPanel(new BorderLayout());
        JLabel label = new JLabel("Waiter");
        label.setHorizontalAlignment(SwingConstants.CENTER);

        JButton exitButton = new JButton("Exit");
        exitButton.addActionListener(e -> fullExit.run());

        topBar.add(label, BorderLayout.CENTER);
        topBar.add(exitButton, BorderLayout.EAST);

        timer = new Timer(5000, e -> {
            try {
                update();
            } catch (Exception ex) {
            }
        });

        try {
            update();
        } catch (Exception e) {
            fullExit.run();
        }

        main.add(topBar, BorderLayout.NORTH);
        main.add(tablesUI, BorderLayout.CENTER);

        setContent(main);

        timer.start();
    }

    private void createTable(WaiterTable table, JPanel tableUI, Optional<WaiterOrder> orderDataOptional) {
        tableUI.setLayout(new BoxLayout(tableUI, BoxLayout.Y_AXIS));
        tableUI.add(new JLabel("Number: " + table.number()));
        tableUI.add(new JLabel("Seats: " + table.numSeats()));

        var orderData = orderDataOptional.isPresent() ? orderDataOptional.get() : null;

        if (table.isOccupied()) {
            String labelText = orderDataOptional.isPresent() ? orderData.status().toString()
                    : "Ready to Order";
            tableUI.add(new JLabel(labelText));
            tableUI.setBackground(Color.YELLOW);
        } else {
            tableUI.add(new JLabel("Empty"));
            tableUI.setBackground(Color.GREEN);
            return;
        }

        JButton cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(e -> {
            Waiters.cancelOrder(orderData.id());
            try {
                update();
            } catch (Exception ex) {
            }
        });

        if (orderData == null) {
            JButton orderButton = new JButton("Order");
            orderButton.addActionListener(e -> {
                setContent(new WaiterOrderUI(table, () -> setContent(main)));
            });
            tableUI.add(orderButton);
        } else {
            tableUI.add(new JLabel("Order Id: " + orderData.id()));
            switch (orderData.status()) {
                case Delivered:
                    JButton completeButton = new JButton("Complete");
                    completeButton.addActionListener(e -> {
                        Waiters.orderDone(orderData.id());
                        try {
                            update();
                        } catch (Exception ex) {
                        }
                    });
                    tableUI.add(completeButton);
                    break;
                case Cooked:
                    JButton deliverButton = new JButton("Deliver");
                    deliverButton.addActionListener(e -> {
                        Waiters.completeOrder(orderData.id());
                        try {
                            update();
                        } catch (Exception ex) {
                        }
                    });
                    tableUI.add(deliverButton);
                    tableUI.add(cancelButton);
                    break;
                case Ordered:
                case InProgress:
                    tableUI.add(cancelButton);
                    break;
                default:
                    break;
            }
        }
    }

    public void update() throws Exception {
        Runnable run = () -> {
            var tablesData = Waiters.getTables().get();
            var workerData = General.setDetails().get();
            var waiterTablesData = tablesData.tables().stream()
                    .filter(table -> table.waiter() != null && table.waiter().id() == workerData.id());
            var orders = Waiters.getOrders().get();
            waiterTablesData.forEach((tableData) -> {
                // Out of bounds
                if (tableData.x() >= TablesUI.X || tableData.y() >= TablesUI.Y) {
                    System.err.println("Invalid Table Positions");
                    return;
                }
                var orderDataOptional = orders.orders().stream().filter(order -> order.table().id() == tableData.id())
                        .findAny();
                var table = tablesUI.tables[tableData.x()][tableData.y()];
                createTable(tableData, table, orderDataOptional);
            });

        };
        tablesUI.update(run);
    }
}
