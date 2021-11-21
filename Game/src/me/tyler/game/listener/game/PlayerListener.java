package me.tyler.game.listener.game;

import me.tyler.game.GamePanel;
import me.tyler.game.entity.impl.npc.NPC;
import me.tyler.game.event.GameEvent;
import me.tyler.game.event.Listener;
import me.tyler.game.event.impl.PlayerMoveEvent;

public class PlayerListener implements Listener {

    private final GamePanel gamePanel;

    public PlayerListener() {
        this.gamePanel = GamePanel.get();
    }

    @GameEvent
    public void onPlayerMove(PlayerMoveEvent event) {
        // get entities at current location
        final NPC npcAtLocation = this.gamePanel.getEntityHandler().getEntities().stream()
                .filter(entity -> entity.getRow() == event.getRow())
                .filter(entity -> entity.getCol() == event.getCol())
                .filter(entity -> entity instanceof NPC)
                .map(entity -> (NPC) entity)
                .findFirst().orElse(null);

        if(npcAtLocation == null) return;
        // show npc menu | add to npcs or something
    }
}
