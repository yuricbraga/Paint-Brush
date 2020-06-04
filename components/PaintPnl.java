package components;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import helpers.*;
import clipping.*;
import curves.hermite.Curve;

/**
 * Classe que implementa os algoritmos principais
 *
 * @author Ian
 * @author Saul Melo
 * @author Yuri
 * @since 04 de 2020
 * @version 1.2 <extends> JPanel
 */

public class PaintPnl extends JPanel {
  private final Color CUSTOMGREEN = new Color(0, 254, 0);
  private final Color CUSTOMBLACK = new Color(0, 0, 1);

  private Configurations configurations;
  private Color canvas[][];
  private Color canvasCopy[][];
  private Color selectedRegion[][];
  private Point point0;
  private int normalized[];
  private JFrame parent;
  private Curve curveCurrentObj;
  private int clicks;

  private ArrayList<LineSegment> lines;
  private LineClipper clipper;
  private LineSegment clippedLine;

  /**
   * O Construtor da classe que realiza a captura dos eventos, A configuracao dos
   * parametros basicos e selecionada qual Algoritmo devera ser executado.
   *
   * @param Configurations, Objeto do tipo Configurations contendo a configuracao
   *                        de um componente JButton
   * @param JFrame,         Contendo o JFrame pai do software
   */
  public PaintPnl(Configurations configurations, JFrame parent) {
    setBorder(BorderFactory.createLineBorder(Color.BLACK));
    setBackground(Color.WHITE);
    this.configurations = configurations;
    this.parent = parent;
    this.lines = new ArrayList<>();

    configurations.setColor(Color.BLACK);

    addMouseListener(new MouseAdapter() {
      @Override
      public void mousePressed(MouseEvent e) {
        clearColor(CUSTOMBLACK);
        clearColor(CUSTOMGREEN);

        switch (configurations.getMODE()) {
          case 11:
            configurations.setColor(null);
            for (int i = -5; i < 5; i++) {
              for (int j = -5; j < 5; j++) {
                setPixel(e.getX() + i, e.getY() + j);
              }
            }
          case 0:
            setPixel(e.getX(), e.getY());
            break;

          case 1:
          case 2:
            canvasCopy = cloneMatrix(canvas);
            point0 = e.getPoint();
            break;

          case 50:
            clearRegion(canvasCopy, normalized[0], normalized[1], normalized[0] + selectedRegion.length,
                normalized[1] + selectedRegion[0].length);
            break;

          case 5:
          case 6:
          case 7:
          case 8:
          case 9:
          case 10:
            point0 = e.getPoint();
            configurations.setColor(CUSTOMBLACK);
            canvasCopy = cloneMatrix(canvas);
            break;

          default:
            canvasCopy = cloneMatrix(canvas);
            point0 = e.getPoint();
            break;
        }

        repaint();
      }

    });

    addMouseListener(new MouseAdapter() {
      @Override
      public void mouseReleased(MouseEvent e) {
        switch (configurations.getMODE()) {
          case 1:
          case 2:
            lines.add(new LineSegment(point0.x, point0.y, e.getPoint().x, e.getPoint().y));
            break;

          case 5:
            configurations.setMODE(50);
            break;

          case 6:
            Double degrees = Double.parseDouble((String) JOptionPane.showInputDialog(parent,
                "A rotacao sera de quantos graus?", "", JOptionPane.PLAIN_MESSAGE, null, null, ""));

            clearRegion(canvasCopy, normalized[0], normalized[1], normalized[0] + selectedRegion.length,
                normalized[1] + selectedRegion[0].length);
            canvas = cloneMatrix(canvasCopy);
            Rotation rotation = new Rotation(selectedRegion, new Point(normalized[0], normalized[1]), null);
            Point rotatedCoordinates[][] = rotation.getRotatedCoodinates(Math.toRadians(degrees));
            for (int i = 0; i < rotatedCoordinates.length; i++) {
              for (int j = 0; j < rotatedCoordinates[0].length; j++) {
                if (rotatedCoordinates[i][j].x >= 0 && rotatedCoordinates[i][j].y >= 0
                    && rotatedCoordinates[i][j].x <= 799 && rotatedCoordinates[i][j].y <= 599) {
                  canvas[rotatedCoordinates[i][j].x][rotatedCoordinates[i][j].y] = selectedRegion[i][j];
                }
              }
            }
            clearColor(CUSTOMBLACK);
            break;

          case 7:
            String response = (String) JOptionPane.showInputDialog(parent, "Como será a reflexão?", "",
                JOptionPane.PLAIN_MESSAGE, null, new String[] { "x e y", "apenas x", "apenas y" }, "");

            Reflexion reflexion = new Reflexion(cloneMatrix(selectedRegion), new Point(normalized[0], normalized[1]));

            switch (response) {
              case "x e y":
                selectedRegion = reflexion.getReflection(true, true);
                break;
              case "apenas x":
                selectedRegion = reflexion.getReflection(true, false);
                break;
              case "apenas y":
                selectedRegion = reflexion.getReflection(false, true);
                break;
            }

            moveRegion(normalized[0], normalized[1]);
            clearColor(CUSTOMBLACK);
            break;

          case 8:
            clearRegion(canvasCopy, normalized[0], normalized[1], normalized[0] + selectedRegion.length,
                normalized[1] + selectedRegion[0].length);
            canvas = cloneMatrix(canvasCopy);
            Resize resize = new Resize(selectedRegion.length, selectedRegion[0].length, point0);
            String xProportion = (String) JOptionPane.showInputDialog(parent, "Redimensionar x em quantos %?", "",
                JOptionPane.PLAIN_MESSAGE, null, null, "100");
            String yProportion = (String) JOptionPane.showInputDialog(parent, "Redimensionar y em quantos %?", "",
                JOptionPane.PLAIN_MESSAGE, null, null, "100");
            Point[][] resizedCoordinates = resize.getResizedCoordinates(Float.parseFloat(xProportion) / 100.0f,
                Float.parseFloat(yProportion) / 100.0f);
            for (int i = 0; i < resizedCoordinates.length; i++) {
              for (int j = 0; j < resizedCoordinates[0].length; j++) {
                if (resizedCoordinates[i][j].x >= 0 && resizedCoordinates[i][j].y >= 0
                    && resizedCoordinates[i][j].x <= 799 && resizedCoordinates[i][j].y <= 599) {
                  canvas[resizedCoordinates[i][j].x][resizedCoordinates[i][j].y] = selectedRegion[i][j];
                }
              }
            }
            clearColor(CUSTOMBLACK);
            break;

          case 9:
            clipper = new CohenSutherland(normalized[0], normalized[1], normalized[2], normalized[3]);
            for (LineSegment x : lines) {
              clippedLine = clipper.clip(x);

              if (clippedLine != null) {
                configurations.setColor(Color.GREEN);
                Bresenham(clippedLine.x0, clippedLine.y0, clippedLine.x1, clippedLine.y1);
              }
            }
            break;

          case 10:
            clipper = new LiangBarsky(normalized[0], normalized[1], normalized[2], normalized[3]);
            for (LineSegment x : lines) {
              clippedLine = clipper.clip(x);

              if (clippedLine != null) {
                configurations.setColor(CUSTOMGREEN);
                Bresenham(clippedLine.x0, clippedLine.y0, clippedLine.x1, clippedLine.y1);
              }
            }
            break;

          case 11:
            configurations.setPixelSize(2);
            break;

          case 12:
            if (clicks == 0) {
              curveCurrentObj = new Curve(getInstance());
              curveCurrentObj.setP1(new Point(e.getPoint()));

              clicks++;
            } else if (clicks == 1) {
              curveCurrentObj.setP2(new Point(e.getPoint()));

              clicks++;
            } else if (clicks == 2) {
              curveCurrentObj.setP3(new Point(e.getPoint()));
              clicks++;
            } else {
              curveCurrentObj.setP4(new Point(e.getPoint()));
              curveCurrentObj.StartHermiteCurve();
              clicks = 0;
            }

            break;
        }
        repaint();
      }
    });

    addMouseMotionListener(new MouseAdapter() {
      @Override
      public void mouseDragged(MouseEvent e) {
        switch (configurations.getMODE()) {
          case 11:
            for (int i = -5; i < 5; i++) {
              for (int j = -5; j < 5; j++) {
                setPixel(e.getX() + i, e.getY() + j);
              }
            }
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

          case 50:
            canvas = cloneMatrix(canvasCopy);
            moveRegion(e.getX(), e.getY());
            break;

          case 5:
          case 6:
          case 7:
          case 8:
          case 9:
          case 10:
            canvas = cloneMatrix(canvasCopy);
            normalized = normalizeCoordinates(point0.x, point0.y, e.getX(), e.getY());
            rectBresenham(normalized[0], normalized[1], normalized[2], normalized[3]);
            selectRegion(normalized[0], normalized[1], normalized[2], normalized[3]);
            break;

          default:
            break;
        }
        repaint();
      }
    });

  }

