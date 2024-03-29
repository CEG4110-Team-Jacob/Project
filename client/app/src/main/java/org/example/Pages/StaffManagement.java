package org.example.Pages;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Optional;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import org.example.Components.ManagerWaiterView;
import org.example.Data.controllers.Managers;
import org.example.functions.Exit;

// ChatGPT Modified Code
public class StaffManagement extends JPanel {

    public StaffManagement(Exit exit) throws Exception {
        setLayout(new BorderLayout());

        // Create a JPanel for the left side of the screen
        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
        JLabel nameLabel = new JLabel("List of Names:");
        leftPanel.add(nameLabel);

        var workersOptional = Managers.getWorkers();
        if (workersOptional.isEmpty())
            throw new Exception();
        var workers = workersOptional.get();

        // Create a JPanel for the right side of the screen
        JPanel rightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JLabel addAccountLabel = new JLabel("Add Account:");
        JButton addButton = new JButton("Add Account");
        addButton.addActionListener(new ActionListener() {
            // TODO ADD CREATION
            @Override
            public void actionPerformed(ActionEvent e) {
                // Add account functionality goes here
                // For example, show a dialog to input account details
                if(JOptionPane.showConfirmDialog(null, "Do you want to add an account?", "Add Account", JOptionPane.YES_NO_OPTION) == 0) {
                    //addAccount(worker)
                }
            }
        });
        rightPanel.add(addAccountLabel);
        rightPanel.add(addButton);

        JLabel deleteAccountLabel = new JLabel("Delete Account:");
        JButton delButton = new JButton("Delete Account");
        delButton.setVisible(false);
        deleteAccountLabel.setVisible(false);
        delButton.addActionListener(new ActionListener() {
            // TODO ADD DELETION
            @Override
            public void actionPerformed(ActionEvent e) {
                // Delete account functionality goes here
                if(JOptionPane.showConfirmDialog(null, "Are you sure you want to delete this account?", "Delete Account", JOptionPane.YES_NO_OPTION) == 0) {
                    //deleteAccount(worker);
                }
            }
        });
        rightPanel.add(deleteAccountLabel);
        rightPanel.add(delButton);

        add(rightPanel, BorderLayout.EAST);
        // Creating a center panel for worker information
        JPanel centerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        ManagerWaiterView workerInfo = new ManagerWaiterView();
        centerPanel.add(workerInfo);

        // Add buttons for each name
        for (var worker : workers.workers()) {
            String name = worker.firstName() + " " + worker.lastName();
            // workerInfo.append(Id, age, job);
            JButton nameButton = new JButton(name);
            nameButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    try {
                        workerInfo.update(worker);
                    } catch (Exception exception) {
                        exception.printStackTrace();
                    }
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

}