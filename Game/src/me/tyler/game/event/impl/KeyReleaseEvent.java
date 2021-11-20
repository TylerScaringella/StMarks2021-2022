package me.tyler.game.event.impl;

import me.tyler.game.event.Event;

public class KeyReleaseEvent extends Event {

    private final char keyChar;
    private final int keyCode;

    public KeyReleaseEvent(char keyChar, int keyCode) {
        this.keyChar = keyChar;
        this.keyCode = keyCode;
    }

    public char getKeyChar() {
        return keyChar;
    }

    public int getKeyCode() {
        return keyCode;
    }
}
