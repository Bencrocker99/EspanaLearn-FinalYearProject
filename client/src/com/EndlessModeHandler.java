package src.com;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.Window;


import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import static java.lang.Character.toUpperCase;

public class EndlessModeHandler {
    private int currentLevelNumber;
    private int currentScore;
    private int random;
    private int currentLives; // 1 free life every 10 max of 3?
    private Random randomGenerator = new Random();
    private String currentTranslation;
    private boolean englishToSpanish;
    private Word word;
    private String exactWord;
    private ArrayList<Word> currentWords = new ArrayList<Word>();
    private ArrayList<Topic> currentStructure = new ArrayList<Topic>();
    private ArrayList<String> currentExactWords = new ArrayList<String>();
    private boolean currentTranslationCorrect;
    private TranslationHandler translationHandler;
    private Level currentLevel;
    private boolean bossLevel;
    private HashMap<Integer, Level> levelsHashMap = new HashMap<Integer, Level>();
    private HashMap<String, Topic> topicsHashMap = new HashMap<String, Topic>();
    private EndlessModeHandler endlessModeHandler;
    private BossMode1Handler bossMode1Handler;
    private boolean bossLevelCleared;
    private BossMode2Handler bossMode2Handler;
    private int pluralsChance;
    private boolean currentWordPlural;
    private ArrayList<String> possibleCorrectAnswer = new ArrayList<String>();
    private boolean gameover = false;


    @FXML
    Button continueButton;
    @FXML
    AnchorPane newLevelPane;
    @FXML
    Label newLevelLabel;
    @FXML
    Button startEndlessButton;
    @FXML
    Label currentQuestion;
    @FXML
    Button submitButton;
    @FXML
    TextField userInput;
    @FXML
    Button returnToMenuButton;
    @FXML
    Label score;
    @FXML
    Label highestScore;
    @FXML
    Label highestLevel;
    @FXML
    Label currentlevelLabel;
    @FXML
    Button accent1Button;
    @FXML
    Button accent2Button;
    @FXML
    Button accent3Button;
    @FXML
    Button accent4Button;
    @FXML
    Button accent5Button;
    @FXML
    Button accent6Button;
    @FXML
    Button accent7Button;
    @FXML
    Label activateUnlockedSkips;
    @FXML
    Button skipToLevel2Button;
    @FXML
    Button skipToLevel3Button;
    @FXML
    Button skipToLevel4BossButton;
    @FXML
    Button skipToLevel4Button;
    @FXML
    ImageView heart1;
    @FXML
    ImageView heart2;
    @FXML
    ImageView heart3;
    @FXML
    ImageView heart4;
    @FXML
    ImageView heart5;
    @FXML
    Label correctLabel;
    @FXML
    Label incorrectLabel;
    @FXML
    Button skipToLevel3BossButton;
    @FXML
    AnchorPane anchorPane;


    public EndlessModeHandler() throws AWTException {
        translationHandler = new TranslationHandler();
        bossMode1Handler = new BossMode1Handler();
        bossMode2Handler = new BossMode2Handler();
        EndlessModeHandler endlessModeHandler = this;
        levelsHashMap = Client.getLevelsHashMap();
        topicsHashMap = Client.getTopicsHashMap();
    }


