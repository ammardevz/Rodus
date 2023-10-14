package com.github.ammardevz.rodus;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

public class DecryptUI extends Stage {

    List<FileInfo> fileInfoList;

    public DecryptUI(List<FileInfo> fileInfoList) {
        super();
        setTitle("Decrypt Manager");
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("decrypt.fxml"));
            DecryptController decryptController = new DecryptController();
            decryptController.setFileInfoList(fileInfoList);
            loader.setController(decryptController);
            Parent parent = loader.load();
            setScene(new Scene(parent));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}