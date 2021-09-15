package me.tyler.linkedlist;

public class LinkedList<E> {

    private int size;

    private class Node {

        private Node next;
        private E data;

        public Node(E data) {
            this.data = data;
        }

    }

    private Node first;

    public void add(E info) {
        this.add(info, this.size());
    }

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

            this.size++;

        } catch(NullPointerException e) {
            throw new IndexOutOfBoundsException();
        }
    }

    public Node get(int index) {
        Node curr = this.first;
        for(int i = 0; i <= index; i++) {
            curr = curr.next;
        }

        return curr;
    }

    public void remove(int i) {
        Node prevRemoval = null, curr = this.first;
        for(int x = 0; x < i; x++) {
            curr = curr.next;

            if(x == (i-1)) {
                    prevRemoval = curr;
            }
        }

        if(prevRemoval == null) throw new ArrayIndexOutOfBoundsException();

        prevRemoval.next = prevRemoval.next.next;
        this.size--;
    }

    public int size() {
        return this.size;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        for(int i=0; i<this.size; i++) {
            sb.append(this.get(i).data + ", ");
        }

        return sb.toString();
    }
}
