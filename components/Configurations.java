package components;

import java.awt.*;

public class Configurations{
  private int MODE;
  private Color color;

  public Configurations(int MODE, Color color){
    this.MODE = MODE;
    this.color = color;
  }

 public Color getColor() {
    return color;
  }

 public int getMODE() {
    return MODE;
  }

 public void setColor(Color color) {
    this.color = color;
  }

  public void setMODE(int MODE) {
    this.MODE = MODE;
  }
}