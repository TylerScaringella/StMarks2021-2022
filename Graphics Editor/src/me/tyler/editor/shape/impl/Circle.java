package me.tyler.editor.shape.impl;

import me.tyler.editor.shape.Shape;

import java.awt.*;

public class Circle extends Shape {

    protected int centerX, centerY, radius;

    /**
     *
     * @param x - X of center
     * @param y - Y of center
     * @param r - Radius
     * @param c - Color
     */
    public Circle(int x, int y, int r, Color c) {
        super(x - r, y - r, r*2, r*2, c);
        this.centerX = x;
        this.centerY = y;
        this.radius = r;
    }

    @Override
    public Shape copy() {
        return null;
    }

    @Override
    public void draw(Graphics g) {
        g.setColor(this.c);
        g.fillOval(this.x, this.y, this.width, this.height);
    }

    @Override
    public boolean isOn(int x, int y) {
        // distance from center | need to compare that to the radius
        double distance = Math.sqrt(Math.pow((this.centerY - y), 2) + Math.pow((this.centerX - x), 2));
        return distance <= this.radius;
    }

    @Override
    public void resize(int x1, int y1, int x2, int y2) {

    }
}
