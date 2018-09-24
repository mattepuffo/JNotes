package com.mp.jnotes;

import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Window;

import java.io.File;
import java.util.Optional;

public class DirChooser {

    private File lastDir;

    public DirChooser() {
        lastDir = new File(System.getProperty("user.home"));
    }

    public Optional<File> chooseDirectory(Window ownerWindow, String title) {
        DirectoryChooser chooser = new DirectoryChooser();
        chooser.setTitle(title);
        chooser.setInitialDirectory(lastDir);
        File dir = chooser.showDialog(ownerWindow);
        if (dir != null) {
            lastDir = dir;
            return Optional.of(dir);
        } else {
            return Optional.empty();
        }
    }

    public Optional<File> chooseFile(Window ownerWindow, String title, FileChooser.ExtensionFilter... ext) {
        FileChooser chooser = new FileChooser();
        chooser.setTitle(title);
        chooser.setInitialDirectory(lastDir);
        chooser.getExtensionFilters().addAll(ext);
        File file = chooser.showOpenDialog(ownerWindow);
        return Optional.ofNullable(file);
    }

    public Optional<File> saveFile(Window ownerWindow, String title, FileChooser.ExtensionFilter... ext) {
        FileChooser chooser = new FileChooser();
        chooser.setTitle(title);
        chooser.setInitialDirectory(lastDir);
        chooser.getExtensionFilters().addAll(ext);
        File file = chooser.showSaveDialog(ownerWindow);
        return Optional.ofNullable(file);
    }

}