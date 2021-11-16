package me.tyler.editor.shape.impl;

import me.tyler.editor.shape.Shape;

import java.awt.*;
import java.awt.geom.Path2D;

public class LineShape extends Shape {

    private int endX, endY;

    public LineShape(int x, int y, int endX, int endY, Color c) {
        super(x, y, 0, 0, c);
        this.endX = endX;
        this.endY = endY;
    }

    @Override
    public void draw(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(this.c);
        g2d.setStroke(new BasicStroke(3));

        Path2D path = new Path2D.Double();
        path.moveTo(this.x, this.y);
        path.lineTo(this.endX, this.endY);
        path.closePath();
        g2d.draw(path);
    }

    @Override
    public boolean isOn(int x, int y) {
        return false;
    }

    public void setEndX(int endX) {
        this.endX = endX;
    }

    public void setEndY(int endY) {
        this.endY = endY;
    }
}
