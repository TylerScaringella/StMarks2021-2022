package me.tyler.game.sprite;

import java.awt.image.BufferedImage;

public class Sprite {

    private final int row, col;
    private BufferedImage image;

    public Sprite(int row, int col, BufferedImage image) {
        this.row = row;
        this.col = col;
        this.image = image;
    }

    public BufferedImage getImage() {
        return image;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    @Override
    public String toString() {
        return "Sprite{" + "row=" + row + ", col=" + col + ", image=" + image + '}';
    }
}
