package me.tyler.chatbot;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.concurrent.ThreadLocalRandom;

public class ChatBot {

    private String[] responses = new String[]{
            "Hello, how are you?",
            "I'm not reading all of that, so I'm either happy for you, or sad for your loss.",
            "Have a nice day.",
            "Cool.",
            "I'm great."
    };

    public static void main(String[] args) {
        new ChatBot();
    }

    public ChatBot() {
        JFrame frame = new JFrame();
        frame.setSize(500, 500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);

        JPanel container = new JPanel();
        container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));

        JTextArea chatArea = new JTextArea();
        chatArea.setText("Welcome to the AI Chat Bot");
        chatArea.setBorder(BorderFactory.createTitledBorder("AI Chat Bot"));
        chatArea.setPreferredSize(new Dimension(250, 250));
        chatArea.setEditable(false);

        JTextArea inputArea = new JTextArea();
        inputArea.setBorder(BorderFactory.createTitledBorder("Input"));
        inputArea.setPreferredSize(new Dimension(150, 150));

        inputArea.addKeyListener(new KeyListener() {
            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode() == 10) {
                    submitChat(inputArea, chatArea);
                }
            }
            @Override public void keyReleased(KeyEvent e) {}
            @Override public void keyTyped(KeyEvent e) {}

        });

        JButton button = new JButton("Send");

        button.addActionListener(e -> submitChat(inputArea, chatArea));

        container.add(chatArea);
        container.add(inputArea);
        container.add(button);

        frame.add(container);
        frame.setVisible(true);
    }

    private String pickResponse() {
        int response = ThreadLocalRandom.current().nextInt(0, this.responses.length);
        return responses[response];
    }

    private void submitChat(JTextArea inputArea, JTextArea chatArea) {
        if(inputArea.getText().length() < 1) return;

        String userResponse = "You: " + inputArea.getText() + "\n";
        inputArea.setText("");
        String newResponse = userResponse + "User3923: " + this.pickResponse();
        chatArea.setText(chatArea.getText() + "\n" + newResponse);
    }
}
