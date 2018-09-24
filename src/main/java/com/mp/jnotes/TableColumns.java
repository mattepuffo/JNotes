package com.mp.jnotes;

import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.ArrayList;

public class TableColumns {

    public static ArrayList setCols(String... arrCols) {
        ArrayList tbc = new ArrayList<>();
        for (int i = 0; i < arrCols.length; i++) {
            TableColumn tc = new TableColumn(arrCols[i]);
            tc.setCellValueFactory(
                    new PropertyValueFactory<Nota, String>(arrCols[i].toLowerCase())
            );
            tbc.add(tc);
        }
        return tbc;
    }
}
