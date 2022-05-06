package me.tyler.roguelike.screen.impl;

import asciiPanel.AsciiPanel;
import me.tyler.roguelike.screen.Screen;

import java.awt.event.KeyEvent;

public class LoseScreen implements Screen {

    @Override
    public void display(AsciiPanel terminal) {
        terminal.write("you lost.", 1, 1);
        terminal.writeCenter("-- press [enter] to restart --", 22);
    }

    @Override
    public Screen respondToInput(KeyEvent key) {
        return key.getKeyCode() == KeyEvent.VK_ENTER ? new GameScreen() : this;
    }

}
