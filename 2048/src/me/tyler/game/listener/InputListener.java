package me.tyler.game.listener;

import me.tyler.game.GamePanel;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class InputListener implements KeyListener {

    private final GamePanel gamePanel;

    public InputListener(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }

    @Override public void keyTyped(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e) {
        this.gamePanel.getBoard().handleKey(e.getKeyCode());
    }

    @Override public void keyReleased(KeyEvent e) {}
}
