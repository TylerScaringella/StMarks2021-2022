package me.tyler.editor.shape;

import java.awt.*;

public abstract class Shape {

    protected int x, y, width, height;
    protected Color c;
    protected String type;
    protected boolean created = true;

    public Shape(int x,int y, int w, int h, Color c) {
        this.x = x; this.y = y;
        width = w; height = h;
        this.c = c;
    }

    public Shape(int x, int y, int w, int h, Color c, boolean created) {
        this(x, y, w, h, c);
        this.created = created;
    }

    public void move(int x1, int y1, int x2, int y2) {
        x = x2-x1; y = y2-y1;
    }

    public abstract void draw(Graphics g);
    public abstract boolean isOn(int x, int y);

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}