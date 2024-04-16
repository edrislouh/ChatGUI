module Chatt {
    requires javafx.controls;
    requires javafx.fxml;

    opens main to javafx.fxml;

    exports main;
}
