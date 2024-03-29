package org.example;

import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import org.example.Data.controllers.General;
import org.example.Pages.Login;
import org.example.Pages.Cooks.OrdersUI;
import org.example.Pages.Managers.ManagerOptionsMenu;

public class MainFrame extends JFrame {

    private Login login;

    void setMainContentPane(JPanel panel) {
        setContentPane(panel);
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
                    var cookUI = new OrdersUI(() -> setMainContentPane(login));
                    setMainContentPane(cookUI);
                    break;
                case Host:
                    break;
                case Manager:
                    var options = new ManagerOptionsMenu(() -> setMainContentPane(login));
                    setMainContentPane(options);
                    break;
                case Waiter:
                    break;
            }
        });

        setMainContentPane(login);

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