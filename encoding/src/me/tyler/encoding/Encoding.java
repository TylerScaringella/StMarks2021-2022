package me.tyler.encoding;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class Encoding {

    private final Map<Character, String> codeMap;
    private final String codesPath = "C:\\Users\\tjsca\\Documents\\cs\\encoding\\codes.txt";
    private final Map<String, Character> charMap;
    private final Map<Character, Integer> freqMap;


    public static void main(String[] args) throws IOException {
        new Encoding();
    }

    public Encoding() throws IOException {
        // Initialize our maps
        this.charMap = new HashMap<>();
        this.freqMap = new HashMap<>();
        this.codeMap = new HashMap<>();

        // Read the file "read.txt" character by character
        try{
            FileReader reader = new FileReader("C:\\Users\\tjsca\\Documents\\cs\\encoding\\read.txt");
            int charNum = -1;
            while((charNum = reader.read()) != -1) {
                char readChar = (char) charNum;
                int freq = this.freqMap.getOrDefault(readChar, 0);
                freq++;
                this.freqMap.put(readChar, freq);
            }
            reader.close();
        }catch(IOException ex) {
            ex.printStackTrace();
        }

        PriorityQueue<Branch<Character>> queue = new PriorityQueue<>();
        this.freqMap.forEach((character, freq) -> queue.add(new Branch(character), freq));

        while(queue.size() > 1) {
            if(queue.size() >= 2) {
                // Branch with children
                PriorityQueue.PriorityData leftData = queue.popData();
                PriorityQueue.PriorityData rightData = queue.popData();

                // These are leaves
                Branch leftBranch = (Branch) leftData.getData();
                Branch rightBranch = (Branch) rightData.getData();

                Branch parentBranch = new Branch(leftBranch, rightBranch);
                int totalPriority = (leftData.getPriority() + rightData.getPriority());
                queue.add(parentBranch, totalPriority);
            }else {
                // Branch with leaf
                PriorityQueue.PriorityData data = queue.popData();
                Branch branch = new Branch(data.getData());
                queue.add(branch, data.getPriority());
            }
        }

        this.buildCode("", queue.pop());

        this.writeCodes();
        handleWriting();
        handleReading();
    }

    public void buildCode(String currentCode, Branch<Character> currentBranch) {
        if(currentBranch.isLeaf()) {
            this.codeMap.put(currentBranch.getInfo(), currentCode);
        }else {
            this.buildCode(currentCode + "0", currentBranch.getLeft());
            this.buildCode(currentCode + "1", currentBranch.getRight());
        }
    }

    private void writeCodes() {
        try {
            FileWriter writer = new FileWriter(this.codesPath);
            this.codeMap.forEach((character, code) -> {
                try {
                    // This will write our codes into our codes.txt file in the format "(char):code:(code)"
                    writer.write(character + ":code:" + code + "\n");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            writer.close();
        }catch(IOException ex) {
            ex.printStackTrace();
        }
    }

    private void handleWriting() throws IOException {
        BufferedBitWriter writer = new BufferedBitWriter("C:\\Users\\tjsca\\Documents\\cs\\encoding\\compress.txt");
        try{
            FileReader reader = new FileReader("C:\\Users\\tjsca\\Documents\\cs\\encoding\\read.txt");
            int charNum = -1;
            while((charNum = reader.read()) != -1) {
                char readChar = (char) charNum;
                // This will access the code/bits that we have stored from our binary tree
                String bits = this.codeMap.get(readChar);
                // Splitting this will allow us to iterate through each individual bit
                String[] bitSplit = bits.split("");
                for(String bit : bitSplit) {
                    writer.writeBit(bit.equalsIgnoreCase("1"));
                }
            }
        }catch(IOException ex) {
            ex.printStackTrace();
        }

        writer.close();
    }

    private void handleReading() throws IOException {
        FileWriter writer = new FileWriter("C:\\Users\\tjsca\\Documents\\cs\\encoding\\decompress.txt");

        try {
            BufferedReader reader = new BufferedReader(new FileReader(this.codesPath));

            String line;

            while((line = reader.readLine()) != null) {
                // This right here is reversing our writing for codes.txt |
                String[] split = line.split(":code:");
                char character = split[0].toCharArray()[0];
                String code = split[1];
                this.charMap.put(code, character);
            }
        } catch(IOException ex) {
            ex.printStackTrace();
        }

        BufferedBitReader reader = new BufferedBitReader("C:\\Users\\tjsca\\Documents\\cs\\encoding\\compress.txt");

        String cur = "";

        while(reader.hasNext()) {
            boolean bit = reader.readBit();
            cur += (bit ? "1" : "0");

            if(this.charMap.containsKey(cur)) {
                writer.write(this.charMap.get(cur));
                cur = "";
            }
        }

        writer.close();
    }
}
