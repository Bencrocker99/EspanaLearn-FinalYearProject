package src.com;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Line;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.IOException;
import java.util.*;

public class BossMode3Handler {

    @FXML
    Line underscore1;
    @FXML
    Line underscore2;
    @FXML
    Line underscore3;
    @FXML
    Line underscore4;
    @FXML
    Line underscore5;
    @FXML
    Line underscore6;
    @FXML
    Line underscore7;
    @FXML
    Line underscore8;
    @FXML
    Line underscore9;
    @FXML
    Line underscore10;
    @FXML
    Line underscore11;
    @FXML
    Line underscore12;
    @FXML
    Line underscore13;
    @FXML
    Line underscore14;
    @FXML
    Line underscore15;
    @FXML
    Line underscore16;
    @FXML
    Line underscore17;
    @FXML
    Line underscore18;
    @FXML
    Line underscore19;
    @FXML
    Line underscore20;
    @FXML
    Line underscore21;
    @FXML
    Line underscore22;
    @FXML
    Line underscore23;
    @FXML
    Line underscore24;
    @FXML
    Line underscore25;
    @FXML
    Line underscore26;
    @FXML
    Line underscore27;
    @FXML
    Label character1;
    @FXML
    Label character2;
    @FXML
    Label character3;
    @FXML
    Label character4;
    @FXML
    Label character5;
    @FXML
    Label character6;
    @FXML
    Label character7;
    @FXML
    Label character8;
    @FXML
    Label character9;
    @FXML
    Label character10;
    @FXML
    Label character11;
    @FXML
    Label character12;
    @FXML
    Label character13;
    @FXML
    Label character14;
    @FXML
    Label character15;
    @FXML
    Label character16;
    @FXML
    Label character17;
    @FXML
    Label character18;
    @FXML
    Label character19;
    @FXML
    Label character20;
    @FXML
    Label character21;
    @FXML
    Label character22;
    @FXML
    Label character23;
    @FXML
    Label character24;
    @FXML
    Label character25;
    @FXML
    Label character26;
    @FXML
    Label character27;
    @FXML
    Line hangmanBorderLine1;
    @FXML
    Line hangmanBorderLine2;
    @FXML
    Line hangmanBorderLine3;
    @FXML
    Line hangmanRopeLine;
    @FXML
    Ellipse hangmanEllipse;
    @FXML
    Line hangmanTorsoLine;
    @FXML
    Line hangmanArmLine1;
    @FXML
    Line hangmanArmLine2;
    @FXML
    Line hangmanLegLine1;
    @FXML
    Line hangmanLegLine2;
    @FXML
    Label guessedCharacter1;
    @FXML
    Label guessedCharacter2;
    @FXML
    Label guessedCharacter3;
    @FXML
    Label guessedCharacter4;
    @FXML
    Label guessedCharacter5;
    @FXML
    Label guessedCharacter6;
    @FXML
    Label guessedCharacter7;
    @FXML
    Label guessedCharacter8;
    @FXML
    Button aButton;
    @FXML
    Button áButton;
    @FXML
    Button bButton;
    @FXML
    Button cButton;
    @FXML
    Button dButton;
    @FXML
    Button eButton;
    @FXML
    Button éButton;
    @FXML
    Button fButton;
    @FXML
    Button gButton;
    @FXML
    Button hButton;
    @FXML
    Button iButton;
    @FXML
    Button íButton;
    @FXML
    Button jButton;
    @FXML
    Button kButton;
    @FXML
    Button lButton;
    @FXML
    Button mButton;
    @FXML
    Button nButton;
    @FXML
    Button ñButton;
    @FXML
    Button oButton;
    @FXML
    Button óButton;
    @FXML
    Button pButton;
    @FXML
    Button qButton;
    @FXML
    Button rButton;
    @FXML
    Button sButton;
    @FXML
    Button tButton;
    @FXML
    Button uButton;
    @FXML
    Button úButton;
    @FXML
    Button üButton;
    @FXML
    Button vButton;
    @FXML
    Button wButton;
    @FXML
    Button xButton;
    @FXML
    Button yButton;
    @FXML
    Button zButton;
    @FXML
    AnchorPane newLevelPane;
    @FXML
    Button continueButton;
    @FXML
    Button returnToMenuButton;



