package me.tyler.gps;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.*;

public class GPS extends JPanel implements MouseListener {

    private final LocationGraph<Location, Integer> map;
    private final Image backgroundImage;
    private final File file;

    private Location initialConnectionClick;
    private boolean connectionMode;

    public static void main(String[] args) {
        new GPS();
    }

    public GPS() {
        this.file = new File(GRAPH_FILE_NAME);
        this.map = new LocationGraph<>();
        this.backgroundImage = Toolkit.getDefaultToolkit().getImage("C:\\Users\\tjsca\\Documents\\cs\\GPS\\st marks aerial.jpg");
        registerVertexPersistence();
        final JFrame frame = new JFrame();

        frame.setName("GPS");
        frame.setResizable(false);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setSize(Toolkit.getDefaultToolkit().getScreenSize());
        frame.setLocationRelativeTo(null);

        frame.getContentPane().add(this);

        this.addMouseListener(this);

        new ImageIcon(backgroundImage);

        frame.setVisible(true);
        this.repaint();
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        g.drawImage(
                backgroundImage,
                0,
                0,
                (int)Toolkit.getDefaultToolkit().getScreenSize().getWidth(),
                (int)Toolkit.getDefaultToolkit().getScreenSize().getHeight(),
                null);
        renderMap(g);
    }

    private void renderMap(Graphics g) {
        g.setColor(Color.RED);
        map.forEach(location -> {
            System.out.println("Drawing " + location.getName());
            g.fillOval(
                    location.getX()-DISTANCE_THRESHOLD,
                    location.getY()-DISTANCE_THRESHOLD,
                    DISTANCE_THRESHOLD*2,
                    DISTANCE_THRESHOLD*2);

            g.drawString(location.getName(), location.getX()-15, location.getY()+20);
        });
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if(isMouseOnLocation(e.getX(), e.getY())) {
            if(connectionMode) {
                connectionClick(e);
            }

            initialConnectionClick = getLocation(e.getX(), e.getY());
            connectionMode = true;
            return;
        }

        if(connectionMode) connectionMode = false;
        if(initialConnectionClick != null) initialConnectionClick = null;

        final String locationName = JOptionPane.showInputDialog(NAME_FIELD_PLACEHOLDER);
        if(locationName == null || locationName.length() == 0) return;
        map.add(new Location(
                locationName,
                e.getX(),
                e.getY()));

        System.out.println(String.format("Created %s", locationName));
        this.repaint();
    }

    private void connectionClick(MouseEvent e) {
        final Location clickedLocation = getLocation(e.getX(), e.getY());
        if(initialConnectionClick == null) {
            initialConnectionClick = clickedLocation;
            return;
        }

        System.out.println(String.format("Connected %s with %s", initialConnectionClick.getName(), clickedLocation.getName()));
        map.connect(initialConnectionClick, clickedLocation, (int) initialConnectionClick.distance(clickedLocation));
        initialConnectionClick = null;
    }

    private void registerVertexPersistence() {
        loadVertices();
        Runtime.getRuntime().addShutdownHook(new Thread(this::saveVertices));
    }

    private void loadVertices() {
        try {
            final FileReader fileReader = new FileReader(file);
            final BufferedReader reader = new BufferedReader(fileReader);
            String line;

            while((line = reader.readLine()) != null) {
                // data is split with
                // name_with_spaces x y
                // United_States 35 62
                final String[] data = line.split(" ");
                final Location loadedLocation = new Location(
                        data[0],
                        Integer.parseInt(data[1]),
                        Integer.parseInt(data[2])
                );

                map.add(loadedLocation);
            }

            reader.close();
        }catch(IOException ex) {
            ex.printStackTrace();
        }
    }

    private void saveVertices() {
        try {
            final FileWriter fileWriter = new FileWriter(file);
            final BufferedWriter writer = new BufferedWriter(fileWriter);
            map.forEach(location -> {
                final String name = location.getName().replace(" ", "_");
                try {
                    writer.write(name + " " + location.getX() + " " + location.getY() + "\n");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });

            writer.close();
        }catch(IOException ex) {
            ex.printStackTrace();
        }
    }

    private boolean isMouseOnLocation(int x, int y) {
        return getLocation(x, y) != null;
    }

    private Location getLocation(int x, int y) {
        return map
                .stream()
                .filter(location -> location.distance(x, y) <= DISTANCE_THRESHOLD)
                .findFirst()
                .orElse(null);
    }

    @Override public void mousePressed(MouseEvent e) {}
    @Override public void mouseReleased(MouseEvent e) {}
    @Override public void mouseEntered(MouseEvent e) {}
    @Override public void mouseExited(MouseEvent e) {}

    private static final String NAME_FIELD_PLACEHOLDER = "Name of location..";
    private static final String GRAPH_FILE_NAME = "graph.txt";
    private static final int DISTANCE_THRESHOLD = 7;
}
