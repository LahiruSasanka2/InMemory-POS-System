package lk.ijse.dep.fx.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import lk.ijse.dep.fx.util.*;

import java.io.IOException;

public class ShowOneOrderController {

    static public Order selectedOrder;

    @FXML
    private Label lblTotal;

    @FXML
    private Button btnBack;

    @FXML
    private TextField txtOrderID;

    @FXML
    private TextField txtCustomerID;

    @FXML
    private TextField txtOrderCreatedDate;

    @FXML
    private TextField txtCustomerName;

    @FXML
    private TableView<OrderDetails> tblItemDetails;

    public void initialize(){

        btnBack.requestFocus();

        txtOrderID.setEditable(false);
        txtOrderCreatedDate.setEditable(false);
        txtCustomerName.setEditable(false);
        txtCustomerID.setEditable(false);

        txtCustomerID.setText(selectedOrder.getCustomerID());

        String customerName = null ;

        for (Customer customer: ManageCustomers.customerDatabase) {
            if(customer.getId().equals(selectedOrder.getCustomerID())){
                customerName = customer.getName();
            }
        }

        txtCustomerName.setText(customerName);
        txtOrderCreatedDate.setText(selectedOrder.getLocalDate());
        txtOrderID.setText(Integer.toString(selectedOrder.getOrderID()));

        tblItemDetails.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("itemCode"));
        tblItemDetails.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("quantity"));
        tblItemDetails.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        tblItemDetails.getColumns().get(3).setCellValueFactory(new PropertyValueFactory<>("total"));


        ObservableList<OrderDetails>  orderDetailsList = FXCollections.observableArrayList(selectedOrder.getOrderDetails());
        tblItemDetails.setItems(orderDetailsList);

        int totalOrderValue = 0;
        for (OrderDetails orderDetails : selectedOrder.getOrderDetails()) {
            totalOrderValue = totalOrderValue + orderDetails.getTotal();
        }
        lblTotal.setText("Total Order Value Rs: " + Integer.toString(totalOrderValue));


    }

    @FXML
    void clickBackButton(ActionEvent event) throws IOException {
        selectedOrder = null;
        Parent root = FXMLLoader.load(this.getClass().getResource("/lk/ijse/dep/fx/views/ShowOrdersPage.fxml"));
        Scene mainScene = new Scene(root);
        Stage primeryStage = (Stage) btnBack.getScene().getWindow();
        primeryStage.setScene(mainScene);
        primeryStage.setTitle("Main Page");

    }

}
