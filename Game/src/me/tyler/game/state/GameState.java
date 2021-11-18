package me.tyler.game.state;

import me.tyler.game.GamePanel;

import java.awt.*;

public abstract class GameState {

    private final GamePanel gamePanel;

    public GameState(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }

    public GamePanel getGamePanel() {
        return gamePanel;
    }

    public abstract void render(Graphics g);
}
