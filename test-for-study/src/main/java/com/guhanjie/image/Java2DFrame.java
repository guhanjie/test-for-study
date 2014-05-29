package com.guhanjie.image;

import java.awt.Graphics;
import java.awt.Graphics2D;
/**
 * Displays a JFrame and draws text on it using the Java 2D Graphics API
 * @author cn.outofmemory
 */
public class Java2DFrame extends javax.swing.JFrame {

    /**
     * Creates a new instance of Java2DFrame
     */
    public Java2DFrame() {
        initComponents();
    }

    /**
     * This is the method where the String is drawn.
     *
     * @param g The graphics object
     */
    public void paint(Graphics g) {
        Graphics2D graphics = (Graphics2D) g;
        graphics.drawString("Using Java 2D API to draw a String", 20, 100);
    }

    // <editor-fold defaultstate="collapsed" desc=" Generated Code "> 
    private void initComponents() {

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
//        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(getContentPane());
//        getContentPane().setLayout(layout);
//        layout.setHorizontalGroup(
//            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
//            .add(0, 400, Short.MAX_VALUE)
//        );
//        layout.setVerticalGroup(
//            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
//            .add(0, 300, Short.MAX_VALUE)
//        );
        pack();
    }// </editor-fold> 

    /**
     * Starts the program
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Java2DFrame().setVisible(true);
            }
        });
    }

}