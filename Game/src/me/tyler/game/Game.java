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
        frame.setSize(GameConstants.SCREEN_COLS * GameConstants.TILE_SIZE, GameConstants.SCREEN_ROWS * GameConstants.TILE_SIZE);
        frame.setTitle("Game");
        frame.setLocationRelativeTo(null);
        frame.add(this.gamePanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
