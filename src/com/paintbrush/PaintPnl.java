package com.paintbrush;

import javax.swing.*;
import java.awt.*;

public class PaintPnl extends JPanel {
    public PaintPnl(){
        setBorder(BorderFactory.createLineBorder(Color.black));
    }

    public Dimension getPreferredSize(){
        return new Dimension(250,200);
    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);

        // Draw text
        g.drawString("this is my custom painel!", 10, 20);
    }
}
