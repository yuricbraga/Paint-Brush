package helpers;

import java.awt.*;

public class Reflexion {
  private Color matrix[][];
  private Point start;

  public Reflexion(Color matrix[][], Point start){
    this.matrix = matrix;
    this.start = start;
  }

  public Color[][] getReflection(boolean x, boolean y){
    if(x && y){
      for(int i = 0; i < matrix.length/2; i++){
        for(int j = 0; j < matrix[0].length; j++){
          swap(i,j,matrix.length-i-1,matrix[0].length-j-1);
        }
      }
    } else if(y){
      for(int i = 0; i < matrix.length; i++){
        for(int j = 0; j < matrix[0].length/2; j++){
          swap(i,j,i,matrix[0].length-j-1);
        }
      }
    } else if(x){
      for(int i = 0; i < matrix.length/2; i++){
        for(int j = 0; j < matrix[0].length; j++){
          swap(i,j,matrix.length-i-1,j);
        }
      }
    }

    return matrix;
  }

  private void swap(int x0, int y0, int x1, int y1){
    Color aux = matrix[x0][y0];
    matrix[x0][y0] = matrix[x1][y1];
    matrix[x1][y1] = aux;
  }
}