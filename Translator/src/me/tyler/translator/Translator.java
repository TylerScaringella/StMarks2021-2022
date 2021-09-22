package me.tyler.translator;

import com.sun.deploy.util.StringUtils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Translator {

    private Map<String, String> wordMap;
    private Scanner scanner;

    public static void main(String[] args) throws IOException {
        new Translator().startTranslator();
    }

    private void startTranslator() throws IOException {
        this.wordMap = new HashMap<>();
        this.scanner = new Scanner(System.in);

        loadWords();

        while(true) {
            System.out.println("What would you like to be translated to Arabic from English?");
            String lineToTranslate = scanner.nextLine();
            String[] wordsToTranslate = lineToTranslate.split(" ");
            List<String> translated = new ArrayList<>();
            for(String word : wordsToTranslate) {
                word = word.replace("_", " ");
                translated.add(wordMap.get(word.toLowerCase()));
            }

            System.out.println(StringUtils.join(translated, " ") + "\n\n");
        }
    }

    private void loadWords() throws IOException {
        FileReader input = new FileReader("C:/Users/tjsca/Downloads/EnglishToArabicDictionary.txt");
        BufferedReader reader = new BufferedReader(input);

        reader.readLine();

        String english, arabic;
        while((english = reader.readLine()) != null) {
            arabic = reader.readLine();

            wordMap.put(english, arabic);
        }
    }
}
