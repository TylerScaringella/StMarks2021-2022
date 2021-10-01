package me.tyler.snake;

import javax.swing.*;

public class Game {

    private final JFrame jFrame;
    private GameState gameState;

    public static void main(String[] args) {
        new Game();
    }

    public Game() {
        this.jFrame = new JFrame("Snake");
        this.jFrame.setSize(Constants.WIDTH, Constants.HEIGHT);
        this.jFrame.setResizable(false);
        this.jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.jFrame.setVisible(true);

        this.gameState = GameState.RUNNING;
    }

    public GameState getGameState() {
        return this.gameState;
    }

    public void setGameState(GameState gameState) {
        this.gameState = gameState;
    }

    private enum GameState {
        RUNNING,
        STOPPED
    }
}
