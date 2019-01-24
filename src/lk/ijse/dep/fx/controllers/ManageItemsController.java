package lk.ijse.dep.fx.controllers;

import javafx.application.Platform;
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

public class ManageItemsController {

    @FXML
    private Button btnNewItem;

    @FXML
    private Button btnSave;

    @FXML
    private Button btnDelete;

    @FXML
    private TextField txtDescription;

    @FXML
    private TextField txtCode;

    @FXML
    private TextField txtQuantity;

    @FXML
    private TableView<Item> tblItemsTable;

    @FXML
    private TextField txtPrice;


    public void initialize(){

        if(ManageUser.loginUser.getUserType().equals("User")){         // Users Only can view the items
            btnSave.setDisable(true);
            btnDelete.setDisable(true);
            txtCode.setEditable(false);
            btnNewItem.setVisible(false);
            txtQuantity.setEditable(false);
            txtPrice.setEditable(false);
            txtDescription.setEditable(false);
        }

        tblItemsTable.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("code"));
        tblItemsTable.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("description"));
        tblItemsTable.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("quantity"));
        tblItemsTable.getColumns().get(3).setCellValueFactory(new PropertyValueFactory<>("unitPrice"));

        refreshTable();

        tblItemsTable.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Item>() {
            @Override
            public void changed(ObservableValue<? extends Item> observable, Item oldValue, Item newValue) {

                if(newValue==null){
                    return;
                }
                txtCode.setText(newValue.getCode());
                txtDescription.setText(newValue.getDescription());
                txtPrice.setText(newValue.getUnitPrice());
                txtQuantity.setText(newValue.getQuantity());
                   txtCode.setEditable(false);
                   btnSave.setText("Update");

            }
        });

    }

    @FXML
    void ClickBack(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(this.getClass().getResource("/lk/ijse/dep/fx/views/MainPage.fxml"));
        Scene mainScene = new Scene(root);
        Stage primaryStage = (Stage) txtCode.getScene().getWindow();
        primaryStage.setScene(mainScene);
        primaryStage.setTitle("Main Page");
    }

    @FXML
    void ClickDelete(ActionEvent event) {
            String code = txtCode.getText();

            for(Order order : OrderManage.orderDatabase){
                for (OrderDetails orderDetails: order.getOrderDetails()) {
                    if(orderDetails.getItemCode().equals(code)){
                        new Alert(Alert.AlertType.INFORMATION,"This item has Orders.\nSo can not delete",ButtonType.OK).showAndWait();
                        refreshTable();
                        return;
                    }
                }
            }

            for(Item item : ManageItems.itemDatabase){
                if(code.equals(item.getCode())){
                    int i = ManageItems.itemDatabase.indexOf(item);
                    ManageItems.itemDatabase.remove(i);
                    emptyFields();
                    refreshTable();
                  txtCode.setEditable(true);
                  return;
                }
            }
    }

    @FXML
    void ClickNewItems(ActionEvent event) {
            txtCode.setEditable(true);
            emptyFields();
    }

    @FXML
    void ClickSave(ActionEvent event) {
            String code = txtCode.getText();
            String description = txtDescription.getText();
            String qyt = txtQuantity.getText();
            String price = txtPrice.getText();

        if(ManageItems.isFieldsEmpty(code,description,qyt,price)){
            new Alert(Alert.AlertType.INFORMATION, "Fields Can not be Empty", ButtonType.OK).showAndWait();
            return;
        }

        if(!Validation.isInteger(qyt)){
            new Alert(Alert.AlertType.INFORMATION, "Only Numeric are allow in Q  ", ButtonType.OK).showAndWait();
            return;
        }
        if(!Validation.isInteger(price)){
            new Alert(Alert.AlertType.INFORMATION, "Unit Price ", ButtonType.OK).showAndWait();
            return;
        }

            if(!txtCode.isEditable()){
                for (Item item: ManageItems.itemDatabase) {
                    if(item.getCode().equals(txtCode.getText())){
                        item.setDescription(description);
                        item.setQuantity(qyt);
                        item.setUnitPrice(price);
                        new Alert(Alert.AlertType.INFORMATION, "Item Successfully Updated", ButtonType.OK).showAndWait();
                        refreshTable();
                        txtCode.setEditable(true);
                        emptyFields();
                        return;
                    }
                }
            }else{
                if(ManageItems.addItem(code,description,price,qyt)){
                    new Alert(Alert.AlertType.INFORMATION, "Item Successfully added to the system", ButtonType.OK).showAndWait();
                    emptyFields();
                    refreshTable();
                }else{
                    new Alert(Alert.AlertType.INFORMATION, "Item code Already exist", ButtonType.OK).showAndWait();
                    txtCode.requestFocus();
                }
            }
    }

    private void emptyFields(){
        txtCode.setText("");
        txtDescription.setText("");
        txtPrice.setText("");
        txtQuantity.setText("");
    }
    ////////////////////////////////////////////////////////////
    //    Clear All TextField
    ////////////////////////////////////////////////////////////
    private void refreshTable(){
        ObservableList<Item> items = FXCollections.observableArrayList(ManageItems.itemDatabase);
        tblItemsTable.setItems(items);
        tblItemsTable.refresh();
    }

}
