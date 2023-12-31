# Motus Game

## Description
Motus Game is a Java-based word puzzle game implemented using Swing for the graphical user interface. The game utilizes several classes from the "controllers" package, each responsible for specific aspects of the game.

![mo](https://github.com/selairgi/Motus-Game/assets/113915540/fb706d7f-66dd-4916-bfd0-5dca736ad97a)


## Classes

### 1. MotusFrame (graphics/MotusFrame.java)
- **Description:** The main class responsible for the graphical user interface, game setup, and user interactions.

### 2. Score (controllers/Score.java)
- **Description:** Manages the scoring system for the game. It keeps track of the player's score and provides methods to increment the score. The score data is saved in a file named "score.txt".

### 3. GameControls (controllers/GameControls.java)
- **Description:** Handles the game logic, including word generation, masking, and comparison of user input with the target word.

### 4. Matrix (controllers/Matrix.java)
- **Description:** Represents a matrix that stores user entries. It is used to keep track of the user's attempts during the game.

### 5. Dictionary (controllers/Dictionary.java)
- **Description:** Reads a "Doc.txt" file and stores its content in a List<String>. The dictionary is used to choose a random word for the game.

### 6. WordChooserGUI (controllers/WordChooserGUI.java)
- **Description:** Interacts with the user to gather information such as difficulty level, player name, and the desired number of characters in the word. It communicates with the Dictionary class to obtain a word for the game.

## How to Run the Game

1. Execute the `MotusFrame` class.
2. A dialog will prompt the player to enter their name and choose the difficulty level.
3. The game interface will be displayed, showing the player's name, the masked word, and the game controls.
4. The player can make guesses by entering words in the input field.
5. The game provides feedback on the correctness of the guessed word and updates the score accordingly.
6. The player can start a new game or quit at any time.

## Score Storage
The player's score is stored in a file named "score". Each player's score is maintained even after exiting the game. The Score class handles the loading and saving of scores from/to this file.

## Notes
- The game uses an image (motus.jpg) for decoration during startup.
- The game includes a timer, and if the time runs out, the game ends with the correct word revealed.
- The code includes a restart feature to begin a new game.
- The project structure assumes that certain resources (like the dictionary file) are present in specific locations. Ensure these files are available for the proper functioning of the game.

## License

This project is licensed under the [MIT License](LICENSE).

Feel free to modify or extend the code to add new features or improve existing ones. Enjoy playing Motus!
