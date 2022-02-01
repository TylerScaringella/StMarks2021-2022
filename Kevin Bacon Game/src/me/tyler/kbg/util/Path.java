package me.tyler.kbg.util;

public class Path {
    private String one, two;
    private String label;

    public Path(String one, String two, String label) {
        this.one = one;
        this.two = two;
        this.label = label;
    }

    public String getOne() {
        return one;
    }

    public String getTwo() {
        return two;
    }

    public String getLabel() {
        return label;
    }
}