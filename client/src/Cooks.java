import javax.swing.*;
import javax.swing.border.EmptyBorder;

import java.awt.*;

public class Waiters extends JPanel {
Heading heading; 
Table tables;

}
class Heading extends JPanel{
JLabel waiter; 
JLabel assignTable;


    public Heading(){
        waiter = new JLabel();
        waiter.setText("Waiter: ____");
        waiter.setBorder(BorderFactory.createLineBorder(new Color(0, 0, 0), 1));
        add(waiter);
    

    assignTable = new JLabel();
        assignTable.setText("Tables Assigned: ____");
        //assignTable.setBorder(BorderFactory.createLineBorder(new Color(0, 0, 0), 1));
        add(assignTable);
    }
}

class Table extends JPanel{

}