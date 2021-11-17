package me.tyler.game;

import javax.swing.*;
import java.awt.*;

public class Game {

    private final GamePanel gamePanel;

    public static void main(String[] args) {
        new Game();
    }

    public Game() {
        this.gamePanel = new GamePanel();
        JFrame frame = new JFrame();
        frame.setSize(Toolkit.getDefaultToolkit().getScreenSize());
        frame.setTitle("Game");
        frame.add(this.gamePanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
