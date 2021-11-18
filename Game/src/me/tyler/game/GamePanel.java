package me.tyler.game;

import me.tyler.game.sprite.SpriteHandler;
import me.tyler.game.state.GameState;
import me.tyler.game.state.impl.PlayingState;
import me.tyler.game.tile.TileHandler;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel {

    private final GameLoop gameLoop;

    private final TileHandler tileHandler;
    private final SpriteHandler spriteHandler;

    private GameState gameState;

    public GamePanel() {

        this.spriteHandler = new SpriteHandler(this);
        this.tileHandler = new TileHandler(this.spriteHandler);

        this.gameState = new PlayingState(this);

        this.gameLoop = new GameLoop();
        this.gameLoop.start();
    }

    public void update() {
    }

    public void render() {
        this.repaint();
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        this.gameState.render(g);
    }

    public TileHandler getTileHandler() {
        return tileHandler;
    }

    public SpriteHandler getSpriteHandler() {
        return spriteHandler;
    }

    public GameState getGameState() {
        return gameState;
    }

    private class GameLoop extends Thread {

        private final int UPS = 60;
        private final int FPS = 120;
        private final boolean RENDER_TIME = true;

        @Override
        public void run() {
            long initialTime = System.nanoTime();
            final double timeU = 1000000000 / UPS;
            final double timeF = 1000000000 / FPS;
            double deltaU = 0, deltaF = 0;
            int frames = 0, ticks = 0;
            long timer = System.currentTimeMillis();

            while (true) {

                long currentTime = System.nanoTime();
                deltaU += (currentTime - initialTime) / timeU;
                deltaF += (currentTime - initialTime) / timeF;
                initialTime = currentTime;

                if (deltaU >= 1) {
                    update();
                    ticks++;
                    deltaU--;
                }

                if (deltaF >= 1) {
                    render();
                    frames++;
                    deltaF--;
                }

                if (System.currentTimeMillis() - timer > 1000) {
                    if (RENDER_TIME) {
                        System.out.println(String.format("UPS: %s, FPS: %s", ticks, frames));
                    }
                    frames = 0;
                    ticks = 0;
                    timer += 1000;
                }
            }
        }
    }

}
