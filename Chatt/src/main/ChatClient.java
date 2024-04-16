package main;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ChatClient extends Application {
    private BufferedReader in;
    private PrintWriter out;
    private TextArea messageArea;
    private TextField textField;
    private TextField usernameField;
    private Button sendButton;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Chat Client");

        textField = new TextField();
        usernameField = new TextField();
        sendButton = new Button("Send");
        sendButton.setOnAction(e -> sendMessage());

        VBox bottomBox = new VBox();
        bottomBox.getChildren().addAll(usernameField, textField, sendButton);
        bottomBox.setSpacing(10);
        bottomBox.setPadding(new Insets(10));

        messageArea = new TextArea();
        messageArea.setEditable(false);

        BorderPane root = new BorderPane();
        root.setCenter(messageArea);
        root.setBottom(bottomBox);

        primaryStage.setScene(new Scene(root, 400, 300));
        primaryStage.show();

        connectToServer();
    }

    private void connectToServer() {
        try {
            Socket socket = new Socket("localhost", 2332);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);

            sendButton.setOnAction(e -> {
                String username = usernameField.getText().trim();
                String message = textField.getText().trim();
                if (!username.isEmpty() && !message.isEmpty()) {
                    out.println(username + ": " + message);
                    textField.clear();
                } else {
                    System.out.println("Please enter both username and message.");
                }
            });

            Thread readerThread = new Thread(() -> {
                try {
                    while (true) {
                        String message = in.readLine();
                        if (message == null) {
                            break;
                        }
                        Platform.runLater(() -> {
                            messageArea.appendText(message + "\n");
                        });
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            readerThread.start();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void sendMessage() {
        String username = usernameField.getText().trim();
        String message = textField.getText();
        if (!username.isEmpty() && !message.isEmpty()) {
            out.println(username + ": " + message);
            textField.clear();
        } else {
            System.out.println("Please enter both username and message.");
        }
    }
}
