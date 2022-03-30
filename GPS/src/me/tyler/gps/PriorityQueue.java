package me.tyler.gps;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

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

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            PriorityData that = (PriorityData) o;
            return data.equals(that.data);
        }

        @Override
        public int hashCode() {
            return Objects.hash(data);
        }
    }

//    public E add(E data, int priority) {
//        PriorityData priorityData = new PriorityData(data, priority);
//        this.queue.add(priorityData);
//        this.recalculateSort();
//        return priorityData.getData();
//    }

    public E put(E data, int priority) {
        // works just like add, however if the data is already in, it replaces / updates with the new priority

        PriorityData insertingData = new PriorityData(data, priority);
        queue.remove(insertingData);
        queue.add(insertingData);
        recalculateSort();

        return insertingData.getData();
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
        return  this.queue.stream().map(data -> data.getData().toString()).collect(Collectors.joining(", "));
    }

    public boolean isEmpty() {
        return this.queue.isEmpty();
    }
}
