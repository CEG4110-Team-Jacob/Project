import javax.swing.*;

public class UserInterface extends JFrame {

   private final int SIZE = 1000;

   public UserInterface() {
      JPanel root = new JPanel();
      createExitButton(root);

      this.setSize(SIZE, SIZE);
      this.setVisible(true);
      this.getContentPane().add(root);
      this.setTitle("Temporary Title");
      this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
   }

   private void createExitButton(JPanel root) {
      JButton a = new JButton("Exit");
      a.addActionListener(e -> {
         System.exit(0);
      });
      root.add(a);
   }
}
