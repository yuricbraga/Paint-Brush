package components;

import java.awt.*;
import java.awt.event.*;

import javax.swing.JPanel;

public class MainPanel extends JPanel{
  Configurations configurations;
  ToolsPanel toolsPanel;
  PaintPnl paintPnl;

  public MainPanel(){
    setBackground(Color.LIGHT_GRAY);
    configurations = new Configurations(0, Color.RED);
    toolsPanel = new ToolsPanel(configurations);
    paintPnl = new PaintPnl(configurations);

    add(toolsPanel);
    add(paintPnl);

    addComponentListener(new ComponentAdapter(){
      public void componentResized(ComponentEvent e){
        toolsPanel.setSize(100, e.getComponent().getHeight() - 50);
        paintPnl.setSize(e.getComponent().getWidth() - toolsPanel.getWidth() - 30, e.getComponent().getHeight() - 50);
      }
    });
  }

}