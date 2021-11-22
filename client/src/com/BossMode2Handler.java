package src.com;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import static java.lang.Character.toUpperCase;

public class BossMode2Handler {


    public BossMode2Handler() {
        currentLevel = Client.getLevelsHashMap().get(2);
        translationHandler = new TranslationHandler();
    }

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
    Button accent8Button;
    @FXML
    Button accent9Button;
    @FXML
    Button accent10Button;
    @FXML
    Button accent11Button;
    @FXML
    Button accent12Button;
    @FXML
    Button accent13Button;
    @FXML
    Button accent14Button;
    @FXML
    Button submitButton;
    @FXML
    TextField userInput1;
    @FXML
    TextField userInput2;
    @FXML
    AnchorPane newLevelPane;
    @FXML
    Button currentQuestion1;
    @FXML
    Button currentQuestion2;
    @FXML
    ImageView timerImage;
    @FXML
    Label remainingTime;
    @FXML
    Label newLevelLabel;
    @FXML
    Button startButton;
    @FXML
    Button okayButton;
    @FXML
    Button returnToMenuButton;

    private int timeLeft;
    private final int STARTING_TIME = 20; // How long to give the user to complete the level
    private TranslationHandler translationHandler;
    private ArrayList<Word> question1Words = new ArrayList<Word>(); // List of the Word types in the first translation question
    private ArrayList<Word> question2Words = new ArrayList<Word>(); // List of the Word types in the second translation question
    private ArrayList<String> question1ExactWords = new ArrayList<String>(); // List of the exact strings in the first translation question
    private ArrayList<String> question2ExactWords = new ArrayList<String>(); // List of the exact string in the second translation question
    private boolean englishToSpanish1; // Is the first translation question an English to Spanish question or a Spanish to English question
    private boolean englishToSpanish2; // Is the second translation question an English to Spanish question or a Spanish to English question
    private Random randomGenerator = new Random();
    private String currentTranslation; // Stores the user's input for the current question
    private ArrayList<String> possibleCorrectAnswer1 = new ArrayList<String>(); // Stores a list of words which act as a possible correct answer for question 1
    private ArrayList<String> possibleCorrectAnswer2 = new ArrayList<String>(); // Stores a list of words which act as a possible correct answer for question 2
    private Level currentLevel;
    private ArrayList<Topic> currentStructure; // Stores a list of topics which act as the structure for the translation phrase to take
    private int pluralsChance; // Probability from 0 to 100 of a given noun/personal verb being plural
    private Word word;
    private boolean currentWordPlural;
    private String exactWord; // The String representing the exact form a Word takes

