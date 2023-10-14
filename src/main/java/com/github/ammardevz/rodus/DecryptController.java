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

import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class DecryptController implements Initializable {

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
    private Button decryptButton;

    List<FileInfo> fileInfoList = new ArrayList<>();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Platform.runLater(() -> {
            restrictToSingleNumber(pin1);
            restrictToSingleNumber(pin2);
            restrictToSingleNumber(pin3);
            restrictToSingleNumber(pin4);
            decryptButton.setOnAction(this::decryptFiles);
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

    public void decryptFiles(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        for (FileInfo fileInfo : fileInfoList) {
            try {
                Path filePath = fileInfo.getPath();
                if (!Files.exists(filePath)) {
                    displayErrorMessage("File not found: " + filePath);
                    continue;
                }

                String encryptedData = Files.readString(filePath);
                String pin = pin1.getText() + pin2.getText() + pin3.getText() + pin4.getText();
                String decryptedData = Cryptography.decrypt(passwordField.getText(), pin, encryptedData);
                Files.writeString(filePath, decryptedData);
                stage.close();
                displayInformationMessage("Decryption successful!");
            } catch (IOException e) {
                displayErrorMessage("Error reading or writing file: " + e.getMessage());
                e.printStackTrace();
            } catch (Exception e) {
                displayErrorMessage("Error during decryption: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }

    private void displayErrorMessage(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void displayInformationMessage(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

}