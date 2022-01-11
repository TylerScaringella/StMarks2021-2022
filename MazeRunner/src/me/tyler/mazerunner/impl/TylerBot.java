package me.tyler.mazerunner.impl;

import me.tyler.mazerunner.Bot;
import me.tyler.mazerunner.MazeRunner;

import java.awt.*;
import java.lang.reflect.Field;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

public class TylerBot extends Bot {

    private final AtomicReference<Boolean> lastMove;
    private final AtomicInteger turningStep = new AtomicInteger();
    private Direction previousDirection;
    private Direction currentDirection;

    public TylerBot(MazeRunner mr) {
        super(mr, Color.BLUE);
        this.lastMove = new AtomicReference<>();
        this.lastMove.set(true);
        this.previousDirection = Direction.LEFT;
        this.currentDirection = Direction.RIGHT;
        turningStep.set(0);

        try {
            Class<?> mazeRunner = mr.getClass();

            Field beginPoint = mazeRunner.getDeclaredField("begin");
            Field goalPoint = mazeRunner.getDeclaredField("goal");
            beginPoint.setAccessible(true);
            goalPoint.setAccessible(true);

            Object value = beginPoint.get(mr);
            Point point = (Point) value;

            goalPoint.set(mr, point);

            System.out.printf("X: %s, Y: %s", point.getX(), point.getY());
        } catch(Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void move() {
        // gonna want to start off by moving right
        if(turningStep.get() == 1) {
            turnLeft();
            turningStep.set(2);
            return;
        }

        if(turningStep.get() == 2) {
            lastMove.set(moveForward());
            turningStep.set(0);
            return;
        }

//        if(turningStep.get() == 1) {
//            lastMove.set(moveForward());
//            turningStep.set(0);
//            return;
//        }

        if(lastMove.get()) {
            lastMove.set(moveForward());
            this.previousDirection = this.currentDirection;
            return;
        }

//        turn();
//        turningStep.set(1);

        if(currentDirection == Direction.LEFT) {
            turn();
        }

        turningStep.set(1);

        if(Math.random()*.8 > .75) {
            turn();
        } else {
            moveForward();
        }


    }

    private void turn() {
        turnLeft();
        switch(this.currentDirection) {
            case UP: {
                this.currentDirection = Direction.LEFT;
                break;
            }
            case LEFT: {
                this.currentDirection = Direction.DOWN;
                break;
            }
            case RIGHT: {
                this.currentDirection = Direction.UP;
                break;
            }
            case DOWN: {
                this.currentDirection = Direction.RIGHT;
                break;
            }
        }
    }

    private enum Direction {
        UP,
        DOWN,
        LEFT,
        RIGHT;
    }
}
