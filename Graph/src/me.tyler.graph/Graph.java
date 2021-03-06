package me.tyler.graph;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public class Graph<E> {

    private final Map<E, Vertex> vertices;

    public Graph() {
        this.vertices = new HashMap<>();
    }

    public int size() {
        return this.vertices.size();
    }

    public void remove(E info) {
        final Vertex vertex = getVertex(info);
        this.vertices.remove(info);

        this.vertices.values().forEach(curVertex -> curVertex.getNeighbors().remove(vertex));
    }

    public E add(E info) {
        Vertex vertex = new Vertex(info);
        this.vertices.put(info, vertex);
        return info;
    }

    public boolean contains(E info) {
        return this.vertices.containsKey(info);
    }

    public void connect(E one, E two) {
        final Vertex first = getVertex(one);
        final Vertex second = getVertex(two);

        first.addNeighbor(second);
        second.addNeighbor(first);
    }

    public List<E> path(E start, E end) {
        return this.path(getVertex(start), getVertex(end)).stream()
                .map(Vertex::getInfo).collect(Collectors.toList());
    }

    private List<Vertex> path(Vertex start, Vertex end) {
        Vertex curr = start;
        // Key - Vertex
        // Value - The vertex that lead to Key
        final Map<Vertex, Vertex> leadsTo = new HashMap<>();
        final List<Vertex> toVisit = new ArrayList<>();
        toVisit.add(curr);
        leadsTo.put(curr, null);
        while(!toVisit.isEmpty()) {
            curr = toVisit.remove(0);

            for(Vertex neighbor : curr.getNeighbors()) {
                if(!leadsTo.containsKey(neighbor)) {
                    toVisit.add(neighbor);
                    leadsTo.put(neighbor, curr);
                }
                if(neighbor.equals(end)) {
                    return backtrace(leadsTo, end);
                }
            }
        }

        return Collections.emptyList();
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

    private Vertex getVertex(E info) {
        if(!contains(info)) throw new NullPointerException("Data is not a member of the graph");
        return this.vertices.get(info);
    }

    public void debug() {
        this.vertices.values().forEach(value -> {
            System.out.println("--------------------------------");
            System.out.println(
                    "Info: " + value.getInfo() + "\n" +
                    "Neighbors: " + value.getNeighbors().stream().map(Vertex::getInfo).map(E::toString).collect(Collectors.joining(","))
            );
        });
        System.out.println("--------------------------------");
    }

    private class Vertex {
        private E info;
        private Set<Vertex> neighbors;

        public Vertex(E info) {
            this.info = info;
            this.neighbors = new HashSet<>();
        }

        public Vertex(E info, Set<Vertex> neighbors) {
            this.info = info;
            this.neighbors = neighbors;
        }

        public boolean isNeighbor(Vertex vertex) {
            return this.neighbors.contains(vertex);
        }

        public Vertex addNeighbor(Vertex vertex) {
            this.neighbors.add(vertex);
            return vertex;
        }

        public E getInfo() {
            return info;
        }

        public Set<Vertex> getNeighbors() {
            return neighbors;
        }

        public void setNeighbors(Set<Vertex> neighbors) {
            this.neighbors = neighbors;
        }
    }

    public void save() {
        try {
            final FileWriter fileWriter = new FileWriter("C:\\users\\tjsca\\graph.txt");
            final BufferedWriter writer = new BufferedWriter(fileWriter);

            final List<Vertex> vertices = new ArrayList<>(this.vertices.values());

            this.vertices.values().forEach(vertex -> {
                final StringBuilder sb = new StringBuilder();

                sb.append(vertex.getInfo() + (vertex.getNeighbors().size() > 0 ? "/split/" : ""));
                if(vertex.neighbors.size() > 0)
                    sb.append(vertex.getNeighbors().stream().map(vertices::indexOf).map(Objects::toString).collect(Collectors.joining(",")));
                sb.append("//split//");
                try {
                    writer.write(sb.toString());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });

            writer.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void fromFile(String fileName) {
        try {
            final FileReader fileReader = new FileReader("C:\\users\\tjsca\\graph.txt");
            final BufferedReader reader = new BufferedReader(fileReader);

            final String unparsedData = reader.readLine();
            if(unparsedData == null) throw new RuntimeException("lol stupid idiot there's no data");

            final String[] dataSplit = unparsedData.split("//split//");

            final List<E> info = new ArrayList<>();
            final Map<E, List<Integer>> neighbors = new HashMap<>();

            for(String dataInput : dataSplit) {
                String[] inputSplit = dataInput.split("/split/");
                final E data = (E) inputSplit[0];
                info.add(data);
                String[] unparsedNeighbors = inputSplit[1].split(",");
                neighbors.put(data, new ArrayList<>());
                for(String neighbor : unparsedNeighbors) {
                    int neighborId = Integer.parseInt(neighbor);
                    final List<Integer> curNeighbors = neighbors.get(data);
                    curNeighbors.add(neighborId);
                    neighbors.put(data, curNeighbors);
                }
            }

            info.forEach(data -> vertices.put(data, new Vertex(data)));
            neighbors.forEach((key, value) -> {
                final Vertex vertex = vertices.get(key);
                vertex.setNeighbors(value.stream().map(info::get).map(vertices::get).collect(Collectors.toSet()));
            });
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
