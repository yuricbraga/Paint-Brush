package com.paintbrush;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class PaintPnl extends JPanel {
    int x0 = 0,y0 = 0,x = 0,y = 0;
    ArrayList<Point> points;

    public PaintPnl(){
        setBorder(BorderFactory.createLineBorder(Color.black));
        setBackground(Color.WHITE);
        points = new ArrayList<>();

        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                points.add(e.getPoint());
                repaint();
            }
        });

        addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                points.add(e.getPoint());
                repaint();
            }
        });
    }

    public Dimension getPreferredSize(){
        return new Dimension(800,600);
    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);
        g.setColor(Color.RED);
        for(Point i : points){
            //g.drawLine(i.x, i.y, i.x, i.y);
            g.fillOval(i.x, i.y, 5, 5);
        }
    }

}

