package UI;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class observableData {
    StringProperty ID = new SimpleStringProperty();
    StringProperty productID = new SimpleStringProperty();
    StringProperty stockQuantity =new SimpleStringProperty();
    StringProperty WholesaleCost = new SimpleStringProperty();
    StringProperty salePrice = new SimpleStringProperty();

    public void setID(String iD) {
        ID.set(iD);;
    }
    public String getID() {
        return ID.get();
    }
    public void setProductID(String ProductID) {
        this.productID.set(ProductID);
    }
    public String getProductID() {
        return productID.get();
    }
    public void setSalePrice(String salePrice) {
        this.salePrice.set(salePrice);
    }
    public String getSalePrice() {
        return salePrice.get();
    }
    public void setWholesaleCost(String WholesaleCost) {
        this.WholesaleCost.set(WholesaleCost);
    }
    public String getWholesaleCost() {
        return WholesaleCost.get();
    }
    public void setStockQuantity(String q) {
        this.stockQuantity.set(q);
    }
    public String getStockQuantity() {
        return stockQuantity.get();
    }
}