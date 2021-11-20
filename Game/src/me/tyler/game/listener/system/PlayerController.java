package me.tyler.game.listener.system;

import me.tyler.game.GamePanel;
import me.tyler.game.event.impl.KeyPressEvent;
import me.tyler.game.event.impl.KeyReleaseEvent;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class PlayerController implements KeyListener {

    private final GamePanel gamePanel;

    public PlayerController(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        this.gamePanel.getEventHandler().handleEvent(new KeyPressEvent(e.getKeyChar(), e.getKeyCode()));
    }

    @Override
    public void keyReleased(KeyEvent e) {
        this.gamePanel.getEventHandler().handleEvent(new KeyReleaseEvent(e.getKeyChar(), e.getKeyCode()));
    }
}
