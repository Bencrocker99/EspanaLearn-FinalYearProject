package src.com;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.BufferedReader;
import java.io.IOException;

public class LeaderboardsHandler {

    @FXML
    Button leaderboardsReturnToMenuButton;
    @FXML
    Label userScoreLabel;
    @FXML
    Button highestEndlessScoreButton;
    @FXML
    Button totalEndlessScoreButton;
    @FXML
    ScrollPane scrollPane;
    @FXML
    AnchorPane scrollAnchorPane;
    @FXML
    Button highestEndlessLevelButton;

    public LeaderboardsHandler() {

    }

    private Font mainFont = new Font(40.0);
    private Font rankingFont = new Font(60.0);

    // Load main menu screen when user clicks return
    public void leaderboardsReturnButtonClicked() throws IOException {
        Client.playClickSound();
        Scene scene = leaderboardsReturnToMenuButton.getScene();
        Window window = scene.getWindow();
        Stage stage = (Stage) window;
        Parent mainMenu = FXMLLoader.load(getClass().getResource("MainMenu.fxml"));
        stage.setTitle("Main Menu");
        stage.getScene().setRoot(mainMenu);
        stage.show();
    }

    // If user requests to see highest score leaderboard
    public void highestEndlessScoreButtonClicked() throws IOException {
        Client.playClickSound();
        // Clear any current FX elements in the scrollpane
        scrollAnchorPane.getChildren().clear();
        // Display user's highest endless score
        userScoreLabel.setVisible(true);
        userScoreLabel.setText("Your Highest Endless Score: " + Client.getHighestScore());
        // Tell server client wants highscore information
        Client.printToServer("leaderboardByHighestScore");
        BufferedReader bufferedReader = Client.getBufferedReader();
        // Read in each entry name and score server sends over in descending order of score, until server sends "done"
        for (int i = 0; i < 50; i++){
            String username = bufferedReader.readLine();
            if (username.equals("done")){
                break;
            }
            int score = Integer.parseInt(bufferedReader.readLine());
            // Place the name and score
            placeLeaderboardEntry(username, score, "Highscore", i + 1);
        }
    }

    // If user requests to see highest level leaderboard
    public void highestEndlessLevelButtonClicked() throws IOException {
        Client.playClickSound();
        // Clear any current FX elements in the scrollpane
        scrollAnchorPane.getChildren().clear();
        // Display user's highest endless level
        userScoreLabel.setVisible(true);
        userScoreLabel.setText("Your Highest Endless Level: " + Client.getHighestLevel());
        // Tell server client wants high level information
        Client.printToServer("leaderboardByHighestLevel");
        BufferedReader bufferedReader = Client.getBufferedReader();
        // Read in each entry name and highest level server sends over in descending order of level, until server sends "done"
        for (int i = 0; i < 50; i++){
            String username = bufferedReader.readLine();
            if (username.equals("done")){
                break;
            }
            int level = Integer.parseInt(bufferedReader.readLine());
            // Place the name and level number
            placeLeaderboardEntry(username, level, "Highest Level", i + 1);
        }
    }

    // If user requests to see total score leaderboard
    public void totalEndlessScoreButtonClicked() throws IOException {
        Client.playClickSound();
        // Clear any current FX elements in the scrollpane
        scrollAnchorPane.getChildren().clear();
        // Display user's total score
        userScoreLabel.setVisible(true);
        userScoreLabel.setText("Your Total Endless Score: " + Client.getTotalScore());
        // Tell server client wants total score information
        Client.printToServer("leaderboardByTotalScore");
        BufferedReader bufferedReader = Client.getBufferedReader();
        // Read in each entry name and score server sends over in descending order of score, until server sends "done"
        for (int i = 0; i < 50; i++){
            String username = bufferedReader.readLine();
            if (username.equals("done")){
                break;
            }
            int score = Integer.parseInt(bufferedReader.readLine());
            // Place the name and score
            placeLeaderboardEntry(username, score, "Total Score", i + 1);
        }
    }

    // Place received entry into leaderboard
    public void placeLeaderboardEntry(String username, int score, String criteria, int position) {
        Label rankingLabel = new Label();
        // Set up Ranking label (displays the number they ranked at)
        // Colour 1st, 2nd and 3rd places as gold, silver and bronze
        rankingLabel.setText(String.valueOf(position));
        if (position == 1){
            rankingLabel.setTextFill(Color.GOLD);
        }
        else if (position == 2){
            rankingLabel.setTextFill(Color.GREY);
        }
        else if (position == 3){
            rankingLabel.setTextFill(Color.SADDLEBROWN);
        }
        rankingLabel.setLayoutX(0.0);
        rankingLabel.setPrefWidth(170.0);
        rankingLabel.setPrefHeight(131.0);
        rankingLabel.setLayoutY((position - 1) * 131.0);
        rankingLabel.setFont(rankingFont);
        rankingLabel.setTextAlignment(TextAlignment.CENTER);
        rankingLabel.setAlignment(Pos.CENTER);
        scrollAnchorPane.getChildren().add(rankingLabel);

        // Set up username label (displays their username)
        Label usernameLabel = new Label();
        usernameLabel.setText(username);
        usernameLabel.setLayoutX(170.0);
        usernameLabel.setPrefWidth(720.0);
        usernameLabel.setPrefHeight(131.0);
        usernameLabel.setLayoutY((position - 1) * 131.0);
        usernameLabel.setFont(mainFont);
        usernameLabel.setTextAlignment(TextAlignment.CENTER);
        usernameLabel.setAlignment(Pos.CENTER);
        scrollAnchorPane.getChildren().add(usernameLabel);

        // Set up score label (displays their score for the particular leaderboard ranking)
        Label scoreLabel = new Label();
        scoreLabel.setText(criteria + ": " + score);
        scoreLabel.setLayoutX(890.0);
        scoreLabel.setPrefWidth(563.0);
        scoreLabel.setPrefHeight(131.0);
        scoreLabel.setLayoutY((position - 1) * 131.0);
        scoreLabel.setFont(mainFont);
        scoreLabel.setTextAlignment(TextAlignment.CENTER);
        scoreLabel.setAlignment(Pos.CENTER);
        scrollAnchorPane.getChildren().add(scoreLabel);
    }

}
