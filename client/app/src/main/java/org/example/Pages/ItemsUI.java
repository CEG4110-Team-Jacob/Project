package org.example.Pages;

import javax.swing.JPanel;

import org.example.Data.records.Item;

public class ItemsUI extends JPanel {
    public interface ElementCreator {
        JPanel create(Item item);
    }

    public ItemsUI(Runnable exit, ElementCreator eCreator) {
        //
    }
}