    private int hangmanStage; // Stores how far through the stickman is to being fully drawn (from 0 to 8)
    private String chosenWord;
    private int numberOfRemainingLetters; // How many characters havent not been guessed yet within the chosen word
    private ArrayList<Character> allGuessedCharacters = new ArrayList<Character>();
    private ArrayList<Character> incorrectlyGuessedCharacters = new ArrayList<Character>();
    private ArrayList<Word> possibleWords = new ArrayList<Word>(); // List of possible words from which chosen word is picked
    private HashMap<String, Topic> topicsHashMap = new HashMap<String, Topic>();
    private Random randomGenerator = new Random();


    public BossMode3Handler() {
        topicsHashMap = Client.getTopicsHashMap();
    }

    // Initial game setup
    public void continueButtonClicked() {
        chooseWord();
        numberOfRemainingLetters = chosenWord.length();
        fillCharacterUnderscores(chosenWord.length()); // Draw out the particular underscores for the word/phrase
        fillCharacterLabels(chosenWord.length());
        newLevelPane.setVisible(false);
        returnToMenuButton.setVisible(true);
        Client.playClickSound();
    }

    // Draw out the particular underscores for the word/phrase
    public void fillCharacterUnderscores(int numberOfUnderscores) {
        switch (numberOfUnderscores) {
            // Make correct number of underscores visible
            case 27:
                underscore27.setVisible(true);
            case 26:
                underscore26.setVisible(true);
            case 25:
                underscore25.setVisible(true);
            case 24:
                underscore24.setVisible(true);
            case 23:
                underscore23.setVisible(true);
            case 22:
                underscore22.setVisible(true);
            case 21:
                underscore21.setVisible(true);
            case 20:
                underscore20.setVisible(true);
            case 19:
                underscore19.setVisible(true);
            case 18:
                underscore18.setVisible(true);
            case 17:
                underscore17.setVisible(true);
            case 16:
                underscore16.setVisible(true);
            case 15:
                underscore15.setVisible(true);
            case 14:
                underscore14.setVisible(true);
            case 13:
                underscore13.setVisible(true);
            case 12:
                underscore12.setVisible(true);
            case 11:
                underscore11.setVisible(true);
            case 10:
                underscore10.setVisible(true);
            case 9:
                underscore9.setVisible(true);
            case 8:
                underscore8.setVisible(true);
            case 7:
                underscore7.setVisible(true);
            case 6:
                underscore6.setVisible(true);
            case 5:
                underscore5.setVisible(true);
            case 4:
                underscore4.setVisible(true);
            case 3:
                underscore3.setVisible(true);
            case 2:
                underscore2.setVisible(true);
            case 1:
                underscore1.setVisible(true);
            case 0:
                break;
        }
        // If a particular character is whitespace (the gap between words), then make its corresponding underscore invisible
        for (int i = 0; i < chosenWord.length(); i++){
            if (chosenWord.charAt(i) == ' '){
                numberOfRemainingLetters--;
                switch (i){
                    case 0:
                        underscore1.setVisible(false);
                        break;
                    case 1:
                        underscore2.setVisible(false);
                        break;
                    case 2:
                        underscore3.setVisible(false);
                        break;
                    case 3:
                        underscore4.setVisible(false);
                        break;
                    case 4:
                        underscore5.setVisible(false);
                        break;
                    case 5:
                        underscore6.setVisible(false);
                        break;
                    case 6:
                        underscore7.setVisible(false);
                        break;
                    case 7:
                        underscore8.setVisible(false);
                        break;
                    case 8:
                        underscore9.setVisible(false);
                        break;
                    case 9:
                        underscore10.setVisible(false);
                        break;
                    case 10:
                        underscore11.setVisible(false);
                        break;
                    case 11:
                        underscore12.setVisible(false);
                        break;
                    case 12:
                        underscore13.setVisible(false);
                        break;
                    case 13:
                        underscore14.setVisible(false);
                        break;
                    case 14:
                        underscore15.setVisible(false);
                        break;
                    case 15:
                        underscore16.setVisible(false);
                        break;
                    case 16:
                        underscore17.setVisible(false);
                        break;
                    case 17:
                        underscore18.setVisible(false);
                        break;
                    case 18:
                        underscore19.setVisible(false);
                        break;
                    case 19:
                        underscore20.setVisible(false);
                        break;
                    case 20:
                        underscore21.setVisible(false);
                        break;
                    case 21:
                        underscore22.setVisible(false);
                        break;
                    case 22:
                        underscore23.setVisible(false);
                        break;
                    case 23:
                        underscore24.setVisible(false);
                        break;
                    case 24:
                        underscore25.setVisible(false);
                        break;
                    case 25:
                        underscore26.setVisible(false);
                        break;
                    case 26:
                        underscore27.setVisible(false);
                        break;
                }
            }
        }

    }

