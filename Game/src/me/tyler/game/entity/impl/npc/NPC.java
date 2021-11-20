package me.tyler.game.entity.impl.npc;

import me.tyler.game.entity.Entity;
import me.tyler.game.sprite.Sprite;

import java.util.ArrayList;
import java.util.List;

public class NPC extends Entity {

    private List<String> text;
    private boolean speaking;
    private String nameplate;

    public NPC(Sprite sprite) {
        super(sprite);
        this.text = new ArrayList<>();
        this.speaking = false;

        setVisible(true);
    }

    public NPC(Sprite sprite, String nameplate) {
        super(sprite);
        this.nameplate = nameplate;

        setVisible(true);
    }

    @Override
    public void update() {

    }

    public boolean isNear(Entity entity) {
        return false;
    }

    public String getNameplate() {
        return nameplate;
    }

    public boolean isNameplate() {
        return nameplate.length() > 0;
    }

    public List<String> getText() {
        return text;
    }
}
