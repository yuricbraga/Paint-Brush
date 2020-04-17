package clipping;

/**
* Interface responsavel por implementar os segmentos de reta
*
* @author Ian
* @author Saul Melo
* @author Yuri
* @since 04 de 2020 
* @version 1
*/

public interface LineClipper {
  /**
  * Construtor parametrizado da classe
  *
  * @param int, O menor valor da coordenada x pertencente a regiao
  * @param int, O menor valor da coordenada y pertencente a regiao
  * @param int, O maior valor da coordenada x pertencente a regiao
  * @param int, O maior valor da coordenada y pertencente a regiao
  */
  public LineSegment clip(LineSegment clip);
}