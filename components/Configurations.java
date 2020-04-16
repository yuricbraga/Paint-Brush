package components;

import java.awt.*;

/**
 * Esta Classe realiza a configuracao, da cor a ser exibida Pelo cursor e
 * determina o evento a ser realizado pelo objeto
 * 
 * @author Ian
 * @author Saul Melo
 * @author Yuri
 * @since 04 de 2020
 * @version 1
 */
public class Configurations {
   private int MODE;
   private Color color;
   private Color previous;
   private int pixelSize;

   /**
    * O Construtor da classe que especifica as configuracoes de evento, E cor
    * exibida pelo cursor
    *
    * @param int,   Indicando o codigo do evento a ser configurado para este objeto
    * @param Color, Indica a cor a ser exibida no cursor
    */
   public Configurations(int MODE, Color color, int pixelSize) {
      this.MODE = MODE;
      this.color = color;
      this.pixelSize = pixelSize;
      this.previous = null;
   }

   /**
    * Este m�todo retorna a configuracao de cor utilizada pelo objeto
    * 
    * @return java.awt.Color
    */
   public Color getColor() {
      return color;
   }

   /**
    * Este m�todo retorna o indice do vento a ser realizado pelo objeto
    * 
    * @return int , Indice do evento a ser realizado
    */
   public int getMODE() {
      return MODE;
   }

   /**
    * Este m�todo seta a configuracao de cor utilizada pelo objeto
    * 
    * @param java.awt.Color
    */
   public void setColor(Color color) {
      this.previous = this.color;
      this.color = color;
   }

   /**
    * Este m�todo seta o indice do vento a ser realizado pelo objeto
    * 
    * @param int , Indice do evento a ser realizado
    */
   public void setMODE(int MODE) {
      this.MODE = MODE;
   }

   /**
    * @return the pixelSize
    */
   public int getPixelSize() {
      return pixelSize;
   }

   /**
    * @param pixelSize the pixelSize to set
    */
   public void setPixelSize(int pixelSize) {
      this.pixelSize = pixelSize;
   }

}