package lk.ijse.dep.fx.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import lk.ijse.dep.fx.util.*;

import java.io.IOException;

public class MainPageController {

    @FXML
    private Button btnManageCustomers;

    static {

        ManageCustomers.customerDatabase.add(new Customer("C001", "Sameera", "Kalutara"));
        ManageCustomers.customerDatabase.add(new Customer("C002", "Amila", "Panadura"));
        ManageCustomers.customerDatabase.add(new Customer("C003", "Sudaraka", "Wadduwa"));
        ManageCustomers.customerDatabase.add(new Customer("C004", "Danuka", "Maharagama"));

        ManageItems.itemDatabase.add(new Item("IT001", "Apple X", "1600", "50"));
        ManageItems.itemDatabase.add(new Item("IT002", "Apple 8s", "1000", "30"));
        ManageItems.itemDatabase.add(new Item("IT003", "Apple 8s plus", "1300", "20"));
        ManageItems.itemDatabase.add(new Item("IT004", "Apple 8", "800", "10"));

    }

    public void clickManageCustomers(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(this.getClass().getResource("/lk/ijse/dep/fx/views/ManageCustomersPage.fxml"));
        Scene mainScene = new Scene(root);
        Stage primaryStage = (Stage) btnManageCustomers.getScene().getWindow();
        primaryStage.setScene(mainScene);
        primaryStage.setTitle("Manage Customers");
        primaryStage.getIcons().add(new Image("/lk/ijse/dep/fx/img/POS LOGO.png"));
    }

    public void ClickManageitems(ActionEvent actionEvent) throws IOException {

        Parent root = FXMLLoader.load(this.getClass().getResource("/lk/ijse/dep/fx/views/ManageItemPage2.fxml"));
        Scene mainScene = new Scene(root);
        Stage primaryStage = (Stage) btnManageCustomers.getScene().getWindow();
        primaryStage.setScene(mainScene);
        primaryStage.setTitle("Manage Items");
        primaryStage.getIcons().add(new Image("/lk/ijse/dep/fx/img/POS LOGO.png"));
    }

    public void ClickPlaceOrder(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(this.getClass().getResource("/lk/ijse/dep/fx/views/PlaceOrderPage.fxml"));
        Scene mainScene = new Scene(root);
        Stage primaryStage = (Stage) btnManageCustomers.getScene().getWindow();
        primaryStage.setScene(mainScene);
        primaryStage.setTitle("Place Order");
        primaryStage.getIcons().add(new Image("/lk/ijse/dep/fx/img/POS LOGO.png"));
    }

    public void clickViewOrders(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(this.getClass().getResource("/lk/ijse/dep/fx/views/ShowOrdersPage.fxml"));
        Scene mainScene = new Scene(root);
        Stage primaryStage = (Stage) btnManageCustomers.getScene().getWindow();
        primaryStage.setScene(mainScene);
        primaryStage.setTitle("Show Orders");
        primaryStage.getIcons().add(new Image("/lk/ijse/dep/fx/img/POS LOGO.png"));
    }

    public void clickLogOut(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(this.getClass().getResource("/lk/ijse/dep/fx/views/LoginPage.fxml"));
        Scene mainScene = new Scene(root);
        Stage primaryStage = (Stage) btnManageCustomers.getScene().getWindow();
        primaryStage.setScene(mainScene);
        primaryStage.setTitle("Login Page");
        primaryStage.getIcons().add(new Image("/lk/ijse/dep/fx/img/POS LOGO.png"));

        ManageUser.loginUser = null; // Clear Log in user

    }

    public void clickOnSettingButton(ActionEvent actionEvent) throws IOException {

        Parent root = FXMLLoader.load(this.getClass().getResource("/lk/ijse/dep/fx/views/SystemSettingPage.fxml"));
        Scene mainScene = new Scene(root);
        Stage primaryStage = (Stage) btnManageCustomers.getScene().getWindow();
        primaryStage.setScene(mainScene);
        primaryStage.setTitle("Settings");
        primaryStage.getIcons().add(new Image("/lk/ijse/dep/fx/img/POS LOGO.png"));

    }
}
