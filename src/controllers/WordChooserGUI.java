package controllers;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

public class WordChooserGUI extends JFrame {

    private Dictionary dictionary;
    private String chosenWord;
    private final Object lock = new Object(); // Object for synchronization
    private CountDownLatch latch = new CountDownLatch(1);
    public static int maxAttempts;
    public int countDown;

    public WordChooserGUI(Dictionary dictionary) {
        this.dictionary = dictionary;

        setTitle("Word Chooser");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(300, 140); // Increased height for additional components
        setLocationRelativeTo(null);

        JLabel labelLength = new JLabel("Choisir la taille du mot:");
        JComboBox<Integer> lengthComboBox = new JComboBox<>(getLengthOptions());

        // New JLabel and JComboBox for difficulty level
        JLabel labelDifficulty = new JLabel("Choisir le niveau de difficulté:");
        String[] difficultyOptions = {"Facile", "Moyen", "Difficile"};
        JComboBox<String> difficultyComboBox = new JComboBox<>(difficultyOptions);

        JButton chooseButton = new JButton("Choisir");
        JLabel resultLabel = new JLabel();

        chooseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedLength = (int) lengthComboBox.getSelectedItem();
                chosenWord = dictionary.chooseWord(selectedLength);

                // Set the selected difficulty level
                String selectedDifficulty = (String) difficultyComboBox.getSelectedItem();
                switch (selectedDifficulty) {
                    case "Facile":
                        countDown = 180;
                        maxAttempts = 6;
                        break;
                    case "Moyen":
                        countDown = 120;
                        maxAttempts = 4;
                        break;
                    case "Difficile":
                        countDown = 60;
                        maxAttempts = 3;
                        break;
                }

                resultLabel.setText(chosenWord != null ? "Chosen word: " + chosenWord : "No matching word.");

                dispose();
                // Synchronize and notify waiting threads
                synchronized (lock) {
                    lock.notifyAll();
                }
                latch.countDown();
            }
        });

        JPanel panel = new JPanel();
        panel.add(labelLength);
        panel.add(lengthComboBox);

        // Add the new components for difficulty level
        panel.add(labelDifficulty);
        panel.add(difficultyComboBox);

        panel.add(chooseButton);
        add(panel);
    }

    private DefaultComboBoxModel<Integer> getLengthOptions() {
        return new DefaultComboBoxModel<>(dictionary.getLengthOptions());
    }

    public String getChosenWord() {
        // Synchronize and wait until a word is chosen
        synchronized (lock) {
            try {
                lock.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        return chosenWord;
    }

    public int getMaxAttempts() {
        return maxAttempts;
    }
    public int getTime(){
        return countDown;
    }

    public static void main(String[] args) {
        for (int i = 0; i < 3; i++) {
            String filepath = "utils/Doc.txt";
            List<String> list = new ArrayList<>();
            WordChooserGUI choose = new WordChooserGUI(new Dictionary(list, filepath));

            choose.setVisible(true);
            String chosenWord = choose.getChosenWord();
            System.out.println("Chosen word: " + chosenWord + " Difficulty: "
                    + " Countdown: " + choose.countDown + " Max Attempts: " + choose.getMaxAttempts());
        }
    }
}
