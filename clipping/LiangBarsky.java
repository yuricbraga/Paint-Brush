package clipping;

/**
* Classe responsavel pelo algoritmo de recorte de regi�es, Liang-Barsky
*
* @author Ian
* @author Saul Melo
* @author Yuri
* @since 04 de 2020 
* @version 1
*/

public class LiangBarsky implements LineClipper {
  private int xMin;
  private int xMax;
  private int yMin;
  private int yMax;

  /**
  * Construtor parametrizado da classe
  *
  * @param int, O menor valor da coordenada x pertencente a regiao
  * @param int, O menor valor da coordenada y pertencente a regiao
  * @param int, O maior valor da coordenada x pertencente a regiao
  * @param int, O maior valor da coordenada y pertencente a regiao
  */
  public LiangBarsky(int xMin, int yMin, int xMax, int yMax) {
    this.xMin = xMin;
    this.xMax = xMax;
    this.yMin = yMin;
    this.yMax = yMax;
  }

  /**
  * Algoritmo Liang-Barsky para o recorte de regioes
  *
  * @param LineSegment, Objeto do tipo LineSegment contendo as dimensoes da janela
  */
  public LineSegment clip(LineSegment line) {
    System.out.println("\nExecuting Liang-Barsky...");
    double u1 = 0, u2 = 1;
    int x0 = line.x0, y0 = line.y0, x1 = line.x1, y1 = line.y1;
    int dx = x1 - x0, dy = y1 - y0;
    int p[] = { -dx, dx, -dy, dy };
    int q[] = { x0 - xMin, xMax - x0, y0 - yMin, yMax - y0 };
    for (int i = 0; i < 4; i++) {
      if (p[i] == 0) {
        if (q[i] < 0) {
          return null;
        }
      } else {
        double u = (double) q[i] / p[i];
        if (p[i] < 0) {
          u1 = Math.max(u, u1);
        } else {
          u2 = Math.min(u, u2);
        }
      }
    }
    System.out.println("u1: " + u1 + ", u2: " + u2);
    if (u1 > u2) {
      return null;
    }
    int nx0, ny0, nx1, ny1;
    nx0 = (int) (x0 + u1 * dx);
    ny0 = (int) (y0 + u1 * dy);
    nx1 = (int) (x0 + u2 * dx);
    ny1 = (int) (y0 + u2 * dy);
    return new LineSegment(nx0, ny0, nx1, ny1);
  }
}