package src.com;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;


public class VocabularyAndGrammarHandler {

    @FXML
    BorderPane borderPane;
    @FXML
    Button loadButton;
    @FXML
    AnchorPane anchorPane;
    @FXML
    ScrollPane scrollPane;
    @FXML
    AnchorPane scrollAnchorPane;
    @FXML
    Button vocabularyReturnToMenuButton;
    @FXML
    Button vocabularyButton;
    @FXML
    Button conjugationsButton;

    private double currentLowestHeight;
    private HashMap<String, Topic> topicsHashMap = new HashMap<String, Topic>();
    private ArrayList<Topic> topicsList = new ArrayList<Topic>();
    private Font titleFont = new Font("Arial Bold", 60.0);
    private Font mainFont = new Font(24.0);
    private Font subtitleFont = new Font("Arial Bold", 36.0);
    private Font headings = new Font("Arial Bold", 30.0);

    public VocabularyAndGrammarHandler() {
        topicsHashMap = Client.getTopicsHashMap();
        topicsList = Client.getTopicsList();

    }

    // When user clicks vocabulary, show user all vocabulary
    public void vocabularyButtonClicked() {
        Client.playClickSound();
        scrollAnchorPane.getChildren().clear();
        addVocabulary();
    }

    // When user clicks conjugations, show all verb ending
    public void conjugationsButtonClicked() throws IOException {
        Client.playClickSound();
        scrollAnchorPane.getChildren().clear();
        addVerbConjugations();
    }


