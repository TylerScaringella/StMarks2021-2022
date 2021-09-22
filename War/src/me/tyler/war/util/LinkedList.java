package me.tyler.war.util;

public class LinkedList<E> {

    private class Node {

        Node next;
        E data;

        public Node(E data) {
            this.data = data;
        }

    }

    private Node first;
    private int size;

    public void add(E info) {
        this.add(info, this.size());
    }

    public void add(E info, int i) {
        Node node = new Node(info);

        if(this.first == null) {
            this.first = node;
            this.size++;
            return;
        }

        if(i == 0) {
            node.next = this.first;
            this.first = node;
            this.size++;
            return;
        }

        try {
            Node prev = this.getNode(i-1);
            if (i < this.size) {
                node.next = prev.next;
            }
            prev.next = node;
            this.size++;
        } catch(NullPointerException e) {
            throw new IndexOutOfBoundsException();
        }
    }

    private Node getNode(int index) {
        if(index == 0) return this.first;

        Node curr = this.first;
        for(int i = 0; i < size; i++) {
            if(i == index) {
                return curr;
            }
            curr = curr.next;
        }

        return curr;
    }

    public E get(int index) {
        try{
            Node curr = this.first;
            for(int i = 0; i < index; i++) {
                curr = curr.next;
            }

            return curr.data;
        }catch(NullPointerException e) {
            throw new IndexOutOfBoundsException();
        }
    }

    public E remove(int i) {
        if(this.size == 1) {
            Node first = this.first;
            this.first = null;
            this.size--;
            return first.data;
        }

        if(i == 0) {
            Node first = this.first;
            this.first = this.first.next;
            this.size--;
            return first.data;
        }

        Node previous = this.getNode(i-1);
//        System.out.println("previous " + previous.data);
        Node removal = previous.next;
//        System.out.println("removal " + removal.data);

        if(previous == null) throw new IndexOutOfBoundsException();
        if(previous.next != null) previous.next = previous.next.next;

        this.size--;
        return removal.data;
    }

    public int size() {
        return this.size;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        for(int i=0; i < this.size; i++) {
            sb.append(i + ". " + this.getNode(i).data + "\n");
        }

        return sb.toString();
    }
}
