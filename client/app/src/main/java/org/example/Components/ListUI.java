package org.example.Components;

import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

/**
 * Makes a simple List
 */
public class ListUI extends JScrollPane {
    public JPanel list;

    public ListUI() {
        list = new JPanel();
        list.setLayout(new BoxLayout(list, BoxLayout.Y_AXIS));
        setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        setViewportView(list);
    }

    /**
     * Clears the list
     */
    public void clear() {
        list.removeAll();
    }

    /**
     * Redraws the list
     */
    public void update() {
        revalidate();
        repaint();
    }
}
