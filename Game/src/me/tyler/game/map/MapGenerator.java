package me.tyler.game.map;

import me.tyler.game.GameConstants;
import me.tyler.game.GamePanel;
import me.tyler.game.tile.TileType;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class MapGenerator {

    private final GamePanel gamePanel;

    public MapGenerator(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }

    public void writeBaseMap() {
        try {
            FileWriter writer = new FileWriter("C:\\Users\\tjsca\\Documents\\cs\\Game\\map.txt");
            for(int row=0; row<GameConstants.SCREEN_ROWS; row++) {
                for(int col=0; col<GameConstants.SCREEN_COLS; col++) {
                    writer.write(TileType.GRASS_1.getId());
                }
                writer.write("\n");
            }

            writer.close();
        } catch(IOException ex) {
            ex.printStackTrace();
        }
    }

    public Map readMap(String location) {
        final Set<MapTile> tiles = new HashSet<>();

        try {
            FileReader fileReader = new FileReader(location);
            BufferedReader reader = new BufferedReader(fileReader);

            String line;
            int row = 0;
            while((line = reader.readLine()) != null) {
                int col = 0;
                char[] lineChars = line.toCharArray();

                for(char curChar : lineChars) {
                    tiles.add(new MapTile(
                            row,
                            col,
                            this.gamePanel.getTileHandler().getTile(curChar)));

                    col++;
                }

                row++;
            }

        } catch(IOException ex) {
            ex.printStackTrace();
        }

        return new Map(tiles);
    }
}
