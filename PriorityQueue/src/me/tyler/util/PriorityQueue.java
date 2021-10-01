package me.tyler.util;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class PriorityQueue<E> {

    private final List<PriorityData> queue;

    public class PriorityData {
        private final E data;
        private final int priority;

        public PriorityData(E data, int priority) {
            this.data = data;
            this.priority = priority;
        }

        public E getData() {
            return this.data;
        }

        public int getPriority() {
            return this.priority;
        }

        @Override
        public String toString() {
            return this.data.toString();
        }
    }

    public E add(E data, int priority) {
        PriorityData priorityData = new PriorityData(data, priority);
        this.queue.add(priorityData);
        this.recalculateSort();
        return priorityData.getData();
    }

    public E pop() {
        return this.popData().getData();
    }

    public PriorityData popData() {
        if(this.size() < 1) throw new IndexOutOfBoundsException();
        PriorityData firstData = this.queue.remove(0);
        this.recalculateSort();
        return firstData;
    }

    public PriorityQueue() {
        this.queue = new ArrayList<>();
    }

    public int size() {
        return this.queue.size();
    }

    private void recalculateSort() {
        this.queue.sort(Comparator.comparingInt(PriorityData::getPriority));
    }

    @Override
    public String toString() {
        return this.queue.toString();
    }
}
