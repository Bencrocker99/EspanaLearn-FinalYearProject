package src.com;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.Window;
import org.json.JSONException;

import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import static java.lang.Character.isDigit;
import static java.lang.Character.isLetter;

public class MainMenuHandler {

    Stage stage;
    Client client;

    @FXML
    Button playButton;
    @FXML
    Label currentQuestion;
    @FXML
    Button leaderboardsButton;
    @FXML
    Button practiceButton;
    @FXML
    Button achievementsButton;
    @FXML
    Button settingsButton;
    @FXML
    Button quitButton;
    @FXML
    Button beginEndlessButton;
    @FXML
    Button questionButton;
    @FXML
    Button vocabularyAndGrammarButton;
    @FXML
    Button playMenuReturnButton;
    @FXML
    Button loginButton;
    @FXML
    Button registerButton;
    @FXML
    javafx.scene.control.TextField usernameTextfield;
    @FXML
    javafx.scene.control.PasswordField passwordTextfield;
    @FXML
    Label errorLabel;
    @FXML
    Button createAccountButton;
    @FXML
    javafx.scene.control.TextField registerUsernameTextfield;
    @FXML
    javafx.scene.control.PasswordField registerPasswordTextfield;
    @FXML
    javafx.scene.control.PasswordField registerConfirmPasswordTextfield;
    @FXML
    Label registerErrorLabel;
    @FXML
    AnchorPane newAccountPane;
    @FXML
    Button continueButton;
    @FXML
    Label newAccountLabel;
    @FXML
    Button backButton;
    @FXML
    AnchorPane anchorPane;


    // Load play menu screen when user clicks play
    public void playButtonClick() throws IOException {
        Client.playClickSound();
        Scene scene = playButton.getScene();
        Window window = scene.getWindow();
        Stage stage = (Stage) window;
        Parent playMenu = FXMLLoader.load(getClass().getResource("PlayMenu.fxml"));
        stage.setTitle("Play Menu");
        stage.getScene().setRoot(playMenu);
        stage.show();
    }

    // Load main menu screen if user clicks return within play menu
    public void beginPlayMenuReturnButtonClick() throws IOException {
        Client.playClickSound();
        Scene scene = playMenuReturnButton.getScene();
        Window window = scene.getWindow();
        Stage stage = (Stage) window;
        Parent mainMenu = FXMLLoader.load(getClass().getResource("MainMenu.fxml"));
        stage.setTitle("Main Menu");
        stage.getScene().setRoot(mainMenu);
        stage.show();
    }

    // Load leaderboards screen when user clicks leaderboards
    public void leaderboardsButtonClick() throws IOException {
        Client.playClickSound();
        Scene scene = leaderboardsButton.getScene();
        Window window = scene.getWindow();
        Stage stage = (Stage) window;
        Parent leaderboardsMenu = FXMLLoader.load(getClass().getResource("LeaderboardsMenu.fxml"));
        stage.setTitle("Leaderboards Menu");
        stage.getScene().setRoot(leaderboardsMenu);
        stage.show();
    }

    // Load vocabulary and grammar screen when user clicks vocabulary and grammar
    public void vocabularyAndGrammarButtonClick() throws IOException {
        Client.playClickSound();
        Scene scene = vocabularyAndGrammarButton.getScene();
        Window window = scene.getWindow();
        Stage stage = (Stage) window;
        Parent vocabularyAndGrammarMode = FXMLLoader.load(getClass().getResource("VocabularyAndGrammarMode.fxml"));
        stage.setTitle("Vocabulary and Grammar Mode");
        stage.getScene().setRoot(vocabularyAndGrammarMode);
        stage.show();
    }

    // Loads achievements screen when user clicks achievements
    public void achievementsButtonClick() throws IOException {
        Client.playClickSound();
        Scene scene = achievementsButton.getScene();
        Window window = scene.getWindow();
        Stage stage = (Stage) window;
        Parent achievementsMenu = FXMLLoader.load(getClass().getResource("AchievementsMenu.fxml"));
        stage.setTitle("Achievements Menu");
        stage.getScene().setRoot(achievementsMenu);
        stage.show();
    }

    // Loads settings screen when user clicks settings
    public void settingsButtonClick() throws IOException {
        Client.playClickSound();
        Scene scene = settingsButton.getScene();
        Window window = scene.getWindow();
        Stage stage = (Stage) window;
        Parent settingsMenu = FXMLLoader.load(getClass().getResource("SettingsMenu.fxml"));
        stage.setTitle("Settings Menu");
        stage.getScene().setRoot(settingsMenu);
        stage.show();
    }

    // Safely quit the application when user clicks quit
    public void quitButtonClick() {
        System.exit(0);
    }

    // Loads practice mode screen when user clicks practice
    public void practiceButtonClick() throws IOException {
        Client.playClickSound();
        Scene scene = practiceButton.getScene();
        Window window = scene.getWindow();
        Stage stage = (Stage) window;
        Parent practiceMode = FXMLLoader.load(getClass().getResource("PracticeMode.fxml"));
        stage.setTitle("Practice Mode");
        stage.getScene().setRoot(practiceMode);
        stage.show();
    }

