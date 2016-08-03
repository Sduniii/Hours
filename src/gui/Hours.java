package gui; /**
 * Created by Tbaios on 14.07.2016.
 */

import enums.SingleInstanceChecker;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Hours extends Application {

    private static final String APPLICATION_ICON = "assets/logo.png";

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {


        if (!SingleInstanceChecker.INSTANCE.isOnlyInstance(Hours::otherInstanceTriedToLaunch, false)) {
            System.exit(0);
        }
        primaryStage.setOnCloseRequest(e ->{
            Platform.exit();
            System.exit(0);
        });
        Parent root = FXMLLoader.load(getClass().getResource("main.fxml"));
        primaryStage.setTitle("Hours");
        primaryStage.getIcons().add(new Image(APPLICATION_ICON));
        primaryStage.setScene(new Scene(root, 800, 600));
        primaryStage.show();
    }

    private static void otherInstanceTriedToLaunch() {
        // Restore your application window and bring it to front.
        // But make sure your situation is apt: This method could be called at *any* time.
        System.err.println("Deiconified because other instance tried to start.");
    }
}
