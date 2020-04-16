package helpers;

import java.awt.*;

public class CustomColor extends Color {
  private int pixelSize;

  public CustomColor(int r, int g, int b) {
    super(r, g, b);
  }

  /**
   * @param pixelSize the pixelSize to set
   */
  public void setPixelSize(int pixelSize) {
    this.pixelSize = pixelSize;
  }

  /**
   * @return the pixelSize
   */
  public int getPixelSize() {
    return pixelSize;
  }

}