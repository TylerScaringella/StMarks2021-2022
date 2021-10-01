package me.tyler.snake;

import java.util.ArrayList;
import java.util.List;

public class Snake {

    private final List<SnakeComponent> components;

    public Snake() {
        this.components = new ArrayList<>();
    }

    public class SnakeComponent {
        private int x, y;

        public SnakeComponent(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public int getX() {
            return this.x;
        }

        public int getY() {
            return this.y;
        }
    }
}
