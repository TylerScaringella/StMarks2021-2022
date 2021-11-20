package me.tyler.game.map;

import me.tyler.game.GameConstants;
import me.tyler.game.GamePanel;
import me.tyler.game.tile.Tile;
import me.tyler.game.tile.TileType;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

public class MapGenerator {

    private final GamePanel gamePanel;

    public MapGenerator(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }

    public Map generateMap(int rooms) {
        final Map baseMap = generatePlainMap();

        final Tile interiorTile = this.gamePanel.getTileHandler().getTile(TileType.PATH);
        final Tile wallTile = this.gamePanel.getTileHandler().getTile(TileType.STONE_1);

        // pick 3 random locations within the map
        for(int i=0; i<rooms; i++) {
            final int row = ThreadLocalRandom.current().nextInt(0, GameConstants.SCREEN_ROWS);
            final int col = ThreadLocalRandom.current().nextInt(0, GameConstants.SCREEN_COLS);
            baseMap.getTile(row, col).setTile(interiorTile);

            // 5x5 radius | set border to walls
            for(int wallRow=row-2; wallRow<row+2 && wallRow<GameConstants.SCREEN_ROWS && wallRow>=0; wallRow++) {
                for(int wallCol=col-2; wallCol<col+2 && wallCol<GameConstants.SCREEN_COLS && wallCol>=0; wallCol++) {
                    baseMap.getTile(wallRow, wallCol).setTile(wallTile);
                }
            }

            // 3x3 radius | set interior to tiles
            for(int wallRow=row-1; wallRow<row+1 && wallRow<GameConstants.SCREEN_ROWS && wallRow>=0; wallRow++) {
                for(int wallCol=col-1; wallCol<col+1 && wallCol<GameConstants.SCREEN_COLS && wallCol>=0; wallCol++) {
                    baseMap.getTile(wallRow, wallCol).setTile(interiorTile);
                }
            }
        }
        // get the size of the room

        return baseMap;
    }

    public Map generatePlainMap() {
        final Tile baseTile = this.gamePanel.getTileHandler().getTile(TileType.GRASS_1);
        final Set<MapTile> tiles = new HashSet<>();

        for(int row=0; row<GameConstants.SCREEN_ROWS; row++) {
            for(int col=0; col<GameConstants.SCREEN_COLS; col++) {
                tiles.add(new MapTile(
                        row,
                        col,
                        baseTile
                ));
            }
        }

        return new Map(tiles);
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
