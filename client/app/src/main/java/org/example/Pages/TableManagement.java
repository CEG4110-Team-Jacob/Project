package org.example.Pages;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

// Modified ChatGPT Code
public class TableManagement extends JPanel {

    private int numTables;
    private ArrayList<JButton> tableButtons;
    private ArrayList<JButton> editButtons;
    private ArrayList<Boolean> tableCheck;

    public TableManagement() {
        setLayout(new BorderLayout());

        numTables = 0;
        tableButtons = new ArrayList<>(); //Buttons for the tables, empty slots will not be enabled
        editButtons = new ArrayList<>(); //Buttons for editing the tables
        tableCheck = new ArrayList<>(); //If a slot in the tableCheck is true, that spot contains a table

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

        //Creates a JPanel to accommodate the layout of tables
        JPanel centerPanel = new JPanel(new GridLayout(10,10, -20, 5));
        for(int i = 0; i < 100; i++) {
            tableButtons.add(new JButton());
            tableButtons.get(i).setEnabled(false);
            editButtons.add(new JButton());
            editButtons.get(i).setVisible(false);
            editButtons.get(i).addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    JOptionPane.showMessageDialog(null, "Hey! This should let you add a table, maybe with the createTable method");
                    // createTable(i); // Need i to "be final or effectively final"
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
                showTableButtons();
            }
        });
        rightPanel.add(editTableLabel);
        rightPanel.add(editButton);
        rightPanel.add(doneEditLabel);
        rightPanel.add(doneEditButton);

        add(rightPanel, BorderLayout.EAST);
    }

    //Shows the buttons meant for getting information about a table, and hides the buttons meant for editing
    private void showTableButtons() {
        for(int i = 0; i < 100; i++) {
            editButtons.get(i).setVisible(false);
            tableButtons.get(i).setVisible(true);
            if(tableCheck.get(i).booleanValue()) {
                tableButtons.get(i).setEnabled(true);
            } else {
                tableButtons.get(i).setEnabled(false);
            }
        }
    }

    //Hides the table buttons and shows the buttons made for editing
    private void showEditButtons() {
        for(int i = 0; i < 100; i++) {
            tableButtons.get(i).setVisible(false);
            editButtons.get(i).setVisible(true);
        }
    }

    //Should create a table at the given position, 61 is the 6th row 2nd column, 85 is the 8th row 6th column, etc.
    private void createTable(int index) {
        ActionListener tableAction = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "Display Table Information Here Please");
            }
        };
        tableButtons.get(index).removeActionListener(tableButtons.get(index).getActionListeners()[0]);
        tableButtons.get(index).addActionListener(tableAction);
        numTables++;
        tableCheck.remove(index);
        tableCheck.add(index, Boolean.TRUE);
        ActionListener editButtonAction = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "Hey! This should be a confirmation on deleting the table!");
            }
        };
        editButtons.get(index).removeActionListener(editButtons.get(index).getActionListeners()[0]);
        editButtons.get(index).addActionListener(editButtonAction);
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Table Management");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(new TableManagement());
        frame.setSize(600, 400);
        frame.setVisible(true);
    }
}
