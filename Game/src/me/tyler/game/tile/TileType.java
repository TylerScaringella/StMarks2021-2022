package me.tyler.game.tile;

public enum TileType {
    DOOR_CLOSE(0, 4),
    DOOR_OPEN_1(0, 5),
    DOOR_OPEN_2(0, 6),
    DOOR_OPEN(0, 7),

    CELL_CLOSE(1, 4),
    CELL_OPEN_1(1, 5),
    CELL_OPEN_2(1, 6),
    CELL_OPEN(1, 7),

    CHEST_CLOSE(2, 4),
    CHEST_OPEN_1(2, 5),
    CHEST_OPEN_3(2, 6),
    CHEST_OPEN_4(2, 7),

    POT_FULL(3, 4),
    POT_CRACK(3, 5),
    POT_BROKEN(3, 6),
    POT_PIECES(3, 7),

    TREE_1(1, 8),
    TREE_2(1, 9),
    TREE_3(2, 8),
    TREE_4(2, 9),

    STONE_1(0, 8),
    STONE_2(0, 9),
    STONE_3(0, 10),
    STONE_4(0, 11),

    BRICK_1('b', 0, 12),
    BRICK_2(0, 13),
    BRICK_3(0, 14),
    BRICK_4(0, 15),

    YELLOW_LEVER_LEFT(1, 0),
    YELLOW_LEVER(1, 1),
    YELLOW_LEVER_RIGHT(1, 2),

    BLUE_LEVER_LEFT(2, 0),
    BLUE_LEVER(2, 1),
    BLUE_LEVER_RIGHT(2, 2),

    RED_LEVER_LEFT(3, 0),
    RED_LEVER(3, 1),
    RED_LEVER_RIGHT(3, 2),

    GREEN_LEVER_LEFT(4, 0),
    GREEN_LEVER(4, 1),
    GREEN_LEVER_RIGHT(4, 2),

    ORDER_TOP(1, 3),
    ORDER_BOTTOM(2, 2),

    BED_TOP(3, 3),
    BED_BOTTOM(4, 3),

    FLOORING_SLANTED(4, 4),
    FLOORING_VERTICAL(4, 5),

    MOUNTAIN_1(4, 6),
    MOUNTAIN_2(4, 7),

    GRASS_1('g', 1, 14),
    GRASS_2(1, 15),
    GRASS_3(2, 14),
    GRASS_4(2, 15),

    BRUSH_SMALL_1(1, 10),
    BRUSH_SMALL_2(1, 11),
    BRUSH_SMALL_3(2, 10),
    BRUSH_SMALL_4(2, 11),

    BRUSH_LARGE_1(1, 12),
    BRUSH_LARGE_2(1, 13),
    BRUSH_LARGE_3(2, 12),
    BRUSH_LARGE_4(2, 13),

    PATH('p', 4, 11),

    FURNACE_1(7, 2),
    FURNACE_2(7, 1),
    FURNACE_3(7, 0),
    FURNACE_4(6, 2),
    FURNACE_5(6, 1),
    FURNACE_6(6, 0),
    FURNACE_7(5, 2),
    FURNACE_8(5, 1),
    FURNACE_9(5, 0),

    SOLID_RED(6, 14),
    BLUE_CRACK(8, 14),
    WATER(7, 14),

    CARPET_1(6, 15),
    CARPET_2(6, 16),
    CARPET_3(7, 15),
    CARPET_4(7, 16);

    private int row, col;
    private char id;

    TileType(int row, int col) {
        this.row = row;
        this.col = col;
    }

    TileType(char id, int row, int col) {
        this.id = id;
        this.row = row;
        this.col = col;
    }

    public char getId() {
        return id;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }
}
