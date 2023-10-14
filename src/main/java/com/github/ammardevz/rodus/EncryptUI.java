package com.github.ammardevz.rodus;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

public class EncryptUI extends Stage {

    List<FileInfo> fileInfoList;
    public EncryptUI(List<FileInfo> fileInfoList) {
        super();
        setTitle("Encrypt Manager");
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("encrypt.fxml"));
            EncryptController encryptController = new EncryptController();
            encryptController.setFileInfoList(fileInfoList);
            loader.setController(encryptController);
            Parent parent = loader.load();
            setScene(new Scene(parent));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}