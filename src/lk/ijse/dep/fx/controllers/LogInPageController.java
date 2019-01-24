package lk.ijse.dep.fx.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import lk.ijse.dep.fx.util.ManageUser;

import java.io.IOException;

public class LogInPageController {

    @FXML
    private TextField txtUserName;

    @FXML
    private TextField txtPassword;

    @FXML
    private Button btnLogin;

    @FXML
    void clickOnLoginbtn(ActionEvent event) throws IOException {
        String userName = txtUserName.getText();
        String password = txtPassword.getText();

        if (userName.trim().isEmpty() || password.trim().isEmpty()) {
            new Alert(Alert.AlertType.INFORMATION, "Fields Can not be Empty", ButtonType.OK).showAndWait();
            return;
        }

        if(ManageUser.validateUser(userName,password)){
            Parent root = FXMLLoader.load(this.getClass().getResource("/lk/ijse/dep/fx/views/MainPage.fxml"));
            Scene newScene = new Scene(root);
            Stage primeryStage = (Stage) btnLogin.getScene().getWindow();
            primeryStage.setScene(newScene);
            primeryStage.setTitle("MainPage");
            primeryStage.getIcons().add(new Image("/lk/ijse/dep/fx/img/POS LOGO.png"));
        }else{
            new Alert(Alert.AlertType.INFORMATION, "UserName and Password Combination is Wrong \nTry Again", ButtonType.OK).showAndWait();
            txtPassword.requestFocus();
            txtPassword.selectAll();
        }


    }

    public void clickEnterOnPassword(ActionEvent actionEvent) throws IOException {
        clickOnLoginbtn(null);
    }

    public void enterOnUserNameTxt(ActionEvent actionEvent) {
        txtPassword.requestFocus();
    }
}
