package me.tyler.roguelike.world;

import me.tyler.roguelike.world.tile.Tile;

import java.awt.*;

public class World {
    private Tile[][] tiles;
    private int width, height;

    public World(Tile[][] tiles) {
        this.tiles = tiles;
        this.width = tiles.length;
        this.height = tiles[0].length;
    }

    public Tile getTile(int x, int y) {
        if (x < 0 || x >= width || y < 0 || y >= height)
            return Tile.BOUNDS;
        else
            return tiles[x][y];
    }

    public char getGlyph(int x, int y) {
        return getTile(x, y).glyph();
    }

    public Color getColor(int x, int y) {
        return getTile(x, y).color();
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}
