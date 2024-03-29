package org.example.Pages.Managers;

import javax.swing.*;

import org.example.Data.controllers.General;
import org.example.Pages.Cooks.OrdersUI;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

// Modified ChatGPT Code
public class TableManagement extends JPanel {

    private ArrayList<JButton> tableButtons;
    private ArrayList<JButton> editButtons;
    private ArrayList<Boolean> tableCheck;

    public TableManagement(Runnable exit) {
        setLayout(new BorderLayout());

        tableButtons = new ArrayList<>(); // Buttons for the tables, empty slots will not be enabled
        editButtons = new ArrayList<>(); // Buttons for editing the tables
        tableCheck = new ArrayList<>(); // If a slot in the tableCheck is true, that spot contains a table

        // Create a JPanel for the left side of the screen
        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
        JLabel nameLabel = new JLabel("List of Tables:");
        leftPanel.add(nameLabel);

        // Example list of table names
        ArrayList<String> tableNames = new ArrayList<>();
        tableNames.add("Table 1");
        tableNames.add("Table 2");
        tableNames.add("Table 3");
        tableNames.add("Table 4");

        // Add buttons for each table
        for (String tableName : tableNames) {
            JButton tableButton = new JButton(tableName);
            leftPanel.add(tableButton);
        }

        // Create a JScrollPane to accommodate the list of tables
        JScrollPane scrollPane = new JScrollPane(leftPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        add(scrollPane, BorderLayout.WEST);

        // Creates a JPanel to accommodate the layout of tables
        JPanel centerPanel = new JPanel(new GridLayout(10, 10, -20, 5));
        for (int i = 0; i < 100; i++) {
            final int index = i;
            tableButtons.add(new JButton());
            tableButtons.get(i).setBackground(Color.LIGHT_GRAY);
            tableButtons.get(i).setEnabled(false);
            editButtons.add(new JButton());
            editButtons.get(i).setBackground(Color.LIGHT_GRAY);
            editButtons.get(i).setVisible(false);
            editButtons.get(i).addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    createTable(index);
                }
            });
            tableCheck.add(Boolean.FALSE);
            centerPanel.add(tableButtons.get(i));
            centerPanel.add(editButtons.get(i));
        }

        add(centerPanel);

        // Create a JPanel for the right side of the screen
        JPanel rightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JLabel editTableLabel = new JLabel("Edit Tables:");
        JButton editButton = new JButton("Edit Tables");
        var workerDetails = General.getDetails();
        switch (workerDetails.get().job()) {
            case Cook:
                editButton.setVisible(false);
                editTableLabel.setVisible(false);
                break;
            case Host:
                editButton.setVisible(false);
                editTableLabel.setVisible(false);
                break;
            case Manager:
                editButton.setVisible(true);
                editTableLabel.setVisible(true);
                break;
            case Waiter:
                editButton.setVisible(false);
                editTableLabel.setVisible(false);
                break;
        }
        JLabel doneEditLabel = new JLabel("Done Editing:");
        doneEditLabel.setVisible(false);
        JButton doneEditButton = new JButton("Done Editing");
        doneEditButton.setVisible(false);
        editButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                editButton.setVisible(false);
                editTableLabel.setVisible(false);
                doneEditLabel.setVisible(true);
                doneEditButton.setVisible(true);
                showEditButtons();
            }
        });
        doneEditButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                editButton.setVisible(true);
                editTableLabel.setVisible(true);
                doneEditLabel.setVisible(false);
                doneEditButton.setVisible(false);
                int n = 1;
                for (int i = 0; i < 100; i++) {
                    if (tableCheck.get(i)) {
                        tableButtons.get(i).setText("" + n);
                        n++;
                    }
                }
                showTableButtons();
            }
        });
        JButton exitButton = new JButton("Exit");
        exitButton.addActionListener(e -> exit.run());

        rightPanel.add(editTableLabel);
        rightPanel.add(editButton);
        rightPanel.add(doneEditLabel);
        rightPanel.add(doneEditButton);
        rightPanel.add(exitButton);

        add(rightPanel, BorderLayout.EAST);
    }

    // Shows the buttons meant for getting information about a table, and hides the
    // buttons meant for editing
    private void showTableButtons() {
        for (int i = 0; i < 100; i++) {
            editButtons.get(i).setVisible(false);
            tableButtons.get(i).setVisible(true);
            if (tableCheck.get(i).booleanValue()) {
                tableButtons.get(i).setEnabled(true);
            } else {
                tableButtons.get(i).setEnabled(false);
            }
        }
    }

    // Hides the table buttons and shows the buttons made for editing
    private void showEditButtons() {
        for (int i = 0; i < 100; i++) {
            tableButtons.get(i).setVisible(false);
            editButtons.get(i).setVisible(true);
        }
    }

    // Creates a table at the given position, 61 is the 6th row 2nd column, 85 is
    // the 8th row 6th column, etc.
    private void createTable(int index) {
        ActionListener tableAction = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (JOptionPane.showConfirmDialog(null, "Is the Table in Use?", "Table status",
                        JOptionPane.YES_NO_OPTION) == 0) {
                    tableButtons.get(index).setBackground(Color.RED);
                } else {
                    tableButtons.get(index).setBackground(Color.GREEN);
                }
            }
        };
        for (ActionListener action : tableButtons.get(index).getActionListeners()) {
            tableButtons.get(index).removeActionListener(action);
        }
        tableButtons.get(index).addActionListener(tableAction);
        tableButtons.get(index).setBackground(Color.GREEN);
        tableCheck.remove(index);
        tableCheck.add(index, Boolean.TRUE);
        ActionListener editButtonAction = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (JOptionPane.showConfirmDialog(null, "Delete Table?", "Delete Table",
                        JOptionPane.YES_NO_OPTION) == 0) {
                    deleteTable(index);
                }
            }
        };
        for (ActionListener action : editButtons.get(index).getActionListeners()) {
            editButtons.get(index).removeActionListener(action);
        }
        editButtons.get(index).setBackground(Color.DARK_GRAY);
        editButtons.get(index).addActionListener(editButtonAction);
    }

    // Deletes the table, reverts the buttons back to the way they started
    private void deleteTable(int index) {
        for (ActionListener action : tableButtons.get(index).getActionListeners()) {
            tableButtons.get(index).removeActionListener(action);
        }
        tableButtons.get(index).setBackground(Color.LIGHT_GRAY);
        tableButtons.get(index).setEnabled(false);
        tableButtons.get(index).setText("");
        for (ActionListener action : editButtons.get(index).getActionListeners()) {
            editButtons.get(index).removeActionListener(action);
        }
        editButtons.get(index).setBackground(Color.LIGHT_GRAY);
        editButtons.get(index).addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                createTable(index);
            }
        });
        tableCheck.remove(index);
        tableCheck.add(index, Boolean.FALSE);
    }
}
