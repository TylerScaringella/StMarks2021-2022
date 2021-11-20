package me.tyler.game.entity.impl.player;

import me.tyler.game.GameConstants;
import me.tyler.game.GamePanel;
import me.tyler.game.entity.Entity;

public class Player extends Entity {

    public Player() {
        super(GamePanel.get().getSpriteHandler().getSprite(
                GameConstants.PLAYER_SPRITE_ROW,
                GameConstants.PLAYER_SPRITE_COL));

        setVisible(true);
    }

    @Override
    public void update() {
        if(!isMoving()) return;
        switch(getDirection()) {
            case LEFT: {
                setCol(getCol()-1);
                break;
            }
            case RIGHT: {
                setCol(getCol()+1);
                break;
            }
            case UP: {
                setRow(getRow()-1);
                break;
            }
            case DOWN: {
                setRow(getRow()+1);
                break;
            }
        }
    }
}
