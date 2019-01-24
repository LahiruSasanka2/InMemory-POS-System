package lk.ijse.dep.fx.util;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;

public class Order {
    int orderID = 0;
    String  customerID;
    String localDate;
    ArrayList<OrderDetails> orderDetails = new ArrayList<>();


    public String getLocalDate() {
        return localDate;
    }

    public void setLocalDate(String localDate) {
        this.localDate = localDate;
    }

    public void setOrderDetails(ArrayList<OrderDetails> orderDetails) {
        this.orderDetails = orderDetails;
    }

    public ArrayList<OrderDetails> getOrderDetails() {
        return orderDetails;
    }

    public Order(int orderID, String localDate) {
        this.orderID = orderID;
        this.localDate = localDate;
    }


    public void setOrderID(int orderID) {
        this.orderID = orderID;
    }

    public int getOrderID() {
        return this.orderID;
    }

    public String getCustomerID() {
        return customerID;
    }

    public void setCustomerID(String customerID) {
        this.customerID = customerID;
    }

    public boolean addOrderDetails(String customerID, String itemCode, String description, String unitPrice, String quantity, String orderInHand) {

        OrderDetails sameItem = null;

        int totalOrderquantityfromtheItem = Integer.valueOf(quantity);

        for (OrderDetails orderDetails : orderDetails) {
            if (orderDetails.getItemCode().equals(itemCode)) {
                totalOrderquantityfromtheItem = totalOrderquantityfromtheItem + orderDetails.getQuantity();
                sameItem = orderDetails;
            }
        }

        if(Integer.valueOf(quantity)<=0){
            new Alert(Alert.AlertType.INFORMATION, "Order Quantity Should be Greater than Zero").showAndWait();
            return false;
        }
        if (Integer.valueOf(quantity) == totalOrderquantityfromtheItem && Integer.valueOf(orderInHand) >= Integer.valueOf(quantity)) {                  // new item
            int total = Integer.valueOf(quantity) * Integer.valueOf(unitPrice);
            OrderDetails neworderDetails = new OrderDetails(itemCode, Integer.valueOf(quantity), Integer.valueOf(unitPrice), description, total, Integer.valueOf(orderInHand));
            orderDetails.add(neworderDetails);
            return true;
        } else if (Integer.valueOf(orderInHand) < totalOrderquantityfromtheItem) {         // total Order quantity is less than order in hand
            new Alert(Alert.AlertType.INFORMATION, "Order quantity can not exceed quantity in hand...").showAndWait();
            return false;
        } else {                                                                          // Amend existing Order Details
            int total = Integer.valueOf(unitPrice) * totalOrderquantityfromtheItem;
            sameItem.setQuantity(totalOrderquantityfromtheItem);
            sameItem.setTotal(total);
            new Alert(Alert.AlertType.INFORMATION, "Item already exist in the order. \nSo quantity changed.").showAndWait();
            return true;
        }
    }

    public boolean changeOrderDetails(OrderDetails selectedOrderDetail, String customerID, String itemCode, String description, String unitPrice, String quantity, String quantityInHand) {

        if(Integer.valueOf(quantity)<=0){
            new Alert(Alert.AlertType.INFORMATION, "Order Quantity Should be Greater than Zero").showAndWait();
            return false;
        }

        if (Integer.valueOf(quantityInHand) < Integer.valueOf(quantity)) {         // total Order quantity is less than order in hand
            new Alert(Alert.AlertType.INFORMATION, "Order quantity can not exceed quantity in hand...").showAndWait();
            return false;
        }
        if(selectedOrderDetail.getQuantity()==Integer.valueOf(quantity))return true;

        selectedOrderDetail.setItemCode(itemCode);
        selectedOrderDetail.setQuantity(Integer.valueOf(quantity));
        selectedOrderDetail.setDescription(description);
        selectedOrderDetail.setUnitPrice(Integer.valueOf(unitPrice));
        selectedOrderDetail.setQuantityInHand(Integer.parseInt(quantityInHand));
        int total = Integer.valueOf(quantity) * Integer.valueOf(unitPrice);
        selectedOrderDetail.setTotal(total);
        new Alert(Alert.AlertType.INFORMATION, "Order Detail Successfully Changed...", ButtonType.OK).showAndWait();
        return true;
    }

    public void updateItemInventory(){
        for (OrderDetails orderDetails : orderDetails) {
            for (Item item: ManageItems.itemDatabase) {
                if(item.getCode().equals(orderDetails.getItemCode())){
                    int newQuantity = Integer.parseInt(item.getQuantity())-orderDetails.getQuantity();
                    item.setQuantity(Integer.toString(newQuantity));
                    if(Integer.parseInt(item.getQuantity())<0){
                        new Alert(Alert.AlertType.INFORMATION, "Some Error in updateItemInventory function").showAndWait();
                    }
                }
            }
        }
    }

}
