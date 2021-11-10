package me.tyler.editor.shape.impl;

import me.tyler.editor.shape.Shape;

import java.awt.*;

public class TextShape extends Shape {

    private String text;
    private int fontSize;

    public TextShape(String text, int fontSize, int x, int y, Color c) {
        super(x, y, 0, 0, c);
        this.text = text;
        this.fontSize = fontSize;
    }

    @Override
    public void draw(Graphics g) {
        g.setColor(c);
        g.setFont(new Font("Arial", Font.BOLD, this.fontSize));
        g.drawString(this.text, this.x, this.y);

        this.width = g.getFontMetrics().stringWidth(this.text);
        this.height = g.getFontMetrics().getHeight();
    }

    @Override
    public boolean isOn(int x, int y) {
        System.out.println(this.width);
        return x >= this.x && x <= (this.x + this.width) && y <= this.y && y >= (this.y - this.height);
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
