package me.tyler.kbg;

import me.tyler.kbg.util.ButtonBuilder;
import me.tyler.kbg.util.click.ClickType;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.*;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

public class KBG extends JPanel {

    private final LabeledGraph<String, String> graph;

    private final Map<Integer, String> actors;
    private final Map<Integer, String> movies;

    private final Map<Integer, List<Integer>> movieActors;
    private final AtomicInteger connections;
    private boolean loaded;

    public KBG() {
        this.loaded = false;
        final JFrame frame = new JFrame();
        frame.setName("Kevin Bacon Game");
        frame.setResizable(false);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setSize(600, 600);

        setupComponents();
        frame.add(this);
        frame.setVisible(true);

        this.graph = new LabeledGraph<>();

        this.actors = new HashMap<>();
        this.movies = new HashMap<>();
        this.movieActors = new HashMap<>();
        this.connections = new AtomicInteger(0);

        loadActors();
        loadMovies();
        loadConnections();


//        System.out.println("Will Smith has " + this.graph.vertices.get(2888).edges.size() + " edges");

        this.loaded = true;
        System.out.println(String.format("There are %s connections", connections.get()));
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

    private void setupComponents() {
        final JPanel container = new JPanel();
        container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));

        final JPanel buttonContainer = new JPanel();
        buttonContainer.setLayout(new BoxLayout(buttonContainer, BoxLayout.X_AXIS));

        final JPanel inputContainer = new JPanel();

        final JTextArea inputAreaOne = new JTextArea();
        inputAreaOne.setPreferredSize(new Dimension(237, 25));
        inputAreaOne.setEditable(true);

        final JTextArea inputAreaTwo = new JTextArea();
        inputAreaTwo.setPreferredSize(new Dimension(237, 25));
        inputAreaTwo.setEditable(true);

        Arrays.asList(
                inputAreaOne,
                inputAreaTwo
        ).forEach(inputContainer::add);

        final JTextArea responseArea = new JTextArea();
        responseArea.setPreferredSize(new Dimension(475, 600));
        responseArea.setEditable(false);

        buttonContainer.add(new ButtonBuilder("Path", (type, event) -> {
            if (type == ClickType.CLICKED) {
                if(!this.loaded) {
                    responseArea.setText("The program is still loading");
                    return;
                }

                if(inputAreaOne.getText().length() == 0 | inputAreaTwo.getText().length() == 0) {
                    responseArea.setText("You must supply the name of two actors");
                    return;
                }

                final List<String> path = graph.path(inputAreaOne.getText(), inputAreaTwo.getText());
                responseArea.setText(String.join("\n", path));
            }
        }).build());

        Arrays.asList(
                buttonContainer,
                inputContainer,
                responseArea
        ).forEach(container::add);
        this.add(container);
    }

    public static void main(String[] args) {
        new KBG();
    }
}
