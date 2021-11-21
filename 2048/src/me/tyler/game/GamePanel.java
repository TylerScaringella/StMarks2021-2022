package me.tyler.game;

import me.tyler.game.board.Board;
import me.tyler.game.listener.InputListener;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel {

    private static GamePanel instance;

    private Board board;

    public GamePanel() {
        this.setBackground(Color.DARK_GRAY);
        System.out.println("Game Start:");
        this.board = new Board(this);

        this.requestFocus();
        this.setFocusable(true);
        addKeyListener(new InputListener(this));
    }

    public void setBoard(Board board) {
        System.out.println("Game Reset:");
        this.board = board;
        this.repaint();
    }

    public Board getBoard() {
        return board;
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        board.render(g);
    }
}
