package me.tyler.game;

import me.tyler.game.board.Board;
import me.tyler.game.listener.InputListener;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel {

    private static GamePanel instance;

    private final Board board;

    public GamePanel() {
        this.setBackground(Color.DARK_GRAY);
        this.board = new Board(this);

        this.requestFocus();
        this.setFocusable(true);
        addKeyListener(new InputListener(this));
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
