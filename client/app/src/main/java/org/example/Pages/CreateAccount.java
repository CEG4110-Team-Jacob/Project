package org.example.Pages;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashSet;
import java.util.Set;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import org.example.Data.controllers.Managers;
import org.example.Data.controllers.Managers.PostCreateWorker;
import org.example.Data.enums.Job;
import org.example.functions.Exit;

public class CreateAccount extends JPanel {
    private final JTextField firstNameField;
    private final JTextField lastNameField;
    private final JTextField ageField;
    private final JTextField jobField;
    private final JTextField usernameField;
    private final JPasswordField passwordField;
    private final JTextField tableIdsField;

    public CreateAccount(Exit exit) {
        setLayout(new GridLayout(9, 2, 5, 5));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel firstNameLabel = new JLabel("First Name:");
        firstNameField = new JTextField();
        add(firstNameLabel);
        add(firstNameField);

        JLabel lastNameLabel = new JLabel("Last Name:");
        lastNameField = new JTextField();
        add(lastNameLabel);
        add(lastNameField);

        JLabel ageLabel = new JLabel("Age:");
        ageField = new JTextField();
        add(ageLabel);
        add(ageField);

        JLabel jobLabel = new JLabel("Job:");
        jobField = new JTextField();
        add(jobLabel);
        add(jobField);

        JLabel usernameLabel = new JLabel("Username:");
        usernameField = new JTextField();
        add(usernameLabel);
        add(usernameField);

        JLabel passwordLabel = new JLabel("Password:");
        passwordField = new JPasswordField();
        add(passwordLabel);
        add(passwordField);

        JLabel tableIdsLabel = new JLabel("Table IDs (comma-separated):");
        tableIdsField = new JTextField();
        add(tableIdsLabel);
        add(tableIdsField);

        JButton createButton = new JButton("Create");
        createButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    var details = getAccountDetails();
                    var success = Managers.createWorker(details);
                    if (success.isEmpty())
                        throw new Exception();
                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Invalid User Account.");
                }
            }
        });
        add(createButton);

        JButton exitButton = new JButton("Exit");
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                exit.exit();
            }
        });
        add(exitButton);
    }

    public PostCreateWorker getAccountDetails() throws Exception {
        String firstName = firstNameField.getText();
        String lastName = lastNameField.getText();
        int age = Integer.parseInt(ageField.getText());
        Job job = Job.valueOf(jobField.getText());
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());
        Set<Integer> tableIds = parseTableIds(tableIdsField.getText());
        System.out.println(tableIds);
        return new PostCreateWorker(firstName, lastName, age, job, username, password, tableIds);
    }

    private Set<Integer> parseTableIds(String tableIdsText) {
        Set<Integer> tableIds = new HashSet<>();
        String[] ids = tableIdsText.split(",");
        for (String id : ids) {
            try {
                int tableId = Integer.parseInt(id.trim());
                tableIds.add(tableId);
            } catch (NumberFormatException e) {
                // Ignore invalid table IDs
            }
        }
        return tableIds;
    }
}