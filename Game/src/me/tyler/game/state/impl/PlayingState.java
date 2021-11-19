package me.tyler.game.state.impl;

import me.tyler.game.GameConstants;
import me.tyler.game.GamePanel;
import me.tyler.game.state.GameState;
import me.tyler.game.tile.TileType;

import java.awt.*;

public class PlayingState extends GameState {

    public PlayingState(GamePanel gamePanel) {
        super(gamePanel);
    }

    @Override
    public void render(Graphics g) {
        int tpr = GameConstants.SCREEN_COLS;
        int tpc = GameConstants.SCREEN_ROWS;

        for(int col=0; col<tpr; col++) {
            for(int row=0; row<tpc; row++) {
                g.drawImage(
                        getGamePanel().getTileHandler().getTile(TileType.PATH).getImage(),
                        col * GameConstants.TILE_SIZE,
                        row * GameConstants.TILE_SIZE,
                        null
                );
            }
        }
    }
}
