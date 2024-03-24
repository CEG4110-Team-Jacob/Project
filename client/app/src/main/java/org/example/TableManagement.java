package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class TableManagement extends JPanel {

    public TableManagement() {
        setLayout(new BorderLayout());

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

        // Create a JPanel for the right side of the screen
        JPanel rightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JLabel addTableLabel = new JLabel("Add Table:");
        JButton addButton = new JButton("Add Table");
        JLabel deleteTableLabel = new JLabel("Delete Table:");
        JButton deleteButton = new JButton("Delete Table");
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Add table functionality goes here
                // For example, show a dialog to input table details
                JOptionPane.showMessageDialog(null, "Add table functionality goes here!");
            }
        });
        rightPanel.add(addTableLabel);
        rightPanel.add(addButton);
        rightPanel.add(deleteTableLabel);
        rightPanel.add(deleteButton);

        add(rightPanel, BorderLayout.EAST);
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Table Management");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(new TableManagement());
        frame.setSize(600, 400);
        frame.setVisible(true);
    }
}