  /**
   * Este metodo realiza o dimensionamento ...
   */
  public Dimension getPreferredSize() {
    this.canvas = new Color[800][600];
    return new Dimension(800, 600);
  }

  /**
   * Este metodo sobrescreve o metodo paintComponent Para gerar um objeto de forma
   * circular
   *
   * @param Graphics
   */
  @Override
  public void paintComponent(Graphics g) {
    super.paintComponent(g);

    for (int row = 0; row < this.canvas.length; row++) {
      for (int col = 0; col < this.canvas[row].length; col++) {
        if (canvas[row][col] != null) {
          g.setColor(canvas[row][col]);
          g.fillOval(row, col, this.configurations.getPixelSize(), this.configurations.getPixelSize());
        }
      }
    }
  }

  /**
   * Este metodo implementa o algoritmo DDA de rasterizacao De retas
   *
   * @param int, Coordenada x do ponto de origem
   * @param int, Coordenada y do ponto de origem
   * @param int, Coordenada x do ponto final de selecao do evento
   * @param int, Coordenada y do ponto final de selecao do evento
   */
  private void DDA(int x0, int y0, int x1, int y1) {
    int dx, dy, passos;
    float xIncr, yIncr, x, y;

    dx = x1 - x0;
    dy = y1 - y0;
    if (Math.abs(dx) > Math.abs(dy)) {
      passos = Math.abs(dx);
    } else {
      passos = Math.abs(dy);
    }

    xIncr = dx / (float) passos;
    yIncr = dy / (float) passos;
    x = x0;
    y = y0;
    setPixel(Math.round(x), Math.round(y));
    for (int i = 0; i < passos; i++) {
      x = x + xIncr;
      y = y + yIncr;
      setPixel(Math.round(x), Math.round(y));
    }
  }

