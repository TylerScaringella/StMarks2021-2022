package me.tyler.test;

import me.tyler.util.PriorityQueue;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

public class TreeTest {

    public static void main(String[] args) {
        new TreeTest();
    }

    public TreeTest() {
        PriorityQueue<String> priorityQueue = new PriorityQueue<>();

        // add 50 different random strings with different priorities ranging from 0-50

        for(int i=0; i<25; i++) {
            int rand = ThreadLocalRandom.current().nextInt(0, 30);
            priorityQueue.add("this is string #" + i, rand);
        }

        // print strings
        System.out.println(priorityQueue.toString());
        System.out.println(priorityQueue.size());

        // take in groups of twos
        PriorityQueue<Branch> binaryTree = new PriorityQueue<>();

        while(priorityQueue.size() > 1) {
            PriorityQueue.PriorityData left = priorityQueue.popData();
            PriorityQueue.PriorityData right = priorityQueue.popData();
            Branch branch = new Branch(left, right);
            binaryTree.add(branch, branch.getPriority());
        }

        PriorityQueue<Branch> binaryTree2 = new PriorityQueue<>();

        while(binaryTree.size() > 1) {
            PriorityQueue.PriorityData left = binaryTree.popData();
            PriorityQueue.PriorityData right = binaryTree.popData();
            Branch branch = new Branch(left, right);
            binaryTree2.add(branch, branch.getPriority());
        }

        System.out.println(binaryTree2.size());

    }

    public enum BranchEnum {
        LEFT(0),
        RIGHT(1);

        private int num;

        BranchEnum(int num) {
            this.num = num;
        }

        public int getNum() {
            return this.num;
        }
    }

    public class BranchData {
        private BranchEnum value;
        private Object data;

        public BranchData(BranchEnum value, Object data) {
            this.value = value;
            this.data = data;
        }

        public int getValue() {
            return this.value.getNum();
        }

        public Object getData() {
            return this.data;
        }
    }

    private class Branch {

        private final int priority;
        private BranchData left, right;

        public Branch(PriorityQueue.PriorityData left, PriorityQueue.PriorityData right) {
            this.priority = (left.getPriority() + right.getPriority());
            this.left = new BranchData(BranchEnum.LEFT, left.getData());
            this.right = new BranchData(BranchEnum.RIGHT, right.getData());
        }

        public int getPriority() {
            return this.priority;
        }

        public Object getLeft() {
            return this.left.getData();
        }

        public BranchData getLeftBranchData() {
            return this.left;
        }

        public Object getRight() {
            return this.right.getData();
        }

        public BranchData getRightBranchData() {
            return this.right;
        }

        @Override
        public String toString() {
            return "Branch{" + "priority=" + priority + ", left=" + left + ", right=" + right + '}';
        }
    }
}