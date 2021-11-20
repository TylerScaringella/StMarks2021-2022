package me.tyler.game.tile;

import me.tyler.game.sprite.SpriteHandler;

import java.util.HashMap;
import java.util.Map;

public class TileHandler {

    private final SpriteHandler spriteHandler;
    private final Map<TileType, Tile> tiles;

    public TileHandler(SpriteHandler spriteHandler) {
        this.spriteHandler = spriteHandler;
        this.tiles = new HashMap<>();
        loadTiles();
        System.out.println(String.format("Loaded %s tiles", tiles.size()));
    }

    private void loadTiles() {
        for(TileType tileType : TileType.values()) {
            tiles.put(tileType, new Tile(tileType, spriteHandler.getSprite(tileType.getRow(), tileType.getCol())));
        }
    }

    public Tile getTile(TileType type) {
        return tiles.getOrDefault(type, null);
    }

    public Tile getTile(char id) {
        TileType type = null;

        for(TileType tileType : TileType.values()) {
            if(tileType.getId() == id) {
                type = tileType;
                break;
            }
        }
        return tiles.getOrDefault(type, null);
    }
}
