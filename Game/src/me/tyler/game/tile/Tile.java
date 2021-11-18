package me.tyler.game.tile;

import me.tyler.game.sprite.Sprite;

import java.awt.image.BufferedImage;

public class Tile {

    private final TileType type;
    private final Sprite sprite;

    public Tile(TileType type, Sprite sprite) {
        this.type = type;
        this.sprite = sprite;
    }

    public TileType getType() {
        return type;
    }

    public Sprite getSprite() {
        return sprite;
    }

    public BufferedImage getImage() {
        return sprite.getImage();
    }
}
