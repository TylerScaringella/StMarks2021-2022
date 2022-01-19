package me.tyler.graph;

public class GraphTest {

    public static void main(String[] args) {
        final Graph<String> testing = new Graph<>();
        String tyler = "Tyler";
        String kanav = "Kanav";
        String friedman = "Friedman";
        String testingStr = "Testing12";
        String abc = "abfjw";

        testing.add(tyler);
        testing.add(kanav);
        testing.add(friedman);
        testing.add(testingStr);
        testing.add(abc);

        testing.connect(tyler, kanav);
        testing.connect(friedman, kanav);
        testing.connect(friedman, testingStr);
        testing.connect(testingStr, abc);

        testing.debug();

        System.out.println(testing.path(tyler, abc).toString());
        testing.save();


        final Graph<String> fromFile = new Graph<>();
        fromFile.fromFile("C:\\users\\tjsca\\graph.txt");
        fromFile.debug();
    }
}
