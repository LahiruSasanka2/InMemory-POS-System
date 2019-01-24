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
import java.time.LocalDate;


public class PlaceOrderController {

    public Order newOrder;

    private boolean edit = false;

    @FXML
    private Button btnAdd;

    @FXML
    private Button btnRemove;

    @FXML
    private TextField txtCustomerID;

    @FXML
    private TableView<OrderDetails> tblOrderDetails;

    @FXML
    private TextField txtOrderID;

    @FXML
    private TextField txtDate;

    @FXML
    private TextField txtCustomerName;

    @FXML
    private TextField txtItemCode;

    @FXML
    private TextField txtItemDescription;

    @FXML
    private TextField txtUnitPrice;

    @FXML
    private TextField txtQuantityInHand;

    @FXML
    private TextField txtOrderQuantity;

    @FXML
    private Label lblTotal;
    private OrderDetails selectedCustomer;
    private Customer thisOrderCustomer;

    @FXML
    void ClickAddButton(ActionEvent event) {

        String itemCode = txtItemCode.getText();
        String unitPrice = txtUnitPrice.getText();
        String description = txtItemDescription.getText();
        String quantity = txtOrderQuantity.getText();
        String quantityInHand = txtQuantityInHand.getText();
        String customerID = txtCustomerID.getText();

        for (OrderDetails orderDetails: newOrder.getOrderDetails()) {         // if given item is already in the Order, this loop assign total order in hand without this order
            if(txtItemCode.getText().equals(orderDetails.getItemCode())){
                quantityInHand = Integer.toString(orderDetails.getQuantityInHand());
                break;
            }
        }

        if (itemCode.trim().isEmpty() || customerID.trim().isEmpty() || quantity.trim().isEmpty()) {
            new Alert(Alert.AlertType.INFORMATION, "Fields Can not Be Empty...").showAndWait();
            return;
        }
        if (!EnterCustomerIDtxt(null)) {
            return;
        }

        if (!Validation.isInteger(quantity)) {
            new Alert(Alert.AlertType.INFORMATION, "Only Numbers are Allowed in Quantity Field..", ButtonType.OK).showAndWait();
            txtOrderQuantity.requestFocus();
            return;
        }

        if (!EnterItemCodetxt(null)) {   // check item code is correct
            return;
        }

        if (0 == Integer.parseInt(quantity)) {
            new Alert(Alert.AlertType.INFORMATION, "Order Quantity Can not be Zero ...", ButtonType.OK).showAndWait();
            txtOrderQuantity.requestFocus();
            return;
        }

        if (edit) {

            // show add item Code txt field test to automatically run

            if (newOrder.changeOrderDetails(selectedCustomer, customerID, itemCode, description, unitPrice, quantity, quantityInHand)) {
                refreshTable();
                clearFields();
                edit = false;
                selectedCustomer = null;
                btnAdd.setText("Add");
                txtItemCode.setEditable(true);
                txtItemCode.requestFocus();
                showTotalOrderValue();
            }

        } else {

            if (newOrder.addOrderDetails(customerID, itemCode, description, unitPrice, quantity, quantityInHand)) {   // Add new item
                refreshTable();
                clearFields();
                txtCustomerID.setEditable(false);
                txtItemCode.setEditable(true);
                txtItemCode.requestFocus();
                showTotalOrderValue();
            } else {
                txtOrderQuantity.requestFocus();
            }
        }
    }

