package src.com;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;
import org.json.JSONException;

import java.io.IOException;

import static javafx.application.Application.launch;



public class Run extends Application {
    Stage stage;

    public static void main(String[] args) throws IOException, JSONException {
        launch(args);
        //Client client = new Client ();

        //View view = new View();
        //ByteTest.hello();
    }

    @Override
    public void start(Stage stage) throws Exception {
        // Load login menu when program is first run
        Parent mainMenu = FXMLLoader.load(getClass().getResource("LoginMenu.fxml"));
        this.stage = stage;
        stage.setTitle("Login Menu");
        stage.setScene(new Scene(mainMenu, Screen.getPrimary().getBounds().getWidth(), Screen.getPrimary().getBounds().getHeight()));
        stage.getScene().getStylesheets().add("@../../src/resources/formatting.css");
        stage.setFullScreen(true);
        stage.setFullScreenExitHint("");
        stage.show();

        // Shutdown hook to run when user quits
        Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
            public void run() {
                System.out.println("Yes I have ran shutdown hook");
                try {
                    // Close socket to server if it exists to end connection nicely
                    if (Client.getSocket() != null) {
                        Client.printToServer("quit");
                        Thread.sleep(200);
                        Client.getSocket().close();
                    }
                } catch (IOException | InterruptedException e) {
                    e.printStackTrace();
                }
            }

        }));


    }
}
