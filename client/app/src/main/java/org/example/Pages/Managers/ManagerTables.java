package org.example.Pages.Managers;

import java.awt.BorderLayout;
import java.util.HashMap;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.Timer;

import org.example.Components.TablesUI;
import org.example.Data.controllers.Managers;
import org.example.Data.controllers.Managers.PostSetTable;
import org.example.Data.controllers.Waiters;
import org.example.Data.controllers.Waiters.WaiterTable.WaiterDetails;
import org.example.Data.enums.Job;

public class ManagerTables extends JPanel {

    private TablesUI tablesUI = new TablesUI();
    private Timer timer;

    public ManagerTables(Runnable exit) {
        Runnable fullExit = () -> {
            timer.stop();
            exit.run();
        };
        setLayout(new BorderLayout());
        add(tablesUI, BorderLayout.CENTER);

        JPanel topBar = new JPanel(new BorderLayout());
        JLabel label = new JLabel("Manager Tables");
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

    public void update() {
        Runnable run = () -> {
            var tablesData = Waiters.getTables().get();
            var workersData = Managers.getWorkers().get();
            var waitersData = workersData.workers().stream().filter(w -> w.job().equals(Job.Waiter)).toList();
            var waitersMap = new HashMap<String, Integer>();
            waitersData.forEach((waiter) -> {
                waitersMap.put(waiter.firstName() + " " + waiter.lastName(), waiter.id());
            });
            for (var tableData : tablesData.tables()) {
                // System.out.println(tableData.number());
                if (tableData.x() >= TablesUI.X || tableData.y() >= TablesUI.Y) {
                    System.err.println("Invalid Table Positions");
                    continue;
                }

                var waiterTableData = tableData.waiter() == null ? new WaiterDetails("", "", 0) : tableData.waiter();

                var table = tablesUI.tables[tableData.x()][tableData.y()];
                var waiter = waitersData.stream().filter(w -> {
                    return w.id() == waiterTableData.id();
                }).findAny();

                table.setLayout(new BoxLayout(table, BoxLayout.Y_AXIS));
                JPanel waiterPanel = new JPanel();
                waiterPanel.add(new JLabel("Waiter: "));

                var waiterCombo = new JComboBox<String>(waitersMap.keySet().toArray(new String[] {}));
                if (waiter.isEmpty())
                    waiterCombo.setSelectedItem("");
                else
                    waiterCombo.setSelectedItem(waiter.get().firstName() + " " + waiter.get().lastName());
                waiterPanel.add(waiterCombo);

                JPanel numberPanel = new JPanel();
                numberPanel.add(new JLabel("Number: "));
                JTextField numberField = new JTextField(Integer.toString(tableData.number()));
                numberPanel.add(numberField);

                JPanel seatsPanel = new JPanel();
                seatsPanel.add(new JLabel("Seats: "));
                JTextField seatsField = new JTextField(Integer.toString(tableData.numSeats()));
                seatsPanel.add(seatsField);

                JPanel occupyPanel = new JPanel();
                occupyPanel.add(new JLabel("Occupied: "));
                var occupiedCombo = new JComboBox<>(new Boolean[] { true, false });
                occupiedCombo.setSelectedItem(tableData.isOccupied());
                occupyPanel.add(occupiedCombo);

                var buttons = new JPanel();
                var applyButton = new JButton("Apply");
                var deleteButton = new JButton("Delete");
                applyButton.addActionListener(e -> {
                    var waiterName = (String) waiterCombo.getSelectedItem();
                    var waiterId = waitersMap.get(waiterName);
                    var number = Integer.parseInt(numberField.getText());
                    var numSeats = Integer.parseInt(seatsField.getText());
                    var isOccupied = (Boolean) occupiedCombo.getSelectedItem();
                    var post = new PostSetTable(tableData.x(), tableData.y(), waiterId, number, numSeats, isOccupied,
                            true);
                    Managers.setTable(post);
                    try {
                        this.update();
                    } catch (Exception ex) {
                    }
                });
                deleteButton.addActionListener(e -> {
                    var post = new PostSetTable(tableData.x(), tableData.y(), waiterTableData.id(),
                            tableData.number(), tableData.numSeats(), tableData.isOccupied(), false);
                    Managers.setTable(post);
                    try {
                        this.update();
                    } catch (Exception ex) {
                    }
                });

                buttons.add(applyButton);
                buttons.add(deleteButton);

                table.add(waiterPanel);
                table.add(numberPanel);
                table.add(occupyPanel);
                table.add(seatsPanel);
                table.add(buttons);
            }
        };
        tablesUI.update(run);
    }
}
