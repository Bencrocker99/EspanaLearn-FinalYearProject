package src.com;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.IOException;
import java.util.ArrayList;

public class AchievementsHandler {
    private ArrayList<Achievement> achievementsList;

    public AchievementsHandler() {
        achievementsList = Client.getAchievementsList();
    }


    private int numberOfPlacedAchievements = 0; // Current number of achievements placed on the current page
    private Font mainFont = new Font(30.0);
    private Font symbolFont = new Font(84.0);
    private Font progressNumberFont = new Font(40.0);

    @FXML
    Button achievementsReturnToMenuButton;
    @FXML
    Button level1Button;
    @FXML
    Button level2Button;
    @FXML
    Button level3Button;
    @FXML
    Button level4Button;
    @FXML
    Button level5Button;
    @FXML
    Button generalButton;
    @FXML
    ScrollPane scrollPane;
    @FXML
    AnchorPane scrollAnchorPane;



    public void placeAchievement(Achievement achievement){
        boolean completed = false;
        int progress = Client.getAchievementsProgress().get(achievement.getAchievementName());
        int maxProgress = achievement.getNumberOfTimes();
        if (progress >= maxProgress){ // See whether the user has fully completed the achievement
            completed = true;
            progress = maxProgress;
        }

        // Setting up the progress bar with user's current progress
        ProgressBar progressBar = new ProgressBar();
        progressBar.setLayoutX(20.0);
        progressBar.setPrefWidth(1209.0);
        progressBar.setPrefHeight(78.0);
        progressBar.setLayoutY(29.0 + numberOfPlacedAchievements * 131.0);
        progressBar.setProgress((float) progress / maxProgress);
        scrollAnchorPane.getChildren().add(progressBar);

        // Setting up the label which describes how to complete the achievement
        Label achievementLabel = new Label();
        achievementLabel.setText(achievement.getAchievementDescription());
        achievementLabel.setLayoutX(20.0);
        achievementLabel.setPrefWidth(1209.0);
        achievementLabel.setPrefHeight(131.0);
        achievementLabel.setLayoutY(numberOfPlacedAchievements * 131.0);
        achievementLabel.setFont(mainFont);
        achievementLabel.setTextAlignment(TextAlignment.CENTER);
        achievementLabel.setAlignment(Pos.CENTER);
        scrollAnchorPane.getChildren().add(achievementLabel);

        // Setting up the digital display to show user their exact progress with the achievement
        Label progressNumberLabel = new Label();
        progressNumberLabel.setText(progress + " / " + maxProgress);
        progressNumberLabel.setLayoutX(1021.0);
        progressNumberLabel.setLayoutY(numberOfPlacedAchievements * 131.0);
        progressNumberLabel.setPrefHeight(131.0);
        progressNumberLabel.setPrefWidth(260.0);
        progressNumberLabel.setFont(progressNumberFont);
        progressNumberLabel.setTextAlignment(TextAlignment.CENTER);
        progressNumberLabel.setAlignment(Pos.CENTER);
        scrollAnchorPane.getChildren().add(progressNumberLabel);

        // Setting up a green tick if they have completed the achievement, and a red cross if they haven't
        Label progressLabel = new Label();
        if (completed){
            progressLabel.setText("✔");
            progressLabel.setTextFill(Color.LIGHTGREEN);
        }
        else {
            progressLabel.setText("❌");
            progressLabel.setTextFill(Color.RED);
        }
        progressLabel.setLayoutX(1277.0);
        progressLabel.setPrefHeight(131.0);
        progressLabel.setPrefWidth(131.0);
        progressLabel.setLayoutY(numberOfPlacedAchievements * 131.0);
        progressLabel.setFont(symbolFont);
        progressLabel.setTextAlignment(TextAlignment.CENTER);
        progressLabel.setAlignment(Pos.CENTER);
        scrollAnchorPane.getChildren().add(progressLabel);
        numberOfPlacedAchievements++;
    }

    // Load level 1 achievements when level 1 button clicked
    public void level1ButtonClicked() {
        scrollAnchorPane.getChildren().clear();
        numberOfPlacedAchievements = 0;
        for (int i = 0; i < achievementsList.size(); i++) {
            if (achievementsList.get(i).getAchievementType() == 1){
                placeAchievement(achievementsList.get(i));
            }
        }
        Client.playClickSound();
    }

    // Load level 2 achievements when level 2 button clicked
    public void level2ButtonClicked(){
        scrollAnchorPane.getChildren().clear();
        numberOfPlacedAchievements = 0;
        for (int i = 0; i < achievementsList.size(); i++) {
            if (achievementsList.get(i).getAchievementType() == 2){
                placeAchievement(achievementsList.get(i));
            }
        }
        Client.playClickSound();
    }

    // Load level 3 achievements when level 3 button clicked
    public void level3ButtonClicked(){
        scrollAnchorPane.getChildren().clear();
        numberOfPlacedAchievements = 0;
        for (int i = 0; i < achievementsList.size(); i++) {
            if (achievementsList.get(i).getAchievementType() == 3){
                placeAchievement(achievementsList.get(i));
            }
        }
        Client.playClickSound();
    }

    // Load level 4 achievements when level 4 button clicked
    public void level4ButtonClicked(){
        scrollAnchorPane.getChildren().clear();
        numberOfPlacedAchievements = 0;
        for (int i = 0; i < achievementsList.size(); i++) {
            if (achievementsList.get(i).getAchievementType() == 4){
                placeAchievement(achievementsList.get(i));
            }
        }
        Client.playClickSound();
    }

    // Load general achievements when general button clicked
    public void generalButtonClicked(){
        scrollAnchorPane.getChildren().clear();
        numberOfPlacedAchievements = 0;
        for (int i = 0; i < achievementsList.size(); i++) {
            if (achievementsList.get(i).getAchievementType() == 0){
                placeAchievement(achievementsList.get(i));
            }
        }
        Client.playClickSound();
    }


    // Load main menu when user clicks return to menu button
    public void achievementsReturnButtonClicked() throws IOException {
        Scene scene = achievementsReturnToMenuButton.getScene();
        Window window = scene.getWindow();
        Stage stage = (Stage) window;
        Parent mainMenu = FXMLLoader.load(getClass().getResource("MainMenu.fxml"));
        stage.setTitle("Main Menu");
        stage.getScene().setRoot(mainMenu);
        stage.show();
        Client.playClickSound();
    }

}
