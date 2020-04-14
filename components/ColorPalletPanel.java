package components;

import java.awt.*;

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
class ColorPalletPanel extends JPanel{
  
  /**
  * Construtor padrao nao parametrizado da classe
  */
  public ColorPalletPanel(){
    setBackground(new Color(0f,0f,0f,0f));
  }
  
  /**
  * Efetua o dimensionamento
  */
  public Dimension getPreferredSize(){
    return new Dimension(900, 100);
  }
}