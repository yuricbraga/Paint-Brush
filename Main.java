import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import components.MainPanel;

/**
* Classe principal do PaintBrush
*
* @author Ian
* @author Saul Melo
* @author Yuri
* @since 04 de 2020 
* @version 1.2
*/
public class Main{
  public static void main(String[] args) {
    SwingUtilities.invokeLater(Main::createAndShowGUI);
  }

   /**
   * Este método inicializa a janela principal, JFrame. Com as configuracoes
   * de layoult pre-definidas.
   */
  private static void createAndShowGUI(){
    System.out.println("Created GUI on EDT? " + SwingUtilities.isEventDispatchThread());
    JFrame f = new JFrame("Paint brush");
    f.setResizable(false);
    f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    MainPanel mainPanel = new MainPanel(f);
    f.add(mainPanel);
    f.pack();
    f.setVisible(true);
  }
}