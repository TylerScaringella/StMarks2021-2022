package me.tyler.game.map;

import java.util.Set;

public class Map {

    private final Set<MapTile> tiles;
    private boolean active;

    public Map(Set<MapTile> tiles) {
        this.tiles = tiles;
        this.active = false;
    }

    public MapTile getTile(int row, int col) {
        return this.tiles.stream()
                .filter(tile -> tile.getRow() == row)
                .filter(tile -> tile.getCol() == col)
                .findFirst().orElse(null);
    }

    public Set<MapTile> getTiles() {
        return tiles;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
