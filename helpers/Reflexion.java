package helpers;

import java.awt.*;

/**
 * Classe que implementa o algoritmo de reflexão
 *
 * @author Ian
 * @author Saul Melo
 * @author Yuri
 * @since 04 de 2020
 * @version 1.2 <extends> JPanel
 */

public class Reflexion {
  private Color matrix[][];
  private Point start;
  
  /**
   * O Construtor da classe que realiza a reflexão de um objeto atravez de uma representacao (matriz) e o ponto inicial.
   *
   * @param Color matrix[][], matriz que representa o objeto
   * @param Point start, ponto inicial
   */

  public Reflexion(Color matrix[][], Point start){
    this.matrix = matrix;
    this.start = start;
  }

  /**
   * Metodo  que realiza  a recuperção de uma reflexao.
   *
   * @param boolean x
   * @param boolean y
   */
  
  public Color[][] getReflection(boolean x, boolean y){
    if(x && y){
      for(int i = 0; i < matrix.length/2; i++){
        for(int j = 0; j < matrix[0].length; j++){
          swap(i,j,matrix.length-i-1,matrix[0].length-j-1);
        }
      }
    } else if(y){
      for(int i = 0; i < matrix.length; i++){
        for(int j = 0; j < matrix[0].length/2; j++){
          swap(i,j,i,matrix[0].length-j-1);
        }
      }
    } else if(x){
      for(int i = 0; i < matrix.length/2; i++){
        for(int j = 0; j < matrix[0].length; j++){
          swap(i,j,matrix.length-i-1,j);
        }
      }
    }

    return matrix;
  }
  
/**
   * metodo implementa a troca de um ponto (x0,yo) por um ponto (x1,y1)
   *
   * @param int x0, Coordenada x do ponto (x0,y0)
   * @param int y0, Coordenada y do ponto (x0,y0)
   * @param int x1, Coordenada x do ponto (x1,y1)
   * @param int y1, Coordenada y do ponto (x1,y1)
   */
  
  private void swap(int x0, int y0, int x1, int y1){
    Color aux = matrix[x0][y0];
    matrix[x0][y0] = matrix[x1][y1];
    matrix[x1][y1] = aux;
  }
}
