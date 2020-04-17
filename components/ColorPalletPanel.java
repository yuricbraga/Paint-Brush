package components;

import java.awt.*;
import java.awt.event.*;

import javax.swing.JButton;
import javax.swing.JPanel;

/**
 * Esta Classe realiza a configuracao, do palheta de cores
 * 
 * @author Ian
 * @author Saul Melo
 * @author Yuri
 * @since 04 de 2020
 * @version 1
 */
class ColorPalletPanel extends JPanel {
  private Configurations configurations;

  /**
   * Construtor parametrizado da classe
   * 
   * @param Configurations, Configuracoes basicas do pixel
   */
  public ColorPalletPanel(Configurations configurations) {
    this.configurations = configurations;
    setBackground(new Color(0f, 0f, 0f, 0f));

    createButtons();

  }

  /**
   * Este metodo efetua a criacao da caixa da palheta de cores
   */
  private void createButtons() {
    Color[] colors = { Color.BLACK, Color.BLUE, Color.CYAN, Color.DARK_GRAY, Color.GRAY, Color.GREEN, Color.LIGHT_GRAY,
        Color.MAGENTA, Color.ORANGE, Color.PINK, Color.RED, Color.YELLOW, Color.WHITE, Color.WHITE, Color.WHITE,
        Color.WHITE, Color.WHITE, Color.WHITE, Color.WHITE, Color.WHITE, Color.WHITE, Color.WHITE, Color.WHITE,
        Color.WHITE, Color.WHITE, Color.WHITE, Color.WHITE, Color.WHITE, Color.WHITE, Color.WHITE, Color.WHITE,
        Color.WHITE, Color.WHITE, Color.WHITE };

    JPanel selectedColorPanel = new JPanel();
    selectedColorPanel.setPreferredSize(new Dimension(100, 100));
    add(selectedColorPanel, BorderLayout.WEST);

    JButton selectedColor = new JButton("");
    selectedColor.setPreferredSize(new Dimension(90, 85));
    selectedColor.setBackground(configurations.getColor());
    selectedColorPanel.add(selectedColor);

    JPanel palletPanel = new JPanel();
    palletPanel.setPreferredSize(new Dimension(790, 100));
    add(palletPanel, BorderLayout.EAST);

    int i = 0;

    for (Color a : colors) {
      JButton button = new JButton("");
      button.setActionCommand(Integer.toString(i));
      button.setBackground(a);
      button.setPreferredSize(new Dimension(40, 40));

      button.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
          configurations.setColor(colors[Integer.parseInt(e.getActionCommand())]);
          selectedColor.setBackground(configurations.getColor());
        }
      });

      palletPanel.add(button);
      i++;
    }
  }

  /**
   * Efetua o dimensionamento
   */
  public Dimension getPreferredSize() {
    return new Dimension(900, 100);
  }
}