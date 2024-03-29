package org.example.Components;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.example.Data.controllers.Managers.ManagerViewWorker;

// ChatGPT Modified Code
public class ManagerWaiterView extends JPanel {
    JPanel inputPanel;
    private int id;

    public int getId() {
        return id;
    }

    public ManagerWaiterView() {
        super(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        setBackground(Color.WHITE);
    }

    public void update(ManagerViewWorker worker) throws Exception {
        if (inputPanel != null)
            remove(inputPanel);
        id = worker.id();

        inputPanel = new JPanel(new GridBagLayout());
        inputPanel.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = GridBagConstraints.RELATIVE;
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;

        inputPanel.add(createFieldLabel("Name:"), gbc);
        inputPanel.add(createFieldValue(worker.firstName() + " " + worker.lastName()), gbc);

        inputPanel.add(createFieldLabel("ID:"), gbc);
        inputPanel.add(createFieldValue(Integer.toString(worker.id())), gbc);

        inputPanel.add(createFieldLabel("Age:"), gbc);
        inputPanel.add(createFieldValue(Integer.toString(worker.age())), gbc);

        inputPanel.add(createFieldLabel("Job:"), gbc);
        inputPanel.add(createFieldValue(worker.job().toString()), gbc);

        add(inputPanel, BorderLayout.CENTER);
        revalidate();
        repaint();
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
