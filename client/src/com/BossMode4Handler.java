package src.com;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.stage.Window;


import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class BossMode4Handler {

    private int timeLeft;
    private final int STARTING_TIME = 150;
    private ArrayList<Word> possibleWords = new ArrayList<Word>(); // Bank of words to choose from
    private HashMap<String, Topic> topicsHashMap = new HashMap<String, Topic>();
    private HashMap<Integer, Word> chosenWordsHashMap = new HashMap<Integer, Word>(); // Word objects chosen for each index
    private Random randomGenerator = new Random();
    private ArrayList<Character> possibleLetters = new ArrayList<Character>();
    private final int NUMBER_OF_ROWS = 10;
    private final int NUMBER_OF_COLUMNS = 10;
    private final int NUMBER_OF_WORDS = 10;
    private final int NUMBER_OF_DIRECTIONS = 8; // Left to right is considered '1', '2' is 45 degrees clockwise from this and so on
    private final int NUMBER_OF_CELLS = NUMBER_OF_ROWS * NUMBER_OF_COLUMNS; // Top left is 1, ascending going right (bottom right is 100)
    char[][] finalisedLetters = new char[NUMBER_OF_ROWS][NUMBER_OF_COLUMNS]; // 2d Array storing the letter of each cell
    private TranslationHandler translationHandler;
    private HashMap<Integer, String> finalisedWordsHashMap = new HashMap<Integer, String>(); // Exact string chosen for each index
    private int currentClickedLetter;
    // Maps word number to hashmap mapping direction number to all remaining positions for that word number and direction number
    private HashMap<Integer, HashMap<Integer, ArrayList<Integer>>> remainingPositionsByWord = new HashMap<Integer, HashMap<Integer, ArrayList<Integer>>>();
    // Maps word number to list of cells that word currently uses
    private HashMap<Integer, ArrayList<Integer>> cellsUsedByWords = new HashMap<Integer, ArrayList<Integer>>();
    boolean[] correctlyGuessed = new boolean[10];
    // Maps cells used multiple times to how many times that cell is overlapped
    // (So when removing word can see whether to remove letter or just decrement this value)
    private HashMap<Integer, Integer> overlappedCells = new HashMap<Integer, Integer>();

    @FXML
    Label row1Column1;
    @FXML
    Label row1Column2;
    @FXML
    Label row1Column3;
    @FXML
    Label row1Column4;
    @FXML
    Label row1Column5;
    @FXML
    Label row1Column6;
    @FXML
    Label row1Column7;
    @FXML
    Label row1Column8;
    @FXML
    Label row1Column9;
    @FXML
    Label row1Column10;
    @FXML
    Label row2Column1;
    @FXML
    Label row2Column2;
    @FXML
    Label row2Column3;
    @FXML
    Label row2Column4;
    @FXML
    Label row2Column5;
    @FXML
    Label row2Column6;
    @FXML
    Label row2Column7;
    @FXML
    Label row2Column8;
    @FXML
    Label row2Column9;
    @FXML
    Label row2Column10;
    @FXML
    Label row3Column1;
    @FXML
    Label row3Column2;
    @FXML
    Label row3Column3;
    @FXML
    Label row3Column4;
    @FXML
    Label row3Column5;
    @FXML
    Label row3Column6;
    @FXML
    Label row3Column7;
    @FXML
    Label row3Column8;
    @FXML
    Label row3Column9;
    @FXML
    Label row3Column10;
    @FXML
    Label row4Column1;
    @FXML
    Label row4Column2;
    @FXML
    Label row4Column3;
    @FXML
    Label row4Column4;
    @FXML
    Label row4Column5;
    @FXML
    Label row4Column6;
    @FXML
    Label row4Column7;
    @FXML
    Label row4Column8;
    @FXML
    Label row4Column9;
    @FXML
    Label row4Column10;
    @FXML
    Label row5Column1;
    @FXML
    Label row5Column2;
    @FXML
    Label row5Column3;
    @FXML
    Label row5Column4;
    @FXML
    Label row5Column5;
    @FXML
    Label row5Column6;
    @FXML
    Label row5Column7;
    @FXML
    Label row5Column8;
    @FXML
    Label row5Column9;
    @FXML
    Label row5Column10;
    @FXML
    Label row6Column1;
    @FXML
    Label row6Column2;
    @FXML
    Label row6Column3;
    @FXML
    Label row6Column4;
    @FXML
    Label row6Column5;
    @FXML
    Label row6Column6;
    @FXML
    Label row6Column7;
    @FXML
    Label row6Column8;
    @FXML
    Label row6Column9;
    @FXML
    Label row6Column10;
    @FXML
    Label row7Column1;
    @FXML
    Label row7Column2;
    @FXML
    Label row7Column3;
    @FXML
    Label row7Column4;
    @FXML
    Label row7Column5;
    @FXML
    Label row7Column6;
    @FXML
    Label row7Column7;
    @FXML
    Label row7Column8;
    @FXML
    Label row7Column9;
    @FXML
    Label row7Column10;
    @FXML
    Label row8Column1;
    @FXML
    Label row8Column2;
    @FXML
    Label row8Column3;
    @FXML
    Label row8Column4;
    @FXML
    Label row8Column5;
    @FXML
    Label row8Column6;
    @FXML
    Label row8Column7;
    @FXML
    Label row8Column8;
    @FXML
    Label row8Column9;
    @FXML
    Label row8Column10;
    @FXML
    Label row9Column1;
    @FXML
    Label row9Column2;
    @FXML
    Label row9Column3;
    @FXML
    Label row9Column4;
    @FXML
    Label row9Column5;
    @FXML
    Label row9Column6;
    @FXML
    Label row9Column7;
    @FXML
    Label row9Column8;
    @FXML
    Label row9Column9;
    @FXML
    Label row9Column10;
    @FXML
    Label row10Column1;
    @FXML
    Label row10Column2;
    @FXML
    Label row10Column3;
    @FXML
    Label row10Column4;
    @FXML
    Label row10Column5;
    @FXML
    Label row10Column6;
    @FXML
    Label row10Column7;
    @FXML
    Label row10Column8;
    @FXML
    Label row10Column9;
    @FXML
    Label row10Column10;
    @FXML
    Label word1;
    @FXML
    Label word2;
    @FXML
    Label word3;
    @FXML
    Label word4;
    @FXML
    Label word5;
    @FXML
    Label word6;
    @FXML
    Label word7;
    @FXML
    Label word8;
    @FXML
    Label word9;
    @FXML
    Label word10;
    @FXML
    Line line1;
    @FXML
    Line line2;
    @FXML
    Line line3;
    @FXML
    Line line4;
    @FXML
    Line line5;
    @FXML
    Line line6;
    @FXML
    Line line7;
    @FXML
    Line line8;
    @FXML
    Line line9;
    @FXML
    Line line10;
    @FXML
    AnchorPane newLevelPane;
    @FXML
    Label newLevelLabel;
    @FXML
    Button startButton;
    @FXML
    Button okayButton;
    @FXML AnchorPane anchorPane;
    @FXML
    Button loseButton;
    @FXML
    Button returnToMenuButton;


    @FXML
    ImageView timerImage;
    @FXML
    Label remainingTime;




    public BossMode4Handler() {
        topicsHashMap = Client.getTopicsHashMap();
        translationHandler = new TranslationHandler();
    }

    // Thread to countdown timer and update it in the top right
    Thread thread = new Thread(new Runnable() {
        @Override
        public void run() {
            for (int i = 0; i < STARTING_TIME; i++) {
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                timeLeft--;
                Platform.runLater(() -> remainingTime.setText(String.valueOf(timeLeft))); // In order for thread to change FX values
            }
            //If it gets to here user ran out of time
            Client.playIncorrectSound();
            Platform.runLater(() -> newLevelPane.setVisible(true));
            Platform.runLater(() -> newLevelPane.toFront());
            Platform.runLater(() -> newLevelLabel.setText("You have ran out of time! So you have lost a life."));
            Platform.runLater(() -> startButton.setVisible(false));
            Platform.runLater(() -> loseButton.setVisible(true));
            Platform.runLater(() -> newLevelLabel.setTextAlignment(TextAlignment.CENTER));

        }
    });

    // When user clicks start button, set up grid etc
    public void startButtonClicked() {
        Client.playClickSound();
        newLevelPane.setVisible(false);
        timeLeft = STARTING_TIME;
        remainingTime.setText(String.valueOf(STARTING_TIME));
        newLevelPane.setVisible(false);
        timerImage.setVisible(true);
        remainingTime.setVisible(true);
        returnToMenuButton.setVisible(true);
        thread.start();
        setPossibleLetters();
        initialiseArray();
        chooseWords();
        finaliseWords();
        populateLists();
        placeWord(1);
        randomiseEmptyCells();
        setLetters();
        fillRemainingLetters();
        remainingSetup();
    }

    // Have the program chose 10 words from the available bank
    public void chooseWords() {
        // Load possible words with possible options
        addWords((ArrayList<Word>) topicsHashMap.get("basicAdjectives").getTopicWords(), "basicAdjectives");
        addWords((ArrayList<Word>) topicsHashMap.get("basicNouns").getTopicWords(), "basicNouns");
        addWords((ArrayList<Word>) topicsHashMap.get("travellingVerbs").getTopicWords(), "travellingVerbs");
        addWords((ArrayList<Word>) topicsHashMap.get("buildingNouns").getTopicWords(), "buildingNouns");
        addWords((ArrayList<Word>) topicsHashMap.get("buildingAdjectives").getTopicWords(), "buildingAdjectives");

        int random = 0;
        for (int i = 0; i < NUMBER_OF_ROWS; i++) {
            random = randomGenerator.nextInt(possibleWords.size());
            setWord(i + 1, possibleWords.get(random).getMainTranslation());
            chosenWordsHashMap.put(i + 1, possibleWords.get(random));
            possibleWords.remove(random);
            // Remove a word from list when it gets chosen so it isn't chosen twice
        }
    }

    // Add words only if longest version of word is no more than 10 characters so it can always fit in the grid
    public void addWords(ArrayList<Word> words, String wordsType) {
        for (int i = 0; i < words.size(); i++) {
            if (wordsType.contains("verbs")) {
                if (!(words.get(i).getInfinitive().length() > 10)) {
                    possibleWords.add(words.get(i));
                }
            } else if (wordsType.contains("adjectives") || wordsType.contains("nouns")) {
                if (!(words.get(i).getSpanishPlural().length() > 10)) {
                    possibleWords.add(words.get(i));
                }
            } else if (!(words.get(i).getWord().length() > 10)) {
                possibleWords.add(words.get(i));
            }
        }
    }

    // Make any necessary changes to chosen words
    public void finaliseWords() {
        for (int i = 0; i < chosenWordsHashMap.size(); i++) {
            Word word = chosenWordsHashMap.get(i + 1);
            String finalisedWord = word.getWord();
            if (word.getWordTopic().contains("adjectives")) { // Randomise gender and number of word in grid
                char gender = ' ';
                boolean plural = false;
                int random = randomGenerator.nextInt(2);
                if (random == 0) {
                    gender = 'm';
                } else gender = 'f';
                random = randomGenerator.nextInt(2);
                if (random == 0) {
                    plural = false;
                } else plural = true;
                finalisedWord = translationHandler.adjectiveConjugator(plural, gender, word, false);

            } else if (word.getWordTopic().contains("nouns")) { // Randomise plural or singular
                int random = randomGenerator.nextInt(2);
                if (random == 0) {
                    finalisedWord = word.getSpanishPlural();
                } else finalisedWord = word.getWord();

            } else if (word.getWordTopic().contains("verbs")) { // Always use infinite
                finalisedWord = word.getInfinitive();
            }
            finalisedWordsHashMap.put(i, finalisedWord);
            cellsUsedByWords.put(i + 1, new ArrayList<Integer>());
        }
    }

    // Place a particular word into the grid
    public void placeWord(int index) {
        // While there are still possible positions the word can go which haven't been tried, attempt to put the word in
        // one of these positions (randomly chosen)
            while (remainingPositionsByWord.get(index) != null) {
                int random = (randomGenerator.nextInt(remainingPositionsByWord.get(index).size())) + 1;

                while (remainingPositionsByWord.get(index).get(random) == null){
                    random = (randomGenerator.nextInt(remainingPositionsByWord.get(index).size())) + 1;
                }

                while (remainingPositionsByWord.get(index).get(random) != null) {
                    int correctedIndex = (index - 1);
                    // See if the grid allows the word to be placed there in that direction
                    if (!potentialFit(finalisedWordsHashMap.get(correctedIndex).length(), getRow(remainingPositionsByWord.get(index).get(random).get(0) - 1), getColumn(remainingPositionsByWord.get(index).get(random).get(0) - 1), random, finalisedWordsHashMap.get(correctedIndex), index)) {
                        // If it doesn't fit, remove this direction and position, and remove this direction entirely if last
                        // remaining possible position for that direction
                        if (remainingPositionsByWord.get(index).get(random).size() == 1){
                            remainingPositionsByWord.get(index).get(random).remove(0);
                            remainingPositionsByWord.get(index).remove(random);
                            break;
                        }
                        else {
                            remainingPositionsByWord.get(index).get(random).remove(0);
                            continue;
                        }
                    }

                    remainingPositionsByWord.get(index).get(random).remove(0);
                    if (index + 1 <= NUMBER_OF_WORDS){
                        // Try placing next word (unless last word)
                        placeWord(index + 1);
                    }
                    return;

                }
                // If word doesn't fit in position, remove position as a possible option
                remainingPositionsByWord.get(index).remove(random);
                continue;
            }
    }

    // Takes a cell number and returns that cell's row number
    public int getRow (int position){
        return position / 10;
    }

    // Takes a cell number and returns that cell's column number
    public int getColumn (int position){
        return position % 10;
    }

    // Takes a row and column number and returns the cell number
    public int getCellNumber (int rows, int columns){
        return (rows * NUMBER_OF_COLUMNS) + columns;
    }

    // Check if a word currently fits in a certain direction in a certain start position
    public boolean potentialFit(int length, int startRow, int startColumn, int direction, String finalisedWord, int finalisedWordNumber) { // See if the grid size allows the word to fit

        switch (direction) {
            case 1:
                // See if grid allows word to fit there in that direction based on word length
                if (startColumn + length <= NUMBER_OF_COLUMNS){
                    // for each letter in the word
                    for (int i = 0; i < length; i++) {
                        // ! symnbolises unused letter
                        if (finalisedLetters[startRow][startColumn + i] == ('!')) {
                            // Temporarily change this cell's value to the i'th letter of the word
                            cellsUsedByWords.get(finalisedWordNumber).add(getCellNumber(startRow, startColumn + i));
                            finalisedLetters[startRow][startColumn + i] = finalisedWord.charAt(i);
                            continue;
                            // If cell's value already matches the words i'th letter, temporarily add this as an overlapped cell
                        } else if (finalisedLetters[startRow][startColumn + i] == finalisedWord.charAt(i)){
                            if (overlappedCells.containsKey(getCellNumber(startRow, startColumn + i))){
                                // Increment value in hash map associated to that cell number if already exists in overlapped cells
                                overlappedCells.put(getCellNumber(startRow, startColumn + i), overlappedCells.get(getCellNumber(startRow, startColumn + i)) + 1);
                            }
                            else {
                                // Add as new entry if not already in table (with value 2 as there must already be 1 usage of cell with that letter)
                                overlappedCells.put(getCellNumber(startRow, startColumn + i), 2);
                            }
                        continue;
                    }
                        else {
                            // Word cannot fit as letter already in that cell doesn't match words i'th letter,
                            // so remove all temporarily placed letters
                            removeLetters(finalisedWordNumber);
                            return false;
                        }
                    }
                    return true;
                }
                else return false;

            case 2:
                // See if grid allows word to fit there in that direction based on word length
                if ((startColumn + length <= NUMBER_OF_COLUMNS) && (startRow + length <= NUMBER_OF_ROWS)){
                    // for each letter in the word
                    for (int i = 0; i < length; i++) {
                        // ! symnbolises unused letter
                        if (finalisedLetters[startRow + i][startColumn + i] == ('!')) {
                            // Temporarily change this cell's value to the i'th letter of the word
                            cellsUsedByWords.get(finalisedWordNumber).add(getCellNumber(startRow + i, startColumn + i));
                            finalisedLetters[startRow + i][startColumn + i] = finalisedWord.charAt(i);
                            continue;
                            // If cell's value already matches the words i'th letter, temporarily add this as an overlapped cell
                        } else if (finalisedLetters[startRow + i][startColumn + i] == finalisedWord.charAt(i)){
                            if (overlappedCells.containsKey(getCellNumber(startRow + i, startColumn + i))){
                                // Increment value in hash map associated to that cell number if already exists in overlapped cells
                                overlappedCells.put(getCellNumber(startRow + i, startColumn + i), overlappedCells.get(getCellNumber(startRow + i, startColumn + i)) + 1); // Increment value in hash map associated to that cell number
                            }
                            else {
                                // Add as new entry if not already in table (with value 2 as there must already be 1 usage of cell with that letter)
                                overlappedCells.put(getCellNumber(startRow + i, startColumn + i), 2);
                            }
                            continue;
                        }
                        else {
                            // Word cannot fit as letter already in that cell doesn't match words i'th letter,
                            // so remove all temporarily placed letters
                            removeLetters(finalisedWordNumber);
                            return false;
                        }
                    }
                    return true;
                }
                else return false;

            case 3:
                // See if grid allows word to fit there in that direction based on word length
            if (startRow + length <= NUMBER_OF_ROWS){
                // for each letter in the word
                for (int i = 0; i < length; i++) {
                    // ! symnbolises unused letter
                    if (finalisedLetters[startRow + i][startColumn] == ('!')) {
                        // Temporarily change this cell's value to the i'th letter of the word
                        cellsUsedByWords.get(finalisedWordNumber).add(getCellNumber(startRow + i, startColumn));
                        finalisedLetters[startRow + i][startColumn] = finalisedWord.charAt(i);
                        continue;
                        // If cell's value already matches the words i'th letter, temporarily add this as an overlapped cell
                    } else if (finalisedLetters[startRow + i][startColumn] == finalisedWord.charAt(i)){
                        if (overlappedCells.containsKey(getCellNumber(startRow + i, startColumn))){
                            // Increment value in hash map associated to that cell number if already exists in overlapped cells
                            overlappedCells.put(getCellNumber(startRow + i, startColumn), overlappedCells.get(getCellNumber(startRow + i, startColumn)) + 1); // Increment value in hash map associated to that cell number
                        }
                        else {
                            // Add as new entry if not already in table (with value 2 as there must already be 1 usage of cell with that letter)
                            overlappedCells.put(getCellNumber(startRow + i, startColumn), 2);
                        }

                        continue;
                    }
                    else {
                        // Word cannot fit as letter already in that cell doesn't match words i'th letter,
                        // so remove all temporarily placed letters
                        removeLetters(finalisedWordNumber);
                        return false;
                    }
                }
                return true;
            }
            else return false;

            case 4:
                // See if grid allows word to fit there in that direction based on word length
                if ((startRow + length <= NUMBER_OF_ROWS) && (startColumn - length >= 0 )) { // need to invidually set letter
                    // for each letter in the word
                    for (int i = 0; i < length; i++) {
                        // ! symnbolises unused letter
                        if (finalisedLetters[startRow + i][startColumn - i] == ('!')) {
                            // Temporarily change this cell's value to the i'th letter of the word
                            cellsUsedByWords.get(finalisedWordNumber).add(getCellNumber(startRow + i, startColumn - i));
                            finalisedLetters[startRow + i][startColumn - i] = finalisedWord.charAt(i);
                            continue;
                            // If cell's value already matches the words i'th letter, temporarily add this as an overlapped cell
                        } else if (finalisedLetters[startRow + i][startColumn - i] == finalisedWord.charAt(i)){
                            if (overlappedCells.containsKey(getCellNumber(startRow + i, startColumn - i))){
                                // Increment value in hash map associated to that cell number if already exists in overlapped cells
                                overlappedCells.put(getCellNumber(startRow + i, startColumn - i), overlappedCells.get(getCellNumber(startRow + i, startColumn - i)) + 1); // Increment value in hash map associated to that cell number
                            }
                            else {
                                // Add as new entry if not already in table (with value 2 as there must already be 1 usage of cell with that letter)
                                overlappedCells.put(getCellNumber(startRow + i, startColumn - i), 2);
                            }
                            continue;
                        }
                        else {
                            // Word cannot fit as letter already in that cell doesn't match words i'th letter,
                            // so remove all temporarily placed letters
                            removeLetters(finalisedWordNumber);
                            return false;
                        }
                    }
                    return true;
                }
                else return false;

            case 5:
                // See if grid allows word to fit there in that direction based on word length
                if (startColumn - length >= 0){
                    // for each letter in the word
                    for (int i = 0; i < length; i++) {
                        // ! symnbolises unused letter
                        if (finalisedLetters[startRow][startColumn - i] == ('!')) {
                            // Temporarily change this cell's value to the i'th letter of the word
                            cellsUsedByWords.get(finalisedWordNumber).add(getCellNumber(startRow, startColumn - i));
                            finalisedLetters[startRow][startColumn - i] = finalisedWord.charAt(i);
                            continue;
                            // If cell's value already matches the words i'th letter, temporarily add this as an overlapped cell
                        } else if (finalisedLetters[startRow][startColumn - i] == finalisedWord.charAt(i)){
                            if (overlappedCells.containsKey(getCellNumber(startRow, startColumn - i))){
                                // Increment value in hash map associated to that cell number if already exists in overlapped cells
                                overlappedCells.put(getCellNumber(startRow, startColumn - i), overlappedCells.get(getCellNumber(startRow, startColumn - i)) + 1); // Increment value in hash map associated to that cell number
                            }
                            else {
                                // Add as new entry if not already in table (with value 2 as there must already be 1 usage of cell with that letter)
                                overlappedCells.put(getCellNumber(startRow, startColumn - i), 2);
                            }
                            continue;
                        }
                        else {
                            // Word cannot fit as letter already in that cell doesn't match words i'th letter,
                            // so remove all temporarily placed letters
                            removeLetters(finalisedWordNumber);
                            return false;
                        }
                    }
                    return true;
                }
                else return false;

            case 6:
                // See if grid allows word to fit there in that direction based on word length
                if ((startColumn - length >= 0) && (startRow - length >= 0)){
                    // for each letter in the word
                    for (int i = 0; i < length; i++) {
                        // ! symnbolises unused letter
                        if (finalisedLetters[startRow - i][startColumn - i] == ('!')) {
                            // Temporarily change this cell's value to the i'th letter of the word
                            cellsUsedByWords.get(finalisedWordNumber).add(getCellNumber(startRow - i, startColumn - i));
                            finalisedLetters[startRow - i][startColumn - i] = finalisedWord.charAt(i);
                            continue;
                            // If cell's value already matches the words i'th letter, temporarily add this as an overlapped cell
                        } else if (finalisedLetters[startRow - i][startColumn - i] == finalisedWord.charAt(i)){
                            if (overlappedCells.containsKey(getCellNumber(startRow - i, startColumn - i))){
                                // Increment value in hash map associated to that cell number if already exists in overlapped cells
                                overlappedCells.put(getCellNumber(startRow - i, startColumn - i), overlappedCells.get(getCellNumber(startRow - i, startColumn - i)) + 1); // Increment value in hash map associated to that cell number
                            }
                            else {
                                // Add as new entry if not already in table (with value 2 as there must already be 1 usage of cell with that letter)
                                overlappedCells.put(getCellNumber(startRow - i, startColumn - i), 2);
                            }
                            continue;
                        }
                        else {
                            // Word cannot fit as letter already in that cell doesn't match words i'th letter,
                            // so remove all temporarily placed letters
                            removeLetters(finalisedWordNumber);
                            return false;
                        }
                    }
                    return true;
                }
                else return false;

            case 7:
                // See if grid allows word to fit there in that direction based on word length
                if (startRow - length >= 0){
                    // for each letter in the word
                    for (int i = 0; i < length; i++) {
                        // ! symnbolises unused letter
                        if (finalisedLetters[startRow - i][startColumn] == ('!')) {
                            // Temporarily change this cell's value to the i'th letter of the word
                            cellsUsedByWords.get(finalisedWordNumber).add(getCellNumber(startRow - i, startColumn));
                            finalisedLetters[startRow - i][startColumn] = finalisedWord.charAt(i);
                            continue;
                            // If cell's value already matches the words i'th letter, temporarily add this as an overlapped cell
                        } else if (finalisedLetters[startRow - i][startColumn] == finalisedWord.charAt(i)){
                            if (overlappedCells.containsKey(getCellNumber(startRow - i, startColumn))){
                                // Increment value in hash map associated to that cell number if already exists in overlapped cells
                                overlappedCells.put(getCellNumber(startRow - i, startColumn), overlappedCells.get(getCellNumber(startRow - i, startColumn)) + 1); // Increment value in hash map associated to that cell number
                            }
                            else {
                                // Add as new entry if not already in table (with value 2 as there must already be 1 usage of cell with that letter)
                                overlappedCells.put(getCellNumber(startRow - i, startColumn), 2);
                            }
                            continue;
                        }
                        else {
                            // Word cannot fit as letter already in that cell doesn't match words i'th letter,
                            // so remove all temporarily placed letters
                            removeLetters(finalisedWordNumber);
                            return false;
                        }
                    }
                    return true;
                }
                else return false;

            case 8:
                // See if grid allows word to fit there in that direction based on word length
                if ((startRow - length >= 0) && (startColumn + length <= NUMBER_OF_COLUMNS)){
                    // for each letter in the word
                    for (int i = 0; i < length; i++) {
                        // ! symnbolises unused letter
                        if (finalisedLetters[startRow - i][startColumn + i] == ('!')) {
                            // Temporarily change this cell's value to the i'th letter of the word
                            cellsUsedByWords.get(finalisedWordNumber).add(getCellNumber(startRow - i, startColumn + i));
                            finalisedLetters[startRow - i][startColumn + i] = finalisedWord.charAt(i);
                            continue;
                            // If cell's value already matches the words i'th letter, temporarily add this as an overlapped cell
                        } else if (finalisedLetters[startRow - i][startColumn + i] == finalisedWord.charAt(i)){
                            if (overlappedCells.containsKey(getCellNumber(startRow - i, startColumn + i))){
                                // Increment value in hash map associated to that cell number if already exists in overlapped cells
                                overlappedCells.put(getCellNumber(startRow - i, startColumn + i), overlappedCells.get(getCellNumber(startRow - i, startColumn + i)) + 1); // Increment value in hash map associated to that cell number
                            }
                            else {
                                // Add as new entry if not already in table (with value 2 as there must already be 1 usage of cell with that letter)
                                overlappedCells.put(getCellNumber(startRow - i, startColumn + i), 2);
                            }
                            continue;
                        }
                        else {
                            // Word cannot fit as letter already in that cell doesn't match words i'th letter,
                            // so remove all temporarily placed letters
                            removeLetters(finalisedWordNumber);
                            return false;
                        }
                    }
                    return true;
                }
                else return false;
        }
        return false;
    }

    public void removeLetters(int finalisedWordNumber) {
        int lettersUsed = cellsUsedByWords.get(finalisedWordNumber).size();
        // for each letter temporarily placed
        for (int i = 0; i < lettersUsed; i++) {
            int currentCellNumber = cellsUsedByWords.get(finalisedWordNumber).get(0);
            // check if cell is still in use with another word, and decerement value in hashmap if so rather than rempving letter
            if (overlappedCells.containsKey(currentCellNumber)) {
                overlappedCells.put(currentCellNumber, overlappedCells.get(currentCellNumber) - 1);
                // if there is now only one use of that cell, no longer need overlapped cells for the cell
                if (overlappedCells.get(currentCellNumber) == 1) {
                    overlappedCells.remove(currentCellNumber);
                }
            } else {
                // If not in overlapped cells, that cell no longer has a use so set back to '!' (no letter currently assigned)
                finalisedLetters[getRow(currentCellNumber)][getColumn(currentCellNumber)] = '!';
            }
            cellsUsedByWords.get(finalisedWordNumber).remove(0);
        }



    }

    // Set all words as not currently guessed at start
    public void remainingSetup() {
        for (int i = 0; i < NUMBER_OF_WORDS; i++){
            correctlyGuessed[i] = false;
        }
    }

    public void populateLists() {

        // At the start, populate lists with every possible position and direction (both randomised) for each word
        for (int i = 0; i < NUMBER_OF_WORDS; i++) {
            HashMap<Integer, ArrayList<Integer>> positionsForWord = new HashMap<Integer, ArrayList<Integer>>();

            ArrayList<Integer> positionList = new ArrayList<Integer>();
            ArrayList<Integer> randomisedPositionList = new ArrayList<Integer>();

            for (int j = NUMBER_OF_CELLS; j > 0; j--) { // Populate list with all possible directions
                positionList.add(j);
            }

            for (int j = NUMBER_OF_CELLS; j > 0; j--) { // Randomly add elements to randomised list
                int random = randomGenerator.nextInt(j);
                randomisedPositionList.add(positionList.get(random));
                positionList.remove(random);
            }

            for (int j = NUMBER_OF_DIRECTIONS; j > 0; j--) { // Populate list with all possible directions
                ArrayList<Integer> randomisedPositionListToAdd = (ArrayList<Integer>) randomisedPositionList.clone();
                positionsForWord.put(j, randomisedPositionListToAdd);
            }
             remainingPositionsByWord.put(i + 1, positionsForWord);
        }
    }


    // Set all words on the right and make visible
    public void setWord(int wordNumber, String word) {
        switch (wordNumber) {
            case 1:
                word1.setText(word);
                word1.setVisible(true);
                break;
            case 2:
                word2.setText(word);
                word2.setVisible(true);
                break;
            case 3:
                word3.setText(word);
                word3.setVisible(true);
                break;
            case 4:
                word4.setText(word);
                word4.setVisible(true);
                break;
            case 5:
                word5.setText(word);
                word5.setVisible(true);
                break;
            case 6:
                word6.setText(word);
                word6.setVisible(true);
                break;
            case 7:
                word7.setText(word);
                word7.setVisible(true);
                break;
            case 8:
                word8.setText(word);
                word8.setVisible(true);
                break;
            case 9:
                word9.setText(word);
                word9.setVisible(true);
                break;
            case 10:
                word10.setText(word);
                word10.setVisible(true);
                break;
        }
    }


    // Fill all letters which aren't used by any words with random letters
    public void fillRemainingLetters() {
        for (int i = 0; i < NUMBER_OF_ROWS; i++) {
            for (int j = 0; j < NUMBER_OF_COLUMNS; j++) {
                if (finalisedLetters[i][j] == '!') {
                    randomiseLetter(i, j);
                }
            }
        }
    }

    // Pick a random letter out of Spanish alphabet
    public void randomiseLetter(int rowNumber, int columnNumber) {
        int random = randomGenerator.nextInt(33);
        finalisedLetters[rowNumber][columnNumber] = possibleLetters.get(random);
    }

    // Initally all array elements are '!' - representing the element not having a particular letter yet
public void initialiseArray() {
        for (int i = 0; i < NUMBER_OF_ROWS; i++){
            for (int j = 0; j < NUMBER_OF_COLUMNS; j++){
                finalisedLetters[i][j] = '!';
            }
        }
}

// All possible letters to appear in grid
    public void setPossibleLetters() {
        possibleLetters.add('A');
        possibleLetters.add('Á');
        possibleLetters.add('B');
        possibleLetters.add('C');
        possibleLetters.add('D');
        possibleLetters.add('E');
        possibleLetters.add('É');
        possibleLetters.add('F');
        possibleLetters.add('G');
        possibleLetters.add('H');
        possibleLetters.add('I');
        possibleLetters.add('Í');
        possibleLetters.add('J');
        possibleLetters.add('K');
        possibleLetters.add('L');
        possibleLetters.add('M');
        possibleLetters.add('N');
        possibleLetters.add('Ñ');
        possibleLetters.add('O');
        possibleLetters.add('Ó');
        possibleLetters.add('P');
        possibleLetters.add('Q');
        possibleLetters.add('R');
        possibleLetters.add('S');
        possibleLetters.add('T');
        possibleLetters.add('U');
        possibleLetters.add('Ú');
        possibleLetters.add('Ü');
        possibleLetters.add('V');
        possibleLetters.add('W');
        possibleLetters.add('X');
        possibleLetters.add('Y');
        possibleLetters.add('Z');
    }

    // Pick a random letter for cells which are currently '!'
    public void randomiseEmptyCells() {
        for (int i = 0; i < NUMBER_OF_ROWS; i++){
            for (int j = 0; j < NUMBER_OF_COLUMNS; j++){
                if (finalisedLetters[i][j] == '!'){
                    int random = randomGenerator.nextInt(possibleLetters.size());
                    finalisedLetters[i][j] = possibleLetters.get(random);
                }
            }
        }
    }

    // When user clicks 2 letters to submit a word attempt
    public void verifyWord(int start, int end) throws IOException {
        currentClickedLetter = 0;
        removeLetterColours();
        int startRow = getRow(start - 1);
        int startColumn = getColumn(start - 1);
        int endRow = getRow(end - 1);
        int endColumn = getColumn(end - 1);
        int direction = 0;
        int length = 0;
        // Work out direction that user has guessed based on start and end cell values
        if ((startRow == endRow) && (startColumn < endColumn)){
            direction = 1;
        }
        else if ((startRow < endRow) && (startColumn < endColumn)){
            direction = 2;
        }
        else if ((startRow < endRow) && (startColumn == endColumn)){
            direction = 3;
        }
        else if ((startRow < endRow) && (startColumn > endColumn)){
            direction = 4;
        }
        else if ((startRow == endRow) && (startColumn > endColumn)){
            direction = 5;
        }
        else if ((startRow > endRow) && (startColumn > endColumn)){
            direction = 6;
        }
        else if ((startRow > endRow) && (startColumn == endColumn)){
            direction = 7;
        }
        else direction = 8;

        if (direction == 8 || direction == 7 || direction == 6){
            length = startRow - endRow + 1;
        }
        else if (direction == 5 || direction == 4){
            length = startColumn - endColumn + 1;
        }
        else if (direction == 3 || direction == 2){
            length = endRow - startRow + 1;
        }
        else length = endColumn - startColumn + 1;

        // Create word consisting of letters inbetween first and second letters the user clicked
        String attemptedWord = "";
        for (int i = 0; i < length; i++){
            switch(direction){
                case 1:
                    attemptedWord = attemptedWord + finalisedLetters[startRow][startColumn + i];
                    break;
                case 2:
                    attemptedWord = attemptedWord + finalisedLetters[startRow + i][startColumn + i];
                    break;
                case 3:
                    attemptedWord = attemptedWord + finalisedLetters[startRow + i][startColumn];
                    break;
                case 4:
                    attemptedWord = attemptedWord + finalisedLetters[startRow + i][startColumn - i];
                    break;
                case 5:
                    attemptedWord = attemptedWord + finalisedLetters[startRow][startColumn - i];
                    break;
                case 6:
                    attemptedWord = attemptedWord + finalisedLetters[startRow - i][startColumn - i];
                    break;
                case 7:
                    attemptedWord = attemptedWord + finalisedLetters[startRow - i][startColumn];
                    break;
                case 8:
                    attemptedWord = attemptedWord + finalisedLetters[startRow - i][startColumn + i];
                    break;
            }
        }
        // Check whether word matches each finalised word
        for (int i = 0; i < finalisedWordsHashMap.size(); i++){
            // If word is correct
            if (attemptedWord.equals(finalisedWordsHashMap.get(i))){
                correctlyGuessed[i] = true;
                // Draw line over corresponding English word on the right
                switch(i + 1){
                    case 1:
                        line1.setVisible(true);
                        break;
                    case 2:
                        line2.setVisible(true);
                        break;
                    case 3:
                        line3.setVisible(true);
                        break;
                    case 4:
                        line4.setVisible(true);
                        break;
                    case 5:
                        line5.setVisible(true);
                        break;
                    case 6:
                        line6.setVisible(true);
                        break;
                    case 7:
                        line7.setVisible(true);
                        break;
                    case 8:
                        line8.setVisible(true);
                        break;
                    case 9:
                        line9.setVisible(true);
                        break;
                    case 10:
                        line10.setVisible(true);
                        break;
                }
                // Draw line over the word within the grid
                Line line = new Line();
                line.setStartX(139.0 + 56.2 * startColumn + 28.1);
                line.setStartY(119.0 + 56.2 * startRow + 28.1);
                line.setEndX(139.0 + 56.2 * endColumn + 28.1);
                line.setEndY(119.0 + 56.2 * endRow + 28.1);
                line.setStrokeWidth(3.0);
                line.setOpacity(0.7);
                if (direction == 1){
                    line.setStartX(line.getStartX() - 15);
                    line.setEndX(line.getEndX() + 15);
                }
                else if (direction == 2){
                    line.setStartX(line.getStartX() - 15);
                    line.setEndX(line.getEndX() + 15);
                    line.setStartY(line.getStartY() - 15);
                    line.setEndY(line.getEndY() + 15);
                }
                else if (direction == 3){
                    line.setStartY(line.getStartY() - 15);
                    line.setEndY(line.getEndY() + 15);
                }
                else if (direction == 4){
                    line.setStartX(line.getStartX() + 15);
                    line.setEndX(line.getEndX() - 15);
                    line.setStartY(line.getStartY() - 15);
                    line.setEndY(line.getEndY() + 15);
                }
                else if (direction == 5){
                    line.setStartX(line.getStartX() + 15);
                    line.setEndX(line.getEndX() - 15);
                }
                else if (direction == 6){
                    line.setStartX(line.getStartX() + 15);
                    line.setEndX(line.getEndX() - 15);
                    line.setStartY(line.getStartY() + 15);
                    line.setEndY(line.getEndY() - 15);
                }
                else if (direction == 7){
                    line.setStartY(line.getStartY() + 15);
                    line.setEndY(line.getEndY() - 15);
                }
                else if (direction == 8){
                    line.setStartX(line.getStartX() - 15);
                    line.setEndX(line.getEndX() + 15);
                    line.setStartY(line.getStartY() + 15);
                    line.setEndY(line.getEndY() - 15);
                }

                anchorPane.getChildren().add(line);
                boolean allCorrect = true;
                // Check whether user has now correctly found every word
                for (int j = 0; j < NUMBER_OF_WORDS; j++){
                    if (correctlyGuessed[j] == false){
                        allCorrect = false;
                    }
                }
                if (allCorrect){
                    // win game if user has found ever word
                    winGame();
                }
            }
        }



    }


    // Called if user runs out of time
    public void loseButtonClicked() throws IOException {
        Client.playClickSound();
        if (Client.getCurrentlyPractising()){
            // Reset streak if in practice mode
            Client.client.setPracticeCurrentStreak(0);
        }
        else {
            // Increment score and decrement life if in endless mode
            Client.client.setCurrentEndlessLives(Client.client.getCurrentEndlessLives() - 1);
            Client.client.setCurrentEndlessScore(Client.client.getCurrentEndlessScore() + 1);
        }
        // Load next screen depending on if in practice or endless mode
        Scene scene = line1.getScene();
        Window window = scene.getWindow();
        Stage stage = (Stage) window;
        FXMLLoader loader = new FXMLLoader();
        if (Client.getCurrentlyPractising()) {
            Client.setNeedPracticePopup(true);
            Parent practiceMode = loader.load(getClass().getResource("PracticeModeEndless.fxml"));
            stage.setTitle("Practice Mode");
            stage.getScene().setRoot(practiceMode);
            stage.show();
        }
        else {
            Parent endlessMode = loader.load(getClass().getResource("ContinueEndlessMode.fxml"));
            stage.setTitle("Endless Mode");
            stage.getScene().setRoot(endlessMode);
            stage.show();
        }

    }


    // Called if user correctly found every word
    public void winGame() throws IOException {
        Client.playCorrectSound();
        if (Client.getCurrentlyPractising()){
            // Add to streak if in practice mode
            Client.client.setPracticeCurrentStreak(Client.client.getPracticeCurrentStreak() + 1);
        }
        else {
            // Add to score if in endless mode
            Client.client.setCurrentEndlessScore(Client.client.getCurrentEndlessScore() + 1);
        }

        // Check whether matches achievement criteria, and increment that achievement progress
            if (timeLeft > (STARTING_TIME / 2)){
                Client.getAchievementsProgress().put("4achievement1", Client.getAchievementsProgress().get("4achievement1") + 1);
                if (Client.getAchievementsProgress().get("4achievement1") == Client.getAchievementsList().get(15).getNumberOfTimes()) {
                    Client.incrementSkipAchievement(4); // Increment level 5 skip if just completed achievement
                }
                Client.printToServer("incrementAchievement");
                Client.printToServer("4achievement1");
            }

        Client.getAchievementsProgress().put("4achievement2", Client.getAchievementsProgress().get("4achievement2") + 1);
        if (Client.getAchievementsProgress().get("4achievement2") == Client.getAchievementsList().get(16).getNumberOfTimes()) {
            Client.incrementSkipAchievement(4); // Increment level 5 skip if just completed achievement
        }
        Client.printToServer("incrementAchievement");
        Client.printToServer("4achievement2");

        // Load next screen depending on if in practice or endless mode
        Scene scene = line1.getScene();
        Window window = scene.getWindow();
        Stage stage = (Stage) window;
        FXMLLoader loader = new FXMLLoader();
        if (Client.getCurrentlyPractising()) {
            Client.setNeedPracticePopup(true);
            Parent practiceMode = loader.load(getClass().getResource("PracticeModeEndless.fxml"));
            stage.setTitle("Practice Mode");
            stage.getScene().setRoot(practiceMode);
            stage.show();
        }
        else {
            Parent endlessMode = loader.load(getClass().getResource("ContinueEndlessMode.fxml"));
            stage.setTitle("Endless Mode");
            stage.getScene().setRoot(endlessMode);
            stage.show();
        }

    }

    // If user clicks on particular cell
    public void row1Column1Clicked() throws IOException {
        if (currentClickedLetter == 0) { // 0 is when nothing has been currently clicked
            currentClickedLetter = 1;
            Client.playClickSound();
            // Highlight currently clicked letter to visually indicate to user
            row1Column1.setTextFill(Color.GOLD);
        } else {
            // If this is the end letter clicked, see if word that joins start and end letters the user clicked appears in finalised words
            verifyWord(currentClickedLetter, 1);
        }
    }

    public void row1Column2Clicked() throws IOException {
        if (currentClickedLetter == 0) {
            currentClickedLetter = 2;
            Client.playClickSound();
            row1Column2.setTextFill(Color.GOLD);
        } else {
            verifyWord(currentClickedLetter, 2);
        }
    }

    public void row1Column3Clicked() throws IOException {
        if (currentClickedLetter == 0) {
            currentClickedLetter = 3;
            Client.playClickSound();
            row1Column3.setTextFill(Color.GOLD);
        } else {
            verifyWord(currentClickedLetter, 3);
        }
    }

    public void row1Column4Clicked() throws IOException {
        if (currentClickedLetter == 0) {
            currentClickedLetter = 4;
            Client.playClickSound();
            row1Column4.setTextFill(Color.GOLD);
        } else {
            verifyWord(currentClickedLetter, 4);
        }
    }

    public void row1Column5Clicked() throws IOException {
        if (currentClickedLetter == 0) {
            currentClickedLetter = 5;
            Client.playClickSound();
            row1Column5.setTextFill(Color.GOLD);
        } else {
            verifyWord(currentClickedLetter, 5);
        }
    }

    public void row1Column6Clicked() throws IOException {
        if (currentClickedLetter == 0) {
            currentClickedLetter = 6;
            Client.playClickSound();
            row1Column6.setTextFill(Color.GOLD);
        } else {
            verifyWord(currentClickedLetter, 6);
        }
    }

    public void row1Column7Clicked() throws IOException {
        if (currentClickedLetter == 0) {
            currentClickedLetter = 7;
            Client.playClickSound();
            row1Column7.setTextFill(Color.GOLD);
        } else {
            verifyWord(currentClickedLetter, 7);
        }
    }

    public void row1Column8Clicked() throws IOException {
        if (currentClickedLetter == 0) {
            currentClickedLetter = 8;
            Client.playClickSound();
            row1Column8.setTextFill(Color.GOLD);
        } else {
            verifyWord(currentClickedLetter, 8);
        }
    }

    public void row1Column9Clicked() throws IOException {
        if (currentClickedLetter == 0) {
            currentClickedLetter = 9;
            Client.playClickSound();
            row1Column9.setTextFill(Color.GOLD);
        } else {
            verifyWord(currentClickedLetter, 9);
        }
    }

    public void row1Column10Clicked() throws IOException {
        if (currentClickedLetter == 0) {
            currentClickedLetter = 10;
            Client.playClickSound();
            row1Column10.setTextFill(Color.GOLD);
        } else {
            verifyWord(currentClickedLetter, 10);
        }
    }

    public void row2Column1Clicked() throws IOException {
        if (currentClickedLetter == 0) {
            currentClickedLetter = 11;
            Client.playClickSound();
            row2Column1.setTextFill(Color.GOLD);
        } else {
            verifyWord(currentClickedLetter, 11);
        }
    }

    public void row2Column2Clicked() throws IOException {
        if (currentClickedLetter == 0) {
            currentClickedLetter = 12;
            Client.playClickSound();
            row2Column2.setTextFill(Color.GOLD);
        } else {
            verifyWord(currentClickedLetter, 12);
        }
    }

    public void row2Column3Clicked() throws IOException {
        if (currentClickedLetter == 0) {
            currentClickedLetter = 13;
            Client.playClickSound();
            row2Column3.setTextFill(Color.GOLD);
        } else {
            verifyWord(currentClickedLetter, 13);
        }
    }

    public void row2Column4Clicked() throws IOException {
        if (currentClickedLetter == 0) {
            currentClickedLetter = 14;
            Client.playClickSound();
            row2Column4.setTextFill(Color.GOLD);
        } else {
            verifyWord(currentClickedLetter, 14);
        }
    }

    public void row2Column5Clicked() throws IOException {
        if (currentClickedLetter == 0) {
            currentClickedLetter = 15;
            Client.playClickSound();
            row2Column5.setTextFill(Color.GOLD);
        } else {
            verifyWord(currentClickedLetter, 15);
        }
    }

    public void row2Column6Clicked() throws IOException {
        if (currentClickedLetter == 0) {
            currentClickedLetter = 16;
            Client.playClickSound();
            row2Column6.setTextFill(Color.GOLD);
        } else {
            verifyWord(currentClickedLetter, 16);
        }
    }

    public void row2Column7Clicked() throws IOException {
        if (currentClickedLetter == 0) {
            currentClickedLetter = 17;
            Client.playClickSound();
            row2Column7.setTextFill(Color.GOLD);
        } else {
            verifyWord(currentClickedLetter, 17);
        }
    }

    public void row2Column8Clicked() throws IOException {
        if (currentClickedLetter == 0) {
            currentClickedLetter = 18;
            Client.playClickSound();
            row2Column8.setTextFill(Color.GOLD);
        } else {
            verifyWord(currentClickedLetter, 18);
        }
    }

    public void row2Column9Clicked() throws IOException {
        if (currentClickedLetter == 0) {
            currentClickedLetter = 19;
            Client.playClickSound();
            row2Column9.setTextFill(Color.GOLD);
        } else {
            verifyWord(currentClickedLetter, 19);
        }
    }

    public void row2Column10Clicked() throws IOException {
        if (currentClickedLetter == 0) {
            currentClickedLetter = 20;
            Client.playClickSound();
            row2Column10.setTextFill(Color.GOLD);
        } else {
            verifyWord(currentClickedLetter, 20);
        }
    }

    public void row3Column1Clicked() throws IOException {
        if (currentClickedLetter == 0) {
            currentClickedLetter = 21;
            Client.playClickSound();
            row3Column1.setTextFill(Color.GOLD);
        } else {
            verifyWord(currentClickedLetter, 21);
        }
    }

    public void row3Column2Clicked() throws IOException {
        if (currentClickedLetter == 0) {
            currentClickedLetter = 22;
            Client.playClickSound();
            row3Column2.setTextFill(Color.GOLD);
        } else {
            verifyWord(currentClickedLetter, 22);
        }
    }

    public void row3Column3Clicked() throws IOException {
        if (currentClickedLetter == 0) {
            currentClickedLetter = 23;
            Client.playClickSound();
            row3Column3.setTextFill(Color.GOLD);
        } else {
            verifyWord(currentClickedLetter, 23);
        }
    }

    public void row3Column4Clicked() throws IOException {
        if (currentClickedLetter == 0) {
            currentClickedLetter = 24;
            Client.playClickSound();
            row3Column4.setTextFill(Color.GOLD);
        } else {
            verifyWord(currentClickedLetter, 24);
        }
    }

    public void row3Column5Clicked() throws IOException {
        if (currentClickedLetter == 0) {
            currentClickedLetter = 25;
            Client.playClickSound();
            row3Column5.setTextFill(Color.GOLD);
        } else {
            verifyWord(currentClickedLetter, 25);
        }
    }

    public void row3Column6Clicked() throws IOException {
        if (currentClickedLetter == 0) {
            currentClickedLetter = 26;
            Client.playClickSound();
            row3Column6.setTextFill(Color.GOLD);
        } else {
            verifyWord(currentClickedLetter, 26);
        }
    }

    public void row3Column7Clicked() throws IOException {
        if (currentClickedLetter == 0) {
            currentClickedLetter = 27;
            Client.playClickSound();
            row3Column7.setTextFill(Color.GOLD);
        } else {
            verifyWord(currentClickedLetter, 27);
        }
    }

    public void row3Column8Clicked() throws IOException {
        if (currentClickedLetter == 0) {
            currentClickedLetter = 28;
            Client.playClickSound();
            row3Column8.setTextFill(Color.GOLD);
        } else {
            verifyWord(currentClickedLetter, 28);
        }
    }

    public void row3Column9Clicked() throws IOException {
        if (currentClickedLetter == 0) {
            currentClickedLetter = 29;
            Client.playClickSound();
            row3Column9.setTextFill(Color.GOLD);
        } else {
            verifyWord(currentClickedLetter, 29);
        }
    }

    public void row3Column10Clicked() throws IOException {
        if (currentClickedLetter == 0) {
            currentClickedLetter = 30;
            Client.playClickSound();
            row3Column10.setTextFill(Color.GOLD);
        } else {
            verifyWord(currentClickedLetter, 30);
        }
    }

    public void row4Column1Clicked() throws IOException {
        if (currentClickedLetter == 0) {
            currentClickedLetter = 31;
            Client.playClickSound();
            row4Column1.setTextFill(Color.GOLD);
        } else {
            verifyWord(currentClickedLetter, 31);
        }
    }

    public void row4Column2Clicked() throws IOException {
        if (currentClickedLetter == 0) {
            currentClickedLetter = 32;
            Client.playClickSound();
            row4Column2.setTextFill(Color.GOLD);
        } else {
            verifyWord(currentClickedLetter, 32);
        }
    }

    public void row4Column3Clicked() throws IOException {
        if (currentClickedLetter == 0) {
            currentClickedLetter = 33;
            Client.playClickSound();
            row4Column3.setTextFill(Color.GOLD);
        } else {
            verifyWord(currentClickedLetter, 33);
        }
    }

    public void row4Column4Clicked() throws IOException {
        if (currentClickedLetter == 0) {
            currentClickedLetter = 34;
            Client.playClickSound();
            row4Column4.setTextFill(Color.GOLD);
        } else {
            verifyWord(currentClickedLetter, 34);
        }
    }

    public void row4Column5Clicked() throws IOException {
        if (currentClickedLetter == 0) {
            currentClickedLetter = 35;
            Client.playClickSound();
            row4Column5.setTextFill(Color.GOLD);
        } else {
            verifyWord(currentClickedLetter, 35);
        }
    }

    public void row4Column6Clicked() throws IOException {
        if (currentClickedLetter == 0) {
            currentClickedLetter = 36;
            Client.playClickSound();
            row4Column6.setTextFill(Color.GOLD);
        } else {
            verifyWord(currentClickedLetter, 36);
        }
    }

    public void row4Column7Clicked() throws IOException {
        if (currentClickedLetter == 0) {
            currentClickedLetter = 37;
            Client.playClickSound();
            row4Column7.setTextFill(Color.GOLD);
        } else {
            verifyWord(currentClickedLetter, 37);
        }
    }

    public void row4Column8Clicked() throws IOException {
        if (currentClickedLetter == 0) {
            currentClickedLetter = 38;
            Client.playClickSound();
            row4Column8.setTextFill(Color.GOLD);
        } else {
            verifyWord(currentClickedLetter, 38);
        }
    }

    public void row4Column9Clicked() throws IOException {
        if (currentClickedLetter == 0) {
            currentClickedLetter = 39;
            Client.playClickSound();
            row4Column9.setTextFill(Color.GOLD);
        } else {
            verifyWord(currentClickedLetter, 39);
        }
    }

    public void row4Column10Clicked() throws IOException {
        if (currentClickedLetter == 0) {
            currentClickedLetter = 40;
            Client.playClickSound();
            row4Column10.setTextFill(Color.GOLD);
        } else {
            verifyWord(currentClickedLetter, 40);
        }
    }

    public void row5Column1Clicked() throws IOException {
        if (currentClickedLetter == 0) {
            currentClickedLetter = 41;
            Client.playClickSound();
            row5Column1.setTextFill(Color.GOLD);
        } else {
            verifyWord(currentClickedLetter, 41);
        }
    }

    public void row5Column2Clicked() throws IOException {
        if (currentClickedLetter == 0) {
            currentClickedLetter = 42;
            Client.playClickSound();
            row5Column2.setTextFill(Color.GOLD);
        } else {
            verifyWord(currentClickedLetter, 42);
        }
    }

    public void row5Column3Clicked() throws IOException {
        if (currentClickedLetter == 0) {
            currentClickedLetter = 43;
            Client.playClickSound();
            row5Column3.setTextFill(Color.GOLD);
        } else {
            verifyWord(currentClickedLetter, 43);
        }
    }

    public void row5Column4Clicked() throws IOException {
        if (currentClickedLetter == 0) {
            currentClickedLetter = 44;
            Client.playClickSound();
            row5Column4.setTextFill(Color.GOLD);
        } else {
            verifyWord(currentClickedLetter, 44);
        }
    }

    public void row5Column5Clicked() throws IOException {
        if (currentClickedLetter == 0) {
            currentClickedLetter = 45;
            Client.playClickSound();
            row5Column5.setTextFill(Color.GOLD);
        } else {
            verifyWord(currentClickedLetter, 45);
        }
    }

    public void row5Column6Clicked() throws IOException {
        if (currentClickedLetter == 0) {
            currentClickedLetter = 46;
            Client.playClickSound();
            row5Column6.setTextFill(Color.GOLD);
        } else {
            verifyWord(currentClickedLetter, 46);
        }
    }

    public void row5Column7Clicked() throws IOException {
        if (currentClickedLetter == 0) {
            currentClickedLetter = 47;
            Client.playClickSound();
            row5Column7.setTextFill(Color.GOLD);
        } else {
            verifyWord(currentClickedLetter, 47);
        }
    }

    public void row5Column8Clicked() throws IOException {
        if (currentClickedLetter == 0) {
            currentClickedLetter = 48;
            Client.playClickSound();
            row5Column8.setTextFill(Color.GOLD);
        } else {
            verifyWord(currentClickedLetter, 48);
        }
    }

    public void row5Column9Clicked() throws IOException {
        if (currentClickedLetter == 0) {
            currentClickedLetter = 49;
            Client.playClickSound();
            row5Column9.setTextFill(Color.GOLD);
        } else {
            verifyWord(currentClickedLetter, 49);
        }
    }

    public void row5Column10Clicked() throws IOException {
        if (currentClickedLetter == 0) {
            currentClickedLetter = 50;
            Client.playClickSound();
            row5Column10.setTextFill(Color.GOLD);
        } else {
            verifyWord(currentClickedLetter, 50);
        }
    }

    public void row6Column1Clicked() throws IOException {
        if (currentClickedLetter == 0) {
            currentClickedLetter = 51;
            Client.playClickSound();
            row6Column1.setTextFill(Color.GOLD);
        } else {
            verifyWord(currentClickedLetter, 51);
        }
    }

    public void row6Column2Clicked() throws IOException {
        if (currentClickedLetter == 0) {
            currentClickedLetter = 52;
            Client.playClickSound();
            row6Column2.setTextFill(Color.GOLD);
        } else {
            verifyWord(currentClickedLetter, 52);
        }
    }

    public void row6Column3Clicked() throws IOException {
        if (currentClickedLetter == 0) {
            currentClickedLetter = 53;
            Client.playClickSound();
            row6Column3.setTextFill(Color.GOLD);
        } else {
            verifyWord(currentClickedLetter, 53);
        }
    }

    public void row6Column4Clicked() throws IOException {
        if (currentClickedLetter == 0) {
            currentClickedLetter = 54;
            Client.playClickSound();
            row6Column4.setTextFill(Color.GOLD);
        } else {
            verifyWord(currentClickedLetter, 54);
        }
    }

    public void row6Column5Clicked() throws IOException {
        if (currentClickedLetter == 0) {
            currentClickedLetter = 55;
            Client.playClickSound();
            row6Column5.setTextFill(Color.GOLD);
        } else {
            verifyWord(currentClickedLetter, 55);
        }
    }

    public void row6Column6Clicked() throws IOException {
        if (currentClickedLetter == 0) {
            currentClickedLetter = 56;
            Client.playClickSound();
            row6Column6.setTextFill(Color.GOLD);
        } else {
            verifyWord(currentClickedLetter, 56);
        }
    }

    public void row6Column7Clicked() throws IOException {
        if (currentClickedLetter == 0) {
            currentClickedLetter = 57;
            Client.playClickSound();
            row6Column7.setTextFill(Color.GOLD);
        } else {
            verifyWord(currentClickedLetter, 57);
        }
    }

    public void row6Column8Clicked() throws IOException {
        if (currentClickedLetter == 0) {
            currentClickedLetter = 58;
            Client.playClickSound();
            row6Column8.setTextFill(Color.GOLD);
        } else {
            verifyWord(currentClickedLetter, 58);
        }
    }

    public void row6Column9Clicked() throws IOException {
        if (currentClickedLetter == 0) {
            currentClickedLetter = 59;
            Client.playClickSound();
            row6Column9.setTextFill(Color.GOLD);
        } else {
            verifyWord(currentClickedLetter, 59);
        }
    }

    public void row6Column10Clicked() throws IOException {
        if (currentClickedLetter == 0) {
            currentClickedLetter = 60;
            Client.playClickSound();
            row6Column10.setTextFill(Color.GOLD);
        } else {
            verifyWord(currentClickedLetter, 60);
        }
    }

    public void row7Column1Clicked() throws IOException {
        if (currentClickedLetter == 0) {
            currentClickedLetter = 61;
            Client.playClickSound();
            row7Column1.setTextFill(Color.GOLD);
        } else {
            verifyWord(currentClickedLetter, 61);
        }
    }

    public void row7Column2Clicked() throws IOException {
        if (currentClickedLetter == 0) {
            currentClickedLetter = 62;
            Client.playClickSound();
            row7Column2.setTextFill(Color.GOLD);
        } else {
            verifyWord(currentClickedLetter, 62);
        }
    }

    public void row7Column3Clicked() throws IOException {
        if (currentClickedLetter == 0) {
            currentClickedLetter = 63;
            Client.playClickSound();
            row7Column3.setTextFill(Color.GOLD);
        } else {
            verifyWord(currentClickedLetter, 63);
        }
    }

    public void row7Column4Clicked() throws IOException {
        if (currentClickedLetter == 0) {
            currentClickedLetter = 64;
            Client.playClickSound();
            row7Column4.setTextFill(Color.GOLD);
        } else {
            verifyWord(currentClickedLetter, 64);
        }
    }

    public void row7Column5Clicked() throws IOException {
        if (currentClickedLetter == 0) {
            currentClickedLetter = 65;
            Client.playClickSound();
            row7Column5.setTextFill(Color.GOLD);
        } else {
            verifyWord(currentClickedLetter, 65);
        }
    }

    public void row7Column6Clicked() throws IOException {
        if (currentClickedLetter == 0) {
            currentClickedLetter = 66;
            Client.playClickSound();
            row7Column6.setTextFill(Color.GOLD);
        } else {
            verifyWord(currentClickedLetter, 66);
        }
    }

    public void row7Column7Clicked() throws IOException {
        if (currentClickedLetter == 0) {
            currentClickedLetter = 67;
            Client.playClickSound();
            row7Column7.setTextFill(Color.GOLD);
        } else {
            verifyWord(currentClickedLetter, 67);
        }
    }

    public void row7Column8Clicked() throws IOException {
        if (currentClickedLetter == 0) {
            currentClickedLetter = 68;
            Client.playClickSound();
            row7Column8.setTextFill(Color.GOLD);
        } else {
            verifyWord(currentClickedLetter, 68);
        }
    }

    public void row7Column9Clicked() throws IOException {
        if (currentClickedLetter == 0) {
            currentClickedLetter = 69;
            Client.playClickSound();
            row7Column9.setTextFill(Color.GOLD);
        } else {
            verifyWord(currentClickedLetter, 69);
        }
    }

    public void row7Column10Clicked() throws IOException {
        if (currentClickedLetter == 0) {
            currentClickedLetter = 70;
            Client.playClickSound();
            row7Column10.setTextFill(Color.GOLD);
        } else {
            verifyWord(currentClickedLetter, 70);
        }
    }

    public void row8Column1Clicked() throws IOException {
        if (currentClickedLetter == 0) {
            currentClickedLetter = 71;
            Client.playClickSound();
            row8Column1.setTextFill(Color.GOLD);
        } else {
            verifyWord(currentClickedLetter, 71);
        }
    }

    public void row8Column2Clicked() throws IOException {
        if (currentClickedLetter == 0) {
            currentClickedLetter = 72;
            Client.playClickSound();
            row8Column2.setTextFill(Color.GOLD);
        } else {
            verifyWord(currentClickedLetter, 72);
        }
    }

    public void row8Column3Clicked() throws IOException {
        if (currentClickedLetter == 0) {
            currentClickedLetter = 73;
            Client.playClickSound();
            row8Column3.setTextFill(Color.GOLD);
        } else {
            verifyWord(currentClickedLetter, 73);
        }
    }

    public void row8Column4Clicked() throws IOException {
        if (currentClickedLetter == 0) {
            currentClickedLetter = 74;
            Client.playClickSound();
            row8Column4.setTextFill(Color.GOLD);
        } else {
            verifyWord(currentClickedLetter, 74);
        }
    }

    public void row8Column5Clicked() throws IOException {
        if (currentClickedLetter == 0) {
            currentClickedLetter = 75;
            Client.playClickSound();
            row8Column5.setTextFill(Color.GOLD);
        } else {
            verifyWord(currentClickedLetter, 75);
        }
    }

    public void row8Column6Clicked() throws IOException {
        if (currentClickedLetter == 0) {
            currentClickedLetter = 76;
            Client.playClickSound();
            row8Column6.setTextFill(Color.GOLD);
        } else {
            verifyWord(currentClickedLetter, 76);
        }
    }

    public void row8Column7Clicked() throws IOException {
        if (currentClickedLetter == 0) {
            currentClickedLetter = 77;
            Client.playClickSound();
            row8Column7.setTextFill(Color.GOLD);
        } else {
            verifyWord(currentClickedLetter, 77);
        }
    }

    public void row8Column8Clicked() throws IOException {
        if (currentClickedLetter == 0) {
            currentClickedLetter = 78;
            Client.playClickSound();
            row8Column8.setTextFill(Color.GOLD);
        } else {
            verifyWord(currentClickedLetter, 78);
        }
    }

    public void row8Column9Clicked() throws IOException {
        if (currentClickedLetter == 0) {
            currentClickedLetter = 79;
            Client.playClickSound();
            row8Column9.setTextFill(Color.GOLD);
        } else {
            verifyWord(currentClickedLetter, 79);
        }
    }

    public void row8Column10Clicked() throws IOException {
        if (currentClickedLetter == 0) {
            currentClickedLetter = 80;
            Client.playClickSound();
            row8Column10.setTextFill(Color.GOLD);
        } else {
            verifyWord(currentClickedLetter, 80);
        }
    }

    public void row9Column1Clicked() throws IOException {
        if (currentClickedLetter == 0) {
            currentClickedLetter = 81;
            Client.playClickSound();
            row9Column1.setTextFill(Color.GOLD);
        } else {
            verifyWord(currentClickedLetter, 81);
        }
    }

    public void row9Column2Clicked() throws IOException {
        if (currentClickedLetter == 0) {
            currentClickedLetter = 82;
            Client.playClickSound();
            row9Column2.setTextFill(Color.GOLD);
        } else {
            verifyWord(currentClickedLetter, 82);
        }
    }

    public void row9Column3Clicked() throws IOException {
        if (currentClickedLetter == 0) {
            currentClickedLetter = 83;
            Client.playClickSound();
            row9Column3.setTextFill(Color.GOLD);
        } else {
            verifyWord(currentClickedLetter, 83);
        }
    }

    public void row9Column4Clicked() throws IOException {
        if (currentClickedLetter == 0) {
            currentClickedLetter = 84;
            Client.playClickSound();
            row9Column4.setTextFill(Color.GOLD);
        } else {
            verifyWord(currentClickedLetter, 84);
        }
    }

    public void row9Column5Clicked() throws IOException {
        if (currentClickedLetter == 0) {
            currentClickedLetter = 85;
            Client.playClickSound();
            row9Column5.setTextFill(Color.GOLD);
        } else {
            verifyWord(currentClickedLetter, 85);
        }
    }

    public void row9Column6Clicked() throws IOException {
        if (currentClickedLetter == 0) {
            currentClickedLetter = 86;
            Client.playClickSound();
            row9Column6.setTextFill(Color.GOLD);
        } else {
            verifyWord(currentClickedLetter, 86);
        }
    }

    public void row9Column7Clicked() throws IOException {
        if (currentClickedLetter == 0) {
            currentClickedLetter = 87;
            Client.playClickSound();
            row9Column7.setTextFill(Color.GOLD);
        } else {
            verifyWord(currentClickedLetter, 87);
        }
    }

    public void row9Column8Clicked() throws IOException {
        if (currentClickedLetter == 0) {
            currentClickedLetter = 88;
            Client.playClickSound();
            row9Column8.setTextFill(Color.GOLD);
        } else {
            verifyWord(currentClickedLetter, 88);
        }
    }

    public void row9Column9Clicked() throws IOException {
        if (currentClickedLetter == 0) {
            currentClickedLetter = 89;
            Client.playClickSound();
            row9Column9.setTextFill(Color.GOLD);
        } else {
            verifyWord(currentClickedLetter, 89);
        }
    }

    public void row9Column10Clicked() throws IOException {
        if (currentClickedLetter == 0) {
            currentClickedLetter = 90;
            Client.playClickSound();
            row9Column10.setTextFill(Color.GOLD);
        } else {
            verifyWord(currentClickedLetter, 90);
        }
    }

    public void row10Column1Clicked() throws IOException {
        if (currentClickedLetter == 0) {
            currentClickedLetter = 91;
            Client.playClickSound();
            row10Column1.setTextFill(Color.GOLD);
        } else {
            verifyWord(currentClickedLetter, 91);
        }
    }

    public void row10Column2Clicked() throws IOException {
        if (currentClickedLetter == 0) {
            currentClickedLetter = 92;
            Client.playClickSound();
            row10Column2.setTextFill(Color.GOLD);
        } else {
            verifyWord(currentClickedLetter, 92);
        }
    }

    public void row10Column3Clicked() throws IOException {
        if (currentClickedLetter == 0) {
            currentClickedLetter = 93;
            Client.playClickSound();
            row10Column3.setTextFill(Color.GOLD);
        } else {
            verifyWord(currentClickedLetter, 93);
        }
    }

    public void row10Column4Clicked() throws IOException {
        if (currentClickedLetter == 0) {
            currentClickedLetter = 94;
            Client.playClickSound();
            row10Column4.setTextFill(Color.GOLD);
        } else {
            verifyWord(currentClickedLetter, 94);
        }
    }

    public void row10Column5Clicked() throws IOException {
        if (currentClickedLetter == 0) {
            currentClickedLetter = 95;
            Client.playClickSound();
            row10Column5.setTextFill(Color.GOLD);
        } else {
            verifyWord(currentClickedLetter, 95);
        }
    }

    public void row10Column6Clicked() throws IOException {
        if (currentClickedLetter == 0) {
            currentClickedLetter = 96;
            Client.playClickSound();
            row10Column6.setTextFill(Color.GOLD);
        } else {
            verifyWord(currentClickedLetter, 96);
        }
    }

    public void row10Column7Clicked() throws IOException {
        if (currentClickedLetter == 0) {
            currentClickedLetter = 97;
            Client.playClickSound();
            row10Column7.setTextFill(Color.GOLD);
        } else {
            verifyWord(currentClickedLetter, 97);
        }
    }

    public void row10Column8Clicked() throws IOException {
        if (currentClickedLetter == 0) {
            currentClickedLetter = 98;
            Client.playClickSound();
            row10Column8.setTextFill(Color.GOLD);
        } else {
            verifyWord(currentClickedLetter, 98);
        }
    }

    public void row10Column9Clicked() throws IOException {
        if (currentClickedLetter == 0) {
            currentClickedLetter = 99;
            Client.playClickSound();
            row10Column9.setTextFill(Color.GOLD);
        } else {
            verifyWord(currentClickedLetter, 99);
        }
    }

    public void row10Column10Clicked() throws IOException {
        if (currentClickedLetter == 0) {
            currentClickedLetter = 100;
            Client.playClickSound();
            row10Column10.setTextFill(Color.GOLD);
        } else {
            verifyWord(currentClickedLetter, 100);
        }
    }

    // Remove all letter highlighting
    public void removeLetterColours() {
        row1Column1.setTextFill(Color.BLACK);
        row1Column2.setTextFill(Color.BLACK);
        row1Column3.setTextFill(Color.BLACK);
        row1Column4.setTextFill(Color.BLACK);
        row1Column5.setTextFill(Color.BLACK);
        row1Column6.setTextFill(Color.BLACK);
        row1Column7.setTextFill(Color.BLACK);
        row1Column8.setTextFill(Color.BLACK);
        row1Column9.setTextFill(Color.BLACK);
        row1Column10.setTextFill(Color.BLACK);
        row2Column1.setTextFill(Color.BLACK);
        row2Column2.setTextFill(Color.BLACK);
        row2Column3.setTextFill(Color.BLACK);
        row2Column4.setTextFill(Color.BLACK);
        row2Column5.setTextFill(Color.BLACK);
        row2Column6.setTextFill(Color.BLACK);
        row2Column7.setTextFill(Color.BLACK);
        row2Column8.setTextFill(Color.BLACK);
        row2Column9.setTextFill(Color.BLACK);
        row2Column10.setTextFill(Color.BLACK);
        row3Column1.setTextFill(Color.BLACK);
        row3Column2.setTextFill(Color.BLACK);
        row3Column3.setTextFill(Color.BLACK);
        row3Column4.setTextFill(Color.BLACK);
        row3Column5.setTextFill(Color.BLACK);
        row3Column6.setTextFill(Color.BLACK);
        row3Column7.setTextFill(Color.BLACK);
        row3Column8.setTextFill(Color.BLACK);
        row3Column9.setTextFill(Color.BLACK);
        row3Column10.setTextFill(Color.BLACK);
        row4Column1.setTextFill(Color.BLACK);
        row4Column2.setTextFill(Color.BLACK);
        row4Column3.setTextFill(Color.BLACK);
        row4Column4.setTextFill(Color.BLACK);
        row4Column5.setTextFill(Color.BLACK);
        row4Column6.setTextFill(Color.BLACK);
        row4Column7.setTextFill(Color.BLACK);
        row4Column8.setTextFill(Color.BLACK);
        row4Column9.setTextFill(Color.BLACK);
        row4Column10.setTextFill(Color.BLACK);
        row5Column1.setTextFill(Color.BLACK);
        row5Column2.setTextFill(Color.BLACK);
        row5Column3.setTextFill(Color.BLACK);
        row5Column4.setTextFill(Color.BLACK);
        row5Column5.setTextFill(Color.BLACK);
        row5Column6.setTextFill(Color.BLACK);
        row5Column7.setTextFill(Color.BLACK);
        row5Column8.setTextFill(Color.BLACK);
        row5Column9.setTextFill(Color.BLACK);
        row5Column10.setTextFill(Color.BLACK);
        row6Column1.setTextFill(Color.BLACK);
        row6Column2.setTextFill(Color.BLACK);
        row6Column3.setTextFill(Color.BLACK);
        row6Column4.setTextFill(Color.BLACK);
        row6Column5.setTextFill(Color.BLACK);
        row6Column6.setTextFill(Color.BLACK);
        row6Column7.setTextFill(Color.BLACK);
        row6Column8.setTextFill(Color.BLACK);
        row6Column9.setTextFill(Color.BLACK);
        row6Column10.setTextFill(Color.BLACK);
        row7Column1.setTextFill(Color.BLACK);
        row7Column2.setTextFill(Color.BLACK);
        row7Column3.setTextFill(Color.BLACK);
        row7Column4.setTextFill(Color.BLACK);
        row7Column5.setTextFill(Color.BLACK);
        row7Column6.setTextFill(Color.BLACK);
        row7Column7.setTextFill(Color.BLACK);
        row7Column8.setTextFill(Color.BLACK);
        row7Column9.setTextFill(Color.BLACK);
        row7Column10.setTextFill(Color.BLACK);
        row8Column1.setTextFill(Color.BLACK);
        row8Column2.setTextFill(Color.BLACK);
        row8Column3.setTextFill(Color.BLACK);
        row8Column4.setTextFill(Color.BLACK);
        row8Column5.setTextFill(Color.BLACK);
        row8Column6.setTextFill(Color.BLACK);
        row8Column7.setTextFill(Color.BLACK);
        row8Column8.setTextFill(Color.BLACK);
        row8Column9.setTextFill(Color.BLACK);
        row8Column10.setTextFill(Color.BLACK);
        row9Column1.setTextFill(Color.BLACK);
        row9Column2.setTextFill(Color.BLACK);
        row9Column3.setTextFill(Color.BLACK);
        row9Column4.setTextFill(Color.BLACK);
        row9Column5.setTextFill(Color.BLACK);
        row9Column6.setTextFill(Color.BLACK);
        row9Column7.setTextFill(Color.BLACK);
        row9Column8.setTextFill(Color.BLACK);
        row9Column9.setTextFill(Color.BLACK);
        row9Column10.setTextFill(Color.BLACK);
        row10Column1.setTextFill(Color.BLACK);
        row10Column2.setTextFill(Color.BLACK);
        row10Column3.setTextFill(Color.BLACK);
        row10Column4.setTextFill(Color.BLACK);
        row10Column5.setTextFill(Color.BLACK);
        row10Column6.setTextFill(Color.BLACK);
        row10Column7.setTextFill(Color.BLACK);
        row10Column8.setTextFill(Color.BLACK);
        row10Column9.setTextFill(Color.BLACK);
        row10Column10.setTextFill(Color.BLACK);
    }

    // Set text of FX elements in grid depending on finalised words
    public void setLetters() {
        row1Column1.setText((String.valueOf(finalisedLetters[0][0]).toUpperCase()));
        row1Column2.setText((String.valueOf(finalisedLetters[0][1]).toUpperCase()));
        row1Column3.setText((String.valueOf(finalisedLetters[0][2]).toUpperCase()));
        row1Column4.setText((String.valueOf(finalisedLetters[0][3]).toUpperCase()));
        row1Column5.setText((String.valueOf(finalisedLetters[0][4]).toUpperCase()));
        row1Column6.setText((String.valueOf(finalisedLetters[0][5]).toUpperCase()));
        row1Column7.setText((String.valueOf(finalisedLetters[0][6]).toUpperCase()));
        row1Column8.setText((String.valueOf(finalisedLetters[0][7]).toUpperCase()));
        row1Column9.setText((String.valueOf(finalisedLetters[0][8]).toUpperCase()));
        row1Column10.setText((String.valueOf(finalisedLetters[0][9]).toUpperCase()));
        row2Column1.setText((String.valueOf(finalisedLetters[1][0]).toUpperCase()));
        row2Column2.setText((String.valueOf(finalisedLetters[1][1]).toUpperCase()));
        row2Column3.setText((String.valueOf(finalisedLetters[1][2]).toUpperCase()));
        row2Column4.setText((String.valueOf(finalisedLetters[1][3]).toUpperCase()));
        row2Column5.setText((String.valueOf(finalisedLetters[1][4]).toUpperCase()));
        row2Column6.setText((String.valueOf(finalisedLetters[1][5]).toUpperCase()));
        row2Column7.setText((String.valueOf(finalisedLetters[1][6]).toUpperCase()));
        row2Column8.setText((String.valueOf(finalisedLetters[1][7]).toUpperCase()));
        row2Column9.setText((String.valueOf(finalisedLetters[1][8]).toUpperCase()));
        row2Column10.setText((String.valueOf(finalisedLetters[1][9]).toUpperCase()));
        row3Column1.setText((String.valueOf(finalisedLetters[2][0]).toUpperCase()));
        row3Column2.setText((String.valueOf(finalisedLetters[2][1]).toUpperCase()));
        row3Column3.setText((String.valueOf(finalisedLetters[2][2]).toUpperCase()));
        row3Column4.setText((String.valueOf(finalisedLetters[2][3]).toUpperCase()));
        row3Column5.setText((String.valueOf(finalisedLetters[2][4]).toUpperCase()));
        row3Column6.setText((String.valueOf(finalisedLetters[2][5]).toUpperCase()));
        row3Column7.setText((String.valueOf(finalisedLetters[2][6]).toUpperCase()));
        row3Column8.setText((String.valueOf(finalisedLetters[2][7]).toUpperCase()));
        row3Column9.setText((String.valueOf(finalisedLetters[2][8]).toUpperCase()));
        row3Column10.setText((String.valueOf(finalisedLetters[2][9]).toUpperCase()));
        row4Column1.setText((String.valueOf(finalisedLetters[3][0]).toUpperCase()));
        row4Column2.setText((String.valueOf(finalisedLetters[3][1]).toUpperCase()));
        row4Column3.setText((String.valueOf(finalisedLetters[3][2]).toUpperCase()));
        row4Column4.setText((String.valueOf(finalisedLetters[3][3]).toUpperCase()));
        row4Column5.setText((String.valueOf(finalisedLetters[3][4]).toUpperCase()));
        row4Column6.setText((String.valueOf(finalisedLetters[3][5]).toUpperCase()));
        row4Column7.setText((String.valueOf(finalisedLetters[3][6]).toUpperCase()));
        row4Column8.setText((String.valueOf(finalisedLetters[3][7]).toUpperCase()));
        row4Column9.setText((String.valueOf(finalisedLetters[3][8]).toUpperCase()));
        row4Column10.setText((String.valueOf(finalisedLetters[3][9]).toUpperCase()));
        row5Column1.setText((String.valueOf(finalisedLetters[4][0]).toUpperCase()));
        row5Column2.setText((String.valueOf(finalisedLetters[4][1]).toUpperCase()));
        row5Column3.setText((String.valueOf(finalisedLetters[4][2]).toUpperCase()));
        row5Column4.setText((String.valueOf(finalisedLetters[4][3]).toUpperCase()));
        row5Column5.setText((String.valueOf(finalisedLetters[4][4]).toUpperCase()));
        row5Column6.setText((String.valueOf(finalisedLetters[4][5]).toUpperCase()));
        row5Column7.setText((String.valueOf(finalisedLetters[4][6]).toUpperCase()));
        row5Column8.setText((String.valueOf(finalisedLetters[4][7]).toUpperCase()));
        row5Column9.setText((String.valueOf(finalisedLetters[4][8]).toUpperCase()));
        row5Column10.setText((String.valueOf(finalisedLetters[4][9]).toUpperCase()));
        row6Column1.setText((String.valueOf(finalisedLetters[5][0]).toUpperCase()));
        row6Column2.setText((String.valueOf(finalisedLetters[5][1]).toUpperCase()));
        row6Column3.setText((String.valueOf(finalisedLetters[5][2]).toUpperCase()));
        row6Column4.setText((String.valueOf(finalisedLetters[5][3]).toUpperCase()));
        row6Column5.setText((String.valueOf(finalisedLetters[5][4]).toUpperCase()));
        row6Column6.setText((String.valueOf(finalisedLetters[5][5]).toUpperCase()));
        row6Column7.setText((String.valueOf(finalisedLetters[5][6]).toUpperCase()));
        row6Column8.setText((String.valueOf(finalisedLetters[5][7]).toUpperCase()));
        row6Column9.setText((String.valueOf(finalisedLetters[5][8]).toUpperCase()));
        row6Column10.setText((String.valueOf(finalisedLetters[5][9]).toUpperCase()));
        row7Column1.setText((String.valueOf(finalisedLetters[6][1]).toUpperCase()));
        row7Column3.setText((String.valueOf(finalisedLetters[6][2]).toUpperCase()));
        row7Column4.setText((String.valueOf(finalisedLetters[6][3]).toUpperCase()));
        row7Column5.setText((String.valueOf(finalisedLetters[6][4]).toUpperCase()));
        row7Column6.setText((String.valueOf(finalisedLetters[6][5]).toUpperCase()));
        row7Column7.setText((String.valueOf(finalisedLetters[6][6]).toUpperCase()));
        row7Column8.setText((String.valueOf(finalisedLetters[6][7]).toUpperCase()));
        row7Column9.setText((String.valueOf(finalisedLetters[6][8]).toUpperCase()));
        row7Column10.setText((String.valueOf(finalisedLetters[6][9]).toUpperCase()));
        row8Column1.setText((String.valueOf(finalisedLetters[7][0]).toUpperCase()));
        row8Column2.setText((String.valueOf(finalisedLetters[7][1]).toUpperCase()));
        row8Column3.setText((String.valueOf(finalisedLetters[7][2]).toUpperCase()));
        row8Column4.setText((String.valueOf(finalisedLetters[7][3]).toUpperCase()));
        row8Column5.setText((String.valueOf(finalisedLetters[7][4]).toUpperCase()));
        row8Column6.setText((String.valueOf(finalisedLetters[7][5]).toUpperCase()));
        row8Column7.setText((String.valueOf(finalisedLetters[7][6]).toUpperCase()));
        row8Column8.setText((String.valueOf(finalisedLetters[7][7]).toUpperCase()));
        row8Column9.setText((String.valueOf(finalisedLetters[7][8]).toUpperCase()));
        row8Column10.setText((String.valueOf(finalisedLetters[7][9]).toUpperCase()));
        row9Column1.setText((String.valueOf(finalisedLetters[8][0]).toUpperCase()));
        row9Column2.setText((String.valueOf(finalisedLetters[8][1]).toUpperCase()));
        row9Column3.setText((String.valueOf(finalisedLetters[8][2]).toUpperCase()));
        row9Column4.setText((String.valueOf(finalisedLetters[8][3]).toUpperCase()));
        row9Column5.setText((String.valueOf(finalisedLetters[8][4]).toUpperCase()));
        row9Column6.setText((String.valueOf(finalisedLetters[8][5]).toUpperCase()));
        row9Column7.setText((String.valueOf(finalisedLetters[8][6]).toUpperCase()));
        row9Column8.setText((String.valueOf(finalisedLetters[8][7]).toUpperCase()));
        row9Column9.setText((String.valueOf(finalisedLetters[8][8]).toUpperCase()));
        row9Column10.setText((String.valueOf(finalisedLetters[8][9]).toUpperCase()));
        row10Column1.setText((String.valueOf(finalisedLetters[9][0]).toUpperCase()));
        row10Column2.setText((String.valueOf(finalisedLetters[9][1]).toUpperCase()));
        row10Column3.setText((String.valueOf(finalisedLetters[9][2]).toUpperCase()));
        row10Column4.setText((String.valueOf(finalisedLetters[9][3]).toUpperCase()));
        row10Column5.setText((String.valueOf(finalisedLetters[9][4]).toUpperCase()));
        row10Column6.setText((String.valueOf(finalisedLetters[9][5]).toUpperCase()));
        row10Column7.setText((String.valueOf(finalisedLetters[9][6]).toUpperCase()));
        row10Column8.setText((String.valueOf(finalisedLetters[9][7]).toUpperCase()));
        row10Column9.setText((String.valueOf(finalisedLetters[9][8]).toUpperCase()));
        row10Column10.setText((String.valueOf(finalisedLetters[9][9]).toUpperCase()));

    }

    // Return to play menu if user clicks return
    public void returnButtonClicked() throws IOException {
        Client.playClickSound();
        Scene scene = returnToMenuButton.getScene();
        Window window = scene.getWindow();
        Stage stage = (Stage) window;
        Parent playMenu = FXMLLoader.load(getClass().getResource("PlayMenu.fxml"));
        stage.setTitle("Play Menu");
        stage.getScene().setRoot(playMenu);
        stage.show();
    }

    // When user clicks ok to "You have run out of time" message
    public void okayButtonClicked() throws IOException {
        loseGame();
    }

    public void loseGame() throws IOException {
        //Client.playIncorrectSound();
        if (Client.getCurrentlyPractising()){
            // Reset user's streak if in practice
            Client.client.setPracticeCurrentStreak(0);
        }
        else {
            // Decrement lives and increment score if user's in endless mode
            Client.client.setCurrentEndlessLives(Client.client.getCurrentEndlessLives() - 1);
            Client.client.setCurrentEndlessScore(Client.client.getCurrentEndlessScore() + 1);
        }
        // Load next screen depending on if user is in practice or endless mode
        Scene scene = line1.getScene();
        Window window = scene.getWindow();
        Stage stage = (Stage) window;
        FXMLLoader loader = new FXMLLoader();
        if (Client.getCurrentlyPractising()) {
            Client.setNeedPracticePopup(true);
            Parent practiceMode = loader.load(getClass().getResource("PracticeModeEndless.fxml"));
            stage.setTitle("Practice Mode");
            stage.getScene().setRoot(practiceMode);
            stage.show();
        }
        else {
            Parent endlessMode = loader.load(getClass().getResource("ContinueEndlessMode.fxml"));
            stage.setTitle("Endless Mode");
            stage.getScene().setRoot(endlessMode);
            stage.show();
        }
    }


}
