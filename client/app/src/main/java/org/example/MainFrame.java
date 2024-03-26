package org.example;

import java.awt.CardLayout;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JPanel;

import org.example.Components.ManagerWaiterView;
import org.example.Data.Data;
import org.example.Data.controllers.General;
import org.example.Data.controllers.Waiters;
import org.example.Pages.Cooks;
import org.example.Pages.Login;
import org.example.Pages.StaffManagement;
import org.example.Pages.TableManagement;

public class MainFrame extends JFrame {
    private CardLayout layout = new CardLayout();
    private JPanel main = new JPanel();

    private Waiters waiterGui = new Waiters();
    private ManagerWaiterView managerWorkerView;
    private Cooks cookGui = new Cooks();
    private TableManagement TableManagement = new TableManagement();
    private StaffManagement staffManagement;
    private Login login;

    public MainFrame() {
        super("Restaurant");
        main.setLayout(layout);

        MainFrame frame = this;
        // Observer Pattern
        login = new Login(() -> {
            remove(this.login);
            try {
                staffManagement = new StaffManagement(() -> {
                });
                main.add(staffManagement, "Staffing");
                layout.show(main, "Staffing");
            } catch (Exception e) {
                e.printStackTrace();
            }
            // TODO What happens when login is successful
        });

        main.add(login, "LOGIN");
        // main.add(waiterGui);
        // main.add(cookGui);
        // main.add(TableManagement);

        add(main);
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