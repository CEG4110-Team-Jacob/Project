package org.example;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.example.Data.Data;

public class ManagerWaiterView extends JPanel {
    JPanel waiterInfo = new JPanel();
    JPanel times = new JPanel();
    JPanel tableList = new JPanel();
    ArrayList<Table> tables = new ArrayList<>();

    public ManagerWaiterView(Data.Worker worker) {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        waiterInfo.add(new JLabel("Waiter: " + worker.name()));
        waiterInfo.add(new JLabel("ID: " + worker.id()));
        add(waiterInfo);
        times.add(new JLabel("Login Time: " + Date.from(Instant.ofEpochSecond(worker.login())).toString()));
        times.add(new JLabel("Work hours: Sometime"));
        add(times);
        tableList.setLayout(new BoxLayout(tableList, BoxLayout.Y_AXIS));
        for (Integer t : worker.tables()) {
            Data.Table table = Data.getTables().get(t);
            Table tableUi = new Table(table);
            add(tableUi);
            tableList.add(tableUi);
        }
        add(tableList);
    }

    class Table extends JPanel {
        public Table(Data.Table table) {
            JPanel info = new JPanel();
            info.setLayout(new BoxLayout(info, BoxLayout.Y_AXIS));
            info.add(new JLabel("Table " + table.id()));
            info.add(new JLabel("Status"));
            info.add(new JLabel(table.order().status()));
            add(info);
            JPanel order = new JPanel();
            order.setLayout(new BoxLayout(order, BoxLayout.Y_AXIS));
            for (String o : table.order().items()) {
                order.add(new JLabel(o));
            }
            add(order);
        }
    }
}