    // Loads endless mode screen when user clicks endless mode
    public void beginEndlessButtonClick() throws IOException {
        Client.playClickSound();
        Scene scene = beginEndlessButton.getScene();
        Window window = scene.getWindow();
        Stage stage = (Stage) window;
        Parent endlessMode = FXMLLoader.load(getClass().getResource("EndlessMode.fxml"));
        stage.setTitle("Endless Mode");
        stage.getScene().setRoot(endlessMode);
        stage.show();
    }

    // When user clicks login
    public void loginButtonClicked() throws IOException, AWTException, InterruptedException, JSONException {
        String username = usernameTextfield.getText();
        String password = passwordTextfield.getText();

        // Start connection to server
        Socket socket = new Socket("localhost", 6666);

        PrintWriter printWriter = new PrintWriter(socket.getOutputStream(), true);
        printWriter.println("login");
        printWriter.println(username);
        printWriter.println(password);

        InputStreamReader inputStreamReader = new InputStreamReader(socket.getInputStream());
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
        // See if the username and password given is a match for a User on the server
        String match = bufferedReader.readLine();
        if (match.equals("true")) {
            // If they match, load main menu screen and create new client class for user
            Scene scene = usernameTextfield.getScene();
            Window window = scene.getWindow();
            Stage stage = (Stage) window;
            Parent mainMenu = FXMLLoader.load(getClass().getResource("MainMenu.fxml"));
            stage.setTitle("Main Menu");
            stage.getScene().setRoot(mainMenu);
            stage.show();
            Client client = new Client(socket, bufferedReader, printWriter);
        } else {
            // If they don't match, give user an error message and close the current connection
            errorLabel.setText("The username or password given is incorrect.");
            errorLabel.setVisible(true);
            socket.close();
        }
    }

    // When user clicks create account
    public void createAccountButtonClicked() throws IOException {
        String username = registerUsernameTextfield.getText();
        String password = registerPasswordTextfield.getText();

        // Ensure username is more than 6 characters, and password matches restrictions, or else give user the correct error message
        if (username.length() < 6) {
            registerErrorLabel.setText("Usernames must be longer than 5 characters.");
            registerErrorLabel.setVisible(true);
            return;
        }

        if (password.length() < 9) {
            registerErrorLabel.setText("Passwords must be longer than 8 characters.");
            registerErrorLabel.setVisible(true);
            return;
        }
        boolean containsLetters = false;
        boolean containsNumbers = false;
        for (int i = 0; i < password.length(); i++) {
            char currentCharacter = password.charAt(i);
            if (isDigit(currentCharacter)) {
                containsNumbers = true;
            }
            if (isLetter(currentCharacter)) {
                containsLetters = true;
            }
        }
        if (!(containsNumbers && containsLetters)) {
            registerErrorLabel.setText("Passwords must contain both letters and numbers.");
            registerErrorLabel.setVisible(true);
            return;
        }
        if (password.equals(password.toLowerCase()) || password.equals(password.toUpperCase())) {
            registerErrorLabel.setText("Passwords must contain both uppercase and lowercase letters.");
            registerErrorLabel.setVisible(true);
            return;
        }
        if (!(password.equals(registerConfirmPasswordTextfield.getText()))) {
            registerErrorLabel.setText("Passwords don't match.");
            registerErrorLabel.setVisible(true);
            return;
        }

        // Set up connection to server
        Socket socket = new Socket("localhost", 6666);

        PrintWriter printWriter = new PrintWriter(socket.getOutputStream(), true);
        printWriter.println("register");
        printWriter.println(username);
        printWriter.println(password);

        InputStreamReader inputStreamReader = new InputStreamReader(socket.getInputStream());
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
        // Check whether username has already been used for a User on server, if so, give an error message, if not,
        // tell user account has been created successfully
        String accountCreated;
        accountCreated = bufferedReader.readLine();
        if (accountCreated.equals("true")) {
            newAccountPane.setVisible(true);
            newAccountLabel.setText("The account " + username + " has been successfully created.");
        } else {
            newAccountPane.setVisible(true);
            newAccountLabel.setText("An account with the username " + username + " already exists.");
        }
        socket.close();
    }

    // Load login menu screen when user clicks back in register menu
    public void backButtonClicked() throws IOException {
        Scene scene = continueButton.getScene();
        Window window = scene.getWindow();
        Stage stage = (Stage) window;
        Parent mainMenu = FXMLLoader.load(getClass().getResource("LoginMenu.fxml"));
        stage.setTitle("Login Menu");
        stage.getScene().setRoot(mainMenu);
        stage.show();
    }

    // Load login menu screen when user clicks continue
    public void continueButtonClicked() throws IOException {
        Scene scene = continueButton.getScene();
        Window window = scene.getWindow();
        Stage stage = (Stage) window;
        Parent mainMenu = FXMLLoader.load(getClass().getResource("LoginMenu.fxml"));
        stage.setTitle("Login Menu");
        stage.getScene().setRoot(mainMenu);
        stage.show();
    }

    // Load register menu screen when user clicks register
    public void registerButtonClicked() throws IOException {
        Scene scene = usernameTextfield.getScene();
        Window window = scene.getWindow();
        Stage stage = (Stage) window;
        Parent mainMenu = FXMLLoader.load(getClass().getResource("RegisterMenu.fxml"));
        stage.setTitle("Register Menu");
        stage.getScene().setRoot(mainMenu);
        stage.show();
    }

}
