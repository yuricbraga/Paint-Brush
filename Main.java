import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import components.MainPanel;

public class Main{
  public static void main(String[] args) {
    SwingUtilities.invokeLater(Main::createAndShowGUI);
  }

  private static void createAndShowGUI(){
    System.out.println("Created GUI on EDT? " + SwingUtilities.isEventDispatchThread());
    JFrame f = new JFrame("Paint brush");
    f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    f.add(new MainPanel());
    f.pack();
    f.setVisible(true);
  }
}