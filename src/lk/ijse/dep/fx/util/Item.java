package lk.ijse.dep.fx.util;

public class Item {
    private String code;
    private String description;
    private String unitPrice;
    private String quantity;

    public Item(String code, String description, String unitPrice, String quantity) {
        this.code = code;
        this.description = description;
        this.unitPrice = unitPrice;
        this.quantity = quantity;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(String unitPrice) {
        this.unitPrice = unitPrice;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

}
