/*------------------------------------------------------------------------------
 * Algoritmo para Curvas de Hemrite
 * Yuri Braga, Saul Melo, Ian Rodrigues
 */
package curves.hermite;

import components.PaintPnl;
import java.awt.*;

/*------------------------------------------------------------------------------
 * Classe para construção de uma curva hermite
 */
public class Hermite {
  private PaintPnl paintPnl;
  private Point P0, P1, P2, P3, P2Copy, P3Copy;

  public Hermite(PaintPnl paintPnl) {
    this.paintPnl = paintPnl;
  }//

  public void setP0(Point p0) {
    P0 = p0;
  }//

  public void setP1(Point p1) {
    P1 = p1;
  }//

  public void setP2(Point p2) {
    P2 = p2;
    P2Copy = new Point(P2);
  }//

  public void setP3(Point p3) {
    P3 = p3;
    P3Copy = new Point(P3);
  }//

  /*--------------------------------------------------------------------------
   * Pontos Cúbicos Hemite
   -------------------------------------------------------------------------*/

  public Point[] cubic() {

    Point array[] = { new Point((1 * P0.x) + (0) + (0) + (0), (1 * P0.y) + (0) + (0) + (0)),
        new Point((0) + (0) + (1 * P2.x) + (0), (0) + (0) + (1 * P2.y) + (0)),
        new Point((-3 * P0.x) + (3 * P1.x) + (-2 * P2.x) + (-1 * P3.x),
            (-3 * P0.y) + (3 * P1.y) + (-2 * P2.y) + (-1 * P3.y)),
        new Point((2 * P0.x) + (-2 * P1.x) + (1 * P2.x) + (1 * P3.x),
            (2 * P0.y) + (-2 * P1.y) + (1 * P2.y) + (1 * P3.y)) };

    return array;

  }//
  /*------------------------------------------------------------------------
   * Metodo steps que 
   * auxiliam na suavidade da curva
   */

  public void steps(double step) {
    // Compute Vector
    P2 = new Point(P2Copy.x - P0.x, P2Copy.y - P0.y);
    P3 = new Point(P3Copy.x - P1.x, P3Copy.y - P1.y);

    Point array[] = cubic();

    Point array2[] = new Point[(int) (1 / step)];
    int i = 0;

    for (double u = 0.00; u < 1; u += step) {
      array2[i] = new Point((int) (array[0].x + array[1].x * u + array[2].x * u * u + array[3].x * u * u * u),
          (int) (array[0].y + array[1].y * u + array[2].y * u * u + array[3].y * u * u * u));

      paintPnl.setPixel(array2[i].x, array2[i].y);
      i++;

    }

    int j;
    for (j = 0; j < (int) (1 / step) - 1; j++) {

      paintPnl.Bresenham(array2[j].x, array2[j].y, array2[j + 1].x, array2[j + 1].y);
    }

    // Chamada para o desenho da curva

    paintPnl.Bresenham(array2[j].x, array2[j].y, P1.x, P1.y);

  }
}

/*----------------------------------------------------------------------------*/