    public void addVocabulary() {
        // Set main title
        Label vocabularyTitle = new Label();
        vocabularyTitle.setText("Vocabulary");
        vocabularyTitle.setLayoutX(10.0);
        vocabularyTitle.setLayoutY(10.0);
        vocabularyTitle.setFont(titleFont);

        currentLowestHeight = vocabularyTitle.getLayoutY() + 90.0; // Offset by enough so next text can just go underneath
        scrollAnchorPane.getChildren().add(vocabularyTitle);
        // Set subtitles
        Label mainWordSubtitle = new Label();
        mainWordSubtitle.setText("English Word");
        mainWordSubtitle.setFont(subtitleFont);
        mainWordSubtitle.setLayoutX(10.0);
        mainWordSubtitle.setLayoutY(currentLowestHeight + 10.0);

        Label translationSubtitle = new Label();
        translationSubtitle.setText("Main Translation");
        translationSubtitle.setFont(subtitleFont);
        translationSubtitle.setLayoutX(280.0);
        translationSubtitle.setLayoutY(currentLowestHeight + 10.0);

        Label genderSubtitle = new Label();
        genderSubtitle.setText("Gender");
        genderSubtitle.setFont(subtitleFont);
        genderSubtitle.setLayoutX(585.0);
        genderSubtitle.setLayoutY(currentLowestHeight + 10.0);

        Label englishPluralSubtitle = new Label();
        englishPluralSubtitle.setText("English Plural");
        englishPluralSubtitle.setFont(subtitleFont);
        englishPluralSubtitle.setLayoutX(770.0);
        englishPluralSubtitle.setLayoutY(currentLowestHeight + 10.0);

        Label spanishPluralSubtitle = new Label();
        spanishPluralSubtitle.setText("Spanish Plural");
        spanishPluralSubtitle.setFont(subtitleFont);
        spanishPluralSubtitle.setLayoutX(1080.0);
        spanishPluralSubtitle.setLayoutY(currentLowestHeight + 10.0);

        currentLowestHeight = mainWordSubtitle.getLayoutY() + 30.0;
        scrollAnchorPane.getChildren().addAll(mainWordSubtitle, translationSubtitle, genderSubtitle, englishPluralSubtitle, spanishPluralSubtitle);

        // Start each new topic with a subtitle
        for (int i = 0; i < topicsList.size(); i++) {
            Label topicTitle = new Label();
            String topicTitleString = topicsList.get(i).getTopicName();
            topicTitleString = topicTitleString.substring(0, 1).toUpperCase() + topicTitleString.substring(1); // Capitalise first letter
            // Insert capitals before spaces (variable names are camel case)
            for (int j = 1; j < topicTitleString.length(); j++) {
                char currentCharacter = topicTitleString.charAt(j);
                String currentCapitalisedCharacter = Character.toString(currentCharacter).toUpperCase();
                //if original character is capitalised
                if (Character.toString(currentCharacter).equals(currentCapitalisedCharacter)) {
                    topicTitleString = topicTitleString.substring(0, j) + " " + topicTitleString.substring(j);
                    j++;
                }
            }
            topicTitle.setText(topicTitleString);
            topicTitle.setFont(headings);
            topicTitle.setLayoutY(currentLowestHeight + 55.0);
            currentLowestHeight = topicTitle.getLayoutY() + 20.0;
            scrollAnchorPane.getChildren().add(topicTitle);

            // For every word within that topic
            for (int j = 0; j < topicsList.get(i).getTopicWords().size(); j++) {

                // Set up label to show the word in Spanish
                Label mainWord = new Label();
                mainWord.setText(topicsList.get(i).getTopicWords().get(j).getMainTranslation());
                mainWord.setLayoutX(20.0);
                mainWord.setLayoutY(currentLowestHeight + 35.0);
                mainWord.setFont(mainFont);

                // Set up label to show the word's English translation
                Label mainTranslation = new Label();
                mainTranslation.setText(topicsList.get(i).getTopicWords().get(j).getWord());
                mainTranslation.setLayoutX(280.0);
                mainTranslation.setFont(mainFont);
                mainTranslation.setLayoutY(currentLowestHeight + 35.0);


                // Set up label to show word's gender (with n/a if this doesn't apply to the type of word)
                Label gender = new Label();
                String genderString = (String.valueOf(topicsList.get(i).getTopicWords().get(j).getGender()));
                if (genderString.equals("n")) {
                    gender.setText("n/a");
                } else gender.setText(genderString);
                gender.setLayoutX(620.0);
                gender.setFont(mainFont);
                gender.setLayoutY(currentLowestHeight + 35.0);

                // Set up label to show word's English plural
                Label englishPlural = new Label();
                String englishPluralString = topicsList.get(i).getTopicWords().get(j).getEnglishPlural();
                if (englishPluralString.equals("")) {
                    englishPlural.setText("n/a");
                } else englishPlural.setText(englishPluralString);
                englishPlural.setLayoutX(800.0);
                englishPlural.setFont(mainFont);
                englishPlural.setLayoutY(currentLowestHeight + 35.0);


                // Set up label to show word's Spanish plural
                Label spanishPlural = new Label();
                String spanishPluralString = topicsList.get(i).getTopicWords().get(j).getSpanishPlural();
                if (spanishPluralString.equals("")) {
                    spanishPlural.setText("n/a");
                } else spanishPlural.setText(spanishPluralString);
                spanishPlural.setLayoutX(1110.0);
                spanishPlural.setFont(mainFont);
                spanishPlural.setLayoutY(currentLowestHeight + 35.0);

                // Add all these labels
                currentLowestHeight = mainWord.getLayoutY() + 10.0;
                scrollAnchorPane.getChildren().addAll(mainWord, mainTranslation, gender, englishPlural, spanishPlural);
            }
        }
    }

