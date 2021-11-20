package me.tyler.game.entity;

import me.tyler.game.sprite.Sprite;

import java.util.UUID;

public abstract class Entity {

    private final UUID id;
    private final Sprite sprite;
    private int row, col;
    private boolean visible;
    private boolean moving;
    private EntityDirection direction;

    public Entity(Sprite sprite) {
        this.id = UUID.randomUUID();
        this.sprite = sprite;
        this.row = 0;
        this.col = 0;
        this.visible = false;
        this.moving = false;
        this.direction = EntityDirection.UP;
    }

    public abstract void update();

    public UUID getId() {
        return id;
    }

    public Sprite getSprite() {
        return sprite;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public void setCol(int col) {
        this.col = col;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public boolean isMoving() {
        return moving;
    }

    public void setMoving(boolean moving) {
        this.moving = moving;
    }

    public EntityDirection getDirection() {
        return direction;
    }

    public void setDirection(EntityDirection direction) {
        this.direction = direction;
    }
}
