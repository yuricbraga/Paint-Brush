package components;

import java.awt.*;
import java.awt.event.*;

import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * Esta classe e a responsavel pela gerencia dos componentes Do painel principal
 * do software
 * 
 * @author Ian
 * @author Saul Melo
 * @author Yuri
 * @since 04 de 2020
 * @version 1.2 
 * <extends> JPanel
 */
public class MainPanel extends JPanel {
  Configurations configurations;
  ToolsPanel toolsPanel;
  PaintPnl paintPnl;
  ColorPalletPanel colorPalletPanel;

  /**
   * Este metodo e o responsavel por gerenciar os componentes do painel principal
   *
   * @param JFrame, JFrame pai do programa
   */
  public MainPanel(JFrame parent) {
    setBackground(Color.LIGHT_GRAY);
    configurations = new Configurations(0, Color.RED, 2);
    toolsPanel = new ToolsPanel(configurations);
    paintPnl = new PaintPnl(configurations, parent);
    colorPalletPanel = new ColorPalletPanel(configurations);

    add(toolsPanel, BorderLayout.LINE_START);
    add(paintPnl, BorderLayout.CENTER);
    add(colorPalletPanel, BorderLayout.WEST);
  }

  /**
   * Metodo responsavel pelo dimensionamento ...
   */
  public Dimension getPreferredSize() {
    return new Dimension(920, 720);
  }

}