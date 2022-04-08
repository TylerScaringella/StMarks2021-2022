package me.tyler.gps;

import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public abstract class Graph<E, T> {

    protected final Map<E, Vertex> vertices;

    public Graph() {
        this.vertices = new HashMap<>();
    }

    public abstract void draw(Graphics g);

    public int size() {
        return this.vertices.size();
    }

    public E add(E info) {
        Vertex vertex = new Vertex(info);
        this.vertices.put(info, vertex);
        return info;

    }

    private void forEach(BiConsumer<E, Vertex> consumer) {
        this.vertices.forEach(consumer);
    }

    public void forEach(Consumer<E> consumer) {
        this.vertices.keySet().forEach(consumer);
    }

    public Stream<E> stream() {
        return this.vertices.keySet().stream();
    }

    public boolean contains(E info) {
        return this.vertices.containsKey(info);
    }

    public void connect(E one, E two, T label) {
        final Vertex first = getVertex(one);
        final Vertex second = getVertex(two);

        final Edge edge = new Edge(
                first,
                second,
                label
        );

        first.addEdge(edge);
        second.addEdge(edge);
    }

    public List<Vertex> path(Vertex start, Vertex end) {
        Vertex curr = start;
        final Map<Vertex, Vertex> leadsTo = new HashMap<>();
        final Set<Vertex> visited = new HashSet<>();
        final Map<Vertex, Integer> distance = new HashMap<>();
        final PriorityQueue<Vertex> toVisit = new PriorityQueue<>();
        toVisit.put(curr, 0);
        leadsTo.put(curr, null);

        while(!toVisit.isEmpty()) {
            curr = toVisit.pop();
            visited.add(curr);

            if(curr.equals(end)) {
                return backtrace(leadsTo, end);
            }

            for(Edge edge : curr.getEdges()) {
                final Vertex neighbor = edge.getNeighbor(curr);
                if(visited.contains(neighbor)) continue;

                final int currDistStart = distance.getOrDefault(curr, 0);
                final int neighborDistPrev = edge.getLabel();

                final int distFromStart = currDistStart + neighborDistPrev;
                if(currDistStart < distFromStart) {
                    distance.put(neighbor, distFromStart);
                    toVisit.put(neighbor, distFromStart);
                    leadsTo.put(neighbor, curr);
                }
            }

        }

        return Collections.emptyList();
    }

    public List<E> path(E start, E end) {
        return this.path(getVertex(start), getVertex(end)).stream()
                .map(Vertex::getInfo).collect(Collectors.toList());
    }

    public List<Vertex> backtrace(Map<Vertex, Vertex> leadsTo, Vertex endVertex) {
        final List<Vertex> path = new ArrayList<>();
        Vertex curr = endVertex;

        while(curr != null) {
            path.add(curr);
            curr = leadsTo.getOrDefault(curr, null);
        }

        Collections.reverse(path);
        return path;
    }

    protected Vertex getVertex(E info) {
        if(!contains(info)) throw new NullPointerException("Data is not a member of the graph");
        return this.vertices.get(info);
    }


    protected class Vertex {
        private E info;
        private Set<Edge> edges;

        public Vertex(E info) {
            this.info = info;
            this.edges = new HashSet<>();
        }

        public Edge addEdge(Edge edge) {
            this.edges.add(edge);
            return edge;
        }

        public E getInfo() {
            return info;
        }

        public Set<Edge> getEdges() {
            return edges;
        }

        public boolean isConnected(Vertex vertex) {
            return this.edges
                    .stream()
                    .anyMatch(edge -> edge.getNeighbor(this).equals(vertex));
        }
    }

    protected class Edge {
        private Vertex v1, v2;
        private T info;

        public Edge(Vertex v1, Vertex v2, T info) {
            this.v1 = v1;
            this.v2 = v2;
            this.info = info;
        }

        public Vertex getV1() {
            return v1;
        }

        public Vertex getV2() {
            return v2;
        }

        public T getInfo() {
            return info;
        }

        public int getLabel() {
            return Integer.parseInt(this.info.toString());
        }

        public Vertex getNeighbor(Vertex v) {
            if (v.info.equals(v1.info)) {
                return v2;
            }
            return v1;
        }
    }
}
