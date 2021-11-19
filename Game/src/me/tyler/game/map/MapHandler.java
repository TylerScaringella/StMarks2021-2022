package me.tyler.game.map;

import me.tyler.game.GamePanel;

import java.util.ArrayList;
import java.util.List;

public class MapHandler {

    private final GamePanel gamePanel;
    private final List<Map> maps;

    public MapHandler(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
        this.maps = new ArrayList<>();
    }
}
