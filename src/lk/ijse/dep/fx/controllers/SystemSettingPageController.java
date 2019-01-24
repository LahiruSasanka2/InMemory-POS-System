package lk.ijse.dep.fx.controllers;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import lk.ijse.dep.fx.util.ManageUser;
import lk.ijse.dep.fx.util.User;

import java.io.IOException;
import java.util.ArrayList;

public class SystemSettingPageController {

    public static User deleteUser; // Set Selection User to Delete

    @FXML
    public Label lblShowUserDetails;

    @FXML
    public Label lblCreateNewUser;

    @FXML
    public Pane panalCreateNewUser;

    @FXML
    private TextField txtOldPassword;

    @FXML
    private TextField txtNewPassword;

    @FXML
    private TextField txtConfirmPassword;

    @FXML
    private RadioButton radioAdmin;

    @FXML
    private RadioButton RadioUser;

    @FXML
    private TextField txtCreateUserName;

    @FXML
    private TextField txtCreatePassword;

    @FXML
    private Button btnCreateNewUser;

    @FXML
    private TableView<User> tblUsers;

    public static TableView<User> copyOftblUser;

    private ToggleGroup toggleGroup = new ToggleGroup();


    public void initialize() {

        copyOftblUser=tblUsers;

        lblShowUserDetails.setText("Welcome " + ManageUser.loginUser.getUserName() + " (" + ManageUser.loginUser.getUserType() + ")"); // update Label


        RadioUser.setToggleGroup(toggleGroup);
        radioAdmin.setToggleGroup(toggleGroup);
        radioAdmin.setSelected(true);


        if (ManageUser.loginUser.getUserType().equals("User")) {  // hide Admin Feature from User
            tblUsers.setVisible(false);
            btnCreateNewUser.setVisible(false);
            txtCreateUserName.setVisible(false);
            txtCreatePassword.setVisible(false);
            radioAdmin.setVisible(false);
            RadioUser.setVisible(false);
            lblCreateNewUser.setVisible(false);
            panalCreateNewUser.setVisible(false);
        }

        if (ManageUser.loginUser.getUserType().equals("Admin")) {
            RadioUser.setVisible(false);
            radioAdmin.setVisible(false);
        }

        tblUsers.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("userName"));
        tblUsers.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("userType"));
        tblUsers.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("button"));

        if(ManageUser.loginUser.getUserType().equals("System")){
            refreshTableSystemUser();
            tblUsers.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<User>() {
                @Override
                public void changed(ObservableValue<? extends User> observable, User oldValue, User newValue) {
                    if(newValue==null){return;}
                    txtCreateUserName.setText(newValue.getUserName());
                    txtCreatePassword.setText(newValue.getPassword());
                }
            });
        }
        if(ManageUser.loginUser.getUserType().equals("Admin")){
            refreshTableAdminUser();
        }

    }

    @FXML
    void clickChangeButton(ActionEvent event) {

        String oldPassword = txtOldPassword.getText();
        String newPassword = txtNewPassword.getText();
        String confirmPassword = txtConfirmPassword.getText();

        if (oldPassword.trim().isEmpty() || newPassword.trim().isEmpty() || confirmPassword.trim().isEmpty()) {
            new Alert(Alert.AlertType.INFORMATION, "Fields Can not Be Empty", ButtonType.OK).showAndWait();
            return;
        }
        if (!ManageUser.loginUser.getPassword().equals(oldPassword)) {   // old PassWord is not Correct
            new Alert(Alert.AlertType.INFORMATION, "Old Password is incorrect. \nTry again...", ButtonType.OK).showAndWait();
            txtOldPassword.requestFocus();
            txtOldPassword.selectAll();
            return;
        }
        if (!newPassword.equals(confirmPassword)) {
            new Alert(Alert.AlertType.INFORMATION, "Confirm New PassWord is Wrong", ButtonType.OK).showAndWait();
            txtConfirmPassword.requestFocus();
            return;
        }

        ManageUser.loginUser.setPassword(newPassword);
        new Alert(Alert.AlertType.INFORMATION, "Password Successfully Changed..", ButtonType.OK).showAndWait();

        txtOldPassword.setText("");
        txtNewPassword.setText("");
        txtConfirmPassword.setText("");

    }

    @FXML
    void clickCreateNewUser(ActionEvent event) {
        String userName = txtCreateUserName.getText();
        String password = txtCreatePassword.getText();

        if (userName.trim().isEmpty() || password.trim().isEmpty()) {
            new Alert(Alert.AlertType.INFORMATION, "Fields can not be empty...", ButtonType.OK).showAndWait();
            return;
        }

        if (ManageUser.loginUser.getUserType().equals("System")) {   // Only System User Can create Admin Users
            if (radioAdmin.isSelected()) {
                if (ManageUser.createNewUser("Admin", userName, password)) {
                    clearFields();
                } else {
                    txtCreateUserName.requestFocus();
                }
            }
            if (RadioUser.isSelected()) {
                if (ManageUser.createNewUser("User", userName, password)) {
                    clearFields();
                } else {
                    txtCreateUserName.requestFocus();
                }
            }
            refreshTableSystemUser();
            return;
        }

        if (ManageUser.loginUser.getUserType().equals("Admin")) {   // Only System User Can create Admin Users

            if (ManageUser.createNewUser("User", userName, password)) {
                clearFields();
            } else {
                txtCreateUserName.requestFocus();
            }
            refreshTableAdminUser();
        }


    }

    public void clickHomeButton(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(this.getClass().getResource("/lk/ijse/dep/fx/views/MainPage.fxml"));
        Scene mainScene = new Scene(root);
        Stage primeryStage = (Stage) btnCreateNewUser.getScene().getWindow();
        primeryStage.setScene(mainScene);
        primeryStage.setTitle("Main Page");
    }
    ////////////////////////////////////////////////////////////
    //    Clear All TextField
    ////////////////////////////////////////////////////////////
    private void clearFields() {
        txtCreateUserName.setText("");
        txtCreatePassword.setText("");
        txtConfirmPassword.setText("");
        txtOldPassword.setText("");
        txtNewPassword.setText("");
    }

    private void refreshTableSystemUser(){
        ArrayList<User> copyOfUserDataBase = new ArrayList<>();

        for (User user : ManageUser.userDatabase) {
            if(!user.getUserType().equals("System")){
                copyOfUserDataBase.add(user);
            }
        }

        ObservableList<User> usersTable = FXCollections.observableArrayList(copyOfUserDataBase);
        tblUsers.setItems(usersTable);
        tblUsers.refresh();

    }

    private void refreshTableAdminUser(){

        ArrayList<User> copyOfUserDataBase = new ArrayList<>();

        for (User user : ManageUser.userDatabase) {
            if(!user.getUserType().equals("System")&&!user.getUserType().equals("Admin")){
                copyOfUserDataBase.add(user);
            }
        }

        ObservableList<User> usersTable = FXCollections.observableArrayList(copyOfUserDataBase);
        tblUsers.setItems(usersTable);
        tblUsers.refresh();
    }

}