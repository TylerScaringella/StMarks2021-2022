package me.tyler.kbg;

import me.tyler.kbg.util.ButtonBuilder;
import me.tyler.kbg.util.Path;
import me.tyler.kbg.util.click.ClickType;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.*;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class KBG extends JPanel {

    private final LabeledGraph<String, String> graph;

    private final Map<Integer, String> actors;
    private final Map<Integer, String> movies;

    private final Map<Integer, List<Integer>> movieActors;
    private final Map<String, String> actorDisplay;
    private final AtomicInteger connections;
    private boolean loaded;

    public KBG() {
        // Make frame
        this.loaded = false;
        final JFrame frame = new JFrame();
        frame.setName("Kevin Bacon Game");
        frame.setResizable(false);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setSize(600, 600);

        // Add frame content
        setupComponents();
        frame.add(this);
        frame.setVisible(true);

        this.graph = new LabeledGraph<>();

        // Initialize caches
        this.actors = new HashMap<>();
        this.movies = new HashMap<>();
        this.movieActors = new HashMap<>();
        this.connections = new AtomicInteger(0);
        this.actorDisplay = new HashMap<>();

        // Fill up the caches
        loadActors();
        loadMovies();
        loadConnections();

        // Set loaded to true so we know that the cache is up to date with the accurate information
        this.loaded = true;
        System.out.println(String.format("There are %s connections", connections.get()));
    }

    /**
     * Loads actors from actors.txt and puts them in the actors map
     * Key - Actor ID
     * Value - Actor Name
     */
    private void loadActors() {
        try {
            final FileReader fileReader = new FileReader("Kevin Bacon Game\\actors.txt");
            final BufferedReader reader = new BufferedReader(fileReader);
            String line;
            while((line = reader.readLine()) != null) {
                String[] split = line.split("~");
                final int actorId = Integer.parseInt(split[0]);
                final String actorName = split[1];
                this.actors.put(actorId, actorName.toLowerCase());
                // We add the actor to graph via a vertex so we are able to connect them later on
                this.graph.addVertex(this.actors.getOrDefault(actorId, "Unknown Actor"));
                // We keep a cache of actor display names so we are able to have case-insensitive inputs later on
                this.actorDisplay.put(actorName.toLowerCase(), actorName);
            }

            System.out.println(String.format("Successfully loaded and cached %s actors", this.actors.size()));
        }catch(Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Just loads movies from movies.txt and puts them in the movies map
     * Key - Movie ID
     * Value - Movie Name
     */
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

    /**
     * Loads connections from movie-actors.txt and puts them in the movie actors map
     * Key - Movie ID
     * Value - List of actor IDs in the movie
     */
    private void loadConnections() {
        try {
            final FileReader fileReader = new FileReader("Kevin Bacon Game\\movie-actors.txt");
            final BufferedReader reader = new BufferedReader(fileReader);
            String line;
            while((line = reader.readLine()) != null) {
                String[] split = line.split("~");
                int movieId = Integer.parseInt(split[0]);
                int actorId = Integer.parseInt(split[1]);

                // We get the current actors in each of the movies
                final List<Integer> curActors = this.movieActors.getOrDefault(movieId, new ArrayList<>());

                // We connect the current actor in the movie to other actors in the same movie
                curActors.forEach(actor -> {
                    this.connections.addAndGet(1); // Keep track of how many *total* connections there are
                    this.graph.connect(this.actors.get(actorId), actors.get(actor), this.movies.getOrDefault(movieId, "Unknown Movie"));
                });

                // Add current actor to the list of actors in this movie so we are able to recycle this code
                curActors.add(actorId);
                this.movieActors.put(movieId, curActors);
            }

            System.out.println(String.format("Successfully loaded and cached %s connections", this.movieActors.size()));
        }catch(Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Puts all of the different components that we see visually in the JFrame
     */
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
        responseArea.setPreferredSize(new Dimension(525, 600));
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

                // Get the path from one actor to another - utilizing our actor name cache & whitespace method
                final List<Path> path = graph.path(formatName(inputAreaOne.getText().toLowerCase()), formatName(inputAreaTwo.getText().toLowerCase()));
                if(path == null) {
                    responseArea.setText("Could not find any connections");
                    return;
                }
                // Print the path, splitting each entry with one line
                responseArea.setText(path.stream().map(a -> String.format("%s is connected to %s thru %s", actorDisplay.get(a.getOne()), actorDisplay.get(a.getTwo()), a.getLabel())).collect(Collectors.joining("\n")));
            }
        }).build());

        buttonContainer.add(new ButtonBuilder("Furthest", (type, event) -> {
            if (type == ClickType.CLICKED) {
                if (!this.loaded) {
                    responseArea.setText("The program is still loading");
                    return;
                }

                if (inputAreaOne.getText().length() == 0) {
                    responseArea.setText("You must supply the name of an actor");
                    return;
                }

                responseArea.setText(String.format("The furthest actor from %s is %s", actorDisplay.get(formatName(inputAreaOne.getText().toLowerCase())), actorDisplay.get(graph.furthest(formatName(inputAreaOne.getText().toLowerCase())))));
            }
        }).build());

        buttonContainer.add(new ButtonBuilder("Connections", (type, event) -> {
            if (type == ClickType.CLICKED) {
                if (!this.loaded) {
                    responseArea.setText("The program is still loading");
                    return;
                }

                if (inputAreaOne.getText().length() == 0) {
                    responseArea.setText("You must supply the name of an actor");
                    return;
                }

                final List<String> directConnections = graph.connections(formatName(inputAreaOne.getText().toLowerCase()));
                responseArea.setText(String.format("%s has %s direct connections:\n%s", actorDisplay.get(formatName(inputAreaOne.getText().toLowerCase())), directConnections.size(), directConnections.stream().map(actorDisplay::get).collect(Collectors.joining("\n"))));
            }
        }).build());

        buttonContainer.add(new ButtonBuilder("Most Connections", (type, event) -> {
            if (type == ClickType.CLICKED) {
                if (!this.loaded) {
                    responseArea.setText("The program is still loading");
                    return;
                }

                final String mostConnections = graph.mostConnections();
                responseArea.setText(String.format("The actor with the most connections is %s with %s connections", actorDisplay.get(mostConnections), graph.connections(mostConnections).size()));
            }
        }).build());

        Arrays.asList(
                buttonContainer,
                inputContainer,
                responseArea
        ).forEach(container::add);
        this.add(container);
    }

    /**
     *
     * @param input
     * @return - The input without whitespace
     */
    public String formatName(String input) {
        return input
                .replaceAll("^[\\s]+|[\\s]+$", "")
                .replaceAll("( +)", " ");
    }

    public static void main(String[] args) {
        new KBG();
    }
}