  /**
   * Este metodo implementa o algoritmo Bresenham de Rasterizacao de retas
   *
   * @param int, Coordenada x do ponto de origem
   * @param int, Coordenada y do ponto de origem
   * @param int, Coordenada x do ponto final de selecao do evento
   * @param int, Coordenada y do ponto final de selecao do evento
   */
  private void Bresenham(int x0, int y0, int x1, int y1) {
    int dx, dy, x, y, i, const1, const2, p, incrx, incry;
    dx = x1 - x0;
    dy = y1 - y0;
    if (dx >= 0) {
      incrx = 1;
    } else {
      incrx = -1;
      dx = -dx;
    }

    if (dy >= 0) {
      incry = 1;
    } else {
      incry = -1;
      dy = -dy;
    }

    x = x0;
    y = y0;
    setPixel(x, y);

    if (dy < dx) {
      p = 2 * dy - dx;
      const1 = 2 * dy;
      const2 = 2 * (dy - dx);
      for (i = 0; i < dx; i++) {
        x += incrx;
        if (p < 0) {
          p += const1;
        } else {
          y += incry;
          p += const2;
        }
        setPixel(x, y);
      }
    } else {
      p = 2 * dx - dy;
      const1 = 2 * dx;
      const2 = 2 * (dx - dy);
      for (i = 0; i < dy; i++) {
        y += incry;
        if (p < 0) {
          p += const1;
        } else {
          x += incrx;
          p += const2;
        }

        setPixel(x, y);
      }
    }
  }

  /**
   * Este metodo seta os pixels pertencentes a figura da circunferencia
   *
   * @param int, Coordenada x do ponto de origem
   * @param int, Coordenada y do ponto de origem
   * @param int, Coordenada x do ponto final de selecao do evento
   * @param int, Coordenada y do ponto final de selecao do eventoo
   */
  private void plotCirclePoints(int xc, int yc, int x, int y) {
    setPixel(xc + x, yc + y);
    setPixel(xc - x, yc + y);
    setPixel(xc + x, yc - y);
    setPixel(xc - x, yc - y);
    setPixel(xc + y, yc + x);
    setPixel(xc - y, yc + x);
    setPixel(xc + y, yc - x);
    setPixel(xc - y, yc - x);
  }

  /**
   * Este metodo implementa o algoritmo de Brasenhan para Plot de circunferencias
   *
   * @param int, Coordenada x do ponto de origem
   * @param int, Coordenada y do ponto de origem
   * @param int, Raio da circunferencia
   */
  private void cirdBresenham(int xc, int yc, int r) {
    int x = 0, y = r, p = 3 - 2 * r;
    plotCirclePoints(xc, yc, x, y);
    while (x < y) {
      if (p < 0) {
        p = p + 4 * x + 6;
      } else {
        p = p + 4 * (x - y) + 10;
        y--;
      }
      x++;
      plotCirclePoints(xc, yc, x, y);
    }
  }

  /**
   * Este metodo implementa uma modificacao Do algoritmo de Bresenham para o plot
   * de retangulos
   *
   * @param int, Coordenada x do ponto de origem
   * @param int, Coordenada y do ponto de origem
   * @param int, Coordenada x do ponto final de selecao do evento
   * @param int, Coordenada y do ponto final de selecao do evento
   */
  private void rectBresenham(int x0, int y0, int x1, int y1) {
    Bresenham(x0, y0, x1, y0);
    Bresenham(x0, y0, x0, y1);
    Bresenham(x1, y0, x1, y1);
    Bresenham(x0, y1, x1, y1);
  }

