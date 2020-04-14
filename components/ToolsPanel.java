package components;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JPanel;


public class ToolsPanel extends JPanel{
  Configurations configurations;
  ArrayList<JButton> buttons;

  public ToolsPanel(Configurations configurations){
    this.configurations = configurations;
    setBackground(new Color(1f, 1f, 1f, 0f));
    createButtons();
  }

  private void createButtons(){
    String hintText[] = {"Lápis", "Linha (DDA)", "Linha (Bresenham)", "Retângulo", "Círculo", "Selecionar e mover", "Selecionar e rotacionar", "Selecionar e redimensionar"};
    int i = 0;

    for(String x : hintText){
      JButton button = new JButton(i + "");
      button.setToolTipText(x);

      button.addActionListener(new ActionListener(){
        public void actionPerformed(ActionEvent e){
          configurations.setMODE(Integer.parseInt(e.getActionCommand()));
        }
      });

      add(button);
      i++;

    }
  }

  public Dimension getPreferredSize(){
    return new Dimension(100, 600);
  }

}