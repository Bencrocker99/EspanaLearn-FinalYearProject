package src.com;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;



public class View extends Application{

    Button button;


    @Override
    public void start(Stage primaryStage) throws Exception{
        primaryStage.setTitle("Main Menu");

        button = new Button("");
        button.setText("Play");

        StackPane layout = new StackPane();
        layout.getChildren().add(button);

        Scene scene = new Scene(layout, 300, 250);
        primaryStage.setScene(scene);
        primaryStage.show();

    }

}
