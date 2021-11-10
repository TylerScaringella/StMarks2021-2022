package me.tyler.editor.shape.impl;

import me.tyler.editor.shape.Shape;

import java.awt.*;

public class Rectangle extends Shape {

    public Rectangle(int x, int y, int w, int h, Color c) {
        super(x, y, w, h, c);
    }

    public Rectangle(int x, int y, int w, int h, Color c, boolean created) {
        this(x, y, w, h, c);
        this.created = created;
    }

    @Override
    public void draw(Graphics g) {
        g.setColor(this.c);
        if(this.created) {
            g.fillRect(this.x, this.y, this.width, this.height);
        } else {
            g.drawRect(this.x, this.y, this.width, this.height);
        }
    }

    @Override
    public boolean isOn(int x, int y) {
        return x >= this.x && x <= (this.x + this.width) && y >= this.y && y <= (this.y + this.height);
    }
}
