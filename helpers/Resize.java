package helpers;

import java.awt.*;

public class Resize {
  private int rows;
  private int cols;
  private Point start;

  public Resize(int rows, int cols, Point start){
    this.rows = rows;
    this.cols = cols;
    this.start = start;
  }

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