    // Make correct number of character labels visible (area above each underscore which will show the correct
    // letter for the underscore)
    public void fillCharacterLabels(int numberOfLabels) {
        switch (numberOfLabels) {
            case 27:
                character27.setVisible(true);
            case 26:
                character26.setVisible(true);
            case 25:
                character25.setVisible(true);
            case 24:
                character24.setVisible(true);
            case 23:
                character23.setVisible(true);
            case 22:
                character22.setVisible(true);
            case 21:
                character21.setVisible(true);
            case 20:
                character20.setVisible(true);
            case 19:
                character19.setVisible(true);
            case 18:
                character18.setVisible(true);
            case 17:
                character17.setVisible(true);
            case 16:
                character16.setVisible(true);
            case 15:
                character15.setVisible(true);
            case 14:
                character14.setVisible(true);
            case 13:
                character13.setVisible(true);
            case 12:
                character12.setVisible(true);
            case 11:
                character11.setVisible(true);
            case 10:
                character10.setVisible(true);
            case 9:
                character9.setVisible(true);
            case 8:
                character8.setVisible(true);
            case 7:
                character7.setVisible(true);
            case 6:
                character6.setVisible(true);
            case 5:
                character5.setVisible(true);
            case 4:
                character4.setVisible(true);
            case 3:
                character3.setVisible(true);
            case 2:
                character2.setVisible(true);
            case 1:
                character1.setVisible(true);
        }
    }

    //Add all possible word lists to possibleWords and then randomly choose a word within possibleWords
    public void chooseWord() {
        for (int i = 0; i < topicsHashMap.get("basicQuestions").getTopicWords().size(); i++){
            possibleWords.add(topicsHashMap.get("basicQuestions").getTopicWords().get(i));
        }
        for (int i = 0; i < topicsHashMap.get("travellingVerbs").getTopicWords().size(); i++){
            possibleWords.add(topicsHashMap.get("travellingVerbs").getTopicWords().get(i));
        }
        for (int i = 0; i < topicsHashMap.get("buildingAdjectives").getTopicWords().size(); i++){
            possibleWords.add(topicsHashMap.get("buildingAdjectives").getTopicWords().get(i));
        }
        for (int i = 0; i < topicsHashMap.get("basicQuestions").getTopicWords().size(); i++){
            possibleWords.add(topicsHashMap.get("basicQuestions").getTopicWords().get(i));
        }
        for (int i = 0; i < topicsHashMap.get("buildingNouns").getTopicWords().size(); i++){
            possibleWords.add(topicsHashMap.get("buildingNouns").getTopicWords().get(i));
        }
        int random = randomGenerator.nextInt(possibleWords.size());
        chosenWord = possibleWords.get(random).getWord();
    }

    // Draw the scene corresponding to the hangmanStage the user is currently at
    public void drawHangman() throws IOException {
        switch (hangmanStage) {
            case 0:
                break;
            case 1:
                hangmanBorderLine1.setVisible(true);
                hangmanBorderLine2.setVisible(true);
                hangmanBorderLine3.setVisible(true);
                break;
            case 2:
                hangmanRopeLine.setVisible(true);
                break;
            case 3:
                hangmanEllipse.setVisible(true);
                break;
            case 4:
                hangmanTorsoLine.setVisible(true);
                break;
            case 5:
                hangmanArmLine1.setVisible(true);
                break;
            case 6:
                hangmanArmLine2.setVisible(true);
                break;
            case 7:
                hangmanLegLine1.setVisible(true);
                break;
            case 8:
                hangmanLegLine2.setVisible(true);
                loseGame(); // Didn't correctly guess all letters of the word within the number of allowed attempts
                break;
        }
    }

