/*------------------------------------------------------------------------------
 Interpolated Curve: Drawing Curve using Interpolated Curve Algorithm
 * Advanced Computer Graphics
 * Written by: Saul G.C. Melo
 */
package curves.interpolated;

import components.PaintPnl;
import java.awt.*;

/*------------------------------------------------------------------------------
 * Class InterpolatedCurve using Bresenham between points
 */
public class InterpolatedCurve {
  PaintPnl paintPnl;
  Point P0, P1, P2, P3;

  public InterpolatedCurve(PaintPnl paintPnl) {
    this.paintPnl = paintPnl;
  }

  public void setP0(Point p0) {
    P0 = p0;
  }

  public void setP1(Point p1) {
    P1 = p1;

  }

  public void setP2(Point p2) {
    P2 = p2;
  }

  public void setP3(Point p3) {
    P3 = p3;
  }

  /*--------------------------------------------------------------------------
   *  Compute Interpolated Cubic Points
   *  +-----------------+   +-----------+
   *  | -4.5   13.5  -13.5   4.5 |   |  x0   y0  |
   *  |  9    -22.5   18    -4.5 | * |  x1   y1  | * [t^3 t^2 t 1] = [x, y]
   *  | -5.5   9     -4.5    1   |   |  x2   y2  |
   *  |  1     0      0       0  |   |  x3   y3  |
   *  +-----------------+   +-----------+
   -------------------------------------------------------------------------*/

  public Point[] cubic() {
    Point array[] = new Point[4];
    for (int i = 0; i < array.length; i++)
      array[i] = new Point(0, 0);

    /*------------------ Firt Column --------------------------*/
    array[3].x = (int) ((-4.5 * P0.x) + (13.5 * P1.x) + (-13.5 * P2.x) + (4.5 * P3.x));
    array[2].x = (int) ((9 * P0.x) + (-22.5 * P1.x) + (18 * P2.x) + (-4.5 * P3.x));
    array[1].x = (int) ((-5.5 * P0.x) + (9 * P1.x) + (-4.5 * P2.x) + (1 * P3.x));
    array[0].x = (int) ((1 * P0.x) + (0) + (0) + (0));

    /*------------------ Second  Column -----------------------*/
    array[3].y = (int) ((-4.5 * P0.y) + (13.5 * P1.y) + (-13.5 * P2.y) + (4.5 * P3.y));
    array[2].y = (int) ((9 * P0.y) + (-22.5 * P1.y) + (18 * P2.y) + (-4.5 * P3.y));
    array[1].y = (int) ((-5.5 * P0.y) + (9 * P1.y) + (-4.5 * P2.y) + (1 * P3.y));
    array[0].y = (int) ((1 * P0.y) + (0) + (0) + (0));

    /*---------------------------------------------------------*/

    return array;

  }
  /*------------------------------------------------------------------------
   * Create Steps Method
   * Increasing steps make curve smooth
   * Calculate "z" as 3rd dimension
   */

  public void steps(double step) {

    Point array[] = cubic();
    Point array2[] = new Point[(int) (1 / step)];
    int i = 0;

    for (double u = 0.00; u < 1; u += step) {
      array2[i] = new Point(0, 0);
      array2[i].x = (int) (array[0].x + array[1].x * u + array[2].x * u * u + array[3].x * u * u * u);
      array2[i].y = (int) (array[0].y + array[1].y * u + array[2].y * u * u + array[3].y * u * u * u);

      paintPnl.setPixel(array2[i].x, array2[i].y);
      i++;
    }

    int j;
    for (j = 0; j < (int) (1 / step) - 1; j++) {

      paintPnl.Bresenham(array2[j].x, array2[j].y, array2[j + 1].x, array2[j + 1].y);
    }

  }
}

/*----------------------------------------------------------------------------*/