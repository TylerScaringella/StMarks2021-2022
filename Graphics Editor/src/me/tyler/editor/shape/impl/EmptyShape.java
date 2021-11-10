package me.tyler.editor.shape.impl;

import me.tyler.editor.shape.Shape;

import java.awt.*;

public class EmptyShape extends Shape {
    public EmptyShape(int x, int y, int w, int h, Color c) {
        super(x, y, w, h, c);
    }

    @Override
    public void draw(Graphics g) {

    }

    @Override
    public boolean isOn(int x, int y) {
        return false;
    }
}
