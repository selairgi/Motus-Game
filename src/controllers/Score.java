package controllers;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;
import java.io.File;


public class Score {
    private String playerName;
    private int score;
    private Date date;

    // Chemin du fichier texte pour stocker les scores
    private static final String SCORE_FILE_PATH = "../scores";

    public Score(String playerName) {
        this.playerName = playerName;
        this.score = 0;
        this.date = new Date();
        createScoreFileIfNotExists();
    }

    public String getPlayerName() {
        return playerName;
    }

    public int getScore() {
        return score;
    }

    public Date getDate() {
        return date;
    }
    private static void createScoreFileIfNotExists() {
        try {
            File scoreFile = new File(SCORE_FILE_PATH);

            if (!scoreFile.exists()) {
                scoreFile.createNewFile();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void incrementScore() {
        int existingScore = getScoreFromPlayerName(playerName);
        score = existingScore + 1;
        writeScoresToFile(playerName, score); // Met à jour le fichier avec le nouveau score
    }
    

    public static int getScoreFromPlayerName(String playerName) {
        try (BufferedReader br = new BufferedReader(new FileReader(SCORE_FILE_PATH))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 2 && parts[0].equals(playerName)) {
                    return Integer.parseInt(parts[1]);
                }
            }
        } catch (IOException | NumberFormatException e) {
            e.printStackTrace();
        }
        return 0; // Retourne 0 si le joueur n'existe pas dans le fichier
    }

    private void writeScoresToFile(String playerName, int updatedScore) {
        boolean playerExists = false;
    
        try (BufferedReader br = new BufferedReader(new FileReader(SCORE_FILE_PATH))) {
            StringBuilder fileContent = new StringBuilder();
    
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 2 && parts[0].equals(playerName)) {
                    playerExists = true;
                    line = playerName + "," + updatedScore; // Met à jour le score du joueur
                }
                fileContent.append(line).append("\n");
            }
    
            if (!playerExists) {
                fileContent.append(playerName).append(",").append(updatedScore).append("\n");
            }
    
            try (BufferedWriter bw = new BufferedWriter(new FileWriter(SCORE_FILE_PATH))) {
                bw.write(fileContent.toString());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    private static void displayScore(Score score) {
        System.out.println("Player: " + score.getPlayerName() + ", Score: " + score.getScore());
    }
    public static void main(String[] args) {
        Score score1 = new Score("Player1");

        displayScore(score1);

        score1.incrementScore();

        displayScore(score1);
    }
}
