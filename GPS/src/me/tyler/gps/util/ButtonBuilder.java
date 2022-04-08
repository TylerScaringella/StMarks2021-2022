package me.tyler.gps.util;

import me.tyler.gps.util.click.ClickType;

import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.function.BiConsumer;

public class ButtonBuilder {

    private final JButton button;
    private final BiConsumer<ClickType, MouseEvent> event;

    public ButtonBuilder(String text, BiConsumer<ClickType, MouseEvent> event) {
        this.button = new JButton(text);

        this.event = event;

        this.button.addMouseListener(new MouseListener() {

            @Override
            public void mouseClicked(MouseEvent e) {
                event.accept(ClickType.CLICKED, e);
            }

            @Override
            public void mousePressed(MouseEvent e) {
                event.accept(ClickType.PRESSED, e);
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                event.accept(ClickType.RELEASED, e);
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                event.accept(ClickType.ENTERED, e);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                event.accept(ClickType.EXITED, e);
            }
        });
    }

    public JButton build() {
        return this.button;
    }
}