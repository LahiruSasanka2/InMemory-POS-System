package lk.ijse.dep.fx.util;


public class OrderDetails {
    private String itemCode;
    private int quantity;
    private int unitPrice;
    private String description;
    private int total;
    int quantityInHand;


    public OrderDetails(String itemCode, int quantity, int unitPrice, String description, int total, int quantityInHand) {

        this.itemCode = itemCode;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
        this.description = description;
        this.total = total;
        this.quantityInHand=quantityInHand;

    }

    public void setQuantityInHand(int quantityInHand) {
        this.quantityInHand = quantityInHand;
    }

    public int getQuantityInHand() {
        return quantityInHand;
    }

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(int unitPrice) {
        this.unitPrice = unitPrice;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }
}
