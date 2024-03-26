package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Optional;
import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.example.Data.controllers.Managers;
import org.example.Data.controllers.Managers.ManagerViewWorker;

public class StaffManagement extends JPanel {

    public StaffManagement() {
        setLayout(new BorderLayout());

        // Create a JPanel for the left side of the screen
        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
        JLabel nameLabel = new JLabel("List of Names:");
        leftPanel.add(nameLabel);

        // Example list of names
        ArrayList<String> names = new ArrayList<>();
        ManagerViewWorker.ListWorkers workers = org.example.Data.controllers.Managers.getWorkers()
                .get();
        for (ManagerViewWorker worker : workers.workers()) {
            names.add(worker.firstName() + " " + worker.lastName());
        }

        // Create a JPanel for the right side of the screen
        JPanel rightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JLabel addAccountLabel = new JLabel("Add Account:");
        JButton addButton = new JButton("Add Account");
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Add account functionality goes here
                // For example, show a dialog to input account details
                JOptionPane.showMessageDialog(null, "Add account functionality goes here!");
            }
        });
        rightPanel.add(addAccountLabel);
        rightPanel.add(addButton);

        JLabel deleteAccountLabel = new JLabel("Delete Account:");
        JButton delButton = new JButton("Delete Account");
        delButton.setVisible(false);
        deleteAccountLabel.setVisible(false);
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Delete account functionality goes here
                // For example, ask the user if they really want to delete the account
                int wantToDelete = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete this account?");
                // Do something with this information
            }
        });
        rightPanel.add(deleteAccountLabel);
        rightPanel.add(delButton);

        add(rightPanel, BorderLayout.EAST);
        // Creating a center panel for worker information
        JPanel centerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));

        // Add buttons for each name
        for (String name : names) {
            JTextArea workerInfo = new JTextArea("Name: " + name + "\n", 20, 20);
            // workerInfo.append(Id, age, job);
            workerInfo.setVisible(false);
            workerInfo.setFont(new Font("Times Roman", Font.PLAIN, 24));
            workerInfo.setBackground(new Color(238, 238, 238)); // Color of application background
            workerInfo.setEditable(false);
            centerPanel.add(workerInfo);
            JButton nameButton = new JButton(name);
            nameButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    Component[] oldText = centerPanel.getComponents();
                    for (Component c : oldText) {
                        c.setVisible(false); // Hides text from previous worker
                    }
                    workerInfo.setVisible(true); // Shows text from worker that was clicked on
                    deleteAccountLabel.setVisible(true);
                    delButton.setVisible(true);
                }
            });
            leftPanel.add(nameButton);
        }

        add(centerPanel);

        // Create a JScrollPane to accommodate the list of names
        JScrollPane scrollPane = new JScrollPane(leftPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        add(scrollPane, BorderLayout.WEST);

    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Staff Management");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(new StaffManagement());
        frame.setSize(600, 400);
        frame.setVisible(true);
    }
}