    public void endlessMode() throws IOException {
        // Reset variables
        Client.setCurrentlyPractising(false);
        userInput.setText("");
        currentExactWords.clear();
        currentWords.clear();
        gameover = false;
        switch (currentLevelNumber) { // Send to specific boss level if user has reached that level
            case -1:
                bossLevel1();
                return;
            case -2:
                bossLevel2();
                return;
            case -3:
                bossLevel3();
                return;
            case -4:
                bossLevel4();
                return;
        }
        // Choose whether english to spanish or spanish to english
        random = randomGenerator.nextInt(2);
        if (random == 0) {
            englishToSpanish = false;
            currentTranslation = "Translate from Spanish to English: ";
        } else {
            englishToSpanish = true;
            currentTranslation = "Translate from English to Spanish: ";
        }
        currentTranslationCorrect = false;
        currentLevel = levelsHashMap.get(currentLevelNumber);
        // Update if this is client's highest level
        if (currentLevelNumber > Client.getHighestLevel()) {
            Client.setHighestLevel(currentLevelNumber);
        }
        highestLevel.setText("Highest Level: " + Client.getHighestLevel());
        random = (randomGenerator.nextInt(100)) + 1;
        int currentIndex = 0;
        // Pick a current structure within that level, according to the probability of picking each structure
        while (random > 0) {
            int currentProbability = currentLevel.getOrderedProbabilities().get(currentIndex);
            random = random - currentProbability;
            if (random > 0) {
                currentIndex++;
            } else break;
        }
        currentStructure = currentLevel.getPossibleStructures().get(currentIndex);
        pluralsChance = currentLevel.getIncludePluralsChance();

        for (int j = 0; j < currentStructure.size(); j++) {
            // Randomly determine whether current word is plural based on plurals probability for that level
            int currentTopicSize = currentStructure.get(j).getTopicWords().size();
            random = randomGenerator.nextInt(currentTopicSize);
            word = currentStructure.get(j).getTopicWords().get(random);
            random = randomGenerator.nextInt(100);
            if (random > pluralsChance) {
                currentWordPlural = false;
            } else currentWordPlural = true;
            // Add word object to currentWords
            currentWords.add(word);

            // Get exact word string based on language and plural or singular, and add to currentExactWords
            if (englishToSpanish) {
                if (currentStructure.get(j).getTopicName().contains("Nouns") && currentWordPlural) {
                    exactWord = word.getEnglishPlural();
                } else {
                    exactWord = word.getMainTranslation();
                }

            } else if (currentStructure.get(j).getTopicName().contains("Nouns") && currentWordPlural) {
                exactWord = word.getSpanishPlural();
            } else {
                exactWord = word.getWord();
            }
            currentExactWords.add(exactWord);
        }

        for (int i = 0; i < currentWords.size(); i++) {
            // If word is a determinant, correct its ending
            if (currentWords.get(i).getWordTopic().contains("Determinants")) {
                String correctDeterminant = translationHandler.determinantCorrecter(i, currentWords, currentExactWords, englishToSpanish);
                currentExactWords.set(i, correctDeterminant);
            }
            // conjugate verbs, verb should refer to nearest noun, if there isn't one then be random
            if (currentWords.get(i).getWordTopic().contains("Verbs")) {
                random = randomGenerator.nextInt(currentLevel.getPossibleTenses().get(currentIndex).size());
                String tense = currentLevel.getPossibleTenses().get(currentIndex).get(random);
                int person = -1;
                boolean needNoun = true;
                for (int j = i; j >= 0; j--) {
                    // If there is a noun to refer to, use that
                    if (currentWords.get(j).getWordTopic().contains("Nouns")) {
                        needNoun = false;
                        if (currentExactWords.get(j).equals(currentWords.get(j).getEnglishPlural()) ||
                                currentExactWords.get(j).equals(currentWords.get(j).getSpanishPlural())) {
                            person = 6;
                        } else person = 3;
                    }
                }
                // If there's no noun, randomly assign verb ending
                if (needNoun) {
                    random = randomGenerator.nextInt(6);
                    person = random + 1;
                }
                // if there is a noun before verb dont need the I you etc before verb in english
                String correctVerb = translationHandler.verbConjugator(currentWords.get(i), englishToSpanish, tense, person, needNoun, true);
                currentExactWords.set(i, correctVerb);
            }
            if (currentWords.get(i).getWordTopic().contains("Adjectives")) { // Adjectives need to agree with noun/certain verbs
                String correctAdjective = translationHandler.adjectiveAgree(i, currentWords, currentExactWords, true, englishToSpanish);
                currentExactWords.set(i, correctAdjective);
            }
        }

        if (englishToSpanish) { // Word order is structures follows Spanish principles, so need to correct if initial phrase is English instead
            currentExactWords = translationHandler.wordOrderCorrecter(currentWords, currentExactWords);
            currentWords = translationHandler.wordOrderCorrecter(currentWords);
        }

        // "a" followed by "el" in Spanish become the single word "al", so need to check for this, change "a" to "al" and remove "el"
        if (!englishToSpanish) {
            ArrayList<Integer> wordsToRemove = new ArrayList<Integer>(); // Store positions of elements which need to be removed
            for (int i = 0; i < currentExactWords.size() - 1; i++) {
                if (currentExactWords.get(i).equals("a") && currentExactWords.get(i + 1).equals("el")) {
                    currentExactWords.set(i, currentWords.get(i).getAlternateSpanishTranslations().get(0)); // a el becomes al
                    wordsToRemove.add(i + 1);
                }
            }
            for (int i = wordsToRemove.size() - 1; i >= 0; i--) { // Remove elements in reverse order to prevent concurrent exceptions
                int position = wordsToRemove.get(i);
                currentWords.remove(position);
                currentExactWords.remove(position);
            }
        }


        boolean containsQuestion = false;
        for (int i = 0; i < currentExactWords.size(); i++) {
            if (currentWords.get(i).getWordTopic().contains("Questions")) {
                currentTranslation = currentTranslation + "¿"; // Add questions marks for questions
                containsQuestion = true;
            }
            if (i == 0) { // Capitalise the first word
                currentTranslation = currentTranslation + toUpperCase(currentExactWords.get(i).charAt(0)) + currentExactWords.get(i).substring(1) + " ";
            } else {
                currentTranslation = currentTranslation + currentExactWords.get(i) + " ";
            }
        }
        // Add ending punctuation without a space (question mark for question, else normal full stop)
        if (containsQuestion) {
            currentTranslation = currentTranslation.substring(0, currentTranslation.length() - 1) + "?";
        } else
            currentTranslation = currentTranslation.substring(0, currentTranslation.length() - 1) + ".";


        currentQuestion.setText(currentTranslation);


    }

