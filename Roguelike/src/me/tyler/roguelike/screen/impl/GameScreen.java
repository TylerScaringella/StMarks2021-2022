package me.tyler.roguelike.screen.impl;

import asciiPanel.AsciiPanel;
import me.tyler.roguelike.screen.Screen;
import me.tyler.roguelike.world.World;
import me.tyler.roguelike.world.WorldBuilder;

import java.awt.event.KeyEvent;

public class GameScreen implements Screen {

    private World world;
    private int
            centerX,
            centerY,
            screenWidth,
            screenHeight;

    public GameScreen() {
        this.screenWidth = 80;
        this.screenHeight = 21;
        this.createWorld();
    }

    @Override
    public void display(AsciiPanel terminal) {
        terminal.write("playing.", 1,1);
        terminal.writeCenter("-- press [enter] to win or [escape] to lose", 22);

        displayTiles(terminal, getScrollX(), getScrollY());
        terminal.write('X', centerX - getScrollX(), centerY - getScrollY());
    }

    @Override
    public Screen respondToInput(KeyEvent key) {
        switch(key.getKeyCode()) {
            case KeyEvent.VK_LEFT:
            case KeyEvent.VK_H: scrollBy(-1, 0); break;
            case KeyEvent.VK_RIGHT:
            case KeyEvent.VK_L: scrollBy( 1, 0); break;
            case KeyEvent.VK_UP:
            case KeyEvent.VK_K: scrollBy( 0,-1); break;
            case KeyEvent.VK_DOWN:
            case KeyEvent.VK_J: scrollBy( 0, 1); break;
            case KeyEvent.VK_Y: scrollBy(-1,-1); break;
            case KeyEvent.VK_U: scrollBy( 1,-1); break;
            case KeyEvent.VK_B: scrollBy(-1, 1); break;
            case KeyEvent.VK_N: scrollBy( 1, 1); break;
            case KeyEvent.VK_ENTER: return new WinScreen();
            case KeyEvent.VK_ESCAPE: return new LoseScreen();
        }

        return this;
    }

    private void displayTiles(AsciiPanel terminal, int left, int top) {
        for (int x = 0; x < screenWidth; x++){
            for (int y = 0; y < screenHeight; y++){
                int wx = x + left;
                int wy = y + top;

                terminal.write(world.getGlyph(wx, wy), x, y, world.getColor(wx, wy));
            }
        }
    }

    private void createWorld() {
        this.world = new WorldBuilder(90, 31)
                .makeCaves()
                .build();
    }

    private void scrollBy(int mx, int my){
        centerX = Math.max(0, Math.min(centerX + mx, world.getWidth() - 1));
        centerY = Math.max(0, Math.min(centerY + my, world.getHeight() - 1));
    }

    public int getScrollX() {
        return Math.max(0, Math.min(centerX - screenWidth / 2, world.getWidth() - screenWidth));
    }

    public int getScrollY() {
        return Math.max(0, Math.min(centerY - screenHeight / 2, world.getHeight() - screenHeight));
    }
}
