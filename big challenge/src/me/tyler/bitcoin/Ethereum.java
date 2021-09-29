package me.tyler.bitcoin;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Ethereum {

    private final String filePath = "C:\\Users\\tjsca\\Documents\\cs\\big challenge\\bitcoin.txt";
    private final List<Character> characters;
    private int remainingSize = 0;
    private char remainingChar = 0;

    public static void main(String[] args) {
        new Ethereum();
    }

    public Ethereum() {
        this.characters = new ArrayList<>();

        try {
            FileReader reader = new FileReader(this.filePath);

            int reading = 0;

            while((reading = reader.read()) != -1) {
                char readingChar = (char) reading;
                this.characters.add(readingChar);
            }
        } catch(IOException ex) {
            ex.printStackTrace();
        }

        for(int i=0; i<this.characters.size(); i++) {
            char currentChar = this.characters.get(i);
            if((int) currentChar == 124) {
                remainingSize = Integer.parseInt(String.valueOf(this.characters.get(i+1)));
                remainingChar = this.characters.get(i+2);
                break;
            }
        }

        doSmartStuff();
    }

    private void doSmartStuff() {
        for(int i=0; i<this.characters.size(); i++) {
            char currentChar = this.characters.get(i);
            int charValue = currentChar;
            String binaryString = Integer.toBinaryString(charValue);
            System.out.println("Char: " + currentChar + " | Value: " + charValue + " | Binary: " + binaryString);
        }
    }
}
