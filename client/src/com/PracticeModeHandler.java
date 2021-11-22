package src.com;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import static java.lang.Character.toUpperCase;

public class PracticeModeHandler {

    private boolean adaptiveMistakesMode;
    private HashMap<Integer, Level> levelsHashMap = new HashMap<Integer, Level>();
    private HashMap<String, Topic> topicsHashMap = new HashMap<String, Topic>();
    private ArrayList<Level> possibleLevels = new ArrayList<Level>(); // need to have access to level list
    private ArrayList<Integer> includedLevels = new ArrayList<Integer>();
    private int currentStreak;
    private boolean onlyEnglishToSpanish;
    private boolean onlySpanishToEnglish;
    private boolean bothLanguages;
    private boolean noBossLevels;
    private boolean includeBossLevels;
    private boolean onlyBossLevels;
    private TranslationHandler translationHandler;
    private Level currentLevel;
    private ArrayList<Word> currentWords = new ArrayList<Word>();
    private ArrayList<String> exactCurrentWords = new ArrayList<String>();
    private Random randomGenerator = new Random();
    private boolean currentLevelIsBoss;
    private ArrayList<Topic> currentStructure;
    private boolean englishToSpanish;
    private String currentTranslation;
    private boolean currentTranslationCorrect;
    private Word word;
    private String exactWord;
    private ArrayList<String> possibleCorrectAnswer = new ArrayList<String>();
    private int highestStreak = 0;


    @FXML
    Button practiceReturnToMenuButton;
    @FXML
    CheckBox bothLanguagesCheckbox;
    @FXML
    ChoiceBox minimumLevelChoice;
    @FXML
    ChoiceBox maximumLevelChoice;
    @FXML
    ChoiceBox specificLevelChoice;
    @FXML
    Button beginPracticeModeButton;
    @FXML
    CheckBox rangeOfLevelsCheckbox;
    @FXML
    CheckBox specificLevelCheckbox;
    @FXML
    Label minimumLevelLabel;
    @FXML
    Label maximumLevelLabel;
    @FXML
    CheckBox englishToSpanishCheckbox;
    @FXML
    CheckBox spanishToEnglishCheckbox;
    @FXML
    CheckBox includeBossCheckbox;
    @FXML
    CheckBox noBossCheckbox;
    @FXML
    CheckBox onlyBossCheckbox;
    @FXML
    CheckBox adaptiveMistakesCheckbox;
    @FXML
    Button returnToMenuButton;
    @FXML
    Button startPracticeButton;
    @FXML
    Button submitButton;
    @FXML
    Label currentLevelLabel;
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
    Label streak;
    @FXML
    Label highestStreakLabel;
    @FXML
    TextField userInput;
    @FXML
    Label currentQuestion;
    @FXML
    AnchorPane newLevelPane;
    @FXML
    Button startButton;
    @FXML
    Label newLevelLabel;
    @FXML
    Label errorLabel;
    @FXML
    Label practiceModeSettings;
    @FXML
    Label languageOptionsLabel;
    @FXML
    Label levelOptionsLabel;
    @FXML
    Label bossLevelOptionsLabel;
    @FXML
    Button returnToPracticeOptionsButton;
    @FXML
    Button questionButton;
    @FXML
    Button continueButton;
    @FXML
    Label correctLabel;
    @FXML
    Label incorrectLabel;

    public PracticeModeHandler() {
        translationHandler = new TranslationHandler();
        this.levelsHashMap = Client.getLevelsHashMap();
        this.topicsHashMap = Client.getTopicsHashMap();
    }

    public void startPracticeButtonClicked() throws IOException {
        Client.playClickSound();
        Client.setNeedPracticePopup(false);
        practiceMode();
    }

    // Load all variables based on variables saved in client
    public void loadVariables() {
        this.currentStreak = Client.getPracticeCurrentStreak();
        this.onlyEnglishToSpanish = Client.isPracticeEnglishToSpanishOnly();
        this.onlySpanishToEnglish = Client.isPracticeSpanishToEnglishOnly();
        this.bothLanguages = Client.isPracticeBothLanguages();
        this.adaptiveMistakesMode = Client.isPracticeAdaptiveMistakesMode();
        this.possibleLevels = Client.getPracticePossibleLevels();
        this.onlyBossLevels = Client.isPracticeOnlyBossLevels();
        this.noBossLevels = Client.isPracticeNoBossLevels();
        this.includeBossLevels = Client.isPracticeIncludeBossLevels();

    }

