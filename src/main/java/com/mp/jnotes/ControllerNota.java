package com.mp.jnotes;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import org.apache.commons.lang3.StringUtils;

import java.sql.SQLException;
import java.util.Date;

public class ControllerNota {

    private DBManager db;
    private Stage stage;
    private Nota nota;

    @FXML
    private TextField txtTitolo;

    @FXML
    private TextField txtGruppo;

    @FXML
    private TextArea he;

    @FXML
    private BorderPane pane;

    @FXML
    private Button btn;

    @FXML
    public void initialize() {
        db = DBManager.getInstance();

        Platform.runLater(() -> {
            pane.requestFocus();
        });
    }

    private Stage getStage() {
        stage = (Stage) pane.getScene().getWindow();
        return stage;
    }

    public void initController() {
        this.db = db;
        getStage().setTitle("Aggiungi nota");
        btn.setText("Aggiungi");
        btn.setOnAction(e -> addNota());
    }

    public void initController(Nota nota) {
        this.db = db;
        this.nota = nota;
        getStage().setTitle(nota.getTitolo());
        txtTitolo.setText(nota.getTitolo());
        txtGruppo.setText(nota.getGruppo());
        he.setText(nota.getTesto());
        btn.setText("Modifica nota");
        btn.setOnAction(e -> upNota());
    }

    private void addNota() {
        if (StringUtils.isNotBlank(txtTitolo.getText()) && StringUtils.isNotBlank(txtGruppo.getText()) && StringUtils.isNotBlank(he.getText())) {
            try {
                Nota n = new Nota();
                n.setId(txtTitolo.getText().hashCode());
                n.setTitolo(txtTitolo.getText().trim().toLowerCase());
                n.setGruppo(txtGruppo.getText().trim().toLowerCase());
                n.setTesto(he.getText().trim().toLowerCase());
                n.setAggiunta(new Date().toString());
                n.setModifica(new Date().toString());
                db.addNota(n);
                GenericDialog.showDialog("Nota salvata con successo!", Alert.AlertType.INFORMATION);
            } catch (SQLException ex) {
                GenericDialog.showDialog("Errore database: " + ex.getMessage(), Alert.AlertType.WARNING);
            }
        } else {
            GenericDialog.showDialog("Tutti i campi obbligatori!", Alert.AlertType.WARNING);
        }
    }

    private void upNota() {
        if (StringUtils.isNotBlank(txtTitolo.getText()) && StringUtils.isNotBlank(txtGruppo.getText()) && StringUtils.isNotBlank(he.getText())) {
            try {
                Nota n = new Nota();
                n.setId(this.nota.getId());
                n.setTitolo(txtTitolo.getText().trim().toLowerCase());
                n.setGruppo(txtGruppo.getText().trim().toLowerCase());
                n.setTesto(he.getText().trim());
                n.setModifica(new Date().toString());
                db.upNota(n);
                GenericDialog.showDialog("Nota modificata con successo!", Alert.AlertType.INFORMATION);
            } catch (SQLException ex) {
                GenericDialog.showDialog("Errore database: " + ex.getMessage(), Alert.AlertType.WARNING);
            }
        } else {
            GenericDialog.showDialog("Tutti i campi obbligatori!", Alert.AlertType.WARNING);
        }
    }
}
