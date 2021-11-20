package me.tyler.game.listener;

import me.tyler.game.GamePanel;
import me.tyler.game.entity.EntityDirection;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class PlayerController implements KeyListener {

    private final GamePanel gamePanel;

    public PlayerController(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }

    @Override public void keyTyped(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e) {
        this.gamePanel.getEntityHandler().getPlayer().setMoving(true);
        switch(e.getKeyChar()) {
            case 'w': {
                this.gamePanel.getEntityHandler().getPlayer().setDirection(EntityDirection.UP);
                break;
            }
            case 'a': {
                this.gamePanel.getEntityHandler().getPlayer().setDirection(EntityDirection.LEFT);
                break;
            }
            case 's': {
                this.gamePanel.getEntityHandler().getPlayer().setDirection(EntityDirection.DOWN);
                break;
            }
            case 'd': {
                this.gamePanel.getEntityHandler().getPlayer().setDirection(EntityDirection.RIGHT);
                break;
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        this.gamePanel.getEntityHandler().getPlayer().setMoving(false);
    }
}
