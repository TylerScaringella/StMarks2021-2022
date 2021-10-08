package me.tyler.snake;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Optional;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class Game extends JFrame {

    private GameState gameState;
    private final JPanel panel;
    private Snake snake;
    private boolean ticking;
    private int score;

    public Game() {
        this.setTitle("Snake");
        this.setResizable(false);
        this.setSize(Constants.WIDTH, Constants.HEIGHT);
        this.setVisible(true);
        this.setVisible(true);

        this.panel = new JPanel();
        add(this.panel);

        addKeyListener(new SnakeAdapter());

        this.snake = new Snake();
        for(int i=0; i<4; i++) {
            this.snake.addToSnake();
        }

        this.gameState = GameState.RUNNING;

        startTicking();
    }

    public static void main(String[] args) {
        new Game();
    }

    private void startTicking() {
        this.ticking = true;
        Runnable runnable = this::onTick;

        ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
        executor.scheduleAtFixedRate(runnable, 0, 50, TimeUnit.MILLISECONDS);
    }

    /*
    Each tick is 1/20 of a second. So 50 milliseconds
     */
    public void onTick() {
        this.repaint();
    }

    private void drawBackground(Graphics g) {
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, Constants.WIDTH, Constants.HEIGHT);
    }

    private void drawSnake(Graphics g) {
        // draw snake
        this.snake.getComponents().forEach(component -> {
            g.setColor(Color.GREEN);
            g.fillRect(
                    component.getX(),
                    component.getY(),
                    Constants.TILE_WIDTH,
                    Constants.TILE_WIDTH
            );
        });

        // move snake
        this.snake.moveSnake();
    }

    private void detectCollision() {
        Optional<Snake.SnakeComponent> colliding = this.snake.getComponents().stream().filter(component -> component.getX() <= 0 || component.getX() >= Constants.WIDTH || component.getY() <= 0 || component.getY() >= Constants.HEIGHT).findAny();
        if(colliding.isPresent()) {
            System.out.println("Collision detected!");
            setGameState(GameState.STOPPED);
        }
    }

    private void drawGameOver(Graphics g) {
        this.ticking = false;
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        switch (this.gameState) {
            case RUNNING: {
                // draw background tiles
                drawBackground(g);
                // update snake movement
                drawSnake(g);
                // spawn apples
                // detect collision
                detectCollision();
                break;
            }
            case STOPPED: {
                if(ticking)
                    System.out.println("Game Stopped");
                    drawGameOver(g);
                break;
            }
            default: {
                System.out.println("Unexpected error occurred.");
            }
        }
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

    private class SnakeAdapter extends KeyAdapter {

        @Override
        public void keyPressed(KeyEvent e) {
            int key = e.getKeyCode();

            switch(key) {
                case KeyEvent.VK_LEFT: {
                    snake.setDirection(Snake.SnakeDirection.LEFT);
                    break;
                }
                case KeyEvent.VK_RIGHT: {
                    snake.setDirection(Snake.SnakeDirection.RIGHT);
                    break;
                }
                case KeyEvent.VK_UP: {
                    snake.setDirection(Snake.SnakeDirection.UP);
                    break;
                }
                case KeyEvent.VK_DOWN: {
                    snake.setDirection(Snake.SnakeDirection.DOWN);
                    break;
                }
            }
        }
    }
}
