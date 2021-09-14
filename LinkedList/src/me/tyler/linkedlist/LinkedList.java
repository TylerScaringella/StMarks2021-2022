package me.tyler.linkedlist;

public class LinkedList<E> {

    private class Node {

        private Node next;
        private E data;

        public Node(E data) {
            this.data = data;
        }

    }

    private Node first;

    public void add(E info, int i) {
        Node node = new Node(info);

        if(this.first == null) {
            this.first = node;
        }

        if(i == 0) {
            node.next = this.first;
            this.first = node;
        }

        try {
            Node curr = first;
            for(int x = 0; x < i; x++) {
                curr = curr.next;
            }

            node.next = curr.next;
            curr.next = node;

        } catch(NullPointerException e) {
            throw new IndexOutOfBoundsException();
        }
    }

    public void remove(E e) {

    }

    public void remove(int i) {

    }

    public int size() {
        return 0;
    }
}
