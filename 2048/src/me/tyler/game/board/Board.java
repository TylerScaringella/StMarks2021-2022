package me.tyler.game.board;

import me.tyler.game.GamePanel;

import java.awt.*;
import java.util.HashMap;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicBoolean;

public class Board {

    private final GamePanel gamePanel;
    private final HashMap<BoardLocation, Tile> tiles;
    private boolean won = false;
    private boolean lost = false;

    public Board(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
        this.tiles = new HashMap<>();

        setupBoard();

    }

    private void setupBoard() {
        // Generate the 4x4 Board
        for(int x=1; x<=4; x++) {
            for(int y=1; y<=4; y++) {
                this.tiles.put(new BoardLocation(y, x), new Tile(0));
            }
        }

        // Put 2 tiles of 2 in random spots
        for(int i=0; i<2; i++) {
            BoardLocation location = generateLocation();
            while(getTile(location.getRow(), location.getCol()).getValue() != 0) {
                location = generateLocation();
            }
            System.out.println(location.getRow() + " " + location.getCol());
            getTile(location.getRow(), location.getCol()).setValue(2);
        }
    }

    public BoardLocation generateLocation() {
        int row = ThreadLocalRandom.current().nextInt(1, 5);
        int col = ThreadLocalRandom.current().nextInt(1, 5);
        BoardLocation location = new BoardLocation(row, col);
        return location;
    }

    public void generateTile() {
        int chance = ThreadLocalRandom.current().nextInt(1, 101);
        int value = 2;

        // 5% chance for value of 4
        if(chance >= 95) value = 4;

        BoardLocation location = generateLocation();
        while(getTile(location.getRow(), location.getCol()).getValue() != 0) {
            location = generateLocation();
        }
        getTile(location.getRow(), location.getCol()).setValue(value);
    }

    public Tile getTile(int row, int col) {
        BoardLocation location = this.tiles.keySet().stream()
                .filter(loc -> loc.getRow() == row)
                .filter(loc -> loc.getCol() == col)
                .findFirst().orElse(null);
        if(location == null) return null;
        return this.tiles.getOrDefault(location, null);
    }

    public void handleKey(int keyCode) {
        MoveDirection direction = null;
        switch(keyCode) {
            case 38:
            case 87: {
                direction = MoveDirection.UP;
                break;
            }
            case 39:
            case 68: {
                direction = MoveDirection.RIGHT;
                break;
            }
            case 40:
            case 83: {
                direction = MoveDirection.DOWN;
                break;
            }
            case 37:
            case 65: {
                direction = MoveDirection.LEFT;
                break;
            }
        }

        if(direction == null) return;
        if(handleMove(direction))
            generateTile();
        won = checkForWin();
        lost = checkForLost();

        this.gamePanel.repaint();
    }

    public boolean checkForWin() {
        return tiles.values().stream().anyMatch(tile -> tile.getValue() == 2048);
    }

    public boolean checkForLost() {
        boolean filled = tiles.values().stream().filter(tile -> tile.getValue() != 0).count() == 16;
        if(!filled) return false;

        AtomicBoolean canMove = new AtomicBoolean(false);

        tiles.forEach((location, tile) -> {
            // above
            if(location.getRow() != 1) {
                canMove.set(getTile(location.getRow() - 1, location.getCol()).getValue() == tile.getValue());
            }

            // below
            if(location.getRow() != 4) {
                canMove.set(getTile(location.getRow() + 1, location.getCol()).getValue() == tile.getValue());
            }

            // left
            if(location.getCol() != 1) {
                canMove.set(getTile(location.getRow(), location.getCol() - 1).getValue() == tile.getValue());
            }

            // right
            if(location.getCol() != 4) {
                canMove.set(getTile(location.getRow(), location.getCol() + 1).getValue() == tile.getValue());
            }
        });

        return !canMove.get();
    }

    public boolean handleMove(MoveDirection direction) {
        AtomicBoolean didMove = new AtomicBoolean(false);

        tiles.forEach((location, tile) -> {
            if(tile.getValue() == 0) return;
            int nextRow = location.getRow();
            int nextCol = location.getCol();
            switch(direction) {
                case LEFT: {
                    if(location.getCol() == 1) return;
                    nextCol-=1;
                    break;
                }
                case RIGHT: {
                    if(location.getCol() == 4) return;
                    nextCol+=1;
                    break;
                }
                case UP: {
                    if(location.getRow() == 1) return;
                    nextRow-=1;
                    break;
                }
                case DOWN: {
                    if(location.getRow() == 4) return;
                    nextRow+=1;
                    break;
                }
            }

//            System.out.println(String.format("Cur Row: %s, Cur Col: %s, Next Row: %s, Next Col: %s", location.getRow(), location.getCol(), nextRow, nextCol));
            Tile nextTile = getTile(nextRow, nextCol);

            if(nextTile.getValue() == tile.getValue()) {
                nextTile.setValue(tile.getValue()*2);
                tile.setValue(0);
                didMove.set(true);
            }

            if(nextTile.getValue() == 0) {
                nextTile.setValue(tile.getValue());
                tile.setValue(0);
                handleMove(direction);
                didMove.set(true);
            }
        });

        return didMove.get();
    }

    public void render(Graphics g) {
        g.setColor(Color.GRAY);

        // Draw Tiles
        for(int row=0; row<4; row++) {
            for(int col=0; col<4; col++) {
                g.drawRect(col * 128, row * 128, 128, 128);
            }
        }

        this.tiles.forEach((location, tile) -> {
            if(tile.getValue() < 2) return;
            g.setColor(getColor(tile.getValue()));
            g.fillRect((location.getCol()-1) * 128, (location.getRow()-1) * 128, 128, 128);
            g.setColor(Color.WHITE);
            g.setFont(new Font("Arial", Font.BOLD, 20));
            g.drawString(String.valueOf(tile.getValue()), (location.getCol()-1)*128, (location.getRow()-1)*128+15);
        });

        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 50));

        if(won) g.drawString("You won!", 100, 250);
        if(lost) g.drawString("Game Over!", 100, 250);
    }

    public Color getColor(int value) {
        switch(value) {
            case 2: return Color.BLACK;
            case 4: return Color.BLUE;
            case 8: return Color.GREEN;
            case 16: return Color.YELLOW;
            case 32: return Color.ORANGE;
            case 64: return Color.RED;
            case 128: return Color.CYAN;
            case 256: return Color.MAGENTA;
            case 512: return Color.PINK;
            case 1028: return Color.DARK_GRAY;
            case 2048: return Color.WHITE;
            default: {
                return Color.WHITE;
            }
        }
    }
}