    // Load play menu if user clicks return
    public void returnButtonClicked() throws IOException {
        Client.playClickSound();
        endlessModeHandler = null;
        translationHandler = null;
        bossMode1Handler = null;
        bossMode2Handler = null;
        Scene scene = returnToMenuButton.getScene();
        Window window = scene.getWindow();
        Stage stage = (Stage) window;
        Parent playMenu = FXMLLoader.load(getClass().getResource("PlayMenu.fxml"));
        stage.setTitle("Play Menu");
        stage.getScene().setRoot(playMenu);
        stage.show();
    }

    // Set up FX elements
    public void gameSetup(boolean beginningGame) throws IOException {
        score.setText("Current Score: " + currentScore);
        highestScore.setText("Highest Score: " + Client.getHighestScore());
        currentlevelLabel.setText("Current Level: " + currentLevelNumber);
        if (beginningGame) {
            startEndlessButton.setVisible(false); // if first game, begin button needs to be invisible, otherwise continue button does
        } else continueButton.setVisible(false);
        accent1Button.setVisible(true);
        accent2Button.setVisible(true);
        accent3Button.setVisible(true);
        accent4Button.setVisible(true);
        accent5Button.setVisible(true);
        accent6Button.setVisible(true);
        accent7Button.setVisible(true);
        activateUnlockedSkips.setVisible(false);
        if (Client.getAchievementsProgress().get("1achievement5") >= Client.getAchievementsList().get(4).getNumberOfTimes()) {
            skipToLevel2Button.setVisible(true);
            activateUnlockedSkips.setVisible(true);
        }
        if (Client.getAchievementsProgress().get("2achievement5") >= Client.getAchievementsList().get(9).getNumberOfTimes()) {
            skipToLevel3Button.setVisible(true);
            activateUnlockedSkips.setVisible(true);
        }
        if (Client.getAchievementsProgress().get("3achievement5") >= Client.getAchievementsList().get(14).getNumberOfTimes()) {
            skipToLevel4Button.setVisible(true);
            activateUnlockedSkips.setVisible(true);
        }
        endlessMode();
    }

