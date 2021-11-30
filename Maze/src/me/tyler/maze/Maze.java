package me.tyler.maze;

import javax.swing.*;
import java.awt.*;

public class Maze extends JPanel{

    private final boolean[][] board;

    public static void main(String[] args) {
        new Maze();
    }

    public Maze() {
        JFrame frame = new JFrame();
        frame.setTitle("Maze");
        frame.setSize(25*16, 25*16);

        this.board = new boolean[25][25];
        generateMaze();

        frame.add(this);

        frame.setVisible(true);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);


    }

    private void generateMaze() {

    }
}