    // Setup FX elements for transitioning from customise options screen to game screen
    public void practiceMode() throws IOException {
        minimumLevelLabel.setVisible(false);
        minimumLevelChoice.setVisible(false);
        maximumLevelLabel.setVisible(false);
        maximumLevelChoice.setVisible(false);
        specificLevelCheckbox.setVisible(false);
        specificLevelChoice.setVisible(false);
        onlyBossCheckbox.setVisible(false);
        noBossCheckbox.setVisible(false);
        includeBossCheckbox.setVisible(false);
        bothLanguagesCheckbox.setVisible(false);
        englishToSpanishCheckbox.setVisible(false);
        spanishToEnglishCheckbox.setVisible(false);
        errorLabel.setVisible(false);
        adaptiveMistakesCheckbox.setVisible(false);
        rangeOfLevelsCheckbox.setVisible(false);
        languageOptionsLabel.setVisible(false);
        levelOptionsLabel.setVisible(false);
        bossLevelOptionsLabel.setVisible(false);
        practiceReturnToMenuButton.setVisible(false);
        beginPracticeModeButton.setVisible(false);
        returnToPracticeOptionsButton.setVisible(true);
        streak.setVisible(true);
        userInput.setVisible(true);
        currentLevelLabel.setVisible(true);
        returnToMenuButton.setVisible(true);
        returnToMenuButton.setVisible(true);
        currentQuestion.setVisible(true);
        submitButton.setVisible(true);
        highestStreakLabel  .setVisible(true);
        accent1Button.setVisible(true);
        accent2Button.setVisible(true);
        accent3Button.setVisible(true);
        accent4Button.setVisible(true);
        accent5Button.setVisible(true);
        accent6Button.setVisible(true);
        accent7Button.setVisible(true);
        newLevelLabel.setVisible(true);
        startButton.setVisible(true);
        //currentQuestion.setVisible(true);
        questionButton.setVisible(true);
        practiceModeSettings.setText("Practice Mode");

        initialiseQuestion();
        if (Client.getNeedPracticePopup() == false){
            newLevelPane.setVisible(false);
        }

    }


