package me.tyler.editor.shape.impl;

import me.tyler.editor.shape.Shape;

import java.awt.*;
import java.awt.image.BufferedImage;

public class ImageShape extends Shape {

    private final BufferedImage image;

    public ImageShape(int x, int y, int width, int height, BufferedImage image) {
        super(x, y, width, height, null);
        this.image = image;
    }
    @Override
    public void draw(Graphics g) {
        g.drawImage(this.image, this.x, this.y, this.width, this.height, null);
    }

    @Override
    public boolean isOn(int x, int y) {
        return x >= this.x && x <= (this.x + this.width) && y >= this.y && y <= (this.y + this.height);
    }
}
