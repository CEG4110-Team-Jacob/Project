package org.example.Pages.Managers;

import java.util.HashMap;

import java.awt.GridLayout;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class CreateTable extends JPanel {
    HashMap<String, Integer> waitersMap;
    public JComboBox<String> waiterCombo;
    public JTextField numberField;
    public JTextField seatsField;
    public JComboBox<Boolean> occupiedCombo = new JComboBox<>(new Boolean[] { true, false });

    public CreateTable(HashMap<String, Integer> waitersMap) {
        this.waitersMap = waitersMap;
        setLayout(new GridLayout(0, 2, 5, 5));
        add(new JLabel("Waiter: "));

        waiterCombo = new JComboBox<String>(waitersMap.keySet().toArray(new String[] {}));
        waiterCombo.setSelectedItem("");
        add(waiterCombo);

        add(new JLabel("Number: "));
        numberField = new JTextField(0);
        add(numberField);

        add(new JLabel("Seats: "));
        seatsField = new JTextField(0);
        add(seatsField);

        add(new JLabel("Occupied: "));
        occupiedCombo.setSelectedItem(false);
        add(occupiedCombo);

    }

}
