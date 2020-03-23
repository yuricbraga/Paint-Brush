import java.awt.BorderLayout;
import java.awt.event.*;

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
    MainPanel mainPanel = new MainPanel();
    f.add(mainPanel, BorderLayout.WEST);
    f.pack();
    f.setVisible(true);
    f.addComponentListener(new ComponentAdapter(){
      public void componentResized(ComponentEvent e){
        //System.out.println(e.getComponent().getWidth() + ", " + e.getComponent().getHeight());
        mainPanel.setSize(e.getComponent().getWidth(), e.getComponent().getHeight());
      }
    });
  }
}