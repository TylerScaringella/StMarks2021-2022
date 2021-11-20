package me.tyler.game.map;

import me.tyler.game.GamePanel;

import java.util.ArrayList;
import java.util.List;

public class MapHandler {

    private final GamePanel gamePanel;
    private final MapGenerator mapGenerator;
    private final List<Map> maps;

    public MapHandler(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
        this.mapGenerator = new MapGenerator(gamePanel);
        this.maps = new ArrayList<>();

        Map generatedMap = this.mapGenerator.generateMap(3);
        generatedMap.setActive(true);
        this.maps.add(generatedMap);
//        loadMap();
    }

    public void loadMap() {
        Map loadedMap = this.mapGenerator.readMap("C:\\Users\\tjsca\\Documents\\cs\\Game\\map.txt");
        if(loadedMap == null) return;

        loadedMap.setActive(true);
        this.maps.add(loadedMap);
    }

    public Map getActiveMap() {
        return this.maps.stream().filter(Map::isActive).findFirst().orElse(null);
    }
}
