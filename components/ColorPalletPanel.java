package components;

import java.awt.*;

import javax.swing.JPanel;

class ColorPalletPanel extends JPanel{
  public ColorPalletPanel(){
    setBackground(new Color(0f,0f,0f,1f));
  }

  public Dimension getPreferredSize(){
    return new Dimension(900, 100);
  }
}