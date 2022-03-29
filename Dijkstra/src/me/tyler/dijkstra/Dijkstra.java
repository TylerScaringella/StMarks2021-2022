package me.tyler.dijkstra;

import java.util.stream.Collectors;

public class Dijkstra {

    public static void main(String[] args) {
        new Dijkstra();
    }

    public Dijkstra() {
        final LocationGraph<String, Integer> graph = new LocationGraph<>();

        final String a = "A", b = "B", c = "C", d = "D", e = "E";
        graph.add(a);
        graph.add(b);
        graph.add(c);
        graph.add(d);
        graph.add(e);
        graph.connect(a, b, 2);
        graph.connect(b, c, 5);
        graph.connect(a, e, 1);
        graph.connect(e, d, 2);
        graph.connect(d, c, 1);

        System.out.println(String.join(", ", graph.path(a, c)));
    }
}
