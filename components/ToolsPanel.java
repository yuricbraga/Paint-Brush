package components;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JPanel;

/**
* Classe que implementa os componentes
* Do painel de ferramentas
* 
* @author Ian
* @author Saul Melo
* @author Yuri
* @since 04 de 2020 
* <extends> JPanel
* version 1.2
*/
public class ToolsPanel extends JPanel{
  Configurations configurations;
  ArrayList<JButton> buttons;

  /**
  * O Construtor da classe que especifica as configuracoes basicas
  * De um objeto do tipo JButton
  *
  * @param Configurations, Objeto do tipo Configurations
  * contendo a configuracao padrao de um objeto JButton
  */
  public ToolsPanel(Configurations configurations){
    this.configurations = configurations;
    setBackground(new Color(1f, 1f, 1f, 0f));
    createButtons();
  }

  /**
  * Este metodo efetua a criacao de um novo botao.
  * E o coloca em estado de espera, de um evento.
  */
  private void createButtons(){
    String hintText[] = {"Lapis", "Linha (DDA)", "Linha (Bresenham)", "Retangulo", "Circulo", "Selecionar e mover", "Selecionar e rotacionar", "Selecionar e redimensionar"};
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

  /**
  * Este metodo efetua o dimensionamento ...
  */
  public Dimension getPreferredSize(){
    return new Dimension(100, 600);
  }

}