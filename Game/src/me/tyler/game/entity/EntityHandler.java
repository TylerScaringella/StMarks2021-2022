package me.tyler.game.entity;

import me.tyler.game.GamePanel;
import me.tyler.game.entity.impl.player.Player;

import java.util.HashSet;
import java.util.Set;

public class EntityHandler {

    private final GamePanel gamePanel;
    private final Set<Entity> entities;

    public EntityHandler(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
        this.entities = new HashSet<>();

        Player player = new Player();
        player.setRow(8);
        player.setCol(6);
        registerEntity(player);
    }

    public void registerEntity(Entity entity) {
        this.entities.add(entity);
    }

    public Set<Entity> getEntities() {
        return entities;
    }

    public Player getPlayer() {
        return (Player) this.entities.stream().filter(entity -> entity instanceof Player).findFirst().orElse(null);
    }
}
