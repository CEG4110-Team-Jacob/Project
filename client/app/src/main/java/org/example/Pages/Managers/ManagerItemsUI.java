package org.example.Pages.Managers;

import java.awt.BorderLayout;
import javax.swing.JButton;
import javax.swing.JPanel;

import org.example.Components.ItemsUI;
import org.example.Components.ManagerItemView;

public class ManagerItemsUI extends ItemsUI {
    public ManagerItemsUI(Runnable exit, Runnable createItemF) {
        super(exit, getCreator());
        topBar.remove(exitButton);
        JPanel buttons = new JPanel();
        var createItem = new JButton("Create Item");
        createItem.addActionListener(e -> createItemF.run());

        buttons.add(createItem);
        buttons.add(exitButton);
        topBar.add(buttons, BorderLayout.EAST);
    }

    // Modified UI Code by ChatGPT
    public static ElementCreator getCreator() {
        ElementCreator creator = (item, update) -> {
            return new ManagerItemView(item, update);
        };
        return creator;
    }

}
