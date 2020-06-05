package curves.hermite;

public class Vector4 {

  public double v[] = new double[4];

  public Vector4() {
  }

  public Vector4(double a, double b, double c, double d) {
    v[0] = a;
    v[1] = b;
    v[2] = c;
    v[3] = d;
  }

  public double[] getV() {
    return v;
  }

  public double getValue(int index) {
    return v[index];
  }

  public void setValue(int index, double value) {
    v[index] = value;
  }

  public double DotProduct(Vector4 b) {
    return v[0] * b.v[0] + v[1] * b.v[1] + v[2] * b.v[2] + v[3] * b.v[3];
  }

}