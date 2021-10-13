package me.tyler.snake;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class Game extends JFrame {

    private GameState gameState;
    private final JPanel panel;
    private Snake snake;
    private boolean ticking;
    private long lastAppleSpawn = System.currentTimeMillis() - TimeUnit.SECONDS.toMillis(15);
    private int score, appleSeconds = 10;
    private List<Apple> apples;

    public Game() {
        this.setTitle("Snake");
        this.setResizable(false);
        this.setSize(Constants.WIDTH, Constants.HEIGHT);
        this.setVisible(true);
        this.setVisible(true);

        this.panel = new JPanel();
        add(this.panel);

        addKeyListener(new SnakeAdapter());

        this.apples = new ArrayList<>();

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

        for(Snake.SnakeComponent component : this.snake.getComponents()) {
            for(Apple apple : this.apples) {
                if(component.getX() >= apple.getX() && component.getX() <= (apple.getX() + Constants.TILE_WIDTH)) {
                    if(component.getY() >= apple.getY() && component.getY() <= (apple.getY() + Constants.TILE_WIDTH)) {
                        this.apples.remove(apple);
                        this.snake.addToSnake();

                        this.lastAppleSpawn = (System.currentTimeMillis() - TimeUnit.SECONDS.toMillis(15));
                        this.score++;
                    }
                }
            }
        }
    }

    private void drawGameOver(Graphics g) {
        this.ticking = false;
    }

    private void appleSpawn(Graphics g) {
        g.setColor(Color.RED);
        this.apples.forEach(apple -> g.fillRect(
                apple.getX(),
                apple.getY(),
                Constants.TILE_WIDTH,
                Constants.TILE_WIDTH
        ));
        // Spawn one every 15 seconds
        if((System.currentTimeMillis() - this.lastAppleSpawn) <= TimeUnit.SECONDS.toMillis(appleSeconds)) return;
        this.lastAppleSpawn = System.currentTimeMillis();

        ThreadLocalRandom random = ThreadLocalRandom.current();
        int x = random.nextInt(0, Constants.WIDTH);
        int y = random.nextInt(0, Constants.HEIGHT);

        this.apples.add(new Apple(x, y));
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
                appleSpawn(g);
                // detect collision
                detectCollision();

                g.setColor(Color.WHITE);
                g.drawString("PUMPKIN", 10, 10);
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

    private class Apple {
        private int x, y;
        private long spawned;

        public Apple(int x, int y) {
            this.x = x;
            this.y = y;
            this.spawned = System.currentTimeMillis();
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
    }
}
