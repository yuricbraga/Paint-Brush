package clipping;

/**
* Classe responsavel por gerar os segmentos de reta
*
* @author Ian
* @author Saul Melo
* @author Yuri
* @since 04 de 2020 
* @version 1
*/
public class LineSegment {
  public int x0, y0, x1, y1;

  /**
  * Construtor parametrizado da classe
  *
  * @param int, O valor da coordenada x pertencente ao ponto inicial do segmento de reta
  * @param int, O valor da coordenada y pertencente ao ponto inicial do segmento de reta
  * @param int, O valor da coordenada x pertencente ao ponto final do segmento de reta
  * @param int, O valor da coordenada y pertencente ao ponto final do segmento de reta
  */
  public LineSegment(int x0, int y0, int x1, int y1){
    this.x0 = x0;
    this.y0 = y0;
    this.x1 = x1;
    this.y1 = y1;
  }
}