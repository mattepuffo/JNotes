package com.mp.jnotes;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class FXMLUtils {
    public static void openWindow(String fileFxml, String title, int width, int height, boolean resiz, Modality modality, boolean maxim) throws IOException {
        Parent root = FXMLLoader.load(FXMLUtils.class.getResource(fileFxml));
        Stage stage = new Stage();
        stage.getIcons().add(new Image(FXMLUtils.class.getClassLoader().getResourceAsStream("application.png")));
        stage.setTitle(title);
        stage.setScene(new Scene(root, width, height));
        stage.setResizable(resiz);
        stage.setMaximized(maxim);
        stage.initModality(modality);
        stage.show();
    }

    public static FXMLLoader openWindowLoader(String fileFxml, String title, int width, int height, boolean resiz, Modality modality, boolean maxim) throws IOException {
        FXMLLoader loader = new FXMLLoader(FXMLUtils.class.getResource(fileFxml));
        Parent root = loader.load();
        Stage stage = new Stage();
        stage.getIcons().add(new Image(FXMLUtils.class.getClassLoader().getResourceAsStream("application.png")));
        stage.setTitle(title);
        stage.setScene(new Scene(root, width, height));
        stage.setResizable(resiz);
        stage.setMaximized(maxim);
        stage.initModality(modality);
        stage.show();
        return loader;
    }
}
