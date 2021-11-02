package me.tyler.editor;

import me.tyler.editor.shape.Shape;
import me.tyler.editor.shape.impl.Circle;
import me.tyler.editor.shape.impl.Rectangle;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class GraphicsEditor {

    private final List<Shape> shapes;
    private Type selectedType;

    public static void main(String[] args) {
        new GraphicsEditor();
    }

    public GraphicsEditor() {
        this.shapes = new ArrayList<>();

        JFrame frame = new JFrame();
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1000, 600);

        // Whole
        JPanel container = new JPanel();
        container.setBorder(BorderFactory.createTitledBorder("Graphics Editor"));

        // Top Part of Entire thing
        JPanel holder = new JPanel();
        holder.setBackground(Color.GRAY);
        holder.setPreferredSize(new Dimension(1000, 100));

        // First Row of buttons
        JPanel topRow = new JPanel();
        topRow.setLayout(new BoxLayout(topRow, BoxLayout.X_AXIS));

        topRow.add(new ButtonBuilder("Circle", (type, event) -> {
            if(type == ClickType.CLICKED)
                this.selectedType = Circle.class;
        }).build());

       topRow.add(new ButtonBuilder("Rectangle", (type, event) -> {
           if(type == ClickType.CLICKED)
               this.selectedType = Rectangle.class;
        }).build());

        // Bottom
        JPanel canvas = new JPanel();
        canvas.setPreferredSize(new Dimension(1000, 500));

        canvas.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                handleCircle(e);
                frame.repaint();
            }

            @Override public void mousePressed(MouseEvent e) {}
            @Override public void mouseReleased(MouseEvent e) {}
            @Override public void mouseEntered(MouseEvent e) {}
            @Override public void mouseExited(MouseEvent e) {}
        });

        holder.add(topRow);

        container.add(holder);
        container.add(canvas);

        frame.add(container);
        frame.setVisible(true);
    }

    /**
     *
     * @param event - From click event
     */
    private void handleCircle(MouseEvent event) {
        if(this.selectedType != Circle.class) return;

        this.shapes.add(new Circle(event.getX(), event.getY(), 50, 50, Color.RED));
    }
}
