package org.example.Pages;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.Timer;

import org.example.Components.TablesUI;
import org.example.Data.controllers.Hosts;
import org.example.Data.controllers.Hosts.GetHostTables.HostTable;

public class HostUI extends JPanel {
    private TablesUI tablesUI = new TablesUI();
    public Timer timer;

    public HostUI(Runnable exit) {
        Runnable fullExit = () -> {
            timer.stop();
            exit.run();
        };
        setLayout(new BorderLayout());

        JPanel topBar = new JPanel(new BorderLayout());
        JLabel label = new JLabel("Host");
        label.setHorizontalAlignment(SwingConstants.CENTER);

        JButton exiButton = new JButton("Exit");
        exiButton.addActionListener(e -> fullExit.run());

        topBar.add(label, BorderLayout.WEST);
        topBar.add(exiButton, BorderLayout.EAST);

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

    private void createTable(HostTable table, JPanel tableUI) {
        tableUI.setLayout(new BoxLayout(tableUI, BoxLayout.Y_AXIS));
        tableUI.add(new JLabel("Number: " + table.number()));
        tableUI.add(new JLabel("Seats: " + table.numSeats()));
        if (table.isOccupied()) {
            tableUI.add(new JButton("Occupied"));
            tableUI.setBackground(Color.YELLOW);
        } else {
            JButton occupy = new JButton("Occupy");
            occupy.addActionListener(e -> {
                Hosts.occupy(table.id());
                try {
                    this.update();
                } catch (Exception ex) {
                }
            });
            tableUI.add(occupy);
            tableUI.setBackground(Color.GREEN);
        }
    }

    public void update() throws Exception {
        Runnable run = () -> {
            var tablesData = Hosts.getTables().get();

            for (var tableData : tablesData.tables()) {
                // Out of bounds
                if (tableData.x() >= TablesUI.X || tableData.y() >= TablesUI.Y) {
                    System.err.println("Invalid Table Positions");
                    continue;
                }
                var table = tablesUI.tables[tableData.x()][tableData.y()];
                createTable(tableData, table);
            }
        };
        tablesUI.update(run);
    }
}
