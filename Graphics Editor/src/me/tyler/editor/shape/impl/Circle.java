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

    public Circle(int x, int y, int r, Color c, boolean created) {
        this(x, y, r, c);
        this.created = created;
    }

    @Override
    public void draw(Graphics g) {
        g.setColor(this.c);
        if(created) {
            g.fillOval(this.x, this.y, this.width, this.height);
        } else {
            g.drawOval(this.x, this.y, this.width, this.height);
        }
    }

    @Override
    public boolean isOn(int x, int y) {
        // distance from center | need to compare that to the radius
        double distance = Math.sqrt(Math.pow((getCenterY() - y), 2) + Math.pow((getCenterX() - x), 2));
        return distance <= this.radius;
    }

    public int getCenterX() {
        return this.x + this.radius;
    }

    public int getCenterY() {
        return this.y + this.radius;
    }
}
