package org.example.Pages.Managers;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Timer;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import org.example.Components.ManagerWaiterView;
import org.example.Data.controllers.Managers;

// ChatGPT Modified Code
public class StaffManagement extends JPanel {
    StaffManagementPanel staffManagement;

    public void setContent(JPanel panel) {
        removeAll();
        add(panel);
        revalidate();
        repaint();
    }

    public StaffManagement(Runnable exit) throws Exception {
        setLayout(new BorderLayout());

        try {
            staffManagement = new StaffManagementPanel(() -> {
                staffManagement.timer.stop();
                exit.run();
            },
                    () -> setContent(new CreateAccount(() -> {
                        setContent(staffManagement);
                        staffManagement.update();
                    })));
            setContent(staffManagement);
        } catch (Exception e) {
            e.printStackTrace();
            exit.run();
        }
    }

    /**
     * StaffManagementPanel
     */
    public class StaffManagementPanel extends JPanel {
        ManagerWaiterView workerInfo;
        JPanel leftPanel;
        JLabel deleteAccountLabel;
        JButton delButton;

        private Timer timer;

        public StaffManagementPanel(Runnable exit, Runnable createAccount) throws Exception {
            setLayout(new BorderLayout());

            timer = new Timer(5000, new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {
                    update();
                }

            });

            // Create a JPanel for the left side of the screen
            leftPanel = new JPanel();
            leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
            JLabel nameLabel = new JLabel("List of Names:");
            leftPanel.add(nameLabel);

            // Create a JPanel for the right side of the screen
            JPanel rightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
            JLabel addAccountLabel = new JLabel("Add Account:");
            JButton addButton = new JButton("Add Account");
            addButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    // Add account functionality goes here
                    // For example, show a dialog to input account details
                    if (JOptionPane.showConfirmDialog(null, "Do you want to add an account?", "Add Account",
                            JOptionPane.YES_NO_OPTION) == 0) {
                        createAccount.run();
                    }
                }
            });
            rightPanel.add(addAccountLabel);
            rightPanel.add(addButton);

            deleteAccountLabel = new JLabel("Delete Account:");

            delButton = new JButton("Delete Account");
            delButton.setVisible(false);
            deleteAccountLabel.setVisible(false);
            delButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    // Delete account functionality goes here
                    if (JOptionPane.showConfirmDialog(null, "Are you sure you want to delete this account?",
                            "Delete Account", JOptionPane.YES_NO_OPTION) == 0) {
                        Managers.deleteWorker(workerInfo.getId());
                        update();
                    }
                }
            });

            var exitButton = new JButton("Exit");
            exitButton.addActionListener(e -> exit.run());
            rightPanel.add(deleteAccountLabel);
            rightPanel.add(delButton);
            rightPanel.add(exitButton);

            add(rightPanel, BorderLayout.EAST);
            // Creating a center panel for worker information
            JPanel centerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
            workerInfo = new ManagerWaiterView();
            centerPanel.add(workerInfo);

            update();

            add(centerPanel, BorderLayout.CENTER);

            // Create a JScrollPane to accommodate the list of names
            JScrollPane scrollPane = new JScrollPane(leftPanel);
            scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

            add(scrollPane, BorderLayout.WEST);
            timer.start();
        }

        private void update() {
            var workersOptional = Managers.setWorkers();
            if (workersOptional.isEmpty())
                return;
            var workers = workersOptional.get();
            leftPanel.removeAll();
            leftPanel.add(new JLabel("List of Names:"));
            // Add buttons for each name
            for (var worker : workers.workers()) {
                String name = worker.firstName() + " " + worker.lastName();
                // workerInfo.append(Id, age, job);
                JButton nameButton = new JButton(name);
                nameButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        try {
                            workerInfo.update(worker);
                        } catch (Exception exception) {
                            exception.printStackTrace();
                        }
                        deleteAccountLabel.setVisible(true);
                        delButton.setVisible(true);
                    }
                });
                leftPanel.add(nameButton);
            }
            revalidate();
            repaint();
        }
    }

}