    // Begin endless mode when user clicks start
    public void startEndlessButtonClicked() throws IOException {
        Client.playClickSound();
        currentLevelNumber = 1;
        currentScore = 0;
        currentLives = 3;
        handleLives();
        gameSetup(true);
    }

    // Draw correct number of hearts for lives
    public void handleLives() {
        switch (currentLives) {
            case 0:
                // End game if user is on 0 lives
                currentQuestion.setText("Game Over! Try Again!");
                heart1.setVisible(false);
                heart2.setVisible(false);
                heart3.setVisible(false);
                heart4.setVisible(false);
                heart5.setVisible(false);
                Client.playGameoverSound();
                endGame();
                gameover = true;
                break;
            case 1:
                heart1.setVisible(true);
                heart2.setVisible(false);
                heart3.setVisible(false);
                heart4.setVisible(false);
                heart5.setVisible(false);
                break;
            case 2:
                heart1.setVisible(true);
                heart2.setVisible(true);
                heart3.setVisible(false);
                heart4.setVisible(false);
                heart5.setVisible(false);
                break;
            case 3:
                heart1.setVisible(true);
                heart2.setVisible(true);
                heart3.setVisible(true);
                heart4.setVisible(false);
                heart5.setVisible(false);
                break;
            case 4:
                heart1.setVisible(true);
                heart2.setVisible(true);
                heart3.setVisible(true);
                heart4.setVisible(true);
                heart5.setVisible(false);
                break;
            case 5:
                heart1.setVisible(true);
                heart2.setVisible(true);
                heart3.setVisible(true);
                heart4.setVisible(true);
                heart5.setVisible(true);
                break;
            default:
                heart1.setVisible(true);
                heart2.setVisible(true);
                heart3.setVisible(true);
                heart4.setVisible(true);
                heart5.setVisible(true);
                break;
        }
    }

    // Skip to level 2
    public void skipToLevel2ButtonClicked() throws IOException {
        Client.playClickSound();
        currentLevelNumber = 2;
        currentScore = levelsHashMap.get(1).getEndingScore();
        currentLives = 3;
        handleLives();
        gameSetup(true);
    }

    // Skip to level 3
    public void skipToLevel3ButtonClicked() throws IOException {
        Client.playClickSound();
        currentLevelNumber = 3;
        currentScore = levelsHashMap.get(2).getEndingScore();
        currentLives = 3;
        handleLives();
        gameSetup(true);
    }

    // Skip to level 4
    public void skipToLevel4ButtonClicked() throws IOException {
        Client.playClickSound();
        currentLevelNumber = 4;
        currentScore = levelsHashMap.get(3).getEndingScore();
        currentLives = 3;
        handleLives();
        gameSetup(true);
    }

    // Skip to boss level 3 (testing purposes)
    public void skipToLevel3BossButtonClicked() throws IOException {
        Client.playClickSound();
        currentLevelNumber = -3;
        currentScore = levelsHashMap.get(2).getEndingScore();
        currentLives = 3;
        bossLevel3();
    }

    // Skip to boss level 4 (testing purposes)
    public void skipToLevel4BossButtonClicked() throws IOException {
        Client.playClickSound();
        currentLevelNumber = -4;
        currentScore = levelsHashMap.get(3).getEndingScore();
        currentLives = 3;
        bossLevel4();
    }


    // Insert accented character into textfield when user click button
    public void accent1ButtonClicked() {
        if (!gameover) {
            Client.playClickSound();
            userInput.setText(userInput.getText() + "á");
        }
    }

    // Insert accented character into textfield when user click button
    public void accent2ButtonClicked() {
        if (!gameover) {
            Client.playClickSound();
            userInput.setText(userInput.getText() + "é");
        }
    }

    // Insert accented character into textfield when user click button
    public void accent3ButtonClicked() {
        if (!gameover) {
            Client.playClickSound();
            userInput.setText(userInput.getText() + "í");
        }
    }

    // Insert accented character into textfield when user click button
    public void accent4ButtonClicked() {
        if (!gameover) {
            Client.playClickSound();
            userInput.setText(userInput.getText() + "ó");
        }
    }

