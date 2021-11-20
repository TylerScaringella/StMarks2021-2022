package me.tyler.game.entity.impl.player;

import me.tyler.game.GameConstants;
import me.tyler.game.GamePanel;
import me.tyler.game.entity.Entity;
import me.tyler.game.entity.EntityDirection;
import me.tyler.game.sprite.Sprite;

import java.util.HashMap;
import java.util.Map;

public class Player extends Entity {

    private final Map<EntityDirection, Sprite> sprites;

    public Player() {
        super(GamePanel.get().getSpriteHandler().getSprite(
                8,
                0));

        this.sprites = new HashMap<>();
        loadSprites();
        setVisible(true);
    }

    private void loadSprites() {
        // col 0 row 8 - DOWN
        this.sprites.put(EntityDirection.DOWN, GamePanel.get().getSpriteHandler().getSprite(
                8,
                0
        ));
        // col 0 row 9 - LEFT
        this.sprites.put(EntityDirection.LEFT, GamePanel.get().getSpriteHandler().getSprite(
                9,
                0
        ));
        // col 0 row 10 - RIGHT
        this.sprites.put(EntityDirection.RIGHT, GamePanel.get().getSpriteHandler().getSprite(
                10,
                0
        ));
        // col 0 row 11 - UP
        this.sprites.put(EntityDirection.UP, GamePanel.get().getSpriteHandler().getSprite(
                11,
                0
        ));
    }

    @Override
    public void update() {
        if(!isMoving()) return;
        switch(getDirection()) {
            case LEFT: {
                setSprite(this.sprites.get(EntityDirection.LEFT));
                setCol(getCol()-1);
                break;
            }
            case RIGHT: {
                setSprite(this.sprites.get(EntityDirection.RIGHT));
                setCol(getCol()+1);
                break;
            }
            case UP: {
                setSprite(this.sprites.get(EntityDirection.UP));
                setRow(getRow()-1);
                break;
            }
            case DOWN: {
                setSprite(this.sprites.get(EntityDirection.DOWN));
                setRow(getRow()+1);
                break;
            }
        }
    }
}
