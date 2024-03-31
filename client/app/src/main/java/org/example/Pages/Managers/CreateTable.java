package org.example.Pages.Managers;

import java.util.HashMap;

import javax.swing.BoxLayout;
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
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        JPanel waiterPanel = new JPanel();
        waiterPanel.add(new JLabel("Waiter: "));

        waiterCombo = new JComboBox<String>(waitersMap.keySet().toArray(new String[] {}));
        waiterCombo.setSelectedItem("");
        waiterPanel.add(waiterCombo);

        JPanel numberPanel = new JPanel();
        numberPanel.add(new JLabel("Number: "));
        numberField = new JTextField(0);
        numberPanel.add(numberField);

        JPanel seatsPanel = new JPanel();
        seatsPanel.add(new JLabel("Seats: "));
        seatsField = new JTextField(0);
        seatsPanel.add(seatsField);

        JPanel occupyPanel = new JPanel();
        occupyPanel.add(new JLabel("Occupied: "));
        occupiedCombo.setSelectedItem(false);
        occupyPanel.add(occupiedCombo);

        add(waiterPanel);
        add(numberPanel);
        add(occupyPanel);
        add(seatsPanel);
    }

}
