package me.tyler.game.tile;

import me.tyler.game.GameConstants;

public class Tile {
    private int x, y;

    public Tile(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getEndX() {
        return this.x + GameConstants.TILE_SIZE;
    }

    public int getEndY() {
        return this.y + GameConstants.TILE_SIZE;
    }
}
