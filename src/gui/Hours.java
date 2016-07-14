package gui; /**
 * Created by Tbaios on 14.07.2016.
 */

import data.OneScedule;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class Hours extends Application {

    public static final String APPLICATION_ICON = "assets/logo.png";

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("main.fxml"));
        primaryStage.setTitle("Hours");
        primaryStage.getIcons().add(new Image(APPLICATION_ICON));
        primaryStage.setScene(new Scene(root, 800, 600));
        primaryStage.show();
    }
}
