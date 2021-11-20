package me.tyler.game.listener.game;

import me.tyler.game.event.GameEvent;
import me.tyler.game.event.Listener;
import me.tyler.game.event.impl.RowClickEvent;

public class RowClickListener implements Listener {

    @GameEvent
    public void onRowClick(RowClickEvent event) {
        System.out.println("Row: " + event.getRow() + ", Col: " + event.getCol());
    }
}
