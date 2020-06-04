package curves.hermite;

public class Matrix4 {

  public Vector4 M[] = new Vector4[4];

  public Matrix4(double A[]) {
    int count = 0;
    for (int i = 0; i < 4; i++) {
      M[i] = new Vector4();
      for (int j = 0; j < 4; j++) {
        System.out.println(A[count]);
        System.out.println(M[i].getValue(0));
        M[i].setValue(j, A[count]);
        count++;
      }
    }
  }

  public Vector4[] getM() {
    return M;
  }

  Vector4 multiply(Vector4 b) {
    Vector4 res = new Vector4();
    double count = 0.0d;
    for (int i = 0; i < 4; i++) {

      for (int j = 0; j < 4; j++) {

        count += M[i].getValue(j) * b.getValue(j);
      }
      res.setValue(i, count);
      count = 0;
    }

    return res;
  }
}