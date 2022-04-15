package me.tyler.gps;

import java.awt.*;
import java.awt.geom.GeneralPath;
import java.awt.geom.Path2D;
import java.io.*;
import java.util.*;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

import static me.tyler.gps.Constants.DISTANCE_THRESHOLD;

public class LocationGraph extends Graph<Location, Integer> {

    public LocationGraph(File file) {
        super();
        try {
            final Map<Location, List<String>> connections = new HashMap<>();

            final FileReader fileReader = new FileReader(file);
            final BufferedReader reader = new BufferedReader(fileReader);
            String line;

            while((line = reader.readLine()) != null) {
                // data is split with
                // name_with_spaces x y
                // United_States 35 62
                // with connections
                // United_States 35 62 Brazil
                // Brazil 86 13 United_States
                final String[] data = line.split(" ");
                final String name = data[0].replace("_", " ");
                final Location loadedLocation = new Location(
                        name,
                        Integer.parseInt(data[1]),
                        Integer.parseInt(data[2])
                );

                final List<String> curConnections = new ArrayList<>();
                if(data.length > 3) {
                    // has connections
                    for(int i=3; i<data.length; i++) {
                        final String connection = data[i]
                                .replace("_", " ");
                        curConnections.add(connection);
                    }
                }

                final Location added = add(loadedLocation);
                System.out.println(curConnections.toString());
                connections.put(added, curConnections);
            }

            reader.close();

            connections.forEach((location, conn) -> {
                final Vertex origin = getVertex(location);

                conn.forEach(connection -> {
                    final Vertex connVertex = getVertexFromInfo(connection);
                    if(connVertex == null) return;
                    if(origin.isConnected(connVertex)) return;

                    connect(origin.getInfo(), connVertex.getInfo(), (int)origin.getInfo().distance(connVertex.getInfo()));
                });
            });
        }catch(IOException ex) {
            ex.printStackTrace();
        }

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            try {
                final FileWriter fileWriter = new FileWriter(file);
                final BufferedWriter writer = new BufferedWriter(fileWriter);
                vertices.forEach((location, vertex) -> {
                    final String name = location.getName().replace(" ", "_");
                    try {
                        final String connString = vertex.getEdges()
                                .stream()
                                .map(edge -> edge.getNeighbor(vertex).getInfo())
                                .map(Location::getName)
                                .map(connName -> connName.replace(" ", "_"))
                                .collect(Collectors.joining(" "));
                        System.out.println(connString);
                        writer.write(name + " " + location.getX() + " " + location.getY() + " " + connString + "\n");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });

                writer.close();
            }catch(IOException ex) {
                ex.printStackTrace();
            }
        }));
    }

    @Override
    public void draw(Graphics g) {
        g.setColor(Color.RED);
        vertices.forEach((location, vertex) -> {
            g.fillOval(location.getX() - DISTANCE_THRESHOLD, location.getY() - DISTANCE_THRESHOLD, DISTANCE_THRESHOLD * 2, DISTANCE_THRESHOLD * 2);

            g.drawString(location.getName(), location.getX() - 15, location.getY() + 20);

            vertex.getEdges().forEach(edge -> {
                final Vertex neighbor = edge.getNeighbor(vertex);
                final Location neighborLocation = neighbor.getInfo();
                // draw line from current to neighbor
                g.drawLine(
                        location.getX(), location.getY(),
                        neighborLocation.getX(), neighborLocation.getY()
                );
            });
        });
    }

    public void drawPath(Graphics g, List<Location> route) {
        System.out.println("drawing path");
        g.setColor(Color.GREEN);
        final Graphics2D g2d = (Graphics2D) g;
        AtomicBoolean startSet = new AtomicBoolean(false);
        final GeneralPath path = new GeneralPath();
        route.forEach(location -> {
            if(!startSet.get()) {
                path.moveTo(location.getX(), location.getY());
                System.out.println("move to");
                startSet.set(true);
                return;
            }

            path.lineTo(location.getX(), location.getY());
            System.out.println("line to " + location.getX() + " | " + location.getY());
        });

        g2d.draw(path);
    }

    private Vertex getVertexFromInfo(String info) {
        return this.vertices.values().stream().filter(vertex -> {
            return vertex.getInfo().getName().equals(info);
        }).findFirst().orElse(null);
    }
}