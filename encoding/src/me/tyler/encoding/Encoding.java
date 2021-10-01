package me.tyler.encoding;

import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Encoding {

    private final Map<Character, Integer> freqMap;

    public static void main(String[] args) {
        new Encoding();
    }

    {
        this.freqMap = new HashMap<>();
        try{
            FileReader reader = new FileReader("C:\\Users\\tjsca\\Documents\\cs\\encoding\\read.txt");
            int charNum = -1;
            while((charNum = reader.read()) != -1) {
                char readChar = (char) charNum;
                int freq = this.freqMap.getOrDefault(readChar, 0);
                freq++;
                this.freqMap.put(readChar, freq);
            }
        }catch(IOException ex) {
            ex.printStackTrace();
        }

        System.out.println("Frequency Map:");
        this.freqMap.forEach((key, value) -> System.out.println("Key: " + key + " | Value: " + value));
    }
}