  /**
   * Este metodo implementa a selecao de uma regiao retangular do canvas
   * Utilizando uma matriz do tipo Color, delimitada por um ponto de origem, E um
   * de destino.
   *
   * @param int, Coordenada x do ponto de origem
   * @param int, Coordenada y do ponto de origem
   * @param int, Coordenada x do ponto final de selecao do evento
   * @param int, Coordenada y do ponto final de selecao do evento
   */
  private void selectRegion(int x0, int y0, int x1, int y1) {
    int rows = x1 - x0;
    int cols = y1 - y0;
    selectedRegion = new Color[rows + 1][cols + 1];

    for (int i = x0; i <= x1; i++) {
      for (int j = y0; j <= y1; j++) {
        selectedRegion[i - x0][j - y0] = canvas[i][j];
      }
    }

  }

  /**
   * Este metodo implementa a movimentacao de uma regicao retangular selecionada
   * No canvas. Utilizando uma matriz do tipo Color, delimitada por um ponto de
   * origem, E um de destino.
   *
   * @param int, Coordenada x do novo ponto de inicio da regiao selecionada
   * @param int, Coordenada y do novo ponto de inicio da regiao selecionada
   */
  private void moveRegion(int x, int y) {
    for (int i = x; i < x + selectedRegion.length; i++) {
      for (int j = y; j < y + selectedRegion[0].length; j++) {
        if (i >= 0 && j >= 0 && i <= 799 && j <= 599) {
          canvas[i][j] = selectedRegion[i - x][j - y];
        }
      }
    }
  }

  /**
   * Este metodo implementa a limpeza de uma regiao, pre selecionada do canvas.
   *
   * @param [][] Color, Matriz de Cores representando o Canvas da aplicacao
   * @param int, Coordenada x da origem da regiao
   * @param int, Coordenada y da origem da regiao
   * @param int, Coordenada x da origem da regiao + tamanho da regiao selecionada
   * @param int, Coordenada y da origem da regiao + tamanho da regiao selecionada
   */
  private void clearRegion(Color pixelMatrix[][], int x0, int y0, int x1, int y1) {
    for (int i = x0; i < x1; i++) {
      for (int j = y0; j < y1; j++) {
        pixelMatrix[i][j] = null;
      }
    }
  }

  /**
   * Este metodo, implementa a limpeza de um pixel do canvas
   *
   * @param Color, Contendo a cor atual do pixel
   */
  private void clearColor(Color color) {
    for (int i = 0; i < canvas.length; i++) {
      for (int j = 0; j < canvas[0].length; j++) {
        if (canvas[i][j] == color) {
          canvas[i][j] = null;
        }
      }
    }
  }

  /**
   * Este metodo efetua a normalizacao das coordenadas do ponto
   *
   * @param int, Coordenada x do ponto de origem
   * @param int, Coordenada y do ponto de origem
   * @param int, Coordenada x do ponto final de selecao do evento
   * @param int, Coordenada y do ponto final de selecao do evento
   */
  private int[] normalizeCoordinates(int x0, int y0, int x1, int y1) {
    int response[] = { 0, 0, 0, 0 };

    if (x1 > x0) {
      response[0] = x0;
      response[2] = x1;
    } else {
      response[0] = x1;
      response[2] = x0;
    }

    if (y1 > y0) {
      response[1] = y0;
      response[3] = y1;
    } else {
      response[1] = y1;
      response[3] = y0;
    }

    return response;
  }

  /**
   * Este metodo, seta um pixel do Canvas como utilizado ou nao
   *
   * @param int, coordenada x do pixel
   * @param int, coordenada y do pixel
   */
  public void setPixel(int x, int y) {
    if (x >= 0 && x < 800 && y >= 0 && y < 600) {
      this.canvas[x][y] = configurations.getColor();
    }
  }

  /**
   * Este metodo realiza uma clonagem do estado atual do Canvas
   *
   * @param [][] Color, Contendo o estado atual do canvas
   * @return [][]Color, Uma copia do estado atual do canvas
   */
  private Color[][] cloneMatrix(Color[][] matrix) {
    Color copy[][] = new Color[matrix.length][matrix[0].length];

    for (int row = 0; row < copy.length; row++) {
      for (int col = 0; col < copy[0].length; col++) {
        copy[row][col] = matrix[row][col];
      }
    }

    return copy;
  }

  private PaintPnl getInstance() {
    return this;
  }
}
