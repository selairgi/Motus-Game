package graphics;

import controllers.Dictionary;
import controllers.Score;
import controllers.GameControls;
import controllers.WordChooserGUI;
import controllers.Matrix;
import javax.swing.*;
import java.util.Timer;
import java.util.TimerTask;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.awt.Toolkit;
import java.util.Map;
import java.util.HashMap;
import javax.swing.ImageIcon;



public class MotusFrame extends JFrame {
    private String wordToGuess;
    public int maxAttempts;
    public static String playerName;
    public static Score scoreActuel;
    public String wordMasked;
    public GameControls gameInit;
    
    private static Timer timer;
    private int remainingAttempts;
    private JButton newGameLabel;
    private int wordLength;
    private Matrix userEntries;
    private JLabel timerLabel;
    private static int countdown;
    private static int is_finished;
    private JButton Quitter;
    private JLabel votreprop;
    private JLabel scoreLabel;
    private List<Score> scores;
    private static int win;
    private JLabel joueur;
    private JPanel gridPanel;
    private static Timer delayTimer;
    private JTextField inputField;
    private List<JLabel[]> letterRows; // Utiliser une liste pour stocker les lignes de lettres

    public MotusFrame(String word, int maxAttempts) {
        
        this.wordToGuess = word.toUpperCase();
        this.wordLength = wordToGuess.length();
        this.maxAttempts = maxAttempts;
        this.remainingAttempts = maxAttempts;
        //this.countdown = 10; // Temps en secondes pour le compte à rebours
        this.timer = new Timer();
        this.gameInit = new GameControls(wordToGuess);

        

        scores = new ArrayList<>();
        userEntries = new Matrix(maxAttempts, wordLength);
        letterRows = new ArrayList<>();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                countdown--;
                if (countdown <= 0) {
                    displayMessageEreur("Temps écoulé. Le jeu est terminé. Le mot était : " + wordToGuess);
                    timer.cancel();
                    endGame();
                } else {
                    updateTimerLabel();
                }
            }
        }, 1000, 1000); // Démarrage immédiat, mise à jour toutes les 1 seconde
        setupUI();
        startGame();
        
    }

    private void setupUI() {
        setTitle("Motus Game");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // setSize(900,1000); // Increased size to accommodate the legend
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        int screenWidth = toolkit.getScreenSize().width;
        int screenHeight = toolkit.getScreenSize().height;

        // Set the size based on screen resolution
        setSize(screenWidth*(3/2) , screenHeight );
        setLocationRelativeTo(null);
    
        joueur = new JLabel("Bonjour : " + playerName);
        joueur.setFont(new Font("Arial", Font.BOLD, 16));
        votreprop = new JLabel("Votre proposition :");
        votreprop.setFont(new Font("Arial", Font.BOLD, 15));
        
        scoreLabel = new JLabel("Nouveau score :");
        updateScoreLabel(scoreActuel.getScoreFromPlayerName(playerName));
        scoreLabel.setFont(new Font("Arial", Font.BOLD, 16));

        inputField = new JTextField();
        inputField.setFont(new Font("Arial", Font.BOLD, 20));
        inputField.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        inputField.setBackground(Color.white);
        inputField.setForeground(Color.darkGray);
        inputField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                makeGuess();
            }
        });

        newGameLabel = new JButton("Recommencer");
        newGameLabel.setHorizontalAlignment(SwingConstants.CENTER);
        newGameLabel.setFont(new Font("Arial", Font.BOLD, 16));
        newGameLabel.setPreferredSize(new Dimension(150, 50));
    
        // Add an action listener to the new JLabel
        newGameLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                startNewGame();
            }
        });
        Quitter = new JButton("Quitter");
        Quitter.setHorizontalAlignment(SwingConstants.CENTER);
        Quitter.setFont(new Font("Arial", Font.BOLD, 16));
        Quitter.setPreferredSize(new Dimension(150, 50));
    
        // Add an action listener to the new JLabel
        Quitter.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int result = JOptionPane.showConfirmDialog(
                    null,
                    "Voulez-vous vraiment quitter ?",
                    "Confirmation",
                    JOptionPane.YES_NO_OPTION
            );
        
                if (result == JOptionPane.YES_OPTION) {
                    System.exit(0);
                }
                else{
                    //bqa
                }
            }
        });
        
    
        gridPanel = new JPanel();
        gridPanel.setLayout(new BoxLayout(gridPanel, BoxLayout.Y_AXIS));


        createLegend();
        createGridRow(); // Adding the first row of buttons
    
        timerLabel = new JLabel("Temps restant: " + countdown + " secondes");
        timerLabel.setHorizontalAlignment(SwingConstants.CENTER);
        timerLabel.setFont(new Font("Arial", Font.BOLD, 20));

    
        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);

        JPanel legendPanel = new JPanel();
        legendPanel.setLayout(new FlowLayout());

        gridPanel.add(legendPanel);
    
        layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER)
                .addComponent(joueur)
                .addComponent(gridPanel)
                .addComponent(votreprop)
                .addComponent(inputField, GroupLayout.PREFERRED_SIZE, 300, GroupLayout.PREFERRED_SIZE)
                .addComponent(timerLabel)
                .addComponent(newGameLabel, GroupLayout.PREFERRED_SIZE, 300, GroupLayout.PREFERRED_SIZE)
                .addComponent(Quitter, GroupLayout.PREFERRED_SIZE, 300, GroupLayout.PREFERRED_SIZE)
                .addComponent(scoreLabel)
                
        ); 

        layout.setVerticalGroup(layout.createSequentialGroup()
                .addComponent(joueur)
                .addComponent(gridPanel)
                .addComponent(votreprop)
                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(inputField, GroupLayout.PREFERRED_SIZE, 50, GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(timerLabel)
                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(newGameLabel, GroupLayout.PREFERRED_SIZE, 50, GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(Quitter, GroupLayout.PREFERRED_SIZE, 50, GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(scoreLabel)
                
        );
    }
    private static void displayDecorationImage() {
        ImageIcon icon = new ImageIcon("utils/motus.jpg"); // Replace with the actual path to your image

        JDialog dialog = new JDialog(null, "Motus game", Dialog.ModalityType.APPLICATION_MODAL);
        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

        // Create a JPanel to hold the image and loading bar
        JPanel panel = new JPanel(new BorderLayout());

        // Add the image to the panel
        JLabel label = new JLabel(icon);
        panel.add(label, BorderLayout.CENTER);

        // Add the loading bar to the bottom of the panel
        JProgressBar loadingBar = new JProgressBar();
        loadingBar.setPreferredSize(new Dimension(300, 30));
        panel.add(loadingBar, BorderLayout.SOUTH);

        dialog.add(panel);

        // Schedule a timer to update the loading bar
        int totalDelay = 3000; // Total delay in milliseconds
        int updateInterval = 50; // Interval for updating the loading bar in milliseconds
        int steps = totalDelay / updateInterval;
        int progressStep = 100 / steps;

        delayTimer = new Timer();
        delayTimer.scheduleAtFixedRate(new TimerTask() {
            int progress = 0;

            @Override
            public void run() {
                loadingBar.setValue(progress);
                progress += progressStep;

                if (progress >= 100) {
                    delayTimer.cancel(); // Stop the timer
                    dialog.dispose();    // Close the dialog after completion
                }
            }
        }, 0, updateInterval);

        dialog.pack();
        dialog.setLocationRelativeTo(null);
        dialog.setVisible(true);
    }

    
    private Map<Character, Integer> countLetterOccurrences(String word) {
        Map<Character, Integer> letterCounts = new HashMap<>();
        for (char c : word.toCharArray()) {
            letterCounts.put(c, letterCounts.getOrDefault(c, 0) + 1);
        }
        return letterCounts;
    }
    private JPanel createColorSquare(Color color) {
        JPanel circle = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.setColor(color);
                g.fillOval(0, 0, getWidth(), getHeight());
            }
        };

        circle.setPreferredSize(new Dimension(40, 40));
        return circle;
    }

    private void createLegend() {
        JPanel legendPanel = new JPanel();
        legendPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
    
        JLabel correctPositionLabel = new JLabel("Position correcte");
        JLabel correctWrongPositionLabel = new JLabel("Lettre correct, Position incorrecte");
        JLabel incorrectLabel = new JLabel("Lettre incorrecte");
    
        correctPositionLabel.setFont(new Font("Times New Roman", Font.BOLD, 18));
        correctPositionLabel.setForeground(Color.GREEN);
    
        correctWrongPositionLabel.setFont(new Font("Times New Roman", Font.BOLD, 18));
        correctWrongPositionLabel.setForeground(Color.ORANGE);
    
        incorrectLabel.setFont(new Font("Times New Roman", Font.BOLD, 18));
        incorrectLabel.setForeground(Color.RED);
    
        JPanel correctPositionSquare = createColorSquare(Color.GREEN);
        JPanel correctWrongPositionSquare = createColorSquare(Color.ORANGE);
        JPanel incorrectSquare = createColorSquare(Color.RED);
    
        legendPanel.add(correctPositionSquare);
        legendPanel.add(correctPositionLabel);
        legendPanel.add(correctWrongPositionSquare);
        legendPanel.add(correctWrongPositionLabel);
        legendPanel.add(incorrectSquare);
        legendPanel.add(incorrectLabel);
        
    
        gridPanel.add(legendPanel);
    }

    private int startGame() {
        //
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                updateTimerLabel();
            }
        }, 0, 1000);
        is_finished = 0;
        return 1;
    
    }

    private void updateScoreLabel(int score) {
        scoreLabel.setText("Ton score est : " + score + " partie gagnée");
    }
    
    private void updateTimerLabel() {
        if(countdown == 1){
            timerLabel.setText("Temps écoulé");
        }
        else{
            timerLabel.setText("Temps restant: " + countdown + " secondes");
        }
        
    }

    private void createGridRow() {
        JPanel rowPanel = new JPanel();
        rowPanel.setLayout(new GridLayout(1, wordLength));
    
        JLabel[] letterLabels = new JLabel[wordLength];
        wordMasked = gameInit.getMaskedWord();
        for (int i = 0; i < wordLength; i++) {
            
            letterLabels[i] = new JLabel(Character.toString(wordMasked.charAt(i)), SwingConstants.CENTER);
            letterLabels[i].setPreferredSize(new Dimension(20, 20));
            letterLabels[i].setBorder(BorderFactory.createLineBorder(Color.BLACK,1));
            letterLabels[i].setFont(new Font("Times New Roman", Font.BOLD, 50));
            
            letterLabels[i].setOpaque(true);
            letterLabels[i].setBackground(Color.WHITE);


            rowPanel.add(letterLabels[i]);
        }
    

    
        gridPanel.add(rowPanel);
        letterRows.add(letterLabels);
        // Ajouter la nouvelle ligne à la liste
    }
    

    private void makeGuess() {
        String guess = inputField.getText().toUpperCase();
        userEntries.add(guess);
        //userEntries.set(guess);
        
        if (remainingAttempts > 1 && countdown > 0) {
            if (guess.equals(wordToGuess)) {
                updateGameState(guess);
                displayMessage("Bravo, vous avez trouvé le mot : " + wordToGuess);
                win ++;
                scoreActuel.incrementScore();
                //addScore(playerName, win);
                //scoreActuel.incrementScore();
                updateScoreLabel(scoreActuel.getScore());
                endGame();
            }
             else {
                if(guess.length() == wordLength){
                    if(guess.charAt(0) != wordToGuess.charAt(0)){
                        displayMessageEreur("Entrer un mot qui commence par " + wordToGuess.charAt(0));
                    }
                    else{
                        updateGameState(guess);
                        displayMessageEreur("Il vous reste " + remainingAttempts + " tentatives\n");
                        createGridRow();
                    }

                }
                else{
                    displayMessageEreur("Entrer un mot de taille : " + wordLength);
                }

            }
        } else {
            if (guess.equals(wordToGuess)) {
                updateGameState(guess);
                displayMessage("Bravo, vous avez trouvé le mot : " + wordToGuess);
                scoreActuel.incrementScore();
                updateScoreLabel(scoreActuel.getScore());
                endGame();
            }
            else if (guess.length() != wordLength){
                displayMessageEreur("Entrer un mot de taille : " + wordLength);
            }
            else{
                updateGameState(guess);
                displayMessageEreur("Vous avez utilisé toutes vos tentatives. Le mot était : " + wordToGuess);
                endGame();
            }

        }
        

        inputField.setText("");
        }
    
        private void updateSquare(JLabel[] currentRow, Color color, int index, String guess) {
            currentRow[index].setBackground(color);
            currentRow[index].setText("<html><span style='font-size: 40px;'>" + guess.charAt(index) + "</span></html>");
        
            // Ajouter un tooltip
            currentRow[index].setToolTipText("<html><span style='font-family: Arial; font-size: 20px; color: white;'>Votre texte de tooltip ici.</span></html>");
        
            currentRow[index].addMouseListener(new MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent e) {
                    currentRow[index].setBackground(Color.GRAY);
                }
        
                @Override
                public void mouseExited(MouseEvent e) {
                    currentRow[index].setBackground(color);
                }
            });
        }
        
    private void updateGameState(String guess) {
        JLabel[] currentRow = letterRows.get(letterRows.size() - 1); 
        Map<Character, Integer> wordCounts = countLetterOccurrences(wordToGuess);
        Map<Character, Integer> guessCounts = new HashMap<>();
    
        for (int i = 0; i < wordLength; i++) {
            final int index = i;
            char guessChar = guess.charAt(i);
            guessCounts.put(guessChar, guessCounts.getOrDefault(guessChar, 0) + 1);
    
            if (guessChar == wordToGuess.charAt(i)) {

                updateSquare(currentRow, Color.GREEN, index,guess);
                currentRow[index].setToolTipText("<html><span style='font-family: Arial; font-size: 20px; color: green;'>Lettre correcte, Position correcte.</span></html>");
            } else {
                if (wordToGuess.indexOf(guessChar) != -1) {
                    if (guessCounts.get(guessChar) <= wordCounts.get(guessChar)) {

                        updateSquare(currentRow, Color.ORANGE, index,guess);
                        currentRow[index].setToolTipText("<html><span style='font-family: Arial; font-size: 20px; color: orange;'>Lettre correcte, Position incorrecte.</span></html>");
                    } else {

                        updateSquare(currentRow, Color.RED, index,guess);
                        currentRow[index].setToolTipText("<html><span style='font-family: Arial; font-size: 20px; color: red;'>Lettre incorrecte, Position incorrecte.</span></html>");
                    }
                } else {

                    updateSquare(currentRow, Color.RED, index,guess);
                    currentRow[index].setToolTipText("<html><span style='font-family: Arial; font-size: 20px; color: red;'>Lettre incorrecte, Position incorrecte.</span></html>");
                }
            }
    
        }
    
        remainingAttempts--;
    }

        
    public void startNewGame() {
        if(is_finished == 0){
            displayMessageEreur("Terminer d'abord la partie courante");
        }
        else{
            int result = JOptionPane.showConfirmDialog(
                this,
                "Voulez-vous vraiment recommencer ?",
                "Confirmation",
                JOptionPane.YES_NO_OPTION
        );
    
            if (result == JOptionPane.YES_OPTION) {
                dispose();
        
                if (timer != null) {
                    timer.cancel();
                }
        
                restartApplication();
            }
            else{
                // do nothing akhoya
            }
        }

    }
    
    private void restartApplication() {
        try {
            String javaHome = System.getProperty("java.home");
            String javaBin = javaHome + File.separator + "bin" + File.separator + "java";
            String classpath = System.getProperty("java.class.path");
            String className = MotusFrame.class.getName();
            
            ProcessBuilder builder = new ProcessBuilder(javaBin, "-cp", classpath, className);
            builder.redirectInput(ProcessBuilder.Redirect.INHERIT);
            builder.redirectOutput(ProcessBuilder.Redirect.INHERIT);
            builder.redirectError(ProcessBuilder.Redirect.INHERIT);
    
            builder.start();
            System.exit(0);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    


    private static void displayMessageEreur(String message) {
        JOptionPane.showMessageDialog(null, message,"Alert",JOptionPane.ERROR_MESSAGE);
    }
    private static void displayMessage(String message) {
        JOptionPane.showMessageDialog(null, message,"Décision",JOptionPane.INFORMATION_MESSAGE);
    }
    private int endGame() {
        timer.cancel();
        inputField.setEnabled(false);
        is_finished = 1;
        return 1;
    }

    public static void launchGame() {
        String filepath = "utils/Doc.txt";
        List<String> list = new ArrayList<>();
        


        WordChooserGUI choose = new WordChooserGUI(new Dictionary(list, filepath));
        choose.setVisible(true);

        String wordToGuess = choose.getChosenWord();

        countdown = choose.getTime();
        
        System.out.println(wordToGuess);
        //choose.dispose();
        int maxAttempts = choose.getMaxAttempts();

        new MotusFrame(wordToGuess, maxAttempts).setVisible(true);

        //choose.dispose();
        
    }
    

    public static void main(String[] args) {

        displayDecorationImage();
        playerName = JOptionPane.showInputDialog("Entrez votre nom :");
        if (playerName == null) {
            System.exit(0);
        }
        
        scoreActuel = new Score(playerName);

        launchGame();
        
    }
}