package curves.hermite;

import java.awt.*;
import components.PaintPnl;

public class Curve {

  private Point p1;
  private Point p2;
  private Point p3;
  private Point p4;
  private double H[] = { 2, 1, -2, 1, -3, -2, 3, -1, 0, 1, 0, 0, 1, 0, 0, 0 };
  private PaintPnl paintPnl;

  public Curve(PaintPnl paintPnl) {
    this.paintPnl = paintPnl;
  }

  public Curve(Point p1, Point p2, Point p3, Point p4) {
    this.p1 = p1;
    this.p2 = p2;
    this.p3 = p3;
    this.p4 = p4;

  }

  public Point getP1() {
    return p1;
  }

  public Point getP2() {
    return p2;
  }

  public void setP1(Point p1) {
    this.p1 = p1;
  }

  public void setP2(Point p2) {
    this.p2 = p2;
  }

  public Point getP3() {
    return p3;
  }

  public Point getP4() {
    return p4;
  }

  public void setP3(Point p3) {
    this.p3 = p3;
  }

  public void setP4(Point p4) {
    this.p4 = p4;
  }

  public void StartHermiteCurve() {
    DrawHermiteCurve(p1, p2, p3, p4, 140);
  }

  Vector4 GetHermiteCoeff(double x0, double s0, double x1, double s1) {

    Matrix4 basis = new Matrix4(H);
    Vector4 v = new Vector4(x0, s0, x1, s1);
    return basis.multiply(v);
  }

  void DrawHermiteCurve(Point P0, Point T0, Point P1, Point T1, int numpoints) {
    Vector4 xcoeff = GetHermiteCoeff(P0.getX(), T0.getX(), P1.getX(), T1.getX());
    Vector4 ycoeff = GetHermiteCoeff(P0.getY(), T0.getY(), P1.getY(), T1.getY());
    System.out.println("Xcoeff" + xcoeff.getValue(0) + ", " + xcoeff.getValue(1) + ", " + xcoeff.getValue(2) + ", "
        + xcoeff.getValue(3));
    System.out.println("Ycoeff" + ycoeff.getValue(0) + ", " + ycoeff.getValue(1) + ", " + ycoeff.getValue(2) + ", "
        + ycoeff.getValue(3) + ", ");

    if (numpoints < 2) {
      return;
    }
    double dt = 1.0 / (numpoints - 1);

    for (double t = 0; t <= 1; t += dt) {
      Vector4 vt = new Vector4();
      vt.setValue(3, 1);
      for (int i = 2; i >= 0; i--) {
        vt.setValue(i, vt.getValue(i + 1) * t);
      }
      int x = (int) Math.round(xcoeff.DotProduct(vt));
      int y = (int) Math.round(ycoeff.DotProduct(vt));
      this.paintPnl.setPixel(x, y);
    }
  }

}