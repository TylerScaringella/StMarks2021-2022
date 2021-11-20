package me.tyler.game.listener.system;

import me.tyler.game.GameConstants;
import me.tyler.game.GamePanel;
import me.tyler.game.event.impl.RowClickEvent;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class MouseController implements MouseListener {

    private final GamePanel gamePanel;

    public MouseController(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        int row = e.getY() / GameConstants.TILE_SIZE,
            col = e.getX() / GameConstants.TILE_SIZE;

        this.gamePanel.getEventHandler().handleEvent(new RowClickEvent(row, col));
    }

    @Override public void mousePressed(MouseEvent e) {}
    @Override public void mouseReleased(MouseEvent e) {}
    @Override public void mouseEntered(MouseEvent e) {}
    @Override public void mouseExited(MouseEvent e) {}
}
