package me.tyler.gps;

import me.tyler.gps.util.ButtonBuilder;
import me.tyler.gps.util.click.ClickType;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.*;
import java.util.List;
import java.util.stream.Collectors;

import static me.tyler.gps.Constants.GRAPH_FILE_NAME;
import static me.tyler.gps.Constants.DISTANCE_THRESHOLD;
import static me.tyler.gps.Constants.NAME_FIELD_PLACEHOLDER;

public class GPS extends JPanel implements MouseListener {

    private final LocationGraph map;
    private final Image backgroundImage;
    private final File file;

    private Location initialConnectionClick;
    private boolean connectionMode, editMode;
    private List<Location> path;

    public static void main(String[] args) {
        new GPS();
    }

    public GPS() {
        this.file = new File(GRAPH_FILE_NAME);
        this.map = new LocationGraph(file);
        this.backgroundImage = Toolkit.getDefaultToolkit().getImage("C:\\Users\\tjsca\\Documents\\cs\\GPS\\st marks aerial.jpg");
        final JFrame frame = new JFrame();

        frame.setName("GPS");
        frame.setResizable(false);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setSize(Toolkit.getDefaultToolkit().getScreenSize());
        frame.setLocationRelativeTo(null);

        frame.getContentPane().add(this);
        this.add(new ButtonBuilder("GPS Mode", (type, event) -> {
            if(type == ClickType.CLICKED)
                editMode = false;
        }).build());
        this.add(new ButtonBuilder("Edit Mode", (type, event) -> {
            if(type == ClickType.CLICKED)
                editMode = true;
        }).build());

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
        map.draw(g);
        if(path != null) map.drawPath(g, path);
    }

    private void gpsMouseClick(MouseEvent e) {
        if(isMouseOnLocation(e.getX(), e.getY())) {
            final Location clickedLocation = getLocation(e.getX(), e.getY());

            if(initialConnectionClick == null) {
                initialConnectionClick = clickedLocation;
                this.path = null;
                return;
            }

            final List<Location> path = map.path(initialConnectionClick, clickedLocation);
            System.out.println(path
                    .stream()
                    .map(Location::getName)
                    .collect(Collectors.joining(", ")));
            this.path = path;
            initialConnectionClick = null;
            this.repaint();
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if(!editMode) gpsMouseClick(e);
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
        this.repaint();
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

}
