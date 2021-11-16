package me.tyler.advent;

import me.tyler.advent.util.AdventReader;

import java.io.IOException;
import java.util.List;

public class Day3 {

    public static void main(String[] args) throws IOException {
        new Day3();
    }

    public Day3() throws IOException {
        final List<String> input = new AdventReader("day3").getLines();
        final int slopeHeight = input.size(), slopeWidth = input.get(0).length();

        final int xChange = 1,
                  yChange = 2;

        int curRow = 0, curCol = 0, trees = 0;
        while(curRow < slopeHeight-1) {
            curRow+=yChange;
            curCol+=xChange;
            if(curCol >= slopeWidth) curCol = curCol - slopeWidth;

            String spot = String.valueOf(input.get(curRow).charAt(curCol));
            if(spot.equals("#")) trees++;
        }
        System.out.println("Hit Trees: " + trees);
    }
}
