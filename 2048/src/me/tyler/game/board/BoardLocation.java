package me.tyler.game.board;

public class BoardLocation {

    private final int row;
    private final int col;

    public BoardLocation(int row, int col) {
        this.row = row;
        this.col = col;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }
}