    // Thread to act as a timer while the boss level plays out
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
                Platform.runLater(() -> remainingTime.setText(String.valueOf(timeLeft))); // Update the text of the time remaining label
            }
            // If program gets to here user has run out of time, load the pop up informing the user along with the okay button
            Platform.runLater(() -> newLevelPane.setVisible(true));
            Platform.runLater(() -> newLevelPane.toFront());
            Platform.runLater(() -> newLevelLabel.setText("You have ran out of time! So you have lost a life."));
            Platform.runLater(() -> startButton.setVisible(false));
            Platform.runLater(() -> okayButton.setVisible(true));
            Platform.runLater(() -> newLevelLabel.setTextAlignment(TextAlignment.CENTER));

        }
    });

    // This button appears only when the user made a mistake, so remove a life and increment score
    public void okayButtonClicked() throws IOException {
        Client.playClickSound();
        Client.client.setCurrentEndlessLives(Client.client.getCurrentEndlessLives() - 1);
        Client.client.setCurrentEndlessScore(Client.client.getCurrentEndlessScore() + 1);
        endBossLevel();
        ;
    }

    // Perform initial set up when user clicks start
    public void startButtonClicked() throws InterruptedException {
        timeLeft = STARTING_TIME;
        newLevelPane.setVisible(false);
        timerImage.setVisible(true);
        remainingTime.setVisible(true);
        accent1Button.setVisible(true);
        accent2Button.setVisible(true);
        accent3Button.setVisible(true);
        accent4Button.setVisible(true);
        accent5Button.setVisible(true);
        accent6Button.setVisible(true);
        accent7Button.setVisible(true);
        accent8Button.setVisible(true);
        accent9Button.setVisible(true);
        accent10Button.setVisible(true);
        accent11Button.setVisible(true);
        accent12Button.setVisible(true);
        accent13Button.setVisible(true);
        accent14Button.setVisible(true);
        submitButton.setVisible(true);
        userInput1.setVisible(true);
        userInput2.setVisible(true);
        submitButton.setVisible(true);
        currentQuestion1.setVisible(true);
        currentQuestion2.setVisible(true);
        returnToMenuButton.setVisible(true);
        setQuestion(1); // Sets up the first question
        setQuestion(2); // Sets up the second question
        Client.playClickSound();
        thread.start(); // Start timer thread
    }

    public void setTime(int time) {
        remainingTime.setText(String.valueOf(time));
    }

    // Add the appropriate accented character to the user's textfield
    public void accent1ButtonClicked() {
        userInput1.setText(userInput1.getText() + "á");
        Client.playClickSound();
    }

    public void accent2ButtonClicked() {
        userInput1.setText(userInput1.getText() + "é");
        Client.playClickSound();
    }

    public void accent3ButtonClicked() {
        userInput1.setText(userInput1.getText() + "í");
        Client.playClickSound();
    }

    public void accent4ButtonClicked() {
        userInput1.setText(userInput1.getText() + "ó");
        Client.playClickSound();
    }

    public void accent5ButtonClicked() {
        userInput1.setText(userInput1.getText() + "ú");
        Client.playClickSound();
    }

    public void accent6ButtonClicked() {
        userInput1.setText(userInput1.getText() + "ü");
        Client.playClickSound();
    }

    public void accent7ButtonClicked() {
        userInput1.setText(userInput1.getText() + "ñ");
        Client.playClickSound();
    }

    public void accent8ButtonClicked() {
        userInput2.setText(userInput2.getText() + "á");
        Client.playClickSound();
    }

    public void accent9ButtonClicked() {
        userInput2.setText(userInput2.getText() + "é");
        Client.playClickSound();
    }

    public void accent10ButtonClicked() {
        userInput2.setText(userInput2.getText() + "í");
        Client.playClickSound();
    }

    public void accent11ButtonClicked() {
        userInput2.setText(userInput2.getText() + "ó");
        Client.playClickSound();
    }

    public void accent12ButtonClicked() {
        userInput2.setText(userInput2.getText() + "ú");
        Client.playClickSound();
    }

    public void accent13ButtonClicked() {
        userInput2.setText(userInput2.getText() + "ü");
        Client.playClickSound();
    }

    public void accent14ButtonClicked() {
        userInput2.setText(userInput2.getText() + "ñ");
        Client.playClickSound();
    }


    public void submitButtonClicked() throws IOException {
        Client.playClickSound();
        thread.stop(); // Stop the thread which is count down
        // Verify each translation separately
        possibleCorrectAnswer1 = translationHandler.verifyTranslation(question1Words, question1ExactWords, userInput1.getText(), englishToSpanish1);
        possibleCorrectAnswer2 = translationHandler.verifyTranslation(question2Words, question2ExactWords, userInput2.getText(), englishToSpanish2);
        // If got at least one translation is wrong, treat the question as incorrect
        if (!(possibleCorrectAnswer1.isEmpty() && possibleCorrectAnswer2.isEmpty())) {
            Client.client.setCurrentEndlessLives(Client.client.getCurrentEndlessLives() - 1);//Subtract a life
            Client.playIncorrectSound();
            // Got at least one wrong
        }
        // If got both translations correct, increment achievement progress and play correct sound
        else {
            Client.getAchievementsProgress().put("2achievement4", Client.getAchievementsProgress().get("2achievement4") + 1);
            if (Client.getAchievementsProgress().get("2achievement4") == Client.getAchievementsList().get(8).getNumberOfTimes()){
                Client.incrementSkipAchievement(2); // Increment level 3 skip if just completed achievement
            }
            Client.printToServer("incrementAchievement");
            Client.printToServer("2achievement4");
            Client.playCorrectSound();
        }

        // Check whether matches achievement criteria, and increment that achievement progress
        if (!(Client.getCurrentlyPractising())){
            if (Client.client.getCurrentEndlessLives() > 0){
                Client.getAchievementsProgress().put("2achievement1", Client.getAchievementsProgress().get("2achievement1") + 1);
                if (Client.getAchievementsProgress().get("2achievement1") == Client.getAchievementsList().get(5).getNumberOfTimes()){
                    Client.incrementSkipAchievement(2); // Increment level 3 skip if just completed achievement
                }
                Client.printToServer("incrementAchievement");
                Client.printToServer("2achievement1");
            }
        }

        // Check whether matches achievement criteria, and increment that achievement progress
        if (Integer.parseInt(remainingTime.getText()) >= 10){
            Client.getAchievementsProgress().put("2achievement3", Client.getAchievementsProgress().get("2achievement3") + 1);
            if (Client.getAchievementsProgress().get("2achievement3") == Client.getAchievementsList().get(7).getNumberOfTimes()){
                Client.incrementSkipAchievement(2); // Increment level 3 skip if just completed achievement
            }
            Client.printToServer("incrementAchievement");
            Client.printToServer("2achievement3");

        }
        // If correct or incorrect, add 1 to score
        Client.client.setCurrentEndlessScore(Client.client.getCurrentEndlessScore() + 1);
        endBossLevel();
    }

    // Load practice mode stage if in practice mode
    public void endBossLevel() throws IOException {
        Scene scene = submitButton.getScene();
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
        // Load endless mode stage if in endless mode
        else {
            Parent endlessMode = loader.load(getClass().getResource("ContinueEndlessMode.fxml"));
            stage.setTitle("Endless Mode");
            stage.getScene().setRoot(endlessMode);
            stage.show();
        }
    }

    // Randomly decide whether the question is English to Spanish or Spanish to English
    public void setQuestion(int questionNumber) {
        int random = randomGenerator.nextInt(2);
        if (random == 0) {
            if (questionNumber == 1) {
                englishToSpanish1 = false;
            } else englishToSpanish2 = false;
            currentTranslation = "Translate from Spanish to English: ";
        } else {
            if (questionNumber == 1) {
                englishToSpanish1 = true;
            } else englishToSpanish2 = true;
            currentTranslation = "Translate from English to Spanish: ";
        }

        // Randomly pick a structure from the level weighted to the list of probabilities in levels.json
        random = (randomGenerator.nextInt(100)) + 1;
        int currentIndex = 0;
        while (random > 0) {
            int currentProbability = currentLevel.getOrderedProbabilities().get(currentIndex);
            random = random - currentProbability;
            if (random > 0) {
                currentIndex++;
            } else break;
        }
        currentStructure = currentLevel.getPossibleStructures().get(currentIndex);

        pluralsChance = currentLevel.getIncludePluralsChance();
        // Go through each type of word listed in the structure
        for (int j = 0; j < currentStructure.size(); j++) {
            // Pick a random word of that specific word type
            int currentTopicSize = currentStructure.get(j).getTopicWords().size();
            random = randomGenerator.nextInt(currentTopicSize);
            word = currentStructure.get(j).getTopicWords().get(random);
            random = randomGenerator.nextInt(100);

            // Determine whether the word should be plural according to the plural chance in levels.json
            if (random >= pluralsChance) {
                currentWordPlural = false;
            }

            // Add the word to its corresponding list of Words
            if (questionNumber == 1) {
                question1Words.add(word);
            } else question2Words.add(word);

            // Determine the exact word form to use based on language and whether it's plural
            if (questionNumber == 1) {
                if (englishToSpanish1) {
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
                question1ExactWords.add(exactWord);
            } else {
                if (englishToSpanish2) {
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
                question2ExactWords.add(exactWord);
            }
        }


        if (questionNumber == 1) {

            // Go through each word and make any necessary adjustments to ensure grammar is correct
            for (int i = 0; i < question1Words.size(); i++) {
                // Correct determinants (make sure they agree to their noun)
                if (question1Words.get(i).getWordTopic().contains("Determinants")) {
                    String correctDeterminant = translationHandler.determinantCorrecter(i, question1Words, question1ExactWords, englishToSpanish1);
                    question1ExactWords.set(i, correctDeterminant);
                }
                // Correct verbs, verb should refer to nearest noun (with exceptions), if there isn't one then be random
                if (question1Words.get(i).getWordTopic().contains("Verbs")) {
                    random = randomGenerator.nextInt(currentLevel.getPossibleTenses().get(currentIndex).size());
                    // Randomly assign tense to the verb based on list of tenses in levels.json
                    String tense = currentLevel.getPossibleTenses().get(currentIndex).get(random);
                    int person = -1;
                    boolean needNoun = true;
                    for (int j = i; j >= 0; j--) {
                        if (question1Words.get(j).getWordTopic().contains("Nouns")) {
                            // If there is a noun before the verb, don't need to add I, you, he etc before verb in English
                            needNoun = false;
                            if (question1ExactWords.get(j).equals(question1Words.get(j).getEnglishPlural()) ||
                                    question1ExactWords.get(j).equals(question1Words.get(j).getSpanishPlural())) {
                                person = 6; // Treated as plural (they)
                            } else person = 3; // Treated as singular (he/she/it)
                        } else {
                            random = randomGenerator.nextInt(6);
                            person = random + 1;
                        }
                    }
                    // Conjugate verb to correct form using all this information
                    String correctVerb = translationHandler.verbConjugator(question1Words.get(i), englishToSpanish1, tense, person, needNoun, true);
                    question1ExactWords.set(i, correctVerb);
                }
                // Correct adjectives, need to agree with noun/certain verbs
                if (question1Words.get(i).getWordTopic().contains("Adjectives")) {
                    String correctAdjective = translationHandler.adjectiveAgree(i, question1Words, question1ExactWords, true, englishToSpanish1);
                    question1ExactWords.set(i, correctAdjective);
                }
            }

            // Stores whether the current phrase to translate is a question (e.g. "how are you?")
            boolean containsQuestion1 = false;
            for (int i = 0; i < question1ExactWords.size(); i++) {
                if (question1Words.get(i).getWordTopic().contains("Questions")){
                    currentTranslation = currentTranslation + "¿"; // Add questions marks for questions
                    containsQuestion1 = true;
                }
                if (i == 0){ // Capitalise the first word
                    currentTranslation = currentTranslation + toUpperCase(question1ExactWords.get(i).charAt(0)) + question1ExactWords.get(i).substring(1) + " ";
                }
                else {
                    currentTranslation = currentTranslation + question1ExactWords.get(i) + " ";
                }
            }
            // Add ending punctuation without a space
            if (containsQuestion1){
                currentTranslation = currentTranslation.substring(0, currentTranslation.length() - 1) + "?";
            }
            else currentTranslation = currentTranslation.substring(0, currentTranslation.length() - 1) + ".";

            currentQuestion1.setText(currentTranslation);
        }

        else {
            // Go through each word and make any necessary adjustments to ensure grammar is correct
            for (int i = 0; i < question2Words.size(); i++) {
                // Correct determinants (make sure they agree to their noun)
                if (question2Words.get(i).getWordTopic().contains("Determinants")) {
                    String correctDeterminant = translationHandler.determinantCorrecter(i, question2Words, question2ExactWords, englishToSpanish2);
                    question2ExactWords.set(i, correctDeterminant);
                }
                // Correct verbs, verb should refer to nearest noun (with exceptions), if there isn't one then be random
                if (question2Words.get(i).getWordTopic().contains("Verbs")) {
                    random = randomGenerator.nextInt(currentLevel.getPossibleTenses().get(currentIndex).size());
                    // Randomly assign tense to the verb based on list of tenses in levels.json
                    String tense = currentLevel.getPossibleTenses().get(currentIndex).get(random);
                    int person = -1;
                    boolean needNoun = true;
                    for (int j = i; j >= 0; j--) {
                        if (question2Words.get(j).getWordTopic().contains("Nouns")) {
                            // If there is a noun before the verb, don't need to add I, you, he etc before verb in English
                            needNoun = false;
                            if (question2ExactWords.get(j).equals(question2Words.get(j).getEnglishPlural()) ||
                                    question2ExactWords.get(j).equals(question2Words.get(j).getSpanishPlural())) {
                                person = 6;  // Treated as plural (they)
                            } else person = 3; // Treated as singular (he/she/it)
                        }
                    }
                    if (needNoun) {
                        random = randomGenerator.nextInt(6);
                        person = random + 1;
                    }
                    // Conjugate verb to correct form using all this information
                    String correctVerb = translationHandler.verbConjugator(question2Words.get(i), englishToSpanish2, tense, person, needNoun, true);
                    question2ExactWords.set(i, correctVerb);
                }
                // Correct adjectives, need to agree with noun/certain verbs
                if (question2Words.get(i).getWordTopic().contains("Adjectives")) { // Adjectives need to agree with noun/certain verbs
                    String correctAdjective = translationHandler.adjectiveAgree(i, question2Words, question2ExactWords, true, englishToSpanish2);
                    question2ExactWords.set(i, correctAdjective);
                }
            }

            // Possible structures in levels.json follows Spanish principles,
            // so need to reorder words if initial phrase is English
            if (questionNumber == 1) {
                if (englishToSpanish1) {
                    question1ExactWords = translationHandler.wordOrderCorrecter(question1Words, question1ExactWords);
                    question1Words = translationHandler.wordOrderCorrecter(question1Words);
                }
            } else {
                if (englishToSpanish2) {
                    question1ExactWords = translationHandler.wordOrderCorrecter(question2Words, question2ExactWords);
                    question2Words = translationHandler.wordOrderCorrecter(question2Words);
                }
            }

            if (!englishToSpanish1) {
                // Store positions of elements which need to be removed
                // (E.g. "a el" goes from 2 words to 1 word "al", so must remove a word)
                ArrayList<Integer> wordsToRemove = new ArrayList<Integer>();

                // "a el" becomes "al"
                for (int i = 0; i < question1ExactWords.size() - 1; i++){
                    if (question1ExactWords.get(i).equals("a") && question1ExactWords.get(i + 1).equals("el")){
                        question1ExactWords.set(i, question1Words.get(i).getAlternateSpanishTranslations().get(0));
                        wordsToRemove.add(i + 1);
                    }
                }
                // Remove unnecessary elements in reverse order to prevent concurrent exceptions
                for (int i = wordsToRemove.size() - 1; i >= 0; i--){
                    int position = wordsToRemove.get(i);
                    question1Words.remove(position);
                    question1ExactWords.remove(position);
                }
            }

            if (!englishToSpanish2) {
                // Store positions of elements which need to be removed
                // (E.g. "a el" goes from 2 words to 1 word "al", so must remove a word)
                ArrayList<Integer> wordsToRemove = new ArrayList<Integer>();
                // "a el" becomes "al"
                for (int i = 0; i < question2ExactWords.size() - 1; i++){
                    if (question2ExactWords.get(i).equals("a") && question2ExactWords.get(i + 1).equals("el")){
                        question2ExactWords.set(i, question2Words.get(i).getAlternateSpanishTranslations().get(0)); // a el becomes al
                        wordsToRemove.add(i + 1);
                    }
                }
                // Remove unnecessary elements in reverse order to prevent concurrent exceptions
                for (int i = wordsToRemove.size() - 1; i >= 0; i--){
                    int position = wordsToRemove.get(i);
                    question2Words.remove(position);
                    question2ExactWords.remove(position);
                }
            }

            // Stores whether the current phrase to translate is a question (e.g. "how are you?")
            boolean containsQuestion2 = false;
            for (int i = 0; i < question2ExactWords.size(); i++) {
                if (question2Words.get(i).getWordTopic().contains("Questions")){
                    currentTranslation = currentTranslation + "¿"; // Add questions marks for questions
                    containsQuestion2 = true;
                }
                if (i == 0){ // Capitalise the first word
                    currentTranslation = currentTranslation + toUpperCase(question2ExactWords.get(i).charAt(0)) + question2ExactWords.get(i).substring(1) + " ";
                }
                else {
                    currentTranslation = currentTranslation + question2ExactWords.get(i) + " ";
                }
            }
            // Add ending punctuation without a space
            if (containsQuestion2){
                currentTranslation = currentTranslation.substring(0, currentTranslation.length() - 1) + "?";
            }
            else currentTranslation = currentTranslation.substring(0, currentTranslation.length() - 1) + ".";  // Add ending punctuation without a space

            currentQuestion2.setText(currentTranslation);
        
        }


    }

    // Load main play menu when user clicks return to menu button
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

}
