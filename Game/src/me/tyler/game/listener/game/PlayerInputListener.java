package me.tyler.game.listener.game;

import me.tyler.game.GamePanel;
import me.tyler.game.entity.EntityDirection;
import me.tyler.game.event.GameEvent;
import me.tyler.game.event.Listener;
import me.tyler.game.event.impl.KeyPressEvent;
import me.tyler.game.event.impl.KeyReleaseEvent;
import me.tyler.game.event.impl.RowClickEvent;

public class PlayerInputListener implements Listener {

    private final GamePanel gamePanel;

    public PlayerInputListener() {
        this.gamePanel = GamePanel.get();
    }

    @GameEvent
    public void onRowClick(RowClickEvent event) {
        System.out.println("Row: " + event.getRow() + ", Col: " + event.getCol());
    }

    @GameEvent
    public void onKeyPress(KeyPressEvent event) {
        switch(event.getKeyChar()) {
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

    @GameEvent
    public void onKeyRelease(KeyReleaseEvent event) {
        this.gamePanel.getEntityHandler().getPlayer().setMoving(false);
    }
}
