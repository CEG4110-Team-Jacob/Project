package org.example.Pages.Cooks;

import java.awt.FlowLayout;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JPanel;

import org.example.Pages.ItemsUI;
import org.example.Pages.ItemsUI.ElementCreator;
import org.example.Pages.Utils.OptionsUI;

public class CookOptions extends OptionsUI {
    Options options;

    public CookOptions(Runnable exit) {
        super();
        ElementCreator creator = (order) -> {
            return null;
        };
        Runnable addItems = () -> setContent(new ItemsUI(() -> setContent(options), creator));
        Runnable addOrders = () -> setContent(new OrdersUI(() -> setContent(options)));
        options = new Options(exit, addItems, addOrders);
        setContent(options);
    }

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
}
