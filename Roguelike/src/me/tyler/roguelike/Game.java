package me.tyler.roguelike;

import asciiPanel.AsciiPanel;
import me.tyler.roguelike.screen.Screen;
import me.tyler.roguelike.screen.impl.StartScreen;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.logging.Logger;

public class Game extends JFrame implements KeyListener {

    private static Logger logger;

    private final AsciiPanel terminal;
    private Screen screen;

    public Game() {
        super();
        logger = Logger.getGlobal();
        logger.info("[Engine] Initialized Logger");

        this.terminal = new AsciiPanel();
        add(terminal);
        pack();

        this.screen = new StartScreen();
        addKeyListener(this);
        repaint();
    }

    public void init() {
        this.setSize(
                terminal.getWidth() + 20,
                terminal.getHeight() + 20);
    }

    @Override
    public void repaint() {
        terminal.clear();
        screen.display(terminal);
        super.repaint();
    }

    public static Logger getLogger() {
        return logger;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        screen = screen.respondToInput(e);
        repaint();
    }

    @Override public void keyTyped(KeyEvent e) {}
    @Override public void keyReleased(KeyEvent e) {}
}
