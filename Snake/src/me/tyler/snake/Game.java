package me.tyler.snake;

import javax.swing.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

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

        Runnable runnable = () -> {
            this.onTick();
        };

        ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
        executor.scheduleAtFixedRate(runnable, 0, 50, TimeUnit.MILLISECONDS);
    }

    /*
    Each tick is 1/20 of a second. So 50 milliseconds
     */
    public void onTick() {
        System.out.println("tick");
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
