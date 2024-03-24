package org.example;

import java.awt.event.WindowEvent;

import javax.swing.JFrame;

import org.example.Data.Data;
import org.example.Data.General;
import org.example.Data.Waiters;

public class MainFrame extends JFrame {
    private Waiters waiterGui = new Waiters();
    private ManagerWaiterView managerWorkerView = new ManagerWaiterView(Data.getWorkers().get(1));
    private Cooks cookGui = new Cooks();
    private Login login = new Login(() -> {
        System.out.println("Logged in");
        System.out.println(Waiters.getOrders());
        System.out.println(General.getDetails());
        // TODO What happens when login is successful
    });

    public MainFrame() {
        super("Restaurant");

        MainFrame frame = this;

        add(login);
        // add(waiterGui);
        // add(cookGui);
        // add(managerWorkerView);

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