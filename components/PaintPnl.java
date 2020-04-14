package components;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import helpers.Rotation;

public class PaintPnl extends JPanel{
  Configurations configurations;
  Color canvas[][];
  Color canvasCopy[][];
  Color selectedRegion[][];
  Point point0;
  Point point0Normalized;
  JFrame parent;

  public PaintPnl(Configurations configurations, JFrame parent){
    setBorder(BorderFactory.createLineBorder(Color.BLACK));
    setBackground(Color.WHITE);
    this.configurations = configurations;
    this.parent = parent;

    addMouseListener(new MouseAdapter(){
      @Override
      public void mousePressed(MouseEvent e){
        clearColor(Color.BLACK);

        switch(configurations.getMODE()){
          case 0:
            configurations.setColor(Color.GREEN);
            setPixel(e.getX(), e.getY());
            break;

          case 5:
            configurations.setColor(Color.BLACK);
            canvasCopy = cloneMatrix(canvas);
            point0 = e.getPoint();
            break;

          case 50:
            clearRegion(canvasCopy, point0.x, point0.y, point0.x + selectedRegion.length, point0.y + selectedRegion[0].length);
            break;

          case 6:
            configurations.setColor(Color.BLACK);
            canvasCopy = cloneMatrix(canvas);
            point0 = e.getPoint();
            break;

          default:
            configurations.setColor(Color.BLUE);
            canvasCopy = cloneMatrix(canvas);
            point0 = e.getPoint();
            break;
        }


        repaint();
      }

    });

    addMouseListener(new MouseAdapter(){
      @Override
      public void mouseReleased(MouseEvent e){
        switch(configurations.getMODE()){
          case 5:
            configurations.setMODE(50);
            break;

          case 6:
            Double degrees = Double.parseDouble((String)JOptionPane.showInputDialog(parent, "A rotação será de quantos graus?", "", JOptionPane.PLAIN_MESSAGE, null, null, ""));

            clearRegion(canvasCopy, point0Normalized.x, point0Normalized.y, point0Normalized.x + selectedRegion.length, point0Normalized.y + selectedRegion[0].length);
            canvas = cloneMatrix(canvasCopy);
            Rotation rotation = new Rotation(selectedRegion, point0Normalized, null);
            Point rotatedCoordinates[][] = rotation.getRotatedCoodinates(Math.toRadians(degrees));
            for(int i = 0; i < rotatedCoordinates.length; i++){
              for(int j = 0; j < rotatedCoordinates[0].length; j++){
                if(rotatedCoordinates[i][j].x >= 0 && rotatedCoordinates[i][j].y >= 0 && rotatedCoordinates[i][j].x <= 799 && rotatedCoordinates[i][j].y <= 599 ){
                  canvas[rotatedCoordinates[i][j].x][rotatedCoordinates[i][j].y] = selectedRegion[i][j];
                }
              }
            }
            clearColor(Color.BLACK);
            break;
 
        }
        repaint();
     }
    });

    addMouseMotionListener(new MouseAdapter(){
      @Override
      public void mouseDragged(MouseEvent e){
        switch(configurations.getMODE()){
          case 0:
            setPixel(e.getX(), e.getY());
            break;

          case 1:
            canvas = cloneMatrix(canvasCopy);
            DDA(point0.x, point0.y, e.getX(), e.getY());

            break;

          case 2:
            canvas = cloneMatrix(canvasCopy);
            Bresenham(point0.x, point0.y, e.getX(), e.getY());

            break;

          case 3:
            canvas = cloneMatrix(canvasCopy);
            rectBresenham(point0.x, point0.y, e.getX(), e.getY());

            break;

          case 4:
            canvas = cloneMatrix(canvasCopy);
            cirdBresenham(point0.x, point0.y, (int) point0.distance(e.getPoint()));

            break;

          case 5:
            canvas = cloneMatrix(canvasCopy);
            rectBresenham(point0.x, point0.y, e.getX(), e.getY());
            selectRegion(point0.x, point0.y, e.getX(), e.getY());

            break;

          case 50:
            canvas = cloneMatrix(canvasCopy);
            moveRegion(e.getX(), e.getY());
            break;

          case 6:
            canvas = cloneMatrix(canvasCopy);
            int normalized[] = normalizeCoordinates(point0.x, point0.y, e.getX(), e.getY());
            rectBresenham(normalized[0], normalized[1], normalized[2], normalized[3]);
            selectRegion(normalized[0], normalized[1], normalized[2], normalized[3]);
            point0Normalized = new Point(normalized[0], normalized[1]);
            break;

          default:
            break;
        }
        repaint();
      }
    });

  }

  public Dimension getPreferredSize(){
    this.canvas = new Color[800][600];
    return new Dimension(800, 600);
  }

  @Override
  public void paintComponent(Graphics g){
    super.paintComponent(g);

    for(int row = 0; row < this.canvas.length; row++){
      for(int col = 0; col < this.canvas[row].length; col++){
        if(canvas[row][col] != null){
          g.setColor(canvas[row][col]);
          g.fillOval(row, col, 5, 5);
        }
      }
    }
  }

