package me.tyler.game;

import javax.swing.*;

public class Game {

    private final GamePanel gamePanel;

    public static void main(String[] args) {
        new Game();
    }

    public Game() {
        this.gamePanel = new GamePanel();
        JFrame frame = new JFrame();
        frame.setSize(530, 555);
        frame.setTitle("2048");
        frame.setLocationRelativeTo(null);
        frame.add(this.gamePanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
