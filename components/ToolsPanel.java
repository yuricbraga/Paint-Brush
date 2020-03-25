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
    for(int i = 0; i < 6; i++){
      JButton button = new JButton(i + "");
      button.addActionListener(new ActionListener(){
        public void actionPerformed(ActionEvent e){
          configurations.setMODE(Integer.parseInt(e.getActionCommand()));
        }
      });

      add(button);
    }
  }

  public Dimension getPreferredSize(){
    return new Dimension(100, 600);
  }

}