    // Called when user guesses a character which doesn't appear in the chosen word
    public void addIncorrectlyGuessedCharacter(char character) {
        int numberOfCharacters = incorrectlyGuessedCharacters.size();
        switch (numberOfCharacters) {
            case 0:
                guessedCharacter1.setVisible(true);
                guessedCharacter1.setText(String.valueOf(character));
                break;
            case 1:
                guessedCharacter2.setVisible(true);
                guessedCharacter2.setText(String.valueOf(character));
                break;
            case 2:
                guessedCharacter3.setVisible(true);
                guessedCharacter3.setText(String.valueOf(character));
                break;
            case 3:
                guessedCharacter4.setVisible(true);
                guessedCharacter4.setText(String.valueOf(character));
                break;
            case 4:
                guessedCharacter5.setVisible(true);
                guessedCharacter5.setText(String.valueOf(character));
                break;
            case 5:
                guessedCharacter6.setVisible(true);
                guessedCharacter6.setText(String.valueOf(character));
                break;
            case 6:
                guessedCharacter7.setVisible(true);
                guessedCharacter7.setText(String.valueOf(character));
                break;
            case 7:
                guessedCharacter8.setVisible(true);
                guessedCharacter8.setText(String.valueOf(character));
                break;
        }
        incorrectlyGuessedCharacters.add(character);
    }

    // Check whether the character appears in the Spanish word and act accordingly
    public void verifyCharacter(char character) throws IOException {
        boolean containsCharacter = false;
        for (int i = 0; i < chosenWord.length(); i++) { // Check whether each the letter appears at each index of the word
            if (chosenWord.charAt(i) == character) {
                containsCharacter = true;
                numberOfRemainingLetters--;
                if (numberOfRemainingLetters == 0){
                    winGame(); // Found all letters in the Spanish word
                }
                setCharacterLabel(i, chosenWord.charAt(i)); // Make the letter appear above the underscore when it's been found
            }
        }
        if (!containsCharacter) {
            hangmanStage++;
            drawHangman();
            addIncorrectlyGuessedCharacter(character); // If the letter doesn't appear in the word, advance the hangman stage
        }
        allGuessedCharacters.add(character);
    }
    // Draw in a chracter over the corrent underscore
    public void setCharacterLabel(int label, char character) {
        switch (label) {
            case 0:
                character1.setText(String.valueOf(character));
                break;
            case 1:
                character2.setText(String.valueOf(character));
                break;
            case 2:
                character3.setText(String.valueOf(character));
                break;
            case 3:
                character4.setText(String.valueOf(character));
                break;
            case 4:
                character5.setText(String.valueOf(character));
                break;
            case 5:
                character6.setText(String.valueOf(character));
                break;
            case 6:
                character7.setText(String.valueOf(character));
                break;
            case 7:
                character8.setText(String.valueOf(character));
                break;
            case 8:
                character9.setText(String.valueOf(character));
                break;
            case 9:
                character10.setText(String.valueOf(character));
                break;
            case 10:
                character11.setText(String.valueOf(character));
                break;
            case 11:
                character12.setText(String.valueOf(character));
                break;
            case 12:
                character13.setText(String.valueOf(character));
                break;
            case 13:
                character14.setText(String.valueOf(character));
                break;
            case 14:
                character15.setText(String.valueOf(character));
                break;
            case 15:
                character16.setText(String.valueOf(character));
                break;
            case 16:
                character17.setText(String.valueOf(character));
                break;
            case 17:
                character18.setText(String.valueOf(character));
                break;
            case 18:
                character19.setText(String.valueOf(character));
                break;
            case 19:
                character20.setText(String.valueOf(character));
                break;
            case 20:
                character21.setText(String.valueOf(character));
                break;
            case 21:
                character22.setText(String.valueOf(character));
                break;
            case 22:
                character23.setText(String.valueOf(character));
                break;
            case 23:
                character24.setText(String.valueOf(character));
                break;
            case 24:
                character25.setText(String.valueOf(character));
                break;
            case 25:
                character26.setText(String.valueOf(character));
                break;
            case 26:
                character27.setText(String.valueOf(character));
                break;
        }
    }

