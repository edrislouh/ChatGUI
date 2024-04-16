package main;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class ChatController {

    @FXML
    private TextField messageField;

    @FXML
    private Button sendButton;

    @FXML
    private TextArea chatArea;

    private String username;

    public void setUsername(String username) {
        this.username = username;
        chatArea.appendText("Welcome, " + username + "!\n"); // Display welcome message with username
    }

    @FXML
    void sendMessage(ActionEvent event) {
        String message = messageField.getText().trim();
        if (!message.isEmpty()) {
            // Append the message to the chat area with the username
            chatArea.appendText(username + ": " + message + "\n");

            // Clear the message field
            messageField.clear();

            // You can handle sending the message to other users or perform any other actions here
        }
    }
}