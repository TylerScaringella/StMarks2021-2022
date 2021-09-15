package me.tyler.linkedlist.test;

import me.tyler.linkedlist.LinkedList;

public class LinkedTest {

    public static void main(String[] args) {
        LinkedList<String> list = new LinkedList<>();

        list.add("Testing", 0);
        list.add("Testing2", 1);
        list.add("Testing3");

        System.out.println(list.toString());
    }
}