    public void addVerbConjugations() throws IOException {
        // Set up main title
        Label vocabularyTitle = new Label();
        vocabularyTitle.setText("Verb Conjugations");
        vocabularyTitle.setLayoutX(10.0);
        vocabularyTitle.setLayoutY(10.0);
        vocabularyTitle.setFont(titleFont);

        currentLowestHeight = vocabularyTitle.getLayoutY() + 55.0; // Offset by enough so next text can just go underneath
        scrollAnchorPane.getChildren().add(vocabularyTitle);


        for (int i = 0; i < topicsList.size(); i++) {
            // if current topic isnt verbs, continue
            if (!(topicsList.get(i).getTopicName().contains("Verbs"))) {
                continue;
            }
            // Set up subtitle for each topic
            Label topicTitle = new Label();
            String topicTitleString = topicsList.get(i).getTopicName();
            topicTitleString = topicTitleString.substring(0, 1).toUpperCase() + topicTitleString.substring(1); // Capitalise first letter

            for (int j = 1; j < topicTitleString.length(); j++) { // Insert capitals before spaces (variable names are camel case)
                char currentCharacter = topicTitleString.charAt(j);
                String currentCapitalisedCharacter = Character.toString(currentCharacter).toUpperCase();
                if (Character.toString(currentCharacter).equals(currentCapitalisedCharacter)) { //original character is capitalised
                    topicTitleString = topicTitleString.substring(0, j) + " " + topicTitleString.substring(j);
                    j++;
                }
            }
            topicTitle.setText(topicTitleString);
            topicTitle.setFont(headings);
            topicTitle.setLayoutY(currentLowestHeight + 55.0);
            currentLowestHeight = topicTitle.getLayoutY() + 20.0;
            scrollAnchorPane.getChildren().add(topicTitle);

            // For each word in topic
            for (int j = 0; j < topicsList.get(i).getTopicWords().size(); j++) {

                Word currentWord = topicsList.get(i).getTopicWords().get(j);
                // Setup label to show the word
                Label mainWord = new Label();
                mainWord.setText(currentWord.getWord() + " (" + currentWord.getMainTranslation() + ")");
                mainWord.setLayoutX(20.0);
                mainWord.setLayoutY(currentLowestHeight + 35.0);
                mainWord.setFont(headings);
                currentLowestHeight = currentLowestHeight + 35.0;

                ArrayList<String> presentEnglishWords = new ArrayList<String>();
                ArrayList<String> presentSpanishWords = new ArrayList<String>();
                ArrayList<String> preteriteEnglishWords = new ArrayList<String>();
                ArrayList<String> preteriteSpanishWords = new ArrayList<String>();

                int infinitiveLength = currentWord.getInfinitive().length();
                char infinitiveType = currentWord.getInfinitive().charAt(infinitiveLength - 2);
                // For each language and tense, if there isn't a list of irregular conjugations available, assume
                // they are regular and work out correct endings
                if (currentWord.getIrregularConjugations().get("presentEnglish") != null) {
                    presentEnglishWords = currentWord.getIrregularConjugations().get("presentEnglish");
                } else {
                    presentEnglishWords.add(currentWord.getMainTranslation());
                    presentEnglishWords.add(currentWord.getMainTranslation());
                    presentEnglishWords.add(currentWord.getMainTranslation() + "s");
                    presentEnglishWords.add(currentWord.getMainTranslation());
                    presentEnglishWords.add(currentWord.getMainTranslation());
                    presentEnglishWords.add(currentWord.getMainTranslation());
                }
                if (currentWord.getIrregularConjugations().get("presentSpanish") != null) {
                    presentSpanishWords = currentWord.getIrregularConjugations().get("presentSpanish");
                } else {
                    if (infinitiveType == 'a') {
                        presentSpanishWords.add(currentWord.getStem() + "o");
                        presentSpanishWords.add(currentWord.getStem() + "as");
                        presentSpanishWords.add(currentWord.getStem() + "a");
                        presentSpanishWords.add(currentWord.getStem() + "amos");
                        presentSpanishWords.add(currentWord.getStem() + "áis");
                        presentSpanishWords.add(currentWord.getStem() + "an");
                    } else if (infinitiveType == 'e') {
                        presentSpanishWords.add(currentWord.getStem() + "o");
                        presentSpanishWords.add(currentWord.getStem() + "es");
                        presentSpanishWords.add(currentWord.getStem() + "e");
                        presentSpanishWords.add(currentWord.getStem() + "emos");
                        presentSpanishWords.add(currentWord.getStem() + "éis");
                        presentSpanishWords.add(currentWord.getStem() + "en");
                    } else {
                        presentSpanishWords.add(currentWord.getStem() + "o");
                        presentSpanishWords.add(currentWord.getStem() + "es");
                        presentSpanishWords.add(currentWord.getStem() + "e");
                        presentSpanishWords.add(currentWord.getStem() + "imos");
                        presentSpanishWords.add(currentWord.getStem() + "ís");
                        presentSpanishWords.add(currentWord.getStem() + "en");
                    }
                }
                if (currentWord.getIrregularConjugations().get("preteriteEnglish") != null) {
                    preteriteEnglishWords = currentWord.getIrregularConjugations().get("preteriteEnglish");
                } else {
                    preteriteEnglishWords.add(currentWord.getMainTranslation() + "ed");
                    preteriteEnglishWords.add(currentWord.getMainTranslation() + "ed");
                    preteriteEnglishWords.add(currentWord.getMainTranslation() + "ed");
                    preteriteEnglishWords.add(currentWord.getMainTranslation() + "ed");
                    preteriteEnglishWords.add(currentWord.getMainTranslation() + "ed");
                    preteriteEnglishWords.add(currentWord.getMainTranslation() + "ed");
                }
                if (currentWord.getIrregularConjugations().get("preteriteSpanish") != null) {
                    preteriteSpanishWords = currentWord.getIrregularConjugations().get("preteriteSpanish");
                } else {
                    if (infinitiveType == 'a') {
                        preteriteSpanishWords.add(currentWord.getStem() + "é");
                        preteriteSpanishWords.add(currentWord.getStem() + "aste");
                        preteriteSpanishWords.add(currentWord.getStem() + "ó");
                        preteriteSpanishWords.add(currentWord.getStem() + "amos");
                        preteriteSpanishWords.add(currentWord.getStem() + "asteis");
                        preteriteSpanishWords.add(currentWord.getStem() + "aron");
                    } else if (infinitiveType == 'e') {
                        preteriteSpanishWords.add(currentWord.getStem() + "í");
                        preteriteSpanishWords.add(currentWord.getStem() + "iste");
                        preteriteSpanishWords.add(currentWord.getStem() + "ió");
                        preteriteSpanishWords.add(currentWord.getStem() + "imos");
                        preteriteSpanishWords.add(currentWord.getStem() + "isteis");
                        preteriteSpanishWords.add(currentWord.getStem() + "ieron");
                    } else {
                        preteriteSpanishWords.add(currentWord.getStem() + "í");
                        preteriteSpanishWords.add(currentWord.getStem() + "iste");
                        preteriteSpanishWords.add(currentWord.getStem() + "ió");
                        preteriteSpanishWords.add(currentWord.getStem() + "imos");
                        preteriteSpanishWords.add(currentWord.getStem() + "ísteis");
                        preteriteSpanishWords.add(currentWord.getStem() + "ieron");
                    }
                }

                currentLowestHeight = mainWord.getLayoutY() + 50.0;

                // Set up label for present, showing english followed by Spanish, for each of the 6 persons
                Label presentLabel1 = new Label();
                presentLabel1.setText("I " + presentEnglishWords.get(0) + ": " + presentSpanishWords.get(0));
                presentLabel1.setLayoutX(20.0);
                presentLabel1.setFont(mainFont);
                presentLabel1.setLayoutY(currentLowestHeight + 10.0);

                Label presentLabel2 = new Label();
                presentLabel2.setText("You " + presentEnglishWords.get(1) + ": " + presentSpanishWords.get(1));
                presentLabel2.setLayoutX(521.0);
                presentLabel2.setFont(mainFont);
                presentLabel2.setLayoutY(currentLowestHeight + 10.0);

                Label presentLabel3 = new Label();
                presentLabel3.setText("He/She/It " + presentEnglishWords.get(2) + ": " + presentSpanishWords.get(2));
                presentLabel3.setLayoutX(1022.0);
                presentLabel3.setFont(mainFont);
                presentLabel3.setLayoutY(currentLowestHeight + 10.0);

                currentLowestHeight = currentLowestHeight + 40.0;

                Label presentLabel4 = new Label();
                presentLabel4.setText("We " + presentEnglishWords.get(3) + ": " + presentSpanishWords.get(3));
                presentLabel4.setLayoutX(20.0);
                presentLabel4.setFont(mainFont);
                presentLabel4.setLayoutY(currentLowestHeight + 10.0);

                Label presentLabel5 = new Label();
                presentLabel5.setText("You (plural) " + presentEnglishWords.get(4) + ": " + presentSpanishWords.get(4));
                presentLabel5.setLayoutX(521.0);
                presentLabel5.setFont(mainFont);
                presentLabel5.setLayoutY(currentLowestHeight + 10.0);

                Label presentLabel6 = new Label();
                presentLabel6.setText("They " + presentEnglishWords.get(5) + ": " + presentSpanishWords.get(5));
                presentLabel6.setLayoutX(1022.0);
                presentLabel6.setFont(mainFont);
                presentLabel6.setLayoutY(currentLowestHeight + 10.0);

                currentLowestHeight = currentLowestHeight + 60.0;

                // Set up label for preterite, showing english followed by Spanish, for each of the 6 persons
                Label preteriteLabel1 = new Label();
                preteriteLabel1.setText("I " + preteriteEnglishWords.get(0) + ": " + preteriteSpanishWords.get(0));
                preteriteLabel1.setLayoutX(20.0);
                preteriteLabel1.setFont(mainFont);
                preteriteLabel1.setLayoutY(currentLowestHeight + 10.0);

                Label preteriteLabel2 = new Label();
                preteriteLabel2.setText("You " + preteriteEnglishWords.get(1) + ": " + preteriteSpanishWords.get(1));
                preteriteLabel2.setLayoutX(521.0);
                preteriteLabel2.setFont(mainFont);
                preteriteLabel2.setLayoutY(currentLowestHeight + 10.0);

                Label preteriteLabel3 = new Label();
                preteriteLabel3.setText("He/She/It " + preteriteEnglishWords.get(2) + ": " + preteriteSpanishWords.get(2));
                preteriteLabel3.setLayoutX(1022.0);
                preteriteLabel3.setFont(mainFont);
                preteriteLabel3.setLayoutY(currentLowestHeight + 10.0);

                currentLowestHeight = currentLowestHeight + 40.0;

                Label preteriteLabel4 = new Label();
                preteriteLabel4.setText("We " + preteriteEnglishWords.get(3) + ": " + preteriteSpanishWords.get(3));
                preteriteLabel4.setLayoutX(20.0);
                preteriteLabel4.setFont(mainFont);
                preteriteLabel4.setLayoutY(currentLowestHeight + 10.0);

                Label preteriteLabel5 = new Label();
                preteriteLabel5.setText("You (plural) " + preteriteEnglishWords.get(4) + ": " + preteriteSpanishWords.get(4));
                preteriteLabel5.setLayoutX(521.0);
                preteriteLabel5.setFont(mainFont);
                preteriteLabel5.setLayoutY(currentLowestHeight + 10.0);

                Label preteriteLabel6 = new Label();
                preteriteLabel6.setText("They " + preteriteEnglishWords.get(5) + ": " + preteriteSpanishWords.get(5));
                preteriteLabel6.setLayoutX(1022.0);
                preteriteLabel6.setFont(mainFont);
                preteriteLabel6.setLayoutY(currentLowestHeight + 10.0);

                currentLowestHeight = currentLowestHeight + 30.0;

                scrollAnchorPane.getChildren().addAll(mainWord, presentLabel1, presentLabel2, presentLabel3, presentLabel4, presentLabel5, presentLabel6, preteriteLabel1, preteriteLabel2, preteriteLabel3, preteriteLabel4, preteriteLabel5, preteriteLabel6);
            }
        }
    }


// Load play menu screen if user clicks return
    public void vocabularyReturnButtonClicked() throws IOException {
        Client.playClickSound();
        Scene scene = vocabularyReturnToMenuButton.getScene();
        Window window = scene.getWindow();
        Stage stage = (Stage) window;
        Parent playMenu = FXMLLoader.load(getClass().getResource("PlayMenu.fxml"));
        stage.setTitle("Play Menu");
        stage.getScene().setRoot(playMenu);
        stage.show();
    }

}