  private void DDA(int x0, int y0, int x1, int y1){
    int dx, dy, passos;
    float xIncr, yIncr, x, y;

    dx = x1 - x0;
    dy = y1 - y0;
    if(Math.abs(dx) > Math.abs(dy)){
      passos = Math.abs(dx);
    } else{
      passos = Math.abs(dy);
    }

    xIncr = dx / (float) passos;
    yIncr = dy / (float) passos;
    x = x0;
    y = y0;
    setPixel(Math.round(x), Math.round(y));
    for(int i = 0; i < passos; i++){
      x = x + xIncr;
      y = y + yIncr;
      setPixel(Math.round(x), Math.round(y));
    }
  }

  private void Bresenham(int x0, int y0, int x1, int y1){
    int dx, dy, x, y, i, const1, const2, p, incrx, incry;
    dx = x1 - x0;
    dy = y1 - y0;
    if(dx >= 0){
      incrx = 1;
    } else{
      incrx = -1;
      dx = -dx;
    }

    if(dy >= 0){
      incry = 1;
    } else{
      incry = -1;
      dy = -dy;
    }

    x = x0;
    y = y0;
    setPixel(x, y);

    if(dy < dx){
      p = 2*dy - dx;
      const1 = 2*dy;
      const2 = 2*(dy-dx);
      for(i = 0; i < dx; i++){
        x += incrx;
        if(p < 0){
          p += const1;
        } else{
          y += incry;
          p += const2;
        }
        setPixel(x, y);
      }
    } else{
      p = 2*dx - dy;
      const1 = 2*dx;
      const2 = 2*(dx-dy);
      for(i = 0; i < dy; i++){
        y += incry;
        if(p < 0){
          p += const1;
        } else{
          x += incrx;
          p += const2;
        }

        setPixel(x, y);
      }
    }
  }

  private void plotCirclePoints(int xc, int yc, int x, int y){
    setPixel(xc+x,yc+y);
    setPixel(xc-x,yc+y);
    setPixel(xc+x,yc-y);
    setPixel(xc-x,yc-y);
    setPixel(xc+y,yc+x);
    setPixel(xc-y,yc+x);
    setPixel(xc+y,yc-x);
    setPixel(xc-y,yc-x);
  }

  private void cirdBresenham(int xc, int yc, int r){
    int x = 0,y = r,p = 3 - 2 * r;
    plotCirclePoints(xc, yc, x, y);
    while(x < y){
      if(p < 0){
        p = p + 4*x + 6;
      } else{
        p = p + 4*(x-y) + 10;
        y--;
      }
      x++;
      plotCirclePoints(xc, yc, x, y);
    }
  }

  private void rectBresenham(int x0, int y0, int x1, int y1){
    Bresenham(x0, y0, x1, y0);
    Bresenham(x0, y0, x0, y1);
    Bresenham(x1, y0, x1, y1);
    Bresenham(x0, y1, x1, y1);
  }

  private void selectRegion(int x0, int y0, int x1, int y1){
    int rows = x1 - x0;
    int cols = y1 - y0;
    selectedRegion = new Color[rows+1][cols+1];

    for(int i = x0; i <= x1; i++){
      for(int j = y0; j <= y1; j++){
        selectedRegion[i - x0][j - y0] = canvas[i][j];
      }
    }

  }

  private void moveRegion(int x, int y){
    for(int i = x; i < x + selectedRegion.length; i++){
      for(int j = y; j < y + selectedRegion[0].length; j++){
        canvas[i][j] = selectedRegion[i-x][j-y];
      }
    }
  }

  private void clearRegion(Color pixelMatrix[][],int x0, int y0, int x1, int y1){
    for(int i = x0; i < x1; i++){
      for(int j = y0; j < y1; j++){
        pixelMatrix[i][j] = null;
      }
    }
  }

  private void clearColor(Color color){
    for(int i = 0; i < canvas.length; i++){
      for(int j = 0; j < canvas[0].length; j++){
        if(canvas[i][j] == color){
          canvas[i][j] = null;
        }
      }
    }
  }

  private int[] normalizeCoordinates(int x0, int y0, int x1, int y1){
    int response[] = {0,0,0,0};

    if(x1 > x0){
      response[0] = x0;
      response[2] = x1;
    } else{
      response[0] = x1;
      response[2] = x0;
    }

    if(y1 > y0){
      response[1] = y0;
      response[3] = y1;
    } else{
      response[1] = y1;
      response[3] = y0;
    }

    return response;
  }

  private void setPixel(int x, int y){
    if(x >= 0 && x < 800 && y >= 0 && y < 600){
      this.canvas[x][y] = configurations.getColor();
    }
  }

  private Color[][] cloneMatrix(Color [][]matrix){
    Color copy[][] = new Color[matrix.length][matrix[0].length];

    for(int row = 0; row < copy.length; row++){
      for(int col = 0; col < copy[0].length; col++){
        copy[row][col] = matrix[row][col];
      }
    }

    return copy;
  }
  
}