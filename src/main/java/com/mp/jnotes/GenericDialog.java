package com.mp.jnotes;

import javafx.application.Platform;
import javafx.scene.control.Alert;

public class GenericDialog {

    public static void showDialog(String message, Alert.AlertType at) {
        Platform.runLater(() -> {
            Alert alert = new Alert(at);
            alert.setContentText(message);
            alert.setResizable(true);
            alert.showAndWait();
        });
    }
}
