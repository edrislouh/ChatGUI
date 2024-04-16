package main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public class Main extends Application {

    private static Stage primaryStage;

    @Override
    public void start(Stage primaryStage) throws Exception {
        Main.primaryStage = primaryStage;
        switchToUsernameEntry();
    }

    public static void switchToUsernameEntry() throws IOException {
        switchScene("username_entry.fxml", "Username Entry");
    }

    public static void switchToChat(String username) throws IOException {
        FXMLLoader loader = new FXMLLoader(Main.class.getResource("chat.fxml"));
        Parent root = loader.load();
        ChatController controller = loader.getController();
        controller.setUsername(username);

        primaryStage.setScene(new Scene(root));
        primaryStage.setTitle("Chat");
        primaryStage.show();
    }

    private static void switchScene(String fxmlFile, String title) throws IOException {
        Parent root = FXMLLoader.load(Main.class.getResource(fxmlFile));
        primaryStage.setScene(new Scene(root));
        primaryStage.setTitle(title);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}