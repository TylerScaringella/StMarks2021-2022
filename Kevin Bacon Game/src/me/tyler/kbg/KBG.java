package me.tyler.kbg;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class KBG extends JPanel {

    private final LabeledGraph<String, String> graph;

    private final Map<Integer, String> actors;
    private final Map<Integer, String> movies;

    private final Map<Integer, List<Integer>> movieActors;
    private final AtomicInteger connections;

    public KBG() {
        final JFrame frame = new JFrame();
        frame.add(this);

        this.graph = new LabeledGraph<>();

        this.actors = new HashMap<>();
        this.movies = new HashMap<>();
        this.movieActors = new HashMap<>();
        this.connections = new AtomicInteger(0);

        loadActors();
        loadMovies();
        loadConnections();


//        System.out.println("Will Smith has " + this.graph.vertices.get(2888).edges.size() + " edges");

        System.out.println(String.format("There are %s connections", connections.get()));

        frame.setVisible(true);
    }

    private void loadActors() {
        try {
            final FileReader fileReader = new FileReader("Kevin Bacon Game\\actors.txt");
            final BufferedReader reader = new BufferedReader(fileReader);
            String line;
            while((line = reader.readLine()) != null) {
                String[] split = line.split("~");
                final int actorId = Integer.parseInt(split[0]);
                this.actors.put(actorId, split[1]);
                this.graph.addVertex(this.actors.getOrDefault(actorId, "Unknown Actor"));
            }

            System.out.println(String.format("Successfully loaded and cached %s actors", this.actors.size()));
        }catch(Exception ex) {
            ex.printStackTrace();
        }
    }

    private void loadMovies() {
        try {
            final FileReader fileReader = new FileReader("Kevin Bacon Game\\movies.txt");
            final BufferedReader reader = new BufferedReader(fileReader);
            String line;
            while((line = reader.readLine()) != null) {
                String[] split = line.split("~");
                this.movies.put(Integer.parseInt(split[0]), split[1]);
            }

            System.out.println(String.format("Successfully loaded and cached %s movies", this.movies.size()));
        }catch(Exception ex) {
            ex.printStackTrace();
        }
    }

    private void loadConnections() {
        try {
            final FileReader fileReader = new FileReader("Kevin Bacon Game\\movie-actors.txt");
            final BufferedReader reader = new BufferedReader(fileReader);
            String line;
            while((line = reader.readLine()) != null) {
                String[] split = line.split("~");
                int movieId = Integer.parseInt(split[0]);
                int actorId = Integer.parseInt(split[1]);

                final List<Integer> curActors = this.movieActors.getOrDefault(movieId, new ArrayList<>());

                curActors.forEach(actor -> {
                    this.connections.addAndGet(1);
                    this.graph.connect(this.actors.get(actorId), actors.get(actor), this.movies.getOrDefault(movieId, "Unknown Movie"));
                });

                curActors.add(actorId);
                this.movieActors.put(movieId, curActors);
            }

            System.out.println(String.format("Successfully loaded and cached %s connections", this.movieActors.size()));
        }catch(Exception ex) {
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new KBG();
    }
}
