package UI;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class observablePO {
    IntegerProperty ID = new SimpleIntegerProperty(0);
    StringProperty productID = new SimpleStringProperty();
    StringProperty quantity=new SimpleStringProperty();
    StringProperty customerLocation = new SimpleStringProperty();
    StringProperty date = new SimpleStringProperty();
    StringProperty email = new SimpleStringProperty();

    public String getCustomerLocation() {
        return customerLocation.get();
    }
    public String getQuantity() {
        return quantity.get();
    }

    public String getEmail() {
        return email.get();
    }
    public void setEmail(String email) {
        this.email.set(email);
    }
    public String getProductID() {
        return productID.get();
    }
    public void setDate(String date) {
        this.date.set(date);
    }
    public String getDate() {
        return date.get();
    }
    public void setProductID(String ProductID) {
        this.productID.set(ProductID);
    }
    public void quantity(String q) {
        this.quantity.set(q);
    }
    public void setCustomerLocation(String customerLocation) {
        this.customerLocation.set(customerLocation);
    }
    public void setID(int iD) {
        ID.set(iD);;
    }
    public int getID() {
        return ID.get();
    }
}