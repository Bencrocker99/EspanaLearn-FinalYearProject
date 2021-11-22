package src.com;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.shape.Line;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class BossMode1Handler {
    private Random randomGenerator = new Random();
    private boolean englishToSpanish; // Is the current translation English to Spanish or Spanish to English
    private ArrayList<Word> possibleWords = new ArrayList<Word>(); // Possible words to be chosen for question labels
    private HashMap<Integer, Level> levelsHashMap = new HashMap<Integer, Level>();
    private ArrayList<Label> remainingAnswerBoxes = new ArrayList<Label>(); // Answer boxes which haven't yet been assigned a value
    private ArrayList<Integer> correctOrderedAnswers = new ArrayList<Integer>(); // Ordered so first entry is answer to question1, second to question2 etc
    private HashMap<Integer, Integer> currentLinkings = new HashMap<Integer, Integer>(); // Key is question number, value ie answer (e.g. (1,2) means question 1 is currently linked to answer 2)
    private int random;
    private int questionBoxClicked; // Currently selected question box, has value 0 is no question box is selected
    private boolean answeredCorrectly;


    @FXML
    Label question1Label;
    @FXML
    Label question2Label;
    @FXML
    Label question3Label;
    @FXML
    Label question4Label;
    @FXML
    Label answer1Label;
    @FXML
    Label answer2Label;
    @FXML
    Label answer3Label;
    @FXML
    Label answer4Label;
    @FXML
    Button submitButton;
    @FXML
    Line question1Answer1;
    @FXML
    Line question1Answer2;
    @FXML
    Line question1Answer3;
    @FXML
    Line question1Answer4;
    @FXML
    Line question2Answer1;
    @FXML
    Line question2Answer2;
    @FXML
    Line question2Answer3;
    @FXML
    Line question2Answer4;
    @FXML
    Line question3Answer1;
    @FXML
    Line question3Answer2;
    @FXML
    Line question3Answer3;
    @FXML
    Line question3Answer4;
    @FXML
    Line question4Answer1;
    @FXML
    Line question4Answer2;
    @FXML
    Line question4Answer3;
    @FXML
    Line question4Answer4;
    @FXML
    Button startButton;
    @FXML
    Button returnToMenuButton;
    @FXML
    Line border1;
    @FXML
    Line border2;
    @FXML
    Line border3;
    @FXML
    Line border4;
    @FXML
    Line border5;
    @FXML
    Line border6;
    @FXML
    Line border7;
    @FXML
    Line border8;
    @FXML
    Line border9;
    @FXML
    Line border10;
    @FXML
    Line border11;
    @FXML
    Line border12;
    @FXML
    Line border13;
    @FXML
    Line border14;
    @FXML
    Line border15;
    @FXML
    Line border16;
    @FXML
    AnchorPane newLevelPane;
    @FXML
    Label newLevelLabel;
    @FXML
    Label errorLabel;


    public BossMode1Handler() {
    }

    public void question1LabelClicked() {
        unhiglightBorders(); // Unhighlight any other label's borders
        border1.setVisible(true); // Higlight label1's button
        border2.setVisible(true);
        border3.setVisible(true);
        border4.setVisible(true);
        questionBoxClicked = 1;
        Client.playClickSound();
    }

    public void question2LabelClicked() {
        unhiglightBorders(); // Unhighlight any other label's borders
        border5.setVisible(true); // Higlight label2's button
        border6.setVisible(true);
        border7.setVisible(true);
        border8.setVisible(true);
        questionBoxClicked = 2;
        Client.playClickSound();

    }


    public void question3LabelClicked() {
        unhiglightBorders(); // Unhighlight any other label's borders
        border9.setVisible(true); // Higlight label3's button
        border10.setVisible(true);
        border11.setVisible(true);
        border12.setVisible(true);
        questionBoxClicked = 3;
        Client.playClickSound();
    }

    public void question4LabelClicked() {
        unhiglightBorders(); // Unhighlight any other label's borders
        border13.setVisible(true); // Higlight label4's button
        border14.setVisible(true);
        border15.setVisible(true);
        border16.setVisible(true);
        questionBoxClicked = 4;
        Client.playClickSound();
    }

    // Unhighlight all labels' borders
    public void unhiglightBorders() {
        border1.setVisible(false);
        border2.setVisible(false);
        border3.setVisible(false);
        border4.setVisible(false);
        border5.setVisible(false);
        border6.setVisible(false);
        border7.setVisible(false);
        border8.setVisible(false);
        border9.setVisible(false);
        border10.setVisible(false);
        border11.setVisible(false);
        border12.setVisible(false);
        border13.setVisible(false);
        border14.setVisible(false);
        border15.setVisible(false);
        border16.setVisible(false);
    }

    public void answer1LabelClicked() {
        unhiglightBorders(); // Unhighlight question label's borders
        lineAppear(questionBoxClicked, 1); // Draw a line between the question clicked and answer1 label
        questionBoxClicked = 0;
        Client.playClickSound();
    }

    public void answer2LabelClicked() {
        unhiglightBorders(); // Unhighlight question label's borders
        lineAppear(questionBoxClicked, 2); // Draw a line between the question clicked and answer2 label
        questionBoxClicked = 0;
        Client.playClickSound();
    }

    public void answer3LabelClicked() {
        unhiglightBorders(); // Unhighlight question label's borders
        lineAppear(questionBoxClicked, 3); // Draw a line between the question clicked and answer3 label
        questionBoxClicked = 0;
        Client.playClickSound();
    }

    public void answer4LabelClicked() {
        unhiglightBorders(); // Unhighlight question label's borders
        lineAppear(questionBoxClicked, 4); // Draw a line between the question clicked and answer4 label
        questionBoxClicked = 0;
        Client.playClickSound();

    }

    // Draw a line between the question and answer boxes
    public void lineAppear(int questionBox, int answerBox) {
        switch (answerBox) {
            case 1:
                if (questionBoxClicked != 0) { // If user has an answer box currently selected
                    question2Answer1.setVisible(false); // Hide any current lines attached to answer1 box
                    question1Answer1.setVisible(false);
                    question3Answer1.setVisible(false);
                    question4Answer1.setVisible(false);
                    if (currentLinkings.containsValue(1)) {
                        currentLinkings.values().remove(1); // Remove any current linkings to answer1 box
                    }
                }
                break;
            case 2:
                if (questionBoxClicked != 0) { // If user has an answer box currently selected
                    question2Answer2.setVisible(false); // Hide any current lines attached to answer2 box
                    question1Answer2.setVisible(false);
                    question3Answer2.setVisible(false);
                    question4Answer2.setVisible(false);
                    if (currentLinkings.containsValue(2)) {
                        currentLinkings.values().remove(2); // Remove any current linkings to answer2 box
                    }
                }
                break;
            case 3:
                if (questionBoxClicked != 0) { // If user has an answer box currently selected
                    question2Answer3.setVisible(false); // Hide any current lines attached to answer3 box
                    question1Answer3.setVisible(false);
                    question3Answer3.setVisible(false);
                    question4Answer3.setVisible(false);
                    if (currentLinkings.containsValue(3)) {
                        currentLinkings.values().remove(3); // Remove any current linkings to answer3 box
                    }
                }
                break;
            case 4:
                if (questionBoxClicked != 0) { // If user has an answer box currently selected
                    question2Answer4.setVisible(false); // Hide any current lines attached to answer4 box
                    question1Answer4.setVisible(false);
                    question3Answer4.setVisible(false);
                    question4Answer4.setVisible(false);
                    if (currentLinkings.containsValue(4)) {
                        currentLinkings.values().remove(4); // Remove any current linkings to answer4 box
                    }
                }
                break;
        }
        switch (questionBox) {
            case 1:
                question1Answer1.setVisible(false); // Hide any current lines drawn from to question1 box
                question1Answer2.setVisible(false);
                question1Answer3.setVisible(false);
                question1Answer4.setVisible(false);
                if (currentLinkings.containsKey(1)) {
                    currentLinkings.remove(1); // Remove any current linkings from question1box
                }
                switch (answerBox) { // Draw the line from clicked question box to clicked answer box, and add the new linking
                    case 1:
                        question1Answer1.setVisible(true);
                        currentLinkings.put(1, 1);
                        break;
                    case 2:
                        question1Answer2.setVisible(true);
                        currentLinkings.put(1, 2);
                        break;
                    case 3:
                        question1Answer3.setVisible(true);
                        currentLinkings.put(1, 3);
                        break;
                    case 4:
                        question1Answer4.setVisible(true);
                        currentLinkings.put(1, 4);
                        break;
                }
                break;
            case 2:
                question2Answer1.setVisible(false); // Hide any current lines drawn from to question2 box
                question2Answer2.setVisible(false);
                question2Answer3.setVisible(false);
                question2Answer4.setVisible(false);
                if (currentLinkings.containsKey(2)) {
                    currentLinkings.remove(2); // Remove any current linkings from question1box
                }
                switch (answerBox) { // Draw the line from clicked question box to clicked answer box, and add the new linking
                    case 1:
                        question2Answer1.setVisible(true);
                        currentLinkings.put(2, 1);
                        break;
                    case 2:
                        question2Answer2.setVisible(true);
                        currentLinkings.put(2, 2);
                        break;
                    case 3:
                        question2Answer3.setVisible(true);
                        currentLinkings.put(2, 3);
                        break;
                    case 4:
                        question2Answer4.setVisible(true);
                        currentLinkings.put(2, 4);
                        break;
                }
                break;
            case 3:
                question3Answer1.setVisible(false); // Hide any current lines drawn from to question3 box
                question3Answer2.setVisible(false);
                question3Answer3.setVisible(false);
                question3Answer4.setVisible(false);
                if (currentLinkings.containsKey(3)) {
                    currentLinkings.remove(3); // Remove any current linkings from question1box
                }
                switch (answerBox) { // Draw the line from clicked question box to clicked answer box, and add the new linking
                    case 1:
                        question3Answer1.setVisible(true);
                        currentLinkings.put(3, 1);
                        break;
                    case 2:
                        question3Answer2.setVisible(true);
                        currentLinkings.put(3, 2);
                        break;
                    case 3:
                        question3Answer3.setVisible(true);
                        currentLinkings.put(3, 3);
                        break;
                    case 4:
                        question3Answer4.setVisible(true);
                        currentLinkings.put(3, 4);
                        break;
                }
                break;
            case 4:
                question4Answer1.setVisible(false); // Hide any current lines drawn from to question4 box
                question4Answer2.setVisible(false);
                question4Answer3.setVisible(false);
                question4Answer4.setVisible(false);
                if (currentLinkings.containsKey(4)) {
                    currentLinkings.remove(4); // Remove any current linkings from question1box
                }
                switch (answerBox) { // Draw the line from clicked question box to clicked answer box, and add the new linking
                    case 1:
                        question4Answer1.setVisible(true);
                        currentLinkings.put(4, 1);
                        break;
                    case 2:
                        question4Answer2.setVisible(true);
                        currentLinkings.put(4, 2);
                        break;
                    case 3:
                        question4Answer3.setVisible(true);
                        currentLinkings.put(4, 3);
                        break;
                    case 4:
                        question4Answer4.setVisible(true);
                        currentLinkings.put(4, 4);
                        break;
                }
        }
        currentLinkings.put(questionBox, answerBox);
    }

    public void startButtonClicked() throws IOException {
        answeredCorrectly = true;
        startButton.setVisible(false);
        newLevelPane.setVisible(false);
        newLevelLabel.setVisible(false);
        returnToMenuButton.setVisible(true);
        levelsHashMap = Client.getLevelsHashMap();
        remainingAnswerBoxes = new ArrayList<Label>();
        remainingAnswerBoxes.add(answer1Label);
        remainingAnswerBoxes.add(answer2Label);
        remainingAnswerBoxes.add(answer3Label);
        remainingAnswerBoxes.add(answer4Label);
        currentLinkings = new HashMap<Integer, Integer>();
        correctOrderedAnswers = new ArrayList<Integer>();
        random = randomGenerator.nextInt(2); // Randomise whether questions will be English to Spanish or vice versa
        if (random == 0) {
            englishToSpanish = false;
        } else {
            englishToSpanish = true;
        }
        for (int i = 0; i < levelsHashMap.get(1).getPossibleStructures().size(); i++) { // load up possibleWords with every possible statement for level 1
            for (int j = 0; j < levelsHashMap.get(1).getPossibleStructures().get(i).get(0).getTopicWords().size(); j++) {
                possibleWords.add(levelsHashMap.get(1).getPossibleStructures().get(i).get(0).getTopicWords().get(j));
            }
        }
        for (int i = 0; i < 4; i++) {
            // Choose a random word and assign to currently unassigned question and answer boxes
            random = randomGenerator.nextInt(possibleWords.size());
            Word chosenWord = possibleWords.get(random);
            possibleWords.remove(random);
            random = randomGenerator.nextInt(4 - i);
            Label answerLabel = remainingAnswerBoxes.get(random);
            // Maintain a record of which answer box is correct for which question box
            if (answerLabel.getText().contains("1")) {
                correctOrderedAnswers.add(1);
            } else if (answerLabel.getText().contains("2")) {
                correctOrderedAnswers.add(2);
            } else if (answerLabel.getText().contains("3")) {
                correctOrderedAnswers.add(3);
            } else {
                correctOrderedAnswers.add(4);
            }
            remainingAnswerBoxes.remove(random);

            // Assign the appropriate text for the question and answer labels
            switch (i) {
                case 0:
                    if (englishToSpanish) {
                        question1Label.setText(chosenWord.getMainTranslation());
                        answerLabel.setText(chosenWord.getWord());
                    } else {
                        question1Label.setText(chosenWord.getWord());
                        answerLabel.setText(chosenWord.getMainTranslation());
                    }
                    break;
                case 1:
                    if (englishToSpanish) {
                        question2Label.setText(chosenWord.getMainTranslation());
                        answerLabel.setText(chosenWord.getWord());
                    } else {
                        question2Label.setText(chosenWord.getWord());
                        answerLabel.setText(chosenWord.getMainTranslation());
                    }
                    break;
                case 2:
                    if (englishToSpanish) {
                        question3Label.setText(chosenWord.getMainTranslation());
                        answerLabel.setText(chosenWord.getWord());
                    } else {
                        question3Label.setText(chosenWord.getWord());
                        answerLabel.setText(chosenWord.getMainTranslation());
                    }
                    break;
                case 3:
                    if (englishToSpanish) {
                        question4Label.setText(chosenWord.getMainTranslation());
                        answerLabel.setText(chosenWord.getWord());
                    } else {
                        question4Label.setText(chosenWord.getWord());
                        answerLabel.setText(chosenWord.getMainTranslation());
                    }
                    break;
            }

        }
        Client.playClickSound();
    }

    public void submitButtonClicked() throws IOException, InterruptedException {
        // If not all question boxes are linked to answer boxes, give an error message and allow users to correct it
        if (currentLinkings.size() < 4){
            errorLabel.setVisible(true);
            return;
        }

        Client.playClickSound();
        // Ensure each questionBox is linked to its corresponding correct answer box
        for (int i = 1; i < 5; i++) {
            int guessedAnswer = currentLinkings.get(i);
            int actualAnswer = correctOrderedAnswers.get(i - 1); // subtract 1 as currentLinkings isn't zero indexed but correctOrderedAnswers is
            if (guessedAnswer != actualAnswer) {
                answeredCorrectly = false;
            }
        }

        if (!answeredCorrectly) {
            Client.playIncorrectSound();
            if (Client.getCurrentlyPractising()){
                Client.setPracticeCurrentStreak(0); // Reset current streak if in practice mode
            }
            else {
                Client.client.setCurrentEndlessLives(Client.client.getCurrentEndlessLives() - 1); // Subtract a life
            }
        }
        else if (Client.getCurrentlyPractising()){
            Client.playCorrectSound();
            Client.setPracticeCurrentStreak(Client.getPracticeCurrentStreak() + 1); // Add 1 to the current practice streak if correct
        }
        else {
            Client.playCorrectSound();
        }
        Client.client.setCurrentEndlessScore(Client.client.getCurrentEndlessScore() + 1); // Correct or not, add 1 to endless score


        // Check whether matches achievement criteria, and increment that achievement progress
        if (!(Client.getCurrentlyPractising())){
            if (Client.client.getCurrentEndlessLives() > 0){
                Client.getAchievementsProgress().put("1achievement1", Client.getAchievementsProgress().get("1achievement1") + 1);
                if (Client.getAchievementsProgress().get("1achievement1") == Client.getAchievementsList().get(0).getNumberOfTimes()){
                    Client.incrementSkipAchievement(1); // Increment level 2 skip if just completed achievement
                }
                Client.printToServer("incrementAchievement");
                Client.printToServer("1achievement1");
            }
        }

        // Check whether matches achievement criteria, and increment that achievement progress
        if (answeredCorrectly && !(Client.getCurrentlyPractising())){
            Client.getAchievementsProgress().put("1achievement2", Client.getAchievementsProgress().get("1achievement2") + 1);
            if (Client.getAchievementsProgress().get("1achievement2") == Client.getAchievementsList().get(1).getNumberOfTimes()){ // Finished achievement so increment achievement for unlocking all others
                Client.incrementSkipAchievement(1); // Increment level 2 skip if just completed achievement
            }
            Client.printToServer("incrementAchievement");
            Client.printToServer("1achievement2");
        }

        Scene scene = submitButton.getScene();
        Window window = scene.getWindow();
        Stage stage = (Stage) window;
        FXMLLoader loader = new FXMLLoader();

        // Load practice mode stage if in practice mode
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

    // Load main play menu when user clicks return to menu button
    public void returnButtonClicked() throws IOException {
        Scene scene = returnToMenuButton.getScene();
        Window window = scene.getWindow();
        Stage stage = (Stage) window;
        Parent playMenu = FXMLLoader.load(getClass().getResource("PlayMenu.fxml"));
        stage.setTitle("Play Menu");
        stage.getScene().setRoot(playMenu);
        stage.show();
        Client.playClickSound();
    }


}

