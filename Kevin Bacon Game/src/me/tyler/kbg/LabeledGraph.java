package me.tyler.kbg;

import me.tyler.kbg.util.Path;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

public class LabeledGraph<E, T> {

    HashMap<E, Vertex> vertices;

    public LabeledGraph() {
        vertices = new HashMap<E, Vertex>();
    }

    public void addVertex(E info) {
        vertices.put(info, new Vertex(info));
    }

    public void connect(E info1, E info2, T label) {
        Vertex v1 = vertices.get(info1);
        Vertex v2 = vertices.get(info2);

        if (v1 == null || v2 == null) {
            System.out.println("Vertex " + (v1==null? v1:v2).toString() + " not found");
            return;
        }

        Edge e = new Edge(label, v1, v2);

        v1.edges.add(e);
        v2.edges.add(e);
    }

    private class Edge {
        T label;
        Vertex v1, v2;

        public Edge(T label, Vertex v1, Vertex v2) {
            this.label = label; this.v1 = v1; this.v2 = v2;
        }

        public Vertex getNeighbor(Vertex v) {
            if (v.info.equals(v1.info)) {
                return v2;
            }
            return v1;
        }

    }

    public class Vertex {
        E info;
        HashSet<Edge> edges;

        public Vertex(E info) {
            this.info = info;
            edges = new HashSet<>();
        }

        public E getInfo() {
            return info;
        }
    }

    public E mostConnections() {
        return this.vertexWithMostConnections().getInfo();
    }

    private Vertex vertexWithMostConnections() {
        final AtomicReference<Vertex> ref = new AtomicReference<>();
        vertices.values().forEach(vertex -> {
            if(ref.get() == null) {
                ref.set(vertex);
                return;
            }
            if(ref.get() == vertex) return;
            if(this.connections(vertex).size() > this.connections(vertex).size()) ref.set(vertex);
        });

        return ref.get();
    }

    public List<E> connections(E start) {
        return this.connections(vertices.get(start)).stream().map(Vertex::getInfo).collect(Collectors.toList());
    }

    private List<Vertex> connections(Vertex start) {
        final List<Vertex> directConnections = new ArrayList<>();

        start.edges.forEach(edge -> {
            directConnections.add(edge.getNeighbor(start));
        });

        return directConnections;
    }

    public List<Path> path(E start, E end) {
        return this.path(vertices.get(start), vertices.get(end));
    }

    private Vertex furthest(Vertex start) {
        Vertex curr = start;
        final LinkedList<Vertex> queue = new LinkedList<>();
        final Map<Vertex, Integer> leadsTo = new HashMap<>();
        queue.add(curr);
        leadsTo.put(curr, null);
        while(!queue.isEmpty()) {
            curr = queue.poll();

            for(Edge edge : curr.edges) {
                Vertex nonCur = edge.getNeighbor(curr);
                if(!leadsTo.containsKey(nonCur)) {
                    queue.add(nonCur);
                    final AtomicInteger value = new AtomicInteger(1);
                    if(leadsTo.get(curr) != null) value.set(leadsTo.get(curr)+1);
                    leadsTo.put(nonCur, value.get());
                }
            }
        }

        System.out.println(String.format("The distance from %s to %s was %s", start.getInfo(), curr.getInfo(), leadsTo.getOrDefault(curr, 0)));
        return curr;
    }

    public E furthest(E start) {
        return this.furthest(vertices.get(start)).getInfo();
    }



    private List<Path> path(Vertex start, Vertex end) {
        Vertex curr = start;
        // Key - Vertex
        // Value - The vertex that lead to Key
        final Map<Vertex, Edge> leadsTo = new HashMap<>();
        final List<Vertex> toVisit = new ArrayList<>();
        toVisit.add(curr);
        leadsTo.put(curr, null);
        while(!toVisit.isEmpty()) {
            curr = toVisit.remove(0);

            for(Edge edge : curr.edges) {
                Vertex nonCur = edge.getNeighbor(curr);
                if(!leadsTo.containsKey(nonCur)) {
                    toVisit.add(nonCur);
                    leadsTo.put(nonCur, edge);
                }

                if(nonCur.equals(end)) {
                    return backtrace(leadsTo, end);
                }
            }
        }

        return Collections.emptyList();
    }

    public List<Path> backtrace(Map<Vertex, Edge> leadsTo, Vertex endVertex) {
        final List<Path> path = new ArrayList<>();
        Vertex curr = endVertex;

        while(curr != null) {
            Edge edge = leadsTo.get(curr);
            if(edge == null) break;
            path.add(new Path(
                    curr.getInfo().toString(),
                    edge.getNeighbor(curr).getInfo().toString(),
                    edge.label.toString()
            ));
            curr = edge.getNeighbor(curr);
        }

        Collections.reverse(path);
        return path;
    }
}
