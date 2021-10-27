package me.tyler.graphics;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

public class Art extends JFrame {

    public static void main(String[] args) throws IOException {
        new Art();
    }

    public Art() throws IOException {
        this.setSize(600, 600);
        this.setVisible(true);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Image image = Toolkit.getDefaultToolkit().getImage("C:\\Users\\tjsca\\Downloads\\sponsor.jpg");

        JPanel canvas = new JPanel() {
            @Override
            public void paint(Graphics g) {
                super.paint(g);

                background(g);

                chromaColor(g);
                snowball(g, 300, 200, 75, 75);
                snowball(g, 250, 250, 150, 150);
                snowball(g, 200, 300, 250, 250);

                g.setColor(Color.WHITE);
                g.setFont(new Font("Arial", Font.BOLD, 20));
                g.drawString("Sally the Snowman", 275, 200);

                g.setColor(Color.ORANGE);
                g.fillRect(300, 230, 40, 15);

                g.setColor(Color.BLACK);
                g.fillOval(320, 215, 10, 10);
                g.fillOval(345, 215, 10, 10);

                g.setColor(new Color(43, 5, 5));
                g.fillRect(175, 275, 100, 15);
                g.fillRect(375, 275, 100, 15);

                g.drawImage(image, 250, 350, 150, 150, this);
            }
        };

        this.add(canvas);

        long lastChange = System.currentTimeMillis();

        while(true) {
            if(System.currentTimeMillis() - lastChange >= 250) {
                this.repaint();
                lastChange = System.currentTimeMillis();
            }
        }
    }

    private void background(Graphics g) {
        g.setColor(Color.CYAN);
        g.fillRect(0, 0, 600, 600);
    }

    private void snowball(Graphics g, int x, int y, int width, int height) {
//        g.setColor(Color.WHITE);
        g.fillOval(x, y, width, height);
    }

    private void chromaColor(Graphics g) {
        int red = ThreadLocalRandom.current().nextInt(0, 255);
        int green = ThreadLocalRandom.current().nextInt(0, 255);
        int blue = ThreadLocalRandom.current().nextInt(0, 255);

        g.setColor(new Color(red, green, blue));
    }
}
