package me.tyler.game;

import me.tyler.game.sprite.SpriteHandler;
import me.tyler.game.tile.TileHandler;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel {

    private final GameLoop gameLoop;

    private final TileHandler tileHandler;
    private final SpriteHandler spriteHandler;

    public GamePanel() {
        this.gameLoop = new GameLoop();
        this.spriteHandler = new SpriteHandler(this);
        this.tileHandler = new TileHandler(this);
    }

    public void update() {
    }

    public void render() {
        this.repaint();
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
    }

    private class GameLoop extends Thread {

        private long lastUpdate;
        private long lastFrame;
        private long frames;

        @Override
        public void run() {
            while(true) {
                if(System.currentTimeMillis() - lastFrame >= 1000) {
                    frames = 0;
                }
                frames++;
            }
        }
    }

}
