package com.paintbrush;

import javax.swing.*;

public class App {
    private JPanel MainPnl;
    private JPanel paintPnl;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(App::createAndShowGUI);

    }

    private static void createAndShowGUI(){
        System.out.println("Created GUI on EDT? " + SwingUtilities.isEventDispatchThread());
        JFrame f = new JFrame("App");
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.add(new App().MainPnl);
        f.pack();
        f.setVisible(true);
    }

    private void createUIComponents() {
        paintPnl = new PaintPnl();
    }
}


