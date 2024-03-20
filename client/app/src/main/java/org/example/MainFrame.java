package org.example;

import javax.swing.JFrame;

import org.example.Data.Data;

public class MainFrame extends JFrame {
    private Waiters waiterGui = new Waiters();
    private ManagerWaiterView managerWorkerView = new ManagerWaiterView(Data.getWorkers().get(1));
    private Cooks cookGui = new Cooks();
    private Login login = new Login(() -> {
        System.out.println("Logged in");
        // TODO What happens when login is successful
    });

    public MainFrame() {
        super("Restaurant");

        add(login);
        // add(waiterGui);
        // add(cookGui);
        // add(managerWorkerView);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 500);
        setVisible(true);
    }

}