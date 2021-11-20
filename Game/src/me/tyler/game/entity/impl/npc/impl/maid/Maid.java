package me.tyler.game.entity.impl.npc.impl.maid;

import me.tyler.game.GamePanel;
import me.tyler.game.entity.impl.npc.NPC;

public class Maid extends NPC {

    public Maid() {
        super(GamePanel.get().getSpriteHandler().getSprite(
                10,
                10
        ), "Maid");

        setRow(1);
        setCol(11);
    }
}
