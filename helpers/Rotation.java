package helpers;

import java.awt.*;
import java.util.*;

public class Rotation {
  private Color subMatrix[][];
  private Point center;
  private Point start;
  private Point end;

  public Rotation(Color subMatrix[][], Point start, Point end){
    this.subMatrix = subMatrix;
    this.start = start;
    this.end = end;

    setCenter((int) this.subMatrix.length/2, (int) this.subMatrix[0].length/2);
  }

  public void setCenter(int x, int y){
    center = new Point(x,y);
  }

  public Point[][] getRotatedCoodinates(double teta){
    Point response[][] = new Point[subMatrix.length][subMatrix[0].length];

    for(int i = 0; i < response.length; i++){
      for(int j = 0; j < response[0].length; j++){
        response[i][j] = rotate(i, j, teta);
      }
    }

    return response;
  }

  private Point rotate(int x, int y, double teta){
    Point shiftedPoint = new Point(x - center.x,y - center.y);
    Point rotatedPoint = new Point();

    rotatedPoint.x = ((int) (shiftedPoint.x * Math.cos(teta) - shiftedPoint.y * Math.sin(teta))) + center.x + start.x;
    rotatedPoint.y = ((int) (shiftedPoint.x * Math.sin(teta) + shiftedPoint.y * Math.cos(teta))) + center.y + start.y;

    return rotatedPoint;
  }
}