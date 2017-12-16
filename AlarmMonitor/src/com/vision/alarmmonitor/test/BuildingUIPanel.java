package com.vision.alarmmonitor.test;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class BuildingUIPanel extends JPanel{
    private static final int D_W = 1000;
    private static final int D_H = 650;

    Cube cube;
    public BuildingUIPanel() {
        cube = new Cube(75, 75, 250,400, 50);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        cube.drawCube(g);
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(D_W, D_H);
    }

    public class Cube {
        int x, y, size, shift;
        Point[] cubeOnePoints;
        Point[] cubeTwoPoints;
        int height;
        public Cube(int x, int y, int size,int height, int shift) {
            this.x = x;
            this.y = y;
            this.size = size;
            this.shift = shift;
            this.height=height;
            cubeOnePoints = getCubeOnePoints();
            cubeTwoPoints = getCubeTwoPoints();
            
        }

        private Point[] getCubeOnePoints() {
            Point[] points = new Point[4];
            points[0] = new Point(x, y);
            points[1] = new Point(x + size, y);
            points[2] = new Point(x + size, y + height);
            points[3] = new Point(x, y + height);
            return points;
        }

        private Point[] getCubeTwoPoints() {
            int newX = x + shift;
            int newY = y + shift;
            Point[] points = new Point[4];
            points[0] = new Point(newX, newY);
            points[1] = new Point(newX + size, newY);
            points[2] = new Point(newX + size, newY + height);
            points[3] = new Point(newX, newY + height);
            return points;
        }

        public void drawCube(Graphics g) {
            g.drawRect(x, y, size, height);
            g.drawRect(x + shift, y + shift, size, height);
            // draw connecting lines
            for (int i = 0; i < 4; i++) {
                g.drawLine(cubeOnePoints[i].x, cubeOnePoints[i].y, 
                        cubeTwoPoints[i].x, cubeTwoPoints[i].y);
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                JFrame frame = new JFrame();
                frame.add(new BuildingUIPanel());
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.pack();
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);
            }
        });
    }
}
