package me.tyler.roguelike.screen;

import asciiPanel.AsciiPanel;

import java.awt.event.KeyEvent;

public interface Screen {
    void display(AsciiPanel terminal);
    Screen respondToInput(KeyEvent key);
}
