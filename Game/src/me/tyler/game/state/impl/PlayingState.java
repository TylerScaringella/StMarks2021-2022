package me.tyler.game.state.impl;

import me.tyler.game.GameConstants;
import me.tyler.game.GamePanel;
import me.tyler.game.entity.Entity;
import me.tyler.game.entity.impl.npc.NPC;
import me.tyler.game.event.GameEvent;
import me.tyler.game.map.Map;
import me.tyler.game.map.MapTile;
import me.tyler.game.state.GameState;
import me.tyler.game.tile.TileType;

import java.awt.*;

public class PlayingState extends GameState {

    public PlayingState(GamePanel gamePanel) {
        super(gamePanel);
    }

    @Override
    public void render(Graphics g) {
        // Render Map
        Map activeMap = getGamePanel().getMapHandler().getActiveMap();
        if(activeMap == null) return;

        activeMap.getTiles().forEach(tile -> g.drawImage(
                tile.getTile().getSprite().getImage(),
                tile.getCol() * GameConstants.TILE_SIZE,
                tile.getRow() * GameConstants.TILE_SIZE,
                null
        ));

        // Render Entities
        getGamePanel().getEntityHandler().getEntities().stream()
                .filter(Entity::isVisible)
                .forEach(entity -> {
                    g.drawImage(
                            entity.getSprite().getImage(),
                            entity.getCol() * GameConstants.TILE_SIZE,
                            entity.getRow() * GameConstants.TILE_SIZE,
                            null);

                    if(entity instanceof NPC) {
                        NPC npc = (NPC) entity;
                        if(!npc.isNameplate()) return;
                        g.setColor(Color.WHITE);
                        g.setFont(new Font("Arial", Font.BOLD, 15));
                        g.drawString(npc.getNameplate(),
                                entity.getCol() * GameConstants.TILE_SIZE,
                                entity.getRow() * GameConstants.TILE_SIZE);
                    }
                });
    }
}
