package me.tyler.editor;

import me.tyler.editor.shape.Shape;
import me.tyler.editor.shape.impl.*;
import me.tyler.editor.shape.impl.Rectangle;
import me.tyler.editor.shape.impl.pen.PenLocation;
import me.tyler.editor.shape.impl.pen.PenShape;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class GraphicsEditor {

    private final List<Shape> shapes;
    private final JTextField textField, sizeField;
    private Shape beingCreated, beingMoved;
    private Type selectedType;
    private Shape lastDeleted;
    private PenShape currentPen;
    private LineShape currentLine;

    private int startX, startY, endX, endY;
    private int moveStartX, moveStartY;
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

       topRow.add(new ButtonBuilder("Text", (type, event) -> {
           if(type == ClickType.CLICKED) {
               this.selectedType = TextShape.class;
           }
       }).build());

       topRow.add(new ButtonBuilder("Pen", (type, event) -> {
           if(type == ClickType.CLICKED) {
               this.selectedType = PenShape.class;
           }
       }).build());

       topRow.add(new ButtonBuilder("Line", (type, event) -> {
           if(type == ClickType.CLICKED) {
               this.selectedType = LineShape.class;
           }
       }).build());

       // Second Row of buttons
        JPanel secondRow = new JPanel();
        secondRow.setLayout(new BoxLayout(secondRow, BoxLayout.X_AXIS));

        secondRow.add(new ButtonBuilder("Delete", (type, event) -> {
            if(type == ClickType.CLICKED)
                this.selectedType = EmptyShape.class;

        }).build());

        secondRow.add(new ButtonBuilder("Color", (type, event) -> {
            if(type == ClickType.CLICKED) {
                this.currentColor = JColorChooser.showDialog(null, "Choose a Color", this.currentColor);
            }
        }).build());

        this.textField = new JTextField();
        this.textField.setPreferredSize(new Dimension(100, 25));
        this.textField.setMaximumSize(this.textField.getPreferredSize());
        this.textField.setText("Text Content");
        secondRow.add(this.textField);

        this.sizeField = new JTextField();
        this.sizeField.setPreferredSize(new Dimension(50, 25));
        this.sizeField.setMaximumSize(this.sizeField.getPreferredSize());
        this.sizeField.setText("15");
        secondRow.add(this.sizeField);

        // Third Row of buttons
        JPanel thirdRow = new JPanel();
        thirdRow.setLayout(new BoxLayout(thirdRow, BoxLayout.X_AXIS));

        thirdRow.add(new ButtonBuilder("Clear", (type, event) -> {
            if(type == ClickType.CLICKED) {
                this.shapes.clear();
                frame.repaint();
            }
        }).build());

        thirdRow.add(new ButtonBuilder("Undo", (type, event) -> {
            if(type == ClickType.CLICKED) {
                handleUndo();
                frame.repaint();
            }
        }).build());

        thirdRow.add(new ButtonBuilder("Move", (type, event) -> {
            if(type == ClickType.CLICKED) {
                this.selectedType = MoveShape.class;
            }
        }).build());

        // Bottom
        JPanel canvas = new JPanel() {
            @Override
            public void paint(Graphics g) {
                super.paint(g);
                shapes.forEach(shape -> shape.draw(g));
                if(beingCreated != null) beingCreated.draw(g);
                if(currentLine != null) currentLine.draw(g);
            }
        };
        canvas.setPreferredSize(new Dimension(1000, 500));

        canvas.addMouseMotionListener(new MouseMotionListener() {
            @Override
            public void mouseDragged(MouseEvent e) {
                handleShapeMove(e);
                handlePenDrag(e);
                handleLineDrag(e);
                if(beingCreated != null)
                    handleShapeCreation(e);
                frame.repaint();
            }

            @Override public void mouseMoved(MouseEvent e) {}
        });

        canvas.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                handleDelete(e);
                handleText(e);
                frame.repaint();
            }

            @Override
            public void mousePressed(MouseEvent e) {
                startX = e.getX();
                startY = e.getY();
                handleShapeCreation(e);
                handleShapeMoveStart(e);
                handlePenPress(e);
                handleLinePress(e);
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                endX = e.getX();
                endY = e.getY();
                handleRectangle(e);
                handleCircle(e);
                handlePenRelease(e);
                handleLineRelease(e);
                startX = 0;
                startY = 0;
                endX = 0;
                endY = 0;
                frame.repaint();
                beingCreated = null;
                beingMoved = null;
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
        int distance = (int) Math.sqrt(Math.pow((this.startY - endY), 2) + Math.pow((this.startX - endX), 2));
        this.shapes.add(new Circle(this.startX, this.startY, distance, this.currentColor));
    }

    private void handleLinePress(MouseEvent event) {
        if(this.selectedType != LineShape.class) return;
        this.currentLine = new LineShape(event.getX(), event.getY(), event.getX(), event.getY(), this.currentColor);
    }

    private void handleLineDrag(MouseEvent event) {
        if(this.selectedType != LineShape.class) return;
        if(this.currentLine == null) return;
        this.currentLine.setEndX(event.getX());
        this.currentLine.setEndY(event.getY());
    }

    private void handleLineRelease(MouseEvent event) {
        if(this.selectedType != LineShape.class) return;
        if(this.currentLine == null) return;
        this.shapes.add(this.currentLine);
        this.currentLine = null;
    }

    private void handlePenPress(MouseEvent event) {
        if(this.selectedType != PenShape.class) return;

        if(currentPen == null) {
            PenShape pen = new PenShape(event.getPoint(), this.currentColor);
            this.shapes.add(pen);
            this.currentPen = pen;
        }
    }

    private void handlePenDrag(MouseEvent event) {
        if(this.selectedType != PenShape.class) return;
        if(currentPen == null) return;
        currentPen.getPixels().add(
                new PenLocation(event.getX(), event.getY())
        );
    }

    private void handlePenRelease(MouseEvent event) {
        if(currentPen == null) return;
        currentPen = null;
    }

    private void handleShapeCreation(MouseEvent event) {
        if(this.selectedType == Circle.class) {
            int distance = (int) Math.sqrt(Math.pow((this.startY - event.getY()), 2) + Math.pow((this.startX - event.getX()), 2));
            this.beingCreated = new Circle(this.startX, this.startY, distance, this.currentColor, false);
        } else if(this.selectedType == Rectangle.class) {
            boolean flippedX = false, flippedY = false;

            int ogX = event.getX();
            int ogY = event.getY();
            if(ogX < this.startX) {
                int tempStartX = this.startX;
                this.startX = ogX;
                ogX = tempStartX;
                flippedX = true;
            }

            if(ogY < this.startY) {
                int tempStartY = this.startY;
                this.startY = ogY;
                ogY = tempStartY;
                flippedY = true;
            }

            int width = ogX - startX;
            int height = ogY - startY;
            this.beingCreated = new Rectangle(this.startX, this.startY, width, height, this.currentColor, false);

            if(flippedX)
                this.startX = ogX;
            if(flippedY)
                this.startY = ogY;
        }
    }

    private void handleRectangle(MouseEvent event) {
        if(this.selectedType != Rectangle.class) return;
        if(this.endX < this.startX) {
            int tempStartX = this.startX;
            this.startX = this.endX;
            this.endX = tempStartX;
        }

        if(this.endY < this.startY) {
            int tempStartY = this.startY;
            this.startY = this.endY;
            this.endY = tempStartY;
        }

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

    private void handleShapeMoveStart(MouseEvent event) {
        if(this.selectedType != MoveShape.class) return;
        Optional<Shape> shapeOptional = this.shapes.stream().filter(shape -> shape.isOn(event.getX(), event.getY())).findFirst();
        if(shapeOptional.isPresent()) {
            this.beingMoved = shapeOptional.get();
            this.moveStartX = event.getX();
            this.moveStartY = event.getY();
        }
    }

    private void handleText(MouseEvent event) {
        if(this.selectedType != TextShape.class) return;
        int fontSize = Integer.parseInt(this.sizeField.getText());
        this.shapes.add(new TextShape(this.textField.getText(), fontSize, event.getX(), event.getY(), this.currentColor));
    }

    private void handleShapeMove(MouseEvent event) {
        if(this.selectedType != MoveShape.class) return;
        if(this.beingMoved == null) return;

        int distanceX = this.moveStartX - event.getX();
        int distanceY = this.moveStartY - event.getY();
        this.beingMoved.setX(this.beingMoved.getX() - distanceX);
        this.beingMoved.setY(this.beingMoved.getY() - distanceY);

        this.moveStartX = event.getX();
        this.moveStartY = event.getY();
    }

    private void handleUndo() {
        if(this.lastDeleted == null) return;
        this.shapes.add(this.lastDeleted);
        this.lastDeleted = null;
    }
}