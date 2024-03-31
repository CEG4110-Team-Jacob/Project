package org.example.Pages.Managers;

import java.awt.FlowLayout;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JPanel;

import org.example.Pages.Utils.OptionsUI;

public class ManagerOptionsMenu extends OptionsUI {
    Options optionsMenu;

    public ManagerOptionsMenu(Runnable exit) {
        Runnable createStaff = () -> {
            try {
                setContent(new StaffManagement(() -> {
                    setContent(optionsMenu);
                }));
            } catch (Exception e) {
                e.printStackTrace();
                setContent(optionsMenu);
            }
        };
        Runnable createTable = () -> {
            try {
                // setContent(new TablesUI(() -> setContent(optionsMenu)));
            } catch (Exception e) {
                e.printStackTrace();
                setContent(optionsMenu);
            }
        };
        Runnable createItems = () -> setContent(
                new ManagerItemsUI(() -> setContent(optionsMenu),
                        () -> setContent(new CreateItem(() -> setContent(optionsMenu)))));
        optionsMenu = new Options(exit, createTable,
                createStaff, createItems);
        setContent(optionsMenu);
    }

    /**
     * Options
     */
    public class Options extends JPanel {
        public Options(Runnable exit, Runnable table, Runnable staff, Runnable items) {
            setLayout(new FlowLayout());
            var staffManagement = new JButton("Staff Management");
            staffManagement.addActionListener(e -> staff.run());
            var tableManagement = new JButton("Table Management");
            tableManagement.addActionListener(e -> table.run());
            var itemManagements = new JButton("Items");
            itemManagements.addActionListener(e -> items.run());

            var logoutButton = new JButton("Logout");
            logoutButton.addActionListener(e -> {
                exit.run();
            });

            add(staffManagement);
            add(tableManagement);
            add(itemManagements);
            add(Box.createVerticalBox());
            add(logoutButton);
        }
    }
}
