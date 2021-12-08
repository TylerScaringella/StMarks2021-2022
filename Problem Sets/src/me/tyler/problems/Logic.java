package me.tyler.problems;

import java.util.*;
import java.util.stream.Collectors;

public class Logic {

    public static void main(String[] args) {
        new Logic(true);
    }

    public Logic(boolean debug) {
//        problem_1(debug);
        System.out.println(problem_2(debug));
    }

    private void problem_1(boolean debug) {
        String[] directions = new String[]{"North", "South", "South", "East", "West", "North", "West", "South", "South", "North", "West", "East", "East"};
        Map<String, Integer> freqMap = new HashMap<>();

        for(String direction : directions) freqMap.put(direction, freqMap.getOrDefault(direction, 0)+1);

        int northTimes = freqMap.getOrDefault("North", 0);
        int southTimes = freqMap.getOrDefault("South", 0);
        int eastTimes = freqMap.getOrDefault("East", 0);
        int westTimes = freqMap.getOrDefault("West", 0);

        List<String> toReturn = new ArrayList<>();
        if(debug) System.out.println(String.format("N: %s, E: %s, S: %s, W: %s", northTimes, eastTimes, southTimes, westTimes));

        if(northTimes != southTimes) {
            if(northTimes > southTimes) {
                int diff = northTimes - southTimes;
                for(int i=0; i<diff; i++) toReturn.add("North");
            } else {
                int diff = southTimes - northTimes;
                for(int i=0; i<diff; i++) toReturn.add("South");
            }
        }
        if(eastTimes != westTimes) {
            if(eastTimes > westTimes) {
                int diff = eastTimes - westTimes;
                for(int i=0; i<diff; i++) toReturn.add("East");
            } else {
                int diff = westTimes - eastTimes;
                for(int i=0; i<diff; i++) toReturn.add("West");
            }
        }

        System.out.println(toReturn.toString());
    }

    private int problem_2(boolean debug) {
        int input = 578;

        // get it as a list
        List<Integer> inputList = new ArrayList<>();

        for(String num : String.valueOf(input).split("")) {
            inputList.add(Integer.parseInt(num));
        }

        List<Integer> possibilities = new ArrayList<>();

        for(int i=0; i<inputList.size(); i++) {
            Collections.rotate(inputList, 1);
            String numStr = "";
            for(Integer num : inputList) {
                numStr += num;
            }

            possibilities.add(Integer.parseInt(numStr));
        }

        if(debug) System.out.println(String.format("Possibilities: %s", possibilities));

        if(possibilities.size() == 1)
            return -1;

        possibilities = possibilities.stream()
                .filter(n -> n < input)
                .sorted(Comparator.comparingInt(o -> (input - o)))
                .collect(Collectors.toList());

        if(debug) System.out.println(String.format("Less Possibilities: %s", possibilities));

        if(possibilities.size() == 1) {
            return possibilities.get(0);
        }


        return -1;
    }
}
