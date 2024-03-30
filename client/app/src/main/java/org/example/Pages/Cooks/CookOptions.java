package org.example.Pages.Cooks;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.UIManager;

import org.example.Data.controllers.Cooks;
import org.example.Pages.ItemsUI;
import org.example.Pages.ItemsUI.ElementCreator;
import org.example.Pages.Utils.OptionsUI;

public class CookOptions extends OptionsUI {
    public class Options extends JPanel {
        public Options(Runnable exit, Runnable items, Runnable orders) {
            setLayout(new FlowLayout());
            var itemsUI = new JButton("Items");
            itemsUI.addActionListener(e -> {
                items.run();
            });
            var ordersUI = new JButton("Orders");
            ordersUI.addActionListener(e -> {
                orders.run();
            });

            var logoutButton = new JButton("Logout");
            logoutButton.addActionListener(e -> {
                exit.run();
            });

            add(itemsUI);
            add(ordersUI);
            add(Box.createVerticalBox());
            add(logoutButton);
        }
    }

    Options options;

    public CookOptions(Runnable exit) {
        super();
        ElementCreator creator = getCreator();
        Runnable addItems = () -> setContent(new ItemsUI(() -> setContent(options), creator));
        Runnable addOrders = () -> setContent(new OrdersUI(() -> setContent(options)));
        options = new Options(exit, addItems, addOrders);
        setContent(options);
    }

    // Modified UI Code by ChatGPT
    private ElementCreator getCreator() {
        ElementCreator creator = (item, update) -> {
            JPanel panel = new JPanel();
            panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS)); // Set layout to vertical BoxLayout

            // Create labels for order information
            JLabel nameLabel = new JLabel("Name: " + item.name());

            // Create a JTextArea for the description
            JTextArea descriptionArea = new JTextArea(item.description());
            descriptionArea.setEditable(false);
            descriptionArea.setLineWrap(true);
            descriptionArea.setWrapStyleWord(true);
            descriptionArea.setBackground(this.getBackground());
            descriptionArea.setFont(UIManager.getFont("Label.font")); // Match font

            JScrollPane descriptionScrollPane = new JScrollPane(descriptionArea);
            descriptionScrollPane.setPreferredSize(new Dimension(200, 2)); // Adjust size as needed

            JLabel typeLabel = new JLabel("Type: " + item.type());
            JLabel priceLabel = new JLabel("Price: " + item.price());

            var inStockPanel = new JPanel(new FlowLayout());
            // Create a JComboBox for inStock status
            JComboBox<Boolean> inStockComboBox = new JComboBox<>(new Boolean[] { true, false });
            inStockComboBox.setSelectedItem(item.inStock());
            inStockComboBox.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    var inStock = (Boolean) inStockComboBox.getSelectedItem();
                    if (inStock)
                        Cooks.itemRestocked(item.id());
                    else
                        Cooks.itemDepleted(item.id());
                    update.run();
                }
            });
            inStockPanel.add(new JLabel("In Stock: "));
            inStockPanel.add(inStockComboBox);

            // Add components to the panel
            panel.add(nameLabel);
            panel.add(descriptionScrollPane);
            panel.add(typeLabel);
            panel.add(priceLabel);
            panel.add(new JLabel("In Stock: "));
            panel.add(inStockPanel);
            panel.setPreferredSize(new Dimension(100, 100));
            return panel;
        };
        return creator;
    }
}
