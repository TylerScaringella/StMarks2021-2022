package me.tyler.test;

import me.tyler.util.PriorityQueue;

public class PriorityTest {

    public static void main(String[] args) {
        PriorityQueue<String> testing = new PriorityQueue<>();
        System.out.println(testing.toString());
        System.out.println(testing.size());

        testing.add("testing", 1);
        System.out.println(testing.toString());
        System.out.println(testing.size());

        testing.add("abc", 2);
        testing.add("bas", 5);
        testing.add("123", 3);

        System.out.println(testing.toString());

        testing.add("asdjflkasjdf", 1);
        System.out.println(testing.toString());
        System.out.println(testing.size());

        int originalSize = testing.size();
        for(int i=0; i<originalSize; i++) {
            System.out.println("Removal #" + i + " | " + testing.pop());
        }
    }
}
