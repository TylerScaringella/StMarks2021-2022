package me.tyler.linkedlist.test;

import me.tyler.linkedlist.LinkedList;

public class LinkedTest {

    public static void main(String[] args) {
        LinkedList<String> list = new LinkedList<>();

        for(int i=1; i<=5; i++) {
            list.add("lol " + i);
        }

        System.out.println(list.toString());
        System.out.println(list.size());

        System.out.println("Let's remove the middle of the list to see what happens");
        list.remove(2);
        System.out.println(list.size());

        System.out.println(list.toString());

        System.out.println("Let's see if this other add method works in 2 different ways");
        list.add("trying to do index 4", 4);
        System.out.println(list.toString());
        System.out.println(list.size());

        System.out.println("Let's see if it works if I try to add something in the middle");
        list.add("something in the middle", 2);

        System.out.println(list.toString());
        System.out.println(list.size());
    }
}