    public void initialiseQuestion() throws IOException {
        currentStreak = Client.getPracticeCurrentStreak();
        highestStreak = Client.getPracticeHighestStreak();
        Client.setCurrentlyPractising(true);
        loadVariables();
        int random = randomGenerator.nextInt(possibleLevels.size());
        currentLevel = possibleLevels.get(random);

        if (onlyBossLevels) { // This level will be boss if only boss levels
            currentLevelIsBoss = true;
        } else if (noBossLevels) { // This level won't be a boss if no boss levels
            currentLevelIsBoss = false;
        } else { // Calculate chance of boss mode based on how many levels in endless you have to beat before facing the boss level
            int previousLevelNumber = currentLevel.getLevelNumber() - 1;
            if (currentLevel.getLevelNumber() == 1) {
                random = randomGenerator.nextInt(4); // Cant get previous number so need special case (level 1 has 3 before boss)
            } else {
                Level previousLevel = levelsHashMap.get(previousLevelNumber);
                int bossLevelLikeliness = currentLevel.getEndingScore() - previousLevel.getEndingScore();
                random = randomGenerator.nextInt(bossLevelLikeliness);
            }
            if (random == 0) {
                currentLevelIsBoss = true;
            } else currentLevelIsBoss = false;
        }
        // Set up boss level
        if (currentLevelIsBoss) {
            initialiseBoss();
        } else { // Set up normal question
            if (!Client.getNeedPracticePopup()) {
                loadPracticeEndlessMode();
            }
            currentTranslationCorrect = false;
            userInput.setText("");
            exactCurrentWords.clear();
            currentWords.clear();
            currentLevelLabel.setText("Current Level: " + currentLevel.getLevelNumber());
            // Choose whether english to spanish or spanish to english depdending on what user has chosen
            if (onlySpanishToEnglish) {
                englishToSpanish = false;
            } else if (onlyEnglishToSpanish) {
                englishToSpanish = true;
                // If user has chosen to allow both ways, randomise
            } else {
                random = randomGenerator.nextInt(2);
                if (random == 0) {
                    englishToSpanish = true;
                } else englishToSpanish = false;
            }
            if (englishToSpanish) {
                currentTranslation = "Translate from English to Spanish: ";
            } else currentTranslation = "Translate from Spanish to English: ";
            random = (randomGenerator.nextInt(100)) + 1;
            int currentIndex = 0;
            while (random > 0) {
                int currentProbability = currentLevel.getOrderedProbabilities().get(currentIndex);
                random = random - currentProbability;
                if (random > 0) {
                    currentIndex++;
                } else break;
            }
            // Get current structure for phrase from level
            currentStructure = currentLevel.getPossibleStructures().get(currentIndex);
            int pluralsChance = currentLevel.getIncludePluralsChance();
            boolean currentWordPlural;
            // Determine whether each word should be plural based on plural probability
            for (int j = 0; j < currentStructure.size(); j++) {
                int currentTopicSize = currentStructure.get(j).getTopicWords().size();
                random = randomGenerator.nextInt(currentTopicSize);
                word = currentStructure.get(j).getTopicWords().get(random);
                random = randomGenerator.nextInt(100);
                if (random > pluralsChance) {
                    currentWordPlural = false;
                }
                else currentWordPlural = true;
                currentWords.add(word);
                // Get correct part of Word object in initial phrase
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
                exactCurrentWords.add(exactWord);
            }


            for (int i = 0; i < currentWords.size(); i++) {
                if (currentWords.get(i).getWordTopic().contains("Determinants")) { // correct determinants
                    String correctDeterminant = translationHandler.determinantCorrecter(i, currentWords, exactCurrentWords, englishToSpanish);
                    exactCurrentWords.set(i, correctDeterminant);
                }
                // conjugate verbs, verb should refer to nearest noun, if there isn't one than be random
                if (currentWords.get(i).getWordTopic().contains("Verbs")) {
                    random = randomGenerator.nextInt(currentLevel.getPossibleTenses().get(currentIndex).size());
                    String tense = currentLevel.getPossibleTenses().get(currentIndex).get(random);
                    int person = -1;
                    boolean needNoun = true;
                    for (int j = i; j >= 0; j--) {
                        // If plural ensure verb is plural, else ensure verb is singular ending
                        if (currentWords.get(j).getWordTopic().contains("Nouns")) {
                            needNoun = false;
                            if (exactCurrentWords.get(j).equals(currentWords.get(j).getEnglishPlural()) ||
                                    exactCurrentWords.get(j).equals(currentWords.get(j).getSpanishPlural())) {
                                person = 6;
                            } else person = 3;
                        }
                    }
                    if (needNoun) {
                        random = randomGenerator.nextInt(6);
                        person = random + 1;
                    }
                    // Conjugate verb to find exact ending
                    String correctVerb = translationHandler.verbConjugator(currentWords.get(i), englishToSpanish, tense, person, needNoun, true);
                    exactCurrentWords.set(i, correctVerb);
                }
                if (currentWords.get(i).getWordTopic().contains("Adjectives")) { // Adjectives need to agree with noun/certain verbs
                    String correctAdjective = translationHandler.adjectiveAgree(i, currentWords, exactCurrentWords, true, englishToSpanish);
                    exactCurrentWords.set(i, correctAdjective);
                }
            }

            if (englishToSpanish) { // Word order is structures follows Spanish principles, so need to correct if initial phrase is English instead
                exactCurrentWords = translationHandler.wordOrderCorrecter(currentWords, exactCurrentWords);
                currentWords = translationHandler.wordOrderCorrecter(currentWords);
            }

            if (!englishToSpanish) {
                ArrayList<Integer> wordsToRemove = new ArrayList<Integer>(); // Store positions of elements which need to be removed
                for (int i = 0; i < exactCurrentWords.size() - 1; i++){
                    if (exactCurrentWords.get(i).equals("a") && exactCurrentWords.get(i + 1).equals("el")){
                        exactCurrentWords.set(i, currentWords.get(i).getAlternateSpanishTranslations().get(0)); // a el becomes al
                        wordsToRemove.add(i + 1);
                    }
                }
                for (int i = wordsToRemove.size() - 1; i >= 0; i--){ // Remove elements in reverse order to prevent concurrent exceptions
                    int position = wordsToRemove.get(i);
                    currentWords.remove(position);
                    exactCurrentWords.remove(position);
                }
            }

            boolean containsQuestion = false;
            for (int i = 0; i < exactCurrentWords.size(); i++) {
                if (currentWords.get(i).getWordTopic().contains("Questions")){
                    currentTranslation = currentTranslation + "¿"; // Add questions marks for questions
                    containsQuestion = true;
                }
                if (i == 0){ // Capitalise the first word
                    currentTranslation = currentTranslation + toUpperCase(exactCurrentWords.get(i).charAt(0)) + exactCurrentWords.get(i).substring(1) + " ";
                }
                else {
                    currentTranslation = currentTranslation + exactCurrentWords.get(i) + " ";
                }
            }
            // Add ending punctuation without a space
            if (containsQuestion){
                currentTranslation = currentTranslation.substring(0, currentTranslation.length() - 1) + "?";
            }
            else currentTranslation = currentTranslation.substring(0, currentTranslation.length() - 1) + ".";
            questionButton.setText(currentTranslation);
        }


    }

