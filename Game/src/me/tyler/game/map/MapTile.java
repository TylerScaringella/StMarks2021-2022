package me.tyler.game.map;

import me.tyler.game.tile.Tile;

public class MapTile {

    private final int row, col;
    private Tile tile;

    public MapTile(int row, int col, Tile tile) {
        this.row = row;
        this.col = col;
        this.tile = tile;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public Tile getTile() {
        return tile;
    }

    public void setTile(Tile tile) {
        this.tile = tile;
    }
}
