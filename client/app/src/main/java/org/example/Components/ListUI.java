package org.example.Components;

import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class ListUI extends JScrollPane {
    public JPanel list;

    public ListUI() {
        list = new JPanel();
        list.setLayout(new BoxLayout(list, BoxLayout.Y_AXIS));
        setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        setViewportView(list);
    }

    public void clear() {
        list.removeAll();
    }

    public void update() {
        revalidate();
        repaint();
    }
}