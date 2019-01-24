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
import javafx.stage.Stage;
import lk.ijse.dep.fx.util.*;

import java.io.IOException;

public class ManageCustomersController {

    @FXML
    private Button btnSave;
    @FXML
    private Button btnDelete;
    @FXML
    private TextField txtName;

    @FXML
    private TextField txtID;

    @FXML
    private TextField txtAddress;

    @FXML
    private TableView<Customer> tblCustomerTable;

    public void initialize() {

        if(ManageUser.loginUser.getUserType().equals("User")){
            btnDelete.setVisible(false);
        }

        tblCustomerTable.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("id"));
        tblCustomerTable.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("name"));
        tblCustomerTable.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("address"));
        ObservableList<Customer> customers = FXCollections.observableArrayList(ManageCustomers.customerDatabase);
        tblCustomerTable.setItems(customers);

        tblCustomerTable.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Customer>() {
            @Override
            public void changed(ObservableValue<? extends Customer> observable, Customer oldValue, Customer newValue) {

                if (newValue == null) return;

                txtID.setText(newValue.getId());
                txtAddress.setText(newValue.getAddress());
                txtName.setText(newValue.getName());
                txtID.setEditable(false);
                btnDelete.setDisable(false);
                btnSave.setText("Change");
            }
        });

        btnDelete.setDisable(true);

    }

    @FXML
    void ClickBack(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(this.getClass().getResource("/lk/ijse/dep/fx/views/MainPage.fxml"));
        Scene mainScene = new Scene(root);
        Stage primaryStage = (Stage) txtID.getScene().getWindow();
        primaryStage.setScene(mainScene);
        primaryStage.setTitle("Main Page");
    }


    @FXML
    void ClickDelete(ActionEvent event) {
        for (Customer customer : ManageCustomers.customerDatabase) {
            if (customer.getId().equals(txtID.getText())) {

                for (Order order : OrderManage.orderDatabase) {
                    if (customer.getId().equals(order.getCustomerID())) {
                        new Alert(Alert.AlertType.INFORMATION, "This Customer Can not Be Deleted \nThis Customer has Orders", ButtonType.OK).showAndWait();
                        return;
                    }
                }

                int deleteIndex = ManageCustomers.customerDatabase.indexOf(customer);
                ManageCustomers.customerDatabase.remove(deleteIndex);
                new Alert(Alert.AlertType.INFORMATION, "Customer Deleted successfully", ButtonType.OK).showAndWait();
                ObservableList<Customer> customers = FXCollections.observableArrayList(ManageCustomers.customerDatabase);
                tblCustomerTable.setItems(customers);
                txtID.setEditable(true);
                txtID.setText("");
                txtName.setText("");
                txtAddress.setText("");
                btnSave.setText("Save");
                return;
            }
        }
    }

    @FXML
    void ClickNewCustomer(ActionEvent event) {
        txtID.setEditable(true);
        txtID.setText("");
        txtName.setText("");
        txtAddress.setText("");
        btnDelete.setDisable(true);
        btnSave.setText("Save");
    }

    @FXML
    void ClickSave(ActionEvent event) {

        String id = txtID.getText();
        String name = txtName.getText();
        String address = txtAddress.getText();

        if (ManageCustomers.isFieldEmpty(id, name, address)) {
            new Alert(Alert.AlertType.INFORMATION, "Fields Can not be empty", ButtonType.OK).showAndWait();
            return;
        }

        if (!txtID.isEditable()) {
            for (Customer customer : ManageCustomers.customerDatabase) {
                if (customer.getId().equals(txtID.getText())) {
                    customer.setName(txtName.getText());
                    customer.setAddress(txtAddress.getText());
                    new Alert(Alert.AlertType.INFORMATION, "Customer Changed Successfully...", ButtonType.OK).showAndWait();
                    ObservableList<Customer> customers = FXCollections.observableArrayList(ManageCustomers.customerDatabase);
                    tblCustomerTable.setItems(customers);
                    tblCustomerTable.getSelectionModel().clearSelection();
                    tblCustomerTable.refresh();
                    clearFields();
                    txtID.setEditable(true);
                    btnSave.setText("Save");
                    return;

                }
            }
        } else {
            if (ManageCustomers.addCustomer(id, name, address)) {
                new Alert(Alert.AlertType.INFORMATION, "Customer Added successfully", ButtonType.OK).showAndWait();
                txtID.setText("");
                txtAddress.setText("");
                txtName.setText("");

                ObservableList<Customer> customers = FXCollections.observableArrayList(ManageCustomers.customerDatabase);
                tblCustomerTable.setItems(customers);

            } else {
                new Alert(Alert.AlertType.INFORMATION, "Customer Not added. \nCustomer ID already Exist in the Database ", ButtonType.OK).showAndWait();
                txtID.requestFocus();
                txtID.selectAll();
            }
        }
    }
////////////////////////////////////////////////////////////
//    Clear All TextField
////////////////////////////////////////////////////////////
    private void clearFields() {
        txtID.setText("");
        txtName.setText("");
        txtAddress.setText("");
    }

}
