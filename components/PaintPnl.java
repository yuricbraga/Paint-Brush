package components;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

public class PaintPnl extends JPanel{
  Configurations configurations;
  boolean canvas[][];
  boolean canvasCopy[][];
  Point point0;

  public PaintPnl(Configurations configurations){
    setBorder(BorderFactory.createLineBorder(Color.BLACK));
    setBackground(Color.WHITE);
    this.configurations = configurations;

    addMouseListener(new MouseAdapter(){
      @Override
      public void mousePressed(MouseEvent e){
        switch(configurations.getMODE()){
          case 0:
            setPixel(e.getX(), e.getY());
            break;

          default:
            canvasCopy = cloneMatrix(canvas);
            point0 = e.getPoint();

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
            Bresenham(point0.x, point0.y, e.getX(), point0.y);
            Bresenham(point0.x, point0.y, point0.x, e.getY());
            Bresenham(e.getX(), point0.y, e.getX(), e.getY());
            Bresenham(point0.x, e.getY(), e.getX(), e.getY());

            break;

          case 4:
            canvas = cloneMatrix(canvasCopy);
            cirdBresenham(point0.x, point0.y, (int) point0.distance(e.getPoint()));

            break;

          default:
            break;
        }
        repaint();
      }
    });
  }

  public Dimension getPreferredSize(){
    this.canvas = new boolean[800][600];
    return new Dimension(800, 600);
  }

  @Override
  public void paintComponent(Graphics g){
    super.paintComponent(g);
    g.setColor(Color.RED);

    for(int row = 0; row < this.canvas.length; row++){
      for(int col = 0; col < this.canvas[row].length; col++){
        if(canvas[row][col]){
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

  private void setPixel(int x, int y){
    if(x >= 0 && x < 800 && y >= 0 && y < 600){
      this.canvas[x][y] = true;
    }
  }

  private boolean[][] cloneMatrix(boolean [][]matrix){
    boolean copy[][] = new boolean[matrix.length][matrix[0].length];

    for(int row = 0; row < copy.length; row++){
      for(int col = 0; col < copy[0].length; col++){
        copy[row][col] = matrix[row][col];
      }
    }

    return copy;
  }
  
}