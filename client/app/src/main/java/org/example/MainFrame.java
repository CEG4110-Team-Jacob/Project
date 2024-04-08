package org.example;

import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import org.example.Data.controllers.General;
import org.example.Data.controllers.Waiters;
import org.example.Pages.HostUI;
import org.example.Pages.Login;
import org.example.Pages.Cooks.CookOptions;
import org.example.Pages.Managers.ManagerOptionsMenu;
import org.example.Pages.Waiters.WaiterUI;

public class MainFrame extends JFrame {

    private Login login;

    void setMainContentPane(JPanel panel) {
        if (panel == login)
            General.logout();
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
            General.messageStart();
            switch (workerDetails.get().job()) {
                case Cook:
                    var cookUI = new CookOptions(() -> setMainContentPane(login));
                    setMainContentPane(cookUI);
                    break;
                case Host:
                    var tableUI = new HostUI(() -> setMainContentPane(login));
                    setMainContentPane(tableUI);
                    break;
                case Manager:
                    var options = new ManagerOptionsMenu(() -> setMainContentPane(login));
                    setMainContentPane(options);
                    break;
                case Waiter:
                    Waiters.startOrderCooked();
                    var waitersUI = new WaiterUI(() -> setMainContentPane(login));
                    setMainContentPane(waitersUI);
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