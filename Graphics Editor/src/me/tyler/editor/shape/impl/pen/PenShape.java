package me.tyler.editor.shape.impl.pen;

import me.tyler.editor.shape.Shape;

import java.awt.*;
import java.awt.geom.Path2D;
import java.util.ArrayList;
import java.util.List;

public class PenShape extends Shape {

    private final Point start;
    private final List<PenLocation> pixels;

    public PenShape(Point p, Color c) {
        super((int)p.getX(), (int)p.getY(), 0, 0, c);
        this.start = p;
        this.pixels = new ArrayList<>();
        this.pixels.add(new PenLocation(
                (int)p.getX(),
                (int)p.getY()
        ));
    }

    @Override
    public void draw(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(this.c);
        g2d.setStroke(new BasicStroke(3));

        Path2D path = new Path2D.Double();
        path.moveTo(start.getX(), start.getY());
        for(int i=0; i<this.pixels.size(); i++) {
            if(i > 0) {
                PenLocation prevLocation = this.pixels.get(i - 1);
                path.moveTo(prevLocation.getX(), prevLocation.getY());
            }
            PenLocation curLocation = this.pixels.get(i);
            path.lineTo(curLocation.getX(), curLocation.getY());
        }
        path.closePath();
        g2d.draw(path);
    }

    @Override
    public boolean isOn(int x, int y) {
        return false;
    }

    public List<PenLocation> getPixels() {
        return pixels;
    }
}
