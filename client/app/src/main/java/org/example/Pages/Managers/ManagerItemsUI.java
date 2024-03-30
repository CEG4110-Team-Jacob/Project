package org.example.Pages.Managers;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.UIManager;

import org.example.Data.controllers.Managers;
import org.example.Data.controllers.Managers.PostAddItem;
import org.example.Data.controllers.Managers.PostChangeItem;
import org.example.Data.enums.ItemType;
import org.example.Pages.ItemsUI;

public class ManagerItemsUI extends ItemsUI {
    public ManagerItemsUI(Runnable exit, Runnable createItemF) {
        super(exit, getCreator());
        topBar.remove(exitButton);
        JPanel buttons = new JPanel();
        var createItem = new JButton("Create Item");
        createItem.addActionListener(e -> createItemF.run());

        buttons.add(createItem);
        buttons.add(exitButton);
        topBar.add(buttons, BorderLayout.EAST);
    }

    // Modified UI Code by ChatGPT
    public static ElementCreator getCreator() {
        ElementCreator creator = (item, update) -> {
            JPanel panel = new JPanel();
            panel.setBorder(BorderFactory.createLineBorder(Color.black));
            panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS)); // Set layout to vertical BoxLayout

            // Create labels for order information
            JPanel namePanel = new JPanel();
            namePanel.add(new JLabel("Name: "));
            JTextField nameField = new JTextField(item.name());
            namePanel.add(nameField);

            // Create a JTextArea for the description
            JTextArea descriptionArea = new JTextArea(item.description());
            descriptionArea.setEditable(true);
            descriptionArea.setLineWrap(true);
            descriptionArea.setWrapStyleWord(true);
            // descriptionArea.setBackground(this.getBackground());
            descriptionArea.setFont(UIManager.getFont("Label.font")); // Match font

            JScrollPane descriptionScrollPane = new JScrollPane(descriptionArea);
            // descriptionScrollPane.setPreferredSize(new Dimension(200, 2)); // Adjust size
            // as needed

            var typePanel = new JPanel();
            typePanel.add(new JLabel("Type: "));
            var typeComboBox = new JComboBox<>(ItemType.values());
            typeComboBox.setSelectedItem(item.type());
            typePanel.add(typeComboBox);

            var pricePanel = new JPanel();
            pricePanel.add(new JLabel("Price (cents): "));
            JTextField priceField = new JTextField(Integer.toString(item.price()));
            pricePanel.add(priceField);

            var inStockPanel = new JPanel(new FlowLayout());
            // Create a JComboBox for inStock status
            JComboBox<Boolean> inStockComboBox = new JComboBox<>(new Boolean[] { true, false });
            inStockComboBox.setSelectedItem(item.inStock());
            inStockPanel.add(new JLabel("In Stock: "));
            inStockPanel.add(inStockComboBox);

            var applyButton = new JButton("Apply");
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

            // Add components to the panel
            panel.add(namePanel);
            panel.add(descriptionScrollPane);
            panel.add(typePanel);
            panel.add(pricePanel);
            panel.add(inStockPanel);
            panel.add(applyButton);
            panel.setPreferredSize(new Dimension(100, 100));
            return panel;
        };
        return creator;
    }

}
