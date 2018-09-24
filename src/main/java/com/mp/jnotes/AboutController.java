package com.mp.jnotes;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.web.HTMLEditor;

import java.io.IOException;
import java.util.Properties;

public class AboutController {

    @FXML
    private HTMLEditor editor;

    @FXML
    public void initialize() {
        hideHTMLEditorToolbars(editor);
        try {
            Properties props = new Properties();
            props.load(getClass().getClassLoader().getResourceAsStream("info.properties"));
            String[] operations = props.getProperty("app.operations").split(";");
            String str = "<p>" + "<strong>Descrizione:</strong> " + props.getProperty("app.description") + "</p>"
                    + "<p>" + "<strong>Versione:</strong> " + props.getProperty("app.version") + "</p>";
            str += "<strong>Operazioni possibili:</strong><br><ul>";
            for (String s : operations) {
                str += "<li>" + s + "</li>";
            }
            str += "</ul>";
            editor.setHtmlText(str);
        } catch (IOException e) {
            GenericDialog.showDialog(e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    private static void hideHTMLEditorToolbars(final HTMLEditor editor) {
        editor.setVisible(false);
        Platform.runLater(() -> {
            Node[] nodes = editor.lookupAll(".tool-bar").toArray(new Node[0]);
            for (Node node : nodes) {
                node.setVisible(false);
                node.setManaged(false);
            }
            editor.setVisible(true);
        });
    }

}
