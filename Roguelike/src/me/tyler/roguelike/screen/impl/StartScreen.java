package me.tyler.roguelike.screen.impl;

import asciiPanel.AsciiPanel;
import me.tyler.roguelike.screen.Screen;

import java.awt.event.KeyEvent;

public class StartScreen implements Screen {

    @Override
    public void display(AsciiPanel terminal) {
        terminal.write("roguelike", 1, 1);
        terminal.writeCenter("-- press [enter] to start --", 22);
    }

    @Override
    public Screen respondToInput(KeyEvent key) {
        return key.getKeyCode() == KeyEvent.VK_ENTER ? new GameScreen() : this;
    }

}