    // Called when user guesses every letter within the word
    public void winGame() throws IOException {
        Client.playCorrectSound();

        // Increase streak if playing on practice
        if (Client.getCurrentlyPractising()){
            Client.client.setPracticeCurrentStreak(Client.client.getPracticeCurrentStreak() + 1);
        }
        // Increase score if playing on endless
        else {
            Client.client.setCurrentEndlessScore(Client.client.getCurrentEndlessScore() + 1);
        }

        // Check whether matches achievement criteria, and increment that achievement progress
        if (!(Client.getCurrentlyPractising())){
            if (Client.client.getCurrentEndlessLives() > 0){
                Client.getAchievementsProgress().put("3achievement1", Client.getAchievementsProgress().get("3achievement1") + 1);
                if (Client.getAchievementsProgress().get("3achievement1") == Client.getAchievementsList().get(10).getNumberOfTimes()){
                    Client.incrementSkipAchievement(3); // Increment level 4 skip if just completed achievement
                }
                Client.printToServer("incrementAchievement");
                Client.printToServer("3achievement1");
            }
            if (incorrectlyGuessedCharacters.size() < 5){
                Client.getAchievementsProgress().put("3achievement2", Client.getAchievementsProgress().get("3achievement2") + 1);
                if (Client.getAchievementsProgress().get("3achievement2") == Client.getAchievementsList().get(11).getNumberOfTimes()){
                    Client.incrementSkipAchievement(3); // Increment level 4 skip if just completed achievement
                }
                Client.printToServer("incrementAchievement");
                Client.printToServer("3achievement2");
            }
        }



        // Load correct screen depending on if playing in practice or endless
        Scene scene = underscore1.getScene();
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

    // Called when user makes 8 incorrect guesses
    public void loseGame() throws IOException {
        Client.playIncorrectSound();

        // Reset streak if playing on practice
        if (Client.getCurrentlyPractising()){
            Client.client.setPracticeCurrentStreak(0);
        }
        // Take away a life if playing on practice
        else {
            Client.client.setCurrentEndlessLives(Client.client.getCurrentEndlessLives() - 1);
        }

        // Check whether matches achievement criteria, and increment that achievement progress
        if (!(Client.getCurrentlyPractising())){
            if (Client.client.getCurrentEndlessLives() > 0){
                Client.getAchievementsProgress().put("3achievement1", Client.getAchievementsProgress().get("3achievement1") + 1);
                if (Client.getAchievementsProgress().get("3achievement1") == Client.getAchievementsList().get(10).getNumberOfTimes()){
                    Client.incrementSkipAchievement(3); // Increment level 4 skip if just completed achievement
                }
                Client.printToServer("incrementAchievement");
                Client.printToServer("3achievement1");
            }
        }

        // Load correct screen depending if playing on practice or endless mode
        Scene scene = underscore1.getScene();
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

    // When user clicks a letter, check whether it appears in the Spanish word and make the letter button invisible
    public void aButtonClicked() throws IOException {
        verifyCharacter('a');
        aButton.setVisible(false);
        Client.playClickSound();
    }

    public void áButtonClicked() throws IOException {
        verifyCharacter('á');
        áButton.setVisible(false);
        Client.playClickSound();
    }

    public void bButtonClicked() throws IOException {
        verifyCharacter('b');
        bButton.setVisible(false);
        Client.playClickSound();
    }

    public void cButtonClicked() throws IOException {
        verifyCharacter('c');
        cButton.setVisible(false);
        Client.playClickSound();
    }

    public void dButtonClicked() throws IOException {
        verifyCharacter('d');
        dButton.setVisible(false);
        Client.playClickSound();
    }

    public void eButtonClicked() throws IOException {
        verifyCharacter('e');
        eButton.setVisible(false);
        Client.playClickSound();
    }

    public void éButtonClicked() throws IOException {
        verifyCharacter('é');
        éButton.setVisible(false);
        Client.playClickSound();
    }

    public void fButtonClicked() throws IOException {
        verifyCharacter('f');
        fButton.setVisible(false);
        Client.playClickSound();
    }

    public void gButtonClicked() throws IOException {
        verifyCharacter('g');
        gButton.setVisible(false);
        Client.playClickSound();
    }

    public void hButtonClicked() throws IOException {
        verifyCharacter('h');
        hButton.setVisible(false);
        Client.playClickSound();
    }

    public void iButtonClicked() throws IOException {
        verifyCharacter('i');
        iButton.setVisible(false);
        Client.playClickSound();
    }

    public void íButtonClicked() throws IOException {
        verifyCharacter('í');
        íButton.setVisible(false);
        Client.playClickSound();
    }

    public void jButtonClicked() throws IOException {
        verifyCharacter('j');
        jButton.setVisible(false);
        Client.playClickSound();
    }

    public void kButtonClicked() throws IOException {
        verifyCharacter('k');
        kButton.setVisible(false);
        Client.playClickSound();
    }

    public void lButtonClicked() throws IOException {
        verifyCharacter('l');
        lButton.setVisible(false);
        Client.playClickSound();
    }

    public void mButtonClicked() throws IOException {
        verifyCharacter('m');
        mButton.setVisible(false);
        Client.playClickSound();
    }

    public void nButtonClicked() throws IOException {
        verifyCharacter('n');
        nButton.setVisible(false);
        Client.playClickSound();
    }

    public void ñButtonClicked() throws IOException {
        verifyCharacter('ñ');
        ñButton.setVisible(false);
        Client.playClickSound();
    }

    public void oButtonClicked() throws IOException {
        verifyCharacter('o');
        oButton.setVisible(false);
        Client.playClickSound();
    }

    public void óButtonClicked() throws IOException {
        verifyCharacter('ó');
        óButton.setVisible(false);
        Client.playClickSound();
    }

    public void pButtonClicked() throws IOException {
        verifyCharacter('p');
        pButton.setVisible(false);
        Client.playClickSound();
    }

    public void qButtonClicked() throws IOException {
        verifyCharacter('q');
        qButton.setVisible(false);
        Client.playClickSound();
    }

    public void rButtonClicked() throws IOException {
        verifyCharacter('r');
        rButton.setVisible(false);
        Client.playClickSound();
    }

    public void sButtonClicked() throws IOException {
        verifyCharacter('s');
        sButton.setVisible(false);
        Client.playClickSound();
    }

    public void tButtonClicked() throws IOException {
        verifyCharacter('t');
        tButton.setVisible(false);
        Client.playClickSound();
    }

    public void uButtonClicked() throws IOException {
        verifyCharacter('u');
        uButton.setVisible(false);
        Client.playClickSound();
    }

    public void úButtonClicked() throws IOException {
        verifyCharacter('ú');
        úButton.setVisible(false);
        Client.playClickSound();
    }

    public void üButtonClicked() throws IOException {
        verifyCharacter('ü');
        üButton.setVisible(false);
        Client.playClickSound();
    }

    public void vButtonClicked() throws IOException {
        verifyCharacter('v');
        vButton.setVisible(false);
        Client.playClickSound();
    }

    public void wButtonClicked() throws IOException {
        verifyCharacter('w');
        wButton.setVisible(false);
        Client.playClickSound();
    }

    public void xButtonClicked() throws IOException {
        verifyCharacter('x');
        xButton.setVisible(false);
        Client.playClickSound();
    }

    public void yButtonClicked() throws IOException {
        verifyCharacter('y');
        yButton.setVisible(false);
        Client.playClickSound();
    }

    public void zButtonClicked() throws IOException {
        verifyCharacter('z');
        zButton.setVisible(false);
        Client.playClickSound();
    }

    // Return to play menu when user clicks return
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