    // Insert accented character into textfield when user click button
    public void accent5ButtonClicked() {
        if (!gameover) {
            Client.playClickSound();
            userInput.setText(userInput.getText() + "ú");
        }
    }

    // Insert accented character into textfield when user click button
    public void accent6ButtonClicked() {
        if (!gameover) {
            Client.playClickSound();
            userInput.setText(userInput.getText() + "ü");
        }
    }

    // Insert accented character into textfield when user click button
    public void accent7ButtonClicked() {
        if (!gameover) {
            Client.playClickSound();
            userInput.setText(userInput.getText() + "ñ");
        }
    }

    // When user clicks ubmit
    public void submitButtonClicked() throws IOException {
        if (!gameover) {
            Client.playClickSound();
            // if returns empty then the translation was marked as correct, else it returns a posible correct answer
            possibleCorrectAnswer = translationHandler.verifyTranslation(currentWords, currentExactWords, userInput.getText(), englishToSpanish);
            // If user got question right
            if (possibleCorrectAnswer.isEmpty()) {
                currentTranslationCorrect = true;
                incorrectLabel.setVisible(false);
                correctLabel.setVisible(true);
                // If user got question wrong
            } else {
                currentTranslationCorrect = false;
                correctLabel.setVisible(false);
                incorrectLabel.setVisible(true);
                // Tell user a possible correct answer
                String incorrectLabelText = "Incorrect  ❌  An example of a correct answer is: ";
                for (int i = 0; i < possibleCorrectAnswer.size(); i++) {
                    if (i == 0) {
                        String word = possibleCorrectAnswer.get(i);
                        incorrectLabelText = incorrectLabelText + word.substring(0, 1).toUpperCase() + word.substring(1) + " "; // Capitalise first letter
                        continue;
                    }
                    incorrectLabelText = incorrectLabelText + possibleCorrectAnswer.get(i) + " ";
                }
                incorrectLabel.setText(incorrectLabelText);
            }
            endOfTranslation(currentTranslationCorrect);
        }
    }

    // Called at the end of translation whether correct or not
    public void endOfTranslation(boolean correct) throws IOException {
        // If correct, increment score here, then save total score in client and server
        if (currentTranslationCorrect) {
            Client.playCorrectSound();
            Client.setTotalScore(Client.getTotalScore() + 1);
            currentScore++;
            // Check if highscore and save in client and server if so
            if (currentScore > Client.getHighestScore()) {
                Client.setHighestScore(currentScore);
                highestScore.setText("Highest Score: " + currentScore);
            }
            score.setText("Current Score: " + currentScore);
            levelChecker();
            currentlevelLabel.setText("Current Level: " + currentLevelNumber);
            endlessMode();
            // If wrong, decrement life
        } else {
            Client.playIncorrectSound();
            currentLives--;
            handleLives();
            if (!gameover) {
                endlessMode();
            }
        }

    }


    public void levelChecker() {
        if (bossLevel) {
            // Assign current level as next normal level after boss
            currentLevelNumber = (currentLevelNumber * -1) + 1;
            // If beat boss level, increment user's lives, but cannot go over 5
            if (currentLives < 5) {
                currentLives++;
            }
            // If current score exceeds current level's ending score, move onto boss level for that level
        } else if (currentScore > currentLevel.getEndingScore()) {
            currentLevelNumber = currentLevelNumber * -1;
        }
    }


