package helpers;

import java.awt.*;
import java.util.*;

/**
 * Classe que implementa o algoritmo de rotacao
 *
 * @author Ian
 * @author Saul Melo
 * @author Yuri
 * @since 04 de 2020
 * @version 1.2 <extends> JPanel
 */

public class Rotation {
  private Color subMatrix[][];
  private Point center;
  private Point start;
  private Point end;

  /**
   * O Construtor da classe que realiza a rotacao de um objeto atravez de uma representacao (matriz), um ponto inicial e ponto final.
   *
   * @param  Color submatrix[][], matriz que representa o objeto
   * @param Point start, ponto inicial
   * @param Point end, ponto final
   */
  
  public Rotation(Color subMatrix[][], Point start, Point end){
    this.subMatrix = subMatrix;
    this.start = start;
    this.end = end;

    setCenter((int) this.subMatrix.length/2, (int) this.subMatrix[0].length/2);
  }

  public void setCenter(int x, int y){
    center = new Point(x,y);
  }

  /**
   * Metodo  que realiza  a recuperacao de uma operacao de rotacao atraves de um angulo teta
   *
   * @param double teta, angulo da rotacao
   */
  
  public Point[][] getRotatedCoodinates(double teta){
    Point response[][] = new Point[subMatrix.length][subMatrix[0].length];

    for(int i = 0; i < response.length; i++){
      for(int j = 0; j < response[0].length; j++){
        response[i][j] = rotate(i, j, teta);
      }
    }

    return response;
  }

   /**
   * Metodo  que realiza uma operacao de rotacao atraves dois pontos e  um angulo teta
   *
   * @param int x, ponto da rotacao
   * @param int y, ponto da rotacao
   * @param double teta, angulo da rotacao
   */
  
  private Point rotate(int x, int y, double teta){
    Point shiftedPoint = new Point(x - center.x,y - center.y);
    Point rotatedPoint = new Point();

    rotatedPoint.x = ((int) (shiftedPoint.x * Math.cos(teta) - shiftedPoint.y * Math.sin(teta))) + center.x + start.x;
    rotatedPoint.y = ((int) (shiftedPoint.x * Math.sin(teta) + shiftedPoint.y * Math.cos(teta))) + center.y + start.y;

    return rotatedPoint;
  }
}
