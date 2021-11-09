package me.tyler.editor;

import me.tyler.editor.shape.Shape;
import me.tyler.editor.shape.impl.Circle;
import me.tyler.editor.shape.impl.EmptyShape;
import me.tyler.editor.shape.impl.Rectangle;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class GraphicsEditor {

    private final List<Shape> shapes;
    private Type selectedType;
    private Shape lastDeleted;

    private int startX, startY, endX, endY;
    private Color currentColor = Color.RED;

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
        holder.setLayout(new BoxLayout(holder, BoxLayout.Y_AXIS));

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

       // Second Row of buttons
        JPanel secondRow = new JPanel();
        secondRow.setLayout(new BoxLayout(secondRow, BoxLayout.X_AXIS));

        secondRow.add(new ButtonBuilder("Delete", (type, event) -> {
            if(type == ClickType.CLICKED)
                this.selectedType = EmptyShape.class;

        }).build());

        secondRow.add(new ButtonBuilder("Undo", (type, event) -> {
            if(type == ClickType.CLICKED) {
                handleUndo();
                frame.repaint();
            }
        }).build());

        secondRow.add(new ButtonBuilder("Color", (type, event) -> {
            if(type == ClickType.CLICKED) {
                this.currentColor = JColorChooser.showDialog(null, "Choose a Color", this.currentColor);
            }
        }).build());

        // Third Row of buttons
        JPanel thirdRow = new JPanel();
        thirdRow.setLayout(new BoxLayout(thirdRow, BoxLayout.X_AXIS));

        thirdRow.add(new ButtonBuilder("Clear", (type, event) -> {
            if(type == ClickType.CLICKED) {
                this.shapes.clear();
                frame.repaint();
            }
        }).build());

        // Bottom
        JPanel canvas = new JPanel() {
            @Override
            public void paint(Graphics g) {
                super.paint(g);
                shapes.forEach(shape -> shape.draw(g));
            }
        };
        canvas.setPreferredSize(new Dimension(1000, 500));

        canvas.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                handleCircle(e);
                handleRectangle(e);
                handleDelete(e);
                frame.repaint();
            }

            @Override
            public void mousePressed(MouseEvent e) {
                startX = e.getX();
                startY = e.getY();
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                endX = e.getX();
                endY = e.getY();
                handleRectangle(e);
                handleCircle(e);
                startX = 0;
                startY = 0;
                endX = 0;
                endY = 0;
                frame.repaint();
            }

            @Override public void mouseEntered(MouseEvent e) {}
            @Override public void mouseExited(MouseEvent e) {}
        });

        holder.add(topRow);
        holder.add(secondRow);
        holder.add(thirdRow);

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
//        this.shapes.add(new Circle(event.getX(), event.getY(), 50, Color.RED));
        int distance = (int) Math.sqrt(Math.pow((this.startY - endY), 2) + Math.pow((this.startX - endX), 2));
        this.shapes.add(new Circle(this.startX, this.startY, distance, this.currentColor));
    }

    private void handleRectangle(MouseEvent event) {
        // TODO - Make all directions work
        if(this.selectedType != Rectangle.class) return;
        int width = endX - startX;
        int height = endY - startY;
        this.shapes.add(new Rectangle(this.startX, this.startY, width, height, this.currentColor));
    }

    private void handleDelete(MouseEvent event) {
        if(this.selectedType != EmptyShape.class) return;
        Optional<Shape> shapeOptional = this.shapes.stream().filter(shape -> shape.isOn(event.getX(), event.getY())).findFirst();
        if(shapeOptional.isPresent()) {
            Shape clickedShape = shapeOptional.get();
            this.lastDeleted = clickedShape;
            this.shapes.remove(clickedShape);
        }
    }

    private void handleUndo() {
        if(this.lastDeleted == null) return;
        System.out.println(this.lastDeleted);
        this.shapes.add(this.lastDeleted);
        this.lastDeleted = null;
    }
}