    public void endGame() {
        startEndlessButton.setVisible(true);
        // Check if user had beaten any achievements and increment progress
        if (currentScore >= 5){
            Client.getAchievementsProgress().put("0achievement1", Client.getAchievementsProgress().get("0achievement1") + 1);
            Client.printToServer("incrementAchievement");
            Client.printToServer("0achievement1");
        }
        if (currentScore >= 10){
            Client.getAchievementsProgress().put("0achievement2", Client.getAchievementsProgress().get("0achievement2") + 1);
            Client.printToServer("incrementAchievement");
            Client.printToServer("0achievement2");
        }
        if (currentScore >= 25){
            Client.getAchievementsProgress().put("0achievement3", Client.getAchievementsProgress().get("0achievement3") + 1);
            Client.printToServer("incrementAchievement");
            Client.printToServer("0achievement3");
        }
        if (currentScore >= 50){
            Client.getAchievementsProgress().put("0achievement4", Client.getAchievementsProgress().get("0achievement4") + 1);
            Client.printToServer("incrementAchievement");
            Client.printToServer("0achievement4");
        }
        if (currentScore >= 100){
            Client.getAchievementsProgress().put("0achievement5", Client.getAchievementsProgress().get("0achievement5") + 1);
            Client.printToServer("incrementAchievement");
            Client.printToServer("0achievement5");
        }
    }

    // Called when user has reached boss level 1
    public void bossLevel1() throws IOException {
        // Need to save variables so they can be accessed for next time EndlessModeHandler is created
        Client.savingEndlessVariables(currentScore, currentLives, 2);
        // Load boss level 1 screen
        Scene scene = currentQuestion.getScene();
        Window window = scene.getWindow();
        Stage stage = (Stage) window;
        FXMLLoader loader = new FXMLLoader();
        Parent bossLevel1 = loader.load(getClass().getResource("Boss1.fxml"));
        stage.setTitle("Boss Level 1");
        stage.getScene().setRoot(bossLevel1);
        stage.show();
    }

    // Called when user has reached boss level 2
    public void bossLevel2() throws IOException {
        // Need to save variables so they can be accessed for next time EndlessModeHandler is created
        Client.savingEndlessVariables(currentScore, currentLives, 3);
        // Load boss level 2 screen
        Scene scene = currentQuestion.getScene();
        Window window = scene.getWindow();
        Stage stage = (Stage) window;
        FXMLLoader loader = new FXMLLoader();
        Parent bossLevel2 = loader.load(getClass().getResource("Boss2.fxml"));
        stage.setTitle("Boss Level 2");
        stage.getScene().setRoot(bossLevel2);
        stage.show();
    }

    // Called when user has reached boss level 3
    public void bossLevel3() throws IOException {
        // Need to save variables so they can be accessed for next time EndlessModeHandler is created
        Client.savingEndlessVariables(currentScore, currentLives, 4);
        // Load boss level 3 screen
        Scene scene = currentQuestion.getScene();
        Window window = scene.getWindow();
        Stage stage = (Stage) window;
        FXMLLoader loader = new FXMLLoader();
        Parent bossLevel3 = loader.load(getClass().getResource("Boss3.fxml"));
        stage.setTitle("Boss Level 3");
        stage.getScene().setRoot(bossLevel3);
        stage.show();
    }

    // Called when user has reached boss level 4
    public void bossLevel4() throws IOException {
        // Need to save variables so they can be accessed for next time EndlessModeHandler is created
        Client.savingEndlessVariables(currentScore, currentLives, 5);
        // Load boss level 4 screen
        Scene scene = currentQuestion.getScene();
        Window window = scene.getWindow();
        Stage stage = (Stage) window;
        FXMLLoader loader = new FXMLLoader();
        Parent bossLevel4 = loader.load(getClass().getResource("Boss4.fxml"));
        stage.setTitle("Boss Level 4");
        stage.getScene().setRoot(bossLevel4);
        stage.show();
    }

    // When user returns to endless mode screen after boss, set up FX elements and load variables from client
    public void continueButtonClicked() throws IOException {
        Client.playClickSound();
        newLevelLabel.setVisible(false);
        newLevelPane.setVisible(false);
        currentScore = Client.client.getCurrentEndlessScore();
        currentLives = Client.client.getCurrentEndlessLives();
        currentLevelNumber = Client.client.getCurrentEndlessLevel();
        currentlevelLabel.setVisible(true);
        highestScore.setVisible(true);
        highestLevel.setVisible(true);
        score.setVisible(true);
        currentLives++;
        handleLives();
        gameSetup(false);
    }


}
