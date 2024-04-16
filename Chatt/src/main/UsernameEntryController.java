package main;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.io.IOException;

public class UsernameEntryController {

    @FXML
    private TextField usernameField;

    @FXML
    private Button enterButton;

    @FXML
    void handleEnterPressed(ActionEvent event) throws IOException {
        String username = usernameField.getText().trim();
        if (!username.isEmpty()) {
            // Switch to the chat interface and pass the username
            Main.switchToChat(username);
        }
    }
}