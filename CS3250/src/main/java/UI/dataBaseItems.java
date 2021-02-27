package UI;

import javafx.beans.property.FloatProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class dataBaseItems {


    public final SimpleStringProperty productID = new SimpleStringProperty();
    public final SimpleStringProperty stockQuantity = new SimpleStringProperty();
    public final SimpleStringProperty wholesaleCost = new SimpleStringProperty();
    public final SimpleStringProperty salePrice = new SimpleStringProperty();
    public final SimpleStringProperty supplierID = new SimpleStringProperty();

   // StringProperty productID;
    //IntegerProperty stockQuantity;
    //FloatProperty wholesaleCost,salePrice;
    //StringProperty supplierID;


    public dataBaseItems(String productID, String stockQuantity, String wholesaleCost, String salePrice, String supplierID) {
        this.productID.set(productID);
        this.stockQuantity.set(stockQuantity);
        this.wholesaleCost.set(wholesaleCost);
        this.salePrice.set(salePrice);
        this.supplierID.set(supplierID);
    }
  
    public final String getProductID() {
        return this.productID.get();
    }

    public final String getStockQuantity() {
        return String.valueOf(this.stockQuantity.get()); 
    }

    public final String getWholesaleCost() {
        return String.valueOf(wholesaleCost.get());
    }

    public final String getSalePrice() {
        return this.salePrice.get();
    }


    public final String getSupplierID(){
        return this.supplierID.get();
    }

    public final void setProductID(String productID) {
        this.productID.set(productID);
    }

    public final void setQuantity(String stockQuantity) {
        this.stockQuantity.set(stockQuantity);
    }

    public final void setCost(String wholesaleCost) {
        this.wholesaleCost.set(wholesaleCost);
    }

    public final void setSale(String salePrice) {
        this.salePrice.set(salePrice);
    }

    

}
