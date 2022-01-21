package me.tyler.kbg;

import java.util.*;
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

    public List<String> path(E start, E end) {
        return this.path(vertices.get(start), vertices.get(end));
    }

    private List<String> path(Vertex start, Vertex end) {
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

    public List<String> backtrace(Map<Vertex, Edge> leadsTo, Vertex endVertex) {
        final List<String> path = new ArrayList<>();
        Vertex curr = endVertex;

        while(curr != null) {
            Edge edge = leadsTo.get(curr);
            path.add(curr.getInfo() + " is connected to " + edge.getNeighbor(curr) + " by " + edge.label);
            curr = edge.getNeighbor(curr);
        }

        Collections.reverse(path);
        return path;
    }
}
