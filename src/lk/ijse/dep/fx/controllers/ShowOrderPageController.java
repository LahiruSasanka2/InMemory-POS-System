package lk.ijse.dep.fx.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import lk.ijse.dep.fx.util.Order;
import lk.ijse.dep.fx.util.OrderManage;
import lk.ijse.dep.fx.util.Validation;

import java.io.IOException;
import java.util.ArrayList;

public class ShowOrderPageController {

    @FXML
    private TextField txtSearch;

    @FXML
    private Button btnBack;

    @FXML
    private TableView<Order> tblOrderDetails;

    public void initialize() {


        tblOrderDetails.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("orderID"));
        tblOrderDetails.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("customerID"));
        tblOrderDetails.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("localDate"));


        ObservableList<Order> orderdatabase = FXCollections.observableArrayList(OrderManage.orderDatabase);
        tblOrderDetails.setItems(orderdatabase);
    }

    @FXML
    public void clickBack(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(this.getClass().getResource("/lk/ijse/dep/fx/views/MainPage.fxml"));
        Scene mainScene = new Scene(root);
        Stage primeryStage = (Stage) btnBack.getScene().getWindow();
        primeryStage.setScene(mainScene);
        primeryStage.setTitle("Main Page");
    }

    public void clickOnItem(MouseEvent mouseEvent) throws IOException {
        Order order = tblOrderDetails.getSelectionModel().getSelectedItem();
        ShowOneOrderController.selectedOrder = order;

        if (order == null) {
            return;
        }

        Parent root = FXMLLoader.load(this.getClass().getResource("/lk/ijse/dep/fx/views/ShowOneOrder.fxml"));
        Scene mainScene = new Scene(root);
        Stage primeryStage = (Stage) btnBack.getScene().getWindow();
        primeryStage.setScene(mainScene);
        primeryStage.setTitle("Show Selected Order");

    }

    public void search(KeyEvent keyEvent) {

      /*  if(keyEvent.getCode().getName().equals("Backspace")){ //remove Backspace from the event.
            return;
        } */

        ArrayList<Order> copyofdataBase = new ArrayList<>();

        if(!txtSearch.getText().equals("")){
            if(!Validation.isInteger(txtSearch.getText())){
                new Alert(Alert.AlertType.INFORMATION, "Only Integers Are allows", ButtonType.OK).showAndWait();
            }


            for (Order order : OrderManage.orderDatabase) {
                copyofdataBase.add(order);
            }

            String searchOrderID = txtSearch.getText();
            int numberOfValues = searchOrderID.length();


            int x = copyofdataBase.size();
            for (int i = 0; i < x; i++) {

                if (copyofdataBase.size() <= i) {
                    break;
                }
                String subString ="";
                try {
                    subString = Integer.toString(copyofdataBase.get(i).getOrderID()).substring(0, numberOfValues);

                } catch (StringIndexOutOfBoundsException e) {
                }

                if (!subString.equals(searchOrderID)) {
                    copyofdataBase.remove(i);
                    i--;
                }
            }

        }else{

            for (Order order : OrderManage.orderDatabase) {
                copyofdataBase.add(order);
            }

            String searchOrderID = txtSearch.getText();
            int numberOfValues = searchOrderID.length();


            int x = copyofdataBase.size();
            for (int i = 0; i < x; i++) {

                if (copyofdataBase.size() <= i) {
                    break;
                }
                String subString ="";
                try {
                    subString = Integer.toString(copyofdataBase.get(i).getOrderID()).substring(0, numberOfValues);

                } catch (StringIndexOutOfBoundsException e) {
                }

                if (!subString.equals(searchOrderID)) {
                    copyofdataBase.remove(i);
                    i--;
                }
            }
        }

        ObservableList<Order> copyOfDatabaseObserverlist = FXCollections.observableArrayList(copyofdataBase);
        tblOrderDetails.setItems(copyOfDatabaseObserverlist);
        tblOrderDetails.refresh();

    }
}
