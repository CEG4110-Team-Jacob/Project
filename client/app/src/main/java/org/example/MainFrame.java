package org.example;

import java.awt.event.WindowEvent;

import javax.swing.JFrame;

import org.example.Data.Data;
import org.example.Data.controllers.General;
import org.example.Data.controllers.Waiters;
import org.example.Pages.Cooks;
import org.example.Pages.Login;
import org.example.Pages.ManagerWaiterView;
import org.example.Pages.StaffManagement;
import org.example.Pages.TableManagement;

public class MainFrame extends JFrame {
    private Waiters waiterGui = new Waiters();
    private ManagerWaiterView managerWorkerView = new ManagerWaiterView(Data.getWorkers().get(1));
    private Cooks cookGui = new Cooks();
    private TableManagement TableManagement = new TableManagement();
    private StaffManagement staffManagement = new StaffManagement();
    private Login login;

    public MainFrame() {
        super("Restaurant");

        MainFrame frame = this;
        // Observer Pattern
        login = new Login(() -> {
            System.out.println("Logged in");
            System.out.println(Waiters.getOrders());
            System.out.println(General.getDetails());
            remove(this.login);
            add(TableManagement);
            // TODO What happens when login is successful
        });

        add(login);
        // add(waiterGui);
        // add(cookGui);
        // add(managerWorkerView);
        // add(TableManagement);
        // add(staffManagement);

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