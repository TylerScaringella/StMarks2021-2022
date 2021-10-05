package me.tyler.encoding;

import com.sun.xml.internal.ws.api.model.wsdl.WSDLOutput;

import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Encoding {

    private final Map<Character, Integer> freqMap;
    private final Map<Character, String> codeMap;

    public static void main(String[] args) {
        new Encoding();
    }

    {
        this.freqMap = new HashMap<>();
        this.codeMap = new HashMap<>();
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

        PriorityQueue<Branch<Character>> queue = new PriorityQueue<>();
        this.freqMap.forEach((character, freq) -> queue.add(new Branch(character), freq));

        while(queue.size() > 1) {
            if(queue.size() >= 2) {
                // Branch with children
                PriorityQueue.PriorityData leftData = queue.popData();
                PriorityQueue.PriorityData rightData = queue.popData();

                // These are leaves
                Branch leftBranch = new Branch(leftData.getData());
                Branch rightBranch = new Branch(rightData.getData());

                Brasnch parentBranch = new Branch(leftBranch, rightBranch);
                int totalPriority = (leftData.getPriority() + rightData.getPriority());
                queue.add(parentBranch, totalPriority);
            }else {
                // Branch with leaf
                PriorityQueue.PriorityData data = queue.popData();
                Branch branch = new Branch(data.getData());
                queue.add(branch, data.getPriority());
            }
        }

        System.out.println(queue.size());
        System.out.println(queue.toString());

        this.buildCode("", queue.pop());
//        this.codeMap.forEach((key, value) -> {
//            System.out.println("Key: " + key + " | Value: " + value);
//        });
    }

    public void buildCode(String currentCode, Branch<Character> currentBranch) {
        if(currentBranch.isLeaf()) {
            System.out.println(currentBranch.getInfo());
            this.codeMap.put(currentBranch.getInfo(), currentCode);
        }else {
            this.buildCode(currentCode + "0", currentBranch.getLeft());
            this.buildCode(currentCode + "1", currentBranch.getRight());
        }
    }
}
