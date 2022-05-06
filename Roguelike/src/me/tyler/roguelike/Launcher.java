package me.tyler.roguelike;

import javax.swing.*;

public class Launcher {

    public static void main(String[] args) {
        final Game game = new Game();
        Game.getLogger().info("[Launcher] Starting Game");
        game.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        game.setVisible(true);
        game.init();
    }
}
