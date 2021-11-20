package me.tyler.game.event.impl;

import me.tyler.game.event.Event;

public class RowClickEvent extends Event {

    private final int row;
    private final int col;

    public RowClickEvent(int row, int col) {
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
