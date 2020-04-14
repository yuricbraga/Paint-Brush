package components;

import java.awt.*;
import java.awt.event.*;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class MainPanel extends JPanel{
  Configurations configurations;
  ToolsPanel toolsPanel;
  PaintPnl paintPnl;
  ColorPalletPanel colorPalletPanel;

  public MainPanel(JFrame parent){
    setBackground(Color.LIGHT_GRAY);
    configurations = new Configurations(0, Color.RED);
    toolsPanel = new ToolsPanel(configurations);
    paintPnl = new PaintPnl(configurations, parent);
    colorPalletPanel = new ColorPalletPanel();

    add(toolsPanel, BorderLayout.LINE_START);
    add(paintPnl, BorderLayout.CENTER);
    add(colorPalletPanel, BorderLayout.SOUTH);
  }

  public Dimension getPreferredSize(){
    return new Dimension(920, 720);
  }

}