    // If current level is a boss, set it up
    public void initialiseBoss() throws IOException {
        switch (currentLevel.getLevelNumber()) {
            case 1:
                bossLevel1();
                break;
            case 2:
                bossLevel2();
                break;
            case 3:
                bossLevel3();
                break;
            case 4:
                bossLevel4();
                break;
        }
    }

    // Save practice variables for return, and load boss 1 screen
    public void bossLevel1() throws IOException {
        Client.savingPracticeVariables(currentStreak, highestStreak, onlyEnglishToSpanish, onlySpanishToEnglish, bothLanguages,
                onlyBossLevels, noBossLevels, includeBossLevels, possibleLevels, adaptiveMistakesMode);
        Scene scene = beginPracticeModeButton.getScene();
        Window window = scene.getWindow();
        Stage stage = (Stage) window;
        FXMLLoader loader = new FXMLLoader();
        Parent bossLevel1 = loader.load(getClass().getResource("Boss1.fxml"));
        stage.setTitle("Boss Level 1");
        stage.getScene().setRoot(bossLevel1);
        stage.show();
    }

    // Save practice variables for return, and load boss 2 screen
    public void bossLevel2() throws IOException {
        Client.savingPracticeVariables(currentStreak, highestStreak, onlyEnglishToSpanish, onlySpanishToEnglish, bothLanguages,
                onlyBossLevels, noBossLevels, includeBossLevels, possibleLevels, adaptiveMistakesMode);
        Scene scene = beginPracticeModeButton.getScene();
        Window window = scene.getWindow();
        Stage stage = (Stage) window;
        FXMLLoader loader = new FXMLLoader();
        Parent bossLevel1 = loader.load(getClass().getResource("Boss2.fxml"));
        stage.setTitle("Boss Level 2");
        stage.getScene().setRoot(bossLevel1);
        stage.show();
    }

    // Save practice variables for return, and load boss 3 screen
    public void bossLevel3() throws IOException {
        Client.savingPracticeVariables(currentStreak, highestStreak, onlyEnglishToSpanish, onlySpanishToEnglish, bothLanguages,
                onlyBossLevels, noBossLevels, includeBossLevels, possibleLevels, adaptiveMistakesMode);
        Scene scene = beginPracticeModeButton.getScene();
        Window window = scene.getWindow();
        Stage stage = (Stage) window;
        FXMLLoader loader = new FXMLLoader();
        Parent bossLevel1 = loader.load(getClass().getResource("Boss3.fxml"));
        stage.setTitle("Boss Level 3");
        stage.getScene().setRoot(bossLevel1);
        stage.show();
    }

    // Save practice variables for return, and load boss 4 screen
    public void bossLevel4() throws IOException {
        Client.savingPracticeVariables(currentStreak, highestStreak, onlyEnglishToSpanish, onlySpanishToEnglish, bothLanguages,
                onlyBossLevels, noBossLevels, includeBossLevels, possibleLevels, adaptiveMistakesMode);
        Scene scene = beginPracticeModeButton.getScene();
        Window window = scene.getWindow();
        Stage stage = (Stage) window;
        FXMLLoader loader = new FXMLLoader();
        Parent bossLevel1 = loader.load(getClass().getResource("Boss4.fxml"));
        stage.setTitle("Boss Level 4");
        stage.getScene().setRoot(bossLevel1);
        stage.show();
    }

    // Insert accented character into textfield if user clicked button
    public void accent1ButtonClicked() {
        Client.playClickSound();
        userInput.setText(userInput.getText() + "á");
    }

