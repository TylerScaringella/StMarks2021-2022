package me.tyler.snake;

import java.util.ArrayList;
import java.util.List;

public class Snake {

    private final List<SnakeComponent> components;
    private SnakeDirection direction;

    public Snake() {
        this.components = new ArrayList<>();
        // we need to create one component in the middle of the screen
        SnakeComponent head = new SnakeComponent(
                Constants.HEIGHT / 2,
                Constants.WIDTH / 2
        );

        head.setHead(true);
        this.components.add(head);

        this.direction = SnakeDirection.UP;
    }

    public List<SnakeComponent> getComponents() {
        return this.components;
    }

    public SnakeDirection getDirection() {
        return direction;
    }

    public void setDirection(SnakeDirection direction) {
        this.direction = direction;
    }

    public void moveSnake() {
        SnakeComponent component = this.components.get(0);
        switch(this.direction) {
            case LEFT: {
                component.setX((component.getX() - (Constants.TILE_WIDTH / 4)));
                break;
            }
            case RIGHT: {
                component.setX((component.getX() + (Constants.TILE_WIDTH / 4)));
                break;
            }
            case UP: {
                component.setY((component.getY() - (Constants.TILE_WIDTH / 4)));
                break;
            }
            case DOWN: {
                component.setY((component.getY() + (Constants.TILE_WIDTH / 4)));
                break;
            }
        }

        for(int i=this.components.size()-1; i>0; i--) {
            SnakeComponent changing = this.components.get(i);
            SnakeComponent previous = this.components.get(i-1);

            changing.setX(previous.getX());
            changing.setY(previous.getY());
        }
    }

    public void addToSnake() {
        int curSize = this.components.size();
        SnakeComponent lastComponent = this.components.get((curSize - 1));

        int x = 0, y = 0;

        switch (this.direction) {
            case LEFT: {
                // add to the right
                y = lastComponent.getY();
                x = (lastComponent.getX() + Constants.TILE_WIDTH);
                break;
            }
            case RIGHT: {
                // add to the left
                y = lastComponent.getY();
                x = (lastComponent.getX() - Constants.TILE_WIDTH);
                break;
            }
            case UP: {
                // add to the bottom
                y = (lastComponent.getY() - Constants.TILE_WIDTH);
                x = lastComponent.getX();
                break;
            }
            case DOWN: {
                // add to the top
                y = (lastComponent.getY() + Constants.TILE_WIDTH);
                x = lastComponent.getX();
                break;
            }
            default: {
                System.out.println("Failed to add to snake.");
            }
        }

        this.components.add(new SnakeComponent(
                x,
                y
        ));
    }

    public enum SnakeDirection {
        LEFT,
        RIGHT,
        UP,
        DOWN
    }

    public class SnakeComponent {
        private int x, y;
        private boolean head;

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

        public void setX(int x) {
            this.x = x;
        }

        public void setY(int y) {
            this.y = y;
        }

        public void setHead(boolean head) {
            this.head = head;
        }

        public boolean isHead() {
            return head;
        }
    }
}
