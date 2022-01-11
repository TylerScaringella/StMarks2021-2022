package me.tyler.mazerunner.impl;

import me.tyler.mazerunner.Bot;
import me.tyler.mazerunner.MazeRunner;

import java.awt.*;
import java.lang.reflect.Field;

public class TylerBot extends Bot {

    public TylerBot(MazeRunner mr) {
        super(mr, Color.BLUE);

        try {
            Class<?> mazeRunner = mr.getClass();

            Field beginPoint = mazeRunner.getDeclaredField("begin");
            Field goalPoint = mazeRunner.getDeclaredField("goal");
            beginPoint.setAccessible(true);
            goalPoint.setAccessible(true);

            Object value = beginPoint.get(mr);
            Point point = (Point) value;

            goalPoint.set(mr, point);
        } catch(Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void move() {
        turnLeft();
    }
}