    // Insert accented character into textfield if user clicked button
    public void accent2ButtonClicked() {
        Client.playClickSound();
        userInput.setText(userInput.getText() + "é");
    }

    // Insert accented character into textfield if user clicked button
    public void accent3ButtonClicked() {
        Client.playClickSound();
        userInput.setText(userInput.getText() + "í");
    }

    // Insert accented character into textfield if user clicked button
    public void accent4ButtonClicked() {
        Client.playClickSound();
        userInput.setText(userInput.getText() + "ó");
    }

    // Insert accented character into textfield if user clicked button
    public void accent5ButtonClicked() {
        Client.playClickSound();
        userInput.setText(userInput.getText() + "ú");
    }

    // Insert accented character into textfield if user clicked button
    public void accent6ButtonClicked() {
        Client.playClickSound();
        userInput.setText(userInput.getText() + "ü");
    }

    // Insert accented character into textfield if user clicked button
    public void accent7ButtonClicked() {
        Client.playClickSound();
        userInput.setText(userInput.getText() + "ñ");
    }

    // If user clicks submit, verify their translation
    public void submitButtonClicked() throws IOException {
        Client.playClickSound();
        possibleCorrectAnswer = translationHandler.verifyTranslation(currentWords, exactCurrentWords, userInput.getText(), englishToSpanish);
        // if translation is correct
        if (possibleCorrectAnswer.isEmpty()){
            currentTranslationCorrect = true;
            incorrectLabel.setVisible(false);
            correctLabel.setVisible(true);
            Client.playCorrectSound();
        }
        else {
            // if translation is incorrect, display possible correct answer for user
            Client.playIncorrectSound();
            currentTranslationCorrect = false;
            correctLabel.setVisible(false);
            incorrectLabel.setVisible(true);
            String incorrectLabelText = "Incorrect  ❌  An example of a correct answer is: ";
            for (int i = 0; i < possibleCorrectAnswer.size(); i++) {
                if (i == 0) {
                    String word = possibleCorrectAnswer.get(i);
                    // Capitalise first letter
                    incorrectLabelText = incorrectLabelText + word.substring(0, 1).toUpperCase() + word.substring(1) + " ";
                    continue;
                }
                incorrectLabelText = incorrectLabelText + possibleCorrectAnswer.get(i) + " ";
            }
            incorrectLabel.setText(incorrectLabelText);
        }
        endOfTranslation(currentTranslationCorrect);
    }

