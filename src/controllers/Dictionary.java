package controllers;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Dictionary {
    private String filepath;
    private List<String> dictionary;
    private Random random;

    public Dictionary(List<String> dictionary, String filepath) {
        this.filepath = filepath;
        this.dictionary = dictionary;
        this.random = new Random();
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(filepath), "UTF-8"));

            String line;
            while ((line = reader.readLine()) != null) {
                dictionary.add(line);
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Erreur lors du chargement du dictionnaire.");
        }
    }

    public String chooseWord(int length) {
        List<String> wordsByDifficulty = new ArrayList<>();
        for (String word : dictionary) {
            if (word.length() == length) {
                wordsByDifficulty.add(word);
            }
        }

        if (wordsByDifficulty.isEmpty()) {
            System.out.println("Aucun mot correspondant au nombre choisi.");
            return null;
        }

        int randomIndex = random.nextInt(wordsByDifficulty.size());
        return wordsByDifficulty.get(randomIndex).toUpperCase();
    }

    public Integer[] getLengthOptions() {

        Integer[] lengths = new Integer[9]; // Adjust the size based on your range
        for (int i = 7; i <= 15; i++) {
            lengths[i - 7] = i;
        }
        return lengths;
    }
    

    public static void main(String[] args) {
        String filepath = "utils/Doc.txt";
        List<String> list = new ArrayList<>();
        Dictionary dictionary = new Dictionary(list, filepath);

        String wordToGuess = dictionary.chooseWord(7);
        System.out.println(wordToGuess);
    }
}
