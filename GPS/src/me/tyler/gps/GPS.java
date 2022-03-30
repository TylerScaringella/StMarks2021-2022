package me.tyler.gps;

import com.sun.glass.ui.Screen;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;

public class GPS extends JPanel implements MouseListener, KeyListener {

    private final LocationGraph<Location, Integer> map;
    private final JTextField nameField;
    private final Image backgroundImage;

    private Location editingLocation;

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
        this.nameField = new JTextField(NAME_FIELD_PLACEHOLDER);
        this.nameField.setVisible(false);
        this.nameField.setPreferredSize(new Dimension(100, 100));
        this.nameField.setBackground(Color.GRAY);
        this.add(nameField);

        drawMap();

        frame.setVisible(true);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
    }

    private void renderMap() {

    }

    private void drawMap() {
        // going to eventually need to load locations from file
        // same thing goes for saving them on close

        // draw image
        final JLabel backgroundLabel = new JLabel(new ImageIcon(backgroundImage));
        this.add(backgroundLabel);

        renderMap();
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        this.nameField.setVisible(true);
        this.nameField.setText(NAME_FIELD_PLACEHOLDER);

        this.editingLocation = new Location("", e.getX(), e.getY());
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(!nameField.isVisible()) return;
        if(nameField.getText().equals(NAME_FIELD_PLACEHOLDER)) return;
        if(editingLocation == null) return;

        this.editingLocation.setName(nameField.getText());
        this.map.add(this.editingLocation);
        System.out.println("Added " + editingLocation.toString());
        this.editingLocation = null;
        renderMap();
    }

    @Override public void mousePressed(MouseEvent e) {}
    @Override public void mouseReleased(MouseEvent e) {}
    @Override public void mouseEntered(MouseEvent e) {}
    @Override public void mouseExited(MouseEvent e) {}
    @Override public void keyTyped(KeyEvent e) {}
    @Override public void keyReleased(KeyEvent e) {}

    private static final String NAME_FIELD_PLACEHOLDER = "Name of location..";
}