    // Called at end of translation whether correct or incorrect
    public void endOfTranslation(boolean translationCorrect) throws IOException {
        if (currentTranslationCorrect) {
            currentStreak++;
            if (currentStreak > highestStreak){
                highestStreak = currentStreak;
                highestStreakLabel.setText("Highest Streak (This session): " + highestStreak);
            }
            // Check if completed any achievement objective, then increment its progress
            if (currentStreak >= 5 && onlyBossLevels && possibleLevels.size() == 1 && possibleLevels.get(0).getLevelNumber() == 1){ // Got streak of 5 with only level 1 boss
                Client.getAchievementsProgress().put("1achievement4", Client.getAchievementsProgress().get("1achievement4") + 1);
                if (Client.getAchievementsProgress().get("1achievement4") == Client.getAchievementsList().get(3).getNumberOfTimes()){ // Finished achievement so increment achievement for unlocking all others
                    Client.incrementSkipAchievement(1);
                }
                Client.printToServer("incrementAchievement");
                Client.printToServer("1achievement4");
            }
            if (currentStreak >= 15 && possibleLevels.size() == 1 && possibleLevels.get(0).getLevelNumber() == 1){ // Got streak of 15 with just level 1
                Client.getAchievementsProgress().put("1achievement3", Client.getAchievementsProgress().get("1achievement3") + 1);
                if (Client.getAchievementsProgress().get("1achievement3") == Client.getAchievementsList().get(2).getNumberOfTimes()){
                    Client.incrementSkipAchievement(1);
                }
                Client.printToServer("incrementAchievement");
                Client.printToServer("1achievement3");
            }

            if (currentStreak >= 10 && possibleLevels.size() == 1 && possibleLevels.get(0).getLevelNumber() == 2 && englishToSpanish){
                Client.getAchievementsProgress().put("2achievement2", Client.getAchievementsProgress().get("2achievement2") + 1);
                if (Client.getAchievementsProgress().get("2achievement2") == Client.getAchievementsList().get(6).getNumberOfTimes()){
                    Client.incrementSkipAchievement(2);
                }
                Client.printToServer("incrementAchievement");
                Client.printToServer("2achievement2");
            }

            if (currentStreak >= 10 && possibleLevels.size() == 1 && possibleLevels.get(0).getLevelNumber() == 3){
                Client.getAchievementsProgress().put("3achievement3", Client.getAchievementsProgress().get("3achievement3") + 1);
                if (Client.getAchievementsProgress().get("3achievement3") == Client.getAchievementsList().get(12).getNumberOfTimes()){
                    Client.incrementSkipAchievement(3);
                }
                Client.printToServer("incrementAchievement");
                Client.printToServer("3achievement3");
            }

            if (currentStreak >= 10 && possibleLevels.size() == 1 && possibleLevels.get(0).getLevelNumber() == 3 && onlyBossLevels){
                Client.getAchievementsProgress().put("3achievement4", Client.getAchievementsProgress().get("3achievement4") + 1);
                if (Client.getAchievementsProgress().get("3achievement4") == Client.getAchievementsList().get(13).getNumberOfTimes()){
                    Client.incrementSkipAchievement(3);
                }
                Client.printToServer("incrementAchievement");
                Client.printToServer("3achievement4");
            }

            if (currentStreak >= 25 && possibleLevels.size() == 1 && possibleLevels.get(0).getLevelNumber() == 4){
                Client.getAchievementsProgress().put("4achievement3", Client.getAchievementsProgress().get("4achievement3") + 1);
                if (Client.getAchievementsProgress().get("4achievement3") == Client.getAchievementsList().get(17).getNumberOfTimes()){
                    Client.incrementSkipAchievement(4);
                }
                Client.printToServer("incrementAchievement");
                Client.printToServer("4achievement3");
            }

            if (currentStreak >= 5 && possibleLevels.size() == 1 && possibleLevels.get(0).getLevelNumber() == 4){
                Client.getAchievementsProgress().put("4achievement4", Client.getAchievementsProgress().get("4achievement4") + 1);
                if (Client.getAchievementsProgress().get("4achievement4") == Client.getAchievementsList().get(18).getNumberOfTimes()){
                    Client.incrementSkipAchievement(4);
                }
                Client.printToServer("incrementAchievement");
                Client.printToServer("4achievement4");
            }


        } else currentStreak = 0;
        streak.setText("Current Streak: " + currentStreak);
        // Ensure client has latest version of variables saved
        Client.savingPracticeVariables(currentStreak, highestStreak, onlyEnglishToSpanish, onlySpanishToEnglish, bothLanguages,
                onlyBossLevels, noBossLevels, includeBossLevels, possibleLevels, adaptiveMistakesMode);
        initialiseQuestion();
    }

    // Give users an error message if they have chosen practice customisation options incorrectly
    public void exceptionHandle(int errorNumber) {
        switch (errorNumber) {
            case 0:
                errorLabel.setText("Error: A specific level has not been chosen in the Specific Level Choicebox.");
                break;
            case 1:
                errorLabel.setText("Error: A minimum level has not been chosen in the Minimum Level Choicebox.");
                break;
            case 2:
                errorLabel.setText("Error: A maximum level has not been chosen in the Maximum Level Choicebox.");
                break;
            case 3:
                errorLabel.setText("Error: The minimum level chosen is larger than the maximum level chosen.");
                break;
        }
        errorLabel.setVisible(true);
    }

