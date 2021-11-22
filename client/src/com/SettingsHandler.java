package src.com;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.IOException;

public class SettingsHandler {

    public SettingsHandler(){
    }

    @FXML
    Button returnToMenuButton;
    @FXML
    Slider soundSlider;

    // Return to main menu when user clicks return
    public void returnButtonClicked() throws IOException {
        Scene scene = returnToMenuButton.getScene();
        Window window = scene.getWindow();
        Stage stage = (Stage) window;
        Parent mainMenu = FXMLLoader.load(getClass().getResource("MainMenu.fxml"));
        stage.setTitle("Main Menu");
        stage.getScene().setRoot(mainMenu);
        stage.show();
        Client.playClickSound();
    }

    // Save new volume on client and server, and play sound for immediate feedback
    public void soundSliderChanged() {
        Client.changeSoundVolume(soundSlider.getValue());
        Client.printToServer("setSoundEffectsVolume");
        Client.printToServer(String.valueOf(soundSlider.getValue()));
        Client.playClickSound();
    }

}
