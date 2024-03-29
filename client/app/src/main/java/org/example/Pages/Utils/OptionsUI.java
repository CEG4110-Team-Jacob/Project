package org.example.Pages.Utils;

import java.awt.BorderLayout;

import javax.swing.JPanel;

public class OptionsUI extends JPanel {
    public void setContent(JPanel panel) {
        removeAll();
        add(panel);
        revalidate();
        repaint();
    }

    public OptionsUI() {
        setLayout(new BorderLayout());
    }
}
