import javax.swing.JFrame;



public class MainFrame extends JFrame {
    private Waiters waiterGui;
    private Cooks cookGui;

    public MainFrame() {
        super("Restaurant");

        waiterGui = new Waiters();
        getContentPane().add(waiterGui);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 500);
        setVisible(true);
    }


}