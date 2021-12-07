package me.tyler.maze;

import javax.swing.*;
import java.awt.*;
import java.util.concurrent.ThreadLocalRandom;

public class Maze extends JPanel{

    private boolean[][] board;

    private final int BOARD_ROWS = 20,
                      BOARD_COLS = 20;
    private final int CELL_SIZE = 20;

    public static void main(String[] args) {
        new Maze();
    }

    public Maze() {
        JFrame frame = new JFrame();
        frame.setTitle("Maze");
        frame.setSize(500, 500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        this.board = new boolean[BOARD_ROWS][BOARD_COLS];
//        printBoard();

        frame.add(this);

        frame.setVisible(true);
        generateMaze();
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);

        for(int row=0; row<board.length; row++) {
            for(int col=0; col<board[0].length; col++) {
                g.setColor(Color.BLACK);
                if(board[row][col]) g.setColor(Color.WHITE);

                g.fillRect(col * CELL_SIZE, row * CELL_SIZE, CELL_SIZE, CELL_SIZE);

                g.setColor(Color.GRAY);
                g.drawRect(col * CELL_SIZE, row * CELL_SIZE, CELL_SIZE, CELL_SIZE);
            }
        }
    }

    private void generateMaze() {
        // generate start point on left side (first col)
        // generate end point on right side (last col)

        int startCol = 0;

        int startRow = ThreadLocalRandom.current().nextInt(0, board.length);

//        System.out.println("Start | Row: " + startRow + ", Col: " + startCol);

        board[startRow][startCol] = true;

        boolean built = false;
        int times = 0;

        while(!built) {
            times++;
            boolean path = generatePath(startRow, startCol, true);
            if(path) {
//                printBoard();
                built = true;
            } else {
                this.board = new boolean[BOARD_ROWS][BOARD_COLS];
                board[startRow][startCol] = true;
            }
        }

        this.repaint();
        System.out.println(String.format("Generated a working maze in %s attempts", times));

        // need to check if theres a solution, if not keep generating until there is

        // go in and fill extra paths
//        fillMaze();
    }

    private boolean generatePath(int row, int col, boolean right) {
        // gen random number 1-4 to get direction
        /*
        1 - up
        2 - right
        3 - down
        4 - left
         */

        int nextRow = row;
        int nextCol = right ? col + 1 : col - 1;

        int timesRan = 0;

        while(nextCol < board[0].length && nextCol >= 0 && nextRow < board.length && nextRow >= 0 && (!board[nextRow][nextCol])) {
            timesRan++;
            board[nextRow][nextCol] = true;

            // 80% to go right
            final int RIGHT = 60;
            final int UP = 80;
            final int DOWN = 100;
            // 10% to go up and down
            int direction = ThreadLocalRandom.current().nextInt(0, 100);
            if(direction <= RIGHT) {
                if(right)
                    nextCol+=1;
                else
                    nextCol-=1;
            } else if(direction <= UP) {
                nextRow-=1;
            } else if(direction <= DOWN) {
                nextRow+=1;
            } else {
                System.out.println("something went wrong, " + direction);
            }

//            System.out.println(String.format("Time: %s | loop: %s, %s", timesRan, nextRow, nextCol));
        }
        return nextCol == board[0].length;
    }

    private void fillMaze() {
        for(int i=0; i<25; i++) {
            // generate different random paths
            int row = ThreadLocalRandom.current().nextInt(2, board.length-1);
            int col = ThreadLocalRandom.current().nextInt(2, board[0].length-1);

            if(i > 3)
                generatePath(row, col, false);
            else
                generatePath(row, col, true);
        }
    }

    private void printBoard() {
        for(int row = 0; row<board.length; row++) {
            for(int col = 0; col<board[0].length; col++) {
                System.out.print((board[row][col] ? "x" : "o") + " ");
            }
            System.out.println();
        }
    }
}
