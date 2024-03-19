import javax.swing.JFrame;

public class MainFrame extends JFrame {
    private Waiters waiterGui = new Waiters();
    private ManagerWaiterView managerWorkerView = new ManagerWaiterView(Data.getWorkers().get(1));
    private Cooks cookGui = new Cooks();

    public MainFrame() {
        super("Restaurant");

        add(waiterGui);
        // add(cookGui);
        // add(managerWorkerView);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 500);
        setVisible(true);
    }

}