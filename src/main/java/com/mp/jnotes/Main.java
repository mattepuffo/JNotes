package com.mp.jnotes;

import it.sauronsoftware.junique.AlreadyLockedException;
import it.sauronsoftware.junique.JUnique;
import javafx.application.Application;
import javafx.scene.control.Alert;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        try {
            FXMLUtils.openWindow("/main.fxml", "JNotes", 1000, 700, true, Modality.NONE, true);
        } catch (IOException e) {
            e.printStackTrace();
            GenericDialog.showDialog(e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    public static void main(String[] args) {
        String appId = "mp.film";
        boolean alreadyStarted;
        try {
            JUnique.acquireLock(appId);
            alreadyStarted = false;
        } catch (AlreadyLockedException ex) {
            alreadyStarted = true;
            GenericDialog.showDialog("Programma già avviato!", Alert.AlertType.ERROR);
        }
        if (!alreadyStarted) {
            launch(args);
        }
    }
}