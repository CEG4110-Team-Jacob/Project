package org.example.Pages.Waiters;

import java.awt.Color;
import java.awt.FlowLayout;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.example.Data.controllers.Waiters.WaiterTable;

class Details extends JPanel {
    JLabel waiter;
    JLabel table;
    JLabel time;

    public Details(WaiterTable tableData) {
        setLayout(new FlowLayout(FlowLayout.LEFT, 10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        waiter = new JLabel("Waiter: " + tableData.waiter().firstName() + " " + tableData.waiter().lastName());
        waiter.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        add(waiter);

        table = new JLabel("Table: " + tableData.number());
        table.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        add(table);

        time = new JLabel("Time: " + new SimpleDateFormat("HH:MM").format(new Date()));
        time.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        add(time);
    }
}