    // When user clciks begin in practice customisation options screen
    public void beginPracticeModeButtonClicked() throws IOException {
        Client.playClickSound();
        possibleLevels.clear();
        levelsHashMap = Client.getLevelsHashMap();
        noBossLevels = noBossCheckbox.isSelected();
        includeBossLevels = includeBossCheckbox.isSelected();
        onlyBossLevels = onlyBossCheckbox.isSelected();
        bothLanguages = bothLanguagesCheckbox.isSelected();
        onlyEnglishToSpanish = englishToSpanishCheckbox.isSelected();
        onlySpanishToEnglish = spanishToEnglishCheckbox.isSelected();
        adaptiveMistakesMode = adaptiveMistakesCheckbox.isSelected();
        if (specificLevelCheckbox.isSelected()) { // Include only the selected level
            try {
                String levelSelected = (String) specificLevelChoice.getSelectionModel().getSelectedItem();
                // get the level number (always at index 7) and convert to int
                int levelNumberSelected = Integer.parseInt(String.valueOf(levelSelected.charAt(6)));
                possibleLevels.add(levelsHashMap.get(levelNumberSelected));
            } catch (NullPointerException nu) { // Exception if specific level not chosen
                exceptionHandle(0);
                return;
            }

        } else { // include all levels within the selected range
            int minimumLevelNumberSelected = 0;
            int maximumLevelNumberSelected = 0;

            try {
                String minimumLevelSelected = (String) minimumLevelChoice.getSelectionModel().getSelectedItem();
                minimumLevelNumberSelected = Integer.parseInt(String.valueOf(minimumLevelSelected.charAt(6)));
            } catch (NullPointerException nu) {
                exceptionHandle(1); // Exception if minimum level not chosen
                return;
            }

            try {
                String maximumLevelSelected = (String) maximumLevelChoice.getSelectionModel().getSelectedItem();
                maximumLevelNumberSelected = Integer.parseInt(String.valueOf(maximumLevelSelected.charAt(6)));
            } catch (NullPointerException nu) {
                exceptionHandle(2); // Exception if maximum level not chosen
                return;
            }

            if (minimumLevelNumberSelected > maximumLevelNumberSelected) {
                exceptionHandle(3); // Exception if minimum level is larger than maximum level
                return;
            }
            for (int i = minimumLevelNumberSelected; i <= maximumLevelNumberSelected; i++) {
                possibleLevels.add(levelsHashMap.get(i));
            }

        }
        // Ensure client has latest versions of variable saved
        Client.savingPracticeVariables(0, 0, onlyEnglishToSpanish, onlySpanishToEnglish, bothLanguages, onlyBossLevels,
                noBossLevels, includeBossLevels, possibleLevels, adaptiveMistakesMode);
        //loadPracticeEndlessMode();
        initialiseQuestion();


    }

    // When user clicks continue
    public void continueButtonClicked() throws IOException {
        Client.playClickSound();
        newLevelPane.setVisible(false);
        questionButton.setVisible(true);
        currentQuestion.setVisible(true);
        initialiseQuestion();
    }

    // Populate drop down menus with correct options
    public void startButtonClicked() {
        Client.playClickSound();
        newLevelPane.setVisible(false);
        newLevelLabel.setVisible(false);
        startButton.setVisible(false);
        specificLevelChoice.getItems().add(0, "Level 1");
        specificLevelChoice.getItems().add(1, "Level 2");
        specificLevelChoice.getItems().add(2, "Level 3");
        specificLevelChoice.getItems().add(3, "Level 4");
//        specificLevelChoice.getItems().add(4, "Level 5");
        minimumLevelChoice.getItems().add(0, "Level 1");
        minimumLevelChoice.getItems().add(1, "Level 2");
        minimumLevelChoice.getItems().add(2, "Level 3");
        minimumLevelChoice.getItems().add(3, "Level 4");
//        minimumLevelChoice.getItems().add(4, "Level 5");
        maximumLevelChoice.getItems().add(0, "Level 1");
        maximumLevelChoice.getItems().add(1, "Level 2");
        maximumLevelChoice.getItems().add(2, "Level 3");
        maximumLevelChoice.getItems().add(3, "Level 4");
//        maximumLevelChoice.getItems().add(4, "Level 5");
    }

