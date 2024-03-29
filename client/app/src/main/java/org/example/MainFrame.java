package org.example;

import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import org.example.Data.controllers.General;
import org.example.Pages.Login;
import org.example.Pages.Managers.ManagerOptionsMenu;

public class MainFrame extends JFrame {

    private Login login;

    public void logout() {
        setContentPane(login);
        revalidate();
        repaint();
    }

    public MainFrame() {
        super("Restaurant");

        MainFrame frame = this;
        // Observer Pattern
        login = new Login(() -> {
            var workerDetails = General.getDetails();
            if (workerDetails.isEmpty())
                JOptionPane.showMessageDialog(login, "Something went wrong with getDetails");
            switch (workerDetails.get().job()) {
                case Cook:
                    break;
                case Host:
                    break;
                case Manager:
                    var options = new ManagerOptionsMenu(() -> {
                        logout();
                    });
                    setContentPane(options);
                    break;
                case Waiter:
                    break;
            }
            revalidate();
            repaint();
        });

        setContentPane(login);

        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                General.logout();
                frame.dispose();
                System.exit(0);
            }
        });
        setSize(1000, 500);
        setVisible(true);
    }

}