package com_bptn_quiz_app.controllers;

import com.jfoenix.controls.JFXButton;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import com_bptn_quiz_app.database.DatabaseHandler;
import com_bptn_quiz_app.interfaces.UserAuthentication;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class LoginController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField enterEmailField;

    @FXML
    private TextField enterPasswordField;

    @FXML
    private JFXButton homeButton;

    @FXML
    private JFXButton loginButton;

    @FXML
    private Label enterValidEmail;

    @FXML
    private Label incorrectEmailOrPassword;

    @FXML
    private Label emptyFieldsError;

    @FXML
    private ImageView emailPic;

    @FXML
    private ImageView passwordPics;

    @FXML
    void
    initialize() {


        enterValidEmail.setVisible(false);
        emptyFieldsError.setVisible(false);
        incorrectEmailOrPassword.setVisible(false);



        if (homeButton != null) {
            HomeController homeController = new HomeController();
            homeController.SetScreen(homeButton, "/com_bptn_quiz_app/home.fxml", "Quiz App");
        }

        loginButton.setOnAction(event -> {

            String email = enterEmailField.getText().trim();
            String password = enterPasswordField.getText().trim();

            if (!email.contains("@")){
                enterValidEmail.setVisible(true);
                return;
            }


            if (email.isEmpty() || password.isEmpty()) {
               emptyFieldsError.setVisible(true);
                return;
            }




            UserAuthentication userAuthentication = null;
            try {
                userAuthentication = new DatabaseHandler();
            } catch (SQLException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }

            ResultSet userRow = userAuthentication.getUser(email, password);
            int counter = 0;
            while (true){
                try {
                    if (!userRow.next()) break;
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                counter++;
            }
            if (counter != 1) {
                // TODO send an error message to the user
                incorrectEmailOrPassword.setVisible(true);
                return;
            }

            loginButton.getScene().getWindow();
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("/com_bptn_quiz_app/quiz_screen.fxml"));

            try {
                fxmlLoader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }

            Parent root = fxmlLoader.getRoot();
            Stage stage = new Stage();
            stage.setTitle("Quiz App");
            stage.setScene(new Scene(root));
            stage.show();
        });

    }
}
