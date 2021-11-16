package me.tyler.advent.util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AdventReader {

    private String location;

    public AdventReader(String name) {
        this.location = "C:\\Users\\tjsca\\Documents\\cs\\AdventOfCode\\" + name + ".txt";
    }

    public List<String> getLines() throws IOException {
        final List<String> lines = new ArrayList<>();

        FileReader fileReader = new FileReader(this.location);
        BufferedReader reader = new BufferedReader(fileReader);

        String line;
        while((line = reader.readLine()) != null) {
            lines.add(line);
        }

        return lines;
    }
}