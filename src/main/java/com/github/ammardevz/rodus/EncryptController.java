package com.github.ammardevz.rodus;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.Reader;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class EncryptController implements Initializable {

    @FXML
    private PasswordField passwordField;

    @FXML
    private TextField pin1;

    @FXML
    private TextField pin2;

    @FXML
    private TextField pin3;

    @FXML
    private TextField pin4;

    @FXML
    private Button encryptButton;


    List<FileInfo> fileInfoList = new ArrayList<>();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Platform.runLater(() -> {
            restrictToSingleNumber(pin1);
            restrictToSingleNumber(pin2);
            restrictToSingleNumber(pin3);
            restrictToSingleNumber(pin4);
            encryptButton.setOnAction(this::encryptFiles);
            System.out.println(fileInfoList.toString());
        });
    }


    private void restrictToSingleNumber(TextField textField) {
        textField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                textField.setText(newValue.replaceAll("[^\\d]", ""));
            } else if (newValue.length() > 1) {
                textField.setText(newValue.substring(0, 1));
            }
        });
    }

    public void setFileInfoList(List<FileInfo> fileInfoList) {
        this.fileInfoList = fileInfoList;
    }

    public void encryptFiles(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        for (FileInfo fileInfo : fileInfoList) {
            try {
                File file = Path.of(fileInfo.getPath().toString()).toFile();
                if (!file.exists()) return;

                String rawData = String.join(System.lineSeparator(), Files.readAllLines(file.toPath()));
                String pin = pin1.getText() + pin2.getText() + pin3.getText() + pin4.getText();
                String encryptedData = Cryptography.encrypt(passwordField.getText(), pin, rawData);
                Files.writeString(file.toPath(), encryptedData);
                stage.close();
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "Done!");
                alert.show();
            }catch (Exception e) {
                Alert alert = new Alert(Alert.AlertType.ERROR, e.getMessage());
                alert.show();
                e.printStackTrace();
            }
        }

    }


}