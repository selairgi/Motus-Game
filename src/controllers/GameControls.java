package controllers;

import java.util.Random;


public class GameControls{
    public String wordToGuess;
    private int index1;
    private int index2;
    


    public GameControls(String word){
        this.wordToGuess = word;
        Random random = new Random();
        index1 = 0;
        do {
            index2 = random.nextInt(wordToGuess.length());
        } while (index2 == index1);
    }

    public String getMaskedWord() {
        int wordLength = wordToGuess.length();
        StringBuilder row = new StringBuilder();
    
        for (int j = 0; j < wordLength; j++) {
                if ((j == index1 || j == index2)) {
                    // Display the letters at the random indices or reveal on the first attempt
                    row.append(wordToGuess.charAt(j));
                } else {
                    row.append(" ");
                }
            }    
        
        return row.toString();
    }

    public static void main(String args[]){
        GameControls gg = new GameControls("testparexemple");
        System.out.println(gg.getMaskedWord());
    }


}