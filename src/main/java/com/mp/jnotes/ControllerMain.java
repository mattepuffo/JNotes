package com.mp.jnotes;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ControllerMain {

    private Stage stage;
    private DBManager db;
    private String[] tblCaption;
    private DirChooser dc = new DirChooser();

    @FXML
    private BorderPane mainPane;

    @FXML
    private TableView<Nota> tbl;

    @FXML
    private TableView<Nota> tblGruppi;

    @FXML
    private MenuItem miJson;

    @FXML
    private MenuItem miCsv;

    @FXML
    private MenuItem miXls;

    @FXML
    private MenuItem miXml;

    @FXML
    private MenuItem miCsvImp;

    @FXML
    private Label lblCntGruppo;

    @FXML
    public void initialize() {
        tblCaption = new String[]{"ID", "TITOLO", "GRUPPO", "TESTO", "AGGIUNTA", "MODIFICA"};
        db = DBManager.getInstance();
        try {
            db.getConnection();
        } catch (SQLException ex) {
            GenericDialog.showDialog("Errore database: " + ex.getMessage(), Alert.AlertType.WARNING);
        }

        tblGruppi.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        setTblGruppi();

        Platform.runLater(() -> mainPane.requestFocus());
    }

    private void setTblGruppi() {
        try {
            List<Nota> list = new ArrayList<>(db.getGruppi());
            ObservableList<Nota> obl = FXCollections.observableArrayList(list);
            tblGruppi.getColumns().setAll(TableColumns.setCols(new String[]{"GRUPPO"}));
            tblGruppi.setItems(obl);

            tblGruppi.setOnKeyPressed(event -> {
                if (event.getCode() == KeyCode.ENTER) {
                    Nota n = tblGruppi.getSelectionModel().getSelectedItem();
                    getNoteGruppo(n);
                }

                if (event.getCode() == KeyCode.DELETE) {
                    Nota n = tblGruppi.getSelectionModel().getSelectedItem();
                    if (n != null) {
                        try {
                            db.removeGruppo(n);
                            setTblGruppi();
                            getNoteGruppo(n);
                            GenericDialog.showDialog("Gruppo cancellato!", Alert.AlertType.INFORMATION);
                        } catch (SQLException ex) {
                            GenericDialog.showDialog("Errore database: " + ex.getMessage(), Alert.AlertType.WARNING);
                        }
                    }
                }
            });
        } catch (SQLException ex) {
            GenericDialog.showDialog("Errore database: " + ex.getMessage(), Alert.AlertType.WARNING);
        }
    }

    private void getNoteGruppo(Nota n) {
        try {
            List<Nota> list = new ArrayList<>(db.getByGruppo(n.getGruppo()));
            ObservableList<Nota> obl = FXCollections.observableArrayList(list);
            tbl.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
            tbl.getColumns().setAll(TableColumns.setCols(tblCaption));
            tbl.setItems(obl);
            lblCntGruppo.setText("No. articoli: " + obl.size());
        } catch (SQLException ex) {
            GenericDialog.showDialog("Errore database: " + ex.getMessage(), Alert.AlertType.WARNING);
        }
    }

    private Stage getStage() {
        stage = (Stage) mainPane.getScene().getWindow();
        return stage;
    }

    @FXML
    private void onTblDoubleClick(MouseEvent me) {
        if (me.getClickCount() == 2) {
            try {
                Nota n = tbl.getSelectionModel().getSelectedItem();
                if (n != null) {
                    FXMLLoader loader = FXMLUtils.openWindowLoader("/nota.fxml", "", 500, 500, false, Modality.APPLICATION_MODAL, false);
                    ControllerNota cb = loader.getController();
                    cb.initController(n);
                }
            } catch (IOException e) {
                GenericDialog.showDialog(e.getMessage(), Alert.AlertType.ERROR);
            }
        }
    }

    @FXML
    private void onTblGruppiClick(MouseEvent me) {
        Nota n = tblGruppi.getSelectionModel().getSelectedItem();
        if (n != null) {
            getNoteGruppo(n);
        }
    }

    @FXML
    private void refresh() {
        tbl.getItems().clear();
        setTblGruppi();
    }

    @FXML
    private void aggiungiNota() {
        try {
            FXMLLoader loader = FXMLUtils.openWindowLoader("/nota.fxml", "", 500, 500, false, Modality.APPLICATION_MODAL, false);
            ControllerNota cb = loader.getController();
            cb.initController();
        } catch (IOException e) {
            GenericDialog.showDialog(e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void deleteNota(KeyEvent ev) {
        if (ev.getCode() == KeyCode.DELETE) {
            Nota n = tbl.getSelectionModel().getSelectedItem();
            if (n != null) {
                try {
                    db.removeNota(n);
                    setTblGruppi();
                    getNoteGruppo(n);
                    GenericDialog.showDialog("Nota cancellata!", Alert.AlertType.INFORMATION);
                } catch (SQLException ex) {
                    GenericDialog.showDialog("Errore database: " + ex.getMessage(), Alert.AlertType.WARNING);
                }
            }
        }
    }

    @FXML
    private void quit() {
        Platform.exit();
    }

    @FXML
    private void about() {
        try {
            FXMLUtils.openWindow("/about.fxml", "About", 600, 300, true, Modality.APPLICATION_MODAL, false);
        } catch (IOException e) {
            GenericDialog.showDialog(e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void importa() {
        try {
            DirChooser dc = new DirChooser();
            Optional<File> f = dc.chooseFile(getStage().getOwner(), "Scegli!", new FileChooser.ExtensionFilter("CSV (*.csv)", "*.csv"));
            if (f.isPresent()) {
                CsvDb cd = new CsvDb();
                List<Nota> list = cd.read(f.get().getPath());
                for (Nota n : list) {
                    try {
                        db.addNota(n);
                    } catch (SQLException ex) {
                    }
                }
                GenericDialog.showDialog("Database importato!", Alert.AlertType.INFORMATION);
            }
        } catch (IOException ex) {
            GenericDialog.showDialog(ex.getMessage(), Alert.AlertType.WARNING);
        }
    }

    // ESPORTAZIONE
    @FXML
    private void esporta(ActionEvent ev) {
        Object obj = ev.getSource();
        List<Nota> list = null;
        try {
            list = db.getAll();
        } catch (SQLException ex) {
            GenericDialog.showDialog("Errore database: " + ex.getMessage(), Alert.AlertType.WARNING);
        }

        if (list != null) {
            if (obj == miJson) {
                eseguiEsporta(list, tblCaption, "Esporta JSON", new FileChooser.ExtensionFilter("JSON (*.json)", "*.json"), this::esportaNoteInJson);
            } else if (obj == miXls) {
                eseguiEsporta(list, tblCaption, "Esporta XLS", new FileChooser.ExtensionFilter("XLS (*.xls)", "*.xls"), this::esportaNoteInXls);
            } else if (obj == miCsv) {
                eseguiEsporta(list, tblCaption, "Esporta CSV", new FileChooser.ExtensionFilter("CSV (*.csv)", "*.csv"), this::esportaNoteInCsv);
            } else if (obj == miXml) {
                eseguiEsporta(list, tblCaption, "Esporta XML", new FileChooser.ExtensionFilter("XML (*.XML)", "*.xml"), this::esportaNoteInXml);
            }
        }
    }

    public void eseguiEsporta(List<Nota> list, String[] header, String titolo, FileChooser.ExtensionFilter filter, DoExport doExport) {
        Optional<File> f = dc.saveFile(getStage().getOwner(), titolo, filter);
        if (f.isPresent()) {
            try {
                doExport.export(f.get(), list, header);
                GenericDialog.showDialog("Database esportato!", Alert.AlertType.INFORMATION);
            } catch (Exception ex) {
                GenericDialog.showDialog(ex.getMessage(), Alert.AlertType.WARNING);
            }
        }
    }

    private void esportaNoteInJson(File file, List<Nota> list, String[] header) throws Exception {
        JsonDb ex = new JsonDb();
        ex.create(file.getAbsolutePath(), list);
    }

    private void esportaNoteInXml(File file, List<Nota> list, String[] header) throws Exception {
        XmlDb ex = new XmlDb();
        ex.create(file.getAbsolutePath(), list);
    }

    private void esportaNoteInCsv(File file, List<Nota> list, String[] header) throws Exception {
        CsvDb ex = new CsvDb();
        ex.create(file.getAbsolutePath(), header, list);
    }

    private void esportaNoteInXls(File file, List<Nota> list, String[] header) throws Exception {
        ExcelDb ex = new ExcelDb();
        ex.create(file.getAbsolutePath(), header, list);
    }

}
