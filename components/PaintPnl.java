package components;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

public class PaintPnl extends JPanel{
  Configurations configurations;
  ArrayList<Point> points;
  ArrayList<Point[]> lines;
  ArrayList<Point[]> linesB;
  ArrayList<Point[]> rectangles;
  ArrayList<Point[]> circles;

  public PaintPnl(Configurations configurations){
    setBorder(BorderFactory.createLineBorder(Color.BLACK));
    setBackground(Color.WHITE);
    this.configurations = configurations;
    this.points = new ArrayList<>();
    this.lines = new ArrayList<>();
    this.linesB = new ArrayList<>();
    this.rectangles = new ArrayList<>();
    this.circles = new ArrayList<>();

    addMouseListener(new MouseAdapter(){
      @Override
      public void mousePressed(MouseEvent e){
        switch(configurations.getMODE()){
          case 0:
            points.add(e.getPoint());
            break;

          case 1:
            Point[] line1 = new Point[2];
            line1[0] = e.getPoint();
            line1[1] = e.getPoint();
            line1[1].x += 1;
            line1[1].y += 1;
            lines.add(line1);

            break;
          
          case 2:
            Point[] line2 = new Point[2];
            line2[0] = e.getPoint();
            line2[1] = e.getPoint();
            line2[1].x += 1;
            line2[1].y += 1;
            linesB.add(line2);
            break;

          case 3:
            Point[] rectangle = new Point[2];
            rectangle[0] = e.getPoint();
            rectangle[1] = e.getPoint();
            rectangle[1].x += 1;
            rectangle[1].y += 1;
            rectangles.add(rectangle);
            break;

          case 4:
            Point[] circle = new Point[2];
            circle[0] = e.getPoint();
            circle[1] = e.getPoint();
            circle[1].x++;
            circle[1].y++;
            circles.add(circle);
            break;

          default:
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
            points.add(e.getPoint());
            break;

          case 1:
            Point[] line1 = lines.remove(lines.size()-1);
            line1[1].x = e.getX();
            line1[1].y = e.getY();
            lines.add(line1);
            break;

          case 2:
            Point[] line2 = linesB.remove(linesB.size()-1);
            line2[1].x = e.getX();
            line2[1].y = e.getY();
            linesB.add(line2);
            break;

          case 3:
            Point[] rectangle = rectangles.remove(rectangles.size()-1);
            rectangle[1].x = e.getX();
            rectangle[1].y = e.getY();
            rectangles.add(rectangle);
            break;

          case 4:
            Point[] circle = circles.remove(circles.size()-1);
            circle[1].x = e.getX();
            circle[1].y = e.getY();
            circles.add(circle);
            break;

          default:
            break;
        }
        repaint();
      }
    });
  }

  public Dimension getPreferredSize(){
    return new Dimension(800, 600);
  }

  public void paintComponent(Graphics g){
    super.paintComponent(g);
    g.setColor(Color.RED);
    for(Point i : points){
      g.fillOval(i.x, i.y, 5, 5);
    }

    for(Point[] i : lines){
      DDA(i[0].x, i[0].y, i[1].x, i[1].y, g);
    }

    for(Point[] i : linesB){
      Bresenham(i[0].x, i[0].y, i[1].x, i[1].y, g);
    }

    for(Point[] i : rectangles){
      Bresenham(i[0].x, i[0].y, i[1].x, i[0].y, g);
      Bresenham(i[0].x, i[0].y, i[0].x, i[1].y, g);
      Bresenham(i[1].x, i[0].y, i[1].x, i[1].y, g);
      Bresenham(i[0].x, i[1].y, i[1].x, i[1].y, g);
    }

    for(Point[] i : circles){
      cirdBresenham(i[0].x, i[0].y, (int) i[0].distance(i[1]) , g);
    }
  }

  private void DDA(int x0, int y0, int x1, int y1, Graphics g){
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
    g.fillOval(Math.round(x), Math.round(y), 5, 5);
    for(int i = 0; i < passos; i++){
      x = x + xIncr;
      y = y + yIncr;
      g.fillOval(Math.round(x), Math.round(y), 5, 5);
    }
  }

  private void Bresenham(int x0, int y0, int x1, int y1, Graphics g){
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
    g.fillOval(x, y, 5, 5);

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
        g.fillOval(x, y, 5, 5);
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

        g.fillOval(x, y, 5, 5);
      }
    }
  }

  private void plotCirclePoints(int xc, int yc, int x, int y, Graphics g){
    g.drawOval(xc+x, yc+y, 5, 5);
    g.drawOval(xc-x, yc+y, 5, 5);
    g.drawOval(xc+x, yc-y, 5, 5);
    g.drawOval(xc-x, yc-y, 5, 5);
    g.drawOval(xc+y, yc+x, 5, 5);
    g.drawOval(xc-y, yc+x, 5, 5);
    g.drawOval(xc+y, yc-x, 5, 5);
    g.drawOval(xc-y, yc-x, 5, 5);
  }

  private void cirdBresenham(int xc, int yc, int r, Graphics g){
    int x = 0,y = r,p = 3 - 2 * r;
    plotCirclePoints(xc, yc, x, y, g);
    while(x < y){
      if(p < 0){
        p = p + 4*x + 6;
      } else{
        p = p + 4*(x-y) + 10;
        y--;
      }
      x++;
      plotCirclePoints(xc, yc, x, y, g);
    }
  }
  
}