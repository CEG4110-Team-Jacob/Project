package org.example.Pages.Waiters;

import java.awt.BorderLayout;
import java.awt.Color;

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

/**
 * Waiters' Main UI
 * Modified ChatGPT Code
 */
public class WaiterUI extends JPanel {
    private TablesUI tablesUI = new TablesUI();
    public Timer timer;

    public WaiterUI(Runnable exit) {
        Runnable fullExit = () -> {
            timer.stop();
            exit.run();
        };
        setLayout(new BorderLayout());

        JPanel topBar = new JPanel(new BorderLayout());
        JLabel label = new JLabel("Waiter");
        label.setHorizontalAlignment(SwingConstants.CENTER);

        JButton exitButton = new JButton("Exit");
        exitButton.addActionListener(e -> fullExit.run());

        topBar.add(label, BorderLayout.WEST);
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

        add(topBar, BorderLayout.NORTH);
        add(tablesUI, BorderLayout.CENTER);

        timer.start();
    }

    private void createTable(WaiterTable table, JPanel tableUI, WaiterOrder orderData) {
        tableUI.setLayout(new BoxLayout(tableUI, BoxLayout.Y_AXIS));
        tableUI.add(new JLabel("Number: " + table.number()));
        tableUI.add(new JLabel("Seats: " + table.numSeats()));

        if (table.isOccupied()) {
            String labelText = orderData != null ? orderData.status().toString() : "Ready to Order";
            tableUI.add(new JLabel(labelText));
            tableUI.setBackground(Color.YELLOW);
        } else {
            tableUI.add(new JLabel("Empty"));
            tableUI.setBackground(Color.GREEN);
            return;
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
                if (orderDataOptional.isEmpty())
                    return;
                var table = tablesUI.tables[tableData.x()][tableData.y()];
                createTable(tableData, table, orderDataOptional.get());
            });

        };
        tablesUI.update(run);
    }
}
