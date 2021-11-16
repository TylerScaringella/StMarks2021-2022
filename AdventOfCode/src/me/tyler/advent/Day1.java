package me.tyler.advent;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Day1 {

    public static void main(String[] args) throws IOException {
        new Day1();
    }

    public Day1() throws IOException {
        List<Integer> numbers = new ArrayList<>();

        FileReader fileReader = new FileReader("C:\\Users\\tjsca\\Documents\\cs\\AdventOfCode\\day1.txt");
        BufferedReader reader = new BufferedReader(fileReader);
        String line;
        while((line = reader.readLine()) != null) {
            numbers.add(Integer.parseInt(line));
        }
        reader.close();

        // Part 1
        numbers.forEach(num -> {
            for(int i=0; i<numbers.size(); i++) {
                int curNum = numbers.get(i);
                if(num + curNum == 2020) {
                    System.out.println(num + " + " + curNum + " = 2020 | " + (num * curNum));
                }
            }
        });

        // Part 2
        numbers.forEach(num -> {
            numbers.forEach(num2 -> {
                numbers.forEach(num3 -> {
                    if(num + num2 + num3 == 2020) {
                        System.out.println(num * num2 * num3);
                    }
                });
            });
        });
    }
}
