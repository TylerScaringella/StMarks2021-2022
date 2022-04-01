package me.tyler.gps;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class GPS extends JPanel implements MouseListener {

    private final LocationGraph<Location, Integer> map;
    private final Image backgroundImage;

    public static void main(String[] args) {
        new GPS();
    }

    public GPS() {
        this.map = new LocationGraph<>();
        this.backgroundImage = Toolkit.getDefaultToolkit().getImage("C:\\Users\\tjsca\\Documents\\cs\\GPS\\st marks aerial.jpg");
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

            g.drawString(location.getName(), location.getX()-15, location.getY()+15);
        });
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if(isMouseOnLocation(e.getX(), e.getY())) return;
        final String locationName = JOptionPane.showInputDialog(NAME_FIELD_PLACEHOLDER);
        if(locationName == null || locationName.length() == 0) return;
        map.add(new Location(
                locationName,
                e.getX(),
                e.getY()));

        System.out.println(String.format("Created %s", locationName));
        this.repaint();
    }

    private boolean isMouseOnLocation(int x, int y) {

        return false;
    }

    @Override public void mousePressed(MouseEvent e) {}
    @Override public void mouseReleased(MouseEvent e) {}
    @Override public void mouseEntered(MouseEvent e) {}
    @Override public void mouseExited(MouseEvent e) {}

    private static final String NAME_FIELD_PLACEHOLDER = "Name of location..";
    private static final int DISTANCE_THRESHOLD = 7;
}
