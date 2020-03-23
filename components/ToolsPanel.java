package components;

import java.awt.*;
import java.awt.event.*;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

public class ToolsPanel extends JPanel{
  public ToolsPanel(){
    //setBorder(BorderFactory.createLineBorder(Color.BLACK));
    setBackground(new Color(1f, 1f, 1f, 0f));

  }

  public Dimension getPreferredSize(){
    return new Dimension(80, 600);
  }

}