    // Used to convert screen from practice customisation options screen to game screen
    public void loadPracticeEndlessMode() throws IOException {
        minimumLevelLabel.setVisible(false);
        minimumLevelChoice.setVisible(false);
        maximumLevelLabel.setVisible(false);
        maximumLevelChoice.setVisible(false);
        specificLevelCheckbox.setVisible(false);
        specificLevelChoice.setVisible(false);
        onlyBossCheckbox.setVisible(false);
        noBossCheckbox.setVisible(false);
        includeBossCheckbox.setVisible(false);
        bothLanguagesCheckbox.setVisible(false);
        englishToSpanishCheckbox.setVisible(false);
        spanishToEnglishCheckbox.setVisible(false);
        errorLabel.setVisible(false);
        adaptiveMistakesCheckbox.setVisible(false);
        rangeOfLevelsCheckbox.setVisible(false);
        languageOptionsLabel.setVisible(false);
        levelOptionsLabel.setVisible(false);
        bossLevelOptionsLabel.setVisible(false);
        practiceReturnToMenuButton.setVisible(false);
        beginPracticeModeButton.setVisible(false);
        returnToPracticeOptionsButton.setVisible(true);
        streak.setVisible(true);
        userInput.setVisible(true);
        currentLevelLabel.setVisible(true);
        returnToMenuButton.setVisible(true);
        returnToMenuButton.setVisible(true);
        currentQuestion.setVisible(true);
        submitButton.setVisible(true);
        highestStreakLabel  .setVisible(true);
        accent1Button.setVisible(true);
        accent2Button.setVisible(true);
        accent3Button.setVisible(true);
        accent4Button.setVisible(true);
        accent5Button.setVisible(true);
        accent6Button.setVisible(true);
        accent7Button.setVisible(true);
        newLevelLabel.setVisible(true);
        startButton.setVisible(true);
        //currentQuestion.setVisible(true);
        questionButton.setVisible(true);
        practiceModeSettings.setText("Practice Mode");


    }

    // Return to practice customisation options screen if user clicks corresponding button
    public void returnToPracticeOptionsButtonClicked() throws IOException {
        Client.playClickSound();
        Scene scene = practiceReturnToMenuButton.getScene();
        Window window = scene.getWindow();
        Stage stage = (Stage) window;
        Parent playMenu = FXMLLoader.load(getClass().getResource("PracticeMode.fxml"));
        stage.setTitle("Play Menu");
        stage.getScene().setRoot(playMenu);
        stage.show();
    }

    // Load play menu screen if user clicks return
    public void practiceReturnButtonClicked() throws IOException {
        Client.playClickSound();
        Scene scene = practiceReturnToMenuButton.getScene();
        Window window = scene.getWindow();
        Stage stage = (Stage) window;
        Parent playMenu = FXMLLoader.load(getClass().getResource("PlayMenu.fxml"));
        stage.setTitle("Play Menu");
        stage.getScene().setRoot(playMenu);
        stage.show();
    }

    // When user clicks checkbox to have a range of levels rather than 1, make necessary labels visible and hide unnecessary
    public void rangeOfLevelsSelected() {
        maximumLevelChoice.setVisible(true);
        minimumLevelChoice.setVisible(true);
        specificLevelChoice.setVisible(false);
        rangeOfLevelsCheckbox.setSelected(true);
        maximumLevelLabel.setVisible(true);
        minimumLevelLabel.setVisible(true);
        specificLevelCheckbox.setSelected(false);
    }

    // When user clicks submit, save all user's customisation options as variables to be able to continually use them
    public void bothLanguagesSelected() {
        spanishToEnglishCheckbox.setSelected(false);
        englishToSpanishCheckbox.setSelected(false);
        bothLanguagesCheckbox.setSelected(true);
        System.out.println((String) specificLevelChoice.getSelectionModel().getSelectedItem());
    }

    public void spanishToEnglishSelected() {
        spanishToEnglishCheckbox.setSelected(true);
        englishToSpanishCheckbox.setSelected(false);
        bothLanguagesCheckbox.setSelected(false);
    }

    public void englishToSpanishSelected() {
        spanishToEnglishCheckbox.setSelected(false);
        englishToSpanishCheckbox.setSelected(true);
        bothLanguagesCheckbox.setSelected(false);
    }

    public void includeBossSelected() {
        includeBossCheckbox.setSelected(true);
        noBossCheckbox.setSelected(false);
        onlyBossCheckbox.setSelected(false);
    }

    public void noBossSelected() {
        includeBossCheckbox.setSelected(false);
        noBossCheckbox.setSelected(true);
        onlyBossCheckbox.setSelected(false);
    }

    public void onlyBossSelected() {
        includeBossCheckbox.setSelected(false);
        noBossCheckbox.setSelected(false);
        onlyBossCheckbox.setSelected(true);
    }

    public void specificLevelSelected() {
        maximumLevelChoice.setVisible(false);
        minimumLevelChoice.setVisible(false);
        specificLevelChoice.setVisible(true);
        maximumLevelLabel.setVisible(false);
        minimumLevelLabel.setVisible(false);
        specificLevelCheckbox.setSelected(true);
        rangeOfLevelsCheckbox.setSelected(false);
    }

    public void adaptiveMistakesSelected() {
    }

    // Load play menu when user clicks return
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

