package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.example.Data.Data;

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
        names.add("John");
        names.add("Alice");
        names.add("Bob");
        names.add("Emily");

        JPanel centerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));

        // Add buttons for each name
        for (String name : names) {
            JTextArea workerInfo = new JTextArea("Name: " + name, 20, 20);
            workerInfo.setVisible(false);
            workerInfo.setEditable(false);
            centerPanel.add(workerInfo);
            JButton nameButton = new JButton(name);
            nameButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    Component[] oldText = centerPanel.getComponents();
                    for(Component c: oldText) {
                        c.setVisible(false);
                    }
                    workerInfo.setVisible(true);
                    //JOptionPane.showMessageDialog(null, "Worker information goes here");
                }
            });
            leftPanel.add(nameButton);
        }

        add(centerPanel);

        // Create a JScrollPane to accommodate the list of names
        JScrollPane scrollPane = new JScrollPane(leftPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        add(scrollPane, BorderLayout.WEST);


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

        add(rightPanel, BorderLayout.EAST);
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Staff Management");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(new StaffManagement());
        frame.setSize(600, 400);
        frame.setVisible(true);
    }
}