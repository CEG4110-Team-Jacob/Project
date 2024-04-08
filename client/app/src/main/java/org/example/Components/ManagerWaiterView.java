package org.example.Components;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import org.example.Data.controllers.Managers;
import org.example.Data.controllers.Managers.ManagerViewWorker;
import org.example.Data.controllers.Managers.PostSendMessage;

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

        inputPanel = new JPanel(new GridLayout(0, 2));
        inputPanel.setBackground(Color.WHITE);

        inputPanel.add(createFieldLabel("Name:"));
        inputPanel.add(createFieldValue(worker.firstName() + " " + worker.lastName()));

        inputPanel.add(createFieldLabel("ID:"));
        inputPanel.add(createFieldValue(Integer.toString(worker.id())));

        inputPanel.add(createFieldLabel("Age:"));
        inputPanel.add(createFieldValue(Integer.toString(worker.age())));

        inputPanel.add(createFieldLabel("Job:"));
        inputPanel.add(createFieldValue(worker.job().toString()));
        // Message area
        JTextArea textArea = new JTextArea();
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        inputPanel.add(textArea);
        // Send Message
        JButton sendMsg = new JButton("Send Message");
        sendMsg.addActionListener(e -> {
            Managers.sendMessage(new PostSendMessage(textArea.getText(), worker.id()));
        });
        inputPanel.add(sendMsg);

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
