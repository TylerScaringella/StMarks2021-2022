package me.tyler.game.listener.system;

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
        switch(e.getKeyChar()) {
            case 'w': {
                this.gamePanel.getEntityHandler().getPlayer().setDirection(EntityDirection.UP);
                this.gamePanel.getEntityHandler().getPlayer().setMoving(true);
                break;
            }
            case 'a': {
                this.gamePanel.getEntityHandler().getPlayer().setDirection(EntityDirection.LEFT);
                this.gamePanel.getEntityHandler().getPlayer().setMoving(true);
                break;
            }
            case 's': {
                this.gamePanel.getEntityHandler().getPlayer().setDirection(EntityDirection.DOWN);
                this.gamePanel.getEntityHandler().getPlayer().setMoving(true);
                break;
            }
            case 'd': {
                this.gamePanel.getEntityHandler().getPlayer().setDirection(EntityDirection.RIGHT);
                this.gamePanel.getEntityHandler().getPlayer().setMoving(true);
                break;
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        this.gamePanel.getEntityHandler().getPlayer().setMoving(false);
    }
}
