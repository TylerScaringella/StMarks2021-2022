package me.tyler.encoding;

import java.io.*;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Encoding {

    private final Map<Character, String> codeMap;
    private final Map<String, Character> charMap;
    private final Map<Character, Integer> freqMap;

    private final File codesPath, readPath, compressedPath, decompressedPath;


    public Encoding(String readPath) throws IOException {
        // Initialize our maps
        this.charMap = new HashMap<>();
        this.freqMap = new HashMap<>();
        this.codeMap = new HashMap<>();


        this.readPath = new File(readPath);
        readPath = readPath.replace(".txt", "");

        this.codesPath = new File(readPath + "-codes.txt");
        this.compressedPath = new File(readPath + "-compressed.txt");
        this.decompressedPath = new File(readPath + "-decompressed.txt");

        this.checkExisting(this.codesPath);
        this.checkExisting(this.compressedPath);
        this.checkExisting(this.decompressedPath);

        // Read the file "read.txt" character by character
        try{
            FileReader reader = new FileReader(this.readPath);
            int charNum = -1;
            while((charNum = reader.read()) != -1) {
                char readChar = (char) charNum;
                // We want to keep track of the frequency of every character, so we have a map of a character and the amount of times it has been seen in the file
                int freq = this.freqMap.getOrDefault(readChar, 0);
                freq++;
                this.freqMap.put(readChar, freq);
            }
            reader.close();
        }catch(IOException ex) {
            ex.printStackTrace();
        }

        PriorityQueue<Branch<Character>> queue = new PriorityQueue<>();
        // Go through the frequency map that we have adding to a priority queue with branches with a priority based on the frequency of the character
        this.freqMap.forEach((character, freq) -> queue.add(new Branch(character), freq));

        // We want to turn the priority queue into a binary tree, so we only want there to be 1 entry
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

        // Initiate the codes of characters in our binary tree
        this.buildCode("", queue.pop());

        this.writeCodes();
        handleWriting();
        handleReading();
    }

    public void checkExisting(File file) throws IOException {
        if(!file.exists())
            file.createNewFile();
    }

    public void buildCode(String currentCode, Branch<Character> currentBranch) {
        // If a branch is a leaf then we know that there are no more children
        if(currentBranch.isLeaf()) {
            this.codeMap.put(currentBranch.getInfo(), currentCode);
        }else {
            // When a branch has children, the branch's children may have children so we want to get the values of the children
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
                    if(((int) character) <= 31 || ((int) character) == 127) {
                        // This will write integers in addition to characters if the character provided is an empty character that way we can read it properly
                        writer.write(character + "" + (int)character + ":code:" + code + "\n");
                    } else {
                        writer.write(character + ":code:" + code + "\n");
                    }
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
        BufferedBitWriter writer = new BufferedBitWriter(this.compressedPath.getAbsolutePath());
        try{
            FileReader reader = new FileReader(this.readPath);
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
        FileWriter writer = new FileWriter(this.decompressedPath);

        try {
            BufferedReader reader = new BufferedReader(new FileReader(this.codesPath));

            String line;

            while((line = reader.readLine()) != null) {
                // This right here is reversing our writing for codes.txt |
                // If the line is empty, then we know that the character is empty, meaning we are going to have the get the character value of the integer provided
                if(line.isEmpty()) {
                    String nextLine = reader.readLine();
                    String[] split = nextLine.split(":code:");
                    char character = (char) Integer.parseInt(split[0]);
                    String code = split[1];
                    this.charMap.put(code, character);
                    continue;
                }

                String[] split = line.split(":code:");
                char character = split[0].charAt(0);
                String code = split[1];
                this.charMap.put(code, character);
            }
        } catch(IOException ex) {
            ex.printStackTrace();
        }

        BufferedBitReader reader = new BufferedBitReader(this.compressedPath.getAbsolutePath());

        String cur = "";

        // This will go through reading each and every character we have stored in our map and constantly check if the group of bits is stored anywhere in our map, and if it is, adding that character
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
