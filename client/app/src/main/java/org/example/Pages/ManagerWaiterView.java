package org.example.Pages;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.Optional;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.example.Data.controllers.Managers;
import org.example.Data.controllers.Managers.ManagerViewWorker;
import org.example.functions.Exit;

public class ManagerWaiterView extends JPanel {
    public ManagerWaiterView(int worker, Exit exit) throws Exception {
        super(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        setBackground(Color.WHITE);

        Optional<ManagerViewWorker.ListWorkers> workers = Managers.getWorkers();
        if (workers.isEmpty())
            throw new Exception("No workers found");
        Optional<ManagerViewWorker> workerOption = workers.get().workers().stream()
                .filter(w -> w.id() == worker)
                .findAny();
        if (workerOption.isEmpty())
            throw new Exception("Worker not found");
        ManagerViewWorker worker1 = workerOption.get();

        JPanel inputPanel = new JPanel(new GridBagLayout());
        inputPanel.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = GridBagConstraints.RELATIVE;
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;

        inputPanel.add(createFieldLabel("Name:"), gbc);
        inputPanel.add(createFieldValue(worker1.firstName() + " " + worker1.lastName()), gbc);

        inputPanel.add(createFieldLabel("ID:"), gbc);
        inputPanel.add(createFieldValue(Integer.toString(worker1.id())), gbc);

        inputPanel.add(createFieldLabel("Age:"), gbc);
        inputPanel.add(createFieldValue(Integer.toString(worker1.age())), gbc);

        inputPanel.add(createFieldLabel("Job:"), gbc);
        inputPanel.add(createFieldValue(worker1.job().toString()), gbc);

        add(inputPanel, BorderLayout.CENTER);

        // Create exit button panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton exitButton = new JButton("Exit");
        exitButton.addActionListener(e -> exit.exit());
        buttonPanel.add(exitButton);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private JLabel createFieldLabel(String labelText) {
        JLabel label = new JLabel(labelText);
        label.setFont(label.getFont().deriveFont(Font.BOLD));
        return label;
    }

    private JLabel createFieldValue(String value) {
        JLabel fieldValue = new JLabel(value);
        fieldValue.setForeground(Color.BLUE);
        return fieldValue;
    }
}
