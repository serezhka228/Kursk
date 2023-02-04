package com.example.mavishop;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;

public class startScreenController {
    @FXML
    private Button btnLog;

    @FXML
    private TextField logLabel;

    @FXML
    private PasswordField passLabel;

    @FXML
    private Button regBtn;

    @FXML
    void initialize() {

        btnLog.setOnAction(event -> {
            String loginText = logLabel.getText().trim();
            String loginPassword = passLabel.getText().trim();

            //Проверка на пустоту полей
            if (!loginText.equals("") && !loginPassword.equals("")) {
                try {
                    loginUser(loginText, loginPassword);
                } catch (SQLException e) {
                    e.printStackTrace();
                } catch (MalformedURLException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        regBtn.setOnAction(event ->{
            Parent wLogin = null;
            try {
                wLogin = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("regScreen.fxml")));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            Stage window = (Stage) regBtn.getScene().getWindow();
            window.setScene(new Scene(wLogin, 960, 540));
            window.show();
        });
    }
    public static String NameFooter = "";
    public void loginUser(String loginText, String loginPassword) throws SQLException, MalformedURLException {
        ConnectDB dbHandler = new ConnectDB();
        ResultSet result = dbHandler.getUser(loginText,loginPassword);

        int counter = 0;
        while (true) {
            try {
                if (result.next()){
                    NameFooter = result.getString(2);
                }else
                    break;
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            counter++;
        }
        if (counter >= 1) {
            btnLog.getScene().getWindow().hide();
            Stage stage = new Stage();
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("menuScreen.fxml"));
            Scene scene = null;
            try {
                scene = new Scene(fxmlLoader.load(), 960, 540);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            stage.setTitle("MaviShop");
            File file = new File("src/main/resources/img/icon.png");
            String urlImage = file.toURI().toURL().toString();
            stage.getIcons().add(new Image(urlImage));
            stage.setScene(scene);
            stage.show();
        }

    }



}