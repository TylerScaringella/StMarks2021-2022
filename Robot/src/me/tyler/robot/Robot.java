package me.tyler.robot;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.concurrent.ThreadLocalRandom;

public class Robot extends JFrame {

    private RobotBlock robot;

    private final int MOVE_SPEED = 10,
                      RECT_SIZE  = 75;

    public static void main(String[] args) {
        new Robot();
    }

    public Robot() {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.setSize(500, 500);

        this.robot = new RobotBlock();

        JPanel canvas = new JPanel() {
            @Override
            public void paint(Graphics g) {
                super.paint(g);

                g.setColor(robot.getColor());
                g.fillRect(robot.getX(), robot.getY(), RECT_SIZE, RECT_SIZE);
            }
        };

        canvas.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                robot.setX(e.getX());
                robot.setY(e.getY());

                repaint();
            }

            @Override public void mousePressed(MouseEvent e) {}
            @Override public void mouseReleased(MouseEvent e) {}
            @Override public void mouseEntered(MouseEvent e) {}
            @Override public void mouseExited(MouseEvent e) {}
        });

        canvas.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
                switch(e.getKeyChar()) {
                    case 'w': {
                        robot.setY(robot.getY()-MOVE_SPEED);
                        repaint();
                        break;
                    }
                    case 'd': {
                        robot.setX(robot.getX()+MOVE_SPEED);
                        repaint();
                        break;
                    }
                    case 's': {
                        robot.setY(robot.getY()+MOVE_SPEED);
                        repaint();
                        break;
                    }
                    case 'a': {
                        robot.setX(robot.getX()-MOVE_SPEED);
                        repaint();
                        break;
                    }
                }
            }

            @Override public void keyPressed(KeyEvent e) {}
            @Override public void keyReleased(KeyEvent e) {}
        });

        this.add(canvas);
        canvas.requestFocus();
    }

    private class RobotBlock {
        private int x, y;

        public RobotBlock() {
            this.x = 250;
            this.y = 250;
        }

        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }

        public void setX(int x) {
            this.x = x;
        }

        public void setY(int y) {
            this.y = y;
        }

        public Color getColor() {
            int r = ThreadLocalRandom.current().nextInt(0, 255);
            int g = ThreadLocalRandom.current().nextInt(0, 255);
            int b = ThreadLocalRandom.current().nextInt(0, 255);

            return new Color(r, g, b);
        }
    }
}
