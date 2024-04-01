package org.example.Components;

import javax.swing.*;

import org.example.Data.controllers.Managers;
import org.example.Data.controllers.Managers.PostAddItem;
import org.example.Data.controllers.Managers.PostChangeItem;
import org.example.Data.enums.ItemType;
import org.example.Data.records.Item;

import java.awt.*;

// Generated by Chat GPT
public class ManagerItemView extends JPanel {
    private final Item item;
    private final Runnable update;
    private JTextField nameField;
    private JTextArea descriptionArea;
    private JComboBox<ItemType> typeComboBox;
    private JComboBox<Boolean> inStockComboBox;
    private JTextField priceField;

    public ManagerItemView(Item item, Runnable update) {
        this.item = item;
        this.update = update;
        initComponents();
    }

    private void initComponents() {
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel formPanel = new JPanel();
        formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));
        formPanel.setBorder(BorderFactory.createLineBorder(Color.black));

        nameField = new JTextField(item.name());
        formPanel.add(createFormField("Name:", nameField));
        formPanel.add(createFormField("Description:", createDescriptionTextArea(item.description())));
        formPanel.add(createFormField("Type:", createTypeComboBox()));
        priceField = new JTextField(String.valueOf(item.price()));
        formPanel.add(createFormField("Price (cents):", priceField));
        formPanel.add(createFormField("In Stock:", createInStockComboBox()));

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.add(createApplyButton());
        buttonPanel.add(createDeleteButton());

        add(formPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private JPanel createFormField(String label, JComponent field) {
        JPanel panel = new JPanel(new BorderLayout());
        JLabel fieldLabel = new JLabel(label);
        fieldLabel.setFont(fieldLabel.getFont().deriveFont(Font.BOLD));
        panel.add(fieldLabel, BorderLayout.WEST);
        panel.add(field, BorderLayout.CENTER);
        panel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        return panel;
    }

    private JTextArea createDescriptionTextArea(String text) {
        descriptionArea = new JTextArea(text);
        descriptionArea.setLineWrap(true);
        descriptionArea.setWrapStyleWord(true);
        descriptionArea.setPreferredSize(new Dimension(200, 80));
        JScrollPane scrollPane = new JScrollPane(descriptionArea);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        return descriptionArea;
    }

    private JComboBox<ItemType> createTypeComboBox() {
        typeComboBox = new JComboBox<>(ItemType.values());
        typeComboBox.setSelectedItem(item.type());
        return typeComboBox;
    }

    private JComboBox<Boolean> createInStockComboBox() {
        inStockComboBox = new JComboBox<>(new Boolean[] { true, false });
        inStockComboBox.setSelectedItem(item.inStock());
        return inStockComboBox;
    }

    private JButton createApplyButton() {
        JButton applyButton = new JButton("Apply");
        applyButton.addActionListener(e -> {
            try {
                int id = item.id();
                var name = nameField.getText();
                var type = (ItemType) typeComboBox.getSelectedItem();
                var description = descriptionArea.getText();
                var price = Integer.parseInt(priceField.getText());
                var inStock = (Boolean) inStockComboBox.getSelectedItem();
                var details = new PostAddItem(name, description, price, inStock, type);
                Managers.changeItem(new PostChangeItem(id, details));
                update.run();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Failed to update item");
            }
        });
        return applyButton;
    }

    private JButton createDeleteButton() {
        JButton deleteButton = new JButton("Delete");
        deleteButton.addActionListener(e -> {
            int choice = JOptionPane.showConfirmDialog(null, "Do you want to delete this item?", "Delete Item",
                    JOptionPane.YES_NO_OPTION);
            if (choice == JOptionPane.YES_OPTION) {
                Managers.deleteItem(item.id());
                update.run();
            }
        });
        return deleteButton;
    }
}