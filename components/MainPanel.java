package components;

import java.awt.*;
import java.awt.event.*;

import javax.swing.JPanel;

public class MainPanel extends JPanel{
  ToolsPanel toolsPanel;
  PaintPnl paintPnl;

  public MainPanel(){
    setBackground(Color.LIGHT_GRAY);
    toolsPanel = new ToolsPanel();
    paintPnl = new PaintPnl();

    add(toolsPanel);
    add(paintPnl);

    addComponentListener(new ComponentAdapter(){
      public void componentResized(ComponentEvent e){
        toolsPanel.setSize(80, e.getComponent().getHeight() - 50);
        paintPnl.setSize(e.getComponent().getWidth() - toolsPanel.getWidth() - 30, e.getComponent().getHeight() - 50);
      }
    });
  }

}