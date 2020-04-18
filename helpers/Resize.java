package helpers;

import java.awt.*;

/**
 * Classe que implementa o algoritmo de resize
 *
 * @author Ian
 * @author Saul Melo
 * @author Yuri
 * @since 04 de 2020
 * @version 1.2 <extends> JPanel
 */

public class Resize {
  private int rows;
  private int cols;
  private Point start;

  /**
   * O Construtor da classe que realiza o resize de um objeto atravez de uma representacao (linhas e colunas) e o ponto inicial.
   *
   * @param int rows,
   * @param int cols,
   * @param Point start, ponto inicial
   */
  
  public Resize(int rows, int cols, Point start){
    this.rows = rows;
    this.cols = cols;
    this.start = start;
  }

  /**
   * Metodo  que realiza  a recuperção de uma operacao resize.
   *
   * @param float constX
   * @param float constY
   */
  
  public Point[][] getResizedCoordinates(float constX, float constY){
    Point[][] response = new Point[rows][cols];

    for(int i = 0; i < response.length; i++){
      for(int j = 0; j < response[0].length; j++){
        response[i][j] = new Point(Math.round(i*constX) + start.x, Math.round(j*constY) + start.y);
      }
    }

    return response;

  }
}
