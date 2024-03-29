package org.example.Pages.Managers;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JPanel;

public class ManagerOptionsMenu extends JPanel {
    Options optionsMenu;

    public void setPane(JPanel panel) {
        removeAll();
        add(panel, BorderLayout.CENTER);
        revalidate();
        repaint();
    }

    public ManagerOptionsMenu(Runnable exit) {
        setLayout(new BorderLayout());
        Runnable createStaff = () -> {
            try {
                setPane(new StaffManagement(() -> {
                    setPane(optionsMenu);
                }));
            } catch (Exception e) {
                e.printStackTrace();
                setPane(optionsMenu);
            }
        };
        Runnable createTable = () -> {
            try {
                setPane(new TableManagement(() -> setPane(optionsMenu)));
            } catch (Exception e) {
                e.printStackTrace();
                setPane(optionsMenu);
            }
        };
        optionsMenu = new Options(exit, createTable::run,
                createStaff::run);
        setPane(optionsMenu);
    }

    /**
     * Options
     */
    public class Options extends JPanel {
        public Options(Runnable exit, Runnable table, Runnable staff) {
            setLayout(new FlowLayout());
            var staffManagement = new JButton("Staff Management");
            staffManagement.addActionListener(e -> {
                staff.run();
            });
            var tableManagement = new JButton("Table Management");
            tableManagement.addActionListener(e -> {
                table.run();
            });

            var logoutButton = new JButton("Logout");
            logoutButton.addActionListener(e -> {
                exit.run();
            });

            add(staffManagement);
            add(tableManagement);
            add(Box.createVerticalBox());
            add(logoutButton);
        }
    }
}
