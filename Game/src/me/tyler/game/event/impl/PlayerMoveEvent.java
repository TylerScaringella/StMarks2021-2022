package me.tyler.game.event.impl;

import me.tyler.game.entity.impl.player.Player;
import me.tyler.game.event.Event;

public class PlayerMoveEvent extends Event {

    private final Player player;
    private final int prevRow, prevCol;
    private final int row, col;

    public PlayerMoveEvent(Player player, int prevRow, int prevCol, int row, int col) {
        this.player = player;
        this.prevRow = prevRow;
        this.prevCol = prevCol;
        this.row = row;
        this.col = col;
    }

    public Player getPlayer() {
        return player;
    }

    public int getPrevCol() {
        return prevCol;
    }

    public int getPrevRow() {
        return prevRow;
    }

    public int getCol() {
        return col;
    }

    public int getRow() {
        return row;
    }
}
