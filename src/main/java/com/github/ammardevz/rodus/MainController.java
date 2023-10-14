package com.github.ammardevz.rodus;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class MainController {

    @FXML
    Button addFilesButton, encryptButton, decryptButton;

    @FXML
    TableView<FileInfo> filesTable;

    private List<FileInfo> fileInfoList = new ArrayList<>();

    public void addFiles(ActionEvent event) {
        Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        FileChooser fileChooser = new FileChooser();
        fileChooser.setSelectedExtensionFilter(
                new FileChooser.ExtensionFilter(
                        "All Files", "*.*")
        );

        List<File> files = fileChooser.showOpenMultipleDialog(stage);

        if (files == null) return;


        for (File file : files) {
            FileInfo info = new FileInfo(fileInfoList.size() + 1, file.getName(), file.toPath());
            if (file.isDirectory()) return;
            if (fileInfoList.stream().anyMatch(search -> search.getPath().equals(info.getPath()))) return;
            fileInfoList.add(info);
        }

        updateFilesTable();
    }

    private void updateFilesTable() {
        filesTable.getItems().clear();
        filesTable.getItems().addAll(fileInfoList);

        TableColumn<FileInfo, Integer> indexColumn = new TableColumn<>("Index");
        TableColumn<FileInfo, String> fileNameColumn = new TableColumn<>("File name");
        TableColumn<FileInfo, Path> pathColumn = new TableColumn<>("Path");

        indexColumn.setCellValueFactory(new PropertyValueFactory<>("index"));
        fileNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        pathColumn.setCellValueFactory(new PropertyValueFactory<>("path"));

        for (int i = 0; i < fileInfoList.size(); i++) {
            fileInfoList.get(i).setIndex(i + 1);
        }

        filesTable.getColumns().clear();
        filesTable.getItems().setAll(fileInfoList);
        filesTable.getColumns().addAll(indexColumn, fileNameColumn, pathColumn);
    }

    public void openEncryptWindow(ActionEvent event){
        EncryptUI encryptWindow = new EncryptUI(fileInfoList);
        encryptWindow.show();
    }

    public void openDecryptWindow(ActionEvent event){
        DecryptUI decryptWindow = new DecryptUI(fileInfoList);
        decryptWindow.show();
    }

}