    public void initialize() {
        newOrder = new Order(OrderManage.orderID++, LocalDate.now().toString());
        txtOrderID.setText(Integer.toString(newOrder.getOrderID()));
        txtDate.setText(newOrder.getLocalDate());
        txtOrderID.setEditable(false);
        txtDate.setEditable(false);
        txtCustomerName.setEditable(false);
        txtItemDescription.setEditable(false);
        txtQuantityInHand.setEditable(false);
        txtOrderQuantity.setEditable(false);
        btnRemove.setDisable(true);
        txtItemCode.setEditable(false);
        txtUnitPrice.setEditable(false);

        tblOrderDetails.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("itemCode"));
        tblOrderDetails.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("description"));
        tblOrderDetails.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        tblOrderDetails.getColumns().get(3).setCellValueFactory(new PropertyValueFactory<>("quantity"));
        tblOrderDetails.getColumns().get(4).setCellValueFactory(new PropertyValueFactory<>("total"));

        tblOrderDetails.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<OrderDetails>() {
            @Override
            public void changed(ObservableValue<? extends OrderDetails> observable, OrderDetails oldValue, OrderDetails newValue) {

                if (newValue == null) return;

                txtCustomerID.setText(thisOrderCustomer.getId());
                txtItemCode.setText(newValue.getItemCode());
                txtItemDescription.setText(newValue.getDescription());
                txtQuantityInHand.setText(Integer.toString(newValue.getQuantityInHand()));
                txtUnitPrice.setText(Integer.toString(newValue.getUnitPrice()));
                txtOrderQuantity.setText(Integer.toString(newValue.getQuantity()));
                txtCustomerName.setText(thisOrderCustomer.getName());
                btnAdd.setText("Change");
                selectedCustomer = newValue;
                btnRemove.setDisable(false);
                txtItemCode.setEditable(false);
                edit = true;

            }
        });

    }

    @FXML
    void ClickBack(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(this.getClass().getResource("/lk/ijse/dep/fx/views/MainPage.fxml"));
        Scene mainScene = new Scene(root);
        Stage primeryStage = (Stage) btnAdd.getScene().getWindow();
        primeryStage.setScene(mainScene);
        primeryStage.setTitle("Main Page");
        OrderManage.orderID--;
    }

    @FXML
    void ClickPlaceOrder(ActionEvent event) throws IOException {
        if (newOrder.getOrderDetails().size() == 0) {
            new Alert(Alert.AlertType.INFORMATION, "Order Should contain at least one Item..", ButtonType.OK).showAndWait();
            return;
        }
        newOrder.updateItemInventory();     // update items according to the new order

        OrderManage.orderDatabase.add(newOrder); // this order added to the database

        new Alert(Alert.AlertType.INFORMATION, "Order Successfully Added to the System", ButtonType.OK).showAndWait();

        Parent root = FXMLLoader.load(this.getClass().getResource("/lk/ijse/dep/fx/views/PlaceOrderPage.fxml"));
        Scene mainScene = new Scene(root);
        Stage primaryStage = (Stage) btnAdd.getScene().getWindow();
        primaryStage.setScene(mainScene);
        primaryStage.setTitle("Place Order");

    }

    @FXML
    void ClickRemoveButton(ActionEvent event) {
        int index = newOrder.getOrderDetails().indexOf(selectedCustomer);
        newOrder.getOrderDetails().remove(index);
        selectedCustomer = null;
        new Alert(Alert.AlertType.INFORMATION, "Customer Deleted Successfull...", ButtonType.OK).showAndWait();
        refreshTable();
        clearFields();
        showTotalOrderValue();

        txtItemCode.requestFocus();
    }

    public boolean EnterCustomerIDtxt(ActionEvent actionEvent) {
        String customerID = txtCustomerID.getText();
        for (Customer customer : ManageCustomers.customerDatabase) {
            if (customer.getId().equals(customerID)) {
                txtCustomerName.setText(customer.getName());
                txtItemCode.requestFocus();
                thisOrderCustomer = customer;
                newOrder.setCustomerID(thisOrderCustomer.getId());
                txtItemCode.setEditable(true);

                return true;
            }
        }
        new Alert(Alert.AlertType.INFORMATION, "Wrong Customer ID...", ButtonType.OK).showAndWait();
        txtCustomerID.requestFocus();
        txtCustomerID.selectAll();
        return false;

    }

    public boolean EnterItemCodetxt(ActionEvent actionEvent) {
        String itemCode = txtItemCode.getText();
        for (Item item : ManageItems.itemDatabase) {
            if (item.getCode().equals(itemCode)) {
                txtItemDescription.setText(item.getDescription());
                txtUnitPrice.setText(item.getUnitPrice());
                txtOrderQuantity.requestFocus();
                txtOrderQuantity.setEditable(true);

                int totalQuantityInHand = Integer.parseInt(item.getQuantity());  // show order in Hand if item alreay in the system
                for (OrderDetails orderDetails: newOrder.getOrderDetails()) {
                    if(item.getCode().equals(orderDetails.getItemCode())){
                        totalQuantityInHand = totalQuantityInHand - orderDetails.getQuantity();
                        break;
                    }
                }

                txtQuantityInHand.setText(Integer.toString(totalQuantityInHand));

                return true;
            }
        }
        new Alert(Alert.AlertType.INFORMATION, "Wrong Item Code...", ButtonType.OK).showAndWait();
        txtItemCode.requestFocus();
        txtItemCode.selectAll();
        return false;
    }

    private void refreshTable() {
        ObservableList<OrderDetails> orderDetails = FXCollections.observableArrayList(newOrder.getOrderDetails());
        tblOrderDetails.setItems(orderDetails);
        tblOrderDetails.refresh();
        tblOrderDetails.getSelectionModel().clearSelection();
    }

    ////////////////////////////////////////////////////////////
    //    Clear All TextField
    ////////////////////////////////////////////////////////////
    private void clearFields() {
        txtItemCode.setText("");
        txtItemDescription.setText("");
        txtQuantityInHand.setText("");
        txtUnitPrice.setText("");
        txtOrderQuantity.setText("");

    }

    public void pressEnterOnOrderquantity(ActionEvent actionEvent) {
        ClickAddButton(null);
    }

    private void showTotalOrderValue() {
        int totalOrderValue = 0;
        for (OrderDetails orderDetails : newOrder.getOrderDetails()) {
            totalOrderValue = totalOrderValue + orderDetails.getTotal();
        }
        lblTotal.setText("Rs: " + Integer.toString(totalOrderValue));
    }

}