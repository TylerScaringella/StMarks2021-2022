package me.tyler.bitcoin;

import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class Bitcoin {

    private final FileWriter writer;
    private final List<Boolean> bitCache;
//    private final List<String> letters;
//    private final Map<String, String> bitDict;
//
//    private int curLetterIndx;

    public static void main(String[] args) throws IOException {
        Bitcoin testing = new Bitcoin();

        for(int i=0; i<204; i++) {
            int x = ThreadLocalRandom.current().nextInt(0, 2);
                if(x == 0)
                    testing.write(false);
                else
                    testing.write(true);
        }

        testing.close();
    }

    public Bitcoin() throws IOException {
        this.writer = new FileWriter("C:\\Users\\tjsca\\Documents\\cs\\big challenge\\bitcoin.txt");
        this.bitCache = new ArrayList<>();
//        this.bitDict = new HashMap<>();
//        this.letters = Arrays.asList("A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z");
//        this.curLetterIndx = 0;
    }

    private void updateBitCache() throws IOException {
        while(this.bitCache.size() >= 8) {
            String bit = "";

            for(int i=0; i<8; i++) {
                bit += (this.bitCache.get(i) ? "1" : 0);
            }

            for(int i=0; i<8; i++) {
                this.bitCache.remove(0);
            }

            int output = Integer.parseInt(bit, 2);
            char bitChar = (char) output;
            System.out.println("Input: " + bit + " | Output: " + output + " | Char: " + bitChar + " | Debug: " + (int) bitChar);
            this.writer.write(bitChar);
        }
    }

//    private void updateBitCache() throws IOException {
//        while(this.bitCache.size() >= 8) {
//            String bit = "";
//
//            for(int i=0; i<8; i++) {
//                bit += (this.bitCache.get(i) ? "1" : 0);
//            }
//
//            for(int i=0; i<8; i++) {
//                this.bitCache.remove(0);
//            }
//
//            System.out.println("Updating Bit Cache | Bit: " + bit);
//
//            if(!this.bitDict.containsKey(bit)) {
//                String letter = this.letters.get(this.curLetterIndx);
//                this.curLetterIndx++;
//                this.bitDict.put(bit, letter);
//            }
//
//            System.out.println("Writing " + this.bitDict.get(bit));
//            this.writer.write(this.bitDict.get(bit));
//        }
//    }

    public void write(boolean b) throws IOException {
        this.bitCache.add(b);
        updateBitCache();
    }

    public void close() throws IOException {
        this.writer.write("|" + this.bitCache.size());
        String bit = "";
        for(int i=0; i<this.bitCache.size(); i++) {
            bit += (this.bitCache.get(i) ? "1" : 0);
        }

        int finalOutput = Integer.parseInt(bit, 2);
        char finalChar = (char) finalOutput;
        System.out.println("Remaining: " + this.bitCache.size() + " | Bit: " + bit + " | Output: " + finalOutput + " | Char: " + finalChar);
        this.writer.write(finalChar);
        this.writer.close();
    }
}
