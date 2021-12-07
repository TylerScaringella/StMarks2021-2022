package me.tyler.josephus;

import java.util.ArrayList;
import java.util.List;

public class Josephus {

    private final int ARMY_SIZE = 1000;

    public static void main(String[] args) {
        new Josephus();
    }

    public Josephus() {
        List<Person> people = new ArrayList<>();

        for(int i=0; i<ARMY_SIZE; i++) {
            people.add(new Person(i));
        }

        int n = 1;
        while(people.size() > 1) {
            people.remove(n);
            n = (n + 1) % people.size();
        }

        System.out.println(String.format("Position %d lives", people.get(0).getPosition()));
    }

    private class Person {
        private final int position;
        private boolean alive;

        public Person(int position) {
            this.position = position;
            this.alive = true;
        }

        public void setAlive(boolean alive) {
            this.alive = alive;
        }

        public boolean isAlive() {
            return alive;
        }

        public int getPosition() {
            return position;
        }
    }
}
