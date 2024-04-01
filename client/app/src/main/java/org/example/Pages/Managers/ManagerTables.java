package org.example.Pages.Managers;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.util.HashMap;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
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
            // Add UI to all valid tables
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

                table.setLayout(new BorderLayout());
                CreateTable tablePanel = new CreateTable(waitersMap);
                table.add(tablePanel, BorderLayout.CENTER);

                if (waiter.isEmpty())
                    tablePanel.waiterCombo.setSelectedItem("");
                else
                    tablePanel.waiterCombo.setSelectedItem(waiter.get().firstName() + " " + waiter.get().lastName());

                tablePanel.numberField.setText(Integer.toString(tableData.number()));
                tablePanel.seatsField.setText(Integer.toString(tableData.numSeats()));
                tablePanel.occupiedCombo.setSelectedItem(tableData.isOccupied());

                var applyButton = new JButton("Apply");
                var deleteButton = new JButton("Delete");
                applyButton.addActionListener(e -> {
                    var waiterName = (String) tablePanel.waiterCombo.getSelectedItem();
                    var waiterId = waitersMap.get(waiterName);
                    var number = Integer.parseInt(tablePanel.numberField.getText());
                    var numSeats = Integer.parseInt(tablePanel.seatsField.getText());
                    var isOccupied = (Boolean) tablePanel.occupiedCombo.getSelectedItem();
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

                tablePanel.add(applyButton);
                tablePanel.add(deleteButton);
            }
            // Add create button for every table not already taken
            for (int i = 0; i < TablesUI.X; i++) {
                for (int j = 0; j < TablesUI.Y; j++) {
                    int x = i;
                    int y = j;
                    var table = tablesUI.tables[i][j];
                    if (table.getComponentCount() != 0)
                        continue;
                    table.setLayout(new FlowLayout());
                    JButton createButton = new JButton("Create");
                    createButton.addActionListener(e -> {
                        JFrame frame = new JFrame("Create Table");
                        var panel = new CreateTable(waitersMap);
                        JButton create = new JButton("Create");
                        create.addActionListener(event -> {
                            var waiterName = (String) panel.waiterCombo.getSelectedItem();
                            var waiterId = waitersMap.get(waiterName);
                            var number = Integer.parseInt(panel.numberField.getText());
                            var numSeats = Integer.parseInt(panel.seatsField.getText());
                            var isOccupied = (Boolean) panel.occupiedCombo.getSelectedItem();
                            var post = new PostSetTable(x, y, waiterId, number, numSeats,
                                    isOccupied,
                                    true);
                            try {
                                Managers.setTable(post).get();
                                frame.dispose();
                                this.update();
                            } catch (Exception ex) {
                                JOptionPane.showMessageDialog(null, "Could Not Create Table");
                            }
                        });
                        panel.add(create);
                        JButton exitButton = new JButton("Exit");
                        exitButton.addActionListener(event -> frame.dispose());
                        panel.add(exitButton);

                        frame.setContentPane(panel);
                        frame.setVisible(true);
                        frame.setSize(400, 400);
                    });
                    table.add(createButton);
                }
            }
        };
        tablesUI.update(run);
    }
}
